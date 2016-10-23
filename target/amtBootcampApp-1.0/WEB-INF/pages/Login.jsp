
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="assets/css/login.css" rel="stylesheet">
      <title>Login</title>
   </head>

   <body>
      
      <div id="headerwrap">
         <h1>Please, Log In ! </h1>
         <h2>${requestScope.signupMessage}</h2>
      </div>

      <div class="login-page">

         <div class="form">
            <form action="CheckLogin" class="login-form" method="POST">
               <input name="username" type="text" placeholder="Username" maxlength="45"/>
               <input name="password" type="password" placeholder="Password" maxlength="45"/>
               <p></p>
               <input type="submit" class="button" name="login" value="Log In"/>
               <p class="message">Not registered ? <a href="Signup">Sign Up !</a></p>
               <p class="message"><a href="LandingPage">Go back Home ! </a></p>
            </form>
         </div>
      </div>
   </body>
</html>
