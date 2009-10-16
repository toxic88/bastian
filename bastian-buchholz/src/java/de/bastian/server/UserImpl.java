package de.bastian.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.bastian.client.User;
import de.bastian.db.UserTable;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author bastian
 */
public class UserImpl extends RemoteServiceServlet implements User {

    private PersistenceManager getPersistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public void createUser(String firstname, String lastname) {

        PersistenceManager pm = getPersistenceManager();

        UserTable user = new UserTable(firstname, lastname);

        try {
            pm.makePersistent(user);
        } finally {
            pm.close();
        }

    }

    public List<String[]> getList() {

        PersistenceManager pm = getPersistenceManager();

        Query query = pm.newQuery(UserTable.class);

        List ret = new ArrayList();
        try {
            List<UserTable> users = (List<UserTable>) query.execute();

            for (UserTable user : users) {
                ret.add(user.toArray());
            }
        } finally {
            pm.close();
        }

        return ret;
    }

}
