<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rider Menu</title>
</head>
<body>
<h1>Welcome, <%=session.getAttribute("username") %>!</h1>
<h2> Let's get down to business; do you want to...</h2>
<h3> 1. Drop a <a href='D1-3 riderequester.jsp'> request?</a></h3>
<h3> 2. Check <a href='D1-4 requestPreview.jsp'> offers that fit your requests?</a></h3>
<h4> 3. Time for a ride? <a href='D1-6 initiateRide.jsp'> Step through the process!</a></h4>
<h3> <a href='D1-2 riderscheduler.jsp'> 4. Find drivers for semester-long carpooling?</a></h3> 
(you can match with drivers who have similar schedules for the semester!)
<h3> 4. Talk to people?</h3>

<h3><a href='C1 - riderordriver.jsp'>Back to menu</a></h3>
<h3>Or maybe you just want to <a href='B3 - logout.jsp'>sign off?</a></h3>
</body>
</html>