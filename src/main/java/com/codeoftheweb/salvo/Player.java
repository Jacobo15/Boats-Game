package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;





    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gameplayers = new HashSet<>();

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> score = new HashSet<>();



    public Player() { }

    public Player(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setPlayer(this);
        gameplayers.add(gameplayer);
    }

    public Score getScore(Game game){
        return this.score.stream().filter(score1 -> score1.getGame().equals(game)).findFirst().orElse(null);

    }
    public void setSingleScore(Score score){
        this.score.add(score);
    }

    public void setScore(Set<Score> score) {
        this.score = score;
    }
}
