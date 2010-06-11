package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import de.dkfz.mga.antibodydb.client.Antibodydb;
import de.dkfz.mga.antibodydb.client.presenter.EditPresenter;
import java.util.ArrayList;

public class EditView extends Composite {

  interface EditViewUiBinder extends UiBinder<HTMLPanel, EditView> { }

  private static EditViewUiBinder uiBinder = GWT.create(EditViewUiBinder.class);

  private EditPresenter presenter = null;

  ArrayList<String> numbers = new ArrayList<String>();

  @UiField FormPanel antibodyForm;
  @UiField FormPanel commentsForm;
  @UiField FormPanel newFieldsForm;
  @UiField FormPanel targetProteinForm;
  @UiField FormPanel incubationprotocolForm;
  @UiField FormPanel buffersetForm;
  @UiField FormPanel imagesForm;
  @UiField FormPanel otherForm;

  @UiField Image antibodyReset;
  @UiField Image commentsReset;
  @UiField Image newFieldsReset;
  @UiField Image targetProteinReset;
  @UiField Image incubationprotocolReset;
  @UiField Image buffersetReset;
  @UiField Image imagesReset;
  @UiField Image otherReset;

  @UiField TabLayoutPanel imageTabs;
  @UiField HTMLPanel imageTemplate;

  @UiField Anchor reset;
  @UiField Button save;

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

  public void addImageTab() {
    Element clone = DOM.clone(imageTemplate.getElement(), true);

    imageTabs.insert(new HTMLPanel(clone.getInnerHTML()), numbers.get(imageTabs.getWidgetCount()), imageTabs.getWidgetCount() - 1);

    (new Timer() {
      @Override
      public void run() {
        imageTabs.selectTab(imageTabs.getWidgetCount() - 2);
      }
    }).schedule(20);
  }

  public void removeImageTab() {
    if (imageTabs.getWidgetCount() > 2) {
      imageTabs.remove(imageTabs.getWidgetCount() - 2);
    } else if (presenter != null) {
      presenter.resetForm(imagesForm);
    }
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

}
