package data;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import support.IControlCenter;
import support.Mixer;

public class Designer 
{
	public Boolean addMixer( Mixer mixer, String ip )
	{
		try 
		{
			Registry reg = LocateRegistry.getRegistry(ip, 3000);
			IControlCenter ic = (IControlCenter) reg.lookup("ControlCenter");
			
			if(ic.add(mixer) == false)
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
}
