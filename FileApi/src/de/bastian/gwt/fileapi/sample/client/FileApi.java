package de.bastian.gwt.fileapi.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.gwt.fileapi.client.file.FileReader;
import de.bastian.gwt.fileapi.client.file.event.LoadEndEvent;
import de.bastian.gwt.fileapi.client.file.exception.BrowserNotSupportedException;
import de.bastian.gwt.fileapi.client.ui.FileUpload;

/**
 * @author Bastian Buchholz
 */
public class FileApi implements EntryPoint {

    private final Label error = new Label("Your browser is not supported");
    private final Image image = new Image();
    private final FileUpload upload = new FileUpload();

    @Override
    public void onModuleLoad() {
        upload.setMultiple(false);

        upload.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                try {
                    final FileReader fileReader = new FileReader();

                    fileReader.addLoadEndHandler(new LoadEndEvent.Handler() {
                        @Override
                        public void onLoadEnd(LoadEndEvent e) {
                            image.setUrl((String) fileReader.getResult());
                        }
                    });

                    fileReader.readAsDataURL(upload.getFiles().get(0));
                } catch(BrowserNotSupportedException e) {
                   RootPanel.get().add(error);
                }
            }
        });

        RootPanel.get().add(upload);
        RootPanel.get().add(image);

        GWT.log("" + upload.isMultiple());
    }
}
