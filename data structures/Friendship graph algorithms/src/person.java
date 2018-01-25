public class person{
	public String name;
	public String school;
	public neighbor front;
	public boolean visited = false;
	public int index;
	public String raw;
	
	public int numNeighbors;
	//public int schoolIndex;
	//public int back;
	//public int dfs;

	public person(String name, String school, neighbor front, boolean visited, 
			int index, String raw/* , int schoolIndex, int back, int dfs */ ){	//ArrayList<Person> friendList 
		this.name = name;
		this.school = school;
		this.front= front;
		this.visited = visited;
		this.index = index;
		this.raw = raw;
		numNeighbors = 0;
	//	this.schoolIndex = schoolIndex;
	}
}