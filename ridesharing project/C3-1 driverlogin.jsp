<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Driver Login</title>
</head>
<body>
<%  
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st= con.createStatement();
String username = (session.getAttribute("username")).toString();
// out.println(username);
ResultSet rs=st.executeQuery("SELECT COUNT(*) as cnt from drivers where username='"+username+"'");
if(rs.next()){
	if(rs.getInt(1) < 1){ 
		con.close();
		response.sendRedirect("C3-2A driverpreenrollment.jsp");
	}else{ 
		rs=st.executeQuery("SELECT dID from drivers where username='"+username+"'");
		rs.next();
		session.setAttribute("dID", rs.getInt(1));
		con.close();
		response.sendRedirect("D2-1 drivermenu.jsp");
}
}

	%>
</body>
</html>