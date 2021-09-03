package com.project.voting.to;

import com.project.voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    @NotNull
    LocalDate dateCreation;

    public RestaurantTo(Integer id, String name, LocalDate dateCreation) {
        super(id, name);
        this.dateCreation = dateCreation;
    }
}
