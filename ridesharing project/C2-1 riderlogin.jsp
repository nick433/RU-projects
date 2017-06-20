<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rider Login</title>
</head>
<body>

<% 
try{
String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection(url, "master", "password");
Statement st= con.createStatement();
String username = (session.getAttribute("username")).toString();
// out.println(username);
ResultSet rs=st.executeQuery("SELECT COUNT(*) as cnt from riders where username='"+username+"'");
if(rs.next()){
	if(rs.getInt(1) < 1){ 
		con.close();
		response.sendRedirect("C2-2 riderenrollment.jsp");
	}else{ 
		rs=st.executeQuery("SELECT rID from riders where username='"+username+"'");
		rs.next();
		session.setAttribute("rID", rs.getInt(1));
		System.out.println("rID: "+ session.getAttribute("rID").toString());
		con.close();
		response.sendRedirect("D1-1 ridermenu.jsp");
}
} 
}catch(Exception ex){
	out.print(ex);
}
/* 
} */
%>
</body>
</html>