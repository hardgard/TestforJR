package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> readAll(){
        return playerRepository.findAll();
    }

    public void create(Player player) {
        playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).get();
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

}
