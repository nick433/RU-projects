<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
Class.forName("com.mysql.jdbc.Driver");

//Create a connection to your DB
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st= con.createStatement(); 
ResultSet rs=st.executeQuery("SELECT MAX(dID) as max from drivers");
rs.next();
int max = rs.getInt("max");
out.println(max);
String insert = "INSERT INTO drivers(dID, username, rating, nratings, nrides)" + " VALUES (?, ?, ?, ?, ?)";
int dID = max+1;
PreparedStatement ps = con.prepareStatement(insert);
ps.setInt(1, dID);
String username = (session.getAttribute("username")).toString();
ps.setString(2, username);
int zeroRating = 0;
ps.setInt(3, zeroRating);
ps.setInt(4, zeroRating);
ps.setInt(5, zeroRating);
ps.executeUpdate();

// Update member table to reflect the RID
st.executeUpdate("UPDATE members SET dID='"+dID+"' WHERE username='"+username+"'");

session.setAttribute("dID", dID);
response.sendRedirect("C3-3 enrollmentconfirmation.jsp");
%>
</body>
</html>