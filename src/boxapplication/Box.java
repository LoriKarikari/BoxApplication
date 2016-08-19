package boxapplication;

import java.util.ArrayList;

public class Box<T extends Package> implements Comparable<Box<T>> {
	private double height, width, weight;
	private boolean danger;
	private Color color;

	private ArrayList<T> boxes = new ArrayList<>();

	public Box(double height, double width, double weight, boolean danger, Color color) {
		this.height = height;
		this.width = width;
		this.weight = weight;
		this.danger = danger;
		this.color = color;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean getDanger() {
		return danger;
	}

	public void setDanger(boolean danger) {
		this.danger = danger;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean addBox(T box) {
		boxes.add(box);
		return true;
	}

	@Override
	public int compareTo(Box<T> box) {
		if (this.weight > box.getWeight()) {
			return -1;
		} else {
		if (this.weight < box.getWeight()) {
			return 1;
		} else {
			return 0;
			}
		}
	}

	@Override
	public String toString() {
		return "Box [height=" + height + ", width=" + width + ", weight=" + weight + ", danger=" + danger + ", color="
				+ color + "]";
	}
}
