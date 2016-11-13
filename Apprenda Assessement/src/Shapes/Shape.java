package Shapes;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;

/**
 * 
 * @author Aaron
 *
 *Shape is an astract class that represents any polygon.
 *It contains functions that allow the user to chack for adjacency, containment, and intersection on any of its subclasses regardless of
 *position or angle.  All points are handled as ints, however it could be easily adjust to handle doubles
 *
 */
public abstract class Shape {
	int[][] points;

	/**
	 * 
	 * @param x - the x coordinate of the test point
	 * @param y - the y coordinate of the test point
	 * @return boolean- true if point is inside, false if outside.
	 */
	public boolean isPointIn(int x, int y) {
		
		int i;
	    int j;
	    boolean result = false;
	    for (i = 0, j = points.length - 1; i < points.length; j = i++) 
	    {
	    	if ((points[i][1] > y) != (points[j][1] > y) &&
	            (x < (points[j][0] - points[i][0]) * (y - points[i][1]) / (points[j][1]-points[i][1]) + points[i][0])) {
	        result = !result;
	    }
	      }
	      return result;
	}
        
        /**
         * This function is used to check if two line segments intersect and to find at what point they do
         * @param x1 -the x coordinate of the first point
         * @param x2 - the x coordinate of the second point
         * @param x3 - the x coordinate of the second point
         * @param y1 - the y coordinate of the first point
         * @param y2 - the y coordinate of the second point
         * @param y3 - the y coordinate of the second point
         * @return ArrayList<double[]> representing the points the lines intersect at
         */
        public ArrayList<double[]> segmentIntersect(int x1 ,int x2, int y1, int y2)
        {
            Line2D seg1 = new Line2D.Double(x1,y1,x2,y2);
            ArrayList<double[]> pointList = new ArrayList<double[]>();
            int count = 0;
	    int i = 0;
            int j;
	    for (i = 0, j = points.length - 1; i < points.length; j = i++) 
	    {
               Line2D seg = new Line2D.Double(points[i][0],points[i][1],points[j][0],points[j][1]);
               if(seg1.intersectsLine(seg))
               {
                   pointList.add(intersectPoint(seg, seg1));
                   count++;
               }
	    }
            return pointList;
        }
        
        
        public double[] intersectPoint(Line2D seg1, Line2D seg2)
        {
            double slopeA = (seg1.getY1()-seg1.getY2())/(seg1.getX1()-seg1.getX2());
            double slopeB = (seg2.getY1()-seg2.getY2())/(seg2.getX1()-seg2.getX2());
            double X = (slopeA*seg1.getX1()-seg1.getY1()+seg2.getY1()-(slopeB*seg2.getX1()))/(slopeA-slopeB);
            double y = slopeA*(X-seg1.getX1())+seg1.getY1();
            double[] out = new double[2];
            if((seg1.getX1()-seg1.getX2())==0)
            {
                X = seg1.getX1();
                y = slopeA*(X-seg2.getX1())+seg2.getY1();
            }
            if((seg2.getX1()-seg2.getX2())==0)
            {
                X = seg2.getX1();
                y = slopeA*(X-seg1.getX1())+seg1.getY1();
            }
            out[0] = X;
            out[1] = y;
            return out;
        }
	
	/**
	 *  A function to check if a shape is contained within another shape
	 * @param b - the shape we are testing to see if it is within this
	 * @return boolean - true if contained false if not
	 */
	public boolean checkContainment(Shape b) 
        {
		int[][] bPoints = b.getPoints();
		int count = 0;
		int i = 0;
		for(int[] a : bPoints)
		{
			if(isPointIn(a[0],a[1]))
			{
				count++;
			}
			i++;
		}
		if(count==i)return true;
		else return false;
	}
	
	/**
	 * A function to check if two shapes intersect by looking for at least on point in the other shape
	 * @param b - the shape to test
	 * @return boolean - true if intersecting, false if not
	 */
	public ArrayList<double[]> checkIntersect(Shape b) {
		int[][] bPoints = b.getPoints();
                int count = 0;
                ArrayList<double[]> outPoints = new ArrayList<double[]>();
		for(int[] a : bPoints)
		{
			if(isPointIn(a[0],a[1]))
			{
				count++;
			}
		}
                if(count<4 && count>0)
                {
                    int i = 0;
                    int j;
                    for (i = 0, j = bPoints.length - 1; i < bPoints.length; j = i++) 
                    {
                        outPoints.addAll(segmentIntersect(bPoints[i][0],bPoints[j][0],bPoints[i][1],bPoints[j][1]));
                    }
                    return outPoints;
                }
		return null;
	}
	
