CREATE DATABASE IF NOT EXISTS pillowcase;
USE pillowcase;

CREATE TABLE IF NOT EXISTS strategies(
    strategyID varchat(36) primary key,
    `type` varchar(50),
    
)