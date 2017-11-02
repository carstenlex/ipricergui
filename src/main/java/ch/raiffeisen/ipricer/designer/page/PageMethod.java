package ch.raiffeisen.ipricer.designer.page;

import ch.raiffeisen.ipricer.designer.PropertiesPanel;

public class PageMethod extends Page {
    public PageMethod(PropertiesPanel propertiesPanel) {
        super(propertiesPanel);
    }

    @Override
    public PageType getPageType() {
        return PageType.METHOD;
    }
}
