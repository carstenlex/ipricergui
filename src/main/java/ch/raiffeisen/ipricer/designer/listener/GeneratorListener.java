package ch.raiffeisen.ipricer.designer.listener;

import ch.raiffeisen.ipricer.designer.Designer;
import ch.raiffeisen.ipricer.designer.generator.Generator;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratorListener implements ActionListener {

    Generator generator;
    private Designer designer;

    public GeneratorListener( Designer designer) {
        this.designer = designer;
        this.generator = new Generator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(designer.getMainframe());
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println(selectedFile.getAbsolutePath());


        java.util.List<DesignComponent> designCOmponents = designer.getDesignComponents();
        String methodID = designer.getMethodIDName().getText();
        String methodLabel = designer.getMethodLabelName().getText();
        String parentLabel = designer.getParentLabelName().getText();
        String optionLabel = designer.getOptionLabelName().getText();
        String generatedDefFile = generator.generateMethod(methodID, methodLabel, parentLabel, optionLabel, designCOmponents);

        try (FileWriter fw = new FileWriter(selectedFile)) {
            fw.write(generatedDefFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
