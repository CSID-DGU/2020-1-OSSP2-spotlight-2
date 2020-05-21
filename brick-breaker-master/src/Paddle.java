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

//This "Paddle" class extends the "Structure" class. It is used for the player's paddle in the game.

//Imports
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//Class definition
public class Paddle extends Structure implements Constants {
	Graphics g;
   //Constructor
   public Paddle(int x , int y, int width, int height, Color color) {
      super(x, y, width, height, color);
   }
	//Draws the paddle
   
    ImageIcon paddle1 = new ImageIcon("./img/paddle1.png"); //일반 패들 이미지
    ImageIcon paddle2 = new ImageIcon("./img/paddle2.png"); //리버스 패들 이미지
	Image pad_1 = paddle1.getImage(); //일반 패들
	Image pad_2 = paddle2.getImage(); //리버스 패들
	
	@Override
	public void draw(Graphics g) {
		if(Board.reverse == false) {
			g.drawImage(pad_1, x, y, width, height, null);
		}
		else {
			g.drawImage(pad_2, x, y, width, height, null);
		}
	}

	//프레임 크기에 따른 패들 크기, 좌표 재설정
	public void changePaddleSet() {
		setX(getX());
		setY(Board.FrameHeight-13);
		if(Item.check == 0)
		{
			setWidth((int)((70.0/486.0)*Board.FrameWidth));
		}
		else if(Item.check == 1)
		{

			setWidth((int)((70.0/486.0)*Board.FrameWidth+Board.FrameWidth/25));
		}
		else if(Item.check == -1)
		{
			setWidth((int)((70.0/486.0)*Board.FrameWidth-Board.FrameWidth/25));
		}
		setHeight((int)((10.0/486.0)*Board.FrameHeight));
    }
	
	//Places the paddle back in starting position at center of screen(하단 바 리셋) 
	public void reset() 
	{
		x = (Board.FrameWidth/2)-(PADDLE_WIDTH/2);
		y = Board.FrameHeight - 13;
		Item.check = 0;
		setWidth((int)((70.0/486.0)*Board.FrameWidth)); //하단 바 크기 초기화
		Board.reverse = false;//리버스 모드 비활성화
	}
	
	//하단 바를 왼쪽으로 이동(숫자를 조절해 하단 바의 속도 조절)
	public void moveLeft() {
		x -= (Board.FrameWidth*2)/486;
	}
	//하단바를 오른쪽으로 이동(숫자를 조절해 하단 바의 속도 조절)
	public void moveRight() {
		x += (2*Board.FrameWidth)/486;
	}
	//Checks if the ball hit the paddle
	public boolean hitPaddle(int ballX, int ballY) {
		if ((ballX >= x) && (ballX <= x + width) && ((ballY >= y - Board.xSpeed) && (ballY <= y + height))) {
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
