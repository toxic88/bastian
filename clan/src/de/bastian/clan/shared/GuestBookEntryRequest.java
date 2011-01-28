package de.bastian.clan.shared;

import java.util.List;

import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.ServiceName;

@ServiceName("de.bastian.clan.server.domain.GuestBookEntry")
public interface GuestBookEntryRequest extends RequestContext {

    Request<GuestBookEntryProxy> findGuestBookEntry(Long id);
    Request<List<GuestBookEntryProxy>> findAll();
    Request<List<GuestBookEntryProxy>> findGuestBookEntrys(int start, int end);

    InstanceRequest<GuestBookEntryProxy, Void> persist();
    InstanceRequest<GuestBookEntryProxy, Void> remove();

}
