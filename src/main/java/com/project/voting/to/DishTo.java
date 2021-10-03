package com.project.voting.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo implements HasId {

    @NotNull
    @Range(max = 5000000)
    Integer price;

    @NotNull
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate dateOfUse;

    public DishTo(Integer id, LocalDate dateOfUse, String name, Integer price) {
        super(id, name);
        this.dateOfUse = dateOfUse;
        this.price = price;
    }
}
