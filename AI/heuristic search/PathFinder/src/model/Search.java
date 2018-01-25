package model;
import java.util.*;
import model.Map;

public class Search implements Astar {
	
	private Map workingmap;
	protected Coordinate start;
	protected Coordinate goal;
	protected double weight;
	
    PriorityQueue<Vertex> fringe = new PriorityQueue<Vertex>(10);
	HashSet<Vertex> closed = new HashSet<Vertex>();
	
	public void loadmap(Map map)
	{
		this.workingmap = map;
	}
	
	public boolean path(Coordinate start, Coordinate goal, double weight)
	{
		boolean success = false;
		this.start = start;
		this.goal = goal;
		this.weight = weight;
		
		fringe.add(new Vertex(start, new Vertex(start, null)));
		System.out.println("Startpoint FGH: " + fringe.element().FGH(start.x, start.y));
		
		for(Vertex target = fringe.poll(); target != null; target = fringe.poll())
		{
			if (target.loc.equals(goal))
			{
				int pathlength = PrintPath(target);
				System.out.println("Path of length " + pathlength + " found between " + start + " and " + goal);
				System.out.println("Endpoint FGH: " + target.FGH(goal.x, goal.y));
				success = true;
				break;
			}
			else
			{
				if (!closed.contains(target))
				{
					closed.add(target);
					for (Vertex possiblenext : target.successors())
					{
						if (!closed.contains(possiblenext))
						{
							fringe.add(possiblenext);
						}
					}
				}
			}
			
			if (fringe.size() > 123456)
			{
				/* This is just so that it doesn't freeze up, although it does approach
				 * this number sometimes. Amit writes that if the open queue is over
				 * 10,000 it would be better to use "a bucketing system". Todo.
				 */
				System.out.print("Fringe got too big, so exiting. ");
				break;
			}
		}
		
		if (!success)
		{
			System.out.println("Could not find a path between " + start + " and " + goal);
		}
		
		System.out.println("Explored " + closed.size() + " nodes.");
		closed.removeAll(closed);
		
		return success;
	}
	
	public int PrintPath(Vertex endgoal)
	{
		int pathlength = 0;
		
		if (endgoal != null)
		{
			for(Vertex curr = endgoal; curr != null; curr = curr.parent)
			{
				pathlength++;
				System.out.print(curr.loc + " <- ");
			}
			System.out.println();
		}
		
		return pathlength;
	}
	
	public class Vertex implements Comparable<Vertex>
	{
		private double h;
		public double f, g;
		Coordinate loc;
		private Vertex parent;
		
		public ArrayList<Double> FGH(int x, int y)
		{
			ArrayList<Double> result = new ArrayList<Double>(3);
			//Buggy...
			
			for(Vertex curr = this; curr != null; curr = curr.parent)
			{
				if ((curr.loc.x == x) && (curr.loc.y == y))
				{
					result.add(new Double(this.f));
					result.add(new Double(this.g));
					result.add(new Double(this.h));
					break;
				}
			}
			
			return result;
		}
		
		Vertex(Coordinate loc, Vertex parent)
		{
			this.loc = loc;
			this.parent = parent;
			
			if (parent != null) {
				this.g = parent.g + cost(this.loc, this.parent.loc);
			} else {
				this.g = 0;
			}
			
			this.h = heuristic(this.loc, goal);
			this.f = g + weight*h;		
		}
		
		Vertex(int x, int y, Vertex parent)
		{
			this(new Coordinate(x, y), parent);
		}
		
		@Override
		public int compareTo(Vertex anotherVertex) {
			if (this.f < anotherVertex.f) return -1;
			else if (this.f > anotherVertex.f) return 1;
			else { // f values are equal 
				if (this.g < anotherVertex.g) return -1;
				else if (this.g > anotherVertex.g) return 1;
				else {
					//if (!this.loc.equals(anotherVertex.loc))
					//	System.out.println("Tie between " + this + " and " + anotherVertex);
					
					return 0;
				}
			}
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o != null && (o instanceof Vertex))
			{
				Vertex compareto = (Vertex)o;
				if (this.loc.equals(compareto.loc))
				{
					if ((this.parent != null) && (compareto.parent != null))
					{
						return (this.parent.loc.equals(compareto.parent.loc) == true);
					}
				}
			}
			
