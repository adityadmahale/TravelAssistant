import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestPlanTrip {
    
    // Input validation tests
    @Test
    void inputTests() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	travelAssistant.addFlight("Halifax", "Toronto", 200, 100);
	
	// Start city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip(null, null, false, 0, 0, 0), 
		"Flight time should be a positive integer");
    }

}
