# Table of contents
* [General info](#general-info)
* [Built With](#built-with)
* [Program structure](#program-structure)
* [Designer](#designer)
* [Site](#site)
* [Manager](#manager)
* [Running site](#running-site)
* [Add Site](#add-site)
* [Designer](#designer)
* [Connect manager](#connect-manager)
* [Add mixer](#add-mixer)
* [License](#license)

# About The Project

## General info

<p class="text-justify">
The program is used to operate a system of lights placed at various locations around the city. It consists of a Designer used to select colors and a sequence of lights. Site represents the display location and Manager connects the created Mixers to the display locations.
</p>

## Built With

* [JAVA](https://www.java.com/pl/)

## Program structure
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/983dcd27f293eb66c7e13cba7182865962603e2b/Illuminations.png" width = "475"/>

## Designer
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/designer.png" width = "600"/>

## Site
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/site.png" width = "300"/>

## Manager
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/manager.png" width = "500"/>

## Running site
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/video.gif" width = "300"/>

## Add Site

```java
public Boolean addTo(String ip)
{
    try 
    {
        Registry reg = LocateRegistry.getRegistry(ip, 3000);
        ic = (IControlCenter) reg.lookup("ControlCenter");
        ISite is = (ISite) UnicastRemoteObject.exportObject(this, port);
        
        if(!ic.add(is))
            return false;
        else
            return true;
    } 
    catch (RemoteException | NotBoundException e)
    {
        System.out.println("Nie dodano mixera do IC");
    }
    return false;
}
```

## Connect manager

```java
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
```

## Add mixer

```java
public class Designer 
{
	public Boolean addMixer( Mixer mixer, String ip )
	{
		try 
		{
			Registry reg = LocateRegistry.getRegistry(ip, 3000);
			IControlCenter ic = (IControlCenter) reg.lookup("ControlCenter");
			
			if(!ic.add(mixer))
				return false;
			else
				return true;
		} 
		catch (RemoteException | NotBoundException e) 
		{ 
			System.out.println("Nie dodano mixera do IC");
		}
		return false;
	}
}
```

## License

Distributed under the Apache-2.0 License.

<p align="right">(<a href="#top">back to top</a>)</p>
