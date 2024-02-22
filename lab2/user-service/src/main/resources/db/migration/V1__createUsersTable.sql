CREATE TABLE IF NOT EXISTS "user-schema".users
(
    user_id integer NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    login character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    enabled boolean NOT NULL,
    company_id integer NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS "user-schema".users
    OWNER to postgres;