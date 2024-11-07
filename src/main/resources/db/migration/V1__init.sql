CREATE SCHEMA IF NOT EXISTS dbo;
CREATE TABLE IF NOT EXISTS dbo.task
(
    id          BIGSERIAL    NOT NULL UNIQUE,
    title       VARCHAR(250) NOT NULL UNIQUE,
    description TEXT         NOT NULL,
    status      VARCHAR(250) NOT NULL,
    created     TIMESTAMP DEFAULT now(),
    updated     TIMESTAMP DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE SEQUENCE dbo.ask_seq START WITH 1 INCREMENT BY 1;