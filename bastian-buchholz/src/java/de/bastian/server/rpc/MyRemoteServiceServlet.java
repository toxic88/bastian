package de.bastian.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpSession;

public class MyRemoteServiceServlet extends RemoteServiceServlet {

    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    protected PersistenceManagerFactory getPersistenceManagerFactory() {
        return MyRemoteServiceServlet.pmfInstance;
    }

    protected PersistenceManager getPersistenceManager() {
        return this.getPersistenceManagerFactory().getPersistenceManager();
    }

    protected HttpSession getSession() {
        return this.getThreadLocalRequest().getSession(true);
    }

}
