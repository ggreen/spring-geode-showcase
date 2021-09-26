CREATE SCHEMA IF NOT EXISTS spring_showcase;

CREATE TABLE spring_showcase.ACCOUNTS (
	account_id varchar(255) not NULL,
    account_nm varchar(255) not NULL,
	CONSTRAINT account_vpkey PRIMARY KEY (account_id)
);