CREATE DATABASE IF NOT EXISTS dreamcatcher;
USE dreamcatcher;

CREATE TABLE IF NOT EXISTS two_moving_averages (
    strategyID VARCHAR(36) PRIMARY KEY,
    `type` VARCHAR(50),
    long_time INTEGER,
    short_time INTEGER,
    stock_name VARCHAR(4),
    volume INTEGER,
    cut_off_percentage DECIMAL(3,2),
    `action` VARCHAR(4),
    buying BOOLEAN,
    delta DECIMAL(3,2),
);

CREATE TABLE IF NOT EXISTS orders (
    orderID VARCHAR(36) PRIMARY KEY,
    buy BOOLEAN,
    price DECIMAL(9,4),
    size INTEGER,
    stock VARCHAR(4),
    when_as_date DATE,
    response VARCHAR(16),
    strategyID VARCHAR(36),
    two_avg_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (two_avg_id) REFERENCES two_moving_averages(strategyID)
);
