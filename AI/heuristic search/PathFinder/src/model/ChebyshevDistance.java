package model;

public class ChebyshevDistance extends Search {
	
	@Override
	public double heuristic(Coordinate a, Coordinate b)
	{
		double[] X = {a.x, b.x};
		double[] Y = {a.y, b.y};
		
	    int count = 2;
	    double[] newData = new double[count];
	    for (int i = 0; i < count; i++)
	    {
	        newData[i] = Math.abs(X[i] - Y[i]);
	    }
	    double max = Double.MIN_VALUE;
	    for (double num : newData)
	    {
	        if (num > max)
	        {
	            max = num;
	        }
	    }
	    return max;
    }
	
	@Override
	public String heuristic()
	{
		return "Chebyshev";
	}
}
