package scroller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
	
	Main main;
	public MouseInput(Main main) {
		this.main = main;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (main.state == Main.STATE.MENU) {
			if (mx >= Main.WIDTH / 2 - 110 && mx <= Main.WIDTH / 2 - 10 && my >= Main.HEIGHT / 2
					&& my <= Main.HEIGHT / 2 + 50) {
				// Play Button
				main.resetVars();
				main.state = Main.STATE.GAME;
			} else if (mx >= Main.WIDTH / 2 + 10 && mx <= Main.WIDTH / 2 + 110 && my >= Main.HEIGHT / 2
					&& my <= Main.HEIGHT / 2 + 50) {
				// Quit Button
				System.exit(0);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
