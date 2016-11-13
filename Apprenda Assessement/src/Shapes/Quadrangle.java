package Shapes;



public class Quadrangle extends Shape {

	//int[][] points;

	
	public Quadrangle(int[][] inpoints)
	{
		points = inpoints;
	}
	
	
	@Override
	public String getShape() {
		
		return "Quadrangle";
	}

}
