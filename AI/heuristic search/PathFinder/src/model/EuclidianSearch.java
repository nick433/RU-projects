package model;

public class EuclidianSearch extends Search {
	
	@Override
	public double heuristic(Coordinate a, Coordinate b)
	{
	    double deltaX = a.y - b.y;
	    double deltaY = a.x - b.y;
	    double result = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	    
	    return result;
	}
	
	@Override
	public String heuristic()
	{
		return "Euclidian";
	}
}
