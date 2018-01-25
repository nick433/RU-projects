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
<h2> You have the following incoming rides; </h2>

<% int rID = Integer.parseInt(session.getAttribute("rID").toString());
String rideLook = "SELECT * FROM confirmedDrives WHERE rID="+rID;

String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();

ResultSet rs = st.executeQuery(rideLook);
while(rs.next()){
	int requestID = rs.getInt("requestID");
	Statement s2 = con.createStatement();
	String requests = "SELECT * FROM riderRequests WHERE requestID="+requestID;
	ResultSet r2 = s2.executeQuery(requests);
	r2.next();
	int day = r2.getInt("desiredDay");
	String aDay = "nullDay";
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
	
	
 %> <h3><% out.println("DriveID: "+rs.getInt("driveID")+" Day: "+aDay+"Time "+r2.getInt("desiredHour")+":"+r2.getInt("desiredMinute")); %>   </h3> <% 
	//Statement st2 = con.createStatement();
	//ResultSet r2 = st.executeQuery()
	// out.println("Ride
		
}
con.close();
%>
<h2>Initiate the drive that's occurring now!</h2>

<form method="post" action="D1-6A initride.jsp">
	<table>
	 <tr> <td>DriveID:</td>
		<td><input type="text" name="driveID"><br> </td>
		</table>
		<input type="submit" value="submit">
		
		</form>
</body>
</html>