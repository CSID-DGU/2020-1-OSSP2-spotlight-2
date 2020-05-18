
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
    private int score = 0, lives = MAX_LIVES, bricksLeft = 1, waitTime = 3, withSound, level = 1;
     
    //창의 크기 불러옴
    public static int FrameWidth = WINDOW_WIDTH ;
    public static int FrameHeight = WINDOW_HEIGHT;
    //공 속도 변수
    public static int xSpeed = 1;
    
    //리버스 모드 확인 변수
    public static boolean reverse = false;
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
    private String songOne = "./dist/wav/One.wav";
    private String songTwo = "./dist/wav/Two.wav";
    private String songThree = "./dist/wav/Three.wav";
    private String songFour = "./dist/wav/Four.wav";
    private String songFive = "./dist/wav/Five.wav";
    private String songSix = "./dist/wav/Six.wav";
    private String songSeven = "./dist/wav/Seven.wav";
    private String songEight = "./dist/wav/Eight.wav";
    private String songNine = "./dist/wav/Nine.wav";
    private String songTen = "./dist/wav/Ten.wav";
    private String[] trackList = {songOne, songTwo, songThree, songFour, songFive, songSix, songSeven, songEight, songNine, songTen};
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
    
    //패들의 위치
    private int paddleX = (486/2)-(PADDLE_WIDTH/2);
    private int paddleY = 463 - 13;
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
        paddle = new Paddle(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
        ball = new Ball(ballX, ballY, BALL_WIDTH, BALL_HEIGHT, Color.BLACK);

        //Get the player's name
        playerName = JOptionPane.showInputDialog(null, "Please enter your name:", "Virus Breaker", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null) {
            System.exit(0);
        }
        if (playerName.toUpperCase().equals("TY") || playerName.toUpperCase().equals("TYKELLEY") || playerName.toUpperCase().equals("TYLUCAS") || playerName.toUpperCase().equals("TYLUCASKELLEY") || playerName.toUpperCase().equals("TY-LUCAS") || playerName.toUpperCase().equals("TY-LUCAS KELLEY") || playerName.toUpperCase().equals("TY KELLEY")) {
            score += 1000;
            JOptionPane.showMessageDialog(null, "You unlocked the secret 1,000 point bonus! Nice name choice by the way.", "1,000 Points", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //게임 모드 선택
        String[] modeOptions = {"Basic", "Hard"};
        gameMode = JOptionPane.showOptionDialog(null, "게임 모드를 선택하세요", "게임 모드 선택", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, modeOptions, modeOptions[0]);
        //Start Screen that displays information and asks if the user wants music or not, stores that choice
        String[] musicOptions = {"Yes", "No"};
        withSound = JOptionPane.showOptionDialog(null, "Brick Breaker, Version 1.2\nTy-Lucas Kelley\nVisit www.tylucaskelley.com for more projects.\n\nControls\n    Spacebar: Start game, Pause/Resume while in game.\n    Left/Right arrow keys: Move paddle\nItems\n    Green Item: Expand paddle\n    Red Item: Shrink paddle\nScoring\n    Block: 50 points\n    Level-up: 100 points\n    Life Loss: -100 points\n\n\n     Do you want background music?", "About the Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, musicOptions, musicOptions[1]);
        playMusic(trackList, withSound, level);

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

    	PBdraw = true; //공과 패들을 그림
        while(true) {   
        	readyDraw = false;
            FrameWidth = (int)getWidth();//현재 프레임의 가로 길이
            FrameHeight = (int)getHeight();//현재 프레임의 세로 길이
            ball.changeBallSet();
            paddle.changePaddleSet(); //패들의 크기, 좌표 재설정
            for (int i = 0; i < 10; i++) {
            	if (brick[i] != null)
            		brick[i].changeBrickSet(); //벽돌의 크기, 좌표 재설정
            }
            int x1 = ball.getX();
            int y1 = ball.getY();
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
            try {
                game.sleep(waitTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
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
	                        bricksLeft--;
	                        score += 1;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 왼쪽에 맞은 경우
	                if (brick[i].hitLeft(x1, y1)) {
	                    ball.setXDir(-xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                        bricksLeft--;
	                        score += 1;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 오른쪽에 맞은 경우
	                if (brick[i].hitRight(x1, y1)) {
	                    ball.setXDir(xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                        bricksLeft--;
	                        score += 1;
	                        addItem(brick[i].item);
	                    }
	                }
	                //공이 벽돌의 위쪽에 맞은 경우
	                if (brick[i].hitTop(x1, y1)) {
	                    ball.setYDir(-xSpeed);
	                    if (brick[i].isDestroyed()) {
	                    	   destroy_check = true;
	                        bricksLeft--;
	                        score += 1;
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
        if (level == 10) {
            level = 1;
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
    	Font font  = new Font("Serif", Font.BOLD, 30);
    	g.setFont(font);
    	g.setColor(Color.yellow);
    	g.drawString("Ready", getWidth()/2 - 50, getHeight()/2 - 20);
    	font  = new Font("Serif", Font.BOLD, 10);
    	g.setFont(font);
    	g.drawString("Press space to start", getWidth()/2 - 50, getHeight()/2);
    }
    
    //fills the board
    @Override
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        super.paintComponent(g);

<<<<<<< HEAD
        g.drawImage(Main.icon.getImage(),0,0,null);
=======
        g.drawImage(Main.icon.getImage(),0 , 0,getWidth(),getHeight(),null);
>>>>>>> kwan

        //Ready 상태 출력(처음 시작 전 Ready 출력)
        if ((lives > MIN_LIVES) && (readyDraw == true)) {
        	drawReady(g);
        }

        //패들과 공을 그려야 되는 경우
<<<<<<< HEAD
=======
        
>>>>>>> kwan
        if(PBdraw == true) {
		       paddle.draw(g);
		       ball.draw(g);
        }
        
	    for (int i = 0; i < 10; i++) {
	            //벽돌이 그 위치에 없을 경우 벽돌을 그린다
	            if (brick[i] != null)
	            	brick[i].draw(g);
	    }
	    g.setColor(Color.BLACK);
	    //하드모드(좌우 방향 키를 게임 화면에 출력)
	    if(gameMode == 1) {
	        g.drawString("Left_Key", 10, getHeight() - (getHeight()/3));
	        g.drawString(Character.toString(randomLeftKey), 10, getHeight() - (getHeight()/3) + 20);
	        g.drawString("Right_Key", getWidth() - 70, getHeight() - (getHeight()/3));
	        g.drawString(Character.toString(randomRightKey), getWidth() - 15, getHeight() - (getHeight()/3) + 20);
	        }
	        g.drawString("Lives: " + lives, 10, getHeight() - 50);
	        g.drawString("Score: " + score, 10, getHeight() - 70);
	        g.drawString("Level: " + level, 10, getHeight() - 90);
	        g.drawString("Player: " + playerName, 10, getHeight() - 110);
	
	        for (Item i: items) {
	            i.draw(g);
	        }
	        
        if (lives == MIN_LIVES) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.WHITE);
            //g.drawString("Name: " + playerName + ", Score: " + score + ", Level: " + level, getWidth()/5, 20);
            g.drawString("Game Over!", getWidth()/5, 50);
            g.drawString("Press the Spacebar twice to play again.", getWidth()/5, getHeight()-20);
        }
    }

    //Makes sure the HighScores.txt file exists
    public void makeTable() throws IOException {
        String filename = "HighScores";
        File f = new File(filename + ".txt");
        if (f.createNewFile()) {
            try {
                writeFakeScores();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        else {
            //do nothing
        }
    }

    //if there was no previous high score table, this one inputs 10 fake players and scores to fill it
    public void writeFakeScores() throws IOException {
        Random rand = new Random();

        int numLines = 10;
        File f = new File("HighScores.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
        for (int i = 1; i <= numLines; i++) {
            int score = rand.nextInt(2000);
            if (numLines - i >= 1) {
                bw.write("Name: " + "Player" + i + ", " + "Score: " + score + "\n");
            }
            else {
                bw.write("Name: " + "Player" + i + ", " + "Score: " + score);
            }
        }
        bw.close();
        try {
            sortTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Returns the player's name and score formatted correctly
    public String playerInfo() {
        return "Name: " + playerName + ", Score: " + score;
    }

    //returns the number of lines in the high score file
    public int linesInFile(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
        int lines = 0;
        while (br.readLine() != null) {
            lines++;
        }
        br.close();
        return lines;
    }

    //Add game to high score file by appending it and getting line number from previous method
    public void saveGame() throws IOException {
        File f = new File("HighScores.txt");
        FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append("\n" + playerInfo());
        bw.close();
    }

    //sorts the high score table high to low using maps and other fun things
    public void sortTable() throws IOException {
        File f = new File("HighScores.txt");
        File temp = new File("temp.txt");
        TreeMap<Integer, ArrayList<String>> topTen = new TreeMap<Integer, ArrayList<String>>();
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp.getAbsoluteFile()));


        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
                String[] scores = line.split("Score: ");
                Integer score = Integer.valueOf(scores[1]);
                ArrayList<String> players = null;
               
                //make sure two players with same score are dealt with
                if ((players = topTen.get(score)) == null) {
                    players = new ArrayList<String>(1);
                    players.add(scores[0]);
                    topTen.put(Integer.valueOf(scores[1]), players);
                }
                else {
                    players.add(scores[0]);
                }

        }

        for (Integer score : topTen.descendingKeySet()) {
            for (String player : topTen.get(score)) {
                try {
                    bw.append(player + "Score: " + score + "\n");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        br.close();
        bw.close();
        try {
            makeNewScoreTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //save the sorted table to the high score file
    public void makeNewScoreTable() throws IOException {
        File f = new File("HighScores.txt");
        File g = new File("temp.txt");
        f.delete();
        g.renameTo(f);
    }
   
    //Print the top 10 scores, but first excecutes all other file-related methods
    public void printScores(Graphics g) throws IOException {
        try {
            makeTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            saveGame();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            sortTable();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        int h = 100;
        File fileToRead = new File("HighScores.txt");
        LineNumberReader lnr = new LineNumberReader(new FileReader(fileToRead));
        String line = lnr.readLine();
        while (line != null && lnr.getLineNumber() <= 10) {
            int rank = lnr.getLineNumber();
            g.drawString(rank + ". " + line, getWidth()/5, h);
            h += 15;
            line = lnr.readLine();
        }
        lnr.close();
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
                    paddle.setWidth(getWidth()/7);
                    lives = MAX_LIVES;
                    score = 0;
                    level = 1;
                    makeBricks();
                    isPaused.set(true);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 5; j++) {
                        		if(brick[i] != null) { //brick이 생성되어 있는 경우
                        			brick[i].setDestroyed(false);
                        		}
                        	}
                    	}
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