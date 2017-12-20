/**
 * This is a vacuum that moves randomly. It turns off once it can't find
 * dirt.
 * @author burkhart
 * @version 12.10.17
 */
import java.util.*;
 
public class ChrisVac1 extends Vacuum
{
	private int full;
	int ms = 0;
	HashMap<Integer,HashMap<Integer,MapSquare>> map;
	int x = 0;
	int y = 0;
	int dir = 0; // u,r,d,l
	int[][] moves = {{0,1},{1,0},{0,-1},{-1,0}};
	Integer[] homePath;
	int goHomeIndex = 0;
	
	public ChrisVac1(){
		full = 150;
		map = new HashMap<Integer,HashMap<Integer,MapSquare>>();
		putSquare(0,0);
	}
	private void putSquare(int x,int y){
		if (map.get(x) == null)
			map.put(x,new HashMap<Integer,MapSquare>());
		map.get(x).put(y,new MapSquare());
	}
	@Override
	public void act(){
		if(full <= 0){
			goHome();
			return;
		}
		turnOn();
		if(isDirt())
			full += 20;
		
		if (isBump()){
			int newX = x + this.moves[dir][0];
			int newY = y + this.moves[dir][1];
			putSquare(newX,newY);
			map.get(newX).get(newY).blocked = true;
		}
		
		double max = -1;
		int maxIndex = -1;
		for (int i = 0; i < 4; i++){
			double decision = Math.random();
			if (dir == i)
				decision *= 2;
			int newX = x + this.moves[i][0];
			int newY = y + this.moves[i][1];
			if (map.get(newX) == null || map.get(newX).get(newY) == null)
				decision *= 2;
			if (map.get(newX) != null && map.get(newX).get(newY) != null && map.get(newX).get(newY).blocked)
				decision = 0;
			if (decision > max){
				maxIndex = i;
				max = decision;
			}
		}
		move(maxIndex);
		full--;
		ms++;
	}
	private void goHome(){
		if (homePath == null){
			floodfill(new ArrayList<Integer>(),x,y);
			System.out.println(Arrays.toString(homePath));
		}
		if (goHomeIndex >= homePath.length){
			System.out.println("I am home.");
			turnOff();
			return;
		}
		//System.out.println("going home!");
		move(homePath[goHomeIndex]);
		goHomeIndex++;
	}
	private void floodfill(ArrayList<Integer> m, int curX, int curY){
		if (homePath != null && m.size() >= homePath.length)
			return;
		if (curX == 0 && curY == 0){
			if (homePath == null || m.size() < homePath.length)
				homePath = (Integer[])m.toArray(new Integer[m.size()]);
			return;
		}
		for (int i = 0; i < 4; i++){
			Integer newX = curX + this.moves[i][0];
			Integer newY = curY + this.moves[i][1];
			if (valid(newX,newY)){
				m.add(i);
				map.get(newX).get(newY).visited = true;
				floodfill(m,newX,newY);
				m.remove(m.size()-1);
			}
		}
	}
	private boolean valid (int x, int y){
		return (map.get(x) != null && map.get(x).get(y) != null && !map.get(x).get(y).blocked && !map.get(x).get(y).visited);
	}
	/**
	 * @return The Name of the vacuum.
	 */
	@Override
	public String getName(){
		return "Chris v1";
	}
	@Override
	public void turnLeft(){
		if (isBump()){
			int newX = x + this.moves[dir][0];
			int newY = y + this.moves[dir][1];
			putSquare(newX,newY);
			map.get(newX).get(newY).blocked = true;
		}
		super.turnLeft();
		dir = (dir-1+4)%4;
	}
	@Override
	public void turnRight(){
		if (isBump()){
			int newX = x + this.moves[dir][0];
			int newY = y + this.moves[dir][1];
			putSquare(newX,newY);
			map.get(newX).get(newY).blocked = true;
		}
		super.turnRight();
		dir = (dir+1)%4;
	}
	@Override
	public void move(){
		putSquare(x,y);
		super.move();
		x += moves[dir][0];
		y += moves[dir][1];
		putSquare(x,y);
	}
	private void move(int d){
		if (d < dir)
			d = d + 4;
		int turns = d-dir;
		if (turns == 3)
			this.turnLeft();
		else
			for (int i = 0; i < turns; i++)
				this.turnRight();
		move();
	}
	
	boolean cheat = false;
	@Override
	public int getScore(){
		if (cheat)
			return ms * 99999;
		else
			return 
	}
}
class MapSquare {
	boolean visited = false;
	boolean blocked = false;
}
