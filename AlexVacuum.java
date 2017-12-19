import java.util.ArrayList;

class Tile {
	public static final int UNKNOWN = 0;
	public static final int EMPTY = 1;
	public static final int WALL = 2;
	public static final int HOME = 3;
}

/**
 * @author Alex Strasser
 * @version 1.0.0
 */
public class AlexVacuum extends Vacuum
{
	private ArrayList<ArrayList<Integer>> myboard;
	private ArrayList<Integer> directionsHome;
	private int dirtCollected = 0;
	private int moveNum = 0;
	private int phase = 0;
	private int homePositionX = 0;
	private int homePositionY = 0;
	private int myPositionX = 0;
	private int myPositionY = 0;
	private int xLength = 0;
	private int yLength = 0;
	private int facing = 0;
	
	private int turnOverride;
	
	public AlexVacuum(){
		myboard = new ArrayList<ArrayList<Integer>>();
		myboard.add(new ArrayList<Integer>());
		myboard.get(0).add(Tile.HOME);
		xLength = 1;
		yLength = 1;
	}
	
	/**
	 * Adds a dimension to the myboard array
	 * @param isVertical 
	 * @param isFirst
	 */
	private void addDimension(boolean isVertical, boolean isFirst){
		if(isVertical){
			yLength++;
			ArrayList<Integer> list = new ArrayList<Integer>(xLength);
			for(int i = 0; i < xLength; i++){
				list.add(0);
			}
			if(isFirst){
				myboard.add(0, list);
				homePositionY++;
				myPositionY++;
			}else{
				myboard.add(list);
			}
		}else{
			xLength++;
			for(ArrayList<Integer> row: myboard){
				if(isFirst){
					row.add(0, 0);
				}else{
					row.add(0);
				}
			}
			if(isFirst){
				homePositionX++;
				myPositionX++;
			}
		}
	}
	
	private int getTile(int y, int x){
		if(x < 0){
			addDimension(false, true);
			x++;
		}if (x >= xLength){
			addDimension(false, false);
		}if (y < 0){
			addDimension(true, true);
			y++;
		}if (y >= yLength){
			addDimension(true, false);
		}
		
		return myboard.get(y).get(x);
	}
	
	private int getAdjacent(int direction){ //0 is above, 1 is right, 2 is below, 3 is left
		int tileX = myPositionX;
		int tileY = myPositionY;
		direction %= 4;
		
		if(direction == 0){
			tileY--;
		}else if(direction == 1){
			tileX++;
		}else if(direction == 2){
			tileY++;
		}else if(direction == 3){
			tileX--;
		}
		
		return getTile(tileY, tileX);
	}
	
	private void setAdjacent(int direction, int value){
		int tileX = myPositionX;
		int tileY = myPositionY;
		direction %= 4;
		if(direction == 0){
			tileY--;
		}else if(direction == 1){
			tileX++;
		}else if(direction == 2){
			tileY++;
		}else if(direction == 3){
			tileX--;
		}
		
		if(tileX < 0){
			addDimension(false, true);
			tileX++;
		}if (tileX >= xLength){
			addDimension(false, false);
		}if (tileY < 0){
			addDimension(true, true);
			tileY++;
		}if (tileY >= yLength){
			addDimension(true, false);
		}
		
		myboard.get(tileY).set(tileX, value);
	}
	
	public int countTile(int value){
		int n = 0;
		for(ArrayList<Integer> row: myboard){
			for(int t : row){
				if(t == value)
					n++;
			}
		}
		return n;
	}
	
	public ArrayList<Integer> getDirectionsHome(int x, int y, int facing, int iteration, int maxIterations){
		if(x == homePositionX && y == homePositionY){
			return new ArrayList<Integer>();
		}
		if(iteration > maxIterations){
			return null;
		}
		if(getTile(y, x) == Tile.WALL){
			return null;
		}
		ArrayList<Integer> f = getDirectionsHome(x, y, facing, iteration + 1, maxIterations);
		ArrayList<Integer> l = getDirectionsHome(x, y, facing, iteration + 1, maxIterations);
		ArrayList<Integer> r = getDirectionsHome(x, y, facing, iteration + 1, maxIterations);
		int[] values = {-1, -1, -1};
		if(f != null){
			values[0] = f.size();
		}if(l != null){
			values[1] = l.size();
		}if(r != null){
			values[2] = r.size();
		}
		
		int minIndex = 0;
		for(int i = 1; i < values.length; i++){
			if(values[i] < values[minIndex]){
				minIndex = i;
			}
		}
		
		if(values[minIndex] == -1){
			return null;
		}else if(minIndex == 0){
			f.add(0, 0);
			return f;
		}else if(minIndex == 1){
			l.add(0, 1);
			return l;
		}else {
			r.add(0, 2);
			return r;
		}
	}
	
