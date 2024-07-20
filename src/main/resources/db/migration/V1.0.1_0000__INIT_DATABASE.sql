-- public.evento definition

-- Drop table

-- DROP TABLE public.evento;

CREATE TABLE public.evento (
                             id uuid NOT NULL,
                             "data" date NULL,
                             nome varchar(255) NULL,
                             CONSTRAINT evento_pkey PRIMARY KEY (id)
);


-- public.logs definition

-- Drop table

-- DROP TABLE public.logs;

CREATE TABLE public.logs (
                           id uuid NOT NULL,
                           data_hora_checkin timestamp(6) NULL,
                           metodo varchar(255) NULL,
                           response int4 NULL,
                           rota varchar(255) NULL,
                           usuario varchar(255) NULL,
                           CONSTRAINT logs_pkey PRIMARY KEY (id)
);


-- public.roles definition

-- Drop table

-- DROP TABLE public.roles;

CREATE TABLE public.roles (
                            id uuid NOT NULL,
                            "name" varchar(255) NULL,
                            CONSTRAINT roles_pkey PRIMARY KEY (id),
                            CONSTRAINT uk_ofx66keruapi6vyqpv6f2or37 UNIQUE (name)
);


-- public.usuario definition

-- Drop table

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario (
                              id uuid NOT NULL,
                              email varchar(255) NULL,
                              nome varchar(255) NULL,
                              username varchar(255) NULL,
                              CONSTRAINT usuario_pkey PRIMARY KEY (id)
);


-- public.inscricao definition

-- Drop table

-- DROP TABLE public.inscricao;

CREATE TABLE public.inscricao (
                                id uuid NOT NULL,
                                evento_id uuid NULL,
                                user_info_id uuid NULL,
                                CONSTRAINT inscricao_pkey PRIMARY KEY (id),
                                CONSTRAINT fk5qsua0ncb80fj3bxjcb8r2sje FOREIGN KEY (evento_id) REFERENCES public.evento(id),
                                CONSTRAINT fkc7jtrs8ng0sb7i24n7kaca6ju FOREIGN KEY (user_info_id) REFERENCES public.usuario(id)
);


-- public.presenca definition

-- Drop table

-- DROP TABLE public.presenca;

CREATE TABLE public.presenca (
                               id uuid NOT NULL,
                               data_hora_checkin timestamp(6) NULL,
                               inscricao_id uuid NULL,
                               CONSTRAINT presenca_pkey PRIMARY KEY (id),
                               CONSTRAINT fk5dbyk388nu0fxbg7882lqotvq FOREIGN KEY (inscricao_id) REFERENCES public.inscricao(id)
);


-- public.usuario_roles definition

-- Drop table

-- DROP TABLE public.usuario_roles;

CREATE TABLE public.usuario_roles (
                                    user_info_id uuid NOT NULL,
                                    roles_id uuid NOT NULL,
                                    CONSTRAINT usuario_roles_pkey PRIMARY KEY (user_info_id, roles_id),
                                    CONSTRAINT fk45th3jgtqendsla8q3q1h02rt FOREIGN KEY (roles_id) REFERENCES public.roles(id),
                                    CONSTRAINT fkc37kb4gxiv2tg7hqh7us7qohk FOREIGN KEY (user_info_id) REFERENCES public.usuario(id)
);
