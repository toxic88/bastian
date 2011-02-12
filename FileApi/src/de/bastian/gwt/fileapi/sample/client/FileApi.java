package de.bastian.gwt.fileapi.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.bastian.gwt.fileapi.client.file.File;
import de.bastian.gwt.fileapi.client.file.FileReader;
import de.bastian.gwt.fileapi.client.file.FileReaderListener;
import de.bastian.gwt.fileapi.client.file.ProgressEvent;
import de.bastian.gwt.fileapi.client.file.URL;
import de.bastian.gwt.fileapi.client.ui.FileUpload;

/**
 * Simple demonstration of the FileAPI in GWT
 * @author Bastian Buchholz
 */
public class FileApi implements EntryPoint {

    private final Label error = new Label("Your browser does not support the File API");
    private final FlowPanel panel = new FlowPanel();
    private final FileUpload upload = new FileUpload();

    @Override
    public void onModuleLoad() {
        upload.setMultiple(true); // you can upload multiple files at once!

        upload.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                panel.clear();
                if (FileReader.isSupported()) { // check if the FileReader is supported by your browser
                    for (int i = 0; i < upload.getFiles().length(); i++) { // loop through a selected files
                        final File file = upload.getFiles().get(i);
                        if (URL.isSupported()) {
                            if (file.getType().startsWith("image/")) {
                                final Image img = new Image(URL.createObjectURL(file)); // for displaying just the image this method is enough if it is supported
                                img.addLoadHandler(new LoadHandler() {
                                    @Override
                                    public void onLoad(LoadEvent event) {
                                        URL.revokeObjectURL(img.getUrl()); // releasing memory
                                    }
                                });
                                panel.add(img);
                            }
                            panel.add(new Label(file.getName()));
                        } else {
                            FileReader fileReader = new FileReader(new FileReaderListener() {
                                @Override
                                public void onProgress(ProgressEvent event) {
                                    // calculate how much % is already read
                                    GWT.log("onProgress: " + Math.round((event.getLoaded() * 100) / event.getTotal()) + "%");
                                }
                                @Override
                                public void onLoadEnd(ProgressEvent e) {
                                    GWT.log("onLoadEnd");
                                    String result = (String) getFileReader().getResult(); // the base64 encoded file contents
                                    if (result.startsWith("data:image")) { // check if its an image. you can also use file.getType()
                                        panel.add(new Image(result)); // create an image and show the file!
                                    }
                                    panel.add(new Label(file.getName())); // show the name of the file
                                }
                                @Override
                                public void onLoadStart(ProgressEvent event) {
                                    GWT.log("onLoadStart");
                                }
                                @Override
                                public void onAbort(ProgressEvent event) {
                                    GWT.log("onAbort");
                                }
                                @Override
                                public void onError(ProgressEvent event) {
                                    GWT.log("onError: " + getFileReader().getError().getCode());
                                }
                                @Override
                                public void onLoad(ProgressEvent event) {
                                    GWT.log("onLoad");
                                }
                            });
                            fileReader.readAsDataURL(file); // read the file! it will be base64 encoded
                        }
                    }
                } else { // if the browser doesn't support the FileReader API show an error message
                   RootPanel.get().add(error);
                }
            }
        });

        RootPanel.get().add(upload);
        RootPanel.get().add(panel);
    }
}
