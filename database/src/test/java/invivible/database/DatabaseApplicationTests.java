package invivible.database;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import invisible.database.service.PointOfInterestService;

@SpringBootTest
class DatabaseApplicationTests {

	private PointOfInterestService pointOfInterestService;

	DatabaseApplicationTests(PointOfInterestService pointOfInterestService) {
		this.pointOfInterestService = pointOfInterestService;
	}

	@Test
	void contextLoads() {
	}
}
