import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Shapes.*;
import java.util.ArrayList;

import javax.xml.bind.ParseConversionEvent;
/**
 * 
 * @author Aaron
 *
 *This main is a simple demonstration setup to demonstrate the Shape Abstract class family of classes.
 *It allows for basic operations with the Shape functions by allowing you to add shapes and perform any action offered by the shape class
 *
 */
public class Main {
	
	
	static Map<String, Shape> shapes;
	static BufferedReader br;

	public static void main(String[] args) {
		shapes = new HashMap<String, Shape>();
		br = new BufferedReader(new InputStreamReader(System.in));
		defShape();
		String Command;
		int exit = 0;
		while(exit == 0)
		{
			char s = 65;
			System.out.println("Please Enter A Command: ");
			Command = null;
			try{
				Command = br.readLine();
			} catch(IOException ioe){
				System.out.println("Something went wrong");
			}
			if(Command.equals("AS"))
			{
				AddShape(br);
			}
			if(Command.equals("LS"))
			{
				Set<String> temp = shapes.keySet();
				System.out.println("Printing Shapes:");
				for(String e:temp)
				{
					System.out.println("	Key: " + e + "   Shape: " +shapes.get(e).getShape());
				}
			}
			if(Command.equals("CI"))
			{
				String name = null;
				String[] names = getNames();
				if(names==null)continue;
				ArrayList<double[]> intersect = shapes.get(names[1]).checkIntersect(shapes.get(names[0]));
				if(intersect!=null)
				{
					System.out.println(names[1]+ " Intersects with "+names[0]);
                                        for(double[] a : intersect)
                                        {
                                            System.out.println(" Intersect At: (" + a[0] + ", " + a[1] + ")");
                                        }
					continue;
				}
				intersect = shapes.get(names[0]).checkIntersect(shapes.get(names[1]));
				if(intersect!=null)
				{
					System.out.println(names[0]+ " Instersects with "+names[1]);
					continue;
				}
				System.out.println("No intersection found");
					
			}
			if(Command.equals("CC"))
			{
				String name = null;
				String[] names = getNames();
				if(names==null)continue;
				boolean intersect = shapes.get(names[0]).checkContainment(shapes.get(names[1]));
				if(intersect)
				{
					System.out.println(names[1]+ " Contains "+names[0]);
					continue;
				}
				intersect = shapes.get(names[0]).checkContainment(shapes.get(names[1]));
				if(intersect)
				{
					System.out.println(names[0]+ " Contains "+names[1]);
					continue;
				}
				System.out.println("No containment found");
					
			}
			if(Command.equals("SI"))
			{
				String name = null;
				boolean notAName =false;
				try{
					while(!notAName){
						System.out.println("Enter a Shape to get the details of(escape with \"q\"):");
						name = br.readLine();
						if(name.equals("q"))continue;
						notAName = shapes.containsKey(name);
						if(!notAName)
						{
							System.out.println("Name: " + name+", is not a name");
							continue;
						}
					}
					Shape theShape = shapes.get(name);
					System.out.print("Key: " + name+"\nShape: "+theShape.getShape()+"\nPoints:\n");
					int[][] thePoints = theShape.getPoints();
					for(int i = 0;i<thePoints.length;i++)
					{
						System.out.println((i+1)+". ("+thePoints[i][0]+", "+thePoints[i][1]+")");
					}
				} catch(IOException ioe){
					System.out.println("WHAT DID YOU DO!?!?!");
				}
			}
			if(Command.equals("CA"))
			{
				String name = null;
				String[] names = getNames();
				if(names==null)continue;
				boolean intersect = shapes.get(names[0]).checkAdjacency(shapes.get(names[1]));
				if(intersect)
				{
					System.out.println(names[1]+ " is Adjacent to "+names[0]);
					continue;
				}
				System.out.println("No Adjacency found");
					
			}
			if(Command.equals("q"))break;
			if(Command.equals("help"))
			{
				System.out.println("Commands are:\n\"AS\" : allows you to add a shape point by point\n"+
						"\"LS"+ "\" : allows you to see all shapes\n"+
						"\"CI\" : allows you to check if two shapes intersect\n"+
						"\"CC\" : allows you to check for containment of shapes\n"+
						"\"SI\" : Shows specific information about a shape\n"+
						"\"Q"+ "\" : Quits the applet");
			}
		}
		

	}
	/**
	 * A function design to add specified shapes to the system
	 * 
	 * @param in the buffered reader to use
	 * @return a boolean that is true if the shape was added, and false if it wasn't
	 */
	static boolean AddShape(BufferedReader in)
	{
		boolean Cont = true;
		String Command = null;
		String name = null;
		String totin = "";
		String[] points;
		int[][] allPoint;
		boolean notAName = true;
		try{
		while(notAName){
			System.out.println("Enter a Shape name:");
			name = in.readLine();
			notAName = shapes.containsKey(name);
			if(notAName)
			{
				System.out.println("Name: " + name+", is already in use");
			}
		}
		while(Cont)
		{
			System.out.println("Enter a point of form \"x,y\" in integers(add points in order around shape) : enter q to stop entering points:");
				Command = in.readLine();
				if(Command.equals("q"))break;
				totin += Command+":";
				
			
		}
		points = totin.split(":");
		allPoint = new int[points.length][];
		for(int i = 0;i<points.length;i++)
		{
			String[] temp = (points[i].trim()).split(",");
			int[] temp2 = new int[2];
			temp2[0] = Integer.parseInt(temp[0].trim());
			temp2[1] = Integer.parseInt(temp[1].trim());
			allPoint[i] = temp2;
					
		}
		Shape out = Shapes.ShapeFactory.makeShape(allPoint);
		if(out==null)
		{
			System.out.println("Shape with "+points.length+" sides not available, please implement it");
			return false;
		}
		shapes.put(String.valueOf(name), out);
		} catch(IOException ioe){
				System.out.println("WHAT DID YOU DO!?!?!");
		}
		return true;
	}
	
