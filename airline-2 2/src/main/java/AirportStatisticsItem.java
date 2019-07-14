public class AirportStatisticsItem {
    private int totalFlights;
    private int delayedBySecurityCount;
    private int lateaircraftCount;

    private String airportCode;
    private String airportName;

    public AirportStatisticsItem(String airportName, String airportCode, int totalFlights, int delayedBySecurityCount, int lateaircraftCount) {
        this.totalFlights = totalFlights;
        this.delayedBySecurityCount = delayedBySecurityCount;
        this.lateaircraftCount = lateaircraftCount;
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public int getTotalFlights() {
        return totalFlights;
    }

    public void setTotalFlights(int totalFlights) {
        this.totalFlights = totalFlights;
    }

    public int getDelayedBySecurityCount() {
        return delayedBySecurityCount;
    }

    public void setDelayedBySecurityCount(int delayedBySecurityCount) {
        this.delayedBySecurityCount = delayedBySecurityCount;
    }

    public int getLateaircraftCount() {
        return lateaircraftCount;
    }

    public void setLateaircraftCount(int lateaircraftCount) {
        this.lateaircraftCount = lateaircraftCount;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
