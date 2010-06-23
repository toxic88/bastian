package de.dkfz.mga.antibodydb.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.dkfz.mga.antibodydb.shared.Images;
import de.dkfz.mga.antibodydb.shared.Lane;
import de.dkfz.mga.antibodydb.shared.Scannersettings;
import de.dkfz.mga.antibodydb.shared.Sds;
import java.util.ArrayList;

public class EditImageView extends Composite {

  interface EditImageViewUiBinder extends UiBinder<HTMLPanel, EditImageView> { }

  private static EditImageViewUiBinder uiBinder = GWT.create(EditImageViewUiBinder.class);

  @UiField HTMLPanel imageTemplate;

  @UiField InputElement Lysate_Protein_1;
  @UiField InputElement Lysate_Protein_2;
  @UiField InputElement Lysate_Protein_3;
  @UiField InputElement Lysate_Protein_4;
  @UiField InputElement Lysate_Protein_5;
  @UiField InputElement Lysate_Protein_6;
  @UiField InputElement Lysate_Protein_7;
  @UiField InputElement Lysate_Protein_8;
  @UiField InputElement Lysate_Protein_9;
  @UiField InputElement Lysate_Protein_10;
  @UiField InputElement Lysate_Protein_11;
  @UiField InputElement Lysate_Protein_12;
  @UiField InputElement Lysate_Protein_13;
  @UiField InputElement Lysate_Protein_14;
  @UiField InputElement Lysate_Protein_15;
  @UiField InputElement Total_Protein_1;
  @UiField InputElement Total_Protein_2;
  @UiField InputElement Total_Protein_3;
  @UiField InputElement Total_Protein_4;
  @UiField InputElement Total_Protein_5;
  @UiField InputElement Total_Protein_6;
  @UiField InputElement Total_Protein_7;
  @UiField InputElement Total_Protein_8;
  @UiField InputElement Total_Protein_9;
  @UiField InputElement Total_Protein_10;
  @UiField InputElement Total_Protein_11;
  @UiField InputElement Total_Protein_12;
  @UiField InputElement Total_Protein_13;
  @UiField InputElement Total_Protein_14;
  @UiField InputElement Total_Protein_15;

  @UiField InputElement Image;

  @UiField InputElement Signal;
  @UiField InputElement Sensitivity;
  @UiField InputElement Linear_Manual;
  @UiField InputElement Contrast;

  @UiField InputElement SDS;
  @UiField InputElement Acrylamid;
  @UiField InputElement Sep;
  @UiField InputElement Voltage;
  @UiField InputElement Size;

  public EditImageView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public Images getImage() {
    Images ret = new Images();
    ret.setImage(Image.getValue());
    return ret;
  }

  public Sds getSds() {
    Sds ret = new Sds();
    ret.setSds(SDS.getValue());
    ret.setAcrylamid(Acrylamid.getValue());
    ret.setSep(Sep.getValue());
    ret.setVoltage(Voltage.getValue());
    ret.setSize(Size.getValue());
    return ret;
  }

  public Scannersettings getScannersettings() {
    Scannersettings ret = new Scannersettings();
    ret.setSignal(Signal.getValue());
    ret.setSensitivity(Sensitivity.getValue());
    ret.setLinearManual(Linear_Manual.getValue());
    ret.setContrast(Contrast.getValue());
    return ret;
  }

  public Lane getLane() {
    Lane ret = new Lane();

    ArrayList<String> lysateProtein = new ArrayList<String>();
    lysateProtein.add(Lysate_Protein_1.getValue());
    lysateProtein.add(Lysate_Protein_2.getValue());
    lysateProtein.add(Lysate_Protein_3.getValue());
    lysateProtein.add(Lysate_Protein_4.getValue());
    lysateProtein.add(Lysate_Protein_5.getValue());
    lysateProtein.add(Lysate_Protein_6.getValue());
    lysateProtein.add(Lysate_Protein_7.getValue());
    lysateProtein.add(Lysate_Protein_8.getValue());
    lysateProtein.add(Lysate_Protein_9.getValue());
    lysateProtein.add(Lysate_Protein_10.getValue());
    lysateProtein.add(Lysate_Protein_11.getValue());
    lysateProtein.add(Lysate_Protein_12.getValue());
    lysateProtein.add(Lysate_Protein_13.getValue());
    lysateProtein.add(Lysate_Protein_14.getValue());
    lysateProtein.add(Lysate_Protein_15.getValue());
    ret.setLysateProtein(lysateProtein);

    ArrayList<String> totalProtein = new ArrayList<String>();
    totalProtein.add(Total_Protein_1.getValue());
    totalProtein.add(Total_Protein_2.getValue());
    totalProtein.add(Total_Protein_3.getValue());
    totalProtein.add(Total_Protein_4.getValue());
    totalProtein.add(Total_Protein_5.getValue());
    totalProtein.add(Total_Protein_6.getValue());
    totalProtein.add(Total_Protein_7.getValue());
    totalProtein.add(Total_Protein_8.getValue());
    totalProtein.add(Total_Protein_9.getValue());
    totalProtein.add(Total_Protein_10.getValue());
    totalProtein.add(Total_Protein_11.getValue());
    totalProtein.add(Total_Protein_12.getValue());
    totalProtein.add(Total_Protein_13.getValue());
    totalProtein.add(Total_Protein_14.getValue());
    totalProtein.add(Total_Protein_15.getValue());
    ret.setTotalProtein(totalProtein);

    return ret;
  }

}
