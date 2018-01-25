<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!--Import some libraries that have classes that we need -->
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leaderboards</title>
</head>
<body>
	<%
	String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
	//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
	Class.forName("com.mysql.jdbc.Driver");

	//Create a connection to your DB
	Connection con = DriverManager.getConnection(url, "master", "password");
	Statement st= con.createStatement(); 
	ResultSet rs=st.executeQuery("SELECT * FROM drivers ORDER BY rating DESC LIMIT 50");
	rs.next();

	
	int i = 1;
	%>
	
<TABLE BORDER="4">
   <TR>
   <TH COLSPAN="5">
         <H2><BR>Highest ratings - Top 50</H2>
      </TH>
   </TR>
   <TR>  
      <TD><strong>placing</strong></TD>
      <TD><strong>username</strong></TD>
      <TD><strong>rating</strong></TD>
      <TD><strong>#of ratings</strong></TD>
      <TD><strong>#of rides</strong></TD>
   </TR>
   
   <% while(rs.next()){ %>
   <TR>  
      <TD><% out.println(i); %></TD>
      <TD><% out.println(rs.getString("username")); %></TD>
      <TD><% out.println(rs.getInt("rating")); %></TD>
      <TD><% out.println(rs.getInt("nratings")); %></TD>
      <TD><% out.println(rs.getInt("nrides")); i++; %></TD>
      
   </TR>
   <% } %>

</TABLE>

	<%
	ResultSet r =st.executeQuery("SELECT * FROM drivers ORDER BY nrides DESC LIMIT 50");
	r.next();

	
	i = 1;
	%>
	
<TABLE BORDER="4">
   <TR>
   <TH COLSPAN="5">
         <H2><BR>Most rides given - Top 50</H2>
      </TH>
   </TR>
   <TR>  
      <TD><strong>placing</strong></TD>
      <TD><strong>username</strong></TD>
      <TD><strong>#of rides</strong></TD>
      <TD><strong>#of ratings</strong></TD>
      <TD><strong>rating</strong></TD>
   </TR>
   
   <% while(r.next()){ %>
   <TR>  
      <TD><% out.println(i); %></TD>
      <TD><% out.println(r.getString("username")); %></TD>
      <TD><% out.println(r.getInt("nrides")); %></TD>
      <TD><% out.println(r.getInt("rating")); %></TD>
      <TD><% out.println(r.getInt("nratings")); i++; %></TD>
      
   </TR>
   <% } con.close(); %>

</TABLE>

</body>
</html>
