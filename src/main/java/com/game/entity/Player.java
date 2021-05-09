package com.game.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name",  length = 12)
    private String name;
    @Column(name = "title", length = 30)
    private String title;
    @Column(name = "race")
    private String race;
    @Column(name = "profession")
    private String profession;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "level")
    private Integer level;
    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "banned")
    private Boolean banned;

    public Player(String name, String title, Race race, Profession profession, Integer experience, Date birthday, Boolean banned) {
        this.id = 0L;
        this.name = name;
        this.title = title;
        this.race = race.toString();
        this.profession = profession.toString();
        this.experience = experience;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
            this.title = title;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) throws Exception {
        if (experience < 0 || experience > 10000) throw new Exception("impossible exp");
        else {
            this.experience = experience;
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) throws Exception {
        Date twoThousand = new GregorianCalendar(2000,Calendar.JANUARY,1).getTime();
        Date threeThousand = new GregorianCalendar(2999,Calendar.DECEMBER,31).getTime();
        if (birthday.before(twoThousand) || birthday.after(threeThousand)) throw new Exception("wrong date of reg - birthday");
        else{
            this.birthday = birthday;
        }
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
