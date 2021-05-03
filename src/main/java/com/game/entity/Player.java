package com.game.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//TODO logging
public class Player {
    private Long id;
    private String name, title;
    private Race race;
    private Profession profession;
    private Integer experience, level, untilNextLevel;
    private Date birthday;
    private Boolean banned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.length() > 12) throw new Exception("too long name");
        else {
            this.name = name;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        if (title.length() > 30) throw new Exception("too long title");
        else {
            this.title = title;
        }
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
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
