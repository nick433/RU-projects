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
<h2> Here are the active requests</h2>
<% 
String sRID = session.getAttribute("dID").toString();
int dID = Integer.parseInt(sRID);
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
String lookUp = "SELECT * FROM driverOffers WHERE active = TRUE AND dID="+dID;
ResultSet rs = st.executeQuery(lookUp);


while(rs.next()){
		/*    
    ps.setInt(1, actualRequestID);
    ps.setInt(2, rIDactual);
    ps.setString(3, origin);
    ps.setString(4, destination);
    ps.setInt(5, hour);
    ps.setInt(6, minute);
    ps.setInt(7, day);
    ps.setString(8, username); */
    
    int offerID = rs.getInt(1);
    
    String origin = rs.getString(3);
    String destination = rs.getString(4);
    int startHour = rs.getInt(5);
    int endHour = rs.getInt(6);
    int startMinute = rs.getInt(7);
    int endMinute = rs.getInt(8);
    int day = rs.getInt(9);
    String username = rs.getString(12);
    String aDay = "NullDay";
    if(day ==1){
    	aDay = "Sunday";
    }else if(day == 2){
    	aDay="Monday";
    }else if(day == 3){
    	aDay="Tuesday";
    }else if(day == 4){
    	aDay="Wednesday";
    }else if (day ==5){
    	aDay="Thursday";
    }else if(day ==6){
    	aDay="Friday";
    }else if(day == 7){
    	aDay="Saturday";
    }
    String luke = "SELECT * FROM riderRequest r WHERE active = TRUE AND d.destination='"+destination+" AND d.location='"+origin+" AND d.hour>="+endHour+" AND d.hour<="+startHour;
    Statement st2 = con.createStatement();
    ResultSet r = st.executeQuery(luke);
    
    
   %> <br> <%=  out.println("User: "+username+ "OfferID: "+ offerID+ " Day: "+ aDay + " Start-Time: "+startHour+":"+startMinute+" End-Time: "+endHour+":"+endMinute+" Origin: "+origin+" Destination: "+destination) %> 
   <% while(r.next()){
   	%>	 <p> <%= out.println("Rider User: "+r.getString("username")+ " RequestID: "+r.getInt("requestID")) %></p> <%
   	} } 
	con.close();%>
</body>
</html>