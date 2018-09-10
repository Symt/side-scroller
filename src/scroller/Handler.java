package scroller;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		GameObject tempObject;
		for (int i = 0; i < object.size(); i++) {
			tempObject = object.get(i);
			tempObject.tick();
		}
	}
	public void render(Graphics g) {
		GameObject tempObject;
		for (int i = 0; i < object.size(); i++) {
			tempObject = object.get(i);
			tempObject.render(g);
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
			
}
