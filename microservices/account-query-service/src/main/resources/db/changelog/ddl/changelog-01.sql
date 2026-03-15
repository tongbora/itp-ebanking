-- liquibase formatted sql

-- changeset tong-bora:1773031874311-1
CREATE TABLE account_types
(
    account_type_id UUID NOT NULL,
    account_type    VARCHAR(255),
    CONSTRAINT pk_account_types PRIMARY KEY (account_type_id)
);

-- changeset tong-bora:1773031874311-2
CREATE TABLE accounts
(
    account_id      UUID NOT NULL,
    account_number  VARCHAR(255),
    account_holder  VARCHAR(255),
    customer_id     UUID,
    account_type_id UUID,
    branch_id       UUID,
    account_status  VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    created_by      VARCHAR(255),
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_by      VARCHAR(255),
    CONSTRAINT pk_accounts PRIMARY KEY (account_id)
);

-- changeset tong-bora:1773031874311-3
ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_ACCOUNT_TYPE FOREIGN KEY (account_type_id) REFERENCES account_types (account_type_id);

