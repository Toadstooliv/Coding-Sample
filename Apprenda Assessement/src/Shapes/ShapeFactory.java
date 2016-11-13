package Shapes;


import Shapes.*;
import java.lang.Math;

public class ShapeFactory {
	
	/**
	 * Factory function used to make shapes based on how many points they have.
	 * @param points - a 2 dimensional array of pairs of integers representing the points in a shape.
	 * @return Return the shape that was created
	 */
	public static Shape makeShape(int[][] points)
	{
		switch (points.length)
		{
			case 4: if(isRect(points))return new Rectangle(points);else return new Quadrangle(points);
		}
		return null;
	}
	
/**
 * This is a function to decide if a 4 sided shape is a Rectangle
 * @param points - a 2 dimensional array of pairs of integers representing the points in a shape.
 * @return true if the shape is a rectagle, false if it is not
 */
	public static boolean isRect(int[][] points)
	{
		if((points[0][0]-points[1][0])==0 && (points[1][1]-points[2][1])==0)
		{
			return true;
		}
		
		if(Math.abs((points[0][1]-points[1][1])/(points[0][0]-points[1][0]))!=Math.abs((points[1][0]-points[2][0])/(points[1][1]-points[2][1])))
		{
			return false;
		}
		
		return true;
	}
	
}
