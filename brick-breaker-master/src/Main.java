/*
*    Brick Breaker, Version 1.2
*    By Ty-Lucas Kelley
*	
*	 **LICENSE**
*
*	 This file is a part of Brick Breaker.
*
*	 Brick Breaker is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    Brick Breaker is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with Brick Breaker.  If not, see <http://www.gnu.org/licenses/>.
*/

//This "Main" class runs the game. 

//Imports
import javax.swing.*;
import java.awt.*;

//Class definition
public class Main extends JFrame implements Constants {
	//Variables
	public static JFrame frame;
	private static Board board;
	private static Container pane;
	private static Dimension dim;
	//private static JPanel jpane;
	
	JPanel background = new JPanel() {
        public void paintComponent(Graphics g) {
       	 ImageIcon ii = new ImageIcon(frame.getClass().getResource("./img/background.jpg"));
       	 // Dispaly image at at full size
            g.drawImage(ii.getImage(), 0, 0, null);
            // Approach 2: Scale image to size of component
            // Dimension d = getSize();
            // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
            // Approach 3: Fix the image position in the scroll pane
            // Point p = scrollPane.getViewport().getViewPosition();
            // g.drawImage(icon.getImage(), p.x, p.y, null);
            setOpaque(false); //그림을 표시하게 설정,투명하게 조절
            super.paintComponent(g);
        }
    };
	
	//Build and run the game
	public static void main(String[] args) {
		
		JPanel jpane = new JPanel();
		
		//Set look and feel to that of OS
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new JFrame("Virus Breaker");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(true);//동적페이지
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);

		//-----------------------------
		pane = frame.getContentPane();
		pane.add(board);
		
		
		/*
		JLabel imageLabel = new JLabel();
		pane = frame.getContentPane();
		ImageIcon ii = new ImageIcon(frame.getClass().getResource("./img/giphy.gif"));
		imageLabel.setIcon(ii);
		pane.add(imageLabel, java.awt.BorderLayout.CENTER);
		*/
		//-----------------------------
	
		//Place frame in the middle of the screen
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		//Sets the icon of the program
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/Icon.png"));
		
		frame.setVisible(true);
	}
	
	 
	
}
