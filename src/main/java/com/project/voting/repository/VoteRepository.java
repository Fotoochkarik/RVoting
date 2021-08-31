package com.project.voting.repository;

import com.project.voting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=?1 AND v.dateCreation =?2")
    Optional<Vote> findByUserIdAndDate(int id, LocalDate current);

    @Query("SELECT v FROM Vote v WHERE v.id =?1 AND v.user.id=?2 ")
    Optional<Vote> findByIdAndUserId(int id, int userId);
}
