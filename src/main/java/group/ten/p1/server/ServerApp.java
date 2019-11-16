package group.ten.p1.server;

import group.ten.p1.shared.Constants;
import group.ten.p1.shared.FlightDetails;
import group.ten.p1.shared.FlightStatus;
import group.ten.p1.client.ClientInterface;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerApp implements ServerInterface{

    HashMap<String, FlightDetails> flights = new HashMap<>();
    HashMap<String, ClientInterface> clients = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("Starting Server.");

        try {
            ServerApp server = new ServerApp();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            LocateRegistry.createRegistry(Constants.RMI_PORT);
            Registry registry = LocateRegistry.getRegistry(Constants.RMI_PORT);
            registry.bind(Constants.RMI_IDENTIFIER, stub);
        } catch (Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    ServerApp() {
        FlightDetails dummyFlight = getDummyFlight();
        String flightKey = dummyFlight.getIATACode() + dummyFlight.getTrackingNumber();
        flights.put(flightKey, dummyFlight);
    }

    public void login(String clientName, Remote clientCommunicatorRemote) throws RemoteException {
        ClientInterface clientInterface = (ClientInterface) clientCommunicatorRemote;
        System.out.println("Client logged in with "+clientName);
        clients.put(clientName, clientInterface);
        clientInterface.receiveListOfFlights(flights);
    };

    private FlightDetails getDummyFlight() {
		Instant instant = Instant.parse("2018-08-13T17:55:00Z");
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.setAircraftModel("A380");
		flightDetails.setArrivalAirport("FRA");
		flightDetails.setArrivalGates("C15A");
		flightDetails.setArrivalTerminal(1);
		flightDetails.setCheckinCounter("664-666");
		flightDetails.setCheckinEnd(instant);
		flightDetails.setCheckinLocation(1);
		flightDetails.setCheckinStart(instant);
		flightDetails.setDepartureAirport("BOM");
		flightDetails.setDepartureGates("C14");
		flightDetails.setDepartureTerminal(2);
		flightDetails.setEstimatedArrival(instant);
		flightDetails.setEstimatedDeparture(instant);
		flightDetails.setIATACode("LH");
		flightDetails.setOperatingAirline("Lufthansa");
		flightDetails.setOriginDate(LocalDate.now());
		flightDetails.setScheduledArrival(instant);
		flightDetails.setScheduledDeparture(instant);
		flightDetails.setTrackingNumber(591);
		flightDetails.setFlightStatus(FlightStatus.fromInt(8));
		return flightDetails;
	}

    public void logout(String clientName) {
        System.out.println("Client logged out with "+clientName);
        clients.remove(clientName);
    };

    public void updateFlight(String clientName, FlightDetails flight) throws RemoteException {
        String flightKey = flight.getIATACode() + flight.getTrackingNumber();
        flights.replace(flightKey, flight);
        for(ClientInterface client: clients.values()) {
            client.receiveUpdatedFlight(flight, false);
        }
    };

    public void deleteFlight(String clientName, FlightDetails flight) throws RemoteException {
        String flightKey = flight.getIATACode() + flight.getTrackingNumber();
        flights.remove(flightKey);
        for(ClientInterface client: clients.values()) {
            client.receiveUpdatedFlight(flight, true);
        }
    };
}
