import java.util.ArrayList;

/**
 * This is a vacuum that moves randomly. It turns off once it can't find
 * dirt.
 * @author burkhart
 * @version 12.10.17
 */
public class DumbVacuum extends Vacuum
{
    private int wallSeen;
    private int wallsChecked;
    private int dirtSeen;
    private int dirtChecked;
    private int xPos;
    private int yPos;
    private int xDir;
    private int yDir;
    private int spiralSize;
    private int pos;
    private int side;
    private boolean right;
    private float lastSeen;
    private ArrayList<Integer> moves;
    /**
     * The only thing a Random Vac remembers is how many turns there have been.
     */
    public DumbVacuum()
    {
        wallSeen = 0;
        wallsChecked = 0;
        dirtSeen = 0;
        dirtChecked = 0;
        xPos = 0;
        yPos = 0;
        xDir = 1;
        yDir = 0;
        spiralSize = 0;
        side = 1;
        pos = 0;
        right = true;
        lastSeen = 0;
        moves = new ArrayList<Integer>();
    }
    /**
     * Do whatever a random vac does.
     */
    @Override
    public void act(){
        turnOn();
        if(isDirt()){
            dirtSeen+= 1;
            lastSeen = 0;
        }
        if(isBump())
            wallSeen += 1;

        /*If there is a wall, turns right or left randomly.
        Also turns right or left randomly roughly 10% of the
        time even if there is no wall*/
        /*
        if(isBump() || Math.random() < .1)
        {
            if(Math.random() > .5)
                tLeft();
            else
                tRight();
        }
        else
        {
            mForward();
        }
        */
        /*
        if(Math.random()<0.05){
            if(Math.random() > .5)
                tLeft();
            else
                tRight();
        }
        if(isBump()){
            tRight();
            if(isBump()){
                tRight();
                tRight();
            }
            if(isBump()){
                tLeft();
            }
        }
        */
        if(Math.random() < lastSeen/100){
            if(Math.random() > .5)
                tLeft();
            else
                tRight();
        }
        if(isBump()){
            escape();
        }
        mForward();
        if(lastSeen>100){
            goHome();
        }
    }
    private void turn(){
        if(right)
            tRight();
        else
            tLeft();
    }
    private void aTurn(){
        if(!right)
            tRight();
        else
            tLeft();
    }
    private void tLeft(){
        lastSeen = lastSeen + 1;
        moves.add(-1);
        wallsChecked += 1;
        if(xDir != 0){
            yDir = xDir;
            xDir = 0;
        }
        else if(yDir != 0){
            xDir = -yDir;
            yDir = 0;
        }
        turnLeft();
    }
    private void tRight(){
        lastSeen = lastSeen + 1;
        moves.add(1);
        wallsChecked += 1;
        if(xDir != 0){
            yDir = -xDir;
            xDir = 0;
        }
        else if(yDir != 0){
            xDir = yDir;
            yDir = 0;
        }
        turnRight();
    }
    private void goHome(){
        if(moves.size()<998){
            flip();
            while(moves.size()>0){
                int last = moves.size()-1;
                int lastMove = moves.remove(last);
                if(lastMove == 0)
                    move();
                if(lastMove == 1){
                    turnLeft();
                }
                if(lastMove == -1){
                    turnRight();
                }
            }
        }
        turnOff();
    }
    private void flip(){
        tRight();
        tRight();
    }
    private void escape(){
        //Try to move right and just go down a column
        turn();
        if(!isBump()){
            mForward();
            turn();
            if(isBump()){
                flip();
                if(! isBump()){
                    right = !right;
                }       
            }
        }
        else{
            flip();
            right = ! right;
            if(!isBump()){
                mForward();
                aTurn();
                if(isBump()){
                    flip();
                    if(! isBump()){
                        right = !right;
                    }       
                }
            }
            else{
                aTurn();
            }
        }
    }
    private void findTrue(){
        aTurn();
        if(isBump()){
            flip();
        }
        
    }
    private void mForward(){
        lastSeen = lastSeen + 1;
        moves.add(0);
        xPos += xDir;
        yPos += yDir;
        move();
    }
    /**
     * @return The Name of the vacuum.
     */
    @Override
    public String getName()
    {
        return "Dumb fVac";
    }

}
