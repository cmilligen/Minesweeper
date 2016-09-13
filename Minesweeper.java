import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Minesweeper extends JFrame implements ActionListener{
    private static int width = 8;
    private static int height = 8;
    private int difficulty = 1;
    private Button buttons[][] = new Button[height][width];
    private Mines mineField;
    private static Minesweeper frame;
    private JPanel statBanner; 
    private JPanel gameBoard;
    private JButton resetButton = new JButton(); 
    private JButton timeText = new JButton();
    private int minesLeft = 10;
    private JButton mines = new JButton();
    private JPanel mineGrid = new JPanel(); 
    private javax.swing.Timer timer = new javax.swing.Timer(1000, this); 
    private boolean timerStarted = false;
    private int time = 0;
    private boolean firstClick = false; // used to check if the first click needs to be a zero
    private boolean gameOver;
    public Minesweeper(){

        mineField = new Mines(difficulty);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        // creates action listener for the easy selection which creates a new game passing in easy difficulty // 
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    difficulty = 1;
                    width = 8;
                    height = 8;
                    minesLeft = 10;
                    newGame(difficulty);
                }
            });

        // adds easy to the JMenu file //
        file.add(easy); 

        // creates action listener for the medium selection which creates a new game passing in medium difficulty // 
        JMenuItem medium = new JMenuItem("Medium");
        medium.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    difficulty = 2;
                    width = 16;
                    height = 16;
                    minesLeft = 40;
                    newGame(difficulty);
                }
            });

        // adds medium to the JMenu file //
        file.add(medium);

        // creates action listener for the hard selection which creates a new game passing in hard difficulty // 
        JMenuItem hard = new JMenuItem("Hard");
        hard.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    difficulty = 3;
                    width = 32;
                    height = 16;
                    minesLeft = 99;

                    newGame(difficulty);
                }
            });

        // adds hard to the JMenu file //
        file.add(hard);

        menuBar.add(file);

        setJMenuBar(menuBar);

        // sets resetButton to smiley face //
        resetButton.setIcon(new ImageIcon("face-smile.png"));

        // sets timer Jbutton to 0 //
        timeText.setText( "00" + time );
        mines.setText( "0" + minesLeft);

        // panel for mines to be placed on //
        mineGrid.setLayout(new GridLayout(width, height));

        setupButtons();

        // for game reset //
        resetButton.addActionListener(this);

        // top banner for timer points and face button //
        statBanner = new JPanel();
        JPanel minesLeftBox = new JPanel();
        JPanel timeLeftBox = new JPanel();
        statBanner.setLayout(new GridLayout());
        minesLeftBox.setLayout(new BorderLayout());
        timeLeftBox.setLayout(new BorderLayout());
        minesLeftBox.add(mines, BorderLayout.WEST);
        statBanner.add(minesLeftBox);

        statBanner.add(resetButton);
        timeLeftBox.add(timeText, BorderLayout.EAST);
        statBanner.add(timeLeftBox);

        gameBoard = new JPanel();
        gameBoard.setLayout(new BorderLayout());

        gameBoard.add(mineGrid, BorderLayout.CENTER);
        gameBoard.add(statBanner, BorderLayout.NORTH);

        add(gameBoard);

    }

    // initializes the buttons on the mine board //
    private void setupButtons(){
        firstClick = true;
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                final int k = i;
                final int l = j;
                buttons[i][j] = new Button(mineField);
                buttons[i][j].addMouseListener(new MouseAdapter(){
                        @Override
                        public void mousePressed(MouseEvent e){
                            if(e.getButton() == MouseEvent.BUTTON3){
                                handleMouseClick(false, buttons[k][l], k , l);
                            } else if(e.getButton() == MouseEvent.BUTTON1){
                                handleMouseClick(true, buttons[k][l], k , l);
                            }
                        }
                    });
                buttons[i][j].setState(i , j);

                buttons[i][j].setRolloverIcon(new ImageIcon("0.png"));
                buttons[i][j].setIcon(new ImageIcon("cover.png"));

                mineGrid.add(buttons[i][j]);
                if( buttons[i][j].getState() == -1){ // used to detirmine a win
                    buttons[i][j].setFound(true);
                }
            }
        }

    }

    // resets all values, and re-initializes all class veriables.  takes the game difficulty in so it set the board //
    // size to the current game size //
    public void newGame(int setDifficulty){ // eventually will take in two ints for a new board size
        gameOver = false;
        //resets found array to false //
        switch(difficulty){
            case 1:
            minesLeft = 10;
            break;
            case 2:
            minesLeft = 40;
            break;
            case 3:
            minesLeft = 99;
            break;

        }
        mines.setText("0" + minesLeft);
        time = 0;
        timeText.setText( "00" + time );// game timer

        // sets resetButton to smiley face //
        resetButton.setIcon(new ImageIcon("face-smile.png"));
        timer.stop();
        timerStarted = false;

        // adds buttons to mineField grid and initialize found[][]//
        buttons= new Button[height][width];
        mineField = new Mines(difficulty);
        mineGrid = new JPanel();
        mineGrid.setLayout(new GridLayout(height , width));

        frame.setSize(width * 35,height * 35 + 100);

        // calls the setupButtons() method, which initiallizes all the buttons // 
        setupButtons(); 

        // removes ands then refreshes the JPanel //
        gameBoard.removeAll();
        gameBoard.add(mineGrid , BorderLayout.CENTER);
        gameBoard.add(statBanner, BorderLayout.NORTH);
        gameBoard.revalidate();
        gameBoard.repaint();
        //}
    }

    // main method, gets the game going... super exciting //
    public static void main(String[] args){
        frame = new Minesweeper();

        frame.setTitle("Minesweeper");
        frame.setSize(width * 35,height * 35 + 100);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // used for timer and reset game button //
    public void actionPerformed(ActionEvent event){

        if(event.getSource().equals(timer)){ 
            time++;
            if( time < 10 ){
                timeText.setText( "00" + time );
            }else if( time < 100 ){
                timeText.setText( "0" + time );
            }else{
                timeText.setText( "" + time );
            }
        }else if(event.getSource().equals(resetButton)){
            newGame(difficulty);
        }
    }
    // returns nothing, and takes nothing in, but runs through the buttons checking to see if they have been found //
    // and if they all have, a win happens and the winning face appears //
    public void checkForWin(){
        boolean win = true;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(!buttons[i][j].getFound()){
                    win = false;
                }
            }
        }
        if(win){
            resetButton.setIcon(new ImageIcon("face-win.png"));
            timer.stop();
            timerStarted=false; 
            gameOver = true;
        }
    }
    // takes in an x and y location from the button[][] , and recusively handles a zero click //

    public void handleClick(int i, int j){

        for(int y = -1; y <= 1; y++){
            for(int x = -1; x <= 1; x++){
                if(buttons[i][j].isValid(i + y, j + x)){
                    if(y == 0 && x == 0){

                        if(buttons[i][j].getState() == -1){
                            endGame(buttons[i][j]);
                        } else {
                            buttons[i][j].setIcon(new ImageIcon(buttons[i][j].getState() + ".png"));
                        }
                        buttons[i ][j].setRolloverIcon(null);
                        buttons[i ][j].setClicked(true);
                        buttons[i][j].setFound(true);
                    } else {
                        if(buttons[i+y][j+x].isFlagged()){
                            // does nothing if the button is flagged
                        } else {
                            if(buttons[i+y][j+x].getState() == 0 && !buttons[i+y][j+x].getFound()){
                                handleClick((i+y),(j+x));
                            } else {
                                if(buttons[i+y][j+x].getState() == -1){
                                    endGame(buttons[i+y][j+x]);
                                } else {
                                    buttons[i+y][j+x].setIcon(new ImageIcon(buttons[i+y][j+x].getState() + ".png"));
                                }
                                buttons[i+y][j+x].setRolloverIcon(null);
                                buttons[i+y][j+x].setClicked(true);
                                buttons[i+y][j+x].setFound(true);
                            }
                        }
                    }
                } 
            }

        }

    }

    // used to tell if the button clicked was a left or right click, and then handles what to do with either case //
    private void handleMouseClick(boolean leftClick, Button button, int i, int j){
        if(!gameOver){
            if(!button.isFlagged()){
                if(leftClick){
                    if(firstClick){
                        mineField.firstClick(i, j);
                        resetButtons();
                        firstClick = false;
                    }
                    if(!timerStarted){
                        timer.start();
                    }

                    ImageIcon image = new ImageIcon(mineField.getNeighborMineCount(i,j) + ".png");
                    // prepares board for ending game //
                    if(button.getState() == -1){
                        endGame(button);
                    } else if(button.getState() == 0){
                        handleClick(i, j);

                    } else {
                        if(button.getClicked()){
                            int flagCount = 0;
                            for(int a = -1; a <= 1; a++){
                                for(int b = -1; b <= 1; b++){
                                    if(button.isValid(i + a, j + b)){
                                        if(buttons[i + a][j+b].isFlagged()){
                                            flagCount++;
                                        }
                                    }
                                }
                            }
                            if(button.getState() == flagCount){
                                handleClick(i,j);
                            }
                            //handleClick(i, j);
                        } else {
                            button.setIcon(image);
                            button.setRolloverIcon(null);
                            button.setFound(true);
                            button.setClicked(true);
                        }
                    }

                    checkForWin();
                } else {
                    // if button isnt clicked, then you are able to flag the tile //
                    if (!button.getClicked()){

                        if(minesLeft > 0){
                            button.setFlagged(true);
                            button.setIcon(new ImageIcon("flag.png"));
                            button.setRolloverIcon(null);
                            minesLeft--;
                            if( minesLeft < 10 ){
                                mines.setText("00" + (minesLeft));
                            }else if( minesLeft < 100 ){
                                mines.setText("0" + (minesLeft));
                            }

                        }
                    }
                }
            } else {
                // if its a right click, then you it flags the tile, and lowers the mine count //
                if(!leftClick){
                    button.setFlagged(false);
                    button.setIcon(new ImageIcon("cover.png"));
                    button.setRolloverIcon(new ImageIcon("0.png"));
                    minesLeft++;
                    if( minesLeft < 10 ){
                        mines.setText("00" + (minesLeft));
                    }else if( minesLeft < 100 ){
                        mines.setText("0" + (minesLeft));
                    }
                }

            }
        }
    }

    public void endGame(Button button){
        ImageIcon image = new ImageIcon("mine-red.png");

        for(int y = 0; y < buttons.length; y++){
            for(int x = 0; x < buttons[y].length; x++){
                if(buttons[y][x].getState() == -1){
                    buttons[y][x].setIcon(new ImageIcon("mine-grey.png"));
                    buttons[y][x].setRolloverIcon(null);
                    buttons[y][x].setClicked(true);
                }
                if(buttons[y][x].isFlagged() && buttons[y][x].getState() != -1){
                    buttons[y][x].setIcon(new ImageIcon("mine-misflagged.png"));
                    buttons[y][x].setRolloverIcon(null);
                    buttons[y][x].setClicked(true);

                }
            }
        }
        resetButton.setIcon(new ImageIcon("face-dead.png"));
        button.setIcon(image);
        button.setRolloverIcon(null);
        timer.stop();
        timerStarted = false;
        gameOver = true;

    }

    // used to reset the state of the buttons, when the first click happens looking for a zero // 
    private void resetButtons(){
        for(int y = 0; y < buttons.length; y++){
            for(int x = 0; x < buttons[y].length; x++){
                buttons[y][x].setState(y,x);
                if(buttons[y][x].getState() == -1){
                    buttons[y][x].setFound(true);
                } else {
                    buttons[y][x].setFound(false);
                }
            }
        }
    }
}