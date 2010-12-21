package de.bastian.gwt.fileapi.client.file.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Bastian Buchholz
 */
public abstract class AbstractFileEvent<H extends EventHandler> extends GwtEvent<H> {

    private boolean lengthComputable;
    private int loaded;
    private int total;

    public AbstractFileEvent(boolean lengthComputable, int loaded, int total) {
        this.lengthComputable = lengthComputable;
        this.loaded = loaded;
        this.total = total;
    }

    public boolean getLengthComputable() {
        return lengthComputable;
    }

    public int getLoaded() {
        return loaded;
    }

    public int getTotal() {
        return total;
    }

}
