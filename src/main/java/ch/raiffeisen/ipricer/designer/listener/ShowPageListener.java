package ch.raiffeisen.ipricer.designer.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPageListener implements ActionListener {
    private final String showPage;
    private final JPanel threePages;

    public ShowPageListener(JPanel threePages, String pageMethod) {
        this.showPage = pageMethod;
        this.threePages = threePages;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout layout = (CardLayout) threePages.getLayout();
        layout.show(threePages, showPage);
    }
}
