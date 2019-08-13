package edu.ameier.hockey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HockeyApp {

	public static void main(String[] args) {
		SpringApplication.run(HockeyApp.class, args);
	}

//	@Bean
//	CommandLineRunner initDB(TeamRepository teamRepository) {
//		return (args) -> {
//			HockeyTeam hockeyTeam = new HockeyTeam();
//			teamRepository.save(hockeyTeam);
//		};
//	}
@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
}


}
