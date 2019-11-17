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
import java.util.LinkedHashMap;

public class ServerApp implements ServerInterface{

    LinkedHashMap<String, FlightDetails> flights = new LinkedHashMap<>();
    LinkedHashMap<String, ClientInterface> clients = new LinkedHashMap<>();
    static final String SERVER_TAG = "[SERVER] : ";
    
    public static void main(String[] args) {
        System.out.println(SERVER_TAG + "Starting Server...\n");

        try {
            ServerApp server = new ServerApp();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            LocateRegistry.createRegistry(Constants.RMI_PORT);
            Registry registry = LocateRegistry.getRegistry(Constants.RMI_PORT);
            registry.bind(Constants.RMI_IDENTIFIER, stub);
            System.out.println(SERVER_TAG + "Server started successfully...\n");
        } catch (Exception e){
            System.err.println(SERVER_TAG + " [ERROR] Could not start Server. Please try running the program again... ");
            System.exit(1);
        }

    }

    ServerApp() {
        FlightDetails dummyFlight = getDummyFlight();
        String flightKey = dummyFlight.getUniqueCode();
        flights.put(flightKey, dummyFlight);
    }

    public void login(String clientName, Remote clientCommunicatorRemote) throws RemoteException {
        ClientInterface clientInterface = (ClientInterface) clientCommunicatorRemote;
        System.out.println(SERVER_TAG + "Client logged in with ID:" + clientName + "\n");
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
        System.out.println(SERVER_TAG + "Client logged out with ID: " + clientName + "\n");
        clients.remove(clientName);
    };

    public void updateFlight(String clientName, FlightDetails flight) throws RemoteException {
        String flightKey = flight.getUniqueCode();
        System.out.println(SERVER_TAG + "Client " + clientName + " updated flight " + flightKey + "\n");
        
        if(flights.containsKey(flightKey))
            flights.replace(flightKey, flight);
        else
            flights.put(flightKey, flight);

        for(ClientInterface client: clients.values()) {
            client.receiveUpdatedFlight(flight, false);
        }
    };

    public void deleteFlight(String clientName, FlightDetails flight) throws RemoteException {
        String flightKey = flight.getUniqueCode();
        System.out.println(SERVER_TAG + "Client " + clientName + " deleted flight " + flightKey + "\n");
        flights.remove(flightKey);
        for(ClientInterface client: clients.values()) {
            client.receiveUpdatedFlight(flight, true);
        }
    };
}
