import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.actor.ActorWorld;
import java.util.Random;
import java.util.ArrayList;

/**
 * This is a world for running a vacuum.
 *
 * Do not edit any of this code!
 *
 * @author burkhart
 * @version 12.11.17
 */
public class VacuumWorld extends ActorWorld
{

	//All VacuumWorlds must have a vacuum.
	private Vacuum vac;


	/**
	 * Creates a default-sized VacuumWorld.
	 *
	 * @param vacuum Vacuum to clean the world.
	 */
	public VacuumWorld(Vacuum vacuum)
	{
		vac = vacuum;
	}

	/**
	 * Adds an actor to the appropriate place in the World.
	 *
	 * @param row Row to add the actor at.
	 * @param col Column to add the actor at.
	 * @param a Actor to add to the world.
	 */
	public void add(int row, int col, Actor a)
	{
		Location loc = new Location(row, col);
		super.add(loc, a);
	}

	/**
	 * Actually places the vacuum in the world.
	 * @param row Starting row for vacuum
	 * @param col Starting col for vacuum
	 */
	public void placeVac()
	{
		add(getRandomEmptyLocation(), vac);
	}

	public void placeVac(int row, int col)
	{
		add(new Location(row, col), vac);
	}


	/**
	 * Consumes any click on the world so it does nothing.
	 */
	public boolean locationClicked(Location loc)
	{
		return true;
	}

	//setGrid(new BoundedGrid<Actor>(height, width));

	public static BoundedGrid<Actor> createRandomGrid()
	{
		Random rand = new Random();

		//Max and min for possible world dimensions
		int max = 150;
		int min = 5;

		//Randomly decide on width and height of World
		int height = rand.nextInt((max - min + 1)) + min;
		int width = rand.nextInt((max - min + 1)) + min;

		//Grid to return
		BoundedGrid<Actor> grid = new BoundedGrid<Actor>(height, width);

		//Chance of a given cell being a wall or dirt.
		//Chance of wall between 5 and 15%
		double wallChance = .05 + (Math.random() / 10);
		//Chance of dirt between 10 and 20%
		double dirtChance = .1 + (Math.random() / 10);

		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				float randomPercentage = rand.nextFloat();
				//Place wall or dirt randomly, according to percentages defined above,
				//but only if the vacuum isn't there.
				if(randomPercentage < wallChance)
				{
					new Wall().putSelfInGrid(grid, new Location(row, col));
					//add(row, col, new Wall());
				}
				else if(randomPercentage < wallChance + dirtChance)
				{
					new Dirt().putSelfInGrid(grid, new Location(row, col));
					//add(row, col, new Dirt());
				}
			}
		}
		return grid;
	}

	public static BoundedGrid<Actor> gridCopy(BoundedGrid<Actor> other)
	{
			BoundedGrid<Actor> grid = new BoundedGrid<Actor>(other.getNumRows(), other.getNumCols());
			ArrayList<Location> locs = other.getOccupiedLocations();
			for(Location loc : locs)
			{
				Actor actor = other.get(loc);
				if(actor instanceof Wall)
				{
					grid.put(loc, new Wall());
				}
				else if(actor instanceof Dirt)
				{
					grid.put(loc, new Dirt());
				}
			}
			return grid;
	}

	/*
	Commented out. To be finished later for determining how much dirt
	is around a given spot.
	private int numDirt(Location loc)
	{


	}*/

	/**
	 * On a single iteration of the world, post the score.
	 */
	public void step()
	{
		setMessage(vac.getName() + ": " + vac.getScore());
		super.step();

	}

