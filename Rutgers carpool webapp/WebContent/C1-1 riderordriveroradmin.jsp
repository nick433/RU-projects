<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*,java.util.*, javax.servlet.*, java.sql.*, java.util.Date, java.util.Calendar" %>
       <%@ page import="javax.servlet.*,java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seeecret Admin Menu</title>
</head>
<body>
<%
	if((session.getAttribute("username")== null) || (session.getAttribute("username") == "")){
		%> 
	<h2>	Eh? You're not logged in! </h2>
	<a href="index.jsp"> Login!</a>
		
	<% }else{ 
		String IG88 = session.getAttribute("level").toString();
		int HK47 = Integer.parseInt(IG88);
	%>
  <h2> Welcome, <i> <% if(HK47>1){ out.print("Administrator"); } else{ out.print("Staff Member");} %></i> <%=session.getAttribute("username") %> </h2>
  <h3> Are you a  <a href='C2-1 riderlogin.jsp'> Rider today?</a> </h3>
  <h4> Or a <a href='C3-1 - driverlogin.jsp'> Driver  </a> </h4>
  
  <h1> Or maybe you'd like to take your <a href='E1-1 AdminMenu.jsp'> staff controls?</a></h1>
  <a href='B3 - logout.jsp'> Or perhaps you'd like to sign out.</a>
  
  <%} %>
</body>
</html>