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

//This "Item" class extends the "Structure" class. It is used for the items that fall from some bricks.

//Imports
import java.awt.*;

//Class definition
public class Item extends Structure implements Constants {
	//Variables
	private int type;

	Ball b = Board.ball; //Board의 ball을 저장
	//---------------------------------------------------------
	public static boolean check = false;
	
	//Constructor
	public Item(int x, int y, int width, int height, Color color, int type) {
		super(x, y, width, height, color);
		setType(type);
	}

	//Draw an item
	public void draw(Graphics g) {
		//board.java의 makeBrick()에서 itemType 확률에 맞게 조건 변경
		//Basic 모드 아이템
		if(Board.gameMode == 0) {
			if(type >= 6 && type <= 10 ) {
				return;
			}
			g.setColor(color);
			g.fillRect(x, y, width, height);
		}
		//Hard 모드 아이템
		else {
			if(type >= 5 && type <= 10 ) {
				return;
			}
			g.setColor(color);
			g.fillRect(x, y, width, height);
		}
	}

	//Drop the item down towards the paddle at slow pace
	public void drop() {
		y += 1;
	}

	//Resize the paddle, depending on which item is caught. Changes in increments of 15 until min/max width is reached.
	public void resizePaddle(Paddle p) {
		if (getType() == 1 && p.getWidth() < PADDLE_MAX) {
			//System.out.println(p.getWidth());
			p.setWidth(p.getWidth() + 15);
			//System.out.println(p.getWidth());
			check = true;
			return;
		}
		else if (getType() == 2 && p.getWidth() > PADDLE_MIN) {
			//System.out.println(p.getWidth());
			p.setWidth(p.getWidth() - 15);
			//System.out.println(p.getWidth());
			check= true;
			return;
		}
		check= false;
	}

	/*공의 속도 변경*/
	public void changeBallSpeed(Paddle p) {
		//공 속도 증가
		if(getType() == 3 && Board.xSpeed < 2) {
			Board.xSpeed++;
			if(b.getXDir() < 0 && b.getYDir() < 0) {
				b.setXDir(-Board.xSpeed);
				b.setYDir(-Board.xSpeed);
			}
			else if(b.getXDir() > 0 && b.getYDir() > 0) {
				b.setXDir(Board.xSpeed);
				b.setYDir(Board.xSpeed);
			}
			else if(b.getXDir() > 0 && b.getYDir() < 0) {
				b.setXDir(Board.xSpeed);
				b.setYDir(-Board.xSpeed);
			}
			else {
				b.setXDir(-Board.xSpeed);
				b.setYDir(Board.xSpeed);
			}
		}
		//공 속도 감소
		else if(getType() == 4 && Board.xSpeed > 1) {
			Board.xSpeed--;
			if(b.getXDir() < 0 && b.getYDir() < 0) {
				b.setXDir(-Board.xSpeed);
				b.setYDir(-Board.xSpeed);
			}
			else if(b.getXDir() > 0 && b.getYDir() > 0) {
				b.setXDir(Board.xSpeed);
				b.setYDir(Board.xSpeed);
			}
			else if(b.getXDir() > 0 && b.getYDir() < 0) {
				b.setXDir(Board.xSpeed);
				b.setYDir(-Board.xSpeed);
			}
			else {
				b.setXDir(-Board.xSpeed);
				b.setYDir(Board.xSpeed);
			}
		}
	}
	
	//리버스 모드 활성화 / 비활성화
	public void reverseMode(Paddle p) {
		//Basic 모드만 적용
		if(Board.gameMode == 0) {
			//리버스 아이템 획득
			if (getType() == 5) {
				//리버스 비활성화 상태라면 활성화로 변경
					if (Board.reverse == false) {
						Board.reverse = true;
						p.setColor(Color.RED);//하단 바 색상 RED로 변경
					}
					//리버스 활성화 상태라면 비활성화 상태로 변경
					else {
						Board.reverse = false;
						p.setColor(Color.BLACK); //하단 바 색상 BLACK으로 변경
					}
				}
		}
	}
	//Set the item's type
	public void setType(int type) {
		this.type = type;
	}

	//Get the item's type
	public int getType() {
		return type;
	}
}
