import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestAddFlight {

    // Input validation tests
    @Test
    void invalidInputs() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Flight time is negative
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Toronto", -2, 100), 
		"Flight time should be a positive integer");
	
	// Flight time is 0
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Toronto", 0, 100),
		"Flight time should be a positive integer");
	
	// Flight cost is negative
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Toronto", 2, -100),
		"Flight cost should be a positive integer");
	
	// Flight cost is 0
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Toronto", 2, 0),
		"Flight cost should be a positive integer");
	
	// Start city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight(null, "Toronto", 2, 100),
		"The city name cannot be null");
	
	// Start city is empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("", "Toronto", 2, 100),
		"The city name cannot be an empty string");
	
	// Destination city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", null, 2, 100),
		"The city name cannot be null");
	
	// Destination city is empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "", 2, 100),
		"The city name cannot be an empty string");
	
    }
    
    // Boundary tests
    @Test
    void boundaryCases() { 
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("H", false, 1, 100);
	travelAssistant.addCity("T", false, 1, 100);
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// One character source city
	boolean output1 = travelAssistant.addFlight("H", "Toronto", 200, 100);
	assertTrue(output1, "City name can be a single character");
	
	// One character destination city
	boolean output2 = travelAssistant.addFlight("Halifax", "T", 200, 100);
	assertTrue(output2, "City name can be a single character");
	
	// Flight time is 1 minute
	boolean output3 = travelAssistant.addFlight("Halifax", "Toronto", 1, 100);
	assertTrue(output3, "Flight time cam be 1 minute");
	
	// Flight cost is 1
	boolean output4 = travelAssistant.addFlight("Toronto", "Halifax", 200, 1);
	assertTrue(output4, "Flight cost can be 1");
    }

    // Control flow tests
    
    @Test
    void duplicateFlight() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Duplicate flight between two cities
	travelAssistant.addFlight("Halifax", "Toronto", 2, 100);
	
	boolean output = travelAssistant.addFlight("Halifax", "Toronto", 2, 100);
	assertFalse(output, "Duplicate flight cannot exist");
    }
    
    @Test
    void flightCases() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	travelAssistant.addCity("Vancouver", false, 1, 100);
	travelAssistant.addCity("Montreal", false, 1, 100);
	travelAssistant.addCity("Bangalore", false, 1, 100);
	travelAssistant.addCity("Pune", false, 1, 100);
	
	// Add flight when there are no flights already present
	boolean output = travelAssistant.addFlight("Halifax", "Toronto", 2, 100);
	assertTrue(output, "Flight can be added if there are no other flights present");
	
	// Add flight when one flight is already present
	boolean output1 = travelAssistant.addFlight("Halifax", "Vancouver", 2, 100);
	assertTrue(output1, "Flight can be added if one flight is present");
	
	// Add flight when many flights are already present
	boolean output2 = travelAssistant.addFlight("Halifax", "Montreal", 2, 100);
	assertTrue(output2, "Flight can be added if many flights are present");
	
	// Add flight from cities A to B when a flight B to A already exists
	boolean output3 = travelAssistant.addFlight("Montreal", "Halifax", 2, 100);
	assertTrue(output3, "Flights can be added in opposite direction");
	
	// Flight time is more than 1 minute
	boolean output4 = travelAssistant.addFlight("Bangalore", "Pune", 3, 100);
	assertTrue(output4, "Flight time can be more than 1 minute");
		
	// Flight cost is more than 1
	boolean output5 = travelAssistant.addFlight("Halifax", "Pune", 200, 200);
	assertTrue(output5, "Flight cost can be more than 1");
	
	// Start city and destination city are the same city
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Halifax", 2, 100), 
		"Start and destination city cannot be same");
    }
    
    // Data flow tests
    @Test
    void cityCases() { 
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Add flight when the start city does not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Pune", "Toronto", 2, 100),
		"Flight cannot be added when the start city does not exist");
	
	// Add flight when the destination city does not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Halifax", "Pune", 2, 100),
		"Flight cannot be added when the destination city does not exist");
	
	// Add flight when the start city and destination cities both do not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addFlight("Pune", "Mumbai", 2, 100),
		"Flight cannot be added when the start city and destination city both do not exist");
	
	// Add flight when the start and the destination city exist
	boolean output = travelAssistant.addFlight("Halifax", "Toronto", 200, 200);
	assertTrue(output, "Flight can be added when the start and destination city exist");
	
	// Add flight when a train exists in the same path
	travelAssistant.addTrain("Toronto", "Halifax", 200, 200);
	boolean output1 = travelAssistant.addFlight("Toronto", "Halifax", 200, 200);
	assertTrue(output1, "Flight can be added when a train exists in the same path");
    }
}
