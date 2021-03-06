//Imports
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Thread;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.TreeMap;

//Class definition
public class Board extends JPanel implements Runnable, Constants {
    //Items on-screen
    public static Paddle paddle;
    public static Ball ball;
    public static Brick[] brick = new Brick[10];
    BoardListener boardtest1 = new BoardListener();
    //Initial Values for some important variables
    public static int score = 0, lives = MAX_LIVES, bricksLeft = 10, waitTime = 3, level = 1;
    protected int withSound =1;
    //창의 크기 불러옴
    public static int FrameWidth = WINDOW_WIDTH ;
    public static int FrameHeight = WINDOW_HEIGHT;
    //공 속도 변수
    static int xSpeed = (int)Math.round(((double)(FrameWidth + FrameHeight)/949.0));
    
    //리버스 모드 확인 변수
    public static boolean reverse = false;
    
    //공속도 상태 확인 변수
    public static int ball_speed = 0;
    
    //왼쪽 오른쪽 키 입력을 확인할 변수
    int key_temp = 0;
   
    //Player's name
    private String playerName;

    //게임 모드
    public static int gameMode;
    
    //하드 모드 시 변경되는 좌우키가 저장되는 변수
    private char randomLeftKey = 'F';
    private char randomRightKey = 'J';
    //The game
    private Thread game;

    //Songs for background music
    private String songOne = "./dist/wav/IDCIDK.wav";    
    private String[] trackList = {songOne};
    private AudioInputStream audio;
    private Clip clip;

    //Data structures to handle high scores
    private ArrayList<Item> items = new ArrayList<Item>();
    public static AtomicBoolean isPaused = new AtomicBoolean(true);

    //Colors for the bricks
    private Color[] blueColors = {BLUE_BRICK_ONE, BLUE_BRICK_TWO, BLUE_BRICK_THREE, Color.BLACK};
    private Color[] redColors = {RED_BRICK_ONE, RED_BRICK_TWO, RED_BRICK_THREE, Color.BLACK};
    private Color[] purpleColors = {PURPLE_BRICK_ONE, PURPLE_BRICK_TWO, PURPLE_BRICK_THREE, Color.BLACK};
    private Color[] yellowColors = {YELLOW_BRICK_ONE, YELLOW_BRICK_TWO, YELLOW_BRICK_THREE, Color.BLACK};
    private Color[] pinkColors = {PINK_BRICK_ONE, PINK_BRICK_TWO, PINK_BRICK_THREE, Color.BLACK};
    private Color[] grayColors = {GRAY_BRICK_ONE, GRAY_BRICK_TWO, GRAY_BRICK_THREE, Color.BLACK};
    private Color[] greenColors = {GREEN_BRICK_ONE, GREEN_BRICK_TWO, GREEN_BRICK_THREE, Color.BLACK};
    private Color[][] colors = {blueColors, redColors, purpleColors, yellowColors, pinkColors, grayColors, greenColors};
    
    //이미지 파일
    private ImageIcon readyimg = new ImageIcon("./img/READY.png");
    private ImageIcon scoreimg = new ImageIcon("./img/SCORE.png");
    private ImageIcon heartimg = new ImageIcon("./img/HEART.png");
    private ImageIcon back = new ImageIcon("./img/back.png");
    private ImageIcon gameOver = new ImageIcon("./img/over.png");
    private ImageIcon space = new ImageIcon("./img/space.png");
    private ImageIcon scoreImg = new ImageIcon("./img/score.png");
    private ImageIcon A = new ImageIcon("./img/A.png");
    private ImageIcon B = new ImageIcon("./img/B.png");
    private ImageIcon C = new ImageIcon("./img/C.png");
    private ImageIcon D = new ImageIcon("./img/D.png");
    private ImageIcon E = new ImageIcon("./img/E.png");
    private ImageIcon F = new ImageIcon("./img/F.png");
    private ImageIcon G = new ImageIcon("./img/G.png");
    private ImageIcon H = new ImageIcon("./img/H.png");
    private ImageIcon I = new ImageIcon("./img/I.png");
    private ImageIcon J = new ImageIcon("./img/J.png");
    private ImageIcon K = new ImageIcon("./img/K.png");
    private ImageIcon L = new ImageIcon("./img/L.png");
    private ImageIcon M = new ImageIcon("./img/M.png");
    private ImageIcon N = new ImageIcon("./img/N.png");
    private ImageIcon O = new ImageIcon("./img/O.png");
    private ImageIcon P = new ImageIcon("./img/P.png");
    private ImageIcon Q = new ImageIcon("./img/Q.png");
    private ImageIcon R = new ImageIcon("./img/R.png");
    private ImageIcon S = new ImageIcon("./img/S.png");
    private ImageIcon T = new ImageIcon("./img/T.png");
    private ImageIcon U = new ImageIcon("./img/U.png");
    private ImageIcon V = new ImageIcon("./img/V.png");
    private ImageIcon W = new ImageIcon("./img/W.png");
    private ImageIcon X = new ImageIcon("./img/X.png");
    private ImageIcon Y = new ImageIcon("./img/Y.png");
    private ImageIcon Z = new ImageIcon("./img/Z.png");
    
