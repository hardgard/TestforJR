package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@RestController

public class PlayerController {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/rest/players")
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
            Predicate p = criteriaBuilder.like(from.get("name"), "%" + name + "%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (title != null) {
            Predicate p = criteriaBuilder.like(from.get("title"), "%" + title + "%");
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
            Predicate p;
            if (banned.equals("false")) {
                p = criteriaBuilder.equal(from.get("banned"), false);
            } else {
                p = criteriaBuilder.equal(from.get("banned"), true);
            }
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (minExperience != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("experience"), minExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxExperience != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("experience"), maxExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (minLevel != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("level"), minLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxLevel != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("level"), maxLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (after != null) {
            Predicate p = criteriaBuilder.greaterThan(from.get("birthday"), new Date(after));
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (before != null) {
            Predicate p = criteriaBuilder.lessThan(from.get("birthday"), new Date(before));
            criteria = criteriaBuilder.and(criteria, p);
        }
        criteriaQuery.where(criteria);
        pageNumber = pageNumber * pageSize;
        TypedQuery<Player> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(pageNumber);
        typedQuery.setMaxResults(pageSize);
        List<Player> players = typedQuery.getResultList();

        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/players/count")
    public ResponseEntity<Integer> count(
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
            Predicate p = criteriaBuilder.like(from.get("name"), "%" + name + "%");
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (title != null) {
            Predicate p = criteriaBuilder.like(from.get("title"), "%" + title + "%");
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
            Predicate p;
            if (banned.equals("false")) {
                p = criteriaBuilder.equal(from.get("banned"), false);
            } else {
                p = criteriaBuilder.equal(from.get("banned"), true);
            }
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (minExperience != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("experience"), minExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxExperience != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("experience"), maxExperience);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (minLevel != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(from.get("level"), minLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (maxLevel != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(from.get("level"), maxLevel);
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (after != null) {
            Predicate p = criteriaBuilder.greaterThan(from.get("birthday"), new Date(after));
            criteria = criteriaBuilder.and(criteria, p);
        }
        if (before != null) {
            Predicate p = criteriaBuilder.lessThan(from.get("birthday"), new Date(before));
            criteria = criteriaBuilder.and(criteria, p);
        }
        criteriaQuery.where(criteria);
        TypedQuery<Player> typedQuery = em.createQuery(select);
        List<Player> players = typedQuery.getResultList();
        int res = players.size();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/rest/players/{id}")
    public ResponseEntity<Player> get(@PathVariable Long id) {
        try {
            if(id < 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Player player = playerService.getPlayer(id);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rest/players")
    public ResponseEntity<?> create(@RequestBody Player player) {
        Date twoThousand = new GregorianCalendar(2000,Calendar.JANUARY,1).getTime();
        Date threeThousand = new GregorianCalendar(2999,Calendar.DECEMBER,31).getTime();
            if(player.getName().isEmpty() || player.getName().length() > 12 || player.getTitle().isEmpty() || player.getExperience() == null
                    || player.getTitle().length() > 30 || player.getRace().isEmpty() || player.getProfession().isEmpty() ||
                    player.getExperience() < 0 || player.getExperience() > 10000
                    || player.getBirthday().before(twoThousand)    || player.getBirthday().after(threeThousand)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        player.setLevel((int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
        playerService.create(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/rest/players/{id}")
    public ResponseEntity<?> update(@RequestBody Player player, @PathVariable Long id) {
        try {
            if(id < 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Player existPlayer = playerService.getPlayer(id);
            player.setId(id);

            return create(player);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/rest/players/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if(id < 0){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            playerService.deletePlayer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
