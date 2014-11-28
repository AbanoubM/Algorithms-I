/*************************************************************************
 * Name: Abanoub Milad
 * Email: abanoubcs@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER; // YOUR DEFINITION HERE

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
		SLOPE_ORDER = new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				double s1 = slopeTo(o1), s2 = slopeTo(o2);
				if (s1 < s2)
					return -1;
				if (s1 > s2)
					return 1;
				return 0;
				// return (int) (slopeTo(o1) - slopeTo(o2));
			}
		};
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		/* YOUR CODE HERE */
		int dx = this.x - that.x;
		int dy = this.y - that.y;
		if (dx == 0 && dy == 0) {
			return Double.NEGATIVE_INFINITY;
		} else if (dx == 0) {
			return Double.POSITIVE_INFINITY;

		} else if (dy == 0) {
			return 0;
		} else {
			return dy / (double) dx;
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		/* YOUR CODE HERE */
		if (this.y < that.y)
			return -1;
		if (this.y > that.y)
			return 1;
		if (this.x < that.x)
			return -1;
		if (this.x > that.x)
			return 1;
		return 0;

	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
