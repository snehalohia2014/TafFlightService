package com.tekarch.TafFlightService.Service;

import com.tekarch.TafFlightService.Controller.FlightController;
import com.tekarch.TafFlightService.Model.Flight;
import com.tekarch.TafFlightService.Service.Interface.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LogManager.getLogger(FlightServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${datastore.service.url}")
    private String DATASOURCE_SERVICE_URL;

    public List<Flight> getAllFlights(){
        ResponseEntity<List> response = restTemplate.exchange(
                DATASOURCE_SERVICE_URL + "/flights",
                HttpMethod.GET,
                null, List.class );
        return response.getBody();
    }

    public Flight getFlightById(Long flightId){
        ResponseEntity<Flight> response = restTemplate.exchange(
                DATASOURCE_SERVICE_URL +  "/flights" + "/" + flightId,
                HttpMethod.GET,
                null, Flight.class);
        return response.getBody();
    }

    public Flight addFlight(Flight flight) {
        ResponseEntity<Flight> response = restTemplate.exchange(
                DATASOURCE_SERVICE_URL + "/flights", HttpMethod.POST, new HttpEntity<>(flight), Flight.class);
        return response.getBody();
    }

    public Flight updateFlight(Long flightId, Flight flight) {
        ResponseEntity<Flight> response = restTemplate.exchange(
                DATASOURCE_SERVICE_URL + "/flights/" + flightId,
                HttpMethod.PUT,
                new HttpEntity<>(flight),
                Flight.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Flight updated successfully: {}", response.getBody());
        } else {
            logger.error("Failed to update flight. Status: {}. Response: {}", response.getStatusCode(), response.getBody());
        }
        return response.getBody();
    }

    public void deleteFlight(Long flightId) {
        restTemplate.exchange(
                DATASOURCE_SERVICE_URL + "/flights/" + flightId,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
}
