package de.bastian.clan.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Version;

import com.google.appengine.api.datastore.Text;

import de.bastian.clan.shared.ValidationException;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Post {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String title;

    @Persistent
    private Text text;

    @Persistent
    private Date created;

    @Persistent
    private Date changed;

    @Persistent
    private Long user;

    @Persistent
    private Long theme;

    @Persistent
    private Long topic;

    @Persistent
    @Version
    private Integer version;


    public static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public static Post findPost(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(Post.class, id);
        } finally {
            pm.close();
        }
    }

    public static List<Post> findAll() {
        PersistenceManager pm = persistenceManager();

        List<Post> list = new ArrayList<Post>();

        try {
            Extent<Post> extent = pm.getExtent(Post.class, false);
            for (Post t : extent) {
                list.add(t);
            }
        } finally {
            pm.close();
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<Post> findThemes(Long topic, int start, int end) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(Post.class, "theme == null && topic == :topicParam");
            query.setRange(start, end);

            List<Post> results = (List<Post>) query.execute(topic);
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    public static int countThemes(Long topic) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery("select count() from de.bastian.clan.server.domain.Post where theme == null && topic == :topicParam");

            return (Integer) query.execute(topic);
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Post> findPosts(Long parent, int start, int end) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(Post.class, "post == :postParam");
            query.setRange(start, end);

            List<Post> results = (List<Post>) query.execute(parent);
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    public static int countPosts(Long parent) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery("select count() from de.bastian.clan.server.domain.Post where post == :postParam");

            return (Integer) query.execute(parent);
        } finally {
            pm.close();
        }
    }

    public void persist() throws ValidationException {
        if (getTitle() == null || getTitle().trim().isEmpty() ||
                getText() == null || getText().trim().isEmpty() ||
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
            Post post = pm.getObjectById(Post.class, getId()); // TODO: handle post.getPost() != null

            pm.deletePersistent(post);
        } finally {
            pm.close();
        }
    }


    public Post() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        if (text == null) {
            return null;
        }
        return text.getValue();
    }

    public void setText(String text) {
        if (text == null) {
            this.text = null;
        } else {
            this.text = new Text(text);
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

    public Long getTheme() {
        return theme;
    }

    public void setTheme(Long theme) {
        this.theme = theme;
    }

    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
