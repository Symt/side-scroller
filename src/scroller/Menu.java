package scroller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	private Rectangle playButton = new Rectangle(Main.WIDTH/2-110, Main.HEIGHT/2, 100, 50);
	private Rectangle quitButton = new Rectangle(Main.WIDTH/2+10, Main.HEIGHT/2, 100, 50);
	public int score = 0;
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.BLACK);
		String str = "Side Scroller";
		g.drawString(str, Main.WIDTH/2-g.getFontMetrics().stringWidth(str)/2, 100);
		
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		String play = "Play";
		String quit = "Quit";
		g.drawString(play, Main.WIDTH/2-120+g.getFontMetrics().stringWidth(play)/2, Main.HEIGHT/2+35);
		g.drawString(quit, Main.WIDTH/2+g.getFontMetrics().stringWidth(quit)/2, Main.HEIGHT/2+35);
		g2d.draw(playButton);
		g2d.draw(quitButton);
		String scr = "Score: " + score;
		g2d.drawString(scr, Main.WIDTH/2-g.getFontMetrics().stringWidth(scr)/2, Main.HEIGHT/2-50);		
		g2d.dispose();
		g.dispose();
	}
	
}
