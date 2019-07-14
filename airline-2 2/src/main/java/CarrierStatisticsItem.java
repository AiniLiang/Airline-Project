public class CarrierStatisticsItem {
    private int totalFlights;
    private int delayedBySecurityCount;
    private int lateaircraftCount;

    private String carrierCode;
    private String carrierName;

    public CarrierStatisticsItem(String carrierName, String carrierCode, int totalFlights, int delayedBySecurityCount, int lateaircraftCount) {
        this.totalFlights = totalFlights;
        this.delayedBySecurityCount = delayedBySecurityCount;
        this.lateaircraftCount = lateaircraftCount;
        this.carrierCode = carrierCode;
        this.carrierName = carrierName;
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

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
}
