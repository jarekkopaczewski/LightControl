package data;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import support.IControlCenter;
import support.IManager;
import support.ISite;
import support.Mixer;

public class Manager implements IManager 
{
	private IControlCenter ic;
	private List<ISite> sites = new ArrayList<ISite>();
	private List<Mixer> mixers = new ArrayList<Mixer>();
	private DefaultTableModel sitesModel;
	private DefaultTableModel mixersModel;
	private JTable mixersTable;
	private JTable sitesTable;
	
	public Manager()
	{
		
	}
	
	public void connect(String ip, int port)
	{
		try 
		{
			Registry reg = LocateRegistry.getRegistry(ip ,3000);
			this.ic = (IControlCenter) reg.lookup("ControlCenter");
			
			IManager im = (IManager) UnicastRemoteObject.exportObject(this, port);
			System.out.println("Manager is ready");
			
			ic.subscribe(im);
		} 
		catch (RemoteException | NotBoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void notify(ISite is, Boolean s) throws RemoteException 
	{
		if( s == true )//add
		{
			sites = ic.getAllSites();//update sites
			String[] rows = {is.getName()};//add current site directly to table in GUI
			sitesModel.addRow(rows);
			sitesTable.setModel(sitesModel);
		}
		else if( s == false)//remove
		{
			sites = ic.getAllSites();
			sitesModel = (new DefaultTableModel(new Object[][] {},new String[] {"Nazwa site"}));
			
			for( int i = 0; i < sites.size(); i++)
			{
				String[] rows = {sites.get(i).getName()};
				sitesModel.addRow(rows);
			}
			sitesTable.setModel(sitesModel);
		}
	}

	@Override
	public void notify(Mixer mi, Boolean s) throws RemoteException 
	{
		if( s == true )//add
		{
			mixers = ic.getAllMixers();//update mixers
			String[] rows = {mi.getName()};//add current mixer directly to table in GUI
			mixersModel.addRow(rows);
			mixersTable.setModel(mixersModel);
		}
		else if( s == false)//delete
		{
			
		}
	}
	
	public void onCreate() throws RemoteException, NullPointerException//je�eli manager jest utworzony po control centre pr�buje wczyta� dane je�eli kto� je wcze�niej doda�
	{
		sites = ic.getAllSites();
		
		for( int i = 0; i < sites.size(); i++)
		{
			String[] rows = {sites.get(i).getName()};
			sitesModel.addRow(rows);
		}
		
		sitesTable.setModel(sitesModel);
		
		mixers = ic.getAllMixers();
		
		for( int i = 0; i < mixers.size(); i++)
		{
			String[] rows = {mixers.get(i).getName()};
			mixersModel.addRow(rows);
		}
		
		mixersTable.setModel(mixersModel);
	}
	
	public void linkModels(DefaultTableModel sitesModel, DefaultTableModel mixersModel, JTable sitesTable, JTable mixersTable)
	{
		this.sitesModel = sitesModel;
		this.sitesTable = sitesTable;
		this.mixersModel = mixersModel;
		this.mixersTable = mixersTable;
	}
	
	public void connectElements(int mixerID, int siteID)
	{
		try 
		{
			sites.get(siteID).setMixer(mixers.get(mixerID));
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void startIlumination(int siteID)
	{
		try 
		{
			sites.get(siteID).start();
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void stopIlumination(int siteID)
	{
		try 
		{
			sites.get(siteID).stop();
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Boolean deleteSite(int id)
	{
		try 
		{
			if(ic.remove(sites.get(id)))
			{
				return true;
			}
		}
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteMixer(int id)
	{
		try 
		{
			if(ic.remove(mixers.get(id)))
			{
				return true;
			}
		}
		catch (RemoteException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
}
