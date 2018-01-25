import java.io.*;
import java.util.*;


/**
 * @author Nick Mangracina, Jade Yee -CS112
 *
 */
public class Friends{
	static BufferedReader br1, br2;
	static Scanner s = new Scanner(System.in);
	static Scanner stdin = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException{
		
		br1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the name of the friendship file => ");
		String input = br1.readLine();
		
		br2 = new BufferedReader(new FileReader(input));

		
		int count = Integer.parseInt(br2.readLine())+1;	
		String[] personList = new String[count-1];	
		int i = 0;
		while(i<count-1){			
			String line= br2.readLine();
			personList[i]= line;
			i++;
		}
		ArrayList<String> connectList = new ArrayList<String>();
		ArrayList<String> connectList2 = new ArrayList<String>();
		String line = br2.readLine();
		connectList.add(line);
		connectList2.add(line);
		while(line != null){
			line = br2.readLine();
			connectList.add(line);
			connectList2.add(line);
		}
		int a = 0;
		person[] people1 = new person[personList.length];
		person[] people2 = new person[personList.length];
		while(a < personList.length){
			people1[a] = makePerson(personList[a], a); //make person with many fields with just number and raw stuff
			a++;
		}
		int b = 0;
		while(b < personList.length){
			people2[b] = makePerson(personList[b], b); //make person with many fields with just number and raw stuff
			b++;
		}
		person[] people = new person[personList.length];
		person[] holder = new person[personList.length];
		people = graphBuild(people1, connectList);

	

		int choice = getChoice();
		loop: while (true) { 
				switch (choice) {
					case 1: // Shortest path
						System.out.print("Enter the name of the starting Person ");
						String start = stdin.nextLine();
						System.out.print("Enter the name of the target ");
						String target = stdin.nextLine();
					    shortestPath(start,target,people,people,people,connectList);
					    choice = getChoice();
						break;
					case 2: // Cliques
						System.out.print("Enter the name of the school "); 
						String school = stdin.nextLine();
						cliques(school,people,people,connectList);    // WTF
						connectList = connectList2;
						people = graphBuild(people1,connectList);
						return;
						//choice = getChoice();
						//break;
					case 3: // Connectors
						Connectors(people, people.length);    //ignore for now
						choice = getChoice();
						break;
					case 4:
						break loop;
						
					}
				
				
		} //end while
	} //end main
	