    //패들의 위치
    private int paddleX = (Board.FrameWidth/2)-(PADDLE_WIDTH/2);
    private int paddleY = Board.FrameHeight - 13;
    //공 시작 위치
    private int ballX = FrameWidth/2;
    private int ballY = FrameHeight/2;
    
    private boolean PBdraw = false; //패들과 공을 그릴지 결정
    private boolean readyDraw = true; //레디 상태

    //Constructor
    public Board(int width, int height) {
        super.setSize(width, height);
        //addKeyListener(new BoardListener());
        addKeyListener(boardtest1);
        setFocusable(true);
        paddle = new Paddle(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT, Color.WHITE);
        ball = new Ball(ballX, ballY, BALL_WIDTH, BALL_HEIGHT, Color.BLACK);
        playMusic(trackList, 0, level);

        game = new Thread(this);
        game.start();
        stop();
        repaint();
        isPaused.set(true);
    }

    //fills the array of bricks(벽돌을 랜덤한 위치에 생성)
    public void makeBricks() {
    	Random rand = new Random();//랜덤 객체
    	//벽돌 생성 속도를 조절하는 변수 rand.nextInt(i) + 1; 에서 i를 수정해 속도 조절
    	int genSpeed = rand.nextInt(150) + 1;
    	//a와 b를 랜덤으로 생성해 랜덤한 위치를 저장
    	int a = rand.nextInt(10);
    	//벽돌 생성 속도 조절과 벽돌을 동시에 최대 10개 까지만 생성되도록 한다
    	if (genSpeed == 1 && brick[a] == null) {
    		//아이템 종류(rand.nextInt(i) + 1의 i를 수정해 아이템 확률 조정)
    		int itemType = rand.nextInt(10) + 1;
    		int numLives = 1;//벽돌을 한번만 맞아도 없어지도록 1로 저장
    		Color color = colors[rand.nextInt(7)][0];//벽돌 color
    		//벽돌 생성
    		int BrickWidth = FrameWidth/10;
    		int BrickHeight = FrameWidth/20;
    		int brickX = (FrameWidth) - (rand.nextInt(FrameWidth - BrickWidth/2) + BrickWidth/2);
    		if(brickX >= FrameWidth-BrickWidth && brickX <= FrameWidth) // 벽돌이 화면을 넘어서 생성이되는 경우 최대 brickX값을 제외해주므로 예외처리를 한다.
    		{
    			brickX = FrameWidth-BrickWidth;
    		}
    		int brickY = (FrameHeight/3) - (rand.nextInt(FrameHeight/3 - BrickHeight/2) + 1);
    		double rateX = (double)FrameWidth / (double)brickX;
    		double rateY = (double)(FrameHeight/3) /(double)brickY;
    		//brick[a] = new Brick(brickX, brickY, BrickWidth, BrickHeight, color, rateX, rateY, numLives, itemType);
    		brick[a] = new Brick(brickX,brickY,BrickWidth,BrickHeight, color, rateX,rateY, numLives, itemType);
	    	bricksLeft++;//벽돌을 생성하고 남은 벽돌 개수 1 증가
	    	//벽돌 위치 중복 검사
	    	for (int i = 0; i < 10; i++) {
	    		if ((brick[i] != null) && (i != a)) { //벽돌이 존재하면서 비교 대상이 자신이 아닌 경우
	    			//두 벽돌이 겹칠 경우
		    		if ((brick[i].getX() - BrickWidth < brickX) && (brickX < brick[i].getX() + BrickWidth )) {
		    			if((brick[i].getY() - BrickHeight < brickY) && (brickY < brick[i].getY() + BrickHeight )) {
			    			brick[a] = null; //벽돌 삭제
			    			bricksLeft--;
		    			}
		    		}
		    	}
	    	}
        }
    }
    
