package support;

import java.rmi.Remote;
import java.rmi.RemoteException;

import support.Mixer;

public interface IManager extends Remote {
	// informs about adding (s=true) or removal (s=false) of ISite
	public void notify(ISite is, Boolean s) throws RemoteException;
	// informs about adding (s=true) or removal (s=false) of Mixer
	public void notify(Mixer mi, Boolean s) throws RemoteException;
}
