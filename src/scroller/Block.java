package scroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends GameObject {

	private int r, g, b;
	public int height;
	public boolean colliding = false;
	public int weight;
	
	public Block(int x, int y, ID id, int width, int height, int r, int g, int b) {
		super(x, y, id);
		
		this.width = width;
		this.height = height;
		this.r = r;
		this.g = g;
		this.b = b;
		velX = Main.speed;
	}

	public void tick() {
		move();
	}
	private void move() {
		x -= velX;
	}

	public void render(Graphics g1) {
		g1.setColor(new Color(r, g, b));
		
		Graphics2D g2d = (Graphics2D) g1;
		rect = new Rectangle(x, y, width, height);
		g2d.fill(rect);
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	
	
}
