import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Write a description of class william_vacuum here.
 *
 * @author William Fallon
 * @version 12/11/17
 */
public class william_vacuum extends Vacuum
{
    
    private int turns;
    
    private int moves;
    
    private int moveLastSeenDirt;
    
    private int maxScore;
    
    private boolean goingHome;
    
    private boolean lastTurn;
    
    private Location previous;
    
    private ArrayList<Location> locations;
    
    private Location start; //initial location
    
    
    
    public william_vacuum()
    {
        turns = 0;
        moves = 0;
        moveLastSeenDirt = 0;
        maxScore = 0;
        
        goingHome = false;
        lastTurn = false;
        
        locations = new ArrayList<Location>();
        
        
    }
    
    
    //returns my name
    public String getName()
    {
        return "William Fallon";
        
    }
    
    
    public void act()
    {
        
        if(turns == 0){
            turnOn();
            double rand = Math.random();
            if(0 <= rand && rand < .25)
                turnRight();
            else if(.25 <= rand && rand < .5)
                turnLeft();
            else if(.5 <= rand && rand < .75){
                turnLeft();
                turnLeft();
            }
            start = getLocation();
        }
        //turns++;
        
        if(isDirt()){
            moveLastSeenDirt = moves;
        }
        
        if(goingHome){
            System.out.println("going home");
            if(getLocation().equals(start)){
                System.out.println("OFF");
                turnOff();
            }
            else{
                //takeStepTowardsHome();
                takeStepTowardsHome_v2();
                //turnOff();
            }
        }
        else{
            
            if(isBump()){
                if(.5 < Math.random())
                    turnRight();
                else
                    turnLeft();
            }
            else if(moves - moveLastSeenDirt > 7){
                if(lastTurn){
                    turnLeft();
                    turns++;
                    if(isBump()){
                        turnRight();
                        turns++;
                        move();
                        moves++;
                        locations.add(getLocation());
                        turns++;
                        lastTurn = !lastTurn;
                    }
                    else{
                        move();
                        moves++;
                        locations.add(getLocation());
                        turns++;
                        turnLeft();
                        lastTurn = !lastTurn;
                    }
                }
                else{
                    turnRight();
                    turns++;
                    if(isBump()){
                        turnLeft();
                        turns++;
                        move();
                        moves++;
                        locations.add(getLocation());
                        turns++;
                        lastTurn = !lastTurn;
                    }
                    else{
                        move();
                        moves++;
                        locations.add(getLocation());
                        turns++;
                        turnRight();
                        lastTurn = !lastTurn;
                    }
                }
                
            }
            
            else{
                if(doesArrContain(getLocation().getAdjacentLocation(getDirection()))){
                    if(locations.get(locations.size()-1).equals(locations.get(locations.size()-2)) && locations.get(locations.size()-1).equals(locations.get(locations.size()-3)) ){
                        System.out.println("3x repitition");
                        goingHome = true;
                    }
                    
                    else if(.5 < Math.random()){
                        turns++;
                        turnRight();
                        locations.add(getLocation());
                    }
                    else{
                        turns++;
                        turnLeft();
                        locations.add(getLocation());
                    }
                }
                else{
                    move();
                    turns++;
                    moves++;
                    locations.add(getLocation());
                }
                
                
            }
            
            
            
            
        }
        if(moves > 20 && (double) getScore() / (double) maxScore <= .85){
            goingHome = true;
        }
        if(turns > 40 && (double) getScore() / (double) maxScore <= .85){
            goingHome = true;
        }
        if(moves > 20 && moves - moveLastSeenDirt > 17){
            goingHome = true;
        }
        if(turns > 40 && moves - moveLastSeenDirt > 17){
            goingHome = true;
        }
        
        
        previous = getLocation();
        maxScore = getScore() > maxScore ? getScore() : maxScore;
        System.out.println(maxScore);
    }
    
    public boolean doesArrContain(Location loc)
    {
        for (int i = 0; i < locations.size(); i++){
            if(locations.get(i).equals(loc)){
                return true;
            }
        }
        return false;
        
    }
    /*
     public void takeStepTowardsHome_V3()
     {
     
     
     }
     
     */
    public void takeStepTowardsHome()
    {
        System.out.println(getDirection());
        if(getLocation().getRow() != start.getRow()){
            if(getLocation().getRow() < start.getRow()){
                while(getDirection() != 0){
                    turnRight();
                }
                while(!isBump()){
                    turnLeft();
                    move();
                    turnRight();
                }
                move();
            }
            else{
                while(getDirection() != 180){
                    turnRight();
                }
                while(!isBump()){
                    turnLeft();
                    move();
                    turnRight();
                }
                
                move();
            }
        }
        else{
            if(getLocation().getCol() < start.getCol()){
                while(getDirection() != 90){
                    turnRight();
                }
                while(!isBump()){
                    turnLeft();
                    move();
                    turnRight();
                }
                move();
            }
            else{
                while(getDirection() != 270){
                    turnRight();
                }
                while(!isBump()){
                    turnLeft();
                    move();
                    turnRight();
                }
                move();
            }
        }
    }
    
