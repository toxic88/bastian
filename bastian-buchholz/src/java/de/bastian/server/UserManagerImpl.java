package de.bastian.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.bastian.client.UserManager;
import de.bastian.db.User;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class UserManagerImpl extends RemoteServiceServlet implements UserManager {

    private PersistenceManager getPersistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public boolean checkPassword(String password) {

        PersistenceManager pm = getPersistenceManager();

        User user = pm.getObjectById(User.class, 1);

        return user.checkPassword(password);
    }

    public void createUser(String firstname, String lastname, String password) {

        PersistenceManager pm = getPersistenceManager();

        User user = new User(firstname, lastname, password);

        try {
            pm.makePersistent(user);
        } finally {
            pm.close();
        }

    }

    public List<String[]> getList() {

        PersistenceManager pm = getPersistenceManager();

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
