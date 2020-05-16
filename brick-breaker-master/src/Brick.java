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

//This "Brick" class extends the "Structure" class. It is for the bricks used in the game.

//Imports
import java.awt.*;
import java.awt.image.ImageObserver;

import java.util.Random;

import javax.swing.ImageIcon;

//Class definition
public class Brick extends Structure implements Constants, ImageObserver {
	//Variables
	private int lives, hits;
	private boolean destroyed;
	public Item item;
	private Color itemColor;
	private Color[] blueColors = {BLUE_BRICK_ONE, BLUE_BRICK_TWO, BLUE_BRICK_THREE, Color.BLACK};
	private Color[] redColors = {RED_BRICK_ONE, RED_BRICK_TWO, RED_BRICK_THREE, Color.BLACK};
	private Color[] purpleColors = {PURPLE_BRICK_ONE, PURPLE_BRICK_TWO, PURPLE_BRICK_THREE, Color.BLACK};
	private Color[] yellowColors = {YELLOW_BRICK_ONE, YELLOW_BRICK_TWO, YELLOW_BRICK_THREE, Color.BLACK};
	private Color[] pinkColors = {PINK_BRICK_ONE, PINK_BRICK_TWO, PINK_BRICK_THREE, Color.BLACK};
	private Color[] grayColors = {GRAY_BRICK_ONE, GRAY_BRICK_TWO, GRAY_BRICK_THREE, Color.BLACK};
	private Color[] greenColors = {GREEN_BRICK_ONE, GREEN_BRICK_TWO, GREEN_BRICK_THREE, Color.BLACK};
	private Color[][] colors = {blueColors, redColors, purpleColors, yellowColors, pinkColors, grayColors, greenColors};

	//블록 이미지 img에 저장
	ImageIcon icon = new ImageIcon("./img/corona.png");
	Image img = icon.getImage();
	
	
	//Toolkit.getDefaultToolkit().getImage
	//Constructor
	public Brick(int x, int y, int width, int height, Color color, int lives, int itemType) {
		super(x, y, width, height, color);
		setLives(lives);
		setHits(0);
		setDestroyed(false);

		/*아이템 타입*/
		if (itemType == 1) { // 하단 바 크기 증가
			itemColor = Color.GREEN;
		}
		if (itemType == 2) { // 하단 바 크기 감소
			itemColor = Color.RED;
		}
		if (itemType == 3) { // 공 속도 증가
			itemColor = Color.BLUE;
		}
		if (itemType == 4) { // 공 속도 감소
			itemColor = Color.PINK;
		}
		if (itemType == 5) { // 리버스 아이템
			itemColor = Color.ORANGE;
		}

		//Places an item of specified type inside the brick to fall when the brick is destroyed
		item = new Item(x + (width / 4), y + (height / 4), ITEM_WIDTH, ITEM_HEIGHT, itemColor, itemType);
	}

	//Draws a brick
	@Override
	public void draw(Graphics g) {
		if (!destroyed) {

	        //img에 저장된 블록 이미지를 블록 크기에 맟춰 그리기
			g.drawImage(img, x, y, width, height, this);

			//g.setColor(color);
			//g.fillOval(x, y, width, height); //벽돌을 타원형으로 그림
		}
	}

	//Add a hit to the brick, and destroy the brick when hits == lives
	public void addHit() {
		hits++;
		nextColor();
		if (hits == lives) {
			setDestroyed(true);
		}
	}

	//Change color to get lighter until the brick is destroyed
	public void nextColor() {
		if (color == colors[0][0] || color == colors[0][1] || color == colors[0][2]) {
			color = blueColors[hits];
		}
		if (color == colors[1][0] || color == colors[1][1] || color == colors[1][2]) {
			color = redColors[hits];
		}
		if (color == colors[2][0] || color == colors[2][1] || color == colors[2][2]) {
			color = purpleColors[hits];
		}
		if (color == colors[3][0] || color == colors[3][1] || color == colors[3][2]) {
			color = yellowColors[hits];
		}
		if (color == colors[4][0] || color == colors[4][1] || color == colors[4][2]) {
			color = pinkColors[hits];
		}
		if (color == colors[5][0] || color == colors[5][1] || color == colors[5][2]) {
			color = grayColors[hits];
		}
		if (color == colors[6][0] || color == colors[6][1] || color == colors[6][2]) {
			color = greenColors[hits];
		}
	}

	//Detect if the brick has been hit on its bottom, top, left, or right sides
	public boolean hitBottom(int ballX, int ballY) { //아래쪽에 공이 맞는 경우
		if(Board.xSpeed == 1) { //속도가 1 일때
			if ((ballX >= x) && (ballX <= x + width + 1) && (ballY == y + height) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		else { //속도가 2 일때
			if ((ballX >= x) && (ballX <= x + width + 1) && (ballY >= y + height - 1) && (ballY <= y + height) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		return false;
	}

	public boolean hitTop(int ballX, int ballY) { //위쪽에 공이 맞는 경우
		if(Board.xSpeed == 1) { //속도가 1 일때
			if ((ballX >= x) && (ballX <= x + width + 1) && (ballY == y) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		else { //속도가 2 일때
			if ((ballX >= x) && (ballX <= x + width + 1) && (ballY >= y) && (ballY <= y + 1) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		return false;
	}

	public boolean hitLeft(int ballX, int ballY) { //왼쪽에 공이 맞는 경우
		if(Board.xSpeed == 1) { //속도가 1 일때
			if ((ballY >= y) && (ballY <= y + height) && (ballX == x) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		else { //속도가 2 일때
			if ((ballY >= y) && (ballY <= y + height) && (ballX >= x) && (ballX <= x + 1) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		return false;
	}

	public boolean hitRight(int ballX, int ballY) { //오른쪽에 공이 맞는 경우
		if(Board.xSpeed == 1) { //속도가 1 일때
			if ((ballY >= y) && (ballY <= y + height) && (ballX == x + width) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		else { //속도가 2 일때
			if ((ballY >= y) && (ballY <= y + height) && (ballX <= x + width) && (ballX >= x + width - 1) && (destroyed == false)) {
				addHit();
				return true;
			}
		}
		return false;
	}

	public void changeBrickSet() {
				//setX(getX() + Board.FrameWidth);
				//brick[i].setY();
				setWidth(Board.FrameWidth/10);
				setHeight(Board.FrameWidth/20);
	}
	//Mutator methods
	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	//Accessor methods
	public int getLives() {
		return lives;
	}

	public int getHits() {
		return hits;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
