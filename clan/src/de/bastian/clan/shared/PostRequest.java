package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(de.bastian.clan.server.domain.Post.class)
public interface PostRequest extends RequestContext {

    Request<PostProxy> findPost(Long id);
    Request<List<PostProxy>> findAll();
    Request<List<PostProxy>> findThemes(Long topic, int start, int end);
    Request<Integer> countThemes(Long topic);
    Request<List<PostProxy>> findPosts(Long parent, int start, int end);
    Request<Integer> countPosts(Long parent);

    InstanceRequest<PostProxy, Void> persist();
    InstanceRequest<PostProxy, Void> remove();

}