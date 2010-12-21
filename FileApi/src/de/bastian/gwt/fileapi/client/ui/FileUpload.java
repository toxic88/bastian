package de.bastian.gwt.fileapi.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.gwt.fileapi.client.file.FileList;

/**
 * @author Bastian Buchholz
 */
public class FileUpload extends com.google.gwt.user.client.ui.FileUpload {

    private final static String MULTIPLE = "multiple";

    public static FileUpload wrap(Element element) {
        // Assert that the element is attached.
        assert Document.get().getBody().isOrHasChild(element);

        FileUpload fileUpload = new FileUpload(element);

        // Mark it attached and remember it for cleanup.
        fileUpload.onAttach();
        RootPanel.detachOnWindowClose(fileUpload);

        return fileUpload;
    }

    public FileUpload() {
        super();
    }

    protected FileUpload(Element elem) {
        super(elem);
    }

    /**
     * http://dev.w3.org/html5/spec/common-input-element-attributes.html#dom-input-files
     * @return FileList
     */
    public final native FileList getFiles() /*-{
        return this.@de.bastian.gwt.fileapi.client.ui.FileUpload::getInputElement()().files;
    }-*/;

    private InputElement getInputElement() {
        return getElement().cast();
    }

    /**
     * http://www.w3.org/TR/html-markup/input.file.html#input.file.attrs.multiple
     * @return boolean
     */
    public boolean isMultiple() {
        return getElement().hasAttribute(MULTIPLE);
    }

    /**
     * http://www.w3.org/TR/html-markup/input.file.html#input.file.attrs.multiple
     * @param multiple
     */
    public void setMultiple(boolean multiple) {
        getElement().setPropertyString(MULTIPLE, multiple ? MULTIPLE : null);
    }

}
