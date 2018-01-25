<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Make schedule</title>
</head>
<body>

Add Classes:
<form action="schedule-dropdown-response-driver.jsp">

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
Below requests for carpooling schedules!

<br/><br/>
<a href ="D2-1 drivermenu.jsp">back to driver menu</a>
</body>
</html>