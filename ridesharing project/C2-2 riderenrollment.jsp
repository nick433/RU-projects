<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rider Enrollment</title>
</head>
<body>
<h1> Given that this is your first time logging in as a rider, you'll need to enroll. Welcome!</h1>
<h2> A few questions; </h2>

<% try{
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
Class.forName("com.mysql.jdbc.Driver");

//Create a connection to your DB
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st= con.createStatement(); 
ResultSet rs=st.executeQuery("SELECT MAX(rID) as max from riders");
rs.next();
int max = rs.getInt("max");
out.println(max);
String insert = "INSERT INTO riders( rID, username, rating, nratings)" + " VALUES (?, ?, ?, ?)";
int rID = max+1;
PreparedStatement ps = con.prepareStatement(insert);

String username = (session.getAttribute("username")).toString();
ps.setInt(1, rID);
ps.setString(2, username);
int zeroRating = 0;
ps.setInt(3, zeroRating);
ps.setInt(4, zeroRating);

ps.executeUpdate();

// Update member table to reflect the RID
st.executeUpdate("UPDATE members SET rID='"+rID+"' WHERE username='"+username+"'");

session.setAttribute("rID", rID);
response.sendRedirect("C2-3 enrollmentconfirmation.jsp"); } catch(Exception ex){
		out.println(ex);
}
%>
</body>
</html>