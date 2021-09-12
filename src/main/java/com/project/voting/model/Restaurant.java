package com.project.voting.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.voting.HasId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//https://stackoverflow.com/a/50146162
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Restaurant extends NamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateCreation DESC")
    private List<Vote> votes;

    public Restaurant(Restaurant r) {
        this(r.id, r.name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
