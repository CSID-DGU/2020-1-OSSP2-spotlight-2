
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
import javax.swing.ImageIcon;

//Class definition
public class collision extends Structure implements Constants, ImageObserver {
	//Variables
	private int lives, hits;
	private double rateX, rateY;
	private boolean destroyed;

	//블록 이미지 img에 저장
	ImageIcon icon = new ImageIcon("./img/HEART.png");
	Image img = icon.getImage();
	
	
	//Toolkit.getDefaultToolkit().getImage
	//Constructor
	public collision(int x, int y, int width, int height, Color color, double rateX, double rateY, int lives, int itemType) {
		super(x, y, width, height, color);
		setRateX(rateX);
		setRateY(rateY);
		setLives(lives);
		setHits(0);
		setDestroyed(false);
	}

	//Draws a brick
	@Override
	public void draw(Graphics g) {
		if (!destroyed) {
	        //img에 저장된 블록 이미지를 블록 크기에 맟춰 그리기
			g.drawImage(img, x, y, width, height, this);
		}
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

	public void setRateX(double rateX) {
		this.rateX = rateX;
	}
	
	public void setRateY(double rateY) {
		this.rateY = rateY;
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

	public double getRateX() {
		return rateX;
	}
	
	public double getRateY() {
		return rateY;
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
