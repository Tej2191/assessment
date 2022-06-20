package com.be.assessment.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.assessment.exception.DuplicateStockException;
import com.be.assessment.exception.StockNotFoundException;
import com.be.assessment.model.Stock;
import com.be.assessment.service.StockService;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class StockController {

    @Autowired
    private StockService stockService;

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @PostMapping("/stock")
    public ResponseEntity<Object> createStock(@RequestBody Stock stock) throws DuplicateStockException {
        Optional<Stock> checkStock = stockService.getStock(stock.getSymbol());
        if (checkStock.isPresent()) {
            LOG.error("StockController.createStock duplicate stock already present for symbol: {}", stock.getSymbol());
            throw new DuplicateStockException("Stock already exists in database");
        }
        stockService.createStock(stock);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/stock/{symbol}")
    public ResponseEntity<?> getStock(@PathVariable String symbol) throws StockNotFoundException {
        Optional<Stock> stock = stockService.getStock(symbol);
        if (stock.isPresent()) {
            return new ResponseEntity(stock, HttpStatus.OK);
        }
        LOG.error("StockController.getStock stock not present for symbol: {}", symbol);
        throw new StockNotFoundException("Stock not found in database");
    }

    @PatchMapping("/stock/{symbol}")
    public ResponseEntity<?> patchStock(@RequestBody Stock stock, @PathVariable String symbol)
            throws StockNotFoundException {
        Optional<Stock> existingStock = stockService.getStock(symbol);
        if (!existingStock.isPresent()) {
            LOG.error("StockController.patchStock stock not present for symbol: {}", symbol);
            throw new StockNotFoundException("Stock not found for symbol: " + symbol);
        }
        stock = stockService.updateStock(existingStock.get(), stock);
        return new ResponseEntity(stock, HttpStatus.OK);
    }

    @DeleteMapping("/stock/{symbol}")
    public ResponseEntity<?> deleteStock(@PathVariable String symbol) throws StockNotFoundException {
        Optional<Stock> existingStock = stockService.getStock(symbol);
        if (!existingStock.isPresent()) {
            LOG.error("StockController.deleteStock stock not present for symbol: {}", symbol);
            throw new StockNotFoundException("Stock not found for symbol: " + symbol);
        }
        stockService.deleteStock(symbol);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/stock/getAllStocks")
    public ResponseEntity<?> getAllStocks() throws StockNotFoundException {
        List<Stock> list = stockService.getAllStocks();
        if (list.isEmpty()) {
            LOG.error("StockController.getAllStocks no stock present in database");
            throw new StockNotFoundException("No Stocks found in database");
        }
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/stock/bulkInsert")
    public ResponseEntity<?> bulkInsertData(@RequestBody List<Stock> list) {
        stockService.bulkInsert(list);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
