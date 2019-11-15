package group.ten.p1.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import group.ten.p1.server.ServerInterface;
import group.ten.p1.shared.FlightDetails;
import group.ten.p1.shared.FlightStatus;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain extends JFrame{

	private ClientMain frame;
	private JPanel contentPane;
	private JTable table;
	private JDialog dialog;
	private JButton btnNew,btnEdit,btnDelete;
	private ArrayList<FlightDetails> flightDetailsTable = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			ServerInterface stub = (ServerInterface) registry.lookup("TK Airport");
		} catch (Exception e){
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientMain currentFrame = new ClientMain();
					currentFrame.frame = currentFrame;
					currentFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientMain() {
		setupJFrame();
		setupJButtons();
		setupJTable();
	}

	private void setupJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setResizable(false);
	}

	private void setupJButtons() {
		btnNew = new JButton("New");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNew.setBounds(317, 598, 106, 41);
		contentPane.add(btnNew);
		
		btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnEdit.setBounds(543, 598, 106, 41);
		contentPane.add(btnEdit);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnDelete.setBounds(750, 598, 106, 41);
		contentPane.add(btnDelete);

		btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DetailsDialog dialog = new DetailsDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(frame);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					FlightDetails selectedFlight = flightDetailsTable.get(table.getSelectedRow());
					DetailsDialog dialog = new DetailsDialog();
					dialog.initializeDialog(selectedFlight);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frame);
					dialog.setResizable(false);
					dialog.setVisible(true);
				}
			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
					flightDetailsTable.remove(table.getSelectedRow());
				}
			}
		});
	}

	private void setupJTable() {
		// Column Names 
		String[] columnNames = { "Operating airline", "IATA Code", "Tracking Number","Departure",
			 "Arrival", "Estimated Departure Time", "Estimated Arrival Time"};


		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
			}
		};

		FlightDetails flight = getFlight();
		tableModel.addRow(new Object[]{flight.getOperatingAirline(), flight.getIATACode(), flight.getTrackingNumber(),flight.getDepartureAirport(),
			flight.getArrivalAirport(), flight.getEstimatedDeparture(), flight.getEstimatedArrival()});

		flightDetailsTable.add(flight);

		table = new JTable(null, columnNames);
		table.setModel(tableModel);
		table.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 16));
		table.getTableHeader().setPreferredSize(new Dimension(100, 40));
		table.setFont(new Font("Consolas", Font.PLAIN, 16));
		table.setRowHeight(30);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 1170, 557);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table);
	}

	private FlightDetails getFlight() {
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

}
