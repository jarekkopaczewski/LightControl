package data;

import java.util.ArrayList;
import java.util.List;

import algo.Algo;
import algo.Arrows;
import algo.Blinker;
import algo.ColorWave;
import algo.HalfStepBlinker;
import algo.Sequencer;
import algo.SnakeInside;
import algo.SnakeOutside;
import support.Mixer;

public class MixerContainer extends Mixer
{
	private static final long serialVersionUID = 30L;
	
	private List<Algo> mixers = new ArrayList<Algo>();
	private int[] lightings = new int[25];

	public MixerContainer(String name) 
	{
		super(name);
	}

	@Override
	public void mix(int[] lightings) 
	{	
	}
	
	public void setLights(int[] lightings)
	{
		this.lightings = lightings;
	}
	
	public void addMixer( int id, int time )
	{	
		switch (id) 
		{
			case 0:
				mixers.add( new Blinker(this.getName(), time));
				System.out.println("Dodano blinker z czasem " + time);
				break;
			case 1:
				mixers.add( new HalfStepBlinker(this.getName(), time));
				System.out.println("Dodano half step blinker z czasem " + time);
				break;
			case 2:
				mixers.add( new Sequencer(this.getName(), time));
				System.out.println("Dodano sequencer blinker z czasem " + time);
				break;
			case 3:
				mixers.add( new Arrows(this.getName(), time));
				System.out.println("Dodano strzalki z czasem " + time);
				break;
			case 4:
				mixers.add( new SnakeInside(this.getName(), time));
				System.out.println("Dodano snake inside z czasem " + time);
				break;
			case 5:
				mixers.add( new SnakeOutside(this.getName(), time));
				System.out.println("Dodano snake outside z czasem " + time);
				break;
			case 6:
				mixers.add( new ColorWave(this.getName(), time));
				System.out.println("Dodano snake outside z czasem " + time);
				break;
			default:
				break;
		}
	}
	
	public List<Algo> getMixers()
	{
		return mixers;
	}
	
	public int[] getLightColor()
	{
		return lightings;
	}
	
	public int getLength()
	{
		return mixers.size();
	}

}
