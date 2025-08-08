-- portfolio
INSERT INTO portfolio (stock_ticker, volume, price_of_buying)
VALUES
('AAPL', 100, 150.00),
('GOOGL', 50, 2800.50),
('TSLA', 30, 700.75);

-- trading
INSERT INTO trading (stock_ticker, volume_to_trade, buy_sell, price_of_action)
VALUES
('AAPL', 50, 1, 155.00),
('GOOGL', 20, 0, 2850.00),
('MSFT', 10, 1, 310.00);

-- orders
INSERT INTO orders (stock_ticker, volume, price_of_buying, buy_sell, status_code)
VALUES
('AAPL', 50, 155.00, 1, 1),
('GOOGL', 20, 2850.00, 0, 0),
('TSLA', 15, 710.00, 0, 2);
