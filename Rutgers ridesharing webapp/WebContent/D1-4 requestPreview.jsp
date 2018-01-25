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
<h2> Here you can see your requests and the drivers that can take them?</h2>
<h3> Format: </h3>
<h3>Request ID: [ID]  </h3>
<% 
String sRID = session.getAttribute("rID").toString();
int rID = Integer.parseInt(sRID);
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
Statement st2 = con.createStatement();
String lookUp = "SELECT * FROM riderRequests r WHERE r.rID ="+rID;
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
    
    int requestID = rs.getInt(1);
    String origin = rs.getString(3);
    String destination = rs.getString(4);
    int hour = rs.getInt(5);
    int minute = rs.getInt(6);
    int day = rs.getInt(7);
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
    	
    String username = session.getAttribute("username").toString();
   %> <br> <%  out.println("Request ID: "+ requestID+ " Day: "+ aDay + " Time: "+hour+":"+minute+" Origin: "+origin+" Destination: "+destination);  %> 
   <%
   // Matches with offers.... 
   String offerLookUp = "SELECT * FROM driverOffers d WHERE d.startHour<="+hour+" AND d.endHour>="+hour+" AND d.day="+day+" AND d.Destination='"+destination+"' AND (d.location = '"+origin+"')";
   ResultSet r = st2.executeQuery(offerLookUp);
  // ResultSet r = st.executeQuery(offerLookup);
  while(r.next()){
		boolean active = r.getBoolean(11);
		if(active){
		
		int offerID = r.getInt(1);
	    String origin2 = r.getString(3);
	    String destination2 = r.getString(4);
	    int starthour = r.getInt(5);
	    int endhour = r.getInt(6);
	    int startminute = r.getInt(7);
	    int endminute = r.getInt(8);
	    int day2 = r.getInt(9);
	    String driverName = r.getString(12);
	    
	    %> <h4>  <% out.println("OfferID: "+offerID+" Potential Driver User: "+driverName); %></h4> <% 
	    
		}
	}
 
  }
con.close();
  %>

<h2> See a driver offer? Type the ID to request them. (Note: Your request will be inactive.)</h2>
<form method="post" action="D1-4A requestOffer.jsp">
	<table>
	 <tr> <td> OfferID:</td>
		<td><input type="text" name="requestTHIS"><br> </td>
		<tr> <td>RequestID this is complying with:</td>
		<td><input type="text" name="requestID"><br> </td> </tr>
		</table>
		<input type="submit" value="submit">
		
		</form>
<h2> Don't want to go somewhere? Type the offerID here.</h2>
</body>
</html>