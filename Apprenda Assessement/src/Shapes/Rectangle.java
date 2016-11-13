package Shapes;



public class Rectangle extends Shape {

	//int[][] points;

	
	public Rectangle(int[][] inpoints)
	{
		points = inpoints;
	}
	
	
	@Override
	public String getShape() {
		
		return "Rectangle";
	}

}
