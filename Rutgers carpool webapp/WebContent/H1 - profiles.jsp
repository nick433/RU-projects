<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% 
String profileName;
boolean fromComments = false;
if(session.getAttribute("fromCommentPost").toString().equals("n")){
	profileName =  request.getParameter("profileName");
	session.setAttribute("profileName", profileName);
}
else{
	profileName = session.getAttribute("profileName").toString();
	fromComments = true;
	session.setAttribute("fromCommentPost", "n");
}
 
%>
<title><% out.println(profileName); %></title>
</head>
<body>
<h2> <%
out.println("Viewing " + profileName + "'s profile");
	%> </h2> 
	
	 <a href='C1 - riderordriver.jsp'> back to main menu.</a>
	
	<h3>
<%
try {
	
	//Create a connection string
	String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
	//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
	Class.forName("com.mysql.jdbc.Driver");

	//Create a connection to your DB
	Connection con = DriverManager.getConnection(url, "master", "password");
	
	//Get the info
	Statement stmt = con.createStatement();
	System.out.println(profileName);
	String str = "SELECT m.* FROM members m WHERE m.username = '"+profileName+"'";
	ResultSet rs = stmt.executeQuery(str);
	rs.next();
	System.out.println("we here");
   
    String firstName = rs.getString("firstname");
    String lastName = rs.getString("lastname");
    String email = rs.getString("email");
    
    int rID = rs.getInt("rID");
	
	int dID = rs.getInt("dID"); //these may be null, need a way to check that they exist first

    
    out.println("firstname: "+ firstName + "  Lastname: " +lastName+"  Email: "+email+"  rID: "+rID+"  dID: "+dID);
	
%> <br><br> 
<%	
//    String insert = "INSERT INTO members(firstname, lastname, email, RUID, username, pwd)" + "VALUES (?, ?, ?, ?, ?, ?)";
    
//    PreparedStatement ps = con.prepareStatement(insert);
    
    
//    ps.executeUpdate();
    Statement stmt2 = con.createStatement();
	System.out.println("this");
    String s = "SELECT d.* FROM drivers d WHERE d.dID = "+ dID;
    ResultSet result = stmt2.executeQuery(s);
    System.out.println("now");
    result.next();
    out.println("rating: " + result.getInt("rating"));
    out.println("  # of ratings: " + result.getInt("nratings"));
	out.println("  Rides given: " + result.getInt("nrides"));
    int numcomments = 0; %> <br><br> Classes: <br> <%
    
    
	Statement stmt3 = con.createStatement();
	System.out.println("this");
	String s2 = "SELECT c.* FROM classes c WHERE c.username = '"+profileName+"' GROUP BY c.dayOfWeek";
	ResultSet result2 = stmt3.executeQuery(s2);


	
	while(result2.next()){
		%> <br> <%
		out.println("  Day: " + result2.getString("dayOfWeek"));
		out.println("Class: " + result2.getString("classCode"));
		out.println("  Campus: " + result2.getString("destcampus"));
		out.println("  Coming from: " + result2.getString("fromcampus"));
		out.println("  Time: " + result2.getInt("hour") + ":" + result2.getInt("minute"));
	
		%> <br> <%
	}
    /////////////////////////////////////////////
    
    /*
    str = "SELECT COUNT(*) as cnt FROM members";
    result = stmt.executeQuery(s);
    result.next();
    System.out.println("Here");
    int countMembersN = result.getInt("cnt");
    System.out.println(countMembersN);
    */
    
    
    	  //Create a Prepared SQL statement allowing you to introduce the parameters of the query



%>

<br>
	<form method="post" action="addComment.jsp">
	<table>
	 <tr> <td>Add a comment: </td> 
		<td><input type="text" name="comment"><br> </td> </tr>
   		</table>
	<input type="submit" value="submit"> <font size="5" color="green"> <% if(fromComments){ out.println("comment added"); } %> </font>
	</form>
<br>

<% 
	Statement stmt33 = con.createStatement();
	System.out.println("this");
	String s22 = "SELECT c.* FROM comments c WHERE c.profileName = '"+profileName+"'";
	ResultSet result22 = stmt33.executeQuery(s22);
    String str5 = "SELECT COUNT(*) as cnt FROM comments WHERE profileName = '"+profileName+"'";
    Statement stmt4 = con.createStatement();
    ResultSet r3 = stmt4.executeQuery(str5);

    r3.next();
    numcomments = r3.getInt("cnt");
    
    //result.next();
    System.out.println("Here");

%>	
	This user has <% out.println(numcomments); %> comments:
		</h3>
		<h4>
<%
	while(result22.next()){
		out.println("Poster: " + result22.getString("poster"));
		%> <br> <%
		out.println("Commment------"); %> <br> <%
		out.println(" " + result22.getString("msg")); %> <br> <%
		out.println("-----------------------------------------------------------");
		%> <br><br> <%
	}
	
	
	con.close();
} catch(Exception ex){
	out.print(ex);
	out.print("insert failed");
}	

%>

</h4>


</body>
</html>