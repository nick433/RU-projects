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
<body><% 
// offerID, dID, origin, destination, startHour, endHour, startMinute, endMinute, day, maxop, active, username
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(url, "master", "password");
		Statement st = con.createStatement();
		String search = "SELECT MAX(offerID) AS max FROM driverOffers ";
		ResultSet rs = st.executeQuery(search);
		rs.next();
		int maxID = rs.getInt("max");
		int offerID = maxID+1;
		
		String sDID = session.getAttribute("dID").toString();
		int dID = Integer.parseInt(sDID);
	//	search = "SELECT maxop as maximo FROM drivers WHERE dID="+dID;
		rs.next();
		String location = request.getParameter("origin");
		String destination = request.getParameter("destination");
		String input = request.getParameter("startHour");
		int startHour = Integer.parseInt(input);
		input = request.getParameter("endHour");
		int endHour = Integer.parseInt(input);
		int startMinute = Integer.parseInt(request.getParameter("startMinute"));
		int endMinute = Integer.parseInt(request.getParameter("endMinute"));
		int day = Integer.parseInt(request.getParameter("day"));
		String username = session.getAttribute("username").toString();
		
		String insert = "INSERT INTO driverOffers(offerID, dID, location, destination, startHour, endHour, startMinute, endMinute, day, maxop, active, username) "+"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(insert);
		int maxop = 1;
		ps.setInt(1, offerID);
	    ps.setInt(2, dID);
	    ps.setString(3, location);
	    ps.setString(4, destination);
	    ps.setInt(5, startHour);
	    ps.setInt(6, endHour);
	    ps.setInt(7, startMinute);
	    ps.setInt(8, endMinute);
	    ps.setInt(9, day);
	    ps.setInt(10, maxop);
	    ps.setBoolean(11, true);
	    ps.setString(12, username);
	    ps.executeUpdate();
	    
	    con.close();
	    response.sendRedirect("D2-4 offerPreview.jsp");
		
		
		
%>
</body>
</html>