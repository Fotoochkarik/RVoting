package com.project.voting.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {
    private String name;
    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
