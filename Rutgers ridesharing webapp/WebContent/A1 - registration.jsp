<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body> <!-- the usual HTML way -->
<% out.println("Login"); %> <!-- output the same thing, but using 
                                      jsp programming -->

									  

This page will send send you back here if the registration fails; sends you to login when it succeeds.
<br>
	<form method="post" action="A2 - registerTHIS.jsp">
	<table>
	 <tr> <td>First Name:</td>
		<td><input type="text" name="firstname"><br> </td> </tr>
   <tr><td> Last Name:</td>
    	<td><input type="text" name="lastname"><br> </td>
    <tr><td> Email: </td>
    	<td><input type="text" name="email"><br></td> </tr>
    <tr><td> RUID: </td>
    	<td><input type="text" name="RUID"><br></td></tr>
    <tr><td> UserName:</td>
    	<td><input type = "text" name="username"><br></td> </tr>
    <tr><td> Password: </td>
   		<td> <input type="password" name="pwd"><br></td></tr>
   		</table>
	<input type="submit" value="submit">
	</form>
<br>

</body>
</html>