    //starts the thread
    public void start() {
        game.resume();
        isPaused.set(false);
    }

    //stops the thread
    public void stop() {
        game.suspend();
    }

    //ends the thread
    public void destroy() {
        game.resume();
        isPaused.set(false);
        game.stop();
        isPaused.set(true);
    }


    //runs the game
    public void run() {  
        try {
	        while(true) {
	        	readyDraw = false;
	        	PBdraw = true; //공과 패들을 그림
	            int x1 = ball.getX();
	            int y1 = ball.getY();
	        	FrameWidth = (int)getWidth();//현재 프레임의 가로 길이
	            FrameHeight = (int)getHeight();//현재 프레임의 세로 길이
	            ball.changeBallSet();
	            paddle.changePaddleSet(); //패들의 크기, 좌표 재설정
	            for (int i = 0; i < 10; i++) {
	            	if (brick[i] != null)
	            		brick[i].changeBrickSet(); //벽돌의 크기, 좌표 재설정
	            }
	
	            makeBricks();//벽돌 생성
	            checkPaddle(x1, y1);
	            checkWall(x1, y1);
	            checkBricks(x1, y1);
	            checkLives();
	            checkIfOut(y1);
	            ball.move();
	            paddleMove();//하단 바 이동 메소드 실행
	            dropItems();
	            checkItemList();
	            repaint();
	            game.sleep(waitTime);
	        }
        } catch (InterruptedException e) {
            System.out.println("게임 종료");
        }
    }
    
    public void addItem(Item i) {
        items.add(i);
    }

    public void dropItems() {
        for (int i = 0; i < items.size(); i++) {
            Item tempItem = items.get(i);
            tempItem.drop();
            items.set(i, tempItem);
        }
    }

    public void checkLives() {
        if (lives == MIN_LIVES) {
            repaint();
            PBdraw = false;
            readyDraw = true;
            stop();
            isPaused.set(true);
            game.interrupt();
        }
    }

    /*하단 바에 공이 닿았는지 확인하고 공의 방향을 변경*/
    public void checkPaddle(int x1, int y1) {
        if (paddle.hitPaddle(x1, y1) && ball.getXDir() < 0) {
            ball.setYDir(-xSpeed);
            ball.setXDir(-xSpeed);
        }
        if (paddle.hitPaddle(x1, y1) && ball.getXDir() > 0) {
            ball.setYDir(-xSpeed);
            ball.setXDir(xSpeed);
        }

        if (paddle.getX() <= 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() >= getWidth()) {
            paddle.setX(getWidth() - paddle.getWidth());
        }
    }

    public void checkItemList() {
	    for (int i = 0; i < items.size(); i++) {
	        Item tempItem = items.get(i);
	        if (paddle.caughtItem(tempItem)) {
	        	//베이직모드 아이템 획득시 소리 출력
	        	if((gameMode == 0) && (tempItem.getType() < 6)) {
	                try { 
	                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Wood.wav").getAbsoluteFile());
	                    Clip clip = AudioSystem.getClip(null);
	                    clip.open(audioInputStream);
	                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                    gainControl.setValue(-10.0f); 
	                    clip.start();
	                } catch(Exception ex) {
	                   ex.printStackTrace();
	                }
	        	}
	        	//하드모드 아이템 획득시 소리 출력
	        	if((gameMode == 1) && (tempItem.getType() < 5)) {
	                try { 
	                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Wood.wav").getAbsoluteFile());
	                    Clip clip = AudioSystem.getClip(null);
	                    clip.open(audioInputStream);
	                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                    gainControl.setValue(-10.0f); 
	                    clip.start();
	                } catch(Exception ex) {
	                   ex.printStackTrace();
	                }
	        	}
	            items.remove(i);
	        }
	        else if (tempItem.getY() > Board.FrameHeight) {
	            items.remove(i);
	        }
	    }
	}

	/*벽돌에 공이 닿았는지 확인하고 공의 방향을 변경*/
    public void checkWall(int x1, int y1) {
        if (x1 >= getWidth() - ball.getWidth()) {
            ball.setXDir(-xSpeed);
        }
        if (x1 <= 0) {
            ball.setXDir(xSpeed);
        }
        if (y1 <= 0) {
            ball.setYDir(xSpeed);
        }
        if (y1 >= getHeight()) {
            ball.setYDir(-xSpeed);
        }
    }

