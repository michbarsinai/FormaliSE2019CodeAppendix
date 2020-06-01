package il.ac.bgu.cs.bp.visualrunningexamples;

import il.ac.bgu.cs.bp.visualrunningexamples.MazeTableModel.CellValue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Renders the cells in the monitor table;
 * 
 * @author michael
 */
public class MazeTableCellRenderer implements TableCellRenderer {
    
    private final JLabel wallLbl, spaceLbl;
    
    private static final int MAX_VISIBLE_AGE = 15;
    
    private static final Color TRAP_COLOR = new Color(255,0,0);
    private static final Color HOT_SPACE_COLOR = new Color(255, 200, 0);
    private static final Color HOT_SPACE_FG_COLOR = new Color(255, 232, 139);
    private static final Color SPACE_COLOR = Color.WHITE;
    private static final Color CURRENT_CELL_COLOR = new Color(128,128,255);
    
    public MazeTableCellRenderer() {
        Font f = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        wallLbl = new JLabel();
        wallLbl.setHorizontalAlignment(SwingConstants.CENTER);
        wallLbl.setBackground(Color.BLACK);
        wallLbl.setBackground(Color.LIGHT_GRAY);
        wallLbl.setFont(f);
        wallLbl.setOpaque(true);
        
        spaceLbl = new JLabel();
        spaceLbl.setHorizontalAlignment(SwingConstants.CENTER);
        spaceLbl.setFont(f);
        spaceLbl.setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if ( value instanceof MazeTableModel.CellValue ) {
            MazeTableModel.CellValue cv = (MazeTableModel.CellValue) value;
            JLabel outputLabel = wallLbl;
            spaceLbl.setForeground(Color.BLACK);
            switch ( cv.type ) {
                case SPACE:
                case HOT_SPACE:
                    Color cellColor = cv.type==CellValue.Type.SPACE ? SPACE_COLOR : HOT_SPACE_COLOR;
                    if ( cv.age == -1 ) {
                        spaceLbl.setBackground( cellColor );
                    } else {
                        double mixRatio = 0.9*((double)MAX_VISIBLE_AGE-Math.min(cv.age, MAX_VISIBLE_AGE))/((double)MAX_VISIBLE_AGE);
                        spaceLbl.setBackground( combine(cellColor, CURRENT_CELL_COLOR, mixRatio) );
                    }
                    spaceLbl.setForeground( cv.type==CellValue.Type.HOT_SPACE ? HOT_SPACE_FG_COLOR : Color.BLACK );
                    spaceLbl.setText(cv.age == 0 ? "•" : String.valueOf(cv.value));
                    outputLabel = spaceLbl;
                    break;
                    
                case TRAP:
                    spaceLbl.setBackground(TRAP_COLOR);
                    spaceLbl.setForeground(Color.YELLOW);
                    spaceLbl.setText(cv.age == 0 ? "•" : "t");
                    outputLabel = spaceLbl;
                    break;
                    
                case WALL:
                    wallLbl.setText(String.valueOf(cv.value));
                    outputLabel = wallLbl;
                    break;
            }
            
            
            return outputLabel;
            
        } else {
            System.out.println("Value not a CellValue: " + value);
            return new JLabel("/!\\" + Objects.toString(value));
        }
    }

    private Color combine( Color c1, Color c2, double bias) {
        return new Color( 
            (int)(c1.getRed()*(1-bias)   + c2.getRed()*bias), 
            (int)(c1.getGreen()*(1-bias) + c2.getGreen()*bias),
            (int)(c1.getBlue()*(1-bias)  + c2.getBlue()*bias) 
        );
    }
    
}
