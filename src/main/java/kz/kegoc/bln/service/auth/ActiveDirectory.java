package kz.kegoc.bln.service.auth;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class ActiveDirectory {
    private static String[] userAttributes = {
        "distinguishedName",
        "cn",
        "name",
        "uid",
        "sn",
        "givenname",
        "memberOf",
        "samaccountname",
        "userPrincipalName"
    };

    private ActiveDirectory() { }

    public static LdapContext getConnection(String username, String password) throws NamingException {
        return getConnection(username, password, null, null);
    }

    public static LdapContext getConnection(String username, String password, String domainName) throws NamingException {
        return getConnection(username, password, domainName, null);
    }

    public static LdapContext getConnection(String username, String password, String domainName, String serverName) throws NamingException {
        if (domainName==null) {
            try {
                String fqdn = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                if (fqdn.split("\\.").length>1)
                    domainName = fqdn.substring(fqdn.indexOf(".")+1);
            }

            catch(java.net.UnknownHostException e) {}
        }

        if (password!=null) {
            password = password.trim();
            if (password.length()==0)
                password = null;
        }

        Hashtable props = new Hashtable();
        String principalName = username + "@" + domainName;
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        if (password!=null)
            props.put(Context.SECURITY_CREDENTIALS, password);

        String ldapURL = "ldap://" + ((serverName==null) ? domainName : serverName + "." + domainName) + '/';
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, ldapURL);

        try {
            return new InitialLdapContext(props, null);
        }

        catch(javax.naming.CommunicationException e){
            throw new NamingException("Failed to connect to " + domainName + ((serverName==null)? "" : " through " + serverName));
        }

        catch(NamingException e){
            throw new NamingException("Failed to authenticate " + username + "@" + domainName + ((serverName==null)? "" : " through " + serverName));
        }
    }
}
