import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class TravelAssistant {

    // Constants defining mode of travel
    private static final String MODE_START = "start";
    private static final String MODE_FLY = "fly";
    private static final String MODE_TRAIN = "train";

    // Maximum weight between two cities
    private static final int MAX_WEIGHT = Integer.MAX_VALUE;

    // Stores the city name and its corresponding City object.
    private Map<String, City> cities = new HashMap<>();

    // Adjacency list for storing relation between cities and travel hops
    private Map<City, List<TravelHop>> adjacencyList = new HashMap<>();

    public boolean addCity(String cityName, boolean testRequired, int timeToTest,
	    int nightlyHotelCost) throws IllegalArgumentException {

	// Validate city name and nightly hotel cost
	if (!isCityNameValid(cityName) || nightlyHotelCost < 1) {
	    throw new IllegalArgumentException();
	}

	// If the city is already known by the TravelAssistant, then return false
	if (cities.containsKey(cityName)) {
	    return false;
	}

	// At this point, the passed arguments are correct.
	// Create a new City object and add it to the cities Map.
	var city = new City(cityName, testRequired, timeToTest, nightlyHotelCost);
	cities.put(cityName, city);

	// Add the city to the adjacency list.
	adjacencyList.put(city, new ArrayList<>());
	return true;
    }

    // Checks if the city name is invalid
    private boolean isCityNameValid(String cityName) {
	return cityName != null && cityName != "";
    }

    // Adds flight between two cities
    public boolean addFlight(String startCity, String destinationCity, int flightTime,
	    int flightCost) throws IllegalArgumentException {
	return addTravelHop(startCity, destinationCity, flightTime, flightCost, MODE_FLY);
    }

    // Add train between two cities
    public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost)
	    throws IllegalArgumentException {
	return addTravelHop(startCity, destinationCity, trainTime, trainCost, MODE_TRAIN);
    }

    private void validateCities(City fromCity, City toCity) throws IllegalArgumentException {
	// Throw an exception if:
	// The start and destination are the same city.
	// The start and destination cities are not present
	if (fromCity == null || toCity == null || fromCity == toCity) {
	    throw new IllegalArgumentException();
	}
    }

    private boolean addTravelHop(String startCity, String destinationCity, int duration, int cost,
	    String mode) throws IllegalArgumentException {
	// Check if the cost and duration are negative values
	if (duration < 1 || cost < 1 || !isCityNameValid(startCity)
		|| !isCityNameValid(destinationCity)) {
	    throw new IllegalArgumentException();
	}

	// Get the start city and destination city objects
	var fromCity = cities.get(startCity);
	var toCity = cities.get(destinationCity);
	validateCities(fromCity, toCity);

	// Check if the travel mode is already present
	if (isTravelHopPresent(fromCity, toCity, mode))
	    return false;

	// At this point, the inputs are correct. So, a new edge can be added between
	// the two cities.
	adjacencyList.get(fromCity).add(new TravelHop(fromCity, toCity, mode, cost, duration));

	return true;
    }

    // Checks if the travel hop is already present
    private boolean isTravelHopPresent(City fromCity, City toCity, String mode)
	    throws IllegalArgumentException {

	// Name of the destination city
	String destinationCity;

	// Mode of travel
	String modeOfTravel;

	// Iterate through all the travel hops
	for (var travelOption : adjacencyList.get(fromCity)) {
	    destinationCity = travelOption.getDestinationCity().getCityName();
	    modeOfTravel = travelOption.getMode();

	    // If the travel hop is already present, then return true
	    if (destinationCity.equals(toCity.getCityName()) && modeOfTravel.equals(mode)) {
		return true;
	    }
	}

	return false;
    }

    // Method to print the graph
    public void print() {
	for (City startCity : adjacencyList.keySet()) {
	    var destinations = adjacencyList.get(startCity);
	    if (!destinations.isEmpty()) {
		System.out.println(startCity + " -> " + destinations);
	    }
	}
    }

    public List<String> planTrip(String startCity, String destinationCity, boolean isVaccinated,
	    int costImportance, int travelTimeImportance, int travelHopImportance)
	    throws IllegalArgumentException {
	// Minimum valid value of the importance parameters
	final int MIN_VALID_IMPORTANCE = 0;

	// Set the source city weight to 0
	final int SOURCE_CITY_WEIGHT = 0;

	// Validate input parameters
	if (!isCityNameValid(startCity) || !isCityNameValid(destinationCity)
		|| costImportance < MIN_VALID_IMPORTANCE
		|| travelTimeImportance < MIN_VALID_IMPORTANCE
		|| travelHopImportance < MIN_VALID_IMPORTANCE) {
	    throw new IllegalArgumentException();
	}

	// Get the start city and destination city objects
	var fromCity = cities.get(startCity);
	var toCity = cities.get(destinationCity);
	validateCities(fromCity, toCity);

	// Set for storing visited cities
	Set<City> visited = new HashSet<>();

	// Stores the distance table from the "fromCity" node
	Map<City, Integer> weights = new HashMap<>();

	// Initialize the table with max value for all the visitable cities
	for (City city : cities.values()) {
	    weights.put(city, MAX_WEIGHT);
	}

	// Table for storing previous cities
	Map<City, City> previousCities = new HashMap<>();

	// Table for storing mode of travel
	Map<City, String> modes = new HashMap<>();
	modes.put(fromCity, MODE_START);

	// Set fromCity distance to 0
	weights.put(fromCity, SOURCE_CITY_WEIGHT);

	// Process cities in priority
	PriorityQueue<CityWeight> queue = new PriorityQueue<>();

	// Add the start city to the queue
	queue.add(new CityWeight(fromCity, SOURCE_CITY_WEIGHT, fromCity.isTestPossible()));

	while (!queue.isEmpty()) {
	    // Remove the city with priority
	    CityWeight currentCityWeight = queue.remove();
	    City current = currentCityWeight.getCity();

	    // Add to the city to the visited set
	    visited.add(current);

	    // Loop for all the neighboring cities
	    for (var hop : adjacencyList.get(current)) {

		// Get the neighbor city
		City neighborCity = hop.getDestinationCity();

		// If the city is already visited, then skip
		if (visited.contains(neighborCity)) {
		    continue;
		}

		// Skip the neighboring city if it requires testing
		// and the current city does not allow testing
		if (!isVisitable(isVaccinated, currentCityWeight, neighborCity)) {
		    continue;
		}

		// Defines if the test report is negative
		boolean isReportNegative = false;

		// Initialize weight to the neighbor city to 0
		int newWeight = 0;

		// Checks if the test is needed and updates the weight
		if (isTestNeeded(isVaccinated, currentCityWeight, neighborCity)) {
		    newWeight += current.getTotalHotelCosts() * costImportance;
		    isReportNegative = true;
		}

		// Calculate new weight
		newWeight += weights.get(current) + hop.getHopWeight(costImportance,
			travelTimeImportance, travelHopImportance);

		// If the new weight is less, then update the weight
		if (newWeight < weights.get(neighborCity)) {
		    weights.put(neighborCity, newWeight);

		    // Update the previousCities table
		    previousCities.put(neighborCity, current);

		    // Update the mode of travel table
		    modes.put(neighborCity, hop.getMode());

		    // Add it to the queue
		    queue.add(new CityWeight(neighborCity, newWeight,
			    currentCityWeight.isReportNegative() || isReportNegative));
		}
	    }
	}

	// If there's no route, then return null
	if (weights.get(toCity) == MAX_WEIGHT) {
	    return null;
	}

	return getPath(toCity, modes, previousCities);
    }

    // Checks if the test is needed in the current city
    private boolean isTestNeeded(boolean isVaccinated, CityWeight currentCityWeight,
	    City neighborCity) {
	// When an individual is vaccinated or the report is negative, then the test is
	// not required
	if (isVaccinated || currentCityWeight.isReportNegative()) {
	    return false;
	}

	return neighborCity.isTestRequired() && currentCityWeight.getCity().isTestPossible();
    }

    private boolean isVisitable(boolean isVaccinated, CityWeight currentCityWeight,
	    City neighborCity) {
	// When an individual is vaccinated or the report is negative,
	// then it's possible to visit the neighbor city
	if (isVaccinated || currentCityWeight.isReportNegative()) {
	    return true;
	}

	// When the neighbor city does not require testing, then the visit is possible
	if (!neighborCity.isTestRequired()) {
	    return true;
	}

	// Otherwise, when the test is possible in the current city, then it is possible
	// to visit the neighbor city
	return currentCityWeight.getCity().isTestPossible();
    }

    // Returns the list of paths between two cities
    private List<String> getPath(City toCity, Map<City, String> modes,
	    Map<City, City> previousCities) {

	// A stack to retrieve the information in the reverse order from the
	// previousCities table
	Stack<City> stack = new Stack<>();

	// Push the destination city to the stack
	stack.push(toCity);

	// Push all the cities to by getting the previously visited cities
	City previousCity = previousCities.get(toCity);
	while (previousCity != null) {
	    stack.push(previousCity);
	    previousCity = previousCities.get(previousCity);
	}

	// Remove all items from the stack to get the paths in order
	List<String> path = new ArrayList<>();
	City city;
	while (!stack.isEmpty()) {
	    city = stack.pop();
	    path.add(modes.get(city) + " " + city.getCityName());
	}

	return path;
    }
}
