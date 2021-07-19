package algo;

public class HalfStepBlinker extends Algo 
{
	private static final long serialVersionUID = 13L;
	
	private boolean step;

	public HalfStepBlinker(String name, int time) 
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
				if( j%2 == 0)
					lightings[j] = copy[j];
				else
					lightings[j] = 0;
			}
			step = true;
		}
		else if( step == true )
		{
			for( int j = 0; j < 25; j++)
			{
				if( j%2 == 1)
					lightings[j] = copy[j];
				else
					lightings[j] = 0;
			}
			step = false;
		}
	}
}
