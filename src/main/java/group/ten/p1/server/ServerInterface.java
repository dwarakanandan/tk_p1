package group.ten.p1.server;

import group.ten.p1.shared.FlightDetails;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote{
    void login(String clientName) throws RemoteException;
    void logout(String clientName) throws RemoteException;
    void updateFlight(String clientName, FlightDetails flight) throws RemoteException;
    void deleteFlight(String clientName, FlightDetails flight) throws RemoteException;
}
