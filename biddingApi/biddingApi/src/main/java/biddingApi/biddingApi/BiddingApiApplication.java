package biddingApi.biddingApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "sharedEntity")
@EnableJpaRepositories(basePackages = "sharedDao")
public class BiddingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiddingApiApplication.class, args);
	}

}
