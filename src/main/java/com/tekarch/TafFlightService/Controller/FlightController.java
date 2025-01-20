package com.tekarch.TafFlightService.Controller;


import com.tekarch.TafFlightService.Model.Flight;
import com.tekarch.TafFlightService.Service.FlightServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private static final Logger logger = LogManager.getLogger(FlightController.class);

    @Autowired
    private FlightServiceImpl flightServiceImpl;

    // Get all flights
    @GetMapping
    public ResponseEntity<?> getAllFlights() {
        try {
            return new ResponseEntity<>(flightServiceImpl.getAllFlights(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching flights: {}", e.getMessage());
            return new ResponseEntity<>("Error fetching flights: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a specific flight by ID
    @GetMapping("/{flightId}")
    public ResponseEntity<?> getFlightById(@PathVariable Long flightId) {
        try {
            Flight flight = flightServiceImpl.getFlightById(flightId);
            if (flight != null) {
                return new ResponseEntity<>(flight, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching flight details: {}", e.getMessage());
            return new ResponseEntity<>("Error fetching flight: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a new flight
    @PostMapping
    public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
        try {
            Flight createdFlight = flightServiceImpl.addFlight(flight);
            return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while adding flight: {}", e.getMessage());
            return new ResponseEntity<>("Error adding flight: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing flight
    @PutMapping("/{flightId}")
    public ResponseEntity<?> updateFlight(@PathVariable Long flightId, @RequestBody Flight flight) {
        try {
            Flight updatedFlight = flightServiceImpl.updateFlight(flightId, flight);
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating flight: {}", e.getMessage());
            return new ResponseEntity<>("Error updating flight: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a flight by ID
    @DeleteMapping("/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long flightId) {
        try {
            flightServiceImpl.deleteFlight(flightId);
            return new ResponseEntity<>("Flight deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error occurred while deleting flight: {}", e.getMessage());
            return new ResponseEntity<>("Error deleting flight: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler
    public ResponseEntity<String> respondWithError(Exception e) {
        logger.error("Exception Occurred. Details : {}", e.getMessage());
        return new ResponseEntity<>("Exception Occurred. More info :" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
