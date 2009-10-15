package de.bastian.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.bastian.client.User;
import de.bastian.db.UserTable;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author bastian
 */
public class UserImpl extends RemoteServiceServlet implements User {
    public String myMethod() {

        PersistenceManager pm = PMF.get().getPersistenceManager();

//        UserTable e = new UserTable("Alfred", "Smith");
//
//        try {
//            pm.makePersistent(e);
//        } finally {
//            pm.close();
//        }

        Query query = pm.newQuery(UserTable.class);
        String ret = "";

        try {
            List<UserTable> users = (List<UserTable>) query.execute();
            if (users.iterator().hasNext()) {
                for (UserTable user : users) {
                    ret += user.getFirstName() + ", ";
                }
            } else {
                ret = "No records!";
            }
        } finally {
            pm.close();
        }

        return ret;
    }

}
