import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestAddTrain {

    // Input validation tests
    @Test
    void invalidInputs() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Train time is negative
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Toronto", -2, 100), 
		"Train time should be a positive integer");
	
	// Train time is 0
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Toronto", 0, 100),
		"Train time should be a positive integer");
	
	// Train cost is negative
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Toronto", 2, -100),
		"Train cost should be a positive integer");
	
	// Train cost is 0
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Toronto", 2, 0),
		"Train cost should be a positive integer");
	
	// Start city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain(null, "Toronto", 2, 100),
		"The city name cannot be null");
	
	// Start city is empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("", "Toronto", 2, 100),
		"The city name cannot be an empty string");
	
	// Destination city is null
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", null, 2, 100),
		"The city name cannot be null");
	
	// Destination city is empty string
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "", 2, 100),
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
	boolean output1 = travelAssistant.addTrain("H", "Toronto", 200, 100);
	assertTrue(output1, "City name can be a single character");
	
	// One character destination city
	boolean output2 = travelAssistant.addTrain("Halifax", "T", 200, 100);
	assertTrue(output2, "City name can be a single character");
	
	// Train time is 1 minute
	boolean output3 = travelAssistant.addTrain("Halifax", "Toronto", 1, 100);
	assertTrue(output3, "Train time cam be 1 minute");
	
	// Train cost is 1
	boolean output4 = travelAssistant.addTrain("Toronto", "Halifax", 200, 1);
	assertTrue(output4, "Train cost can be 1");
    }

    // Control flow tests
    
    @Test
    void duplicateTrain() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Duplicate Train between two cities
	travelAssistant.addTrain("Halifax", "Toronto", 2, 100);
	
	boolean output = travelAssistant.addTrain("Halifax", "Toronto", 2, 100);
	assertFalse(output, "Duplicate Train cannot exist");
    }
    
    @Test
    void trainCases() {
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	travelAssistant.addCity("Vancouver", false, 1, 100);
	travelAssistant.addCity("Montreal", false, 1, 100);
	travelAssistant.addCity("Bangalore", false, 1, 100);
	travelAssistant.addCity("Pune", false, 1, 100);
	
	// Add train when there are no trains already present
	boolean output = travelAssistant.addTrain("Halifax", "Toronto", 2, 100);
	assertTrue(output, "Train can be added if there are no other trains present");
	
	// Add train when one train is already present
	boolean output1 = travelAssistant.addTrain("Halifax", "Vancouver", 2, 100);
	assertTrue(output1, "Train can be added if one train is present");
	
	// Add train when many trains are already present
	boolean output2 = travelAssistant.addTrain("Halifax", "Montreal", 2, 100);
	assertTrue(output2, "Train can be added if many trains are present");
	
	// Add train from cities A to B when a train B to A already exists
	boolean output3 = travelAssistant.addTrain("Montreal", "Halifax", 2, 100);
	assertTrue(output3, "Trains can be added in opposite direction");
	
	// Train time is more than 1 minute
	boolean output4 = travelAssistant.addTrain("Bangalore", "Pune", 3, 100);
	assertTrue(output4, "Train time can be more than 1 minute");
		
	// Train cost is more than 1
	boolean output5 = travelAssistant.addTrain("Halifax", "Pune", 200, 200);
	assertTrue(output5, "Train cost can be more than 1");
	
	// Start city and destination city are the same city
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Halifax", 2, 100), 
		"Start and destination city cannot be same");
    }
    
    // Data flow tests
    @Test
    void cityCases() { 
	var travelAssistant = new TravelAssistant();
	travelAssistant.addCity("Halifax", false, 1, 100);
	travelAssistant.addCity("Toronto", false, 1, 100);
	
	// Add train when the start city does not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Pune", "Toronto", 2, 100),
		"Train cannot be added when the start city does not exist");
	
	// Add train when the destination city does not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Halifax", "Pune", 2, 100),
		"Train cannot be added when the destination city does not exist");
	
	// Add train when the start city and destination cities both do not exist
	assertThrows(IllegalArgumentException.class, 
		() -> travelAssistant.addTrain("Pune", "Mumbai", 2, 100),
		"Train cannot be added when the start city and destination city both do not exist");
	
	// Add train when the start and the destination city exist
	boolean output = travelAssistant.addTrain("Halifax", "Toronto", 200, 200);
	assertTrue(output, "Train can be added when the start and destination city exist");
	
	// Add train when a flight exists in the same path
	travelAssistant.addFlight("Toronto", "Halifax", 200, 200);
	boolean output1 = travelAssistant.addTrain("Toronto", "Halifax", 200, 200);
	assertTrue(output1, "Train can be added when a flight exists in the same path");
    }

}
