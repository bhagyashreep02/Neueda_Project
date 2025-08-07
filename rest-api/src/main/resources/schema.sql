-- 1. Create the database (H2 ignores database names)
-- 2. Use the newly created database (handled by Spring)

-- 3. portfolio table
CREATE TABLE IF NOT EXISTS portfolio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_ticker VARCHAR(10) NOT NULL,
    volume INT NOT NULL,
    price_of_buying DECIMAL(10, 2) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. trading table
CREATE TABLE IF NOT EXISTS trading (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_ticker VARCHAR(10) NOT NULL,
    volume_to_trade INT NOT NULL,
    buy_sell TINYINT NOT NULL,  -- 1 = Buy, 0 = Sell
    price_of_action DECIMAL(10, 2) NOT NULL
);

-- 5. orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_ticker VARCHAR(10) NOT NULL,
    volume INT NOT NULL,
    price_of_buying DECIMAL(10, 2) NOT NULL,
    buy_sell TINYINT NOT NULL,   -- 1 = Buy, 0 = Sell
    status_code INT NOT NULL,     -- 0 = Pending, 1 = Success, 2 = Failed
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
