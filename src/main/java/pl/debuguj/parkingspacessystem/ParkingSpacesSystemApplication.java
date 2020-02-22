package pl.debuguj.parkingspacessystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class ParkingSpacesSystemApplication implements CommandLineRunner{


	private static final Logger logger = LoggerFactory.getLogger(ParkingSpacesSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ParkingSpacesSystemApplication.class, args);
	}


//	@Autowired
//	private Constants constants;

	@Override
	public void run(String... strings) throws Exception {
//		logger.info("CONSTANT "+constants.getDayFormat());
//		logger.info("CONSTANT "+constants.getTimeFormat());
	}
}
