
package ch.heigvd.amt.amtbootcampapp.web;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * Filter used to check if the client is authorized to access the desired
 * resource. Authorization implemented with white list. A User authenticated in
 * the web app has a session and a locations white list. A User not
 * authenticated in the web app doesn't have a session but he has a locations
 * white list too. If the user isn't authorized to access the resource, he's
 * redirected to the Login page.
 *
 * @author F. Franchini, S. Henneberger
 */
public class AuthenticatedUserFilter implements Filter {

   private static final boolean debug = false;

   // The filter configuration object we are associated with.  If
   // this value is null, this filter instance is not currently
   // configured. 
   private FilterConfig filterConfig = null;

   public AuthenticatedUserFilter() {
   }

   /**
    *
    * @param request  The servlet request we are processing
    * @param response The servlet response we are creating
    * @param chain    The filter chain we are processing
    *
    * @exception IOException      if an input/output error occurs
    * @exception ServletException if a servlet error occurs
    */
   public void doFilter(ServletRequest request, ServletResponse response,
           FilterChain chain)
           throws IOException, ServletException {

      // Locations white list for user who are not authenticated
      List<String> notAuthenticatedLocationsWhiteList = new ArrayList<>();
      notAuthenticatedLocationsWhiteList.add("/Login");
      notAuthenticatedLocationsWhiteList.add("/Signup");
      notAuthenticatedLocationsWhiteList.add("/CheckLogin");
      notAuthenticatedLocationsWhiteList.add("/CheckSignup");
      notAuthenticatedLocationsWhiteList.add("/LandingPage");
      notAuthenticatedLocationsWhiteList.add("/");

      // Locations white list for user who are authenticated
      List<String> authenticatedLocationsWhiteList = new ArrayList<>();
      for (String whiteList : notAuthenticatedLocationsWhiteList) {
         authenticatedLocationsWhiteList.add(whiteList);
      }
      authenticatedLocationsWhiteList.add("/Home");
      authenticatedLocationsWhiteList.add("/userManager.html");
      authenticatedLocationsWhiteList.add("/Logout");

      if (debug) {
         log("AuthenticatedUserFilter:doFilter()");
      }

      // Create wrappers for the request and response objects.
      // Using these, you can extend the capabilities of the
      // request and response, for example, allow setting parameters
      // on the request before sending the request to the rest of the filter chain,
      // or keep track of the cookies that are set on the response.
      //
      // Caveat: some servers do not handle wrappers very well for forward or
      // include requests.
      RequestWrapper wrappedRequest = new RequestWrapper((HttpServletRequest) request);
      ResponseWrapper wrappedResponse = new ResponseWrapper((HttpServletResponse) response);

      Throwable problem = null;

      // We get the user session
      HttpSession session = wrappedRequest.getSession(false);

      boolean isAuthenticated = session != null && session.getAttribute("identity") != null;
      boolean isAuthorized = false;

      // Client is authorized to access special resources
      if (wrappedRequest.getRequestURI().matches(".*(css|eot|ttf|woff|svg|jpg|png|js|jsp)")) {
         isAuthorized = true;
      }

      // Client is authorized to use API
      else if (wrappedRequest.getRequestURI().contains("/api/")) {
         isAuthorized = true;
      }

      // If the user isn't authenticated
      else if (!isAuthenticated) {

         // Check if the ressource that the user want to access is white listed
         for (String locationsWhiteListed : notAuthenticatedLocationsWhiteList) {
            if (wrappedRequest.getRequestURI().equals(wrappedRequest.getContextPath() + locationsWhiteListed)) {
               isAuthorized = true;
            }
         }
      }

      // The user is authenticated
      else {
         // Check if the ressource that the user want to access is white listed
         for (String locationsWhiteListed : authenticatedLocationsWhiteList) {
            if (wrappedRequest.getRequestURI().equals(wrappedRequest.getContextPath() + locationsWhiteListed)) {
               isAuthorized = true;
            }
         }
      }

      // The user is authorized to access the desired resource
      if (isAuthorized) {
         try {
            chain.doFilter(wrappedRequest, wrappedResponse);
         } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
         }
      }

      // The user must be redirected
      else {
         wrappedResponse.sendRedirect("Login");
      }

