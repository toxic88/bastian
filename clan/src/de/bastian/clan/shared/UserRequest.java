package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(de.bastian.clan.server.domain.User.class)
public interface UserRequest extends RequestContext {

    Request<UserProxy> findUser(Long id);
    Request<UserProxy> findByEmail(String email);
    Request<List<UserProxy>> findAll();
    Request<UserProxy> login(String email, String password);
    Request<Void> logout();
    Request<UserProxy> getCurrentUser();

    InstanceRequest<UserProxy, Void> persist();
    InstanceRequest<UserProxy, Void> remove();

}
