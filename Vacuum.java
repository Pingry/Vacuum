import info.gridworld.actor.*;
import javax.swing.JOptionPane;
import info.gridworld.grid.*;

/**
 * Represents a vacuum in vacuum world. You should extend
 * this class to make an actual vacuum.
 *
 * DO NOT EDIT THIS CODE!
 * @author burkhart
 * @version 12.10.17
 */
public abstract class Vacuum extends Actor
{
	 private int numTurns;

	    private int points;

	    private Location home;

	    private boolean on;

	    private boolean onBefore;

			private boolean isDirt;


	    private final String[] methods = {"getLocation","getGrid","getDirection","setDirection","putSelfInGrid","moveTo"};

	    /**
	     * Constructs a new Vacuum that is off.
	     */
	    public Vacuum()
	    {
	      setColor(null);
	    	numTurns = 0;
	      points = 0;
	      on = false;
	      onBefore = false;
				isDirt = false;
	    }

			/**
			* @return Whether or not the vacuum cleaned dirt by making its
			* last move. Because GridWorld does not allow multiple actors to
			* inhabit the same location.
			*/
			public boolean isDirt()
			{
				return isDirt;

			}

	    /**
	     * @return Whether or not this vacuum is at it's starting location.
	     */

	    public boolean isHome()
	    {

	        return getLocation().equals(home);

	    }


	     /**
	     * @return True if there is an impediment in front of the vacuum, false otherwise.
	     */
	    public boolean isBump()
	    {

	    	Grid<Actor> grid = getGrid();
	    	Location ahead = getLocation().getAdjacentLocation(getDirection());
	        if(!grid.isValid(ahead))
	        {
	        	return true;

	        }
	        Actor wall = grid.get(ahead);
	        if(wall instanceof Wall)
	        {
	        	return true;
	        }
	        return false;

	    }

	     /**
	     * Turns towards the right.
	     */
	    public void turnRight()
	    {
	        if(!on) { return; }

	        setDirection(getDirection() + Location.RIGHT);

	        points--;
	        numTurns++;
	    }

	     /**
	     * Turns towards the left.
	     */
	    public void turnLeft()
	    {
	    	if(!on) { return; }

	        setDirection(getDirection() + Location.LEFT);

	        points--;
	        numTurns++;
	    }

	     /**
	     * Move one cell forward in the current direction.
	     */
	    public void move()
	    {
	        if(!on) { return; }

	        if (isBump())
	        {
	            return;
	        }

	        Actor dirt = getGrid().get(getLocation().getAdjacentLocation(getDirection()));
	        if(dirt instanceof Dirt)
	        {
	        	points += 100;
						isDirt = true;
	        }
					else
					{
						isDirt = false;
					}

	        moveTo(getLocation().getAdjacentLocation(getDirection()));
	        points--;
	        numTurns++;
	    }

	    /**
	     * Turns the vacuum off. 1000 point penalty if the vacuum turns off not
			 * at home.
	     */
	    public void turnOff()
	    {
	        if(!on) { return; }

	        if(!isHome())
	            points -= 1000;

	        numTurns++;
	        on = false;
	    }
			/**
			* Initializes the vaccum by setting its on variable to true and
			* setting the home location. Doing this in the constructor is dangerous
			* because sometimes the Vacuum is not yet actually places in the world
			* - and thus has no location.
			*/
	    public void turnOn()
	    {
	    	if(on)
	    		return;

	    	if(onBefore)
	    		return;
	    	else
	    		onBefore = true;

	    	home = getLocation();
	    	on = true;
	    }


	    /**
	     * @return The number of points this vacuum has earned.
	     */
	    public int getScore()
	    {
	        if(numTurns > 1000)
	            return points - 1000;
	        return points;

	    }


	    /**
	     * Act - do whatever the Vacuum wants to do. This method is called whenever
	     * the 'Step' or 'Run' button gets pressed in the environment. 'Step' calls it once,
	     * while 'Run' will continue to call it.
	     */
	    @Override
	    public void act() {}

	    /**
	     *
	     * @return The name of the vacuum.
	     */
	    public abstract String getName();


	    public Location getLocation(){
	    	DENIED();
	    	return super.getLocation();
	    }
	    public int getDirection(){
	    	DENIED();
	    	return super.getDirection();
	    }
	    public Grid<Actor> getGrid(){
	    	DENIED();
	    	return super.getGrid();
	    }
	    public void setDirection(int newDirection){
	    	DENIED();
	    	super.setDirection(newDirection);
	    }
	    public void putSelfInGrid(Grid<Actor> gr, Location loc){
	    	DENIED();
	    	super.putSelfInGrid(gr,loc);
	    }
	    public void moveTo(Location newLocation){
	    	DENIED();
	    	super.moveTo(newLocation);
	    }
	    private void DENIED(){
	    	if(checkCheating()){
	    		// what happens if someone cheats - just put it in here.
	    		JOptionPane.showMessageDialog(null, "Cheater!", "You Cheat", JOptionPane.WARNING_MESSAGE);
	    	}
	    }
	    /**
	     * Checks if the programmers implementation of the code is cheating.
	     * @author kunalnabar
	     * @return true if the programmer has cheated.
	     */
	    private boolean checkCheating(){
	    	Throwable t = new Throwable();
	    	StackTraceElement[] elements = t.getStackTrace();
	    	for(int i = 0; i < elements.length; i++){
	    		StackTraceElement e = elements[i];
	    		String methodName = e.getMethodName();
	    		for(String bannedMethod : methods)
	    			// If the method was called.  Checks the next stack element to see if the method was called by a valid class, or the subclass of Vacuum.
	    			if(bannedMethod.equals(methodName) && elements[i+1].getFileName().equals(this.getClass().getName() + ".java"))
	    					return true;
	    	}
	    	return false;
	    }


}
