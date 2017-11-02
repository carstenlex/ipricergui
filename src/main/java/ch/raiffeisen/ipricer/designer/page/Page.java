package ch.raiffeisen.ipricer.designer.page;

import ch.raiffeisen.ipricer.designer.PropertiesPanel;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Page extends JPanel {

    public static int COLUMNS = 6;
    public static int ROWS = 15;



    protected GridBagLayout gbl = new GridBagLayout();

    protected JLabel xcoord, ycoord, xcellLabel, ycellLabel, targetCellLabel;
    protected int xcell, ycell;

    protected CellHandler cellHandler;

    protected PropertiesPanel propertiesPanel;


    public Page(PropertiesPanel propertiesPanel) {
        this.propertiesPanel = propertiesPanel;
        PageActionListener mouseAdapter = new PageActionListener(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setLayout(gbl);
        cellHandler = new CellHandler( gbl);
        initGrid();

        gbl.layoutContainer(this);
    }

    public java.util.List<DesignComponent> getDesignComponents(){

        List<DesignComponent> componentList = Arrays.stream(getComponents()).filter(c -> c instanceof DesignComponent).map(c -> (DesignComponent)c).collect(Collectors.toList());
        return componentList;
    }

    public abstract PageType getPageType();

    public PropertiesPanel getPropertiesPanel() {
        return propertiesPanel;
    }

    private void initGrid() {

        initPageLabel();
        initColumnHeader();
        initRowHeader();
        initCells();
        addMousePositionLabels();
    }

    private void initPageLabel() {
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            JLabel component = new JLabel(getPageType().name());
            component.setForeground(Color.red);
            component.setBackground(Color.orange);
            setConstraints(component, gbc);
            add(component);
    }

    private void initRowHeader() {
        GridBagConstraints gbc = new GridBagConstraints();
        for(int y=1; y<=ROWS; y++) {
            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            JLabel component = new JLabel("Row "+(y-1));
            setConstraints(component, gbc);
            add(component);
        }
    }

    private void initColumnHeader() {
        GridBagConstraints gbc = new GridBagConstraints();
        for(int x=1; x<=COLUMNS; x++) {
            gbc.gridx = x;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel component = new JLabel("Col "+(x-1));
            setConstraints(component, gbc);
            add(component);
        }
    }

    private void initCells() {
        xcell=1;
        ycell=1;
    }

    public int getXcell() {
        return xcell;
    }

    public int getYcell() {
        return ycell;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int[][] dim = gbl.getLayoutDimensions();
        int cols = dim[0].length;
        int rows = dim[1].length;

        g.setColor(Color.BLUE);
        int x = 0; //dim[0][0];
        for (int i =0; i< cols; i++)
        {
            x += dim[0][i];
            g.drawLine(x, 0, x, getHeight());
        }
        int y = 0; //dim[1][0];
        for (int i=0; i< rows; i++)
        {
            y += dim[1][i];
            g.drawLine(0, y, getWidth(), y);
        }
    }

    public void setConstraints(JComponent comp, GridBagConstraints gbc){
        gbl.setConstraints(comp,gbc);
    }


    private  void addMousePositionLabels() {
        int LAST_LINE = ROWS + 2;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = LAST_LINE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        xcoord = new JLabel("Maus X-coord ");
        gbl.setConstraints(xcoord, gbc);
        add(xcoord);

        gbc.gridx = 1;
        gbc.gridy = LAST_LINE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        xcellLabel = new JLabel("xcellLabel ");
        gbl.setConstraints(xcellLabel, gbc);
        add(xcellLabel);

        gbc.gridx = 2;
        gbc.gridy = LAST_LINE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        ycoord = new JLabel("Maus Y-coord ");
        gbl.setConstraints(ycoord, gbc);
        add(ycoord);

        gbc.gridx = 3;
        gbc.gridy = LAST_LINE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        ycellLabel = new JLabel("Maus Y-coord ");
        gbl.setConstraints(ycellLabel, gbc);
        add(ycellLabel);

        gbc.gridx = 4;
        gbc.gridy = LAST_LINE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        targetCellLabel = new JLabel("targetCell: ");
        gbl.setConstraints(targetCellLabel, gbc);
        add(targetCellLabel);
    }

    public void layoutContainer() {
        gbl.layoutContainer(this);
    }

    public GridBagLayout getGBL() {
        return gbl;
    }

    public void setMauslabels(Point mauskoordinaten, Point zelle) {
        xcoord.setText("X:" + mauskoordinaten.getX());
        ycoord.setText("Y:" + mauskoordinaten.getY());
        xcellLabel.setText("xcellLabel:" + zelle.x);
        ycellLabel.setText("ycellLabel:" + zelle.y);
    }

    public Point bestimmeZelleAusMauskoordinate(Point koordinaten){
        return cellHandler.bestimmeZelleAusMauskoordinate(koordinaten);
    }


    public void updateConstraints(JComponent comp, Point zelle) {
        GridBagConstraints constraints = gbl.getConstraints(comp);
        constraints.gridx = zelle.x;
        constraints.gridy = zelle.y;
        gbl.setConstraints(comp, constraints);
    }

    public void updateTargetInfo(Point neueKoordinaten) {
        if(neueKoordinaten == null){
            targetCellLabel.setText("");
            return;
        }

        Point targetZelle = bestimmeZelleAusMauskoordinate(neueKoordinaten);
        targetCellLabel.setText("targetCell: "+(targetZelle.x-1)+"/"+(targetZelle.y-1));
    }
}
