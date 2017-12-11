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

		/*If there is a wall, turns right or left randomly.
		Also turns right or left randomly roughly 10% of the
		time even if there is no wall*/
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

		if(isDirt())
		{
			hunger += 50;
		}

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