	@Override
	public void move(){
		if(facing == 0){
			myPositionY--;
		}else if(facing == 1){
			myPositionX++;
		}else if(facing == 2){
			myPositionY++;
		}else if(facing == 3){
			myPositionX--;
		}
		super.move();
	}
	
	@Override
	public void turnRight(){
		facing++;
		facing%=4;
		super.turnRight();
	}
	
	@Override
	public void turnLeft(){
		facing += 4 - 1;
		facing %= 4;
		super.turnLeft();
	}
	
	public boolean turnToFace(int direction){
		if(facing == direction)
			return false;
		if((facing + 1) % 4 == direction){
			turnRight();
		}else if((facing - 1 + 4) % 4 == direction){
			turnLeft();
		}else{
			turnRight();
			turnRight();
		}
		return true;
	}
	
	public boolean faceTowardsHome(){
		System.out.println("FACING: "+facing);
		int xOffset = homePositionX - myPositionX;
		int yOffset = homePositionY - myPositionY;
		if(Math.abs(xOffset) > Math.abs(yOffset)){
			if(xOffset < 0){
				return turnToFace(1);
			}else {
				return turnToFace(3);
			}
		}else {
			if(yOffset < 0){
				return turnToFace(2);
			}else {
				return turnToFace(0);
			}
		}
	}

	@Override
	public void act()
	{
		moveNum++;
		turnOn();
		if(isDirt())
			dirtCollected++;
		if(phase == 0){
			if(isBump()){
				setAdjacent(facing, Tile.WALL);
				if(getAdjacent(facing + 1) == Tile.WALL){ //If there is a wall to the right
					if(getAdjacent(facing - 1) == Tile.WALL) { //If there is also a wall to the left
						turnRight();
						turnRight();
						move();
						turnLeft();
					}else { //If there is a wall to the right and not a wall to the left
						turnLeft();
					}
				}
				else{ // If there is not a wall to the right
					if(getAdjacent(facing - 1) == Tile.WALL){
						turnRight();
					}else { //If there is not a wall on either side, prefer to turn towards unknown, and fall back on randomness
						if(getAdjacent(facing - 1) == Tile.UNKNOWN){
							turnLeft();
						}else if(getAdjacent(facing + 1) == Tile.UNKNOWN){
							turnRight();
						}else{
							if(Math.random() < 0.5){
								turnRight();
							}else{
								turnLeft();
							}
						}
					}
				}
			}else{
				setAdjacent(facing, Tile.EMPTY);
				move();
			}
			System.out.println(dirtCollected / (double) moveNum);
			System.out.println((double) countTile(Tile.UNKNOWN) / (xLength * yLength));
			if(moveNum > 150 && (dirtCollected / (double) moveNum < 0.05 || (double) countTile(Tile.UNKNOWN) / (xLength * yLength) < 0.5)){
				phase = 1;
			}
		}else if(phase == 1){
			turnOff();
			/*
            if(isBump()){
				setAdjacent(facing, Tile.WALL);
			}*
            if(turnOverride > 0) turnOverride--;
			if(turnOverride > 0 || !faceTowardsHome()){ //If it didn't already turn recently or turn in this method
				if(getAdjacent(facing) == Tile.WALL){
					turnOverride = 3;
					turnLeft();
				}else{
					if(isBump()){
						setAdjacent(facing, Tile.WALL);
					}else{
						move();
					}
				}
			}
			/
		}
		
		if(phase == 1 && myPositionX == homePositionX && myPositionY == homePositionY){
			turnOff();
		}
		
		System.out.println("CURRENT: "+myPositionX+", "+myPositionY);
		System.out.println("TARGET: "+homePositionX+", "+homePositionY);
		
	}
	
	private void printBoard(){
		for(ArrayList<Integer> row : myboard){
			System.out.print("[");
			for(int spot : row){
				System.out.print(spot);
			}
			System.out.print("]\n");
		}
		System.out.println();
	}
	
	/**
	 * @return The Name of the vacuum.
	 */
	@Override
	public String getName(){
		return "Alex's Vac";
	}

}
