package algo;

public class SnakeOutside extends Algo
{
	private static final long serialVersionUID = 16L;
	
	private int[] algo = {12,11,16,17,18,13,8,7,6,5,10,15,20,21,22,23,24,19,14,9,4,3,2,1,0};

	public SnakeOutside(String name, int time) 
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