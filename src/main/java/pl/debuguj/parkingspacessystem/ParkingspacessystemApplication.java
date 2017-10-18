package pl.debuguj.parkingspacessystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.debuguj.parkingspacessystem.config.Constants;
import pl.debuguj.parkingspacessystem.controllers.ParkingSpaceController;

@SpringBootApplication
public class ParkingspacessystemApplication implements CommandLineRunner{


	private static final Logger logger = LoggerFactory.getLogger(ParkingspacessystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ParkingspacessystemApplication.class, args);
	}


	@Autowired
	private Constants constants;

	@Override
	public void run(String... strings) throws Exception {
		logger.info("CONSTANT "+constants.getDayFormat());
		logger.info("CONSTANT "+constants.getTimeFormat());
	}
}
