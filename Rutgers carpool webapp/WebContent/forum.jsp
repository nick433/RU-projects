<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title><% out.println("Welcome to our site's forum!"); %></title>
</head>
<body>
	
	 <a href='C1 - riderordriver.jsp'> back to main menu.</a>
<h5> <%
boolean fromForum = false;
if(session.getAttribute("fromForumPost").toString().equals("n")){
}
else{
	fromForum = true;
	session.setAttribute("fromForumPost", "n");
}
out.println("User's can chat below");
	%> </h5> 	
	<h3>
<%
try {
	
	//Create a connection string
	String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
	//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
	Class.forName("com.mysql.jdbc.Driver");

	//Create a connection to your DB
	Connection con = DriverManager.getConnection(url, "master", "password");
	
	Statement stmt3 = con.createStatement();
	System.out.println("this");
	String s = "SELECT * FROM forum";
	ResultSet result2 = stmt3.executeQuery(s);

%>
	</h3>
	<h4>
<%
while(result2.next()){
	out.println(result2.getString("poster")+ ":  ");
	out.println(" " + result2.getString("msg")); 
	%> <br><br> <%
}

%>

<br>
	<form method="post" action="addPost.jsp">
	<table>
	 <tr> <td>Post to the forum!:</td>
		<td><input type="text" name="post"><br> </td> </tr>
   		</table>
	<input type="submit" value="submit"> <font size="5" color="green"> <% if(fromForum){ out.println("comment added"); } %> </font>
	</form>
<br>

<% 
	
	
	
	con.close();
} catch(Exception ex){
	out.print(ex);
	out.print("insert failed");
}	

%>

</h4>


</body>
</html>