package de.dkfz.mga.antibodydb.client.presenter;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import de.dkfz.mga.antibodydb.client.view.EditView;

public class EditPresenter implements Presenter {

  private final EditView view;

  private static EditPresenter instance = null;

  private EditPresenter() {
    view = new EditView(this);
  }

  public void resetForm(FormPanel f) {
    f.reset();
  }

  public Widget asWidget() {
    return view;
  }

  public static EditPresenter get() {
    if (EditPresenter.instance == null) {
      EditPresenter.instance = new EditPresenter();
    }

    return EditPresenter.instance;
  }

}
