package support;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import support.Mixer;

public interface IControlCenter extends Remote {
	// returns false if the site has been already added
	public Boolean add(ISite is) throws RemoteException;

	// returns false if the site has been not found
	public Boolean remove(ISite is) throws RemoteException;

	// returns false if the mixer has been already added
	public Boolean add(Mixer m) throws RemoteException;

	// returns false if the site has been not found
	public Boolean remove(Mixer m) throws RemoteException;

	public void subscribe(IManager im)throws RemoteException;

	// returns all added sites
	List<ISite> getAllSites() throws RemoteException;

	// returns all added mixers
	List<Mixer> getAllMixers() throws RemoteException;

}
