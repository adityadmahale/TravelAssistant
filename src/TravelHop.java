// Edge of the graph
// Holds travel related information
public class TravelHop {

    // Stores the start city object
    private City startCity;
    // Stores the destination city object
    private City destinationCity;
    // Stores the mode of travel
    private String mode;
    // Stores the cost of travel
    private int cost;
    // Stores the duration of travel
    private int duration;

    public TravelHop(City startCity, City destinationCity, String mode, int cost, int duration) {
	this.startCity = startCity;
	this.destinationCity = destinationCity;
	this.mode = mode;
	this.cost = cost;
	this.duration = duration;
    }

    @Override
    public String toString() {
	return destinationCity + "(" + mode + ")";
    }

    // Returns the destination city object
    public City getDestinationCity() {
	return destinationCity;
    }

    // Returns the travel mode
    public String getMode() {
	return mode;
    }

    // Calculate weight based on the formula:
    // cost * costImportance + travelTime * travelTimeImportance + hop *
    // travelHopImportance
    public int getHopWeight(int costImportance, int travelTimeImportance, int travelHopImportance) {
	return cost * costImportance + duration * travelTimeImportance + travelHopImportance;
    }
}
