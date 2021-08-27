package com.project.voting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.voting.HasId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements HasId {

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
////    @OrderBy("dateTime DESC")
//    @JsonManagedReference
//    private List<Dish> menu;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote")
////    @OrderBy("dateTime DESC")
//    @JsonManagedReference
//    private Set<Vote> votes;

    public Restaurant(Integer id, String name, Date registered) {
        super(id, name);
        this.registered = registered;
    }

//    public Restaurant(Dish ... dishes){
//        this.menu = List.of(dishes);
//    }
}
