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
	private static JFrame frame;
	private static Board board;
	private static Container pane;
	private static Dimension dim;
	
	private static JPanel contentPane;
	
	
	//Build and run the game
	public static void main(String[] args) {
		//Set look and feel to that of OS
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new JFrame("Virus Breaker");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(true);//�룞�쟻 �럹�씠吏�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);

		//-----------------------------
		//pane = frame.getContentPane();
		//pane.add(board);
		JLabel imageLabel = new JLabel();
		pane = frame.getContentPane();
		ImageIcon ii = new ImageIcon(frame.getClass().getResource("/res/giphy.gif"));
		imageLabel.setIcon(ii);
		pane.add(imageLabel, java.awt.BorderLayout.CENTER);
		
		//-----------------------------
		
		
		//Place frame in the middle of the screen
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		//Sets the icon of the program
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/Icon.png"));
		
		frame.setVisible(true);
	}
}
