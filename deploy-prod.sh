#!/bin/bash

BRANCH="main"
LOG_DIR="../logs/$(date +%Y-%m-%d)"
LOG_FILE_PREFIX="RUNNING-deploy-prod-$(date +%H-%M-%S)"
LOG_FILE="$LOG_DIR/$LOG_FILE_PREFIX.log"
HEALTH_CHECK_INTERVAL=10
MAX_HEALTH_CHECK_ATTEMPTS=12

update_log_prefix() {
    mv "$LOG_FILE" "$LOG_DIR/$1-deploy-prod-$(date +%H-%M-%S).log"
    LOG_FILE="$LOG_DIR/$1-deploy-prod-$(date +%H-%M-%S).log"
}

mkdir -p $LOG_DIR

echo "==============================" >> $LOG_FILE
echo "Início da operação: $(date '+%Y-%m-%d %H:%M:%S')" >> $LOG_FILE
echo "==============================" >> $LOG_FILE

echo "PIPELIEN TASK >>> Buscando docker image atualizada: $(date '+%Y-%m-%d %H:%M:%S')" >> $LOG_FILE
docker push schmogelga/eventosfull:prod >> $LOG_FILE 2>&1
if [ $? -ne 0 ]; then
    update_log_prefix "FAILED"
    echo "Erro ao buscar a imagem Docker" >> $LOG_FILE
    exit 1
fi

echo "PIPELIEN TASK >>> Removendo container: $(date '+%Y-%m-%d %H:%M:%S')" >> $LOG_FILE
if docker ps -a --format '{{.Names}}' | grep -q '^eventosfull-app-prod-container$'; then
    docker stop eventosfull-app-prod-container >> $LOG_FILE 2>&1
    docker container rm eventosfull-app-prod-container >> $LOG_FILE 2>&1
else
    echo "Container eventosfull-app-prod-container não encontrado" >> $LOG_FILE
fi

echo "PIPELIEN TASK >>> Inicializando aplicação: $(date '+%Y-%m-%d %H:%M:%S')" >> $LOG_FILE
docker run -d --network=eventosfull-rede --name eventosfull-app-prod-container \
  -p 8081:8080 \
  -e HOST=smtp.gmail.com \
  -e PORT=587 \
  -e USER=admin \
  -e PASSWORD=senha \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://eventosfull-postgres-prod:5432/eventosfull-prod \
  -e AMBIENTE=prod \
  schmogelga/eventosfull:prod >> $LOG_FILE 2>&1

if [ $? -ne 0 ]; then
    update_log_prefix "FAILED"
    echo "Erro ao inicializar a aplicação Docker" >> $LOG_FILE
    exit 1
fi

echo "PIPELINE TASK >>> Verificando a saúde do container: $(date '+%Y-%m-%d %H:%M:%S')" >> $LOG_FILE
attempt=1
while [ $attempt -le $MAX_HEALTH_CHECK_ATTEMPTS ]; do
    container_status=$(docker inspect -f '{{.State.Status}}' eventosfull-app-prod-container 2>/dev/null)
    health_status=$(docker inspect -f '{{.State.Health.Status}}' eventosfull-app-prod-container 2>/dev/null)
    if [ "$container_status" == "running" ] && [ "$health_status" == "healthy" ]; then
        echo "Container está saudável e em execução." >> $LOG_FILE
        docker logs eventosfull-app-prod-container >> $LOG_FILE 2>&1
        break
    elif [ "$container_status" == "exited" ] || [ "$container_status" == "dead" ]; then
        update_log_prefix "FAILED"
        echo "Container falhou ao iniciar." >> $LOG_FILE
        docker logs eventosfull-app-prod-container >> $LOG_FILE 2>&1
        exit 1
    else
        echo "Container não está saudável. Tentativa $attempt de $MAX_HEALTH_CHECK_ATTEMPTS." >> $LOG_FILE
    fi
    sleep $HEALTH_CHECK_INTERVAL
    attempt=$((attempt + 1))
done

if [ $attempt -gt $MAX_HEALTH_CHECK_ATTEMPTS ]; then
    update_log_prefix "FAILED"
    echo "O container não se tornou saudável após $((MAX_HEALTH_CHECK_ATTEMPTS * HEALTH_CHECK_INTERVAL)) segundos." >> $LOG_FILE
    docker logs eventosfull-app-prod-container >> $LOG_FILE 2>&1
    exit 1
fi

update_log_prefix "SUCCESS"



