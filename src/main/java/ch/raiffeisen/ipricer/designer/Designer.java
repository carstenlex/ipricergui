package ch.raiffeisen.ipricer.designer;

import ch.raiffeisen.ipricer.designer.listener.GeneratorListener;
import ch.raiffeisen.ipricer.designer.listener.ShowPageListener;
import ch.raiffeisen.ipricer.designer.page.Page;
import ch.raiffeisen.ipricer.designer.page.PageChild;
import ch.raiffeisen.ipricer.designer.page.PageMethod;
import ch.raiffeisen.ipricer.designer.page.PageParent;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Designer {

    public static final String PAGE_METHOD = "pageMethod";
    public static final String PAGE_PARENT = "pageParent";
    public static final String PAGE_CHILD = "pageChild";


    JPanel threePages = new JPanel(new CardLayout());
    PropertiesPanel propertiesPanel ;
    Page pageMethod ;
    Page pageParent ;
    Page pageChild ;

    JFrame mainFrame;

    JTextField optionLabelName;
    JTextField parentLabelName;
    JTextField methodLabelName;
    JTextField methodIDName;


    public Designer() {
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(1024, 768));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        Container frameContentPane = mainFrame.getContentPane();
        propertiesPanel = createPropertiesPanel();
        frameContentPane.add(createLeftSide(), BorderLayout.LINE_START);
        frameContentPane.add(createCenter(propertiesPanel),BorderLayout.CENTER);
        frameContentPane.add(propertiesPanel, BorderLayout.LINE_END);


        mainFrame.pack();
        mainFrame.setVisible(true);


    }

    private PropertiesPanel createPropertiesPanel() {

        propertiesPanel = new PropertiesPanel();

        return propertiesPanel;
    }

    private JPanel createCenter(PropertiesPanel propertiesPanel) {
        pageMethod = new PageMethod(propertiesPanel);
        pageParent = new PageParent(propertiesPanel);
        pageChild = new PageChild(propertiesPanel);
        threePages.add(pageMethod, PAGE_METHOD);
        threePages.add(pageParent, PAGE_PARENT);
        threePages.add(pageChild, PAGE_CHILD);
        return threePages;
    }

    private JPanel createLeftSide() {
        JPanel leftSide = new JPanel();
        leftSide.setLayout( new BoxLayout(leftSide, BoxLayout.PAGE_AXIS));

        JButton showMethodPage = new JButton("Methode");
        showMethodPage.addActionListener(new ShowPageListener(threePages,PAGE_METHOD));
        JButton showParentPage = new JButton("Parent");
        showParentPage.addActionListener(new ShowPageListener(threePages,PAGE_PARENT));
        JButton showChildPage = new JButton("Child");
        showChildPage.addActionListener(new ShowPageListener(threePages,PAGE_CHILD));
        leftSide.add(showMethodPage);
        leftSide.add(showParentPage);
        leftSide.add(showChildPage);

        Dimension groesse = new Dimension(250, 25);
        methodIDName= new JTextField();
        methodIDName.setMaximumSize(groesse);
        methodLabelName= new JTextField();
        methodLabelName.setMaximumSize(groesse);
        parentLabelName = new JTextField();
        parentLabelName.setMaximumSize(groesse);
        optionLabelName = new JTextField();
        optionLabelName.setMaximumSize(groesse);
        leftSide.add(Box.createVerticalStrut(100));
        leftSide.add(new JLabel("Methode:"));
        leftSide.add(methodIDName);
        leftSide.add(new JLabel("MethodenLabel:"));
        leftSide.add(methodLabelName);
        leftSide.add(new JLabel("ParentLabel:"));
        leftSide.add(parentLabelName);
        leftSide.add(new JLabel("OptionLabel:"));
        leftSide.add(optionLabelName);

        leftSide.add(Box.createVerticalGlue());

        JButton generate = new JButton("Generate Def-File");
        generate.addActionListener(new GeneratorListener(this));
        leftSide.add(generate);
        return leftSide;
    }

    public static void main(String[] argv) throws Exception {
        Designer designer = new Designer();
    }


    public JFrame getMainframe() {
        return mainFrame;
    }

    public JTextField getOptionLabelName() {
        return optionLabelName;
    }

    public JTextField getParentLabelName() {
        return parentLabelName;
    }

    public JTextField getMethodLabelName() {
        return methodLabelName;
    }

    public JTextField getMethodIDName() {
        return methodIDName;
    }

    public java.util.List<DesignComponent> getDesignComponents() {
        java.util.List<DesignComponent> components = new ArrayList<>();
        components.addAll(pageChild.getDesignComponents());
        components.addAll(pageParent.getDesignComponents());
        components.addAll(pageMethod.getDesignComponents());
        return components;
    }
}



