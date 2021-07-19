package algo;

public class Sequencer extends Algo 
{
	private static final long serialVersionUID = 14L;
	
	public Sequencer(String name, int time) 
	{
		super(name,time);
		nextStep = 0;
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
		
		for( int j = 0; j < 25; j++)
		{
			if( j == nextStep )
			{
				lightings[j] = copy[j];
			}
			else
			{
				lightings[j] = 0;
			}
		}
		
		if( (nextStep+1) < 25 )
			nextStep++;
		else
			nextStep = 0;
	}
}