      // If there was a problem, we want to rethrow it if it is
      // a known type, otherwise log it.
      if (problem != null) {
         if (problem instanceof ServletException) {
            throw (ServletException) problem;
         }
         if (problem instanceof IOException) {
            throw (IOException) problem;
         }
         sendProcessingError(problem, response);
      }
   }

   /**
    * Return the filter configuration object for this filter.
    */
   public FilterConfig getFilterConfig() {
      return (this.filterConfig);
   }

   /**
    * Set the filter configuration object for this filter.
    *
    * @param filterConfig The filter configuration object
    */
   public void setFilterConfig(FilterConfig filterConfig) {
      this.filterConfig = filterConfig;
   }

   /**
    * Destroy method for this filter
    */
   public void destroy() {
   }

   /**
    * Init method for this filter
    */
   public void init(FilterConfig filterConfig) {
      this.filterConfig = filterConfig;
      if (filterConfig != null) {
         if (debug) {
            log("AuthenticatedUserFilter: Initializing filter");
         }
      }
   }

   /**
    * Return a String representation of this object.
    */
   @Override
   public String toString() {
      if (filterConfig == null) {
         return ("AuthenticatedUserFilter()");
      }
      StringBuffer sb = new StringBuffer("AuthenticatedUserFilter(");
      sb.append(filterConfig);
      sb.append(")");
      return (sb.toString());

   }

   private void sendProcessingError(Throwable t, ServletResponse response) {
      String stackTrace = getStackTrace(t);

      if (stackTrace != null && !stackTrace.equals("")) {
         try {
            response.setContentType("text/html");
            PrintStream ps = new PrintStream(response.getOutputStream());
            PrintWriter pw = new PrintWriter(ps);
            pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

            // PENDING! Localize this for next official release
            pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
            pw.print(stackTrace);
            pw.print("</pre></body>\n</html>"); //NOI18N
            pw.close();
            ps.close();
            response.getOutputStream().close();
         } catch (Exception ex) {
         }
      }
      else {
         try {
            PrintStream ps = new PrintStream(response.getOutputStream());
            t.printStackTrace(ps);
            ps.close();
            response.getOutputStream().close();
         } catch (Exception ex) {
         }
      }
   }

   public static String getStackTrace(Throwable t) {
      String stackTrace = null;
      try {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         t.printStackTrace(pw);
         pw.close();
         sw.close();
         stackTrace = sw.getBuffer().toString();
      } catch (Exception ex) {
      }
      return stackTrace;
   }

   public void log(String msg) {
      filterConfig.getServletContext().log(msg);
   }

   /**
    * This request wrapper class extends the support class
    * HttpServletRequestWrapper, which implements all the methods in the
    * HttpServletRequest interface, as delegations to the wrapped request. You
    * only need to override the methods that you need to change. You can get
    * access to the wrapped request using the method getRequest()
    */
   class RequestWrapper extends HttpServletRequestWrapper {

      public RequestWrapper(HttpServletRequest request) {
         super(request);
      }

      // You might, for example, wish to add a setParameter() method. To do this
      // you must also override the getParameter, getParameterValues, getParameterMap,
      // and getParameterNames methods.
      protected Hashtable localParams = null;

      public void setParameter(String name, String[] values) {
         if (debug) {
            System.out.println("AuthenticatedUserFilter::setParameter(" + name + "=" + values + ")" + " localParams = " + localParams);
         }

         if (localParams == null) {
            localParams = new Hashtable();
            // Copy the parameters from the underlying request.
            Map wrappedParams = getRequest().getParameterMap();
            Set keySet = wrappedParams.keySet();
            for (Iterator it = keySet.iterator(); it.hasNext();) {
               Object key = it.next();
               Object value = wrappedParams.get(key);
               localParams.put(key, value);
            }
         }
         localParams.put(name, values);
      }

      @Override
      public String getParameter(String name) {
         if (debug) {
            System.out.println("AuthenticatedUserFilter::getParameter(" + name + ") localParams = " + localParams);
         }
         if (localParams == null) {
            return getRequest().getParameter(name);
         }
         Object val = localParams.get(name);
         if (val instanceof String) {
            return (String) val;
         }
         if (val instanceof String[]) {
            String[] values = (String[]) val;
            return values[0];
         }
         return (val == null ? null : val.toString());
      }

      @Override
      public String[] getParameterValues(String name) {
         if (debug) {
            System.out.println("AuthenticatedUserFilter::getParameterValues(" + name + ") localParams = " + localParams);
         }
         if (localParams == null) {
            return getRequest().getParameterValues(name);
         }
         return (String[]) localParams.get(name);
      }

      @Override
      public Enumeration getParameterNames() {
         if (debug) {
            System.out.println("AuthenticatedUserFilter::getParameterNames() localParams = " + localParams);
         }
         if (localParams == null) {
            return getRequest().getParameterNames();
         }
         return localParams.keys();
      }

      @Override
      public Map getParameterMap() {
         if (debug) {
            System.out.println("AuthenticatedUserFilter::getParameterMap() localParams = " + localParams);
         }
         if (localParams == null) {
            return getRequest().getParameterMap();
         }
         return localParams;
      }
   }

   /**
    * This response wrapper class extends the support class
    * HttpServletResponseWrapper, which implements all the methods in the
    * HttpServletResponse interface, as delegations to the wrapped response. You
    * only need to override the methods that you need to change. You can get
    * access to the wrapped response using the method getResponse()
    */
   class ResponseWrapper extends HttpServletResponseWrapper {

      public ResponseWrapper(HttpServletResponse response) {
         super(response);
      }
   }

}
