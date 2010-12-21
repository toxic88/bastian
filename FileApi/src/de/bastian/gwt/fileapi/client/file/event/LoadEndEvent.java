package de.bastian.gwt.fileapi.client.file.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * http://www.w3.org/TR/FileAPI/#dfn-loadend-event
 * @author Bastian Buchholz
 */
public class LoadEndEvent extends AbstractFileEvent<LoadEndEvent.Handler> {

    public LoadEndEvent(boolean lengthComputable, int loaded, int total) {
        super(lengthComputable, loaded, total);
    }

    public interface Handler extends EventHandler {
        void onLoadEnd(LoadEndEvent e);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onLoadEnd(this);
    }

}
