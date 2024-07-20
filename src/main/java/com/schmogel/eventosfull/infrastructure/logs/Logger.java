package com.schmogel.eventosfull.infrastructure.logs;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Logger {

    private final LogRepository logRepository;

    public void saveLog(LogInfo log) {
        logRepository.save(log);
    }
}
