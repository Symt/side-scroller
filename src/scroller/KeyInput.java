package scroller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean keyDown = false;
	private int counter = 0;
	private Main main;
	private Player player;
	private Menu menu;

	public KeyInput(Handler handler, Main main, Player player, Menu menu) {
		this.handler = handler;
		this.main = main;
		this.player = player;
		this.menu = menu;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (main.state == Main.STATE.GAME) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getID() == ID.Player) {
					if (key == KeyEvent.VK_SPACE) {
						if (keyDown != true && player.jumpsLeft > 0) {
							tempObject.jump();
							counter += 1;
							keyDown = true;
							player.jumpsLeft--;
							if (counter >= 10) {
								counter =  0;
								main.specialSpeed++;
								main.speed = main.specialSpeed;
							}
						} else {
							if (tempObject.y == Main.HEIGHT - 56) {
								keyDown = false;
								//Main.running = false;
								main.state = Main.STATE.MENU;
								menu.score = player.totalJumps * main.objectsRemoved;
								main.resetVars();
							}
						}
					}
				}
			}
			if (key == KeyEvent.VK_ESCAPE)
				System.exit(0);

		}
	}
	
	public void resetCounter() {
		counter = 0;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (main.state == Main.STATE.GAME) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);

				if (tempObject.getID() == ID.Player) {
					if (key == KeyEvent.VK_SPACE)
						keyDown = false;
				}
			}
		}
	}
}