package com.tekarch.TafFlightService.Service.Interface;

import com.tekarch.TafFlightService.Model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlights();
    Flight getFlightById(Long flightId);
    Flight addFlight(Flight flight);
    Flight updateFlight(Long flightId, Flight flight);
    void deleteFlight(Long flightId);

}
