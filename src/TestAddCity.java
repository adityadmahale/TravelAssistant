import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAddCity {
    
    // Input validation tests
    @Test
    void invalidInputs() {
	var travelAssistant = new TravelAssistant();
	
	// Negative nightly hotel cost
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addCity("Halifax", false, 1, -100),
		"Hotel cost cannot be a negative value");
	
	// Nightly hotel cost is 0
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addCity("Halifax", false, 1, 0),
		"Hotel cost cannot be 0");
	
	// Empty string for the city name
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addCity("", false, 1, 100),
		"City name cannot be an empty string");
	
	
	// Null for the city name
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addCity(null, false, 1, 100),
		"City name cannot be null");
    }
    
    // Boundary tests
    @Test
    void boundaryCases() {
	var travelAssistant = new TravelAssistant();
	
	// One character city name
	boolean output1 = travelAssistant.addCity("H", false, 1, 100);
	assertTrue(output1, "One character city name is allowed");
	
	// Nightly hotel cost is 1
	boolean output2 = travelAssistant.addCity("Halifax", false, 1, 100);
	assertTrue(output2, "Nightly hotel cost can be 1");
	
	// Time to test is 0
	boolean output3 = travelAssistant.addCity("Mumbai", false, 0, 100);
	assertTrue(output3, "Time to test can be 0");
    }
    
    
    // Control flow tests
    @Test
    void duplicateCityName() {
	
	// Duplicate city name
	
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 2, 100);
	
	boolean output2 = travelAssistant.addCity("Halifax", false, 1, 100);
	assertFalse(output2, "Duplicate city is not allowed");
    }
    
    @Test
    void noCityCreated() {
	
	// Create a city when there are no cities already created
	
	var travelAssistant = new TravelAssistant();
	boolean output1 = travelAssistant.addCity("Halifax", false, 1, 100);
	assertTrue(output1, "City can be created when no city already exists");
    }
    
    @Test
    void oneCityCreated() {
	
	// Create a city when there is one city already created
	
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	
	boolean output2 = travelAssistant.addCity("Toronto", false, 1, 100);
	assertTrue(output2, "City can be created when one city already exists");
    }
    
    @Test
    void manyCitiesCreated() {
	
	// Create a city when there are many cities already created
	
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	travelAssistant.addCity("Pune", false, 1, 100);
	
	boolean output = travelAssistant.addCity("Vancouver", false, 1, 100);
	assertTrue(output, "City can be created when many cities already exist");
    }
    
    @Test
    void hotelStay() {
	
	// Time to test is more than 0 days
	var travelAssistant = new TravelAssistant();
	boolean output1 = travelAssistant.addCity("Halifax", false, 2, 100);
	assertTrue(output1, "Time to test can be more than 0 days");
	
	// Nightly hotel cost is more than 1
	boolean output2 = travelAssistant.addCity("Pune", false, 1, 200);
	assertTrue(output2, "Nightly hotel cost can be more than 1");
	
	// timeToTest < 0
	boolean output3 = travelAssistant.addCity("Bangalore", false, -2, 200);
	assertTrue(output3, "Time to test can be negative");
	
    }
    
    @Test
    void testRequired() {
	
	// Test required is false
	var travelAssistant = new TravelAssistant();
	boolean output1 = travelAssistant.addCity("Halifax", false, 2, 100);
	assertTrue(output1, "Test required can be false");
	
	// Test required is true
	boolean output2 = travelAssistant.addCity("Pune", true, 1, 200);
	assertTrue(output2, "Test required can be true");
	
    }
    
}
