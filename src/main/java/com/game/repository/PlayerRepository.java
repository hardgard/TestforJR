package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Page<Player> findByNameContaining(String name, Pageable pageable);
    Page<Player> findByTitleContaining(String title, Pageable pageable);
    Page<Player> findByRaceContaining(String race, Pageable pageable);
    Page<Player> findByProfessionContaining(String profession, Pageable pageable);
    @Query("select p from Player p where p.experience>= :minExperience")
    Page<Player> findByExperienceGreaterThanEqual(@Param("minExperience") Integer minExperience, Pageable pageable);
    @Query("select p from Player p where p.experience <= :maxExperience")
    Page<Player> findByExperienceLessThanEqual(@Param("maxExperience") Integer maxExperience, Pageable pageable);
    @Query("select p from Player p where p.experience <= :maxExperience and p.experience >= :minExperience")
    Page<Player> findByExperienceBetween(@Param("maxExperience") Integer maxExperience,@Param("minExperience") Integer minExperience, Pageable pageable);
    Page<Player> findByBanned(Boolean banned, Pageable pageable);


}
