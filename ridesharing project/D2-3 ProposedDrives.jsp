<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1> Propose a drive here</h1>

<h1> So, you'd like to request a ride!</h1>
<h2> Well, let's start with a location. Where would you like to go?</h2>

<form method="post" action='D2-3A offer-builder.jsp'>

<select name="destination">
	<option value='Livingston'>Livingston</option>
	<option value='Busch'>Busch</option>
	<option value='College Ave'>College Ave</option>
	<option value='Cook/Douglass'>Cook/Douglass</option>
</select>

<h2> What about an origin? Where will you be picked up from?</h2>
<select name="origin">
	<option value='Livingston'>Livingston</option>
	<option value='Busch'>Busch</option>
	<option value='College Ave'>College Ave</option>
	<option value='Cook/Douglass'>Cook/Douglass</option>
</select>
destination, origin, day, hour, minute, getAttributerID, makerequestID
<h2>For what day of the week? Numbers go from Sunday to Saturday, where 1 = Sunday, 7 = Saturday.</h2>
<select name="day">
	<option value='1'>1</option>
	<option value='2'>2</option>
	<option value='3'>3</option>
	<option value='4'>4</option>
	<option value='5'>5</option>
	<option value='6'>6</option>
	<option value='7'>7</option>
</select>
<h2> As a driver, you assemble a time window. </h2>
<h2> For which starting hour? (Refer to military time; 0 refers to 12 AM, 12 refers to 12 PM, and 13 1 PM.)</h2>
<select name="startHour">
	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
	<option value="6">6</option>
	<option value="7">7</option>
	<option value="8">8</option>
	<option value="9">9</option>
	<option value="10"'>10</option>
	<option value='11'>11</option>
	<option value ='12'>12</option>
	<option value='13'>13</option>
	<option value='14'>14</option>
	<option value='15'>15</option>
	<option value='16'>16</option>
	<option value='17'>17</option>
	<option value='18'>18</option>
	<option value='19'>19</option>
	<option value='20'>20</option>
	<option value='21'>21</option>
	<option value='22'>22</option>
	<option value='23'>23</option>
</select>

<h2> Until which hour?</h2>
<select name="endHour">
	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
	<option value="6">6</option>
	<option value="7">7</option>
	<option value="8">8</option>
	<option value="9">9</option>
	<option value="10"'>10</option>
	<option value='11'>11</option>
	<option value ='12'>12</option>
	<option value='13'>13</option>
	<option value='14'>14</option>
	<option value='15'>15</option>
	<option value='16'>16</option>
	<option value='17'>17</option>
	<option value='18'>18</option>
	<option value='19'>19</option>
	<option value='20'>20</option>
	<option value='21'>21</option>
	<option value='22'>22</option>
	<option value='23'>23</option>
</select>
<h2> For what starting minute? Currently, our system only allows for quarters of the hour. </h2>
<select name="startMinute">
	<option value='00'>00</option>
	<option value='15'>15</option>
	<option value='30'>30</option>
	<option value='45'>45</option>
</select>

<h2>Until which minute></h2>
<select name="endMinute">
	<option value='00'>00</option>
	<option value='15'>15</option>
	<option value='30'>30</option>
	<option value='45'>45</option>
</select>


<input type="submit" value="submit">
</form>

</body>
</html>