/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.model.MenuItem;
import com.sophos.semih.model.TMihUsuario;

/**
 *
 * @author miguel.altamiranda
 */
public class LoginFilter implements Filter {
    
	private static final Log log = LogFactory.getLog(Filter.class);
    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public LoginFilter() {
    }    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @SuppressWarnings({ "unused", "unchecked" })
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("loginFilter:doFilter()");
        }
        Throwable problem = null;
        HttpServletRequest req = null;
        try {
            req=(HttpServletRequest)request;
            HttpSession session = req.getSession(true);
            TMihUsuario o = (TMihUsuario)((session != null) ? session.getAttribute("logedUser") : null);
            ArrayList<MenuItem> menu= (ArrayList<MenuItem>)((session != null) ? session.getAttribute("userMenu") : null);
            String path= req.getPathInfo();
            log.info("PathInfo:"+path);
            
            
            if(path!=null && path.contains(".xhtml")){
                // Descomentar seguridad para menus

                if(!path.contains("login.xhtml")){
                    if(o==null){
                        req.getRequestDispatcher("/").forward(request, response);
                    }
                    else{
                    	if(menu!=null && !this.checkPathPerms((TMihUsuario)o, path, menu)){
	                    	throw new AccessDeniedException("Acceso denegado por falta de permisos");
	                    }
                    }
                }
                else{
                    if(o!=null){
                        req.getRequestDispatcher("main.xhtml").forward(request, response);
                    }                    
                }
            }
            
            if (path != null) {
            	try {
					chain.doFilter(request, response);
				} catch (Exception e) {
					log.error("filter chain exception", e);
				}
			} 
        } catch (ServletException e) {
        	if(e.getCause() instanceof ViewExpiredException){
	        	HttpServletResponse httpResponse = (HttpServletResponse) response;
	        	httpResponse.sendRedirect("main.xhtml");
        	}
    	} catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            log.error("filter chain exception", t);
        }
    }

	public boolean checkPathPerms(TMihUsuario user,String path,ArrayList<MenuItem> userMenu){
		if(path.contains("login.xhtml") || path.contains("main.xhtml")){
			return true;
		}
		for(MenuItem menuItem:userMenu){
			for(MenuItem menuhijos:menuItem.getHijos()){
				if(menuhijos.getUrl().toLowerCase().contains(path.toLowerCase())){
					return true;
				}
			}
		}
		return false;
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
                log("loginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("loginFilter()");
        }
        StringBuffer sb = new StringBuffer("loginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    @SuppressWarnings("unused")
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
            	log.error("Exception", ex);
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            	log.error("Exception", ex);
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        	log.error("Exception", ex);
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
}
