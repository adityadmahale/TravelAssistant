import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelAssistant {
    
    // Stores the city name and its corresponding City object.
    Map<String, City> cities = new HashMap<>();
    
    public boolean addCity( String cityName, boolean testRequired, int timeToTest,
	    int nightlyHotelCost) throws IllegalArgumentException {
	// If the city is already known by the TravelAssistant, then return false
	if (cities.containsKey(cityName)) return false;
	
	// Throw an exception when then the hotel cost is invalid
	if (nightlyHotelCost < 0) throw new IllegalArgumentException();
	
	// At this point, the passed arguments are correct.
	// Create a new City object and add it to the cities Map.
	var city = new City(cityName, testRequired, timeToTest, nightlyHotelCost);
	cities.put(cityName, city);
	
	return true;
    }
    
    public boolean addFlight(String startCity, String destinationCity, int flightTime,
	    int flightCost) throws IllegalArgumentException {
	return addTravelMode(startCity, destinationCity, flightTime, flightCost, "flight");
    }
    
    public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost)
	    throws IllegalArgumentException {
	return addTravelMode(startCity, destinationCity, trainTime, trainCost, "train");
    }
    
    private boolean addTravelMode(String startCity, String destinationCity, int duration,
	    int cost, String mode) throws IllegalArgumentException {
	if (!isTravelInputsCorrect(startCity, destinationCity, duration, cost)) return false;
		
		
	return true;
    }
    
    private boolean isTravelInputsCorrect(String startCity, String destinationCity, int duration,
	    int cost) throws IllegalArgumentException {
	// Get the start city object. Throw an exception if the city is not present.
	var fromCity = cities.get(startCity);
	if (fromCity == null) throw new IllegalArgumentException();
			
	// Get the destination city object. Throw an exception if the city is not present.
	var toCity = cities.get(destinationCity);
	if (toCity == null) throw new IllegalArgumentException();
		
	// Throw an exception if the start and destination are the same city
	if (fromCity == toCity) throw new IllegalArgumentException();
	
	return true;
    }
    
    public List<String> planTrip ( String startCity, String destinationCity, boolean isVaccinated, int
	    costImportance, int travelTimeImportance, int travelHopImportance ) throws
	    IllegalArgumentException {
	return null;
    }
}
