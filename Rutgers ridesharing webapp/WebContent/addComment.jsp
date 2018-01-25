<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Added commented</title>
</head>
<body>
<h1>Comment added!</h1>
	<%
	session.setAttribute("fromCommentPost", "y");
	try {

		//Create a connection string
		String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
		//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
		Class.forName("com.mysql.jdbc.Driver");
		
		//Create a connection to your DB
		Connection con = DriverManager.getConnection(url, "master", "password");
		
		//Get the info
		Statement stmt = con.createStatement();
		
		String poster = session.getAttribute("username").toString();
		String reciever = session.getAttribute("profileName").toString();
		
		String comment = request.getParameter("comment");
		
	    
	    String insert = "INSERT INTO comments(msg, profileName, poster)" + "VALUES (?, ?, ?)";
	    
	    PreparedStatement ps = con.prepareStatement(insert);
	    
	    ps.setString(1, comment);
	    ps.setString(2, reciever);
	    ps.setString(3, poster);
	    
	    ps.executeUpdate();
	    
	    
	    con.close();
	    
	    response.sendRedirect("H1 - profiles.jsp"); 
	    
	    	  //Create a Prepared SQL statement allowing you to introduce the parameters of the query
	    
	    
	
	    	  
	    	  
	} catch(Exception ex){
			out.print(ex);
			out.print("insert failed");
	}
%>
<a href ="H1 - profiles.jsp">Return to profile page</a> click this to return, not back button, this makes sure that the comment page is refreshed!
</body>
</html>