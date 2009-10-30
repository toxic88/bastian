package de.bastian.server;

import de.bastian.server.rpc.MyRemoteServiceServlet;
import de.bastian.client.UserManager;
import de.bastian.db.User;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class UserManagerImpl extends MyRemoteServiceServlet implements UserManager {

    public boolean login(String username, String password) {
        PersistenceManager pm = this.getPersistenceManager();

        Query query = pm.newQuery(User.class, "username == usernameParam");
        query.declareParameters("String usernameParam");

        List<User> users = (List<User>) query.execute(username);

        for (User user : users) {
            if (user.checkPassword(password)) {
                this.getSession().setAttribute("user", user);
                return true;
            }
        }
        
        return false;
    }

    public boolean createUser(String username, String password) {
        PersistenceManager pm = this.getPersistenceManager();

        Query query = pm.newQuery(User.class, "username == usernameParam");
        query.declareParameters("String usernameParam");

        List<User> users = (List<User>) query.execute(username);

        if (users.size() > 0) {
            return false;
        }

        try {
            pm.makePersistent(new User(username, password));
        } finally {
            pm.close();
        }
        return true;
    }

    public List<String[]> getAll() {
        PersistenceManager pm = this.getPersistenceManager();
        Query query = pm.newQuery(User.class);

        List ret = new ArrayList();
        try {
            List<User> users = (List<User>) query.execute();
            for (User user : users) {
                ret.add(user.toArray());
            }
        } finally {
            pm.close();
        }

        return ret;
    }

}
