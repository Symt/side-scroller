package scroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

public class Cloud extends GameObject {
	private int width;
	public Cloud(int x, int y, ID id, int width, Main main) {
		super(x, y, id);

		
		this.width = width;
		velX = main.speed;
	}


	public void tick() {
		move();
	}
	
	private void move() {
		x -= velX;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		
		RoundRectangle2D.Float smallCircle = new RoundRectangle2D.Float(x, y, width, width, width / 4 * 3, width / 4 * 3);
		g2d.fill(smallCircle);
	}

	public Rectangle getBounds() {
		return null;
	}

}
