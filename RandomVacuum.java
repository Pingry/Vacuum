/**
 * This is a vacuum that moves randomly. It turns off once it can't find
 * dirt.
 * @author burkhart
 * @version 12.10.17
 */
public class RandomVacuum extends Vacuum
{

	private int hunger;


	/**
	 * The only thing a Random Vac remembers is how many turns there have been.
	 */
	public RandomVacuum()
	{
		hunger = 50;

	}

	/**
	 * Do whatever a random vac does.
	 */
	@Override
	public void act()
	{
		turnOn();

		if(isDirt())
			hunger += 10;

		if(Math.random() < .1)
		{
			(Math.random() < .5) ? turnRight(): turnLeft();
		}

		if(isBump() || Math.random() < .1)
		{
			if(Math.random() > .5)
				turnLeft();
			else
				turnRight();
		}
		else
		{
			move();
		}

		if(numTurns > 500)
			turnOff();

		hunger--;
		if(hunger <= 0)
			turnOff();
	}

	/**
	 * @return The Name of the vacuum.
	 */
	@Override
	public String getName()
	{
		return "Random Vac";
	}

}
