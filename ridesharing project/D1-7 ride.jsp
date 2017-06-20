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
<h2> Here's an ad from our sponsors.</h2>
<%
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();



String latestad = "SELECT max(adID) as max FROM advertisement";
ResultSet rs = st.executeQuery(latestad);
rs.next();
/* String ad = "SELECT * FROM advertisement WHERE adID="+rs.getInt(latestad);
Statement s2 = con.createStatement();
ResultSet r2 = st.executeQuery(ad);
r2.next(); */
%>
<img src="<% // out.println(r2.getString("url")); %>" alt="ad" style="width:128px;height:128px;">


<h2> Click this button when the ride is done.</h2>
<form method='post' action='D1-7A.jsp'>
<select name='fulfilled'>
<option> But not before taking in the ad. We do need to pay the bills, you know.</option>
</select>
<input type="submit" value="submit"></form>
</body>
</html>