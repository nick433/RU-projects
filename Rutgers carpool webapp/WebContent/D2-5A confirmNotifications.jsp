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

int notifyID = Integer.parseInt(request.getParameter("notifyID"));

String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";

Class.forName("com.mysql.jdbc.Driver");

Connection con = DriverManager.getConnection(url, "master", "password");
Statement st = con.createStatement();
String confirmOffer = "SELECT * from notifyDriver WHERE notifyID="+notifyID;

ResultSet rs=st.executeQuery(confirmOffer);
rs.next();
Statement st3 = con.createStatement();
//String checkOP = "SELECT currop FROM driveOpCheck WHERE dID="+rs.getInt("dID");
//ResultSet r2 = st3.executeQuery(checkOP);

//if(r2.getInt("currop")>=r2.getInt("maxop")){
	if(false){
	%> <a href='D2-6 StartDrive.jsp'> <% out.println("Whoa there, you've got enough already. Maybe it's time to start a drive?"); %></a> <%
	
}else{ 
	
String offerConfirmed = "update notifyRider set confirmed=? where notifyID=?";
PreparedStatement ps = con.prepareStatement(offerConfirmed);
ps.setBoolean(1, true);
ps.setInt(2, notifyID);
int requestID = rs.getInt("requestID");
String createDrive = "INSERT INTO confirmedDrives(driveID, rID, dID, fulfilled, requestID) values(?, ?, ?, ?, ?)";
String searchMax = "SELECT max(driveID) as max from confirmedDrives";
Statement st2 = con.createStatement();
ResultSet r = st2.executeQuery(searchMax);
Statement st4 = con.createStatement();
ResultSet r4 = st4.executeQuery(searchMax);
r4.next();
ps = con.prepareStatement(createDrive);
int a = r4.getInt("max")+1;
ps.setInt(1, a);
ps.setInt(2, rs.getInt("rID"));
ps.setInt(3, rs.getInt("dID"));
ps.setBoolean(4, false);
ps.setInt(5, requestID);
ps.executeUpdate();
/*
ps.executeUpdate();
String updatecurrOP = "UPDATE driveOPCheck SET currop=(currop+1) WHERE dID="+rs.getInt("dID");
ps = con.prepareStatement(updatecurrOP); */

%> <a href='D2-5 seeNotifications.jsp'> <% out.println("Alright, skipper. That's one drive appointed. Care to appoint another? "); %>  </a> <% 

}
	con.close();

%>
</body>
</html>