package ch.raiffeisen.ipricer.designer.page;

import java.awt.*;

public class CellHandler {

    private GridBagLayout gbl;
    private Point cell;

    public CellHandler(GridBagLayout gbl) {
        this.gbl = gbl;
        this.cell = new Point(1,1);
    }

    public Point bestimmeZelleAusMauskoordinate(Point e) {
        int[][] dim = gbl.getLayoutDimensions();
        int cols = dim[0].length;
        int rows = dim[1].length;

        int sum = 0;
        int i=0;
        while(sum <= e.getX() && i<cols){
            sum += dim[0][i++];
        }
        cell.x = i-1;

        sum=0;
        i=0;
        while(sum <= e.getY() && i<rows){
            sum += dim[1][i++];
        }
        cell.y = i-1;
        return cell;
    }

}
