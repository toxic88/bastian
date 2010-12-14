package de.bastian.clan.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import de.bastian.clan.shared.ValidationException;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Topic {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    private Date created;

    @Persistent
    private Date changed;

    @Persistent
    private Long user;

    @Persistent
    private Integer version;


    private static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public static Topic findTopic(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(Topic.class, id);
        } finally {
            pm.close();
        }
    }

    public static List<Topic> findAll() {
        PersistenceManager pm = persistenceManager();

        List<Topic> list = new ArrayList<Topic>();

        try {
            Extent<Topic> extent = pm.getExtent(Topic.class, false);
            for (Topic t : extent) {
                list.add(t);
            }
        } finally {
            pm.close();
        }

        return list;
    }

    public void persist() throws ValidationException {
        if (getName() == null || getName().trim().isEmpty() ||
                getUser() == null || User.isLoggedIn() == null) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            if (getId() == null) { // only on create...
                setCreated(new Date());
                setVersion(1);
            } else {
                setChanged(new Date());
                setVersion(getVersion() + 1);
            }

            pm.makePersistent(this);
        } finally {
            pm.close();
        }
    }

    public void remove() {
        PersistenceManager pm = persistenceManager();

        try {
            Topic topic = pm.getObjectById(Topic.class, getId());
            pm.deletePersistent(topic);
        } finally {
            pm.close();
        }
    }


    public Topic() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
