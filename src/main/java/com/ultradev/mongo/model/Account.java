package com.ultradev.mongo.model;
import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Data
@Entity("scenarios")
public class Account {
    
    @Id
    private ObjectId id;
    private String user;
    private Double amount;
    private Long createdDate;
    
}