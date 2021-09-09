package com.project.voting.to;

import com.project.voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
