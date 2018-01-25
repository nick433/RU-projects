<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Driver Menu</title>
</head>
<body>
<h1>Welcome, <%=session.getAttribute("username") %>!</h1>
<h2> Let's get down to business; do you want to...</h2>
<h3> 1. See your active <a href='D2-4 offerPreview.jsp'> schedule?</a></h3>
<h3> 2. Drop an <a href='D2-3 ProposedDrives.jsp'> offer for a ride?</a></h3>
<h3> 3. See requests that <a href='D2-5 seeNotifications.jsp'>fit your current offers? </a></h3>
<h3> <a href='driverscheduler.jsp'>4. Add classes to your schedule?</a></h3> 
(this is used to pair riders with you for weekly carpool schedules!)
<h3> 5. Talk to people?</h3>

<h3><a href='C1 - riderordriver.jsp'>Back to menu</a></h3>

<h3>Or maybe you just want to <a href='B3 - logout.jsp'>sign off?</a></h3>

</body>
</html>
