package model;

public class CoarseMapSearch extends Search {
	
	private boolean zerothrun = true;
	
	private int cellOverlay[][] = new int[15][20];
	//5th algorithm would be finer [30][40]
	
	private void precomputeCOcosts()
	{
		/* Idea: divide the 120x160 map into 15x20 mini-squares,
		 * where each cell takes the average of the "detailed"
		 * grid. But rounded to the nearest "legible" value 
		 * to calculate the distance.
		 */
		
		/*for(int i=0; i<cellType.length; i++)
		{
			for(int j=0; j<cellType[0].length; j++)
			{
				cellType[i][j] = value;
			}
		}*/
	}
	
	@Override
	public double heuristic(Coordinate a, Coordinate b)
	{
		double result = -1;
		
		if (zerothrun)
		{
			zerothrun = false;
		}
		
	    return result;
	}
	
	@Override
	public String heuristic()
	{
		return "Coarse map";
	}

}
