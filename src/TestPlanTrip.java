import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
		() -> travelAssistant.planTrip(null, "Halifax", false, 1, 1, 1), 
		"Start city cannot be null");
	
	// Start city is an empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("", "Halifax", false, 1, 1, 1), 
		"Start city cannot be an empty string");
	
	// Destination city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("Halifax", null, false, 1, 1, 1), 
		"Destination city cannot be null");
	
	// Destination city is an empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("Halifax", "", false, 1, 1, 1), 
		"Destination city cannot be an empty string");
	
	// Cost importance is a negative value
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("Halifax", "Toronto", false, -1, 1, 1), 
		"Cost importance cannot be a negative value");
	
	// Time importance is a negative value
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("Halifax", "Toronto", false, 1, -1, 1), 
		"Time importance cannot be a negative value");
	
	// Hop importance is a negative value
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.planTrip("Halifax", "Toronto", false, 1, 1, -1), 
		"Hop importance cannot be a negative value");
    }
    
    // Boundary tests
    @Test
    void boundaryCases() { 
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("H", false, 1, 100);
	travelAssistant.addCity("T", false, 1, 100);
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	travelAssistant.addFlight("H", "Toronto", 200, 100);
	travelAssistant.addFlight("Halifax", "T", 200, 100);
	travelAssistant.addFlight("Halifax", "Toronto", 1, 100);
	
	// Single character start city
	List<String> expectedPath1 = new ArrayList<>();
	expectedPath1.add("start H");
	expectedPath1.add("fly Toronto");
	
	var actualPath1 = travelAssistant.planTrip("H", "Toronto", false, 1, 1, 1);
	assertTrue(expectedPath1.equals(actualPath1));
	
	// Single character destination city
	List<String> expectedPath2 = new ArrayList<>();
	expectedPath2.add("start Halifax");
	expectedPath2.add("fly T");
		
	var actualPath2 = travelAssistant.planTrip("Halifax", "T", false, 1, 1, 1);
	assertTrue(expectedPath2.equals(actualPath2));
	
	// Cost importance is 0
	List<String> expectedPath3 = new ArrayList<>();
	expectedPath3.add("start Halifax");
	expectedPath3.add("fly Toronto");
			
	var actualPath3 = travelAssistant.planTrip("Halifax", "Toronto", false, 0, 1, 1);
	assertTrue(expectedPath3.equals(actualPath3));
	
	// Time importance is 0
	List<String> expectedPath4 = new ArrayList<>();
	expectedPath4.add("start Halifax");
	expectedPath4.add("fly Toronto");
				
	var actualPath4 = travelAssistant.planTrip("Halifax", "Toronto", false, 1, 0, 1);
	assertTrue(expectedPath4.equals(actualPath4));
	
	// Hop importance is 0
	List<String> expectedPath5 = new ArrayList<>();
	expectedPath5.add("start Halifax");
	expectedPath5.add("fly Toronto");
					
	var actualPath5 = travelAssistant.planTrip("Halifax", "Toronto", false, 1, 1, 0);
	assertTrue(expectedPath5.equals(actualPath5));
    }

    
}
