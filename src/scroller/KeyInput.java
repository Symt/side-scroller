package scroller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean keyDown = false;
	private int counter = 0;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_SPACE) {
					if (keyDown != true && Player.jumpsLeft > 0) {
						tempObject.jump();
						counter += 1;
						keyDown = true;
						Player.jumpsLeft--;
						if (counter >= 10) {
							counter = 0;
							Main.ORIGINALSPEED++;
							Main.speed = Main.ORIGINALSPEED;
						}
					} else {
						if (tempObject.y == Main.HEIGHT-56) {
							Main.running = false;
						}
					}
				}
			}

			if (key == KeyEvent.VK_ESCAPE)
				System.exit(0);

		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_SPACE)
					keyDown = false;
			}
		}
	}
}