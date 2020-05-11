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

//This "Paddle" class extends the "Structure" class. It is used for the player's paddle in the game.

//Imports
import java.awt.*;
import java.awt.event.*;

//Class definition
public class Paddle extends Structure implements Constants {
	//Variables
	private int xSpeed;

	//Constructor
	public Paddle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}

	//Draws the paddle
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	//Places the paddle back in starting position at center of screen(하단 바 리셋)
	public void reset() {
		x = PADDLE_X_START;
		y = PADDLE_Y_START;
		setColor(Color.BLACK);//하단 바 색상 BLACK으로 초기화
		Board.reverse = false;//리버스 모드 비활성화
	}
	//하단 바를 왼쪽으로 이동(숫자를 조절해 하단 바의 속도 조절)
	public void moveLeft() {
		x -= 2;
	}
	//하단바를 오른쪽으로 이동(숫자를 조절해 하단 바의 속도 조절)
	public void moveRight() {
		x += 2;
	}
	//Checks if the ball hit the paddle
	public boolean hitPaddle(int ballX, int ballY) {
		if ((ballX >= x) && (ballX <= x + width) && ((ballY >= y) && (ballY <= y + height))) {
			return true;
		}
		return false;
	}

	//Resizes the paddle if it touches an item, then returns true or false
	public boolean caughtItem(Item i) {
		if ((i.getX() < x + width) && (i.getX() + i.getWidth() > x) && (y == i.getY() || y == i.getY() - 1)) {
			i.resizePaddle(this); //하단 바 크기 변경
			i.changeBallSpeed(this); //공 속도 변경
			i.reverseMode(this); //리버스 모드
			return true;
		}
		return false;
	}
}
