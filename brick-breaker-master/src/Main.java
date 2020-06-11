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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//Class definition
public class Main extends JFrame implements Constants {
	//Variables
	public JPanel background;
	public int loginCheck2;
	public static Main M;
	public static gameWindow G;
	public static Clip clip;
	public static String sign;
	public static String id;
	public static boolean gameStart = false;
	//private static Board board;
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

       MyMouseListener listener = new MyMouseListener();

       //베이직 모드 버튼
       Image button1 = new ImageIcon("./img/BasicMode.png").getImage();
       button1 = button1.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);
        JButton OpenBasic = new JButton(new ImageIcon(button1));
        OpenBasic.setBorderPainted(false); //버튼 외곽선 삭제
        OpenBasic.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenBasic.setFocusPainted(false); //버튼 눌리는 부분 삭제   
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
        OpenBasic.addMouseListener(listener);


    	//하드 모드 버튼
    	Image button2 = new ImageIcon("./img/HardMode.png").getImage();
    	button2 = button2.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);

        JButton OpenHard = new JButton(new ImageIcon(button2));
        OpenHard.setBorderPainted(false); //버튼 외곽선 삭제
        OpenHard.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenHard.setFocusPainted(false); //버튼 눌리는 부분 삭제 
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
        OpenHard.addMouseListener(listener);
        
        //랭킹 버튼
       Image button3 = new ImageIcon("./img/Ranking.png").getImage();
       button3 = button3.getScaledInstance(200, 80, java.awt.Image.SCALE_SMOOTH);
        JButton OpenRank = new JButton(new ImageIcon(button3));
        OpenRank.setBorderPainted(false); //버튼 외곽선 삭제
        OpenRank.setContentAreaFilled(false); //버튼 나머지 영역 삭제
        OpenRank.setFocusPainted(false); //버튼 눌리는 부분 삭제 
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
        OpenRank.addMouseListener(listener);
        
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
    	//서버 연결
		 Client Client = new Client();
		 Client.startClient();
    	String[] options = {"Sign In", "Sign Up", "Exit"};
    	while(true) {
    		JTextField idField = new JTextField(5); // id 입력 필드
        	JTextField pwField = new JTextField(5); // 패스워드 입력 필드
        	JPanel loginPanel = new JPanel(); // 로그인 패널
        	loginPanel.setLayout(new GridLayout(2,2));
        	loginPanel.add(new JLabel("ID"));
        	loginPanel.add(idField);
        	loginPanel.add(new JLabel("Password"));
        	loginPanel.add(pwField);
			int logIn = JOptionPane.showOptionDialog(null, "Login", "Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			//로그인
			if(logIn == 0) {
				sign = "0";
				JOptionPane.showConfirmDialog(null, loginPanel, "Sign in", JOptionPane.OK_CANCEL_OPTION);
				sign = sign + " " + idField.getText();
				sign = sign + " " + pwField.getText();
				sign = sign + " 0";
				}
			//회원 가입
			else if(logIn == 1) {
				sign = "1";
				JOptionPane.showConfirmDialog(null, loginPanel, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
				sign = sign + " " + idField.getText();
				sign = sign + " " + pwField.getText();
				sign = sign + " 0";
			}
			//종료
			else if(logIn == 2) {
				break;
			}

			if((!idField.getText().equals("")) && (!pwField.getText().equals(""))) {
	               //둘다 빈칸이 아닐때
	            Client.send(sign); 
	            Client.receive();
	        }
	         //둘다 빈칸이거나 하나만 빈칸일때
	        else {
	           JOptionPane.showMessageDialog(null, "잘못된 값을 입력했습니다", "잘못된 값 입력", JOptionPane.ERROR_MESSAGE);
	           continue;
	        }

			 //loginCheck가 1이면 로그인 성공
			 System.out.println(Client.loginCheck);
			 if(Client.loginCheck == 1) {
				 gameStart = true;
				 id = idField.getText();
				 break;
			 }
			 //loginCheck가 2면 로그인 실패
			 else if(Client.loginCheck == 2) {
				 JOptionPane.showMessageDialog(null, "로그인 실패", "Login Fail", JOptionPane.ERROR_MESSAGE);
			 }
			//loginCheck가 3면 회원가입 성공
			 else if(Client.loginCheck == 3) {
				 JOptionPane.showMessageDialog(null, "회원가입 성공", "Success", JOptionPane.INFORMATION_MESSAGE);
			 }
			//loginCheck가 4면 회원가입 실패
			 else if(Client.loginCheck == 4)  {
				 JOptionPane.showMessageDialog(null, "동일 아이디가 존재합니다. 다른 아이디를 시도하세요", "Error", JOptionPane.ERROR_MESSAGE);
			 }
    	}
		 //Client.stopClient();
    	if(gameStart == true) {
    		M = new Main();
    	}

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
   
   class MyMouseListener implements MouseListener{

       @Override
       public void mouseClicked(MouseEvent e) {
       }

       @Override
       public void mousePressed(MouseEvent e) {
       }

       @Override
       public void mouseReleased(MouseEvent e) {
       }

       @Override//마우스가 버튼 안으로 들어오면 빨간색으로 바뀜
       public void mouseEntered(MouseEvent e) {
           JButton b = (JButton)e.getSource();
           b.setBorderPainted(true);
       }

       @Override//마우스가 버튼 밖으로 나가면 노란색으로 바뀜
       public void mouseExited(MouseEvent e) {
          JButton b = (JButton)e.getSource();
           b.setBorderPainted(false);
       }
       
   }