	public static int getChoice() throws IOException {
		System.out.println();
		System.out.println(1 + ". Shortest intro chain");
		System.out.println(2 + ". Cliques at school");
		System.out.println(3 + ". Connectors");
		System.out.println(4 + ". QUIT");
		System.out.println("Enter choice ");
		int choice = Integer.parseInt(br1.readLine());
		while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.print("\tYou must enter one of 1, 2, 3, or 4. Enter again");
            choice = stdin.next().toLowerCase().charAt(0);
        }
        return choice;
	}
	
	public static void shortestPath(String startS, String targetS, person[] people,
			person[] holder, person[] people2, ArrayList<String> connectList)throws IOException  {
		
		int start = -3;
		int target = -5;
		for(int i = 0;i<people.length;i++){
			if(holder[i].name.equals(startS)){
				start = people[i].index;
			}
		}
		if(start == -3){
			System.out.println("first person doesn't exist");
			throw new NoSuchElementException();
		}
		for(int i = 0;i<people.length;i++){ 
			if(holder[i].name.equals(targetS)){
				target = people[i].index; 
			}
		}
		if(start == -5){
			System.out.println("second person doesn't exist");
			throw new NoSuchElementException();
		}
		ArrayList<Integer> holdem = new ArrayList<Integer>();
		while(people2[target].front != null){
			holdem.add(people2[target].front.index);
			people2[target].front = people2[target].front.next;
		}
		
		ArrayList<Integer> nums = new ArrayList<Integer>();
		nums.add(start);
		int j = 1;
			try {
				while(start != target){
						
						while(holder[start].front != null){  //start may need to be a changing variable
							if(!nums.contains(holder[start].front.index)){
								nums.add(holder[start].front.index);
							}
							if(holdem.contains(holder[start].front.index)){
								nums.add(target);
								start = target;
							}
							if(start == target) {break; }
							holder[start].front = holder[start].front.next;
						}
					
						if(start != target){
							start = nums.get(j);
						}
						if(start == target){  //infinite. delete
							break;
						} 
						j++;
						if(j >nums.size()+12){ System.out.println(); break; }
				}
 
				int k = 1;
				people = graphBuild(people,connectList);
				ArrayList<Integer> finals = new ArrayList<Integer>();
				int size = nums.size()-1;
				while(true){
				if(nums.get(size) == target){
					finals.add(nums.get(size));
					break;
				}
				else{ size--; }
				}	
				while(size != 0){
				while(people[nums.get(size)].front != null && size>0){
					if(people[nums.get(size)].front.index == nums.get(size-k)){
						finals.add(nums.get(size-k));
						size = size-k;
						k = 1;
						if(finals.get(finals.size()-1) == start) break;
						continue;
					}
					people[nums.get(size)].front = people[nums.get(size)].front.next;
				}
				if(size!= -1) people = graphBuild(people,connectList);
				if(size == 0){ break; }
				k++;

				}

     
      ArrayList<Integer> otherFinal = new ArrayList<Integer>();
      ArrayList<Integer> otherFinal2 = new ArrayList<Integer>();
      graphBuild(people,connectList);
      graphBuild(holder,connectList);
      graphBuild(people2,connectList);
      otherFinal = shortestOther(targetS, startS, people,holder, people2,connectList);
      int h = otherFinal.size()-1;
      while(h>-1){
  	   otherFinal2.add(otherFinal.get(h));
  	   h--;
      }
      
      ArrayList<Integer> superfinal = new ArrayList<Integer>();
      if(finals.size() > otherFinal2.size()){
  	   superfinal = otherFinal2; 
      }
      else{ superfinal = finals; }
      String result = "";
      int n = superfinal.size()-1;
      while(n>-1){
  	   if(superfinal.get(k) == -1){n--; continue; }
  	   result = result + people[superfinal.get(n)].name + "--";
  	   n--;
      }
      
      result = result.substring(0, result.length()-2);
      System.out.println(result);
			} catch (Exception e) {
				System.out.println("No possible Path");
				
			}
    

       
    }


	
	        //construct the person that will go into the build method. will take an array 
			//or persons(school, index #, ect) and arraylist of neighbors for those people.
public static person[] graphBuild(person[] people, ArrayList<String> connectList){ //construct
		for(int i=0; i<people.length;i++){
			people[i].numNeighbors = 0;
		}
				
				
		for(int i=0; i<connectList.size()-1; i++){		//go through the friends
			String raw = connectList.get(i);
			String first = raw.substring(0, raw.indexOf('|'));	
			String second = raw.substring(raw.indexOf('|')+1);			
			neighbor firstIndex = new neighbor(0,null);
			neighbor secondIndex = new neighbor(0, null);
			boolean firstMatched = false;
			boolean secMatched =false;

			for(int j=0; j<people.length; j++){				//look for first and second name

				if (people[j].name.equalsIgnoreCase(first)){	//firstname found
					firstIndex.index=j;					//index = where the match is
					firstMatched=true;
				}
				if (people[j].name.equalsIgnoreCase(second)){
					secondIndex.index=j;
					secMatched=true;
				}	
				if(firstMatched && secMatched){
					neighbor temp1 = people[firstIndex.index].front;	
					neighbor temp2 = people[secondIndex.index].front;

					people[firstIndex.index].front = secondIndex;
					people[secondIndex.index].front = firstIndex;
					
					people[firstIndex.index].numNeighbors++;
					people[secondIndex.index].numNeighbors++;

					firstIndex.next = temp2;		//attaches first chain
					secondIndex.next = temp1;
					break;
				}
			}
		}	
		
		return people;
	}
	
