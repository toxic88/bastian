package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import de.dkfz.mga.antibodydb.client.Antibodydb;
import de.dkfz.mga.antibodydb.client.presenter.EditPresenter;
import java.util.ArrayList;

public class EditView extends Composite {

  interface EditViewUiBinder extends UiBinder<HTMLPanel, EditView> { }

  private static EditViewUiBinder uiBinder = GWT.create(EditViewUiBinder.class);

  private EditPresenter presenter = null;

  private ArrayList<String> numbers = new ArrayList<String>();

  @UiField FormPanel antibodyForm;
  @UiField FormPanel commentsForm;
  @UiField FormPanel newFieldsForm;
  @UiField FormPanel targetProteinForm;
  @UiField FormPanel incubationprotocolForm;
  @UiField FormPanel buffersetForm;
  @UiField FormPanel imagesForm;
  @UiField FormPanel otherForm;

  private ArrayList<EditImageView> images = new ArrayList<EditImageView>();

  @UiField Image antibodyReset;
  @UiField Image commentsReset;
  @UiField Image newFieldsReset;
  @UiField Image targetProteinReset;
  @UiField Image incubationprotocolReset;
  @UiField Image buffersetReset;
  @UiField Image imagesReset;
  @UiField Image otherReset;

  @UiField TabLayoutPanel imageTabs;

  @UiField Anchor reset;
  @UiField Button save;

  @UiField PopupPanel fileuploadDialog;
  @UiField FormPanel fileuploadForm;
  @UiField Button upload;
  @UiField Hyperlink cancel;


  public EditView(EditPresenter presenter) {
    initWidget(uiBinder.createAndBindUi(this));
    this.presenter = presenter;

    numbers.add("Zero");
    numbers.add("One");
    numbers.add("Two");
    numbers.add("Three");
    numbers.add("Four");
    numbers.add("Five");
    numbers.add("Six");
    numbers.add("Seven");
    numbers.add("Eight");
    numbers.add("Nine");
    numbers.add("Ten");

    initImageTabs();
    initFileuploadForm();
  }

  public void showFileuploadDialog() {
    fileuploadDialog.center();
    fileuploadDialog.show();
  }

  private void initImageTabs() {
    Image add = new Image(Antibodydb.Resources.add());
    add.setAltText("Add new image");
    add.setTitle("Add new image");
    add.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent e) {
        addImageTab();
      }
    });
    imageTabs.add(new HTMLPanel("Please click on the other tabs."), add);

    addImageTab();
  }

  private void addImageTab() {
    images.add(new EditImageView());
    imageTabs.insert(images.get(images.size() - 1), numbers.get(imageTabs.getWidgetCount()), imageTabs.getWidgetCount() - 1);

    (new Timer() {
      @Override
      public void run() {
        imageTabs.selectTab(imageTabs.getWidgetCount() - 2);
      }
    }).schedule(20);
  }

  private void removeImageTab() {
    if (imageTabs.getWidgetCount() > 2) {
      images.remove(imageTabs.getWidgetCount() - 2);
      imageTabs.remove(imageTabs.getWidgetCount() - 2);
    } else if (presenter != null) {
      presenter.resetForm(imagesForm);
    }
  }

  private void initFileuploadForm() {
    fileuploadForm.setAction(GWT.getModuleBaseURL()  + "fileupload");
    fileuploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
    fileuploadForm.setMethod(FormPanel.METHOD_POST);

    fileuploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event) {
        if (Antibodydb.Token.contains("datasheet")) {

        } else {
          EditImageView eiv = images.get(imageTabs.getSelectedIndex());
          eiv.Image.setValue(event.getResults());
        }
        fileuploadForm.reset();
        fileuploadDialog.hide();
        History.newItem("edit");
      }
    });

  }

  @UiHandler("reset")
  void handleResetClick(ClickEvent e) {
    handleAntibodyResetClick(e);
    handleCommentsResetClick(e);
    handleNewFieldsResetClick(e);
    handleTargetProteinResetClick(e);
    handleIncubationprotocolResetClick(e);
    handleBuffersetResetClick(e);
    handleImagesResetClick(e);
    handleOtherResetClick(e);
  }

  @UiHandler("antibodyReset")
  void handleAntibodyResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(antibodyForm);
    }
  }

  @UiHandler("commentsReset")
  void handleCommentsResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(commentsForm);
    }
  }

  @UiHandler("newFieldsReset")
  void handleNewFieldsResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(newFieldsForm);
    }
  }

  @UiHandler("targetProteinReset")
  void handleTargetProteinResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(targetProteinForm);
    }
  }

  @UiHandler("incubationprotocolReset")
  void handleIncubationprotocolResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(incubationprotocolForm);
    }
  }

  @UiHandler("buffersetReset")
  void handleBuffersetResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(buffersetForm);
    }
  }

  @UiHandler("imagesReset")
  void handleImagesResetClick(ClickEvent e) {
    removeImageTab();
  }

  @UiHandler("otherReset")
  void handleOtherResetClick(ClickEvent e) {
    if (presenter != null) {
      presenter.resetForm(otherForm);
    }
  }

  @UiHandler("upload")
  void handleUploadClick(ClickEvent e) {
    fileuploadForm.submit();
  }

  @UiHandler("cancel")
  void handleCancelClick(ClickEvent e) {
    fileuploadDialog.hide();
  }

}