//*************************************************************
//These methods are leftovers from old iterations of the vaccum
//project.
//*************************************************************

	/**
	 * Adds dirt randomly to the world.
	 * @param howMany How many pieces of dirt to add.
	 */
	public void addRandomDirt(int howMany)
	{

		Grid<Actor> gr = getGrid();
		for(int i = 0; i < howMany; i++)
		{
			int row = (int) (Math.random() * gr.getNumRows());
			int col = (int) (Math.random() * gr.getNumCols());
			if(gr.get(new Location(row, col)) == null)
			{
				add(row, col, new Dirt());
			}
			else
			{
				i--;
			}
		}
	}

	/**
	 * Adds walls randomly in the world. Note - This could inadvertently
	 * "trap" an area and make it inaccessible.
	 * @param howMany How many walls to add.
	 */
	public void addRandomWalls(int howMany)
	{
		Grid<Actor> gr = getGrid();
		for(int i = 0; i < howMany; i++)
		{
			int row = (int) (Math.random() * gr.getNumRows());
			int col = (int) (Math.random() * gr.getNumCols());
			if(gr.get(new Location(row, col)) == null)
			{
				add(row, col, new Wall());
			}
			else
			{
				i--;
			}
		}
	}

	/**
	 * Creates a maze-like world.
	 */
	public void mazeWorld()
	{
		setGrid(new BoundedGrid<Actor>(17, 10));
		placeVac(3, 0);
		for(int row = 0; row < 15; row++)
		{
			add(row, 1, new Wall());
		}
		for(int row = 16; row > 1; row--)
		{
			add(row, 3, new Wall());
		}
		for(int row = 0; row < 14; row++)
		{
			add(row, 6, new Wall());
		}
		for(int row = 16; row > 0; row--)
		{
			add(row, 8, new Wall());
		}

		add(0, 0, new Dirt());
		add(9, 0, new Dirt());
		add(13, 0, new Dirt());
		add(16, 1, new Dirt());
		add(16, 2, new Dirt());
		add(8, 2, new Dirt());
		add(0, 2, new Dirt());
		add(1, 4, new Dirt());
		add(1, 5, new Dirt());
		add(8, 4, new Dirt());
		add(13, 4, new Dirt());
		add(15, 7, new Dirt());
		add(13, 7, new Dirt());
		add(10, 7, new Dirt());
		add(7, 7, new Dirt());
		add(2, 7, new Dirt());
		add(1, 9, new Dirt());
		add(5, 9, new Dirt());
		add(9, 9, new Dirt());
		add(16, 9, new Dirt());


	}

	public void roomWorld()
	{
		setGrid(new BoundedGrid<Actor>(15, 15));
		placeVac(8,5);
		for(int row = 0; row < 15; row++)
		{
			if(row != 2 && row != 11)
				add(row, 7, new Wall());
		}

		for(int col = 7; col < 13; col++)
		{
			add(8, col, new Wall());
		}
		add(6, 0, new Wall());
		add(6, 1, new Wall());
		add(6, 2, new Wall());
		add(6, 3, new Wall());
		add(6, 6, new Wall());

		add(2, 0, new Dirt());
		add(0, 4, new Dirt());
		add(4, 5, new Dirt());
		add(6, 4, new Dirt());
		add(8, 0, new Dirt());
		add(10, 2, new Dirt());
		add(13, 1, new Dirt());
		add(13, 2, new Dirt());
		add(14, 5, new Dirt());
		add(2, 7, new Dirt());
		add(3, 9, new Dirt());
		add(1, 12, new Dirt());
		add(0, 14, new Dirt());
		add(7, 8, new Dirt());
		add(8, 13, new Dirt());
		add(8, 14, new Dirt());
		add(9, 8, new Dirt());
		add(11, 11, new Dirt());
		add(13, 9, new Dirt());
		add(14, 13, new Dirt());
	}


	public void openSpace()
	{
		setGrid(new BoundedGrid<Actor>(20, 20));
		placeVac(9, 15);

		add(3, 2, new Wall());
		add(19, 7, new Wall());
		add(15, 15, new Wall());

		add(2, 0, new Dirt());
		add(0, 4, new Dirt());
		add(4, 5, new Dirt());
		add(6, 4, new Dirt());
		add(8, 0, new Dirt());
		add(10, 2, new Dirt());
		add(13, 1, new Dirt());
		add(13, 2, new Dirt());
		add(14, 5, new Dirt());
		add(2, 7, new Dirt());
		add(3, 9, new Dirt());
		add(1, 12, new Dirt());
		add(0, 14, new Dirt());
		add(7, 8, new Dirt());
		add(18, 13, new Dirt());
		add(8, 18, new Dirt());
		add(9, 2, new Dirt());
		add(11, 11, new Dirt());
		add(13, 19, new Dirt());
		add(14, 13, new Dirt());
	}

	public void random()
	{
		setGrid(new BoundedGrid<Actor>(9, 16));
		placeVac(3, 12);

		add(4, 0, new Wall());
		add(6, 14, new Wall());
		add(8, 15, new Wall());
		add(1, 11, new Wall());
		add(0, 10, new Wall());
		add(2, 4, new Wall());
		add(6, 8, new Wall());
		add(4, 12, new Wall());
		add(8, 0, new Wall());
		add(3, 15, new Wall());
		add(5, 2, new Wall());
		add(3, 5, new Wall());
		add(6, 6, new Wall());
		add(8, 5, new Wall());
		add(7, 8, new Wall());
		add(2, 3, new Wall());
		add(1, 2, new Wall());
		add(4, 13, new Wall());
		add(3, 9, new Wall());
		add(4, 6, new Wall());


		add(2, 0, new Dirt());
		add(0, 4, new Dirt());
		add(4, 5, new Dirt());
		add(6, 4, new Dirt());
		add(1, 15, new Dirt());
		add(7, 2, new Dirt());
		add(1, 1, new Dirt());
		add(2, 2, new Dirt());
		add(5, 5, new Dirt());
		add(2, 7, new Dirt());
		add(1, 9, new Dirt());
		add(1, 12, new Dirt());
		add(0, 14, new Dirt());
		add(2, 8, new Dirt());
		add(8, 13, new Dirt());
		add(8, 14, new Dirt());
		add(5, 8, new Dirt());
		add(3, 11, new Dirt());
		add(8, 9, new Dirt());
		add(6, 13, new Dirt());
	}




}
