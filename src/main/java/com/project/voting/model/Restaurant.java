package com.project.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.voting.HasId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {

    @Column(name = "date_creation", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dateCreation = new Date();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateCreation DESC")
//    https://stackoverflow.com/questions/20119142/jackson-multiple-back-reference-properties-with-name-defaultreference
    @JsonManagedReference(value = "restaurant_vote")
    private List<Vote> votes;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.dateCreation);
    }

    public Restaurant(Integer id, String name) {
        this(id, name, new Date());
    }

    public Restaurant(Integer id, String name, Date dateCreation) {
        super(id, name);
        this.dateCreation = dateCreation;
    }
}
