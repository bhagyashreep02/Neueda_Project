
package com.sj.springboot.rest_api.controller;

import com.sj.springboot.rest_api.dto.TransactRequest;
import com.sj.springboot.rest_api.dto.TransactResponse;
import com.sj.springboot.rest_api.service.TransactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transact")
public class TransactController {

    private final TransactService transactService;

    @Autowired
    public TransactController(TransactService transactService) {
        this.transactService = transactService;
    }

    /**
     * Execute a buy or sell transaction.
     *
     * @param request JSON body with:
     *                - ticker (String)
     *                - quantity (Integer)
     *                - price (Double)
     *                - action ("BUY" or "SELL")
     * @return 200 OK with a TransactResponse containing:
     *         - orderId
     *         - statusCode (1=success,2=failed)
     *         - executedAt (timestamp)
     *         - profitPrice & profitPercentage (only for SELL)
     */
    @PostMapping
    public ResponseEntity<TransactResponse> transact(
            @Valid @RequestBody TransactRequest request) {
        TransactResponse response = transactService.execute(request);
        return ResponseEntity.ok(response);
    }
}
