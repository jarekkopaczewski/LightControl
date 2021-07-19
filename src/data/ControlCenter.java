package data;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import support.IControlCenter;
import support.IManager;
import support.ISite;
import support.Mixer;

public class ControlCenter implements IControlCenter 
{
	private List<ISite> sites = new ArrayList<ISite>();
	private List<Mixer> mixers = new ArrayList<Mixer>();
	private IManager im;
	
	public static void main(String[] args) 
	{
		try 
		{
			Registry reg = LocateRegistry.createRegistry(3000);
			reg.rebind("ControlCenter", UnicastRemoteObject.exportObject(new ControlCenter(), 0));
			System.out.println("ControlCenter is ready");
		}
		catch (RemoteException e) 
		{
		}
	}

	@Override
	public Boolean add(ISite is) throws RemoteException 
	{
		if( checkForRepSite(is.getName()) == false)// check for repetition with existing sites
		{
			sites.add(is);
	        im.notify(is, true);
	        System.out.println("Dodano site o nazawie: " + is.getName());
	        return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Boolean remove(ISite is) throws RemoteException
	{
		for( int i = 0; i < sites.size(); i++)// find site with name equals to 'is'
		{
			if(is.getName().equals(sites.get(i).getName()))
			{
				is.stop();
				sites.remove(i);
				System.out.println("Usunieto site o nazawie: " + is.getName());
				im.notify((ISite)null, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean add(Mixer m) throws RemoteException 
	{
		if( checkForRepMix(m.getName()) == false )// check for repetition with existing mixers
		{
			mixers.add(m);
	        im.notify(m, true);
	        return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Boolean remove(Mixer m) throws RemoteException
	{
		for( int i = 0; i < mixers.size(); i++)// find mixer with name equals to 'm'
		{
			if(m.getName().equals(mixers.get(i).getName()))
			{
				mixers.remove(i);
				System.out.println("Usunieto mixer o nazawie: " + m.getName());
				im.notify((Mixer)null, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public void subscribe(IManager im) throws RemoteException 
	{
		this.im = im;
	}

	@Override
	public List<ISite> getAllSites() throws RemoteException 
	{
		return sites;
	}

	@Override
	public List<Mixer> getAllMixers() throws RemoteException 
	{
		return mixers;
	}
	
	private boolean checkForRepMix(String name)
	{
		System.out.println("name -> " + name);
		for(Mixer m: mixers)
		{
			System.out.println(m.getName());
			if( name.equals(m.getName()))
				return true;
		}
		return false;
	}
	
	private boolean checkForRepSite(String name)
	{
		for(ISite s: sites)
		{
			try 
			{
				if( name.equals(s.getName()))
					return true;
			} 
			catch (RemoteException e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}

}

