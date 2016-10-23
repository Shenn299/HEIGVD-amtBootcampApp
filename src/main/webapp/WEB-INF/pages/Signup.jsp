
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
   
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="assets/css/signup.css" rel="stylesheet">
      <title>Sign Up</title>
   </head>
   
   <body>

      <div id="headerwrap">
         <h1>Please, Sign Up ! </h1>
         <h2>${requestScope.signupMessage}</h2>
      </div>

      <div class="login-page">

         <div class="form">
            <form action="CheckSignup" class="login-form" method="POST">
               <input name="username" type="text" placeholder="Username" maxlength="45"/>
               <input name="password" type="password" placeholder="Password" maxlength="45"/>
               <input name="passwordConfirmed" type="password" placeholder="Confirm password" maxlength="45"/>
               <p></p>
               <input type="submit" class="button" name="signup" value="Sign Up"/>
               <p class="message">Already registered ? <a href="Login">Log In !</a></p>
               <p class="message"><a href="LandingPage">Go back Home !</a></p>
            </form>
         </div>         
      </div>
   </body>
</html>