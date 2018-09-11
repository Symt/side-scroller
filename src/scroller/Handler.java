package scroller;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	GameObject temp0;
	GameObject temp1;
	
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			temp0 = object.get(i);
			temp0.tick();
		}
	}
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			temp1 = object.get(i);
			temp1.render(g);
		}
	}
	
	public void addObject(GameObject obj) {
		object.add(obj);
	}
	public void removeObject(GameObject obj) {
		object.remove(obj);
	}
	public void addObject(GameObject obj, int i) {
		object.add(i, obj);
	}
	public void reset() {
		object = new LinkedList<GameObject>();
	}
			
}
