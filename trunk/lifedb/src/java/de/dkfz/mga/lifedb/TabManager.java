package de.dkfz.mga.lifedb;

import javax.faces.component.UIPanel;
import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class TabManager implements Serializable {

    private int tabIndex = 0;
    private List tabs = new ArrayList();

    public TabManager() {
        Tab t1 = new Tab("asdf 1", new UIPanel(), 0);
        this.add(t1);
        Tab t2 = new Tab("asdf 2", new UIPanel(), 1);
        this.add(t2);
    }

    public void add(Tab tab) {
        this.tabs.add(tab);
    }

    public void add(Tab[] tabs) {
        for (Tab tab : tabs) {
            this.add(tab);
        }
    }

    public void remove(Tab tab) {
        this.tabs.remove(tab);
    }

    public void removeAll() {
        this.tabs.clear();
        this.tabIndex = 0;
    }

    public List getTabs() {
        return this.tabs;
    }

    public int getTabsSize(){
        return this.tabs.size();
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public class Tab {

        private String label;
        private UIPanel content;
        private int index;
        private boolean removable = false;

        public Tab() {

        }

        public Tab(String label, UIPanel content, int index) {
            this.setLabel(label);
            this.setContent(content);
            this.setIndex(index);
        }

        public String getLabel() {
            return this.label;
        }

        public UIPanel getContent() {
            return this.content;
        }

        public int getIndex() {
            return this.index;
        }

        public boolean isRemovable() {
            return this.removable;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setContent(UIPanel content) {
            this.content = content;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setRemovable(boolean removable) {
            this.removable = removable;
        }

        public void closeTab(ActionEvent event) {
           TabManager.this.tabs.remove(this);
        }

    }

}
