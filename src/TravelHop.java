
public class TravelHop {

    City startCity;
    City destinationCity;
    private String mode;
    private int cost;
    private int duration;
    
    public TravelHop(City startCity, City destinationCity, String mode, int cost,
	    int duration) {
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

    public City getStartCity() {
        return startCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public String getMode() {
        return mode;
    }

    public int getCost() {
        return cost;
    }
    
    private int getTotalCost(boolean isVaccinated) {
	return !isVaccinated ? destinationCity.getTotalHotelCosts() + cost: cost;
    }

    public int getDuration() {
        return duration;
    }
    
    public int getHopWeight(int costImportance, int travelTimeImportance, 
	    int travelHopImportance, boolean isVaccinated) {
	return getTotalCost(isVaccinated) * costImportance + duration * travelTimeImportance +
		travelHopImportance;
    }
}
