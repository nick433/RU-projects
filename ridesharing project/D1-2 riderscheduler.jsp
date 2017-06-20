<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Make schedule</title>
</head>
<body>

Add Classes:
<form action="schedule-dropdown-response.jsp">

	Select time class starts
	<select name="hour">
		<option>7am</option>
		<option>8am</option>
		<option>9am</option>
		<option>10am</option>
		<option>11am</option>
		<option>12pm</option>
		<option>1pm</option>
		<option>2pm</option>
		<option>3pm</option>
		<option>4pm</option>
		<option>5pm</option>
		<option>6pm</option>
		<option>7pm</option>
		<option>8pm</option>
		<option>9pm</option>	
	</select>
	<select name="minute">
		<option>00</option>
		<option>05</option>
		<option>10</option>
		<option>15</option>
		<option>20</option>
		<option>25</option>
		<option>30</option>
		<option>35</option>
		<option>40</option>
		<option>45</option>
		<option>50</option>
		<option>55</option>
	</select>
	<br/><br/>
	campus you are coming from:
	<select name="fromcampus">
		<option>Livingston</option>
		<option>Busch</option>
		<option>College ave</option>
		<option>Cook</option>
		<option>Douglass</option>
	</select>
	<br/><br/>
	campus you are going to:
	<select name="destcampus">
		<option>Livingston</option>
		<option>Busch</option>
		<option>College ave</option>
		<option>Cook</option>
		<option>Douglass</option>
	</select>
	<br/><br/>
	What day is it on?
	<select name="day of the week">
		<option>Monday</option>
		<option>Tuesday</option>
		<option>Wednesday</option>
		<option>Thursday</option>
		<option>Friday</option>
		<option>Saturday</option>
	</select>
	<tr> <td>Class name or code:</td>
		<td><input type="text" name="classCode"><br> </td> </tr>
	<br/><br/>

	<input type="submit" value="Submit" />
	
</form>
<br/><br/>
Below are matches to request carpooling schedules!
<%
String username = session.getAttribute("username").toString();
System.out.print("u: " + username);
String ride = "r";
try {
	//Create a connection string
	String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
	//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
	Class.forName("com.mysql.jdbc.Driver");

	//Create a connection to your DB
	Connection con = DriverManager.getConnection(url, "master", "password");
	
	//Get the info
	Statement stmt = con.createStatement();
	String str = "SELECT DISTINCT c2.* FROM classes c1, classes c2 WHERE c1.username <> c2.username AND c1.username = '" + username + "' AND c1.rideOrDrive = 'r' AND c2.rideOrDrive = 'd' AND c1.destcampus = c2.destcampus AND c1.fromcampus = c2.fromcampus AND  c1.dayOfWeek = c2.dayOfWeek AND (c1.hour = c2.hour OR c1.hour = c2.hour + 1 OR (c1.hour = c2.hour+2 AND c1.minute + 30 < c2.minute))";
	ResultSet rs = stmt.executeQuery(str);
	int matches = 0;
	%> <br></br> <%
	while(rs.next()){
		matches++;
		out.println(matches + ".                   "); 
		out.println("click HERE to send a schedule request to " +rs.getString("username"));%> <br></br>  <%
		out.println("Username: "+ rs.getString("username")); %> <br></br>  <%
		out.println("day: " + rs.getString("dayOfWeek")); %> <br></br>  <%
		out.println("coming from campus: " + rs.getString("fromcampus")); %> <br></br>  <%
		out.println("going to campus: " + rs.getString("destcampus")); %> <br></br>  <%
		out.println("time: " + rs.getString("hour") + ":" + rs.getString("minute")); %> <br></br>  <%
	}
	
	
	/*
	int countBars=result.getInt("cnt");
	String phour = request.getParameter("hour");
	String pminute = request.getParameter("minute");
	String campus = request.getParameter("campus");
	String day = request.getParameter("day of the week");
	String hour;
	int tempHour;
	if(phour.contains("a")){
		hour = (phour.substring(0,phour.lastIndexOf('a')));
		System.out.println("crashes");
	}
	else if(phour.contains("p") && !phour.equals("12pm")){
		tempHour = Integer.parseInt(phour.substring(0,phour.lastIndexOf('p')));
		tempHour = tempHour + 12;
		hour = Integer.toString(tempHour);
	}
	else{
		hour = (phour.substring(0,phour.lastIndexOf('p')));
	}
	String minute = pminute;
    System.out.print(phour + " - " + hour + "min: " + pminute + " - " + minute);
    String insert = "INSERT INTO classes(hour, minute, campus, dayOfWeek, username, rideOrDrive)" + "VALUES (?, ?, ?, ?, ?, ?)";
    
    PreparedStatement ps = con.prepareStatement(insert);
    
    ps.setString(1, hour);
    ps.setString(2, minute);
    ps.setString(3, campus);
    ps.setString(4, day);
    ps.setString(5, username);
    ps.setString(6, ride);
    
    ps.executeUpdate();
    
    str = "SELECT COUNT(*) as cnt FROM members";
    result = stmt.executeQuery(str);
    result.next();
    System.out.println("Here");
    int countMembersN = result.getInt("cnt");
    System.out.println(countMembersN);
    */
    con.close();
    
    	  //Create a Prepared SQL statement allowing you to introduce the parameters of the query
    
    

    	  
    	  
} catch(Exception ex){
		out.print(ex);
		out.print("insert failed");
}

%>
<br/><br/>
<a href ="D1-1 ridermenu.jsp">back to rider menu page</a>
</body>
</html>