package scroller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -6159849249299867316L;

	private Thread thread;
	private Handler handler;
	private Window frame;
	private int boxHeight;
	private int boxWidth;
	private Player player;
	private Menu menu;
	private KeyInput keyinput;
	private long lastTime;
	private double ns;
	private double delta;
	private long now;
	
	public static boolean running = true;
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12 * 9;
	
	public int cloudWidth = 64;
	public int speed = 5;
	public int ORIGINALSPEED = speed;
	public int specialSpeed = ORIGINALSPEED;
	public int objectsRemoved = 0;
	
	public static enum STATE {
		MENU,
		GAME
	}
	public STATE state = STATE.MENU;

	public Main() {
		handler = new Handler();
		menu = new Menu();
		player = new Player(15, HEIGHT - 58, ID.Player, handler, this);
		keyinput = new KeyInput(handler, this, player, menu);
		this.addKeyListener(keyinput);
		this.addMouseListener(new MouseInput(this));
		new Window(WIDTH, HEIGHT, "Game", this);
		cloudsAndBoxes();
	}

	private int rand(int low, int high) {
		return ThreadLocalRandom.current().nextInt(low, high);
	}

	public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}

	public void stop() {
		try {
			thread.join();
			running = false;
			frame.dispose();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Main();
	}

	public void run() {
		this.requestFocusInWindow();
		lastTime = System.nanoTime();
		ns = 1000000000 / 60;
		delta = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			if (running) {
				render();
			}
		}
		stop();
	}
	
	private void tick() {
		if (state == STATE.GAME) {
			handler.tick();

			GameObject obj;
			for (int i = 0; i < handler.object.size(); i++) {
				obj = handler.object.get(i);
				if (obj.getID() == ID.Cloud || obj.getID() == ID.Block) {
					//System.out.println("Speed: " + speed);
					//System.out.println("Colliding: " + player.isColliding());
					obj.setVelX(speed);
					if (obj.getX() + obj.width <= 0 && (obj.getID() == ID.Cloud || obj.getID() == ID.Block)) {
						if (obj.getID() == ID.Cloud) {
							cloudWidth = rand(32, 96);
							handler.addObject(new Cloud(WIDTH + cloudWidth,
									rand(0, HEIGHT / 2 - cloudWidth / 2), ID.Cloud, cloudWidth, this), 0);
							handler.removeObject(obj);
						} else if (obj.getID() == ID.Block) {
							boxHeight = rand(60, HEIGHT/2);
							boxWidth = rand(40, WIDTH/4);
							handler.addObject(new Block(WIDTH + boxWidth, HEIGHT - boxHeight, ID.Block, boxWidth,
									boxHeight, 0, 100, 0, this));
							handler.removeObject(obj);
							objectsRemoved++;
						}
					}
				}
			}
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 32, WIDTH, 32);
		g.setColor(Color.black);
		g.drawRect(0, HEIGHT - 32, WIDTH, 32);
		
		if (state == STATE.GAME) {
			handler.render(g);
		} else if (state == STATE.MENU) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}

	public static int clamp(int var, int min, int max) {

		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		}
		return var;
	}
	public static int clamp(int var, int min) {

		if (var <= min) {
			return min;
		}
		return var;
	}

	public void resetVars() {
		objectsRemoved = 0;
		speed = ORIGINALSPEED;
		specialSpeed = ORIGINALSPEED;
		keyinput.resetCounter();
		handler.reset();
		cloudsAndBoxes();
		player.jumpsLeft = player.originalJumps;
		player.totalJumps = 0;
	}
	
	public void cloudsAndBoxes() {
		for (int i = 0; i < 26; i++) {
			cloudWidth = rand(32, 96);
			handler.addObject(new Cloud(rand(0, WIDTH - cloudWidth / 2), rand(0, HEIGHT / 2 - cloudWidth / 2), ID.Cloud,
					cloudWidth, this));
		}

		handler.addObject(player);
		for (int i = 0; i < 5; i++) {
			boxHeight = rand(60, HEIGHT/2);
			boxWidth = rand(40, WIDTH/4);
			handler.addObject(new Block(WIDTH + 50 * i * 3, HEIGHT - boxHeight, ID.Block, boxWidth, boxHeight, 0, 100, 0, this));
		}
	}
}
