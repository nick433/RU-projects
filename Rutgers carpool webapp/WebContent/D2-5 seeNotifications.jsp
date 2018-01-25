<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
             <%@ page import="java.io.*,java.util.*, javax.servlet.*, java.sql.*, java.util.Date, java.util.Calendar" %>
       <%@ page import="javax.servlet.*,java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Notifications</title>
<h2> This is your inbox of requests from other riders.</h2>
<h2> You can choose to accept requests up to the max occupancy of your vehicle. </h2>
<%
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
String drid = session.getAttribute("dID").toString();
int dID = Integer.parseInt(drid);
String lookUp = "SELECT * FROM notifyDriver n WHERE dID="+dID;
ResultSet rs = st.executeQuery(lookUp);
Statement st2 = con.createStatement();
while(rs.next()){
	int rID = rs.getInt("rID");
	String lookDown = "SELECT * FROM riders WHERE rID="+rID;
	ResultSet r = st2.executeQuery(lookDown);
	r.next(); 
	{
	%> <h2> <%  out.println("NotifyID: "+rs.getInt("notifyID")+" RiderName: "+r.getString("username")+" requestID: "+rs.getInt("requestID")+" offerID");
	} %> <% 
	
}
%>


<h2> Will you confirm?</h2>
<form method="post" action="D2-5A confirmNotifications.jsp">
	<table>
	 <tr> <td>notifyID:</td>
		<td><input type="text" name="notifyID"><br> </td>
		</table>
		<input type="submit" value="submit">
		
		</form>
<h2> Or reject? </h2>
<form method="post" action="D1-4A requestOffer.jsp">
	<table>
	 <tr> <td>OfferID:</td>
		<td><input type="text" name="requestTHIS"><br> </td>
		<tr> <td>RequestID this is complying with:</td>
		<td><input type="text" name="requestID"><br> </td> </tr>
		</table>
		<input type="submit" value="submit">
		
		</form>

</head>
<a href='D2-1 drivermenu.jsp'>Back to driver menu</a>
<body>

</body>
</html>