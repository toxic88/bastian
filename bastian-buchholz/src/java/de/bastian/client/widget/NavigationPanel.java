package de.bastian.client.widget;

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

public class NavigationPanel {

  private static LayoutContainer navigation = null;

  private static VBoxLayoutData vBoxLayoutData = new VBoxLayoutData(new Margins(0, 0, 5, 0));

  private NavigationPanel() {
  }

  public static LayoutContainer get() {
    if (NavigationPanel.navigation == null) {
      VBoxLayout layout = new VBoxLayout();
      layout.setVBoxLayoutAlign(VBoxLayout.VBoxLayoutAlign.STRETCH);
      final LayoutContainer n = new LayoutContainer(layout);
      n.setId("navigation");

      ContentPanel p;

      /**
       * Navigation
       */
      p = new ContentPanel();
      p.setHeading("Navigation");
      p.setFrame(true);

      p.add(new Hyperlink("Timetable", "" + Application.Events.TimeTable));
      p.add(new Hyperlink("User manager", "" + Application.Events.User));

      n.add(p, vBoxLayoutData);

      /**
       * User
       */
      p = new ContentPanel();
      p.setHeading("User");
      p.setIcon(AbstractImagePrototype.create(Application.Resources.user()));
      p.setFrame(true);

      p.add(new Hyperlink("" + Application.Events.Login, "" + Application.Events.Login));
      
      n.add(p, vBoxLayoutData);

      DeferredCommand.addCommand(new Command() {

        public void execute() {
          n.layout();
        }

      });

      navigation = n;
    }

    return navigation;
  }

}
