package data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import algo.Algo;
import support.IControlCenter;
import support.ISite;
import support.Mixer;

public class Site implements ISite 
{
	private String name;
	private Timer timer;
	private MixerContainer mixer;
	private IControlCenter ic;
	
	private int[] colors = new int[25];
	private List<Algo> mixers = new ArrayList<Algo>();
	private JLabel[] imageLabels = new JLabel[25];
	
	private int time = 0;
	private int ide = 0;
	private int port;
	private int algoNumber = 0;
	private Boolean alreadyStarted = false;
	

	public Site(String name, int port) 
	{
		this.name = name;
		this.port = port;
	}
	
	public Boolean addTo(String ip)
	{
		try 
		{
			Registry reg = LocateRegistry.getRegistry(ip, 3000);
			ic = (IControlCenter) reg.lookup("ControlCenter");
			ISite is = (ISite) UnicastRemoteObject.exportObject(this, port);
			
			if(ic.add(is) == false)
			{
				return false;
			}
			else
			{
				return true;
			}
		} 
		catch (RemoteException | NotBoundException e)
		{
			System.out.println("Nie dodano mixera do IC");
		}
		return false;
	}

	@Override
	public void start() throws RemoteException {
		
		if( alreadyStarted == false && algoNumber != 0)//protect from turn timer twice
			initializeTimer();
		else if( alreadyStarted == true)
			timer.start();
	}

	@Override
	public void stop() throws RemoteException 
	{
		if( alreadyStarted == true)
			timer.stop();
	}

	@Override
	public void setMixer(Mixer m) throws RemoteException 
	{
		this.mixer = (MixerContainer) m;
		mixers = mixer.getMixers();
		colors = mixer.getLightColor();
		algoNumber = mixer.getLength();
	}

	@Override
	public String getName() throws RemoteException 
	{
		return this.name;
	}

	public void linkImages(JLabel[] imageLabels ) 
	{
		this.imageLabels = imageLabels;
	}
	
	public void initializeTimer()
	{
		int delay = 250;
		
		ActionListener taskPerformer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				lightsUpdater();
			}
		};
		
		timer = new Timer(delay, taskPerformer);
		timer.start();
		alreadyStarted = true;
	}
	
	private void updateImages(int[] color)
	{
		
		for( int i = 0; i < 25; i++)
		{
			switch (color[i]) 
			{
				case 0:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb0.png"));
					break;
				case 1:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb1.png"));
					break;
				case 2:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb2.png"));
					break;
				case 3:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb3.png"));
					break;
				case 4:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb4.png"));
					break;
				case 5:
					imageLabels[i].setIcon(new ImageIcon("resource/bulb5.png"));
					break;
				default:
					break;
			}
		}
	}
	
	private void lightsUpdater()
	{
		mixers.get(ide).mix(colors);
		updateImages(colors);
		
		time+=250;
		if(time == mixers.get(ide).getTime())
		{
			mixers.get(ide).sync(colors);
			ide++;
			time = 0;
			if(ide+1 > mixers.size())
			{
				ide = 0;
			}
		}
	}
	
	public Boolean closeSignal() throws RemoteException 
	{
		return ic.remove((ISite)this);
	}
}