	/**
	 * A function to test whether two shapes are adjacent
	 * @param b the shape to test
	 * @return boolean - true if Adjacent, false if not
	 * 
	 */
	public boolean checkAdjacency(Shape b) {
		int i,j,k,l;
		int[][] bPoints = b.getPoints();
		for (i = 0, j = points.length - 1; i < points.length; j = i++) 
		{
			 for (k = 0, l = bPoints.length - 1; k < bPoints.length; l = k++) 
			 {
				if(checkPointOnLine(points[i],points[j],bPoints[k],bPoints[l])) return true;
				
			 }
		}
		return false;
	}
	
	/**
	 * A function that tests if two line segments are on the same line and are touching
	 * 
	 * @param AX - int[] one point on This
	 * @param AY - int[] Adjacent point on This
	 * @param BX - int[] one point on the test shape
	 * @param BY - int[] adjacent point on the test shape
	 * @return boolean - true if on same line and touching, false if not
	 * 
	 * Note:  This function was a tricky one.  It works by finding the angle that a line starting at the origin with the same slope as the base
	 * points (AX,AY) would have it then uses this with matrix rotation to rotate the axis of the 'graph' so that the line the points are 
	 * on has a slope of 0.  It then checks to see if the B points (BX,BY) are on the the same line as the A points by comparing y values.
	 * It then finally checks to see if any of the points are between the two on the other shape and thus 'touching' the other line segment
	 */
	public boolean checkPointOnLine(int[] AX, int[] AY, int[] BX, int[] BY) {
		double slope;
		double theta = 0;
		if(AX[0]-AY[0]!=0)
		{
			slope = (AX[1]-AY[1])/(double)(AX[0]-AY[0]);
			
			theta = Math.atan2((double)(AX[1]-AY[1]),(double)(AX[0]-AY[0]));
		} else theta = (90*Math.PI)/180;
		double[][] A = new double[4][];
		for(int i = 0;i<A.length;i++)
		{
			A[i] = new double[2];
		}
		A[0][0] = (double)AX[0];
		A[0][1] = (double)AX[1];
		A[1][0] = (double)AY[0];
		A[1][1] = (double)AY[1];
		A[2][0] = (double)BX[0];
		A[2][1] = (double)BX[1];
		A[3][0] = (double)BY[0];
		A[3][1] = (double)BY[1];
		//theta = (theta*Math.PI)/180;
		for(int i=0;i<A.length;i++)
		{
			double temp = A[i][0];
			A[i][0] =  (A[i][0]*Math.cos(theta))+A[i][1]*Math.sin(theta);
			A[i][1] = A[i][1]*Math.cos(theta)-temp*Math.sin(theta);
		}
		boolean out;
		for(int i = 0;i<2;i++)
		{
			for(int j=0;j<A.length;j++)
			{
				if(!(Math.abs((A[i][1]-A[j][1]))<0.000001))return false;
			}
		}
		for(int i=2;i<A.length;i++)
		{
			if(((A[i][0]-A[0][0])>=0 && (A[i][0]-A[1][0])<=0) || ((A[i][0]-A[1][0])>=0 && (A[i][0]-A[0][0])<=0))
			{
				return true;
			}
		}
		for(int i=0;i<2;i++)
		{
			if(((A[i][0]-A[2][0])>=0 && (A[i][0]-A[3][0])<=0) || ((A[i][0]-A[3][0])>=0 && (A[i][0]-A[2][0])<=0))
			{
				return true;
			}
		}
		
		return false;
	}
	/**
	 * 
	 * @return a String which is the type of shape, NEEDS to be implemented for each child of Shape
	 */
	public String getShape()
	{
		return null;
	}
	/**
	 * 
	 * @return an int[][] of the points in the shape
	 */
	public int[][] getPoints() {
		return points;
	}

}
