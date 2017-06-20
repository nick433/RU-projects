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
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
int dID = Integer.parseInt(session.getAttribute("helperdID").toString());
String driverCall = "SELECT * from drivers where dID="+dID;
ResultSet rs = st.executeQuery(driverCall);
rs.next();
int currentRating = rs.getInt("rating");
int nrating = rs.getInt("nratings");
int newrating = Integer.parseInt(request.getParameter("rating").toString());
int replaceRating;
int newrides = rs.getInt("nrides")+1;
if(nrating==0){ 
 replaceRating = newrating;
}else{
 replaceRating = currentRating + (newrating-currentRating)/nrating;
}
// This is actually bad math but whatever

String updateRating = "UPDATE drivers SET nratings=? and rating=? and nrides=? where dID=?";

PreparedStatement ps = con.prepareStatement(updateRating);
ps.setInt(1, nrating+1);
ps.setInt(2, replaceRating);
ps.setInt(3, newrides);
ps.setInt(4, dID);
ps.executeUpdate();
%>
<a href='D1-1 ridermenu.jsp'> <% out.println("Ride Complete!"); %></a>




%>
</body>
</html>