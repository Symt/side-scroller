package scroller;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Trail extends GameObject {

	private float alpha = 1;
	private Handler handler;
	private Color color;
	private int width, height;
	private float life;
	
	// life = 0.01 -> 0.1
	
	public Trail(int x, int y, ID id, Handler handler, int width, int height, float life, Color color) {
		super(x, y, id);
		
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;
		valLife();
	}

	public void tick() {
		if (alpha > life) {
			alpha -= life - 0.0001f;
		} else {
			handler.removeObject(this);
		}
	}
	
	private void valLife() {
		if (life < 0.01 && life > .1) {
			life = 0.01f; // default
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g2d.setComposite(makeTransparent(1));
		
		g2d.dispose();
		g.dispose();
		
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		
		return AlphaComposite.getInstance(type, alpha);
	}

	public Rectangle getBounds() {
		return null;
	}

}
