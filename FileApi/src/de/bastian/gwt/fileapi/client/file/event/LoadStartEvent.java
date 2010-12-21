package de.bastian.gwt.fileapi.client.file.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * http://www.w3.org/TR/FileAPI/#dfn-loadstart-event
 * @author Bastian Buchholz
 */
public class LoadStartEvent extends AbstractFileEvent<LoadStartEvent.Handler> {

    public LoadStartEvent(boolean lengthComputable, int loaded, int total) {
        super(lengthComputable, loaded, total);
    }

    public interface Handler extends EventHandler {
        void onLoadStart(LoadStartEvent e);
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onLoadStart(this);
    }

}
