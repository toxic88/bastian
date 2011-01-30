package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.ServiceName;

@ServiceName("de.bastian.clan.server.domain.PushClient")
public interface PushClientRequest extends RequestContext {

    Request<PushClientProxy> findPushClient(Long id);
    Request<List<PushClientProxy>> findAll();
    Request<PushClientProxy> create(String name);

    InstanceRequest<PushClientProxy, Void> persist();
    InstanceRequest<PushClientProxy, Void> remove();

}
