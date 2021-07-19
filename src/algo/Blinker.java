package algo;

public class Blinker extends Algo 
{
	private static final long serialVersionUID = 11L;
	
	private boolean step;

	public Blinker(String name, int time) 
	{
		super(name,time);
		this.step = false;
		firstTime = true;
		copy = new int[25];
	}
	
	@Override
	public void mix(int[] lightings)
	{
		if( firstTime == true )
		{
			for( int j = 0; j < 25; j++)
			{
				copy[j] = lightings[j];
			}
			firstTime = false;
		}
		
		if( step == false )
		{
			for( int j = 0; j < 25; j++)
			{
				lightings[j] = copy[j];
			}
			step = true;
		}
		else if( step == true )
		{
			for( int j = 0; j < 25; j++)
			{
				lightings[j] = 0;
			}
			step = false;
		}
	}
}
