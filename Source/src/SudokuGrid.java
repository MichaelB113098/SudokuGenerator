import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class SudokuGrid extends JPanel {


	public SudokuPanel sudokuArray[][] = new SudokuPanel[9][9];

        SudokuGrid(int w, int h) {
            super(new GridBagLayout());
            sudokuArray = new SudokuPanel[w][h];

            GridBagConstraints c = new GridBagConstraints();
            /** construct the grid */
            for (int i=0; i<w; i++) {
                for (int j=0; j<h; j++) {
                    c.weightx = 1.0;
                    c.weighty = 1.0;
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = i;
                    c.gridy = j;
                    SudokuPanel sPanel = new SudokuPanel(i, j);
                    sudokuArray[i][j] = sPanel;
                    add(sPanel, c);
                    
                }
            }

            /** create a black border */ 
            setBorder(BorderFactory.createLineBorder(Color.black)); 

        }

    }