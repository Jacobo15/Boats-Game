package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private SalvoRepository salvoRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @RequestMapping("/games")
    public Map<String,Object> getAllGames(Authentication authentication) {
        Map<String,Object> data = new HashMap<>();
        data.put(
                "games",
                gameRepository
                        .findAll()
                        .stream()
                        .map(game -> gameDto(game))
                        .collect(Collectors.toList()));

       if(authentication == null){
           data.put(
                   "player",
                  null
           );
       }else {
           data.put(
                   "player",
                   playerDto(playerRepository.findByEmail(authentication.getName()))
           );
       }
        return data;
    }

    private Map<String,Object> gameDto(Game game) {
        Map<String,Object> gamedata = new HashMap<>();
        gamedata.put("id", game.getId());
        gamedata.put("dataGame", game.getDateGame());
        gamedata.put("gamePlayers", game.getGameplayers()
                .stream()
                .map(gameplayer -> gamePlayerDto(gameplayer))
                .collect(Collectors.toList()));
        return gamedata;
    }

    private Map<String,Object> gamePlayerDto(GamePlayer gameplayer) {
        Map<String,Object> gameplayerdata = new HashMap<>();
        gameplayerdata.put("id", gameplayer.getId());
        gameplayerdata.put("player",playerDto(gameplayer.getPlayer()));
        if(  gameplayer.getPlayer().getScore(gameplayer.getGame()) != null) {
            gameplayerdata.put("score", gameplayer.getPlayer().getScore(gameplayer.getGame()).getScore());
        }
        return gameplayerdata;

    }
    private Map<String,Object> playerDto(Player player) {
        Map<String, Object> playerdata = new HashMap<>();

        playerdata.put("name", player.getUserName());
        playerdata.put("id", player.getId());

        return playerdata;
    }



    @RequestMapping("/game_view/{nn}")
    public  ResponseEntity<Map<String, Object>> getVariable(@PathVariable long nn, Authentication authentication) {
        GamePlayer current = gamePlayerRepository.findById(nn);
        Map<String, Object> gameInfo = new HashMap<>();
        if(authentication.getName() == current.getPlayer().getEmail()) {
            gameInfo.put("status", checkStatus(current));
            gameInfo.put("id", current.getGame().getId());
            gameInfo.put("dataGame", current.getGame().getDateGame());
            gameInfo.put("gamePlayers", current.getGame().getGameplayers()
                    .stream()
                    .map(gameplayer -> gamePlayerDto(gameplayer))
                    .collect(Collectors.toList()));
            gameInfo.put("ships", current.getShips());
            gameInfo.put("salvoes", current.getGame().getGameplayers()
                    .stream()
                    .map(gameplayersalvo -> salvoesDto(gameplayersalvo))
                    .collect(Collectors.toList()));
            if(current.getGame().getGameplayers().size() == 2){
                Game game = current.getGame();

                Set<GamePlayer> gamePlayer1 = game.getGameplayers();
                GamePlayer opp;
                List<String> locations = new ArrayList<>();
                List<String> locationsCruiser = new ArrayList<>();
                List<String> locationsBattleship = new ArrayList<>();
                List<String> locationsDestroyer = new ArrayList<>();
                List<String> locationsCruiserHits = new ArrayList<>();
                List<String> locationsBattleshipHits = new ArrayList<>();
                List<String> locationsDestroyerHits = new ArrayList<>();

                for(GamePlayer gameplayer:gamePlayer1){
                    if(gameplayer.getId() != current.getId()){

                        opp = gameplayer;

                        Set<Ship> shipOpp = opp.getShips();

                        for(Ship ship:shipOpp){

                        for(String s: ship.getLocations()){
                            locations.add(s);
                            if(ship.getShipName().equals("cruiser")){
                                locationsCruiser.add(s);
                            }
                            if(ship.getShipName().equals("battleship")){
                                locationsBattleship.add(s);
                            }
                            if(ship.getShipName().equals("destroyer")){
                                locationsDestroyer.add(s);
                            }
                        }
                        }

                    }


                }
               List<String> locationsHits = new ArrayList<>();
                List<String> salvosMe = new ArrayList<>();
                for(GamePlayer gameplayer:gamePlayer1){
                    if(gameplayer.getId() == current.getId()){

                        Set<Salvo> salvoMe = gameplayer.getSalvo();

                        for(Salvo salvo:salvoMe){

                            for(String sal:salvo.getLocations()){
                                salvosMe.add(sal);
                                if( locations.contains(sal)) {
                                    locationsHits.add(sal);
                                }
                                if(locationsCruiser.contains(sal)){
                                    locationsCruiserHits.add(sal);
                                }
                                if(locationsBattleship.contains(sal)){
                                    locationsBattleshipHits.add(sal);
                                }
                                if(locationsDestroyer.contains(sal)){
                                    locationsDestroyerHits.add(sal);
                                }
                            }

                        }

                    }
                }
                List<String> meLocations = new ArrayList<>();
                List<String> meLocationsCruiser = new ArrayList<>();
                List<String> meLocationsBattleship = new ArrayList<>();
                List<String> meLocationsDestroyer = new ArrayList<>();
                List<String> meLocationsCruiserHits = new ArrayList<>();
                List<String> meLocationsBattleshipHits = new ArrayList<>();
                List<String> meLocationsDestroyerHits = new ArrayList<>();


                for(GamePlayer gameplayer:gamePlayer1){
                    if(gameplayer.getId() == current.getId()){



                        Set<Ship> shipMe = gameplayer.getShips();

                        for(Ship ship:shipMe){

                            for(String s: ship.getLocations()){
                                meLocations.add(s);
                                if(ship.getShipName().equals("cruiser")){
                                    meLocationsCruiser.add(s);
                                }
                                if(ship.getShipName().equals("battleship")){
                                    meLocationsBattleship.add(s);
                                }
                                if(ship.getShipName().equals("destroyer")){
                                    meLocationsDestroyer.add(s);
                                }
                            }
                        }

                    }


                }
                List<String> meLocationsHits = new ArrayList<>();
                List<String> salvosOpp = new ArrayList<>();
                for(GamePlayer gameplayer:gamePlayer1){
                    if(gameplayer.getId() != current.getId()){

                        Set<Salvo> salvoOpp = gameplayer.getSalvo();

                        for(Salvo salvo:salvoOpp){

                            for(String sal:salvo.getLocations()){
                                salvosOpp.add(sal);
                                if( meLocations.contains(sal)) {
                                    meLocationsHits.add(sal);
                                }
                                if(meLocationsCruiser.contains(sal)){
                                    meLocationsCruiserHits.add(sal);
                                }
                                if(meLocationsBattleship.contains(sal)){
                                    meLocationsBattleshipHits.add(sal);
                                }
                                if(meLocationsDestroyer.contains(sal)){
                                    meLocationsDestroyerHits.add(sal);
                                }
                            }

                        }

                    }
                }

                int cruLife = (3 - locationsCruiserHits.size());
                int batLife = (3 - locationsBattleshipHits.size());
                int desLife = (2 - locationsDestroyerHits.size());
                int meTurn = (salvosMe.size()/3);
                int meCruLife = (3 - meLocationsCruiserHits.size());
                int meBatLife = (3 - meLocationsBattleshipHits.size());
                int meDesLife = (2 - meLocationsDestroyerHits.size());
                int rivTurn = (salvosOpp.size()/3);
                String finishGame = "";

                if(rivTurn == meTurn) {
                    if (locationsHits.size() == 8 && meLocationsHits.size() < 8) {
                        finishGame = "VICTORY";

                        Score meScoreWin = new Score(1.0,new Date(),current.getGame(),current.getPlayer());
                        current.getPlayer().setSingleScore(meScoreWin);
                        scoreRepository.save(meScoreWin);

                    }
                    if (locationsHits.size() < 8 && meLocationsHits.size() == 8) {
                        finishGame = "LOSER";


                        Score meScoreLose = new Score(0.0,new Date(),current.getGame(),current.getPlayer());
                        current.getPlayer().setSingleScore(meScoreLose);
                        scoreRepository.save(meScoreLose);

                    }
                    if (locationsHits.size() == 8 && meLocationsHits.size() == 8) {
                        finishGame = "DRAW";

                        Score meScoreDraw = new Score(0.5,new Date(),current.getGame(),current.getPlayer());
                        current.getPlayer().setSingleScore(meScoreDraw);
                        scoreRepository.save(meScoreDraw);

                    }
                }

                gameInfo.put("hits", locationsHits );
                gameInfo.put("cruiserLife", cruLife);
                gameInfo.put("battleshipLife", batLife);
                gameInfo.put("destroyerLife", desLife);
                gameInfo.put("meTurn", meTurn);
                gameInfo.put("meCruiserLife", meCruLife);
                gameInfo.put("meBattleshipLife", meBatLife);
                gameInfo.put("meDestroyerLife", meDesLife);
                gameInfo.put("rivTurn", rivTurn);
                gameInfo.put("finish", finishGame);


            }

            return new ResponseEntity<>(gameInfo, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(makeMap("error", "No cheatting"), HttpStatus.UNAUTHORIZED);

        }
    }


        private Map<String,Object> salvoesDto(GamePlayer gameplayersalvoes) {
            Map<String, Object> salvoesdata = new HashMap<>();
            salvoesdata.put("gamePlayerId",gameplayersalvoes.getId());
            salvoesdata.put("info", gameplayersalvoes.getSalvo()
            .stream()
            .map(salvo -> salvoDto(salvo))
            .collect(Collectors.toList()));
            return salvoesdata;
        }
        private Map<String,Object> salvoDto(Salvo salvo){
            Map<String, Object> salvodata = new HashMap<>();
            salvodata.put("turn",salvo.getTurn());
            salvodata.put("locations", salvo.getLocations());

       return salvodata;
        }


        @RequestMapping(path = "/players", method = RequestMethod.POST)
        public ResponseEntity<Map<String, Object>> createUser(@RequestParam String userName, @RequestParam String password,@RequestParam String firstName,@RequestParam String lastName, @RequestParam String email) {
            if (email.isEmpty() || password.isEmpty() || userName.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                return new ResponseEntity<>(makeMap("error", "No name"), HttpStatus.FORBIDDEN);
            }
            Player user = playerRepository.findByEmail(email);
            if (user != null) {

                return new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
            }
            Player newUser = playerRepository.save(new Player(userName ,passwordEncoder.encode(password), firstName, lastName, email));
            return new ResponseEntity<>(makeMap("user" , newUser.getEmail() ), HttpStatus.CREATED);
        }

        private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
        }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "you don't see game"), HttpStatus.UNAUTHORIZED);
        } {
            Player newPlayer = playerRepository.findByEmail(authentication.getName()) ;
            Game newGame = new Game(new Date());
            GamePlayer newGamePlayer = new GamePlayer(newGame,newPlayer);
            gameRepository.save (newGame);
            gamePlayerRepository.save (newGamePlayer);
            return new ResponseEntity<>(makeMap("gpId", newGamePlayer.getId()), HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "/game/{nn}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(Authentication authentication, @PathVariable long nn) {

        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "No"), HttpStatus.UNAUTHORIZED);
        }
            Game game = gameRepository.findById(nn);
       if (game == null) {
           return new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
       }

       if (game.getGameplayers().size() == 2){
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }


            Player player = playerRepository.findByEmail(authentication.getName());
            GamePlayer gamePlayer = new GamePlayer(game,player);
            gameRepository.save (game);
            gamePlayerRepository.save (gamePlayer);
            return new ResponseEntity<>(makeMap("gpId", gamePlayer.getId()), HttpStatus.CREATED);

    }

    @RequestMapping(path = "/games/players/{gpId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> sendShips(@PathVariable long gpId, Authentication authentication, @RequestBody Set<Ship> ships) {

        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId);
        if (authentication == null || !(gamePlayerRepository.existsById(gpId)) || !gamePlayer.getPlayer().getEmail().equals( authentication.getName())) {
            return new ResponseEntity<>(makeMap("error", "No authorized"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getShips().size() != 0){
            return new ResponseEntity<>(makeMap("error", "All ships are placed"), HttpStatus.FORBIDDEN);
        }
        if (ships.size() != 3){
            return new ResponseEntity<>(makeMap("error", "wrong number of ships"), HttpStatus.FORBIDDEN);
        }


        for(Ship ship:ships){
        gamePlayer.addShips(ship);

        shipRepository.save(ship);
        }
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(makeMap("ships", "All ships are created"), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/games/players/{gpId}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> sendSalvos(@PathVariable long gpId, Authentication authentication, @RequestBody Salvo salvos) {

        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId);
        if (authentication == null || !(gamePlayerRepository.existsById(gpId)) || !gamePlayer.getPlayer().getEmail().equals( authentication.getName())) {
            return new ResponseEntity<>(makeMap("error", "No authorized"), HttpStatus.UNAUTHORIZED);
        }

        if (salvos.getLocations().size() != 3){
            return new ResponseEntity<>(makeMap("error", "wrong number of salvos"), HttpStatus.FORBIDDEN);
        }


        salvos.setTurn(gamePlayer.getSalvo().size() + 1);
            gamePlayer.addSalvos(salvos);

            salvoRepository.save(salvos);

        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(makeMap("salvo", "All salvos are created"), HttpStatus.CREATED);
    }

    private String checkStatus(GamePlayer gameplayer){
        if(gameplayer.getGame().getGameplayers().size() != 2){
            return  gameplayer.getPlayer().getUserName() + " " + "VS" + " " + "waiting for opponent";
        }
        Set<GamePlayer> gamePlayer1 = gameplayer.getGame().getGameplayers();
        GamePlayer opp;
        for(GamePlayer player:gamePlayer1){
            if(player.getId() != gameplayer.getId()) {
                opp = player;



          if(gameplayer.getGame().getGameplayers().size() == 2 && gameplayer.getShips().size() == 0){

              return  gameplayer.getPlayer().getUserName() + " " + "VS" + " " + opp.getPlayer().getUserName()+ " " + "-- Place Ships --";

           }
          if(gameplayer.getGame().getGameplayers().size() == 2 && gameplayer.getShips().size() == 3 && opp.getShips().size() == 0 ){
              return  gameplayer.getPlayer().getUserName() + " " + "VS" + " " + opp.getPlayer().getUserName()+ " " + "-- Waiting Opponent Ships--";
          }
                if(gameplayer.getGame().getGameplayers().size() == 2 && gameplayer.getShips().size() == 3 && opp.getShips().size() == 3 && gameplayer.getSalvo().size() > opp.getSalvo().size()){
                    return  gameplayer.getPlayer().getUserName() + " " + "VS" + " " + opp.getPlayer().getUserName()+ " " + "-- Waiting Opponent Salvos--";
                }
                if(gameplayer.getGame().getGameplayers().size() == 2 && gameplayer.getShips().size() == 3 && opp.getShips().size() == 3 && gameplayer.getSalvo().size() <= opp.getSalvo().size()){
                    return  gameplayer.getPlayer().getUserName() + " " + "VS" + " " + opp.getPlayer().getUserName() + " " +"--fire salvos--"  ;
                }

    }}
        return  "C'mon guys";
    }
        }










