package ch.raiffeisen.ipricer.designer.page;

import ch.raiffeisen.ipricer.designer.PropertiesPanel;

public class PageParent extends Page{
    public PageParent(PropertiesPanel propertiesPanel) {
        super(propertiesPanel);
    }

    @Override
    public PageType getPageType() {
        return PageType.PARENT;
    }
}
