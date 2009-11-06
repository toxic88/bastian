package de.bastian.server.rpc;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class RemoteServiceServlet extends com.google.gwt.user.server.rpc.RemoteServiceServlet {

  private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

  protected PersistenceManagerFactory getPersistenceManagerFactory() {
    return RemoteServiceServlet.pmfInstance;
  }

  protected PersistenceManager getPersistenceManager() {
    return this.getPersistenceManagerFactory().getPersistenceManager();
  }

  protected HttpSession getSession() {
    return this.getThreadLocalRequest().getSession(true);
  }

  protected void addCookie(Cookie cookie) {
    this.getThreadLocalResponse().addCookie(cookie);
  }

  protected void addCookie(String name, String value) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    this.addCookie(cookie);
  }

  protected void removeCookie(String name) {
    Cookie cookie = this.getCookie(name);
    if (cookie != null) {
      cookie.setMaxAge(0);
      cookie.setPath("/");
      this.addCookie(cookie);
    }
  }

  protected Cookie getCookie(String name) {
    Cookie[] cookies = this.getThreadLocalRequest().getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }

}