    public void takeStepTowardsHome_v2()
    {
        
        ArrayList<Integer> distances = new ArrayList<Integer>();
        
        
        int firstDistance = getManhattanDistance(getLocation().getAdjacentLocation(0).getRow(), getLocation().getAdjacentLocation(0).getCol());
        int secondDistance = getManhattanDistance(getLocation().getAdjacentLocation(90).getRow(), getLocation().getAdjacentLocation(90).getCol());
        int thirdDistance = getManhattanDistance(getLocation().getAdjacentLocation(180).getRow(), getLocation().getAdjacentLocation(180).getCol());
        int fourthDistance = getManhattanDistance(getLocation().getAdjacentLocation(270).getRow(), getLocation().getAdjacentLocation(270).getCol());
        
        if(!(previous.equals(getLocation().getAdjacentLocation(0)))){
            distances.add(firstDistance);
        }
        if(!(previous.equals(getLocation().getAdjacentLocation(90)))){
            distances.add(secondDistance);
        }
        if(!(previous.equals(getLocation().getAdjacentLocation(180)))){
            distances.add(thirdDistance);
        }
        if(!(previous.equals(getLocation().getAdjacentLocation(270)))){
            distances.add(fourthDistance);
        }
        
        
        
        ArrayList<Integer> distances_copy = new ArrayList<Integer>();
        distances_copy.addAll(distances);
        Collections.sort(distances);
        
        
        System.out.println("jeff");
        while(true){
            if(distances.get(0) == firstDistance && distances_copy.get(0) == firstDistance){
                while(getDirection() != 0){
                    turnRight();
                }
                if(isBump()){
                    distances.remove(0);
                    firstDistance = -1;
                }
                else if(previous.equals(getLocation().getAdjacentLocation(getDirection()))){
                    System.out.println("Different Move");
                    distances.remove(0);
                    firstDistance = -1;
                }
                else{
                    System.out.println("string1");
                    move();
                    if(getLocation().equals(previous)){
                        System.out.println("1");
                        turnRight();
                        turnRight();
                        move();
                        distances.remove(0);
                        firstDistance = -1;
                    }
                    else{
                        previous = getLocation();
                        break;
                    }
                    
                }
                
            }
            if(distances.get(0) == secondDistance && distances_copy.get(1) == secondDistance){
                while(getDirection() != 90){
                    turnRight();
                }
                if(isBump()){
                    distances.remove(0);
                    secondDistance = -1;
                }
                else if(previous.equals(getLocation().getAdjacentLocation(getDirection()))){
                    System.out.println("Different Move");
                    distances.remove(0);
                    secondDistance = -1;
                }
                else{
                    System.out.println("string2");
                    move();
                    if(getLocation().equals(previous)){
                        System.out.println("2");
                        turnRight();
                        turnRight();
                        move();
                        distances.remove(0);
                        secondDistance = -1;
                    }
                    else{
                        previous = getLocation();
                        break;
                    }
                    
                }
            }
            if(distances.get(0) == thirdDistance && distances_copy.get(2) == thirdDistance){
                while(getDirection() != 180){
                    turnRight();
                }
                if(isBump()){
                    distances.remove(0);
                    thirdDistance = -1;
                }
                else if(previous.equals(getLocation().getAdjacentLocation(getDirection()))){
                    System.out.println("Different Move");
                    distances.remove(0);
                    thirdDistance = -1;
                }
                else{
                    System.out.println("string3");
                    move();
                    if(getLocation().equals(previous)){
                        System.out.println("3");
                        turnRight();
                        turnRight();
                        move();
                        distances.remove(0);
                        thirdDistance = -1;
                    }
                    else{
                        previous = getLocation();
                        break;
                    }
                    
                }
            }
            if(distances.get(0) == fourthDistance && distances_copy.get(3) == fourthDistance){
                
                while(getDirection() != 270){
                    turnRight();
                }
                if(isBump()){
                    distances.remove(0);
                    fourthDistance = -1;
                }
                else if(previous.equals(getLocation().getAdjacentLocation(getDirection()))){
                    System.out.println("Different Move");
                    distances.remove(0);
                    fourthDistance = -1;
                }
                else{
                    System.out.println("string4");
                    move();
                    if(getLocation().equals(previous)){
                        System.out.println("4");
                        turnRight();
                        turnRight();
                        move();
                        distances.remove(0);
                        fourthDistance = -1;
                    }
                    else{
                        previous = getLocation();
                        break;
                    }
                    
                }
            }
        }
        
        
        
        
    }
    
    public int getManhattanDistance(int x, int y)
    {
        return Math.abs(x - start.getRow()) + Math.abs(y - start.getCol());
    }
    
    
    
}
