package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.ServiceName;

@ServiceName("de.bastian.clan.server.domain.Picture")
public interface PictureRequest extends RequestContext {

    Request<PictureProxy> findPicture(Long id);
    Request<List<PictureProxy>> findPictures(int start, int end);
    Request<Integer> countPictures();

    InstanceRequest<PictureProxy, Void> persist();
    InstanceRequest<PictureProxy, Void> remove();

}
