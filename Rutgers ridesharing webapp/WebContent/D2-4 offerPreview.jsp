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
<h2>You can see your Active offers here!</h2>
<h2> Would you like to make another <a href='D2-3ProposedDrives.jsp'>offer?</a></h2>
<h2> Back to the <a href='D2-1 drivermenu.jsp'>driver menu?</a></h2>
<h2>You can also see what requests they match with!</h2>
<h2>Want to take down an offer? Pop the ID of the offer here.</h2>
<form method="post" action="D2-4B deleteOffer.jsp">
	<table>
	 <tr> <td>DeleteID:</td>
		<td><input type="text" name="deleteTHIS"><br> </td> </tr>
		</table>
		</form>

<% 
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
int dID = Integer.parseInt(session.getAttribute("dID").toString());
String lookUp = "SELECT * FROM driverOffers d where d.dID ="+dID;
ResultSet rs = st.executeQuery(lookUp);
while(rs.next()){
	boolean active = rs.getBoolean(11);
	if(active){
	
	int offerID = rs.getInt(1);
    String origin = rs.getString(3);
    String destination = rs.getString(4);
    int startHour = rs.getInt(5);
    int endHour = rs.getInt(6);
    int startminute = rs.getInt(7);
    int endminute = rs.getInt(8);
    int day = rs.getInt(9);
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
    String luke = "SELECT * FROM riderRequests r WHERE active = TRUE AND r.destination='"+destination+"' AND r.location='"+origin+"' AND r.desiredHour<="+endHour+" AND r.desiredHour>="+startHour;
    Statement st2 = con.createStatement();
    ResultSet r = st2.executeQuery(luke);
    
    %> <h4>  <% out.println("ID: "+offerID+" Origin: "+ origin+" Destination: "+destination+" Day: "+aDay+" Starting Time: "+startHour+ ":"+startminute+" Ending Time: "+endHour+":"+endminute+" Active?: "+active); %></h4> <% 
    
    
    while(r.next()){
    	
		    
    	if(r.getBoolean("active")){
    
    	
       	%>	 
       	<h3> <% out.println("Rider User: "+r.getString("username")+ " RequestID: "+r.getInt("requestID")); %></h3> <% 
	    
		}
	}
  }
  }
  %>

<%con.close(); 
%>
</body>
</html>