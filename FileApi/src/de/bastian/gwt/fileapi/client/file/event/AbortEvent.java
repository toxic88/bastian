package de.bastian.gwt.fileapi.client.file.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * http://www.w3.org/TR/FileAPI/#dfn-abort-event
 * @author Bastian Buchholz
 */
public class AbortEvent extends AbstractFileEvent<AbortEvent.Handler> {

    public AbortEvent(boolean lengthComputable, int loaded, int total) {
        super(lengthComputable, loaded, total);
    }

    public interface Handler extends EventHandler {
        void onAbort(AbortEvent e);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onAbort(this);
    }

}