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
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

//Class definition
public class Ball extends Structure implements Constants {
	//Variables
	private boolean onScreen;
	private int xDir = (Board.FrameWidth/486), yDir = -(Board.FrameHeight/463);

	//Constructor
	public Ball(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		setOnScreen(true);
	}

	ImageIcon icon = new ImageIcon("./img/ball.png");
	Image img = icon.getImage();
	
	//Draw the ball
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
		
		//g.setColor(color);
		//g.fillOval(x, y, width, height);
	}

	public void changeBallSet() {
		setX(getX());
		setY(getY());
		setWidth((int)((10.0/486.0)*Board.FrameWidth));
		setHeight((int)((10.0/486.0)*Board.FrameHeight));
    }
	
	//Moves the ball
	public void move() {
		x += xDir;
		//System.out.printf("xDir : %lf",xDir);
		y += yDir;
		//System.out.printf("yDir : %lf",yDir);
	}

	//Resets the ball to original position at center of screen
	public void reset() {
		x = Board.FrameWidth/2;
		y = Board.FrameHeight/2;
		xDir = 1;
		yDir = -1;
		Board.xSpeed = 1; //공속도 초기화
	}

	//Mutator methods
	public void setXDir(int xDir) {
		this.xDir = xDir;
	}

	public void setYDir(int yDir) {
		this.yDir = yDir;
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
