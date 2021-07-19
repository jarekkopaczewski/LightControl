package support;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISite extends Remote{
  // starts illumination	
  public void start() throws RemoteException;
  // stops illumination	
  public void stop() throws RemoteException;
  // sets the mixer
  public void setMixer(Mixer m) throws RemoteException;
  // delivers site name 
  public String getName() throws RemoteException; 
}
