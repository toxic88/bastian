package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(de.bastian.clan.server.domain.Topic.class)
public interface TopicRequest extends RequestContext {

    Request<TopicProxy> findTopic(Long id);
    Request<List<TopicProxy>> findAll();

    InstanceRequest<TopicProxy, Void> persist();
    InstanceRequest<TopicProxy, Void> remove();

}
