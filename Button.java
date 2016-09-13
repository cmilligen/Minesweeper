import javax.swing.*;
import java.awt.*;

public class Button extends JButton{
    private int state;
    private Mines mines;
    private boolean found;
    private boolean clicked;
    private boolean flagged;
    
    /*
     *  -1 = mine
     *   0 = void
     *   1 = 1.png
     *   2 = 2.png
     *   3 = 3.png
     *   4 = 4.png
     *   5 = 5.png
     *   6 = 6.png
     *   7 = 7.png
     *   8 = 8.png 
     */

    public Button(Mines mines){
        state = 0;
        found = false;
        clicked = false;
        this.mines = mines;
    }

    public void setState( int y, int x){
        state = mines.getNeighborMineCount(y, x);
    }

    public int getState(){
        return state;        
    }

    public void setFound(boolean found){
        this.found = found;
    }

    public boolean getFound(){
        return found;
    }

    public void setClicked(boolean clicked){
        this.clicked = clicked;
    }

    public boolean getClicked(){
        return clicked;
    }
    
    public void setFlagged(boolean flagged){
        this.flagged = flagged;
    }
    
    public boolean isFlagged(){
        return flagged;
    }
   
    public boolean isValid(int i, int j){
        if(i < mines.getBoardY() && i > -1 && j > -1 && j < mines.getBoardX()){
            return true;
        } else {
            return false;
        }
        
    }
    
}
