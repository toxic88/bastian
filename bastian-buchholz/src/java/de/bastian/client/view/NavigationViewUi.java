package de.bastian.client.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Hyperlink;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;

import de.bastian.client.Application;

public class NavigationViewUi {

  private static LayoutContainer navigation = null;

  private static VBoxLayoutData vBoxLayoutData = new VBoxLayoutData(new Margins(0, 0, 5, 0));

  private NavigationViewUi() { }

  public static LayoutContainer get() {
    if (NavigationViewUi.navigation == null) {
      VBoxLayout layout = new VBoxLayout();
      layout.setVBoxLayoutAlign(VBoxLayout.VBoxLayoutAlign.STRETCH);
      final LayoutContainer n = new LayoutContainer(layout);
      n.setId("navigation");

      ContentPanel p;

      /**
       * Navigation
       */
      p = new ContentPanel();
      p.setHeading(Application.Messages.navigation());
      p.setFrame(true);

      p.add(new Hyperlink(Application.Messages.timetable(), Application.Events.TimeTable.getHash()));
      p.add(new Hyperlink(Application.Messages.user_manager(), Application.Events.User.getHash()));

      n.add(p, vBoxLayoutData);

      /**
       * User
       */
      p = new ContentPanel();
      p.setHeading(Application.Messages.user());
      p.setIcon(AbstractImagePrototype.create(Application.Resources.user()));
      p.setFrame(true);

      p.add(new Hyperlink(Application.Messages.login(), Application.Events.Login.getHash()));
      
      n.add(p, vBoxLayoutData);

      DeferredCommand.addCommand(new Command() {

        public void execute() {
          n.layout(true);
        }

      });

      navigation = n;
    }

    return navigation;
  }

}
