package group.ten.p1.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import group.ten.p1.shared.FlightDetails;
import group.ten.p1.shared.FlightStatus;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;

public class DetailsDialog extends JDialog {
	
	private JTextField tbIATACode;
	private JTextField tbOperatingAirline;
	private JTextField tbAircraftModel;
	private JTextField tbDepartureAirport;
	private JTextField tbOriginDate;
	private JTextField tbScheduledDeparture;
	private JTextField tbDepartureTerminal;
	private JTextField tbDepartureGates;
	private JTextField tbEstimatedDeparture;
	private JTextField tbCheckinLocation;
	private JTextField tbCheckinStart;
	private JTextField tbTrackingNumber;
	private JTextField tbArrivalAirport;
	private JTextField tbScheduledArrival;
	private JTextField tbArrivalTerminal;
	private JTextField tbArrivalGates;
	private JTextField tbEstimatedArrival;
	private JTextField tbCheckinCounter;
	private JTextField tbCheckinEnd;
	private JComboBox comboBox;

	public void initializeDialog(FlightDetails flightDetails) {
		tbIATACode.setText(flightDetails.getIATACode());
		tbOperatingAirline.setText(flightDetails.getOperatingAirline());
		tbAircraftModel.setText(flightDetails.getAircraftModel());
		tbDepartureAirport.setText(flightDetails.getDepartureAirport());
		tbOriginDate.setText(flightDetails.getOriginDate().toString());
		tbScheduledDeparture.setText(flightDetails.getScheduledDeparture().toString());
		tbDepartureTerminal.setText(String.valueOf(flightDetails.getDepartureTerminal()));
		tbDepartureGates.setText(flightDetails.getDepartureGates());
		tbEstimatedDeparture.setText(flightDetails.getEstimatedDeparture().toString());
		tbCheckinLocation.setText(String.valueOf(flightDetails.getCheckinLocation()));
		tbCheckinStart.setText(flightDetails.getCheckinStart().toString());
		tbTrackingNumber.setText(String.valueOf(flightDetails.getTrackingNumber()));
		tbArrivalAirport.setText(flightDetails.getArrivalAirport());
		tbScheduledArrival.setText(flightDetails.getScheduledArrival().toString());
		tbArrivalTerminal.setText(String.valueOf(flightDetails.getArrivalTerminal()));
		tbArrivalGates.setText(flightDetails.getArrivalGates());
		tbEstimatedArrival.setText(flightDetails.getEstimatedArrival().toString());
		tbCheckinCounter.setText(flightDetails.getCheckinCounter());
		tbCheckinEnd.setText(flightDetails.getCheckinEnd().toString());
		comboBox.setSelectedIndex(flightDetails.getFlightStatus().getValue());
	}

