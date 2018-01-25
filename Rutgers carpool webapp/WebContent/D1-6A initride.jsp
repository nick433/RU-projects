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
int driveID = Integer.parseInt(request.getParameter("driveID").toString());
String drive = "SELECT * FROM confirmedDrives WHERE driveID="+driveID;
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery(drive);
rs.next();
int dID = rs.getInt("dID");
String p = "UPDATE confirmedDrives SET fulfilled=? WHERE driveID="+driveID;
PreparedStatement ps = con.prepareStatement(p);
ps.setBoolean(1, true);
ps.executeUpdate();
session.setAttribute("helperdID", dID);
response.sendRedirect("D1-7 ride.jsp");



%>

</body>
</html>