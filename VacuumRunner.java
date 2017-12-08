/**
 * Runner class for vacuum world.
 * @author burkhart
 * @version 0.2
 */

public class VacuumRunner 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//Make a new vacuum
		Vacuum rando = new Smart();
		
		//Create the world
		VacuumWorld world = new VacuumWorld(rando, 15, 15);
		
		//world.mazeWorld();
		//world.roomWorld();
		world.openSpace();
		//world.random();
		
		
		//Display the world
		world.show();
		
	
		
		
	}

}
