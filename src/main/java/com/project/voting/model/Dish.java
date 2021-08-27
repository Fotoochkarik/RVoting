package com.project.voting.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dish")
@Getter
@Setter
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 50000)
    private Integer price;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "restaurant_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
//    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.price);
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
