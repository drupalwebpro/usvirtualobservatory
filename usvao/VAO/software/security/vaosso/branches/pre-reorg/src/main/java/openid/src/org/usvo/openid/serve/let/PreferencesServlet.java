package org.usvo.openid.serve.let;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.openid4java.OpenIDException;
import org.usvo.openid.serve.AuthnAttempt;
import org.usvo.openid.serve.IdServer;
import org.usvo.openid.ui.LoginUI;
import org.usvo.openid.ui.PreferencesPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Manage user preferences -- that is, permissions granted by default as well as to individual portals.
 *  If not logged in, present a login interface first. */
public class PreferencesServlet extends DbSessionServlet {
    private static final Log log = LogFactory.getLog(DbSessionServlet.class);

    public PreferencesServlet() { super(true); }

    @Override
    protected void service(Session hibernateSession, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, OpenIDException
    {
        AuthnAttempt authn = IdServer.getInstance(getServletContext()).authenticate(request, response);
        // logged in --> show preferences page
        if (authn.isSuccessful())
            new PreferencesPage(authn.getUsername(), request, response).handle();
        // not logged in --> show login page
        else {
            LoginUI ui = new LoginUI(null, request, response, authn, authn.getUsername(), null);
            String path = request.getContextPath() + request.getServletPath();
            log.trace("Login form path = " + path);
            // return to this servlet, rather than an OpenID Relying Party
            ui.setInternalReturnTo(path, "Privacy Settings");
            ui.displayLoginForm();
        }
    }
}
