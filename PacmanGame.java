import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import usefulstuff.*;

public class PacmanGame
{
    public static void main (String[] args)
    {
	JFrame j = new JFrame ("Pacman");
	j.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	j.getContentPane ().add (new PacmanGamePanel (), BorderLayout.CENTER);
	j.setSize (500, 500);
	j.setVisible (true);
    }
}

class PacmanGamePanel extends JPanel implements KeyListener
{
    UFO u;              //use ufo as pacman
    Grid grid;          //where pacman can move
    int pmRow, pmCol;   //where pacman is located
    int[] [] board;     //where walls are
    int score;          //score earned from eating pellets
    
    Sound beep;

    public PacmanGamePanel ()
    {
	setBackground (Color.black);
	
	beep = new Sound ("sound/beep.au");

	score = 0;

	u = new UFO ();             //set up ufo pacman
	u.setPosition (200, 250);
	u.setColor (Color.green);
	u.setSize (40);

	grid = new Grid ();         //set up grid for game
	grid.setDimensions (9, 9);
	grid.setFillColor (Color.black);
	grid.setLineColor (Color.blue);
	grid.setPosition (35, 25);

	pmRow = 5;              //start pacman in row 5
	pmCol = 3;              //start pacman in column 3

	u.setPosition (grid.getCellX (pmCol), grid.getCellY (pmRow));

	board = new int [11] [11];      //set up game board object

	board [1] [1] = 1;              // 1 = wall (row then column)
	board [1] [2] = 1;
	board [1] [3] = 1;
	board [1] [4] = 1;
	board [1] [5] = 1;
	board [1] [6] = 1;
	board [1] [7] = 1;
	board [1] [8] = 1;
	board [1] [9] = 1;
	board [9] [1] = 1;
	board [9] [2] = 1;
	board [9] [3] = 1;
	board [9] [4] = 1;
	board [9] [5] = 1;
	board [9] [6] = 1;
	board [9] [7] = 1;
	board [9] [8] = 1;
	board [9] [9] = 1;
	board [8] [9] = 1;
	board [7] [9] = 1;
	board [6] [9] = 1;
	board [4] [9] = 1;
	board [3] [9] = 1;
	board [2] [9] = 1;
	board [0] [9] = 1;
	board [2] [1] = 1;
	board [3] [1] = 1;
	board [4] [1] = 1;
	board [6] [1] = 1;
	board [7] [1] = 1;
	board [8] [1] = 1;
	board [9] [1] = 1;
	board [5] [5] = 1;
	board [3] [3] = 1;
	board [4] [3] = 1;
	board [4] [4] = 1;
	board [4] [5] = 1;
	board [2] [5] = 1;
	board [3] [7] = 1;
	board [4] [7] = 1;
	board [6] [5] = 1;
	board [7] [3] = 1;
	board [6] [3] = 1;
	board [8] [5] = 1;
	board [6] [7] = 1;
	board [6] [6] = 1;
	board [7] [7] = 1;

	board [2] [2] = 3;
	board [8] [8] = 3;

	board [5] [1] = 4;
	board [5] [9] = 4;





	addKeyListener (this);
    }


    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	grid.draw (g);

	for (int r = 1 ; r <= 9 ; r = r + 1) //for each row
	{
	    for (int c = 1 ; c <= 9 ; c = c + 1) //for each column
	    {
		if (board [r] [c] == 1) //if board has one draw a wall
		{
		    g.setColor (Color.pink);
		    g.fillRect (grid.getCellX (c), grid.getCellY (r), 40, 40);
		}

		if (board [r] [c] == 0) //if board has a zero draw a pellet
		{
		    g.setColor (Color.white);
		    g.fillOval (grid.getCellX (c) + 12, grid.getCellY (r) + 12, 15, 15);
		}
		if (board [r] [c] == 3) //if board has three draw a power up
		{
		    g.setColor (Color.red);
		    g.fillOval (grid.getCellX (c) + 10, grid.getCellY (r) + 10, 20, 20);
		}
		if (board [r] [c] == 4) //if board has a four teleport
		{
		    g.setColor (Color.red);
		    g.fillRect (grid.getCellX (c), grid.getCellY (r), 40, 40);

		}
		//  if board has a
	    }

	}



	u.draw (g);

	g.setColor (Color.white);
	g.drawString ("Score: " + score, 40, 20);

	requestFocus ();
    }


    // To fulfill our obligations as a KeyListener, we implement the following...
    public void keyPressed (KeyEvent e)
    {
	if (e.getKeyChar () == 'd') // right
	{
	    if (board [pmRow] [pmCol] == 4 && pmCol == 9)
		pmCol = 1;

	    else if (board [pmRow] [pmCol + 1] != 1)  //check if no wall on right
	    {
		pmCol = pmCol + 1;

	    }
	}
	else if (e.getKeyChar () == 'a') // left
	{
	    if (board [pmRow] [pmCol] == 4 && pmCol == 1)
		pmCol = 10;
		
	    if (board [pmRow] [pmCol - 1] != 1)
	    {
		pmCol = pmCol - 1;
	    }
	}
	else if (e.getKeyChar () == 'w') // up
	{
	    if (board [pmRow - 1] [pmCol] != 1)
	    {
		pmRow = pmRow - 1;
	    }
	}
	else if (e.getKeyChar () == 's') // down
	{
	    if (board [pmRow + 1] [pmCol] != 1)
	    {
		pmRow = pmRow + 1;
	    }
	}
	u.setPosition (grid.getCellX (pmCol), grid.getCellY (pmRow));
	repaint ();

	if (board [pmRow] [pmCol] == 0)     //if pellet in cell
	{
	    board [pmRow] [pmCol] = 2;      //eat pellet
	    score = score + 1;              //add score
	    beep.play();                    //play beep sound
	    //System.out.println("Score = " + score);

	}
	if (board [pmRow] [pmCol] == 3)     //if power up in cell
	{
	    board [pmRow] [pmCol] = 2;      //eat power up
	    score = score + 3;              //add score


	}
    }


    public void keyReleased (KeyEvent e)
    {

    }


    public void keyTyped (KeyEvent e)
    {

    }
}