	/**
	 * Create the dialog.
	 */
	public DetailsDialog(ClientMain clientMainHandler, Boolean isEdit) {
		JPanel contentPanel = new JPanel();
		setBounds(100, 100, 950, 610);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().setLayout(null);
		JButton saveButton, cancelButton;
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 518, 895, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				saveButton = new JButton("Save");
				saveButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FlightDetails flight = getFlightDetails();
				if ( flight != null) {
					if (isValidFlight(clientMainHandler, flight, isEdit)) {
						clientMainHandler.clientCommunicator.updateFlight(flight);
						dispose();
					} else {
						dispose();
						JLabel label = new JLabel("Flight with same IATA Code and Tracking number already exists !!");
						label.setFont(new Font("Consolas", Font.PLAIN, 16));
						JOptionPane.showMessageDialog(null, label, "ERROR" , JOptionPane.WARNING_MESSAGE);
					}
				} else {
					dispose();
					JLabel label = new JLabel("Incorrect values entered !!");
					label.setFont(new Font("Consolas", Font.PLAIN, 16));
					JOptionPane.showMessageDialog(null, label, "ERROR" , JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel lblIataCode = new JLabel("IATA Code :");
		lblIataCode.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblIataCode.setBounds(55, 52, 104, 16);
		getContentPane().add(lblIataCode);
		
		tbIATACode = new JTextField();
		tbIATACode.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbIATACode.setBounds(269, 49, 161, 22);
		getContentPane().add(tbIATACode);
		tbIATACode.setColumns(10);
		if (isEdit) tbIATACode.setEditable(false);
		
		JLabel lblOperatingAirline = new JLabel("Operating Airline :");
		lblOperatingAirline.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblOperatingAirline.setBounds(520, 49, 196, 22);
		getContentPane().add(lblOperatingAirline);
		
		tbOperatingAirline = new JTextField();
		tbOperatingAirline.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbOperatingAirline.setColumns(10);
		tbOperatingAirline.setBounds(734, 49, 161, 22);
		getContentPane().add(tbOperatingAirline);
		if (isEdit) tbOperatingAirline.setEditable(false);
		
		JLabel lblAircraftModel = new JLabel("Aircraft Model :");
		lblAircraftModel.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblAircraftModel.setBounds(55, 96, 161, 16);
		getContentPane().add(lblAircraftModel);
		
		JLabel lblTrackingNumber = new JLabel("Tracking Number :");
		lblTrackingNumber.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblTrackingNumber.setBounds(520, 96, 161, 16);
		getContentPane().add(lblTrackingNumber);
		
		JLabel lblArrivalAirport = new JLabel("Arrival Airport :");
		lblArrivalAirport.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblArrivalAirport.setBounds(520, 135, 161, 16);
		getContentPane().add(lblArrivalAirport);
		
		JLabel lblDepartureAirport = new JLabel("Departure Airport :");
		lblDepartureAirport.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblDepartureAirport.setBounds(55, 135, 176, 16);
		getContentPane().add(lblDepartureAirport);
		
		JLabel lblOriginDate = new JLabel("Origin Date :");
		lblOriginDate.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblOriginDate.setBounds(55, 184, 161, 16);
		getContentPane().add(lblOriginDate);
		
		JLabel lblScheduledArrival = new JLabel("Scheduled Arrival :");
		lblScheduledArrival.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblScheduledArrival.setBounds(520, 226, 181, 16);
		getContentPane().add(lblScheduledArrival);
		
		JLabel lblScheduledDeparture = new JLabel("Scheduled Departure :");
		lblScheduledDeparture.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblScheduledDeparture.setBounds(55, 226, 189, 16);
		getContentPane().add(lblScheduledDeparture);
		
		JLabel lblArrivalTerminal = new JLabel("Arrival Terminal :");
		lblArrivalTerminal.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblArrivalTerminal.setBounds(520, 272, 181, 16);
		getContentPane().add(lblArrivalTerminal);
		
		JLabel lblDepartureTerminal = new JLabel("Departure Terminal :");
		lblDepartureTerminal.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblDepartureTerminal.setBounds(55, 272, 189, 16);
		getContentPane().add(lblDepartureTerminal);
		
		JLabel lblDepartureGates = new JLabel("Departure Gates :");
		lblDepartureGates.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblDepartureGates.setBounds(55, 316, 189, 16);
		getContentPane().add(lblDepartureGates);
		
		JLabel lblArrivalGates = new JLabel("Arrival Gates :");
		lblArrivalGates.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblArrivalGates.setBounds(520, 316, 181, 16);
		getContentPane().add(lblArrivalGates);
		
		JLabel lblEstimatedDeparture = new JLabel("Estimated Departure :");
		lblEstimatedDeparture.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblEstimatedDeparture.setBounds(55, 355, 189, 16);
		getContentPane().add(lblEstimatedDeparture);
		
		JLabel lblEstimatedArrival = new JLabel("Estimated Arrival :");
		lblEstimatedArrival.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblEstimatedArrival.setBounds(520, 355, 181, 16);
		getContentPane().add(lblEstimatedArrival);
		
		JLabel lblCheckinLocation = new JLabel("Check-In Location :");
		lblCheckinLocation.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblCheckinLocation.setBounds(55, 395, 176, 16);
		getContentPane().add(lblCheckinLocation);
		
		JLabel lblCheckinCounter = new JLabel("Check-In Counter :");
		lblCheckinCounter.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblCheckinCounter.setBounds(520, 395, 181, 16);
		getContentPane().add(lblCheckinCounter);
		
		JLabel lblCheckinStart = new JLabel("Check-In Start :");
		lblCheckinStart.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblCheckinStart.setBounds(55, 436, 161, 16);
		getContentPane().add(lblCheckinStart);
		
		JLabel lblCheckinEnd = new JLabel("Check-In End :");
		lblCheckinEnd.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblCheckinEnd.setBounds(520, 436, 181, 16);
		getContentPane().add(lblCheckinEnd);
		
		JLabel lblFlightStatus = new JLabel("Flight Status :");
		lblFlightStatus.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblFlightStatus.setBounds(55, 489, 149, 16);
		getContentPane().add(lblFlightStatus);
		
		String[] comboBoxItems = {
			"'B' = Arrival by bus at Concourse B",
			"'D' = Diverted",
			"'I' = Undefined late arrival or departure",
			"'L' = Aborted departure",
			"'M' = Flight delayed until tomorrow",
			"'S' = Definitively canceled flight",
			"'X' = Canceled flight for which there may be a replacement",
			"'Y' = Return to stand",
			"'Z' = Returned to apron"};
		comboBox = new JComboBox(comboBoxItems);
		comboBox.setFont(new Font("Consolas", Font.PLAIN, 16));
		comboBox.setBounds(269, 484, 457, 22);
		getContentPane().add(comboBox);
		
		tbAircraftModel = new JTextField();
		tbAircraftModel.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbAircraftModel.setColumns(10);
		tbAircraftModel.setBounds(269, 93, 161, 22);
		getContentPane().add(tbAircraftModel);
		
		tbDepartureAirport = new JTextField();
		tbDepartureAirport.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbDepartureAirport.setColumns(10);
		tbDepartureAirport.setBounds(269, 130, 161, 22);
		getContentPane().add(tbDepartureAirport);
		
		tbOriginDate = new JTextField();
		tbOriginDate.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbOriginDate.setColumns(10);
		tbOriginDate.setBounds(269, 179, 161, 22);
		getContentPane().add(tbOriginDate);
		
		tbScheduledDeparture = new JTextField();
		tbScheduledDeparture.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbScheduledDeparture.setColumns(10);
		tbScheduledDeparture.setBounds(269, 221, 161, 22);
		getContentPane().add(tbScheduledDeparture);
		
		tbDepartureTerminal = new JTextField();
		tbDepartureTerminal.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbDepartureTerminal.setColumns(10);
		tbDepartureTerminal.setBounds(269, 267, 161, 22);
		getContentPane().add(tbDepartureTerminal);
		
		tbDepartureGates = new JTextField();
		tbDepartureGates.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbDepartureGates.setColumns(10);
		tbDepartureGates.setBounds(269, 311, 161, 22);
		getContentPane().add(tbDepartureGates);
		
		tbEstimatedDeparture = new JTextField();
		tbEstimatedDeparture.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbEstimatedDeparture.setColumns(10);
		tbEstimatedDeparture.setBounds(269, 350, 161, 22);
		getContentPane().add(tbEstimatedDeparture);
		
		tbCheckinLocation = new JTextField();
		tbCheckinLocation.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbCheckinLocation.setColumns(10);
		tbCheckinLocation.setBounds(269, 390, 161, 22);
		getContentPane().add(tbCheckinLocation);
		
		tbCheckinStart = new JTextField();
		tbCheckinStart.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbCheckinStart.setColumns(10);
		tbCheckinStart.setBounds(269, 431, 161, 22);
		getContentPane().add(tbCheckinStart);
		
		tbTrackingNumber = new JTextField();
		tbTrackingNumber.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbTrackingNumber.setColumns(10);
		tbTrackingNumber.setBounds(734, 93, 161, 22);
		getContentPane().add(tbTrackingNumber);
		if (isEdit) tbTrackingNumber.setEditable(false);
		
		tbArrivalAirport = new JTextField();
		tbArrivalAirport.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbArrivalAirport.setColumns(10);
		tbArrivalAirport.setBounds(734, 132, 161, 22);
		getContentPane().add(tbArrivalAirport);
		
		tbScheduledArrival = new JTextField();
		tbScheduledArrival.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbScheduledArrival.setColumns(10);
		tbScheduledArrival.setBounds(734, 223, 161, 22);
		getContentPane().add(tbScheduledArrival);
		
		tbArrivalTerminal = new JTextField();
		tbArrivalTerminal.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbArrivalTerminal.setColumns(10);
		tbArrivalTerminal.setBounds(734, 269, 161, 22);
		getContentPane().add(tbArrivalTerminal);
		
		tbArrivalGates = new JTextField();
		tbArrivalGates.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbArrivalGates.setColumns(10);
		tbArrivalGates.setBounds(734, 313, 161, 22);
		getContentPane().add(tbArrivalGates);
		
		tbEstimatedArrival = new JTextField();
		tbEstimatedArrival.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbEstimatedArrival.setColumns(10);
		tbEstimatedArrival.setBounds(734, 352, 161, 22);
		getContentPane().add(tbEstimatedArrival);
		
		tbCheckinCounter = new JTextField();
		tbCheckinCounter.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbCheckinCounter.setColumns(10);
		tbCheckinCounter.setBounds(734, 392, 161, 22);
		getContentPane().add(tbCheckinCounter);
		
		tbCheckinEnd = new JTextField();
		tbCheckinEnd.setFont(new Font("Consolas", Font.PLAIN, 16));
		tbCheckinEnd.setColumns(10);
		tbCheckinEnd.setBounds(734, 433, 161, 22);
		getContentPane().add(tbCheckinEnd);
	}

	private FlightDetails getFlightDetails() {
		FlightDetails flightDetails = new FlightDetails();
		try {
			flightDetails.setAircraftModel(tbAircraftModel.getText());
			flightDetails.setArrivalAirport(tbArrivalAirport.getText());
			flightDetails.setArrivalGates(tbArrivalGates.getText());
			flightDetails.setArrivalTerminal(Integer.parseInt(tbArrivalTerminal.getText()));
			flightDetails.setCheckinCounter(tbCheckinCounter.getText());
			flightDetails.setCheckinEnd(Instant.parse(tbCheckinEnd.getText()));
			flightDetails.setCheckinLocation(Integer.parseInt(tbCheckinLocation.getText()));
			flightDetails.setCheckinStart(Instant.parse(tbCheckinStart.getText()));
			flightDetails.setDepartureAirport(tbDepartureAirport.getText());
			flightDetails.setDepartureGates(tbDepartureGates.getText());
			flightDetails.setDepartureTerminal(Integer.parseInt(tbDepartureTerminal.getText()));
			flightDetails.setEstimatedArrival(Instant.parse(tbEstimatedArrival.getText()));
			flightDetails.setEstimatedDeparture(Instant.parse(tbEstimatedDeparture.getText()));
			flightDetails.setIATACode(tbIATACode.getText());
			flightDetails.setOperatingAirline(tbOperatingAirline.getText());
			flightDetails.setOriginDate(LocalDate.parse(tbOriginDate.getText()));
			flightDetails.setScheduledArrival(Instant.parse(tbScheduledArrival.getText()));
			flightDetails.setScheduledDeparture(Instant.parse(tbScheduledDeparture.getText()));
			flightDetails.setTrackingNumber(Integer.parseInt(tbTrackingNumber.getText()));
			flightDetails.setFlightStatus(FlightStatus.fromInt(comboBox.getSelectedIndex()));
			
		} catch (Exception e) {
			return null;
		}
		return flightDetails;
	}

	private Boolean isValidFlight(ClientMain clientMainHandler, FlightDetails flight, Boolean isEdit) {
		if (isEdit) {
			return true;
		}
		String flightKey = flight.getIATACode() + flight.getTrackingNumber();
		if (clientMainHandler.clientCommunicator.flights.containsKey(flightKey)) {
			return false;
		}
		return true;
	}
}
