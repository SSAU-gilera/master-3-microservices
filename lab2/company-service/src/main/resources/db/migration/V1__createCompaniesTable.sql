-- Table: company-schema.companies

-- DROP TABLE IF EXISTS "company-schema".companies;

CREATE TABLE IF NOT EXISTS "company-schema".companies
(
    company_id integer NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    ogrn character varying COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    director_id integer NOT NULL,
    CONSTRAINT companies_pkey PRIMARY KEY (company_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS "company-schema".companies
    OWNER to postgres;