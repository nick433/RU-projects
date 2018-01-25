<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% %>
<title>Welcome, <%   %></title>
</head>
<body>
<% String IG88 = session.getAttribute("level").toString();
int HK47 = Integer.parseInt(IG88); %>
<h1> Welcome, <i> <% if(HK47>1){ out.print("Administrator"); } else{ out.print("Staff Member");} %></i> <%=session.getAttribute("username") %> </h1>
<h2>The Internet is a lawless place. But not the Driver-Rider site! </h2>
<h3> <a href='E1-2 ReportMenu.jsp'> REPORTS: There's reports that need checking!</a></h3>
<h3> <a href='E1-3 UserDirectory.jsp'> USER DIRECTORY: Or maybe you'll go on a hunch.</a></h3>
<h3> <a href='E1-4 DriverDirectory.jsp'> DRIVER DIRECTORY: Check to see the best drivers are doing!</a></h3>
<h3> <a href='E1-5 AdPlacement.jsp'> AD PLACEMENT: Wait, we make money here? Cool! Drop an ad here. </a></h3>
</body>
</html>