    public void checkBricks(int x1, int y1) {
    	boolean destroy_check = false; //벽돌이 깨졌는지 확인하는 변수
        for (int i = 0; i < 10; i++) {
            	//벽돌이 있을 경우
            	if(brick[i] != null) {
            		//공이 벽돌의 아래 맞은 경우
	                if (brick[i].hitBottom(x1, y1)) {
	                    ball.setYDir(xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;	
	                    	   try { // 벽돌을 꺠트리는 경우 소리를 출력해준다.
	                               AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Crash.wav").getAbsoluteFile());
	                               Clip clip = AudioSystem.getClip(null);
	                               clip.open(audioInputStream);
	                               FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                               gainControl.setValue(-10.0f); 
	                               clip.start();
	                           } catch(Exception ex) {
	                              ex.printStackTrace();
	                           }
	                        bricksLeft--;
	                        score += 100;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 왼쪽에 맞은 경우
	                if (brick[i].hitLeft(x1, y1)) {
	                    ball.setXDir(-xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                    	   try {  // 벽돌을 꺠트리는 경우 소리를 출력해준다.
	                               AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Crash.wav").getAbsoluteFile());
	                               Clip clip = AudioSystem.getClip(null);
	                               clip.open(audioInputStream);
	                               FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                               gainControl.setValue(-10.0f); 
	                               clip.start();
	                           } catch(Exception ex) {
	                              ex.printStackTrace();
	                           }
	                        bricksLeft--;
	                        score += 100;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 오른쪽에 맞은 경우
	                if (brick[i].hitRight(x1, y1)) {
	                    ball.setXDir(xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                    	   try {  // 벽돌을 꺠트리는 경우 소리를 출력해준다.
	                               AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Crash.wav").getAbsoluteFile());
	                               Clip clip = AudioSystem.getClip(null);
	                               clip.open(audioInputStream);
	                               FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                               gainControl.setValue(-10.0f); 
	                               clip.start();
	                           } catch(Exception ex) {
	                              ex.printStackTrace();
	                           }
	                        bricksLeft--;
	                        score += 100;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 위쪽에 맞은 경우
	                if (brick[i].hitTop(x1, y1)) {
	                    ball.setYDir(-xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                    	   try {  // 벽돌을 꺠트리는 경우 소리를 출력해준다.
	                               AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Crash.wav").getAbsoluteFile());
	                               Clip clip = AudioSystem.getClip(null);
	                               clip.open(audioInputStream);
	                               FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                               gainControl.setValue(-10.0f); 
	                               clip.start();
	                           } catch(Exception ex) {
	                              ex.printStackTrace();
	                           }
	                        bricksLeft--;
	                        score += 100;
	                        addItem(brick[i].item);
	                    }
	                }
	            }
            	//벽돌이 깨진 경우 그 벽돌 위치에 벽돌 제거
            	if (destroy_check) {
            		brick[i] = null;
            		destroy_check = false;
            	}
        }
    }

    /*lives를 하나 읽을 경우 리셋*/
    public void checkIfOut(int y1) {

        if (y1 > paddle.getY()) {
            lives--; //lives 1 감소
            ball.reset(); //공 리셋
            paddle.reset(); //하단 바 리셋
            key_temp = 0;
            PBdraw = false;
            readyDraw = true;
            //벽돌 리셋
            for (int i = 0; i < 10; i++) {
            	if(brick[i] != null) {
            		brick[i] = null;
            	}
            }
            //아이템 리셋
    	    for (int j = 0; j < items.size(); j++) {
    	        items.remove(j);
    	    }
    	    //하드모드 키 리셋
    	    randomLeftKey = 'F';
    	    randomRightKey = 'J';
            repaint();
            stop();
            isPaused.set(true);
        }
    }

    
    //plays different music throughout game if user wants to
    public void playMusic(String[] songs, int yesNo, int level) {
        if (yesNo == 1) {
            return;
        }
        else if (yesNo == -1) {
            System.exit(0);
        }
        try {
        	audio = AudioSystem.getAudioInputStream(new File(songs[level-1]).getAbsoluteFile());
            clip = AudioSystem.getClip(null);
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*Ready상태 출력*/
    public void drawReady(Graphics g) {
    	g.drawImage(readyimg.getImage(), getWidth()*5/19, getHeight()/4, getWidth()/2, getHeight()/2, null);
    	setBackground(Color.BLACK); //배경 지정	
    	setOpaque(false);
    }
    
    //fills the board
    @Override
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        super.paintComponent(g);
        g.drawImage(gameWindow.icon.getImage(), 0, 0, getWidth(), getHeight(), null);
        //Ready 상태 출력(처음 시작 전 Ready 출력)
        if ((lives > MIN_LIVES) && (readyDraw == true)) {
        	drawReady(g);
        }
        
        //패들과 공을 그려야 되는 경우
        if(PBdraw == true) {
		       paddle.draw(g);
		       ball.draw(g);
        }
        
        //lives 하트 이미지로 출력
        if(lives >= MAX_LIVES-2) {
        	g.drawImage(heartimg.getImage(), getWidth()*96/100, getHeight()/100, getWidth()/30, getHeight()/30, null);
        	if(lives >= MAX_LIVES-1) {
        		g.drawImage(heartimg.getImage(), getWidth()*92/100, getHeight()/100, getWidth()/30, getHeight()/30, null);
        		if(lives >= MAX_LIVES) {
        			g.drawImage(heartimg.getImage(), getWidth()*88/100, getHeight()/100, getWidth()/30, getHeight()/30, null);
        		}
        	}
        }

	    for (int i = 0; i < 10; i++) {
	            //벽돌이 그 위치에 없을 경우 벽돌을 그린다
	            if (brick[i] != null)
	            	brick[i].draw(g);
	    }
	    g.setColor(Color.BLACK);
	    Font font  = new Font("Impact", Font.PLAIN, (getWidth()+getHeight())/50); //폰트 설정
    	g.setFont(font);
	    //하드모드(좌우 방향 키를 게임 화면에 출력)
	    if(gameMode == 1) {
	        //왼쪽 이동 키
	    	switch(randomLeftKey) {
	    	case 'A':
	    		g.drawImage(A.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'B':
	    		g.drawImage(B.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'C':
	    		g.drawImage(C.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'D':
	    		g.drawImage(D.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'E':
	    		g.drawImage(E.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'F':
	    		g.drawImage(F.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'G':
	    		g.drawImage(G.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'H':
	    		g.drawImage(H.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'I':
	    		g.drawImage(I.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'J':
	    		g.drawImage(J.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'K':
	    		g.drawImage(K.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'L':
	    		g.drawImage(L.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'M':
	    		g.drawImage(M.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'N':
	    		g.drawImage(N.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'O':
	    		g.drawImage(O.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'P':
	    		g.drawImage(P.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Q':
	    		g.drawImage(Q.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'R':
	    		g.drawImage(R.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'S':
	    		g.drawImage(S.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'T':
	    		g.drawImage(T.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'U':
	    		g.drawImage(U.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'V':
	    		g.drawImage(V.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'W':
	    		g.drawImage(W.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'X':
	    		g.drawImage(X.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Y':
	    		g.drawImage(Y.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Z':
	    		g.drawImage(Z.getImage(), getWidth()*1/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    		
	    	}
	    	//오른쪽 이동 키
	    	switch (randomRightKey){
	    	case 'A':
	    		g.drawImage(A.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'B':
	    		g.drawImage(B.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'C':
	    		g.drawImage(C.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'D':
	    		g.drawImage(D.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'E':
	    		g.drawImage(E.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'F':
	    		g.drawImage(F.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'G':
	    		g.drawImage(G.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'H':
	    		g.drawImage(H.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'I':
	    		g.drawImage(I.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'J':
	    		g.drawImage(J.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'K':
	    		g.drawImage(K.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'L':
	    		g.drawImage(L.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'M':
	    		g.drawImage(M.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'N':
	    		g.drawImage(N.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'O':
	    		g.drawImage(O.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'P':
	    		g.drawImage(P.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Q':
	    		g.drawImage(Q.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'R':
	    		g.drawImage(R.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'S':
	    		g.drawImage(S.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'T':
	    		g.drawImage(T.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'U':
	    		g.drawImage(U.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'V':
	    		g.drawImage(V.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'W':
	    		g.drawImage(W.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'X':
	    		g.drawImage(X.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Y':
	    		g.drawImage(Y.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	case 'Z':
	    		g.drawImage(Z.getImage(), getWidth()*46/50, getHeight()*9/10, getWidth()/15, getHeight()/25, null);
	    		break;
	    	}  	
	        }

	    	g.setColor(Color.red);//색 설정
	    	//score 출력
	    	g.drawString(Integer.toString(score), getWidth()/100, getHeight()*5/100);

	
	    	for (Item i: items) {
	        	if(i != null)
	        		i.draw(g);
	        }
	        //게임 오버 창
        if (lives == MIN_LIVES) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.WHITE);
            g.drawImage(gameOver.getImage(), getWidth()*25/100, getHeight()*10/100, getWidth()/2, getHeight()/8, null);
            g.drawImage(scoreImg.getImage(), getWidth()*42/100, getHeight()*35/100, getWidth()/6, getHeight()/10, null);
            g.drawString(Integer.toString(score), getWidth()*48/100, getHeight()*50/100);
            g.drawImage(back.getImage(), getWidth()*25/100, getHeight()*70/100, getWidth()/2, getHeight()/10, null);
            g.drawImage(space.getImage(), getWidth()*25/100, getHeight()*80/100, getWidth()/2, getHeight()/8, null);
        }
    }

    //Returns the player's name and score formatted correctly
    public String playerInfo() {
        return "Name: " + playerName + ", Score: " + score;
    }
   
    //하단 바의 이동 방향 제어
    public void paddleMove()
    {
             //왼쪽으로 이동
            if (key_temp == 1) {
                paddle.moveLeft();
            }
            //오른쪽으로 이동
            if (key_temp == -1) {
                paddle.moveRight();
            }
    }
   
    //Private class that handles gameplay and controls
    private class BoardListener extends KeyAdapter {
            
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                //isSpace = true;
                if (lives > MIN_LIVES) {
                    if (isPaused.get() == false) {
                    	//stop();
                        isPaused.set(true);
                    }
                    else {
                        start();
                    }
                }
                else {
            	    String sign = "";
            	    if(gameMode == 0) {
            	    	sign = "2 " + Main.id + " " + "pw" + " " + score + " 0";
            	    }
            	    if(gameMode == 1) {
            	    	sign = "3 " + Main.id + " " + "pw" + " 0 " + score;
            	    }

            	    Main.Client.send(sign); 
            	    
                    lives = MAX_LIVES;
                    score = 0;
                    level = 1;
                    isPaused.set(true);
                    //brick 리셋
                    for (int i = 0; i < 10; i++) {
                        	if(brick[i] != null) { //brick이 생성되어 있는 경우
                        		brick[i].setDestroyed(false);
                        	}
                    	}
                    //item 리셋
            	    for (int j = 0; j < items.size(); j++) {
            	        items.remove(j);
            	    }   
            	    
            	    game.interrupt(); //게임 종료
            	    clip.stop(); //음악 정지
                    //메인메뉴 음악 실행
                    try {
                       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./dist/wav/Venetian.wav").getAbsoluteFile());
                        Main.M.clip = AudioSystem.getClip(null);
                        Main.M.clip.open(audioInputStream);
                        Main.M.clip.loop(Clip.LOOP_CONTINUOUSLY);
                       
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            	    Main.M.setVisible(true);//메인메뉴로
                    Main.G.dispose();//게임 화면 끄기
                	}
           		}
            //Basic 모드 조작 키
            if(gameMode == 0) {
            	//리버스 비 활성화
            	if(reverse == false) {
            		//왼쪽 이동
            		if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
            			key_temp = 1;
            		}
            		//오른쪽 이동
            		if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            			key_temp = -1;
            		}
            	}
            	//리버스 활성화
            	if(reverse == true) {
            		//왼쪽 이동
            		if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            			key_temp = 1;
            		}
            		//오른쪽 이동
            		if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
            			key_temp = -1;
            		}
            	}      	
            }
            else {    	
	            //왼쪽으로 이동
	            if (Character.toUpperCase(ke.getKeyChar()) == randomLeftKey) {
	                key_temp = 1;
	                //다음 randomLeftKey 생성
	                do {
	                		randomLeftKey = (char)((Math.random() * 26) + 65);
	                }while(randomLeftKey == randomRightKey);
	            }
	            //오른쪽으로 이동
	            if (Character.toUpperCase(ke.getKeyChar()) == randomRightKey) {
	                key_temp = -1;
	                //다음 randomRightKey 생성
	                do {
	                		randomRightKey = (char)((Math.random() * 26) + 65);
	                }while(randomLeftKey == randomRightKey);
	            }
            }
        }
    }
}

