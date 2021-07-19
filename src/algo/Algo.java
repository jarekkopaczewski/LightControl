package algo;

import support.Mixer;

public abstract class Algo extends Mixer
{
	protected int time;
	protected boolean firstTime;
	protected int nextStep;
	protected int[] copy;

	private static final long serialVersionUID = 112L;

	public Algo(String name, int time) 
	{
		super(name);
		this.time = time;
	}
	
	public void sync(int[] lightings)
	{
		for( int j = 0; j < 25; j++)
		{
			lightings[j] = copy[j];
		}
	}

	public int getTime()
	{
		return this.time;
	}
	

	
}
