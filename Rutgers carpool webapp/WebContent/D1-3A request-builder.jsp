<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.io.*,java.util.*, javax.servlet.*, java.sql.*, java.util.Date, java.util.Calendar" %>
       <%@ page import="javax.servlet.*,java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
//Create a connection string
		String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(url, "master", "password");
		Statement st = con.createStatement();
		//String str = "SELECT COUNT(*) as cnt FROM members";
		//ResultSet result = stmt.executeQuery(str);
		//result.next();
		//int countBars=result.getInt("cnt");
		String destination = request.getParameter("destination");
		System.out.println("Destination: " +destination);
		//out.println("Destination: "+ destination);
		String origin = request.getParameter("origin");
		System.out.println("origin: "+ origin);
		//out.println("Origin: " +origin);
		int day = Integer.parseInt(request.getParameter("day"));
		System.out.println("Day: "+day);
		//out.println("Day: "+day);
		int hour = Integer.parseInt(request.getParameter("hour"));
		System.out.println("hour: "+hour);
		//out.println("Hour: "+hour);
		int minute = Integer.parseInt(request.getParameter("minute"));  
		System.out.println("Minute: "+minute);
	//	out.println("Minute: "+minute);
	   String rID = (session.getAttribute("rID")).toString();
	   int rIDactual = Integer.parseInt(rID);
	   System.out.println("rID: "+rID);
	   
	  //  out.println("rID: "+rID);
	  
	  /*  String requestID = "SELECT MAX(requestID) as max FROM riderRequests)";
	    String insert = "INSERT INTO riderRequests(requestID, rID, location, destination, desiredHour, desiredMinute, desiredDay, active, username, proposalID)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    ResultSet rs = st.executeQuery(requestID);
	    rs.next();
	    int maxRequestID = rs.getInt(1);
	    int actualRequestID = maxRequestID +1;
	    String username = session.getAttribute("username").toString();
	    PreparedStatement ps = con.prepareStatement(insert);
	    
	    
	    ps.setInt(1, actualRequestID);
	    ps.setInt(2, rIDactual);
	    ps.setString(3, origin);
	    ps.setString(4, destination);
	    ps.setInt(5, hour);
	    ps.setInt(6, minute);
	    ps.setInt(7, day);
	    ps.setBoolean(8, true);
	    ps.setString(9, username);
	    int aZero = 0;
	    ps.setInt(10, aZero);
	    
	    ps.executeUpdate(); 
	    response.sendRedirect("D1-4 requestPreviewMenu.jsp"); */
	    
	    String requestsID = "SELECT MAX(requestID) as max FROM riderRequests";
	    String insert = "INSERT INTO riderRequests(requestID, rID, location, destination, desiredHour, desiredMinute, desiredDay, active, username, proposalID)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    ResultSet rs = st.executeQuery(requestsID);
	    rs.next();
	    int maxRequestID = rs.getInt("max");
	    int actualRequestID = maxRequestID +1;
	    String username = session.getAttribute("username").toString();
	    PreparedStatement ps = con.prepareStatement(insert);
	    
	    ps.setInt(1, actualRequestID);
	    ps.setInt(2, rIDactual);
	    ps.setString(3, origin);
	    ps.setString(4, destination);
	    ps.setInt(5, hour);
	    ps.setInt(6, minute);
	    ps.setInt(7, day);
	    ps.setBoolean(8, true);
	    ps.setString(9, username);
	    ps.setInt(10, 0);
	    
	    ps.executeUpdate(); 
	    response.sendRedirect("D1-4 requestPreview.jsp");
	    %>
	    
	    
	    
</body>
</html>