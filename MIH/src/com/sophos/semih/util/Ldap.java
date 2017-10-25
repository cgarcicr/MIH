/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.util;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.common.ApplicationSetup;
import com.sun.jndi.ldap.LdapCtxFactory;

/**
 *
 * @author miguel.altamiranda
 */
public class Ldap {
	
    private static final Log log = LogFactory.getLog(Ldap.class);
    
    public boolean doLogin(String userName, String password) {
//        Hashtable<String, String> env = new Hashtable<String, String>();
//        
//        String servidor=ApplicationSetup.getInstance().getParamValue("LDAP_SERVER");
//        String dn=ApplicationSetup.getInstance().getParamValue("LDAP_DN");
//        
//		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		log.info("servidor: "+servidor);
//		log.info("dn: "+dn);
//		log.info("userName: "+userName);
//		log.info("password: "+password);
//		log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        
//        String principalName = userName + "@" + dn;
//        
//
//        env.put(Context.SECURITY_PRINCIPAL, principalName);
//        env.put(Context.SECURITY_CREDENTIALS, password);
// 
//        try {
//            DirContext dc = LdapCtxFactory.getLdapCtxInstance(servidor, env);
//            log.info("Authentication succeeded!");
//            
//            //String searchFilter = "(& (userPrincipalName="+principalName+")(objectClass=user))";
//            String searchFilter = "(&(objectClass=user)(sAMAccountName=" + userName + "))";
//            
//            
//            SearchControls controls = new SearchControls();
//            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            NamingEnumeration<SearchResult> renum = dc.search(this.toDC(dn),searchFilter, controls);
//            
//            if(!renum.hasMore()) {
//                throw new AuthenticationException("Cannot locate user information for " + userName);
//            }
//            
//            dc.close();
//            return true;
//        }
//        catch (AuthenticationException ex) {
//        	log.error("AuthenticationException", ex);
//            return false;
//        }
//        catch (NamingException ex) {
//        	log.error("NamingException", ex);
//            return false;
//        }
//        catch (Exception ex) {
//        	log.error("Exception", ex);
//            return false;
//        }
//        
        return true;
    }
    private String toDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if(token.length()==0)   continue;   // defensive check
            if(buf.length()>0)  buf.append(",");
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }
}