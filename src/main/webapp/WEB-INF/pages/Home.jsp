
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="assets/css/home.css" rel="stylesheet">
      <link href="assets/css/main.css" rel="stylesheet">
      <title>Home</title>
   </head>
   <body>
      <div id="headerwrap">
         <br><br><br>
         <h2>Welcome home ${sessionScope.identity} !</h2>
         <div class="container">
            <br>
            <div class="container">
               <a href="LandingPage" class="myButton">Landing Page</a> 
               <a href="userManager.html" class="myButton">User Manager</a>   
               <a href="Logout" class="myButton">Log Out</a>   
            </div>
         </div>
      </div>>
   </body>
</html>
