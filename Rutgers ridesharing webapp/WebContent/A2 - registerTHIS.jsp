<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
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
		String str = "SELECT COUNT(*) as cnt FROM members";
		ResultSet result = stmt.executeQuery(str);
		result.next();
		int countBars=result.getInt("cnt");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String RUID = request.getParameter("RUID");
		String username = request.getParameter("username");    
	    String pwd = request.getParameter("pwd");
	    
	    String insert = "INSERT INTO members(firstname, lastname, email, RUID, username, pwd)" + "VALUES (?, ?, ?, ?, ?, ?)";
	    
	    PreparedStatement ps = con.prepareStatement(insert);
	    
	    ps.setString(1, firstname);
	    ps.setString(2, lastname);
	    ps.setString(3, email);
	    ps.setString(4, RUID);
	    ps.setString(5, username);
	    ps.setString(6, pwd);
	    
	    ps.executeUpdate();
	    
	    str = "SELECT COUNT(*) as cnt FROM members";
	    result = stmt.executeQuery(str);
	    result.next();
	    System.out.println("Here");
	    int countMembersN = result.getInt("cnt");
	    System.out.println(countMembersN);
	    
	    con.close();
	    
	    	  //Create a Prepared SQL statement allowing you to introduce the parameters of the query
	    
	    
	
	    	  
	    	  
	} catch(Exception ex){
			out.print(ex);
			out.print("insert failed");
	}
%>
<a href ="index.jsp">Login</a>;
</body>
</html>