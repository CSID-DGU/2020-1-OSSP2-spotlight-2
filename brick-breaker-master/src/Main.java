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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Class definition
public class Main extends JFrame implements Constants {
	//Variables
	public static JFrame frame;
	//private static Board board;
	private static Container pane;
	private static Dimension dim;

	//---------------------------------배경
	//static ImageIcon icon;

    public Main() {
        
        setTitle("virus breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel MainContainer = new JPanel();
        setContentPane(MainContainer);
        
        JButton OpenBasic = new JButton("게임시작[베이직]");
        OpenBasic.addActionListener(new ActionListener() {
            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	Board.gameMode = 0;
                new gameWindow(); // 클래스 newWindow를 새로 만들어낸다
                setVisible(true);
            }      
        });
        
        JButton OpenHard = new JButton("게임시작[하드]");    
        OpenHard.addActionListener(new ActionListener() {
            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	Board.gameMode = 1;
                new gameWindow(); // 클래스 newWindow를 새로 만들어낸다
                setVisible(false);
            }     
        });
        MainContainer.add(OpenBasic);
        MainContainer.add(OpenHard);
        setSize(500,500);
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Main();
    }
}
	
	class gameWindow extends JFrame implements Constants {
		static ImageIcon icon;
		private static Board board;
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
		
	    gameWindow() {
			icon = new ImageIcon("./img/background.gif");
			setTitle("Virus breaker");
			Dimension dim = new Dimension(750, 750);
			setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			setResizable(true);//동적페이지
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setMinimumSize(dim);
			board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT);

			//-----------------------------
			add(board);

			//Place frame in the middle of the screen
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);

			//Sets the icon of the program
			setIconImage(Toolkit.getDefaultToolkit().getImage("img/Icon.png"));
			setVisible(true);			
	}
}