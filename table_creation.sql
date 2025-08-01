-- 1. Create the database
CREATE DATABASE IF NOT EXISTS stock_trading;

-- 2. Use the newly created database
USE stock_trading;

-- 3. Create the 'portfolio' table
CREATE TABLE IF NOT EXISTS portfolio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_ticker VARCHAR(10) NOT NULL,
    volume INT NOT NULL,
    price_of_buying DECIMAL(10, 2) NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- 4. Create the 'orders' table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_ticker VARCHAR(10) NOT NULL,
    volume INT NOT NULL,
    price_of_buying DECIMAL(10, 2) NOT NULL,
    buy_sell TINYINT(1) NOT NULL,  -- 1 = Buy, 0 = Sell
    status_code INT NOT NULL,      -- 0 = Pending, 1 = Success, 2 = Failed
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 5. Insert dummy values into 'portfolio'
INSERT INTO portfolio (stock_ticker, volume, price_of_buying)
VALUES 
('AAPL', 100, 150.00),
('GOOGL', 50, 2800.50),
('TSLA', 30, 700.75);


-- 6. Insert dummy values into 'orders'
INSERT INTO orders (stock_ticker, volume, price_of_buying, buy_sell, status_code)
VALUES 
('AAPL', 50, 155.00, 1, 1),   -- Successful buy
('GOOGL', 20, 2850.00, 0, 0), -- Pending sell
('TSLA', 15, 710.00, 0, 2);   -- Failed sell
