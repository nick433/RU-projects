<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Become a driver</title>
</head>

<body>
<h1> Add a car and you'll be good to go!</h1>
	
<br>
	<form method="post" action="C3-2 driverenrollment.jsp">
	<table>
	 <tr> <td>Enter your car's make:</td>
		<td><input type="text" name="make"><br> </td> </tr>
   <tr><td> Model:</td>
    	<td><input type="text" name="model"><br> </td>
    <tr><td> Year: </td>
    	<td><input type="text" name="year"><br></td> </tr>
    <tr><td> Color: </td>
    	<td><input type="text" name="color"><br></td></tr>
    <tr><td> License plate #:</td>
    	<td><input type = "text" name="license"><br></td> </tr>
    <tr><td> Max Occupancy: </td>
   		<td> <input type="text" name="maxop"><br></td></tr>
   		</table>
	<input type="submit" value="submit">
	</form>
<br></br>
<a href='C1 - riderordriver.jsp'>Back to menu</a> 
</body>
</html>