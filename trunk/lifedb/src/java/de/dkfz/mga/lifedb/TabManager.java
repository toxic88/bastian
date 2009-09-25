package de.dkfz.mga.lifedb;

import javax.faces.event.ActionEvent;

import java.io.Serializable;

public class TabManager implements Serializable {

    private int activeTabPanel = 0;

    public void showWelcome(ActionEvent e) {
        this.setActiveTabPanel(0);
    }

    public void showSearchClones(ActionEvent e) {
        this.setActiveTabPanel(1);
    }

    public void showSearchPathways(ActionEvent e) {
        this.setActiveTabPanel(2);
    }

    public void showSearchSubcellularLocations(ActionEvent e) {
        this.setActiveTabPanel(3);
    }

    public void setActiveTabPanel(int index) {
        this.activeTabPanel = index;
    }

    public int getActiveTabPanel() {
        return this.activeTabPanel;
    }

}
