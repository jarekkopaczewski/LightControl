package gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import data.Site;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class SiteGUI {

	private JFrame frame;
	private Site site;
	private JLabel[] imageLabels = new JLabel[25];
	private JLabel siteName;
	private JLabel lblPort;
	private JLabel lblName;
	private JLabel lblIp ;
	private JButton deleteButton ;
	private JButton setNameButton;
	private JTextField nameField;
	private JTextField portField;
	private JTextField ipField;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					SiteGUI window = new SiteGUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public SiteGUI() 
	{
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 368, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//labels & text fields
		initializeTexts();
		
		//buttons & listeners
		initializeButtons();
		initializeButtonsListeners();
		
		//bulbs images
		initializeImageLabels();

		//on close send signal
		onClose();
	}
	
	private void initializeButtons()
	{
		setNameButton = new JButton("Ustaw nazwe i port");
		setNameButton.setBounds(10, 410, 162, 55);
		frame.getContentPane().add(setNameButton);
		
		deleteButton = new JButton("Usun site");
		deleteButton.setBounds(179, 410, 165, 55);
		frame.getContentPane().add(deleteButton);
	}
	
	private void onClose()
	{
		frame.addWindowListener( (WindowListener) new WindowAdapter()
		 {
		   public void windowClosing(WindowEvent e)
		    {
				try 
				{
					site.closeSignal();
				} 
				catch (NullPointerException | RemoteException e1) {
				}
		    }
		 });
	}
	
	private void initializeButtonsListeners()
	{
		setNameButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if( nameField.getText().isEmpty() || portField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Site musi mieæ nazwê i port!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					site = new Site(nameField.getText().toString(),Integer.parseInt(portField.getText().toString()));
					site.linkImages(imageLabels);
					
					if(site.addTo(ipField.getText().toString()))
					{
						refactorElements(false,nameField.getText().toString(),nameField.getText().toString());
						frame.setBounds(100, 100, 368, 520);
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Site nie zosta³ dodany przez kolizjê nazwy lub portu!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
					}
					
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					if(site.closeSignal())
					{
						refactorElements(true,"Site","Site");
						frame.setBounds(100, 100, 368, 575);
					}
				} 
				catch (NullPointerException | RemoteException e1) 
				{
				}
			}
		});
	}
	
	private void refactorElements(Boolean how, String name, String title)
	{
		setNameButton.setEnabled(how);
		nameField.setEditable(how);
		portField.setVisible(how);
		nameField.setVisible(how);
		ipField.setVisible(how);
		lblPort.setVisible(how);
		lblName.setVisible(how);
		lblIp.setVisible(how);
		siteName.setText(name);
		frame.setTitle(title);
	}
	
	private void initializeTexts()
	{
		
		siteName = new JLabel("Site");
		siteName.setHorizontalAlignment(SwingConstants.CENTER);
		siteName.setFont(new Font("Tahoma", Font.BOLD, 22));
		siteName.setBounds(98, 10, 157, 34);
		frame.getContentPane().add(siteName);
		
		lblIp = new JLabel("ip:");
		lblIp.setBounds(179, 476, 41, 16);
		frame.getContentPane().add(lblIp);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(66, 475, 106, 19);
		frame.getContentPane().add(nameField);
		
		lblName = new JLabel("nazwa:");
		lblName.setBounds(10, 475, 51, 13);
		frame.getContentPane().add(lblName);
		
		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(66, 503, 106, 19);
		frame.getContentPane().add(portField);
		
		lblPort = new JLabel("port:");
		lblPort.setBounds(10, 504, 41, 16);
		frame.getContentPane().add(lblPort);
		
		ipField = new JTextField();
		ipField.setText("localhost");
		ipField.setColumns(10);
		ipField.setBounds(238, 474, 106, 19);
		frame.getContentPane().add(ipField);
	}
	
	private void initializeImageLabels()
	{
		int id = 0;
		
		for( int x = 0; x < 5; x++ )
		{
			for( int y = 0; y < 5; y++)
			{
				imageLabels[id] = new JLabel(new ImageIcon("resource/bulb0.png"));
				imageLabels[id].setBounds(x * 70, 45+y*70, 70, 70);
				frame.getContentPane().add(imageLabels[id]);
				id++;
			}
		}
	}
}
