package gui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import data.Manager;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class ManagerGUI {

	private JFrame frame;
	private Manager manager;
	
	private JTable mixersTable;
	private JTable sitesTable;
	private DefaultTableModel sitesModel;
	private DefaultTableModel mixersModel;
	private JScrollPane scrollPaneMixers;
	private JScrollPane scrollPaneSites;
	
	private JLabel lblManager;
	private JLabel lblPort; 
	private JLabel lblIp;
	private JButton startButton;
	private JButton deleteMixer;
	private JButton stopButton;
	private JButton deleteSite;
	private JButton connectButton;
	private JButton conectButton;
	private JTextField ipField;
	private JTextField portField;
	
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ManagerGUI window = new ManagerGUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public ManagerGUI() {
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(-7, -25, 759, 441);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//models
		mixersModel = (new DefaultTableModel(new Object[][] {},new String[] {"Nazwa mixera"}));
		sitesModel = (new DefaultTableModel(new Object[][] {},new String[] {"Nazwa site"}));
				
		//label
		initializeTexts();
		
		//tables&panes
		initializeTables();
		
		//buttons & listeners
		initializeButtons();
		initializeButtonListeners();
		
		//manager
		
	}
	
	private void initializeTexts()
	{
		lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblManager.setBounds(6, 9, 547, 34);
		frame.getContentPane().add(lblManager);
		
		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(557, 22, 42, 19);
		frame.getContentPane().add(portField);
		
		ipField = new JTextField();
		ipField.setText("localhost");
		ipField.setColumns(10);
		ipField.setBounds(603, 22, 56, 19);
		frame.getContentPane().add(ipField);
		
		lblIp = new JLabel("ip:");
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setBounds(603, 3, 56, 16);
		frame.getContentPane().add(lblIp);
		
		lblPort = new JLabel("port:");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(557, 3, 42, 19);
		frame.getContentPane().add(lblPort);
	}
	
	private void initializeTables()
	{
		scrollPaneMixers = new JScrollPane();
		scrollPaneMixers.setBounds(6, 53, 274, 345);
		frame.getContentPane().add(scrollPaneMixers);
		
		scrollPaneSites = new JScrollPane();
		scrollPaneSites.setBounds(279, 53, 274, 345);
		frame.getContentPane().add(scrollPaneSites);
		
		mixersTable = new JTable(mixersModel);
		scrollPaneMixers.setViewportView(mixersTable);
		
		sitesTable = new JTable(sitesModel);
		scrollPaneSites.setViewportView(sitesTable);
	}
	
	private void initializeButtons()
	{
		connectButton = new JButton("po\u0142\u0105cz site z mixerem");
		connectButton.setEnabled(false);
		connectButton.setBounds(557, 53, 180, 66);
		frame.getContentPane().add(connectButton);
		
		startButton = new JButton("w\u0142\u0105cz iluminacje w site");
		startButton.setEnabled(false);
		startButton.setBounds(557, 122, 180, 66);
		frame.getContentPane().add(startButton);
		
		stopButton = new JButton("wy\u0142\u0105cz iluminacje w site");
		stopButton.setEnabled(false);
		stopButton.setBounds(557, 192, 180, 66);
		frame.getContentPane().add(stopButton);
		
		deleteMixer = new JButton("Usun wybrany mixer");
		deleteMixer.setEnabled(false);
		deleteMixer.setBounds(557, 262, 180, 66);
		frame.getContentPane().add(deleteMixer);
		
		deleteSite = new JButton("Usun wybrany site");
		deleteSite.setEnabled(false);
		deleteSite.setBounds(557, 332, 180, 65);
		frame.getContentPane().add(deleteSite);
		
		conectButton = new JButton("po\u0142\u0105cz");
		conectButton.setBounds(662, 21, 75, 22);
		frame.getContentPane().add(conectButton);
		
	}
	
	private void initializeButtonListeners()
	{
		conectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				manager = new Manager();
				manager.linkModels(sitesModel, mixersModel, sitesTable, mixersTable);
				try
				{
					manager.connect(ipField.getText().toString(), Integer.parseInt(portField.getText().toString()));
					connectButton.setEnabled(true);
					startButton.setEnabled(true);
					stopButton.setEnabled(true);
					deleteMixer.setEnabled(true);
					deleteSite.setEnabled(true);
					conectButton.setEnabled(false);
				}
				catch(NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(frame, "B³¹d danych!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
				}
				
				try 
				{
					manager.onCreate();
				} 
				catch (RemoteException | NullPointerException e1) 
				{
					JOptionPane.showMessageDialog(frame, "B³¹d kopii!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		
		connectButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				manager.connectElements(mixersTable.getSelectedRow(), sitesTable.getSelectedRow());
			}
		});
		
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				manager.startIlumination(sitesTable.getSelectedRow());
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				manager.stopIlumination(sitesTable.getSelectedRow());
			}
		});
		
		deleteMixer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(manager.deleteMixer(mixersTable.getSelectedRow()) == true)
				{
					mixersModel.removeRow(mixersTable.getSelectedRow());
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Mixer nie zosta³ usuniêty", "B³¹d", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		deleteSite.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(manager.deleteSite(sitesTable.getSelectedRow()) == false)
				{
					JOptionPane.showMessageDialog(frame, "Site nie zosta³o usuniête", "B³¹d", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
