
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Welcome</title>

      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="assets/ico/favicon.png">

      <!-- Bootstrap core CSS -->
      <link href="assets/css/bootstrap.css" rel="stylesheet">

      <!-- Custom styles for this template -->
      <link href="assets/css/landingpage.css" rel="stylesheet">
      <link href="assets/css/main.css" rel="stylesheet">
      <link href="assets/css/font-awesome.min.css" rel="stylesheet">
      
      <script src="assets/js/jquery.min.js"></script>
       
      <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'>
      <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>

   </head>

   <body data-spy="scroll" data-offset="0" data-target="#theMenu">

      <!-- Menu -->
      <nav class="menu" id="theMenu">
         <div class="menu-wrap">
            <i class="icon-remove menu-close"></i>
            <a href="#home" class="smoothScroll">Landing Page</a>
            <a href="#services" class="smoothScroll">Our application</a>
            <a href="#about" class="smoothScroll">About Us</a>
         </div>

         <!-- Menu button -->
         <div id="menuToggle"><i class="icon-reorder"></i></div>
      </nav>


      <!-- ========== HEADER SECTION ========== -->
      <section id="home" name="home"></section>
      <div id="headerwrap">
         <div class="container">
            <br>

            <div class="container">
               <a href="Login" class="myButton">Log In</a> 
               <a href="Signup" class="myButton">Sign Up</a>   
               <a href="Home" class="myButton">My account</a>   
            </div>

            <h1>Welcome</h1>
            <h2>We currently work hard to deliver a high quality service</h2>
            <div class="row">
               <br>
               <br>
               <br>
               <div class="col-lg-6 col-lg-offset-3">
               </div>
            </div>
         </div><!-- /container -->
      </div><!-- /headerwrap -->


      <!-- ========== WHITE SECTION ========== -->
      <div id="w">
         <div class="container">
            <div class="row">
               <div class="col-lg-8 col-lg-offset-2">
                  <h3><bold>Enjoy !</bold><br/>
                  </h3>
               </div>
            </div>
         </div><!-- /container -->
      </div><!-- /w -->

      <!-- ========== SERVICES - GREY SECTION ========== -->
      <section id="services" name="services"></section>
      <div id="g">
         <div class="container">
            <div class="row">
               <h3>OUR APPLICATION</h3>
               <br>
               <br>
               <div class="col-lg-3">
                  <img src="assets/img/java_ee.png">
               </div>
               <div class="col-lg-3">
                  <img src="assets/img/wildfly.png">
               </div>
               <div class="col-lg-3">
                  <img src="assets/img/api.png">
               </div>
               <div class="col-lg-3">
                  <img src="assets/img/mysql.png">
               </div>
            </div>
         </div><!-- /container -->
      </div><!-- /g -->

      <!-- ========== WHITE SECTION ========== -->
      <div id="w">
         <div class="container">
            <div class="row">
               <div class="col-lg-8 col-lg-offset-2">
                  <h3>"Our objective is your complete satisfaction"</h3>
               </div>
            </div>
         </div><!-- /container -->
      </div><!-- /w -->

      <!-- ========== ABOUT - GREY SECTION ========== -->
      <section id="about" name="about"></section>
      <div id="g">
         <div class="container">
            <div class="row">
               <h3>ABOUT US</h3>
               <br>
               <div class="col-lg-2"></div>
               <div class="col-lg-8">
                  <h4>"we answer your questions as quickly as possible"</h4>
                  <br>
               </div>
               <div class="col-lg-2"></div>
               <div class="col-lg-3 team">

               </div>

               <div class="col-lg-3 team">
                  <img class="img-circle" src="assets/img/fabien_franchini.jpg" height="90" width="90">
                  <h4>Fabien Franchini</h4>
                  <h5><i>IT security student at HEIG-VD</i></h5>
                  <h5><i>fabien.franchini@heig-vd.ch</i></h5>
	          <a href="https://twitter.com/crab_one"><i class="icon-twitter"></i></a>

               </div>

               <div class="col-lg-3 team">
                  <img class="img-circle" src="assets/img/sebastien_henneberger.jpg" height="90" width="90">
                  <h4>Sébastien Henneberger</h4>
                  <h5><i>IT security student at HEIG-VD</i></h5>
                  <h5><i>sebastien.henneberger@heig-vd.ch</i></h5>
		  <a href="https://twitter.com/Shen299"><i class="icon-twitter"></i></a>
               </div>

               <div class="col-lg-3 team">

               </div>
            </div>
         </div><!-- /container -->
      </div><!-- /g -->


      <!-- Bootstrap core JavaScript
      ================================================== -->
      <!-- Placed at the end of the document so the pages load faster -->
      <script src="assets/js/classie.js"></script>
      <script src="assets/js/bootstrap.min.js"></script>
      <script src="assets/js/smoothscroll.js"></script>
      <script src="assets/js/main.js"></script>
   </body>
</html>
