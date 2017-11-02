package ch.raiffeisen.ipricer.designer.page.component.types;

import ch.raiffeisen.ipricer.designer.page.Page;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;

//TODO Klasse ändern, wenn im Properties-panel isSeparator angklickt wird
public class Separator extends DesignComponent {
    public Separator(Page page, String labelText, int gridY, int gridX) {
        super(page, labelText, gridX, gridY);

        properties.isSeparator = true;
    }
}
