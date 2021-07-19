package algo;

public class ColorWave extends Algo
{
	private static final long serialVersionUID = 12L;
	
	private int nextColor = 1;
	private int[][] algo = {{0},{1,5},{2,6,10},{3,7,11,15},{4,8,12,16,20},{9,13,17,21},{14,18,22},{19,23},{24}};

	public ColorWave(String name, int time) 
	{
		super(name,time);
		firstTime = true;
		nextStep = 1;
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
		
		for( int i = 0; i < algo[nextStep].length; i++)
		{
			lightings[algo[nextStep][i]] = nextColor;
		}
		
		if( nextColor+1 > 5 )
			nextColor = 1;
		else
			nextColor++;
		
		if( nextStep+1 > 8 )
			nextStep = 1;
		else
			nextStep++;
	}
}