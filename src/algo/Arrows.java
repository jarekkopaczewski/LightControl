package algo;

public class Arrows extends Algo
{
	private static final long serialVersionUID = 10L;
	
	private int[] algo = {0,6,2,8,4};

	public Arrows(String name, int time) 
	{
		super(name,time);
		firstTime = true;
		copy = new int[25];
	}
	
	@Override
	public void mix(int[] lightings)
	{
		if( firstTime == true )//copy for the first time
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
		
		for( int j = 0; j < 5; j++)
		{
			lightings[algo[j]] = copy[algo[j]];
			
			if( algo[j]+5 > 24)
			{
				algo[j] = (algo[j]+5) - 25;
			}
			else
			{
				algo[j]+=5;
			}
		}
	}
}