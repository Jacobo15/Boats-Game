package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;


@SpringBootApplication
public class SalvoApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of customers
			Player p1 = new Player("j.bauer",passwordEncoder().encode( "24"),"Jack","Bauer","j.bauer@ctu.gov");
			playerRepository.save(p1);
			Player p2 = new Player("c.obrian",passwordEncoder().encode( "42"),"Chloe","O'Brian","c.obrian@ctu.gov");
			playerRepository.save(p2);
			Player p3 = new Player("kim_bauer",passwordEncoder().encode( "kb"),"Kim","Bauer","kim_bauer@gmail.com");
			playerRepository.save(p3);
			Player p4 = new Player("t.almeida",passwordEncoder().encode( "mole"),"Tony","Almeida","t.almeida@ctu.gov");
			playerRepository.save(p4);


			Game game1= new Game(new Date());
			gameRepository.save(game1);
			Game game2= new Game(Date.from(new Date().toInstant().plusSeconds(3600)));
			gameRepository.save(game2);
			Game game3= new Game(Date.from(new Date().toInstant().plusSeconds(7200)));
			gameRepository.save(game3);
			Game game4= new Game(Date.from(new Date().toInstant().plusSeconds(10800)));
			gameRepository.save(game4);
			Game game5= new Game(Date.from(new Date().toInstant().plusSeconds(14400)));
			gameRepository.save(game5);
			Game game6= new Game(Date.from(new Date().toInstant().plusSeconds(18000)));
			gameRepository.save(game6);
			Game game7= new Game(Date.from(new Date().toInstant().plusSeconds(21600)));
			gameRepository.save(game7);
			Game game8= new Game(Date.from(new Date().toInstant().plusSeconds(25200)));
			gameRepository.save(game8);


			GamePlayer gp1 = new GamePlayer(game1, p1);
			gamePlayerRepository.save (gp1);
			GamePlayer gp2 = new GamePlayer(game1, p2);
			gamePlayerRepository.save (gp2);
			GamePlayer gp3 = new GamePlayer(game2, p1);
			gamePlayerRepository.save (gp3);
			GamePlayer gp4 = new GamePlayer(game2, p2);
			gamePlayerRepository.save (gp4);
			GamePlayer gp5 = new GamePlayer(game3, p2);
			gamePlayerRepository.save (gp5);
			GamePlayer gp6 = new GamePlayer(game3, p4);
			gamePlayerRepository.save (gp6);
			GamePlayer gp7 = new GamePlayer(game4, p2);
			gamePlayerRepository.save (gp7);
			GamePlayer gp8 = new GamePlayer(game4, p1);
			gamePlayerRepository.save (gp8);
			GamePlayer gp9 = new GamePlayer(game5, p4);
			gamePlayerRepository.save (gp9);
			GamePlayer gp10 = new GamePlayer(game5, p1);
			gamePlayerRepository.save (gp10);
			GamePlayer gp11 = new GamePlayer(game6, p3);
			gamePlayerRepository.save (gp11);
			GamePlayer gp12 = new GamePlayer(game7, p4);
			gamePlayerRepository.save (gp12);
			GamePlayer gp13 = new GamePlayer(game8, p3);
			gamePlayerRepository.save (gp13);
			GamePlayer gp14 = new GamePlayer(game8, p4);
			gamePlayerRepository.save (gp14);


			Ship ship1 = new Ship("destroyer", Arrays.asList("H2","H3","H4"), gp1, 0);
			shipRepository.save (ship1);
			Ship ship2 = new Ship("cruiser", Arrays.asList("E1","F1","G1"), gp1, 0);
			shipRepository.save (ship2);
			Ship ship3 = new Ship("battleship", Arrays.asList("B4","B5"), gp1, 0);
			shipRepository.save (ship3);
			Ship ship4 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp2, 0);
			shipRepository.save (ship4);
			Ship ship5 = new Ship("battleship", Arrays.asList("F1","F2"), gp2, 0);
			shipRepository.save (ship5);
			Ship ship6 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp3, 0);
			shipRepository.save (ship6);
			Ship ship7 = new Ship("battleship", Arrays.asList("C6","C7"), gp3, 0);
			shipRepository.save (ship7);
			Ship ship8 = new Ship("cruiser", Arrays.asList("A2","A3","A4"), gp4, 0);
			shipRepository.save (ship8);
			Ship ship9 = new Ship("battleship", Arrays.asList("G6","H6"), gp4, 0);
			shipRepository.save (ship9);
			Ship ship10 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp5, 0);
			shipRepository.save (ship10);
			Ship ship11 = new Ship("battleship", Arrays.asList("C6","C7"), gp5, 0);
			shipRepository.save (ship11);
			Ship ship12 = new Ship("cruiser", Arrays.asList("A2","A3","A4"), gp6, 0);
			shipRepository.save (ship12);
			Ship ship13 = new Ship("battleship", Arrays.asList("G6","H6"), gp6, 0);
			shipRepository.save (ship13);
			Ship ship14 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp7, 0);
			shipRepository.save (ship14);
			Ship ship15 = new Ship("battleship", Arrays.asList("C6","C7"), gp7, 0);
			shipRepository.save (ship15);
			Ship ship16 = new Ship("cruiser", Arrays.asList("A2","A3","A4"), gp8, 0);
			shipRepository.save (ship16);
			Ship ship17 = new Ship("battleship", Arrays.asList("G6","H6"), gp8, 0);
			shipRepository.save (ship17);
			Ship ship18 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp9, 0);
			shipRepository.save (ship18);
			Ship ship19 = new Ship("battleship", Arrays.asList("C6","C7"), gp9, 0);
			shipRepository.save (ship19);
			Ship ship20 = new Ship("cruiser", Arrays.asList("A2","A3","A4"), gp10, 0);
			shipRepository.save (ship20);
			Ship ship21 = new Ship("battleship", Arrays.asList("G6","H6"), gp10, 0);
			shipRepository.save (ship21);
			Ship ship22 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp11, 0);
			shipRepository.save (ship22);
			Ship ship23 = new Ship("battleship", Arrays.asList("C6","C7"), gp11, 0);
			shipRepository.save (ship23);
			Ship ship24 = new Ship("destroyer", Arrays.asList("B5","C5","D5"), gp13, 0);
			shipRepository.save (ship24);
			Ship ship25 = new Ship("battleship", Arrays.asList("C6","C7"), gp13, 0);
			shipRepository.save (ship25);
			Ship ship26 = new Ship("cruiser", Arrays.asList("A2","A3","A4"), gp14, 0);
			shipRepository.save (ship26);
			Ship ship27 = new Ship("battleship", Arrays.asList("G6","H6"), gp14, 0);
			shipRepository.save (ship27);


			Salvo salvo1 = new Salvo( 1, Arrays.asList("B5","C5","F1"),gp1);
			salvoRepository.save(salvo1);
			Salvo salvo2 = new Salvo( 1, Arrays.asList("B4","B5","B6"),gp2);
			salvoRepository.save(salvo2);
			Salvo salvo3 = new Salvo( 2, Arrays.asList("F2","D5"),gp1);
			salvoRepository.save(salvo3);
			Salvo salvo4 = new Salvo( 2, Arrays.asList("E1","H3","A2"),gp2);
			salvoRepository.save(salvo4);
			Salvo salvo5 = new Salvo( 1, Arrays.asList("A2","A4","G6"),gp3);
			salvoRepository.save(salvo5);
			Salvo salvo6 = new Salvo( 1, Arrays.asList("B5","D5","C7"),gp4);
			salvoRepository.save(salvo6);
			Salvo salvo7 = new Salvo( 2, Arrays.asList("A3","H6"),gp3);
			salvoRepository.save(salvo7);
			Salvo salvo8 = new Salvo( 2, Arrays.asList("C5","C6"),gp4);
			salvoRepository.save(salvo8);
			Salvo salvo9 = new Salvo( 1, Arrays.asList("G6","H6","A4"),gp5);
			salvoRepository.save(salvo9);
			Salvo salvo10 = new Salvo( 1, Arrays.asList("H1","H2","H3"),gp6);
			salvoRepository.save(salvo10);
			Salvo salvo11 = new Salvo( 2, Arrays.asList("A2","A3","D8"),gp5);
			salvoRepository.save(salvo11);
			Salvo salvo12 = new Salvo( 2, Arrays.asList("E1","F2","G3"),gp6);
			salvoRepository.save(salvo12);
			Salvo salvo13 = new Salvo( 1, Arrays.asList("A3","A4","F7"),gp7);
			salvoRepository.save(salvo13);
			Salvo salvo14 = new Salvo( 1, Arrays.asList("B5","C6","H1"),gp8);
			salvoRepository.save(salvo14);
			Salvo salvo15 = new Salvo( 2, Arrays.asList("A2","G6","H6"),gp7);
			salvoRepository.save(salvo15);
			Salvo salvo16 = new Salvo( 2, Arrays.asList("C5","C7","D5"),gp8);
			salvoRepository.save(salvo16);
			Salvo salvo17 = new Salvo( 1, Arrays.asList("A1","A2","A3"),gp9);
			salvoRepository.save(salvo17);
			Salvo salvo18 = new Salvo( 1, Arrays.asList("B5","B6","C7"),gp10);
			salvoRepository.save(salvo18);
			Salvo salvo19 = new Salvo( 2, Arrays.asList("G6","G7","G8"),gp9);
			salvoRepository.save(salvo19);
			Salvo salvo20 = new Salvo( 2, Arrays.asList("C6","D6","E6"),gp10);
			salvoRepository.save(salvo20);
			Salvo salvo21 = new Salvo( 2, Arrays.asList("H1","H8"),gp10);
			salvoRepository.save(salvo21);


			Score score1 = new Score(1.0, new Date(800000),game1,p1);
			scoreRepository.save(score1);
			Score score2 = new Score(0.0,new Date(800000),game1,p2);
			scoreRepository.save(score2);
			Score score3 = new Score(0.5,new Date(800000),game2,p1);
			scoreRepository.save(score3);
			Score score4 = new Score(0.5,new Date(800000),game2,p2);
			scoreRepository.save(score4);
			Score score5 = new Score(1.0,new Date(800000),game3,p2);
			scoreRepository.save(score5);
			Score score6 = new Score(0.0,new Date(800000),game3,p4);
			scoreRepository.save(score6);
			Score score7 = new Score(0.5,new Date(800000),game4,p2);
			scoreRepository.save(score7);
			Score score8 = new Score(0.5,new Date(800000),game4,p1);
			scoreRepository.save(score8);

		};
	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByEmail(inputName);
			if (player != null) {
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}







@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/admin/**").hasAuthority("ADMIN")

				.antMatchers("/web/games.html", "/web/games.js", "/api/games","/api/players").permitAll()

				.antMatchers("/api/game_view/**").hasAuthority("USER");

		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("password")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


	}


	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

	}

}

