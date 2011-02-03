package de.bastian.gwt.fileapi.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.gwt.fileapi.client.file.FileReader;
import de.bastian.gwt.fileapi.client.file.FileReaderListener;
import de.bastian.gwt.fileapi.client.file.ProgressEvent;
import de.bastian.gwt.fileapi.client.ui.FileUpload;

/**
 * @author Bastian Buchholz
 */
public class FileApi implements EntryPoint {

    private final Label error = new Label("Your browser does not support the File API");
    private final Image image = new Image();
    private final FileUpload upload = new FileUpload();

    @Override
    public void onModuleLoad() {
        upload.setMultiple(false);

        upload.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (FileReader.isSupported()) {
                    final FileReader fileReader = new FileReader(new FileReaderListener() {
                        @Override
                        public void onLoadEnd(ProgressEvent e) {
                            image.setUrl((String) getFileReader().getResult());
                        }
                    });
                    fileReader.readAsDataURL(upload.getFiles().get(0));
                } else {
                   RootPanel.get().add(error);
                }
            }
        });

        RootPanel.get().add(upload);
        RootPanel.get().add(image);
    }
}
