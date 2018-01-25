package model;

public interface Astar {
	abstract double heuristic(Coordinate current, Coordinate goal);
	abstract String heuristic();
}