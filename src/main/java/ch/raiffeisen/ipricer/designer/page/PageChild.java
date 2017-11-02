package ch.raiffeisen.ipricer.designer.page;

import ch.raiffeisen.ipricer.designer.PropertiesPanel;

public class PageChild extends Page {

    public PageChild(PropertiesPanel propertiesPanel) {
        super(propertiesPanel);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHILD;
    }
}
