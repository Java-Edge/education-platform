package com.javagpt.dict.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author JavaEdge
 * @date 2024/7/5
 */
@Document
@Getter
@AllArgsConstructor
public class Account {

    @Id
    private String id;
    private String accountCode;
    private String accountName;
}
