package com.be.assessment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Stock {

    @Id
    private String symbol;
    private String name;
    private String sector;
    private String headQuarter;
    private String dateFirstAdded;
}
