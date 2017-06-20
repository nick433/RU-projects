<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Success Check</title>
<body>
<%
	session.setAttribute("fromCommentPost","n");
	session.setAttribute("fromForumPost", "n");
	if((session.getAttribute("username")== null) || (session.getAttribute("username") == "")){
		%> 
	<h2>	Eh? You're not logged in! </h2>
	<a href="index.jsp"> Login!</a>
		
	<% }else{  
	%>
  <h2> Welcome <%=session.getAttribute("username") %> </h2>
  <h3> Are you a  <a href='C2-1 riderlogin.jsp'> Rider today?</a> </h3>
  <h4> Or a <a href='C3-1 driverlogin.jsp'> Driver  </a> </h4>
  <a href='B3 - logout.jsp'> Or perhaps you'd like to sign out.</a>
  <% } %>
 <% 
  String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
  Class.forName("com.mysql.jdbc.Driver");
  Connection con = DriverManager.getConnection(url, "master", "password");
  String username = session.getAttribute("username").toString();
  Statement st= con.createStatement(); 
  ResultSet rs=st.executeQuery("select COUNT(*) from staff where username='"+username+"'"); 
  rs.next();
  if(rs.getInt(1) >0){
	  rs=st.executeQuery("select s.level from staff s where username='"+username+"'");
	  rs.next();
	  int level = rs.getInt(1);
	  session.setAttribute("level", level);
	  response.sendRedirect("C1-1 riderordriveroradmin.jsp");
	  
  }
  con.close();
  %>
	
<br>
	<form method="post" action="H1 - profiles.jsp">
	<table>
	 <tr> <td>Enter username to view their profile: (you can view yourself this way too)</td>
		<td><input type="text" name="profileName"><br> </td> </tr>
   		</table>
	<input type="submit" value="submit">
	</form>
<br>

<h4> Check out our  <a href='forum.jsp'>forum</a> </h4>

</body>
</html>