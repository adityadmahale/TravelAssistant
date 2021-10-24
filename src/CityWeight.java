
// Holds city and it corresponding cumulative weight
public class CityWeight implements Comparable<CityWeight> {
	private City city;
	private int weight;
	
	public CityWeight(City city, int weight) {
	    this.city = city;
	    this.weight = weight;
	}
	
	@Override
	public int compareTo(CityWeight o) {
	    return Integer.compare(weight, o.weight);
	}

	public City getCity() {
	    return city;
	}
}