			return false;
		}
		
		@Override
	    public int hashCode()
	    {
			if (this.parent != null) {
				return Objects.hash(this.loc.x, this.loc.y, this.parent.loc.x, this.parent.loc.y);
			} else {
				return Objects.hash(this.loc.x, this.loc.y);
			}
	    }

		
		public ArrayList<Vertex> successors()
		{
			ArrayList<Vertex> successors = new ArrayList<Vertex>();
			int myx = this.loc.x, myy = this.loc.y;
			
			//Respectively: nw, north, ne, west, east, southwest, south, southeast
			int[] tryXs = { myx-1, myx, myx+1, myx-1, myx+1, myx-1, myx, myx+1 };
			int[] tryYs = { myy+1, myy+1, myy+1, myy, myy, myy-1, myy-1, myy-1 };
			
			for(int i = 0; i < tryXs.length; i++)
			{
				if (workingmap.getCellType(tryXs[i], tryYs[i]) > 0)
				{
					successors.add(new Vertex(tryXs[i], tryYs[i], this));
				}
			}
			
			return successors;
		}
		
		public String toString()
		{
			return "(" + this.loc.x + ", " + this.loc.y + ") type " + workingmap.getCellType(this.loc.x, this.loc.y) + " from "
					+ ((this.parent == null) ? "null" : this.parent.loc + " type " + workingmap.getCellType(this.parent.loc.x, this.parent.loc.y))
					+ " with f = " + this.f + ", g = " + this.g + ", h = " + this.h;
		}
	}
	
	public double heuristic(Coordinate a, Coordinate b) //Manhattan
	{
		return (Math.abs(b.x - a.x) + Math.abs(b.y - a.y))/4;
	}
	
	public String heuristic()
	{
		if (this instanceof EuclidianSearch)
		{
			return "Euclidian";
		}
		else if (this instanceof ChebyshevDistance)
		{
			return "Chebyshev";
		}
		
		return "Manhattan";
	}
	
	private double cost(Coordinate a, Coordinate b)
	{
		int ctA = workingmap.getCellType(a.x, a.y);
		int ctB = workingmap.getCellType(b.x, b.y);
		double cost = -1;
		
		boolean diagonal = ((Math.abs(a.x - b.x) == 1) && (Math.abs(a.y - b.y) == 1));
		
		switch(ctA)
		{
		case Map.UNBLOCKED:
			if ((ctB == Map.UNBLOCKED) || (ctB == Map.UNBLOCKEDHIGHWAY)) { cost = diagonal ? Math.sqrt(2) : 1; }
			if ((ctB == Map.HARD) || (ctB == Map.HARDHIGHWAY)) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : 1.5; }
			break;
		case 2:
			if ((ctB == Map.UNBLOCKED) || (ctB == Map.UNBLOCKEDHIGHWAY)) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : 1.5; }
			if ((ctB == Map.HARD) || (ctB == Map.HARDHIGHWAY)) { cost = diagonal ? Math.sqrt(8) : 2; }
			break;
		case 3:
			if (ctB == Map.UNBLOCKED) { cost = diagonal ? Math.sqrt(2) : 1; } 
			if (ctB == Map.HARD) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : 1.5; }
			if (ctB == Map.UNBLOCKEDHIGHWAY) { cost = diagonal ? Math.sqrt(2) : 0.25 ;}
			if (ctB == Map.HARDHIGHWAY) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : .0375; }
			break;
		case 4:
			if (ctB == Map.UNBLOCKED) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : 1.5;} 
			if (ctB == Map.HARD) { cost = diagonal ? Math.sqrt(8) : 2; }
			if (ctB == Map.UNBLOCKEDHIGHWAY) { cost = diagonal ? (Math.sqrt(2)+Math.sqrt(8))/2 : .0375; }
			if (ctB == Map.HARDHIGHWAY) { cost = diagonal ? Math.sqrt(8) : 0.5; }
			break;
		default:
			System.out.println("Cannot calculate cost function from " + a + " to " + b + " so exiting.");
			System.exit(0);
		}
		
		return cost;
	}
	
}