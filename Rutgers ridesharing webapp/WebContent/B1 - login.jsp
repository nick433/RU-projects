<%@ page import ="java.sql.*" %>
<%
    try{ 

	
    String url = "jdbc:mysql://cs336project.cwohuureg197.us-west-2.rds.amazonaws.com:3306/cs336database";
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection(url, "master", "password");
    /* Statement st = con.createStatement();
    String username = request.getParameter("username");    
    String pwd = request.getParameter("pwd");
    String str = "SELECT COUNT(*) as cnt members m WHERE m.username=" + username +"AND m.pwd=" + pwd;
    ResultSet result = st.executeQuery(str);
    result.next();
    int countUsers = result.getInt("cnt");
    
    String message = countUsers>0? "Login successful :D":"Login failed D:";
    System.out.println(message);
    */
    /* String username=request.getParameter("username"); 
    session.putValue("username",username); 
    String pwd=request.getParameter("pwd");  */
    //Class.forName("com.mysql.jdbc.Driver"); 
    //java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root"); 
    
    
    // Method 2
    
    
    String username = request.getParameter("username");
    Statement st= con.createStatement(); 
    String pwd = request.getParameter("pwd");
    ResultSet rs=st.executeQuery("select * from members where username='"+username+"'"); 
    if(rs.next()) 
    	{
    		if(rs.getString("pwd").equals(pwd)) 
    			{ 
    				con.close();
    				session.setAttribute("username", username);
    				response.sendRedirect("C1 - riderordriver.jsp");

   				 } 
  			 else 
    	{ 
  				out.println(rs.getString(2));
  				con.close();
  				out.println("Invalid password try again"); 
    	} 
    	} 
    	else {
    	}
    } catch(Exception ex){
    //  	throw new Error(ex);
    }
%>
