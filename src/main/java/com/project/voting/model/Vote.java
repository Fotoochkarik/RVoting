package com.project.voting.model;

import com.project.voting.HasId;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_creation"},
        name = "votes_unique_user_created_idx")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, exclude = {"dateCreation", "restaurant"})
public class Vote extends BaseEntity implements HasId {

    @Column(name = "date_creation", nullable = false)
    @NotNull
    private LocalDate dateCreation = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Vote(Integer id, User user, Restaurant restaurant) {
        this(id, user, restaurant, LocalDate.now());
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate dateCreation) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.dateCreation = dateCreation;
    }
}
