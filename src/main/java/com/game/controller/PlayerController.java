package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

@RestController

public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value ="/rest/players")
    public ResponseEntity<List<Player>> readALl() {
        final List<Player> players = playerService.readAll();
        logger.debug("readALl()) have read "+players);
        return players != null && !players.isEmpty()
                ? new ResponseEntity<>(players, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/rest/players/count")
    public Integer count() {
        return playerService.readAll().size();
    }

    @GetMapping("/rest/players/{id}")
    public ResponseEntity<Player> get(@PathVariable Long id) {
        try {
            Player player = playerService.getPlayer(id);
            logger.debug("get() got player "+player);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rest/players")
    public ResponseEntity<?> create (@RequestBody Player player) {
        player.setLevel((int) ((Math.sqrt(2500
                        +200*player.getExperience())-50)/100));
        player.setUntilNextLevel(50*(player.getLevel()+1)*(player.getLevel()+2)- player.getExperience());
        playerService.create(player);
        logger.debug("create() created player "+ player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PutMapping("/rest/players/{id}")
    public ResponseEntity<?> update(@RequestBody Player player, @PathVariable Long id) {
        try {
            logger.debug("update() updating "+ player);
            Player existPlayer = playerService.getPlayer(id);
            player.setId(id);

            return create(player);
        } catch (NoSuchElementException e) {
            logger.error("update() failed" + e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/rest/players/{id}")
    public void delete(@PathVariable Long id) {
        logger.debug("deleting player" + id);
        playerService.deletePlayer(id);
        logger.debug("player" + id+"deleted");
    }

}
