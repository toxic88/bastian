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

import de.bastian.clan.server.ValidationException;
import de.bastian.clan.shared.UserProxy;

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

    // REQUEST METHODS //

    /**
     * Returns the PersistenceManager
     * @return PersistenceManager
     */
    public static final PersistenceManager persistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    /**
     * Returns the Post with the corresponding id
     * @param id
     * @return Post
     */
    public static Post findPost(Long id) {
        if (id == null) {
            return null;
        }

        PersistenceManager pm = persistenceManager();

        try {
            return pm.getObjectById(Post.class, id);
        } catch(RuntimeException e) {
            return null;
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
            query.setOrdering("created DESC"); // TODO: this can be better!

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
    public static List<Post> findPosts(Long theme, int start, int end) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(Post.class, "theme == :themeParam");
            query.setRange(start, end);
            query.setOrdering("created ASC");

            List<Post> results = (List<Post>) query.execute(theme);
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Post> findAllPosts(Long theme) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery(Post.class, "theme == :themeParam");

            List<Post> results = (List<Post>) query.execute(theme);
            results.size(); // stupid check to receive all posts

            return results;
        } finally {
            pm.close();
        }
    }

    public static int countPosts(Long theme) {
        PersistenceManager pm = persistenceManager();

        try {
            Query query = pm.newQuery("select count() from de.bastian.clan.server.domain.Post where theme == :themeParam");

            return (Integer) query.execute(theme);
        } finally {
            pm.close();
        }
    }

    /**
     * Creates or updates the Post
     * @throws ValidationException
     */
    public void persist() throws ValidationException {
        if (getTitle() == null || getTitle().trim().isEmpty() ||
                getText() == null || getText().trim().isEmpty() ||
                getUser() == null && User.getCurrentUser() == null) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            if (getId() == null) { // only on create...
                setCreated(new Date());
                setVersion(1);
                setUser(User.getCurrentUser().getId());
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

    /**
     * Removes the PushClient
     * @throws ValidationException
     */
    public void remove() throws ValidationException {
        if (User.getCurrentUser() == null || (getUser() != User.getCurrentUser().getId() && !User.getCurrentUser().getType().equals(UserProxy.Type.Admin))) {
            throw new ValidationException();
        }

        PersistenceManager pm = persistenceManager();

        try {
            Post post = pm.getObjectById(Post.class, getId());

            if (post.getTheme() == null) {
                List<Post> posts = Post.findAllPosts(post.getId());
                for (Post p : posts) {
                    p.remove();
                }
            }
            pm.deletePersistent(post);
        } finally {
            pm.close();
        }
    }

    // CONSTRUCTOR //

    /**
     * Default constructor is required by the RequestFactory
     */
    public Post() {}

    // GETTER and SETTER //

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
        this.title = title.trim();
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
            this.text = new Text(text.trim());
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
