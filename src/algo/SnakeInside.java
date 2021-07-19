package algo;

public class SnakeInside extends Algo
{
	private static final long serialVersionUID = 15L;
	
	private int[] algo = {0,1,2,3,4,9,14,19,24,23,22,21,20,15,10,5,6,7,8,13,18,17,16,11,12};

	public SnakeInside(String name, int time) 
	{
		super(name,time);
		nextStep = 1;
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
			lightings[j] = 0;
		}
		
		for( int j = 0; j < nextStep; j++)
		{
			lightings[algo[nextStep]] = copy[algo[nextStep]];
		}
		
		if( nextStep+1 < 25 )
			nextStep++;
		else
			nextStep = 1;
	}
}
