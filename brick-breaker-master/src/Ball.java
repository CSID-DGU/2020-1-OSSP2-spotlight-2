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

//This "Ball" class extends the "Structure" class. It is for the ball used in the game.

//Imports
import java.awt.*;

//Class definition
public class Ball extends Structure implements Constants {
	//Variables
	private boolean onScreen;
	private int xDir = 1, yDir = -1;

	//Constructor
	public Ball(int x, int y, int width, int height, Color color, double rateX, double rateY) {
		super(x, y, width, height, color, rateX, rateY);
		setOnScreen(true);
	}

	//Draw the ball
	@Override
	public void draw(Graphics g) {
		//g.drawImage(img, x, y, BALL_WIDTH, BALL_HEIGHT, this);
		
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}

	//Moves the ball
	public void move() {
		x += xDir;
		y += yDir;
	}

	//Resets the ball to original position at center of screen
	public void reset() {
		x = BALL_X_START;
		y = BALL_Y_START;
		xDir = 1;
		yDir = -1;
		Board.xSpeed = 1; //공속도 초기화
	}

	//Mutator methods
	public void setXDir(int xDir) {
		this.xDir = xDir;
	}

	public void setYDir(int yDir) {
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	//Accessor methods
	public int getXDir() {
		return xDir;
	}

	public int getYDir() {
		return yDir;
	}

	public boolean isOnScreen() {
		return onScreen;
	}
}
