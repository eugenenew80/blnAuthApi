package kz.kegoc.bln.service.auth;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class ActiveDirectory {
    public static LdapContext getConnection(String username, String password) throws NamingException {
        String domainName = "corp.kegoc.kz";
        String ldapURL = "ldap://" + domainName + '/';
        String principalName = username + "@" + domainName;

        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.SECURITY_PRINCIPAL, principalName);
        if (password!=null)
            props.put(Context.SECURITY_CREDENTIALS, password);

        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, ldapURL);

        try {
            return new InitialLdapContext(props, null);
        }

        catch(javax.naming.CommunicationException e){
            throw new NamingException("Failed to connect to " + domainName);
        }

        catch(NamingException e){
            throw new NamingException("Failed to authenticate ");
        }
    }
}
