package com.be.assessment.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.assessment.model.Stock;
import com.be.assessment.repository.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);

    public void createStock(Stock stock) {
        LOG.info("StockService.createStock: save the stock to database: {}", stock);
        stockRepository.save(stock);
    }

    public Optional<Stock> getStock(String symbol) {
        LOG.info("StockService.getStock: get stock for symbol: {}", symbol);
        return stockRepository.findById(symbol);
    }

    public Stock updateStock(Stock existingStock, Stock updatedStock) {
        existingStock.setHeadQuarter(updatedStock.getHeadQuarter());
        existingStock.setName(updatedStock.getName());
        existingStock.setSector(updatedStock.getSector());
        LOG.info("StockService.updateStock: update stock for symbol: {}", existingStock.getSymbol());
        return stockRepository.save(existingStock);
    }

    public List<Stock> getAllStocks() {
        LOG.info("StockService.getAllStocks: get all the stocks present in the database");
        return stockRepository.findAll();
    }

    public void deleteStock(String symbol) {
        LOG.info("StockService.deleteStock: delete a stock for symbol: {}", symbol);
        stockRepository.deleteById(symbol);
    }

    public void bulkInsert(List<Stock> lists) {
        LOG.info("StockService.bulkInsert: bulk insert stocks of size: {}", lists.size());
        stockRepository.saveAll(lists);
    }
}
