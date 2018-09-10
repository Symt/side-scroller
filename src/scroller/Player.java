package scroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;

public class Player extends GameObject {

	private Handler handler;
	private int fromtop;
	private int fromleft;
	private boolean colliding;
	private int clampValue;
	private boolean maybeLeft = true;
	public static int jumpsLeft = 5;
	public static int originalJumps = jumpsLeft;
	public int totalJumps = 0;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;
		height = 28;
		width = 28;
		clampValue = Main.WIDTH - 56;
	}

	public void tick() {

		update();
		collision();

		// handler.addObject(new Trail(x, y, ID.Trail, handler, 28, 28, .05f,
		// Color.blue));
	}

	public void update() {
		long time = new Date().getTime();
		jumpTime = time - startTime;
		if (velY <= -10 && jumping) {
			jumping = false;
			falling = true;
			velY = 0;
		}
		gravity();
		y = Main.clamp(y + velY, 0, clampValue);

	}

	private void gravity() {
		if (!top || jumping) {
			if (falling) {
				velY += 1;
			} else if (jumping) {
				velY -= 1;
			}
		} else {
			velY = 0;
		}
	}

	public void jump() {
		totalJumps++;
		jumping = true;
		falling = false;
		startTime = new Date().getTime();
		velY = 0;
	}

	public void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject obj = handler.object.get(i);
			if (obj.getID() == ID.Block) {
				if (getBounds().intersects(obj.getBounds())) {
					obj.colliding = true;
					colliding = true;
					int[] border = { x, y, x + width, y + height }; // left, clockwise
					int[] objectBorder = { obj.x, obj.y, obj.x + obj.width, obj.y + obj.height };
					fromtop = Math.abs((y + height) - obj.y);
					fromleft = Math.abs((x + width) - obj.x);
					int lowest = Math.min(fromtop, fromleft);

					if (lowest == fromtop) {
						bottom = false;
						left = false;
						top = true;
					} else if (lowest == fromleft) {
						bottom = false;
						top = false;
						left = true;
					} else if (objectBorder[3] <= border[1] && fromtop != fromleft) {
						bottom = true;
						top = false;
						left = false;
					} else {
						if (fromtop == fromleft) {
							top = true;
							left = false;
							bottom = false;
						}
					}
					if (border[2] >= objectBorder[0] && left) {
						if (maybeLeft) {
							Main.speed = 0;
							int margin = border[2] - objectBorder[0];
							if (margin > 0) {
								GameObject temp;
								for (int k = 0; k < handler.object.size(); k++) {
									temp = handler.object.get(k);

									if (temp.getID() == ID.Block || temp.getID() == ID.Cloud) {
										temp.x += margin;
									}
								}
							}

						}
					} else if (border[3] >= objectBorder[1] && top) {
						jumpsLeft = originalJumps;
						Main.speed = Main.ORIGINALSPEED;
						clampValue = obj.y;
						y = obj.y - height;
						velY = 0;
					} else if (border[1] >= objectBorder[3] && bottom) {
						Main.speed = Main.ORIGINALSPEED;
						clampValue = Main.HEIGHT - 56;
						velY = 0;
						falling = true;
					}

					break;
				} else {
					maybeLeft = true;
					if (obj.colliding) {
						if (obj.x - (x + width) == 0) {
							colliding = true;
						} else {
							colliding = false;
							obj.colliding = false;
						}
						if (obj.y - (y + height) >= 0 || ((obj.y + obj.height) - y <= 0 && bottom)) {
							colliding = false;
							obj.colliding = false;
						}
					}
					clampValue = Main.HEIGHT - 56;
					allFalse();
				}
			}
		}
		if (!top && !bottom && !left && !isColliding()) {
			Main.speed = Main.ORIGINALSPEED;
		}
	}

	public void allFalse() {
		top = false;
		left = false;
		bottom = false;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 28, 28);
	}

	public void render(Graphics g) {
		// Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.blue);
		g.fillRect(x, y, 28, 28);
	}

	public boolean isColliding() {
		return colliding;
	}
}
