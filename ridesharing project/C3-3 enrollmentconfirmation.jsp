<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Driver Enrollment</title>
</head>
<body>
<h1> You're enrolled, new driver! Your rID is <%=session.getAttribute("dID") %> </h1>
<h2> You don't need to write that down, though.</h2>
<h3> If you want, continue to the <a href='D2-1 drivermenu.jsp'> Driver menu</a></h3>
<h3> Or, <a href='logout.jsp'> log off</a></h3>
</body>
</html>