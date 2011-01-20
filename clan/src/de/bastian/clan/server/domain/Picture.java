package de.bastian.clan.server.domain;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Version;

import com.google.appengine.api.datastore.Text;

import de.bastian.clan.server.ValidationException;
import de.bastian.clan.shared.UserProxy;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Picture {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Text image;

    @Persistent
    private String description;

    @Persistent
    private Date created;

    @Persistent
    private Date changed;

    @Persistent
    private Long user;

    @Persistent
    @Version
    private Integer version;


    public static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public static Picture findPicture(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(Picture.class, id);
        } catch(RuntimeException e) {
            return null;
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Picture> findPictures(int start, int end) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(Picture.class);
            query.setRange(start, end);
            query.setOrdering("created DESC");

            List<Picture> results = (List<Picture>) query.execute();
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    public static int countPictures() {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery("select count() from de.bastian.clan.server.domain.Picture");

            return (Integer) query.execute();
        } finally {
            pm.close();
        }
    }

    public void persist() throws ValidationException {
        if (getDescription() == null || getDescription().trim().isEmpty() ||
                getImage() == null || getImage().trim().isEmpty() ||
                getUser() == null || User.getCurrentUser() == null) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            if (getId() == null) { // only on create...
                setCreated(new Date());
                setVersion(1);
            } else if (getUser() == User.getCurrentUser().getId() || User.getCurrentUser().getType().equals(UserProxy.Type.Admin)) {
                setChanged(new Date());
                setVersion(getVersion() + 1);
            } else {
                throw new ValidationException();
            }

            pm.makePersistent(this);
        } finally {
            pm.close();
        }
    }

    public void remove() throws ValidationException {
        if (User.getCurrentUser() == null || (getUser() != User.getCurrentUser().getId() && !User.getCurrentUser().getType().equals(UserProxy.Type.Admin))) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            Picture picture = pm.getObjectById(Picture.class, getId());
            pm.deletePersistent(picture);
        } finally {
            pm.close();
        }
    }


    public Picture() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        if (image == null) {
            return null;
        }
        return image.getValue();
    }

    public void setImage(String image) {
        if (image == null) {
            this.image = null;
        } else {
            this.image = new Text(image);
        }
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
