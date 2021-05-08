package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Page<Player> findByNameContaining(String name, Pageable pageable);
    Page<Player> findByTitleContaining(String title, Pageable pageable);
    Page<Player> findByRaceContaining(String race, Pageable pageable);
    Page<Player> findByProfessionContaining(String profession, Pageable pageable);
    Page<Player> findByExperienceGreaterThanEqual(Integer minExperience, Pageable pageable);
    Page<Player> findByBanned(Boolean banned, Pageable pageable);

}
