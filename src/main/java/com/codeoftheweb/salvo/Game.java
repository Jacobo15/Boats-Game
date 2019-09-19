package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date dateGame;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gameplayers = new HashSet<>();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Score> score = new HashSet<>();

    public Game() {}

    public Game(Date date) {
       dateGame = date;
    }

    public long getId() {
        return id;
    }

    public Date getDateGame() {
        return dateGame;
    }

    public Set<Score> getScore() {
        return score;
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDateGame(Date dateGame) {
        this.dateGame = dateGame;
    }

    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    public void setGameplayers(Set<GamePlayer> gameplayers) {
        this.gameplayers = gameplayers;
    }

    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setGame(this);
        gameplayers.add(gameplayer);
    }

}