	/**
	 * A function to handle the input of 2 shape names seperated by a comma.
	 * @return null if the user cancelled input or a String array of the names
	 */
	static String[] getNames()
	{
		String name = null;
		String[] names = null;
		boolean notAName =false;
		try{
			while(!notAName){
				System.out.println("Enter shapes to compare seperated by a comma(escape with \"q\"):");
				name = br.readLine();
				if(name.equals("q"))return null;
				names = name.split(",");
				if(names.length!=2)
				{
					System.out.println("Name: " + name+", is not a name");
					continue;
				}
				names[0] = names[0].trim();
				names[1] = names[1].trim();
				notAName = shapes.containsKey(names[0]);
				if(!notAName)
				{
					System.out.println("Name: " + names[0]+", is not a name");
					continue;
				}
				notAName = shapes.containsKey(names[1]);
				if(!notAName)
				{
					System.out.println("Name: " + names[1]+", is not a name");
				}
			}
			return names;
		} catch(IOException ioe){
			System.out.println("WHAT DID YOU DO!?!?!");
		}
		return null;
	}
	/**
	 * A default function that adds a few examples of different rectangles
	 */
	public static void defShape(){
		int[][] adder = new int[4][2];
		adder[0][0]=1;
		adder[0][1]=1;
		adder[1][0]=2;
		adder[1][1]=2;
		adder[2][0]=3;
		adder[2][1]=1;
		adder[3][0]=0;
		adder[3][1]=2;
		
		shapes.put("1", Shapes.ShapeFactory.makeShape(adder));
		adder = new int[4][2];
		adder[0][0]=1;
		adder[0][1]=3;
		adder[1][0]=2;
		adder[1][1]=4;
		adder[2][0]=5;
		adder[2][1]=1;
		adder[3][0]=4;
		adder[3][1]=0;
		
		shapes.put("2", Shapes.ShapeFactory.makeShape(adder));
		
		adder = new int[4][2];
		adder[0][0]=1;
		adder[0][1]=1;
		adder[1][0]=1;
		adder[1][1]=2;
		adder[2][0]=2;
		adder[2][1]=2;
		adder[3][0]=2;
		adder[3][1]=1;
		
		shapes.put("3", Shapes.ShapeFactory.makeShape(adder));
		adder = new int[4][2];
		adder[0][0]=2;
		adder[0][1]=0;
		adder[1][0]=2;
		adder[1][1]=3;
		adder[2][0]=3;
		adder[2][1]=3;
		adder[3][0]=3;
		adder[3][1]=0;
		
		shapes.put("4", Shapes.ShapeFactory.makeShape(adder));

	}
	

}
	
	
	
	
	
	
