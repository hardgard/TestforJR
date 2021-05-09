package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@RestController

public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @PersistenceContext
    private EntityManager em;
    @Autowired
    PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value ="/rest/players")
    public ResponseEntity<List<Player>> readAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String race,
            @RequestParam(required = false) String profession,
            @RequestParam(defaultValue = "id") String order,
            @RequestParam(required = false) String banned,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize) {

        order = order.toLowerCase(Locale.ROOT);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = criteriaBuilder.createQuery(Player.class);
        Root<Player> from = criteriaQuery.from(Player.class);
        CriteriaQuery<Player> select = criteriaQuery.select(from).orderBy(criteriaBuilder.asc(from.get(order)));

        Predicate criteria = criteriaBuilder.conjunction();
        if (name != null) {
            Predicate p = criteriaBuilder.like(from.get("name"),"%"+name+"%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (title != null) {
            Predicate p = criteriaBuilder.like(from.get("title"), "%"+title+"%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (race != null) {
            Predicate p = criteriaBuilder.equal(from.get("race"), race);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (profession != null) {
            Predicate p = criteriaBuilder.equal(from.get("profession"), profession);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (banned != null) {
            if(banned.equals("false")) {
                Predicate p = criteriaBuilder.equal(from.get("banned"), false);
                criteria = criteriaBuilder.and(criteria, p);
            }else {
                Predicate p = criteriaBuilder.equal(from.get("banned"), true);
                criteria = criteriaBuilder.and(criteria, p);
            }
        }
        if (minExperience != null){
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("experience"), minExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxExperience != null){
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("experience"), maxExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (minLevel != null){
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("level"), minLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxLevel != null){
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("level"), maxLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (after != null){
            Predicate p = criteriaBuilder.greaterThan(from.get("birthday"), new Date(after));
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (before != null){
            Predicate p = criteriaBuilder.lessThan(from.get("birthday"), new Date(before));
            criteria = criteriaBuilder.and(criteria, p);
        }
        criteriaQuery.where(criteria);
        pageNumber = pageNumber*pageSize;
        TypedQuery<Player> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(pageNumber);
        typedQuery.setMaxResults(pageSize);
        List<Player> players = typedQuery.getResultList();
        //List<Player> players;

        //Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(order));

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
//        Root<Player> from = criteriaQuery.from(Player.class);
//        //criteriaQuery.select(from);
        //em.createQuery(criteriaQuery).setFirstResult(pageNumber).setMaxResults(pageSize);
//        Predicate criteria = cb.conjunction();
//        if (name != null) {
//            Predicate p = cb.equal(from.get("name"),name);
//            criteria = cb.and(criteria, p);
//        }
//        //lastName
//        if (title != null) {
//            Predicate p = cb.equal(from.get("title"), title);
//            criteria = cb.and(criteria, p);
//        }

        //criteriaQuery.where(criteria);
//        criteriaQuery.orderBy(cb.asc(from.get(order)));
//        em.createQuery(criteriaQuery).setFirstResult(pageNumber).setMaxResults(pageSize);
        //List<Player> result = em.createQuery(criteriaQuery).getResultList();
        return new ResponseEntity<>( players, HttpStatus.OK);

//        Page<Player> pagePlayers;
//
//        if (title != null)
//            pagePlayers = playerRepository.findByTitleContaining(title, paging);
//        else if (name != null)
//            pagePlayers = playerRepository.findByNameContaining(name, paging);
//        else if (banned != null)
//            if (banned.equals("false"))
//                pagePlayers = playerRepository.findByBanned(false, paging);
//            else
//                pagePlayers = playerRepository.findByBanned(true, paging);
//        else if (race != null)
//            pagePlayers = playerRepository.findByRaceContaining(race, paging);
//        else if (profession != null)
//            pagePlayers = playerRepository.findByProfessionContaining(profession, paging);
//        else if (maxExperience != null && minExperience != null)
//            pagePlayers = playerRepository.findByExperienceBetween(maxExperience,minExperience, paging);
//        else if(minExperience != null )
//            pagePlayers = playerRepository.findByExperienceGreaterThanEqual(minExperience,paging);
//        else if(maxExperience != null )
//            pagePlayers = playerRepository.findByExperienceLessThanEqual(maxExperience,paging);
//        else
//            pagePlayers =  playerRepository.findAll(paging);
//
//
//
//
//        players = pagePlayers.getContent();
//
//        logger.debug("readALl()) have read "+players);
//        return players != null && !players.isEmpty()
//                ? new ResponseEntity<>(players, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/rest/players/count")
    public Integer count(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String race,
            @RequestParam(required = false) String profession,
            @RequestParam(defaultValue = "id") String order,
            @RequestParam(required = false) String banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "3") int pageSize) {
        order = order.toLowerCase(Locale.ROOT);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = criteriaBuilder.createQuery(Player.class);
        Root<Player> from = criteriaQuery.from(Player.class);
        CriteriaQuery<Player> select = criteriaQuery.select(from).orderBy(criteriaBuilder.asc(from.get(order)));
        Predicate criteria = criteriaBuilder.conjunction();
        if (name != null) {
            Predicate p = criteriaBuilder.like(from.get("name"),"%"+name+"%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (title != null) {
            Predicate p = criteriaBuilder.like(from.get("title"), "%"+title+"%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        criteriaQuery.where(criteria);
        TypedQuery<Player> typedQuery = em.createQuery(select);
        List<Player> players = typedQuery.getResultList();
        return players.size();
//        order = order.toLowerCase(Locale.ROOT);
//        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(order));
//
//
//        if (title != null)
//            return (int)playerRepository.findByTitleContaining(title, paging).getTotalElements();
//        else if (name != null)
//            return (int)playerRepository.findByNameContaining(name, paging).getTotalElements();
//        else if (banned != null)
//            if (banned.equals("false"))
//                return (int) playerRepository.findByBanned(false, paging).getTotalElements();
//            else
//                return (int) playerRepository.findByBanned(true, paging).getTotalElements();
//        else if (race != null)
//            return (int) playerRepository.findByRaceContaining(race, paging).getTotalElements();
//        else if (profession != null)
//            return (int) playerRepository.findByProfessionContaining(profession, paging).getTotalElements();
//        else if (maxExperience != null && minExperience != null)
//            return (int) playerRepository.findByExperienceBetween(maxExperience,minExperience, paging).getTotalElements();
//        else if (maxExperience != null )
//            return (int) playerRepository.findByExperienceLessThanEqual(maxExperience, paging).getTotalElements();
//        else if (minExperience != null )
//            return (int) playerRepository.findByExperienceGreaterThanEqual(minExperience, paging).getTotalElements();
//        else return (int) playerRepository.findAll(paging).getTotalElements();
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

    @PostMapping("/rest/players/{id}")
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
