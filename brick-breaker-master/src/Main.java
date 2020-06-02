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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Class definition
public class Main extends JFrame implements Constants {
	//Variables
	public static JFrame frame;
	//private static Board board;
	private static Container pane;
	private static Dimension dim;
	static ImageIcon button;
	JScrollPane scrollPane;
	 //---------------------------------배경
	//static ImageIcon icon;
	ImageIcon back;
	
	GridBagConstraints[] gbc = new GridBagConstraints[30];
	
    public Main() {
        setTitle("virus breaker");
        back = new ImageIcon("./img/earth.jpg");
    	
		JPanel background = new JPanel(new GridLayout(1,2)) {
			public void paintComponent(Graphics g) {
				g.drawImage(back.getImage(),0,0,getWidth(), getHeight(),null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		
    	setTitle("virus breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		
		JPanel leftPanel = new JPanel(new GridLayout(5,1)) {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		Image login = new ImageIcon("./img/L.png").getImage();
		login = login.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        JButton Logg = new JButton(new ImageIcon(login));
        Logg.setBorderPainted(false); //버튼 외곽선 삭제
        Logg.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        Logg.setFocusPainted(false); //버튼 눌리는 부분 삭제
        Logg.setPreferredSize(new Dimension(100, 100));
        Logg.addActionListener(new ActionListener() {
            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	Board.gameMode = 0;
                new gameWindow(); // 클래스 newWindow를 새로 만들어낸다
                setVisible(false);
            }      
        });
        leftPanel.add(Logg);
		
        
        JPanel rightPanel = new JPanel(new GridLayout(3,1)) {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
    	//베이직 모드 버튼
    	Image button1 = new ImageIcon("./img/B.png").getImage();
    	button1 = button1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        JButton OpenBasic = new JButton(new ImageIcon(button1));
        OpenBasic.setBorderPainted(false); //버튼 외곽선 삭제
        OpenBasic.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenBasic.setFocusPainted(false); //버튼 눌리는 부분 삭제
        OpenBasic.setPreferredSize(new Dimension(100, 100));
        OpenBasic.addActionListener(new ActionListener() {
            // 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	Board.gameMode = 0;
                new gameWindow(); // 클래스 newWindow를 새로 만들어낸다
                setVisible(false);
            }      
        });
//        gbc[0] = new GridBagConstraints();
//        gbc[0].gridx = 0;
//        gbc[0].gridy = 0;
        
    	//하드 모드 버튼
    	Image button2 = new ImageIcon("./img/H.png").getImage();
    	button2 = button2.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        JButton OpenHard = new JButton(new ImageIcon(button2));
        OpenHard.setBorderPainted(false); //버튼 외곽선 삭제
        OpenHard.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenHard.setFocusPainted(false); //버튼 눌리는 부분 삭제 
        OpenHard.setPreferredSize(new Dimension(100, 100));
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
        for(int u = 1; u < 20; u++) {
        	gbc[u] = new GridBagConstraints();
        }
//        gbc[20] = new GridBagConstraints();
//        gbc[20].gridx = 20;
//        gbc[20].gridy = 0;
        
        //background.add(OpenBasic,gbc[0]);
        //background.add(OpenHard,gbc[20]);
        rightPanel.add(OpenBasic);
        rightPanel.add(OpenHard);
        
        background.add(leftPanel);
        background.add(rightPanel);
        
        setLocationByPlatform(true);
        setSize(1280,720);
        setResizable(true);
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