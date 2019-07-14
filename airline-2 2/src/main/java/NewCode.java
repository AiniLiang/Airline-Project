import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewCode {

    private static int airport_flights_total(JsonNode airportnode) {
        return airportnode.get("statistics").get("flights").get("total").asInt();
    }

    private static int airport_flights_delayedbysecurity(JsonNode airportnode) {
        return airportnode.get("statistics").get("# of delays").get("security").asInt();
    }

    private static int airport_flights_lateaircraft(JsonNode airportnode) {
        return airportnode.get("statistics").get("# of delays").get("late aircraft").asInt();
    }

    private static void writeXml(int result_total, int result_delayed, int result_airport, AirportStatisticsItem max_total_item, AirportStatisticsItem max_delayed_item, CarrierStatisticsItem min_lateaircraft_item) throws IOException {
        Document doc = DocumentHelper.createDocument();
        Element root=doc.addElement("result");
        Element result1 = root.addElement("result1");
        Element result2 = root.addElement("result2");
        Element result3 = root.addElement("result3");
        Element result4 = root.addElement("result4");
        Element result5 = root.addElement("result5");
        Element totalAirport = root.addElement("totalAirport");

        result1.setText(String.format("%d", result_total));
        result2.setText(String.format("%.2f%%", 100.0*result_delayed/result_total));
        result3.addElement("code").setText(max_delayed_item.getAirportCode());
        result3.addElement("name").setText(max_delayed_item.getAirportName());
        result4.addElement("code").setText(max_total_item.getAirportCode());
        result4.addElement("name").setText(max_total_item.getAirportName());
        result5.addElement("code").setText(min_lateaircraft_item.getCarrierCode());
        result5.addElement("name").setText(min_lateaircraft_item.getCarrierName());
        totalAirport.setText(String.format("%d", result_airport));

        XMLWriter writer =new XMLWriter(new FileOutputStream("result.xml"),
                OutputFormat.createPrettyPrint());
        writer.write(doc);
        writer.flush();
        writer.close();

    }

    public static void main(String[] args) throws IOException {
        List<StatisticsItem> statData = new ArrayList<StatisticsItem>();
        Map<String, String> airportNameMap = new HashMap<>();
        Map<String, AirportStatisticsItem> airportStatMap = new HashMap<>();
        Map<String, String> carrierNameMap = new HashMap<>();
        Map<String, CarrierStatisticsItem> carrierStatMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new FileInputStream("airlines"));
        for (JsonNode item : rootNode) {
            String airportcode = item.get("airport").get("code").asText();
            String airportname = item.get("airport").get("name").asText();
            String carriercode = item.get("carrier").get("code").asText();
            String carriername = item.get("carrier").get("name").asText();

            if(!airportNameMap.containsKey(airportcode)) {
                airportNameMap.put(airportcode, airportname);
            }
            if(!carrierNameMap.containsKey(carriercode)) {
                carrierNameMap.put(carriercode, carriername);
            }

            int total = airport_flights_total(item);
            int delayed =  airport_flights_delayedbysecurity(item);
            int lateaircraft = airport_flights_lateaircraft(item);
            StatisticsItem dataitem = new StatisticsItem(airportname, airportcode, total, delayed, lateaircraft);
            dataitem.setCarrierCode(carriercode);
            dataitem.setCarrierCode(carriername);
            statData.add(dataitem);

            if(!airportStatMap.containsKey(airportcode)){
                airportStatMap.put(airportcode, new AirportStatisticsItem(airportname, airportcode, total, delayed, lateaircraft));
            }else{
                AirportStatisticsItem airitem = airportStatMap.get(airportcode);
                airitem.setTotalFlights(airitem.getTotalFlights()+dataitem.getTotalFlights());
                airitem.setDelayedBySecurityCount(airitem.getDelayedBySecurityCount()+dataitem.getDelayedBySecurityCount());
                airitem.setLateaircraftCount(airitem.getLateaircraftCount()+dataitem.getLateaircraftCount());
            }

            if(!carrierStatMap.containsKey(airportcode)) {
                carrierStatMap.put(carriercode, new CarrierStatisticsItem(carriername, carriercode, total, delayed, lateaircraft));
            }else{
                CarrierStatisticsItem carrieritem = carrierStatMap.get(carriercode);
                carrieritem.setTotalFlights(carrieritem.getTotalFlights()+dataitem.getTotalFlights());
                carrieritem.setDelayedBySecurityCount(carrieritem.getDelayedBySecurityCount()+dataitem.getDelayedBySecurityCount());
                carrieritem.setLateaircraftCount(carrieritem.getLateaircraftCount()+dataitem.getLateaircraftCount());
            }
        }


        int result_total = statData.stream().mapToInt(StatisticsItem::getTotalFlights).sum();
        int result_delayed = statData.stream().mapToInt(StatisticsItem::getDelayedBySecurityCount).sum();
        int result_airport = (int) statData.stream().map(StatisticsItem::getAirportCode).distinct().count();

        AirportStatisticsItem max_delayed_item = airportStatMap.values().stream().max((o1, o2) -> o1.getDelayedBySecurityCount() > o2.getDelayedBySecurityCount() ? 1 : -1).get();
        AirportStatisticsItem max_total_item = airportStatMap.values().stream().max((o1, o2) -> o1.getTotalFlights() > o2.getTotalFlights() ? 1 : -1).get();
        CarrierStatisticsItem min_lateaircraft_item = carrierStatMap.values().stream().min((o1, o2) -> o1.getLateaircraftCount() > o2.getLateaircraftCount() ? 1 : -1).get();



        writeXml(result_total, result_delayed,result_airport, max_total_item, max_delayed_item, min_lateaircraft_item);
        System.out.println("Finished!");
    }

}
