/*
*    Brick Breaker, Version 1.2
*    By Ty-Lucas Kelley
*   
*    **LICENSE**
*
*    This file is a part of Brick Breaker.
*
*    Brick Breaker is free software: you can redistribute it and/or modify
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Class definition
public class Main extends JFrame implements Constants {
	//Variables
	public JPanel background;
	public static Main M;
	public static gameWindow G;
	public static Clip clip;
	//private static Board board;
	private BorderLayout layout = new BorderLayout();
	private static Container pane;
	private static Dimension dim;
	static ImageIcon button;
	JScrollPane scrollPane;
	 //---------------------------------배경
	//static ImageIcon icon;
	ImageIcon back;

    public Main() {
        setTitle("virus breaker"); // 타이틀 설정
        Music();
        back = new ImageIcon("./img/earth.jpg");  	
		JPanel background = new JPanel(new GridLayout(1,3)) {

			public void paintComponent(Graphics g) {
				g.drawImage(back.getImage(),0,0,getWidth(), getHeight(),null);

				setOpaque(false);
				super.paintComponent(g);
			}
		};

 
        setContentPane(background);   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		
		JPanel leftPanel = new JPanel(new GridLayout(1,1)) {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		JPanel middlePanel = new JPanel(new GridLayout(6,1))
		{
			public void paintComponent(Graphics g) {
				setOpaque(false);
				super.paintComponent(g);
			}
		};
        
        JPanel rightPanel = new JPanel(new GridLayout(1,1)) {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		 JButton blank1 = new JButton();
		 JButton blank2 = new JButton();
		 blank1.setBorderPainted(false);
		 blank2.setBorderPainted(false);
		 blank1.setContentAreaFilled(false);
		 blank2.setContentAreaFilled(false);


    	//베이직 모드 버튼
    	Image button1 = new ImageIcon("./img/BasicMode.png").getImage();
    	button1 = button1.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);
        JButton OpenBasic = new JButton(new ImageIcon(button1));
        OpenBasic.setBorderPainted(false); //버튼 외곽선 삭제
        OpenBasic.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenBasic.setFocusPainted(false); //버튼 눌리는 부분 삭제   
        //OpenBasic.setPreferredSize(new Dimension(10, 10));
        OpenBasic.addActionListener(new ActionListener() {
            // Basic Mode 행동 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               Board.gameMode = 0;
                G = new gameWindow(); // 클래스 gameWindow를 새로 만들어낸다
                clip.stop(); //메인메뉴 음악 정지
                setVisible(false); //화면 보이지 않게 설정
            }      
        });


    	//하드 모드 버튼
    	Image button2 = new ImageIcon("./img/HardMode.png").getImage();
    	button2 = button2.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);
        JButton OpenHard = new JButton(new ImageIcon(button2));
        OpenHard.setBorderPainted(false); //버튼 외곽선 삭제
        OpenHard.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenHard.setFocusPainted(false); //버튼 눌리는 부분 삭제 
        //OpenHard.setPreferredSize(new Dimension(100, 100));
        OpenHard.addActionListener(new ActionListener() {
            // Hard Mode 버튼 행동 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               Board.gameMode = 1;
                G = new gameWindow(); // 클래스 gameWindow를 새로 만들어낸다
                clip.stop(); //메인메뉴 음악 정지
                setVisible(false); //화면 보이지 않게 설정
            }     
        });
        
        //랭킹 버튼
    	Image button3 = new ImageIcon("./img/Ranking.png").getImage();
    	button3 = button3.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);
        JButton OpenRank = new JButton(new ImageIcon(button3));
        OpenRank.setBorderPainted(false); //버튼 외곽선 삭제
        OpenRank.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenRank.setFocusPainted(false); //버튼 눌리는 부분 삭제 
        //OpenRank.setPreferredSize(new Dimension(100, 100));
        OpenRank.addActionListener(new ActionListener() {
            // Hard Mode 버튼 행동 정의
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               //Board.gameMode = 1;               
               //clip.stop(); //메인메뉴 음악 정지
               //setVisible(false); //화면 보이지 않게 설정
            }     
        });

        middlePanel.add(blank1);
        middlePanel.add(OpenBasic);
        middlePanel.add(OpenHard);
        middlePanel.add(OpenRank);
        background.add(leftPanel);
        background.add(middlePanel);
        background.add(rightPanel);
        
        setLocationByPlatform(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // 창 크기 설정
        setResizable(true);
        setVisible(true);

        Dimension dim = new Dimension(750,750);
        setMinimumSize(dim); // 최소 사이즈 설정
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		// 창 시작 위치 설정
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);

    }


  
    //음악 실행 메소드
    public void Music() {
        try {
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Venetian.wav").getAbsoluteFile());
            clip = AudioSystem.getClip(null);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
          M = new Main();
    }
}

   class gameWindow extends JFrame implements Constants {
      static ImageIcon icon;
      private static Board board;
       // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
       gameWindow() {
         icon = new ImageIcon("./img/background.gif");
         setTitle("Virus breaker"); //타이틀 설정
         Dimension dim = new Dimension(750, 750);
         setSize(Main.M.getWidth(), Main.M.getHeight()); //창 크기 설정
         setResizable(true);//리사이징 가능
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setMinimumSize(dim); //창 최소 사이즈 설정
         board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT); //borad 객체 생성

         //-----------------------------
         add(board); //board 추가

         //Place frame in main menu location
         setLocation(Main.M.getX(), Main.M.getY());

         //Sets the icon of the program
         setIconImage(Toolkit.getDefaultToolkit().getImage("img/Icon.png"));
         setVisible(true);         
   }     
}
