package com.project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @Column(name = "date_creation", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateCreation = LocalDate.now();

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 10, max = 50000)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Dish dish) {
        this(dish.id, dish.dateCreation, dish.name, dish.price);
    }

    public Dish(Integer id, String name, Integer price) {
        this(id, LocalDate.now(), name, price);
    }

    public Dish(Integer id, LocalDate dateCreation, String name, Integer price) {
        super(id, name);
        this.dateCreation = dateCreation;
        this.price = price;
    }
}