static int curr;

public static void Connectors(person[] people, int size){
	
	Boolean[] visited = new Boolean[size];
	Boolean[] isConnector = new Boolean[size];
	
	
	for (int j = 0; j < size; j++) {
		visited[j] = false;
		isConnector[j] = false;
	}
	for (int i = 0; i < size - 1; i++) {

		Integer[] DFSNum = new Integer[size];
		Integer[] back = new Integer[size];
		
		for (int j = 0; j < size; j++) {
			DFSNum[j] = -1;
			back[j] = -1;
		}
		curr = 0;
		
		ConnectorRecursive(people[i], size, DFSNum, back, people, visited, isConnector);

	}
	
	for (int i = 0; i < size; i++) {
		if (isConnector[i] == true)
			System.out.print(people[i].name + ",");
	}
}


public static int ConnectorRecursive(person start, int size, Integer[] DFSNum, Integer[] back, person[] people, Boolean[] visited, Boolean[] isConnector) {
	
	DFSNum[start.index] = curr;
	back[start.index] = curr;
	
	curr++;
	
	//System.out.println("Start dfs at " + start.name);
	
	visited[start.index] = true;
	
	
	neighbor n = start.front;
	
	
	while (n != null) {
		
		// If a neighbor, w, is already visited then back(v) is set to min(back(v),dfsnum(w))
		if (visited[n.index] == true)
			back[start.index] = Math.min(back[start.index], DFSNum[n.index]);
		else {
			ConnectorRecursive (people[n.index], size, DFSNum, back, people, visited, isConnector);
			//System.out.println("DFS backed up");

			// When the DFS backs up from a neighbor, w, to v, if dfsnum(v) > back(w), then back(v) is set to min(back(v),back(w))
			if (DFSNum[start.index] > back[n.index])
				back[start.index] = Math.min(back[start.index], back[n.index]);

			// When the DFS backs up from a neighbor, w, to v, if dfsnum(v)  back(w), 
			// then v is identified as a connector, IF v is NOT the starting point for the DFS.
			//System.out.println("person:" + people[start.index].name + " numneighbors: " + people[start.index].numNeighbors);
			if (DFSNum[start.index] <= back[n.index] && !(DFSNum[start.index] == 0 && people[start.index].numNeighbors <= 1)) 
				isConnector[start.index] = true;
			
		}
		n = n.next;
		
	}

	return 0;
}


	public static void cliques(String school, person[] people, person[] people2, ArrayList<String> connectList){
		ArrayList<String> connections = new ArrayList<String>();
		ArrayList<String> cliqueConnect = new ArrayList<String>();
		ArrayList<person> clique = new ArrayList<person>();
		int cliquenum = 1;
		
		int i = 0;
		while(true){
			if(i == people.length){
				System.out.println("No cliques for this school");
				return;
			}
			if(people[i].school != null){
				if(people[i].school.equalsIgnoreCase(school)){
					break;
				}
			}	
			i++;
		}
		while(i < people.length){
			if(people[i].visited == true){
				i++;  //once it has been verified as not the school, it is unimportant
				continue;
			}
			people[i].visited = true;
			//System.out.println("school: "+ people[i].school + " name: " + people[i].name);
			if(people[i].school.equalsIgnoreCase(school)){
				
				clique = dfs(people[i],school,people);  //this will create the clique
				
				person[] cliqueArray = new person[clique.size()];
				
				if(clique.get(0) ==null){ System.out.println("no one in cliques"); }
				for(int a = 0;a<clique.size();a++){
					cliqueArray[a] = clique.get(a);   //moves items from clique to cliquearray so build can be used
				}
				System.out.println();
				System.out.println("Clique " + cliquenum + ":");
				System.out.println();
				//cliqueArray = graphBuild(cliqueArray,connectList);
				printGraph(cliqueArray);
				break;
			}
			i++;
		}
		person[] cliqueArray = new person[clique.size()];
		if(clique.get(0) == null){ System.out.println("no one in cliques"); }
		for(int a = 0;a<clique.size();a++){
			cliqueArray[a] = clique.get(a);   //moves items from clique to cliquearray so build can be used
		}
		cliqueArray = graphBuild(cliqueArray,connectList);
		connections = getConnects(cliqueArray);   //????? why does this only work with person?
		//System.out.println("connections: " + connections);
		
		
		
		
	}
	
	
	
	//find first person to go to the school then call dfs to generate an arrayList of people which will be turned into a subgraph
	 private static ArrayList<person> dfs(person start, String school, person[] people) {
		 ArrayList<person> list = new ArrayList<person>();  //to return at end after being build with recursion
		 list.add(start);
		 for (int v=0; v < people.length; v++) {
			 if (!people[v].visited) {
				
				 list = stub(v,list,people,school);
				 for(int i= 0; i< list.size();i++){
					// System.out.print(list.get(i) + " | ");
				 }
			 }
		 }
		 
		 return list;
	 }
	 
	 
	 private static ArrayList<person> stub(int v, ArrayList<person> returnPeople, person[] people,String school){
		 if(people[v].front == null){
			 return returnPeople;
		 }
		 neighbor temp;
		 temp = people[v].front;
		 while (temp!=null) {
			 if (people[temp.index].visited == false) {
				 if(people[temp.index].school !=null){
					 if(people[temp.index].school.equalsIgnoreCase(school)){
						 returnPeople.add(people[temp.index]);
					 }
				 } 
				// System.out.println("\t" + people[v].name + "--" + people[people[v].front.index].name);
				 people[temp.index].visited = true;
				 stub(people[temp.index].index, returnPeople, people,school);
			 }
			// if(people[v].front.next == )
			temp = temp.next;
		 }
		 return returnPeople;
	 }
	
	private static person makePerson(String before, int i){   //setter method. does not include parameters
															  //for other methods yet
		String school=null;
		String name = before.substring(0, before.indexOf("|"));	

		if(before.charAt(before.indexOf("|")+1)=='y'){				
			school=before.substring(before.lastIndexOf('|')+1, before.length());	
		}
		person done = new person(name,school,null,false,i,before);
		return done;
	}
	private static String getName(int i, person[] people){
		return people[i].name;
	}
	
	public static void printGraph(person[] people){
		ArrayList<String> connectList = new ArrayList<String>();
		int i = 0;
		while(i < people.length){
			System.out.println(people[i].raw);
			i++;
		}
		int a = 0;
		while(a<people.length){
			person hold = people[a];
			String name = people[a].name;
			neighbor n = people[a].front;
			
			while(n != null && a<people.length && n.index < people.length){ //probably delete 2nd part
				String s = name;
				String s2 = people[n.index].name;
				boolean duplicate = false;
				if(connectList.size() > 0){
					for(int j = 0; j < connectList.size();j++){
						String temp1 = connectList.get(j).substring(0,connectList.get(j).indexOf('|'));
						String temp2 = connectList.get(j).substring(connectList.get(j).indexOf('|')+1);
						if((s.equals(temp1) || s.equals(temp2)) && (s2.equals(temp1) || s2.equals(temp2))){
							duplicate = true;
						}
						if(duplicate) break;
					}
				}	
				if(duplicate == false) connectList.add(s+ '|' + s2);
				n = n.next;			
			}
			people[a] = hold;
			a++;
		}
		for(int q = 0;q<connectList.size();q++){
			System.out.println(connectList.get(q));
		}
		
	}
	
	public static ArrayList<String> getConnects(person[] people){
		ArrayList<String> connectList = new ArrayList<String>();
		int i = 0;
		while(i < people.length){
			//System.out.println(people[i].raw);
			i++;
		}
		int a = 0;
		while(a<people.length){
			person hold = people[a];
			String name = people[a].name;
			neighbor n = people[a].front;
			
			while(n != null && a<people.length && n.index < people.length){ //probably delete 2nd part
				String s = name;
				String s2 = people[n.index].name;
				boolean duplicate = false;
				if(connectList.size() > 0){
					for(int j = 0; j < connectList.size();j++){
						String temp1 = connectList.get(j).substring(0,connectList.get(j).indexOf('|'));
						String temp2 = connectList.get(j).substring(connectList.get(j).indexOf('|')+1);
						if((s.equals(temp1) || s.equals(temp2)) && (s2.equals(temp1) || s2.equals(temp2))){
							duplicate = true;
						}
						if(duplicate) break;
					}
				}	
				if(duplicate == false) connectList.add(s+ '|' + s2);
				n = n.next;			
			}
			people[a] = hold;
			a++;
		}
		for(int q = 0;q<connectList.size();q++){
		//	System.out.println(connectList.get(q));
		}
		return connectList;
	}
	
	public static ArrayList<Integer> shortestOther(String startS, String targetS, person[] people, person[] holder, person[] people2, ArrayList<String> connectList)throws IOException  {
		
	
		//stub method
		int start = -3;
		int target = -5;
		for(int i = 0;i<people.length;i++){
			if(holder[i].name.equals(startS)){
				start = people[i].index;
			}
		}
		if(start == -3){
			System.out.println("first person doesn't exist");
			throw new NoSuchElementException();
		}
		for(int i = 0;i<people.length;i++){
			if(holder[i].name.equals(targetS)){
				target = people[i].index; 
			}
		}
		if(start == -5){
			System.out.println("second person doesn't exist");
			throw new NoSuchElementException();
		}
		ArrayList<Integer> holdem = new ArrayList<Integer>();
		while(people2[target].front != null){
			holdem.add(people2[target].front.index);
			people2[target].front = people2[target].front.next;
		}

		
		ArrayList<Integer> nums = new ArrayList<Integer>();
		nums.add(start);
		nums.add(-1);
		  //front is actually the first neighbor
		int j = 1;
		while(start != target){
				
				while(holder[start].front != null){  //start may need to be a changing variable
					if(!nums.contains(holder[start].front.index)){
						nums.add(holder[start].front.index);
					}
					if(holdem.contains(holder[start].front.index)){
						nums.add(target);
						start = target;
					}
					if(start == target) {break; }
					holder[start].front = holder[start].front.next;
				}
			
				if(nums.get(j) == -1) j++;
				if(start != target){
					start = nums.get(j);
				}
				if(start == target){  //infinite. delete
					break;
				}
				j++;
				if(j>nums.size()){ break; }
		}
		int k = 1;
		people = graphBuild(people,connectList);
		ArrayList<Integer> finals = new ArrayList<Integer>();
		int size = nums.size()-1;
		while(true){
			if(nums.get(size) == target){
				finals.add(nums.get(size));
				break;
			}
			else{ size--; }
		}	
		while(size != 0){
			while(people[nums.get(size)].front != null && size>0){
				if(people[nums.get(size)].front.index == nums.get(size-k)){
					finals.add(nums.get(size-k));
					size = size-k;
					k = 1;
					if(finals.get(finals.size()-1) == start) break;
					continue;
				}
				people[nums.get(size)].front = people[nums.get(size)].front.next;
			}
			if(size!= -1) people = graphBuild(people,connectList);
			if(size == 0){ break; }
			k++;
		}
       String result = "";
       int n = finals.size()-1;
       while(n>-1){
    	   if(finals.get(k) == -1){n--; continue; }
    	   result = result + people[finals.get(n)].name + "--";
    	   n--;
       }
       ArrayList<Integer> otherFinal = new ArrayList<Integer>();
       graphBuild(people,connectList);
       graphBuild(holder,connectList);
       graphBuild(people2,connectList);
   
       result = result.substring(0, result.length()-2);
       return(finals);
    

       
    }
	
}


