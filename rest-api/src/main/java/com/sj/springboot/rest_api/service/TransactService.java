package com.sj.springboot.rest_api.service;

import com.sj.springboot.rest_api.dto.TransactRequest;
import com.sj.springboot.rest_api.dto.TransactResponse;

public interface TransactService {
    /**
     * Execute a BUY or SELL transaction.
     *
     * @param request contains ticker, quantity, price, action ("BUY"/"SELL")
     * @return a TransactResponse with orderId, statusCode, timestamp, and (for SELL) profit info
     */
    TransactResponse execute(TransactRequest request);
}
