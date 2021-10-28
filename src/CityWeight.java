
// Holds city and it corresponding cumulative weight
public class CityWeight implements Comparable<CityWeight> {
    // Stores the city object
    private City city;
    // Weight of the city
    private int weight;
    // Stores if the test report is negative
    private boolean isReportNegative;

    public CityWeight(City city, int weight, boolean isReportNegative) {
	this.city = city;
	this.weight = weight;
	this.isReportNegative = isReportNegative;
    }

    @Override
    public int compareTo(CityWeight o) {
	return Integer.compare(weight, o.weight);
    }

    // Returns the city object
    public City getCity() {
	return city;
    }

    // Returns if the report is negative
    public boolean isReportNegative() {
	return isReportNegative;
    }

}
