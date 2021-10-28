
// Holds city and it corresponding cumulative weight
public class CityWeight implements Comparable<CityWeight> {
	private City city;
	private int weight;
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

	public City getCity() {
	    return city;
	}

	public boolean isReportNegative() {
	    return isReportNegative;
	}
	
}
