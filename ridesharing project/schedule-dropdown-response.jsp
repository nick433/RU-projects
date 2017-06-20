<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<%
	String username = session.getAttribute("username").toString();
	System.out.print("u: " + username);
	String ride = "r";
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
		String phour = request.getParameter("hour");
		String pminute = request.getParameter("minute");
		String fromcampus = request.getParameter("fromcampus");
		String destcampus = request.getParameter("destcampus");
		String day = request.getParameter("day of the week");
		String classCode = request.getParameter("classCode");
		String hour;
		int tempHour;
		if(phour.contains("a")){
			hour = (phour.substring(0,phour.lastIndexOf('a')));
			System.out.println("crashes");
		}
		else if(phour.contains("p") && !phour.equals("12pm")){
			tempHour = Integer.parseInt(phour.substring(0,phour.lastIndexOf('p')));
			tempHour = tempHour + 12;
			hour = Integer.toString(tempHour);
		}
		else{
			hour = (phour.substring(0,phour.lastIndexOf('p')));
		}
		String minute = pminute;
	    System.out.print(phour + " - " + hour + "min: " + pminute + " - " + minute);
	    String insert = "INSERT INTO classes(hour, minute, fromcampus, destcampus, dayOfWeek, username, rideOrDrive, classCode)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    PreparedStatement ps = con.prepareStatement(insert);
	    
	    ps.setString(1, hour);
	    ps.setString(2, minute);
	    ps.setString(3, fromcampus);
	    ps.setString(4, destcampus);
	    ps.setString(5, day);
	    ps.setString(6, username);
	    ps.setString(7, ride);
	    ps.setString(8, classCode);
	    
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
added!
<br></br>
<a href ="D1-2 riderscheduler.jsp">add another</a>;
<a href ="D1-1 ridermenu.jsp">back to menu</a>;
</body>
</html>