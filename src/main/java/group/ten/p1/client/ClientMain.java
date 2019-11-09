package group.ten.p1.client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ClientMain extends JFrame {

	private ClientMain frame;
	private JPanel contentPane;
	private JTable table;
	private JDialog dialog;
	private JButton btnNew,btnEdit,btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
				dialog.setVisible(true);
				dialog.setLocationRelativeTo(frame);
				dialog.setResizable(false);
			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked btnEdit");
			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
				}
			}
		});
	}

	private void setupJTable() {
		// Column Names 
		String[] columnNames = { "Operating airline", "Flight Number", "Departure",
			 "Arrival", "Estimated Departure Time", "Estimated Arrival Time", "Terminal" };


		DefaultTableModel tableModel = new DefaultTableModel(null, columnNames) {

			@Override
			public boolean isCellEditable(int row, int column) {
			//all cells false
			return false;
			}
		};

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
}
