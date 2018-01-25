package model;

import model.Search.Vertex;

public class SequentialAstar extends Search {
	
	@Override
	public boolean path(Coordinate start, Coordinate goal, double weight)
	{
		Search s_search = new Search(); 
		EuclidianSearch e_search = new EuclidianSearch(); 
		ChebyshevDistance ch_search = new ChebyshevDistance();
		Search[] SearchObjects = {s_search, e_search, ch_search};
		
		/*
		 * Idea: each type of heuristic search uses its own, separate priority queue.
		 * "Therefore, in addition to the different h values, each state uses a
		 *  different g value for each search."
		 *  etc etc
		 */
		
		// Copied from regular A*
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

}
