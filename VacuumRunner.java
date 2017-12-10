import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
//import info.gridworld.actor.ActorWorld;


/**
 * Runner class for vacuum world.
 * @author burkhart
 * @version 12.10.17
 */

public class VacuumRunner
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//Make a new vacuum
		Vacuum rando = new RandomVacuum();

		//Create the world
		VacuumWorld world = new VacuumWorld(rando);

		//world.mazeWorld();
		//world.roomWorld();
		//world.openSpace();
		//world.random();



		//Display the world
		world.show();




	}

}
