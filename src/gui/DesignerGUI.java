package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import data.Designer;
import data.MixerContainer;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ItemListener;
import java.util.Random;
import java.awt.event.ItemEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class DesignerGUI {

	private JFrame frame;
	private JTable patternsTable;
	private JComboBox<String>[] bulbes = new JComboBox[25];
	private JComboBox<String> patternBox;
	
	private DefaultComboBoxModel<String>[] boxModel = new DefaultComboBoxModel[25];
	private DefaultTableModel model;
	
	private JLabel delayLabel;
	private JLabel titleLabel;
	private JLabel typeLabel;
	private JLabel lblNazwaMixera;
	
	private JScrollPane scrollPane;
	private JButton saveButton;
	private JButton addButton;
	private JButton normalizeButton;
	private JButton randomButton;
	private JButton createButton;
	private JSpinner delayBox;
	private Random rnd = new Random();
	private MixerContainer mixer;
	private Designer designer = new Designer();
	
	private int[] lights = new int[25];
	private JTextField mixerName;
	private JTextField ipField;
	private JLabel lblIp;
	

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DesignerGUI window = new DesignerGUI();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public DesignerGUI() {
		initialize();
	}

	private void initialize() 
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 838, 298);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//models
		model = (new DefaultTableModel(new Object[][] {},new String[] {"Czas trwania", "Nazwa paternu"}));
		
		//table & scroll pane
		patternsTable = new JTable(model);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(562, 94, 250, 117);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(patternsTable);
		
		//others
		patternBox = new JComboBox<String>();
		patternBox.setBackground(Color.WHITE);
		patternBox.setModel(new DefaultComboBoxModel<String>(new String[] {"miganie", "miganie naprzemienne", "po kolei", "strzalki", "od srodka", "do srodka", "kolorowa fala"}));
		patternBox.setBounds(672, 70, 140, 21);
		frame.getContentPane().add(patternBox);
		
		delayBox = new JSpinner();
		delayBox.setModel(new SpinnerNumberModel(1000, 500, 10000, 250));
		delayBox.setBounds(562, 71, 108, 20);
		frame.getContentPane().add(delayBox);

		mixerName = new JTextField();
		mixerName.setBounds(120, 26, 100, 38);
		frame.getContentPane().add(mixerName);
		mixerName.setColumns(10);
		
		//combo boxes
		initializeBulbesArray();
		
		//labels
		initializeLabels();
		
		//buttons
		initializeButtons();
		
		//buttons listeners
		initializeButtonsListeners();
	}
	
	private void initializeLabels()
	{
		typeLabel = new JLabel("Rodzaj paternu");
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setBounds(672, 48, 140, 21);
		frame.getContentPane().add(typeLabel);
		
		delayLabel = new JLabel("Interwa\u0142 zmiany");
		delayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		delayLabel.setBounds(562, 48, 100, 21);
		frame.getContentPane().add(delayLabel);

		titleLabel = new JLabel("Designer");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		titleLabel.setBounds(221, 10, 242, 61);
		frame.getContentPane().add(titleLabel);
		
		lblNazwaMixera = new JLabel("Nazwa mixera");
		lblNazwaMixera.setHorizontalAlignment(SwingConstants.CENTER);
		lblNazwaMixera.setBounds(120, 0, 100, 27);
		frame.getContentPane().add(lblNazwaMixera);
	}
	
	private void initializeButtons()
	{
		addButton = new JButton("Dodaj sekwencje");
		addButton.setEnabled(false);
		addButton.setBounds(672, 213, 140, 38);
		frame.getContentPane().add(addButton);
		
		saveButton = new JButton("Zapisz mixer");
		saveButton.setEnabled(false);
		saveButton.setBounds(562, 213, 108, 38);
		frame.getContentPane().add(saveButton);
		
		normalizeButton = new JButton("Jednolity kolor");
		normalizeButton.setEnabled(false);
		
		normalizeButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		normalizeButton.setBounds(562, 10, 108, 38);
		frame.getContentPane().add(normalizeButton);
		
		randomButton = new JButton("Losowy uk\u0142ad");
		randomButton.setEnabled(false);
		randomButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		randomButton.setBounds(672, 10, 140, 38);
		frame.getContentPane().add(randomButton);
		
		createButton = new JButton("Utw\u00F3rz mixer");
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		createButton.setBounds(10, 25, 100, 38);
		frame.getContentPane().add(createButton);
		
		ipField = new JTextField();
		ipField.setText("localhost");
		ipField.setColumns(10);
		ipField.setBounds(442, 26, 108, 38);
		frame.getContentPane().add(ipField);
		
		lblIp = new JLabel("Ip");
		lblIp.setHorizontalAlignment(SwingConstants.CENTER);
		lblIp.setBounds(442, 0, 108, 27);
		frame.getContentPane().add(lblIp);
	}
	
	private void initializeButtonsListeners()
	{
		randomButton.addActionListener(new ActionListener() //random color for combo boxes
		{
			public void actionPerformed(ActionEvent e) 
			{
				for( int i = 0; i < 25; i++ )
				{
					bulbes[i].setSelectedIndex((rnd.nextInt(4 + 1) + 1));
					refreshLightsArray();
				}
			}
		});
		
		createButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if( mixerName.getText().isEmpty() == true )
				{
					JOptionPane.showMessageDialog(frame, "Mixer musi mieæ nazwê!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					refactorElements(false,true);
					mixer = new MixerContainer(mixerName.getText().toString());
				}
			}
		});
		
		normalizeButton.addActionListener(new ActionListener()// set one color for every combo box
		{
			public void actionPerformed(ActionEvent e) 
			{
				for( int i = 1; i < 25; i++ )
				{
					bulbes[i].setSelectedIndex(lights[0]);
					refreshLightsArray();
				}
			}
		});
		
		saveButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				mixer.setLights(lights);
				
				if( model.getRowCount() > 0)
				{
					if(designer.addMixer(mixer,ipField.getText().toString()) == false)
					{
						JOptionPane.showMessageDialog(frame, "Mixer nie zosta³ dodany przez kolizjê nazaw!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
					}
					
					// reset options to first state
					refactorElements(true,false);


					mixerName.setText("");
					model = (new DefaultTableModel(new Object[][] {}, new String[] { "Czas trwania", "Nazwa paternu" }));
					patternsTable = new JTable(model);
					scrollPane.setViewportView(patternsTable);

					for (int i = 0; i < 25; i++)// clear colors
					{
						bulbes[i].setSelectedIndex(0);
					}
					refreshLightsArray();
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Mixer nie mo¿e byæ pusty!", "B³¹d danych", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		addButton.addActionListener(new ActionListener()//add mixer do mixersContainer
		{
			public void actionPerformed(ActionEvent e) 
			{
				model.addRow(
				new Object[] { delayBox.getValue().toString(), patternBox.getSelectedItem().toString() });
				patternsTable.setModel(model);
				mixer.addMixer(patternBox.getSelectedIndex(), Integer.parseInt(delayBox.getValue().toString()));
				 
			}
		});
	}
	
	private void refactorElements(Boolean how, Boolean how2)
	{
		//1 false // true
		mixerName.setEnabled(how);
		createButton.setEnabled(how);
		addButton.setEnabled(how2);
		saveButton.setEnabled(how2);
		normalizeButton.setEnabled(how2);
		randomButton.setEnabled(how2);
		
		//2 true// false
		mixerName.setEnabled(how);
		createButton.setEnabled(how);
		addButton.setEnabled(how2);
		saveButton.setEnabled(how2);
		normalizeButton.setEnabled(how2);
		randomButton.setEnabled(how2);
	}
	
	private void initializeBulbesArray()
	{
		int id = 0;
		
		for( int y = 0; y < 5; y++ )
		{
			for( int x = 0; x < 5; x++ )
			{
				boxModel[id] = new DefaultComboBoxModel<String>(new String[] {"wy³¹czona", "niebieski", "czerwony", "zielony", "\u017C\u00F3\u0142ty", "fioletowy"});
				
				bulbes[id] = new JComboBox<String>();
				bulbes[id].setBounds(10+(y*110), 70+(x*40), 100, 21);
				bulbes[id].setModel(boxModel[id]);
				bulbes[id].setBackground(Color.GRAY);

				frame.getContentPane().add(bulbes[id]);
				
				bulbes[id].addItemListener(new ItemListener() 
				{
					public void itemStateChanged(ItemEvent e) 
					{
						refreshLightsArray();
					}
				});
				
				id++;
			}
		}
	}
	
	private void refreshLightsArray()
	{
		for( int i = 0; i < 25; i++ )
		{
			lights[i] = bulbes[i].getSelectedIndex();
			
			switch(lights[i])
			{
				case 0:
					bulbes[i].setBackground(Color.GRAY);
					break;
				case 1:
					bulbes[i].setBackground(Color.BLUE);
					break;
				case 2:
					bulbes[i].setBackground(Color.RED);
					break;
				case 3:
					bulbes[i].setBackground(Color.GREEN);
					break;
				case 4:
					bulbes[i].setBackground(Color.YELLOW);
					break;
				case 5:
					bulbes[i].setBackground(Color.MAGENTA);
					break;
			}
		}
	}
}
