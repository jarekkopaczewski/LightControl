# Table of contents
* [General info](#general-info)
* [Data structure](#data-structure)
* [Built With](#built-with)
* [Save parameters](#save-parameters)
* [Prepare to play](#prepare-to-play)
* [License](#license)

# About The Project

## General info

<p class="text-justify">
This project is a simple VST3 delay effect that works separately on the left and right channel. You can use different delay time on left and right channel, which can be also synchronized to tempo of the song by setting rhythmic division.   There is also a bunch of additional effect like tap echo or flanger.
</p>

## Program structure
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/983dcd27f293eb66c7e13cba7182865962603e2b/Illuminations.png" width = "450"/>

## Designer
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/designer.png" width = "600"/>

## Site
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/site.png" width = "300"/>

## Manager
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/manager.png" width = "500"/>

## Running site
<img src = "https://github.com/jarekkopaczewski/LightControl/blob/6477d28369ab28a2cc142aa37db4a43fa97ef058/video.gif" width = "300"/>

## Built With

* [JAVA](https://www.java.com/pl/)

## Save parameters

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

## License

Distributed under the MIT License.

<p align="right">(<a href="#top">back to top</a>)</p>
