package group.ten.p1.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import group.ten.p1.shared.FlightDetails;
import group.ten.p1.shared.FlightStatus;

public class ClientMain extends JFrame {

	private ClientMain frame;
	public JPanel contentPane;
	public JTable table;
	private JDialog dialog;
	private JButton btnNew, btnEdit, btnDelete;
	private ArrayList<FlightDetails> flightDetailsTable = new ArrayList<>();
	ClientCommunicator clientCommunicator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ClientCommunicator clientCommunicator = new ClientCommunicator();
		clientCommunicator.login();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientMain currentFrame = new ClientMain(clientCommunicator);
					clientCommunicator.setTable(currentFrame.table);
					currentFrame.frame = currentFrame;
					currentFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				clientCommunicator.logout();
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public ClientMain(ClientCommunicator clientCommunicator) {
		this.clientCommunicator = clientCommunicator;
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
				if (table.getSelectedRow() >= 0) {
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
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					((DefaultTableModel) table.getModel()).removeRow(selectedRow);
					flightDetailsTable.remove(selectedRow);
				}
			}
		});
	}

	private void setupJTable() {
		// Column Names
		String[] columnNames = { "Operating airline", "IATA Code", "Tracking Number", "Departure", "Arrival",
				"Estimated Departure Time", "Estimated Arrival Time" };

		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		
		table = new JTable(null, columnNames);
		table.setModel(tableModel);
		table.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 16));
		table.getTableHeader().setPreferredSize(new Dimension(100, 40));
		table.setFont(new Font("Consolas", Font.PLAIN, 16));
		table.setRowHeight(30);
		
		for (FlightDetails flight : this.clientCommunicator.flights.values()) {
            ((DefaultTableModel)table.getModel()).addRow(new Object[]{flight.getOperatingAirline(), flight.getIATACode(), flight.getTrackingNumber(),flight.getDepartureAirport(),
				flight.getArrivalAirport(), flight.getEstimatedDeparture(), flight.getEstimatedArrival()});
			flightDetailsTable.add(flight);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 1170, 557);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table);
	}

}
