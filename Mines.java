public class Mines{
    private int difficulty;
    private int[][] board;
    private int [][] neighborMC;
    private int mineCount;
    private int tempMineCount;
    private int tempI;
    private int tempJ;
    private boolean recheckFirstClick = false;
    public Mines(int difficulty){
        this.difficulty = difficulty;
        switch (difficulty){
            case 1:
            mineCount = 10;
            boardRandomizer(8, 8);
            break;

            case 2:
            mineCount = 40;
            boardRandomizer(16, 16);
            break;

            case 3:
            mineCount = 99;
            boardRandomizer(16, 32);
            break;

        }
    }

    public void boardRandomizer(int i, int j){
        board = new int[i][j];
        tempMineCount = mineCount;

        while(tempMineCount > 0){
            for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board[y].length; x++){
                    int state = (int)(Math.random() * -10);

                    if(board[y][x] != -1 && state == -1 && tempMineCount > 0){
                        board[y][x] = state;
                        tempMineCount--;

                    } else if(board[y][x] == -1) {
                    } else {
                        board[y][x] = 0;
                    }

                }
            }
        }

        neighborMineCount();
        if(recheckFirstClick){
            firstClick(tempI, tempJ);
        }
        
    }

    public void firstClick(int i, int j){
        recheckFirstClick = true;
        tempI = i;
        tempJ = j;
        if(getNeighborMineCount(i , j) != 0){
            boardRandomizer(getBoardY(), getBoardX());
        } else {
            recheckFirstClick = false;
        }
    }

    public int getBoardX(){
        return board[0].length;
    }

    public int getBoardY(){
        return board.length;
    }

    public int getMineCount(){
        return mineCount;
    }

    public boolean isMine(int y, int x){
        if(board[y][x] == -1){
            return true;
        }else {
            return false;
        }
    }

    public void neighborMineCount(){
        // adds up neightboring mine count //
        neighborMC = new int[board.length][board[0].length];
        for(int i =0; i < neighborMC.length; i ++){
            for(int j = 0; j < neighborMC[i].length; j++){
                if(board[i][j] != -1){
                    if(i > 0 && j > 0 && board[i - 1][j -1] == -1){ // top left
                        neighborMC[i][j]++;
                    }
                    if(i > 0  && board[i - 1][j] == -1){ // up
                        neighborMC[i][j]++;
                    }
                    if(i > 0 && j < board[i].length - 1 && board[i - 1][j + 1] == -1){ // top right
                        neighborMC[i][j]++;
                    }
                    if(j > 0 && board[i][j -1] == -1){ //  left
                        neighborMC[i][j]++;
                    }
                    if(j < board[i].length - 1 && board[i][j +1] == -1){ // right
                        neighborMC[i][j]++;
                    }
                    if(i < board.length - 1 && j > 0 && board[i + 1][j -1] == -1){ // bottom left
                        neighborMC[i][j]++;
                    }
                    if(i < board.length - 1  && board[i + 1][j] == -1){ // down
                        neighborMC[i][j]++;
                    }
                    if(i < board.length - 1 && j < board[i].length - 1 && board[i + 1][j + 1] == -1){ // bottom right
                        neighborMC[i][j]++;
                    }
                } else {
                    neighborMC[i][j] = -1;
                }
            }
        }    

    }

    public int getNeighborMineCount(int y, int x){
        return neighborMC[y][x];
    }
    
    public void printMineBoard(){
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board[y].length; x++){
                System.out.print(board[y][x]+ " ");
            }
            System.out.println();
        }
    }

    public void printMineCountBoard(){
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board[y].length; x++){
                System.out.print(neighborMC[y][x]+ " ");
            }
            System.out.println();
        }
    }
    
}