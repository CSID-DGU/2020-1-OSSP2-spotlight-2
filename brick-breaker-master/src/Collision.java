
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
public class Collision extends Structure implements Constants, ImageObserver {
	//Variables
	private int lives, hits;
	private double rateX, rateY;
	private boolean destroyed;
	public Item item;

	//블록 이미지 img에 저장
	ImageIcon icon = new ImageIcon("./img/explosion.gif");
	Image img = icon.getImage();
	
	
	//Toolkit.getDefaultToolkit().getImage
	//Constructor
	public Collision(int x, int y, int width, int height, Color color, double rateX, double rateY) {
		super(x, y, width, height, color);
		setRateX(rateX);
		setRateY(rateY);
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

//	//프레임 크기에 따른 벽돌 크기, 좌표 재설정
//	public void changeBrickSet() {
//			setX((int)Math.round(((double)Board.FrameWidth/getRateX())));
//			setY((int)Math.round(((double)(Board.FrameHeight/3)/getRateY())));
//			setWidth(Board.FrameWidth/10);
//			setHeight(Board.FrameHeight/10);
//			//벽돌의 위치 이동에 따라 아이템 크기 좌표 재설정
//			item.setX(getX() + (width / 4));
//			item.setY(getY() + (height / 4));
//			item.setWidth(Board.FrameWidth/25);
//			item.setHeight(Board.FrameHeight/50);
//	}
//	
	
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public void setRateX(double rateX) {
		this.rateX = rateX;
	}
	
	public void setRateY(double rateY) {
		this.rateY = rateY;
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
