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
int offerID = Integer.parseInt(request.getParameter("requestTHIS"));
int requestID = Integer.parseInt(request.getParameter("requestID"));
String lookUp = "SELECT * from driverOffers where offerID="+offerID;
String lookDown= "SELECT * from riderRequests where requestID ="+requestID;
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery(lookUp);

Statement st2 = con.createStatement();
ResultSet r = st2.executeQuery(lookDown);
if(rs.next()){
	if(r.next()){
		String insert = "INSERT INTO notifyDriver(notifyID, dID, rID, offerID, requestID, confirmed, rejected)"+" Values(?,?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(insert);
		String look = "SELECT max(notifyID) from notifyDriver";
		rs = st.executeQuery(look);
		rs.next();
		int max = rs.getInt(1);
		int notifyID=max+1;
		ps.setInt(1, notifyID);
		int rID = Integer.parseInt((session.getAttribute("rID").toString()));
		String lewk = "SELECT dID from driverOffers WHERE offerID="+offerID;
		rs = st.executeQuery(lewk);
		rs.next();
		int dID = rs.getInt(1);
		ps.setInt(2, dID);
		ps.setInt(3, rID);
		ps.setInt(4, offerID);
		ps.setInt(5, requestID);
		boolean f = false;
		ps.setBoolean(6, f);
		ps.setBoolean(7, f);
		ps.executeUpdate();
		
		out.println("Notification sent!");
		%> <a href='D1-1 ridermenu.jsp'> Back to the Rider Menu</a> <%
	}else{ out.println( "requestID not valid" );
	%> <a href='D1-4 requestPreview.jsp'> Back to preview?</a> <% 
	}
	
}else{ out.println("offerID not valid");
	%> <a href='D1-4 requestPreview.jsp'> Back to preview?</a> <%
}
con.close();
%>
</body>
</html>