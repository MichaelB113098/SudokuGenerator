import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
class SudokuPanel extends JPanel {

        String digit; //the number it would display
        int x, y; //the x,y position on the grid

        SudokuPanel(int x, int y) {
            super();

            this.x = x;
            this.y = y;
            

            /** create a black border */
            setBorder(BorderFactory.createLineBorder(Color.black));

            /** set size to 50x50 pixel for one square */
            setPreferredSize(new Dimension(50,50));
        }

        public String getDigit() { return digit; }

        //getters for x and y

        public void setDigit(String num, Color color) { 
        	
        	removeAll();
        	digit = num; 
        	JLabel label = new JLabel("" + num);
        	label.setForeground(Color.black);
        	setBackground(color);
        	label.setFont(new Font("Arial Black", Font.BOLD, 30));
        	label.setHorizontalAlignment(SwingConstants.CENTER);
        	label.setVerticalAlignment(SwingConstants.CENTER);
        	add(label);
        	repaint();
        
        }

    }