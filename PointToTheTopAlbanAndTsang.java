
/*Point To The Top Game
  Programmed by Micheline Alban and Jennifer Tsang on May 25th - June 13th, 2018
  This program is a rendition of the game 'Snakes and Ladders' and the user will need to click enter to roll the dice. Depending on the square the user lands on, there may be 'ladders'
  that move their pawn closer to the final square 50 or 'snakes' that move their pawn farther from square 50 (move them back a certain number of squares). Inside the code, these square
  are referred to as "snakes" or "ladders", however, they appear as arrows and are only known to the gamer as "mystery arrows", who will their pawn in an unknown direction. There are
  also chance squares that randomly choose whether they move forward or backwards and trivia squares that allow the user to move forward 3 squares if they answer correctly. They have  8 rolls to get to 50 and if they don't make it, the user loses the game.
*/

import hsa.Console;
import java.awt.*;
public class PointToTheTopAlbanAndTsang
{
    static Console c; //global or class variable

   public static void main (String[] args)  // ================================= M A I N === M E T H O D ================================================================
    {
	c = new Console (38, 175);
	int playGame = 1, numOfRolls = 0; //numOfRolls keeps track of how many rolls the user has used --> they have a max of 8 rolls before they must reach square 50

	titlePageWithInstructions ();//makes the title page

	while (playGame == 1) //outer while loop
	{
	    
	    numOfRolls = 0;//must be redeclared and reset to 0 every time the loop is repeated, which is every time the game restarts
	    int counter = 1; //player starts on square number 1
	    // The counter value stores which square the pawn is on

	    makeBoard (counter); //board of 50 squares and the user's pawn are made for the first time before any dice roll

	    //the board is made with the counter as a parameter so it knows where to draw the pawn

	    while (counter < 50) //inner while loop --> applicable while player is in between squares 1 and 50
	    {
		c.clear();
		numOfRolls++;
		if (numOfRolls > 8)//once the number of rolls reaches 10, the user has lost
		{
		    youLost (); // game over page
		    break;
		}
		c.println ("You are on roll "+numOfRolls+" out of 8.");
		delay (1500);
		c.clear();
		counter = rollTheDice (counter); //calls the method to roll the dice, and a new value will be put into the counter
		makeBoard (counter); //board and pawn are drawn with the new value of the counter after the dice roll
		
		//==========checks for CHANCE SQUARES which are on board squares that are multiples of 13=============
		if (counter % 13 == 0)
		{
		    counter = chanceSquares (counter); //calls chance square method to adjust counter according to the square
		    makeBoard (counter); //will redraw the board after the square has changed the counter and therefore the pawn position
		}

		//==========checks for TRIVIA SQUARES which are on board squares that are multiples of 9=============
		else if (counter % 9 == 0)
		{
		    counter = triviaSquares (counter); //calls trivia square method to adjust counter according to the square
		    makeBoard (counter);
		}

		//==========checks for ARROWS POINTING TOWARDS ONE (CALLED SNAKE SQUARES IN THE CODE) which are on all squares that are multiples of 7 except for 42=============
		else if (counter % 7 == 0 && counter != 42)
		{
		    drawArrows (counter); // calls the draw arrow method to draw an arrow from old square to new square on board
		    counter = snakeSquares (counter); //calls snake square method to adjust counter according to the square
		    makeBoard (counter);
		}

		//==========checks for ARROWS POINTING TOWARDS FIFTY (CALLED LADDER SQUARES IN THE CODE) which are on that are multiples of 11=============
		else if (counter % 11 == 0)
		{

		    drawArrows (counter); // calls the draw arrow method to draw an arrow from old square to new square on board
		    counter = ladderSquares (counter); //calls ladder square method to adjust counter according to the square
		    makeBoard (counter);

		}
		
	    } //end of inner while

	    playGame = endGame (); //end game method asks if player wants to play again, adjusting the playGame variable to repeat or exit the outer while loop
	} //end of outer while

	endGameAnimation (); //displays after player has said they do not want to play again

    } //end of main method



    /*This module is for displaying the title page of the game as well as the instructions on how to play the game.*/
public static void titlePageWithInstructions ()
    {
	//Declares, assigns, and creates arrays for the colours of the stars and the background
	Color background = new Color (17, 225, 121);
	Color star1 = new Color (255, 255, 79);
	Color star2 = new Color (189, 126, 158);
	Color star2Outline = new Color (0, 0, 153);
	Color star1Shooting = new Color (225, 190, 17);
	Color star2Shooting = new Color (255, 166, 211);

	//Declares and creates the fonts
	Font gameName = new Font ("Times New Roman", Font.BOLD | Font.ITALIC, 80);
	Font instructions = new Font ("Times New Roman", Font.BOLD, 25);

	//A N I M A T I O N
	//Declares variables related to each star
	int x1 = 10, y1 = 200, xSpeed1 = 2, ySpeed1 = 2, x2 = 100, y2 = 30, xSpeed2 = 2, ySpeed2 = 3;

	//Changes the background color from white to turquoise
	c.setColor (background);
	c.fillRect (10, 10, 1389, 745);

	//animates yellow star
	for (int loop = 0 ; loop < 1288 ; loop++) //so the stars are redrawn multiple times
	{
	    //Draws outline of star
	    c.setColor (Color.red);
	    c.drawStar (x1, y1, 50, 50);

	    //Fills the star
	    c.setColor (star1);
	    c.fillStar (x1, y1, 50, 50);

	    //delays making background star (can only use this delay because the delay method delays too slowly for the animation)
	    for (int m = 0 ; m <= 3 ; m++) //controls the speed of the animation (faster things are erased and re-drawn, the quicker the animation becomes)
	    {
		for (int i = 0 ; i < 100000 ; i++)
		    ;
	    }

	    //Draws 'shooting' part of star (trail star leaves behind)
	    c.setColor (star1Shooting);
	    c.fillStar (x1, y1, 50, 50);

	    //Changes speed of x and y
	    x1 += xSpeed1;
	    y1 += ySpeed1;

	    //Keeps stars within background turquoise rectangle
	    if (x1 > c.maxx () - 60 || x1 < 12)
	    {
		xSpeed1 = -xSpeed1;
	    }
	    if (y1 > c.maxy () - 60 || y1 < 12)
	    {
		ySpeed1 = -ySpeed1;
	    } //end of if
	}

	//Purple star animation
	for (int o = 0 ; o < 3000 ; o++)
	{
	    //draws purple star outline
	    c.setColor (star2Outline);
	    c.drawStar (x2, y2, 50, 50);

	    //draws purple star
	    c.setColor (star2);
	    c.fillStar (x2, y2, 50, 50);

	    //delays making 'shooting' part (animation)
	    for (int m = 0 ; m <= 3 ; m++)
	    {
		for (int i = 0 ; i < 100000 ; i++);
	    }

	    //Draws shooting part of star (replaces the star2 color with a pink color) to show the star moving
	    c.setColor (star2Shooting);
	    c.fillStar (x2, y2, 50, 50);

	    //Changes speed of x2 and y2
	    x2 += xSpeed2;
	    y2 -= ySpeed2;

	    //Keeps stars within background
	    if (x2 > c.maxx () - 60 || x2 < 12)
	    {
		xSpeed2 = -xSpeed2;
	    }
	    if (y2 > c.maxy () - 60 || y2 < 12)
	    {
		ySpeed2 = -ySpeed2;
	    } //end of if
	}

	//Delay making the title so it doesn't seem like too much is happening all at once
	delay (5);

	//Writing the title of game overtop of the stars
	c.setColor (Color.black);
	c.setFont (gameName);
	c.drawString ("Point to the Top", 450, 400);

	//Delays writing the instructions to allow user to read the name of the game
	delay (1000);

	//=================================================== I N S T R U C T I O N S ===================================================

	//Writes 'Instructions'
	c.setColor (Color.black);

	//Clears screen
	c.clear ();
	c.setFont (instructions);
	c.drawString ("Hello! Welcome to Point to the Top !", 475, 375);

	//allows user to read message
	delay (1500);

	//Clears screen to give enough space to display all instructions
	c.clear ();

	//Visibly indicate that the instructions are beginning
	c.drawString ("Instructions", 600, 50);
	c.drawLine (590, 60, 740, 60);
	//allows user to read it
	delay (1500);

	//How to play game w/ delays between allowing the user to read it so it doesn't seem like too much information all at once
c.drawString ("Your pawn will appear on the game board and all you need to do is sit back, relax, and roll the dice!", 100, 100);
	delay (2000);

	c.drawString ("You win once you reach board square number 50! Simple enough, right? So let's make this a little more exciting...", 100, 175);
	delay (2000);

	c.drawString ("There will be some surprises along the way - the board is laden with 3 kinds of special squares:", 100, 250);
	delay (2000);

	c.drawString ("1) Chance squares will let you move forwards or backwards based on your luck.", 100, 300);
	c.drawString ("2) Trivia squares will let you remain at the same spot or move forwards based on whether your answer was correct or not.", 100, 350);
	c.drawString ("3) And finally, Mystery Arrows will point to a random square on the board your pawn will move to. ", 100, 400);
	c.drawString ("    Will it be towards or away from 50, who knows, it's a mystery.", 100, 450);
	delay (9000);

	c.drawString ("If you happen to land on any special square and your pawn is moved or pointed to another special square,", 100, 525);
	c.drawString ("(ex. You land on a chance square that tells you to go back 3 spaces, where you land on a trivia square), the", 100, 575);
	c.drawString ("second special squares actions will not apply.", 100, 625);
	delay (9000);
	
	//Clears screen for continuation of instructions
	c.clear();
	
	c.drawString ("Surprise! There's more! If you can't reach square 50 after 8 rolls, it's game over for you. ):", 100, 100);
	c.drawString ("After every turn, we will keep you updated on the number of tries you have left until you reach 8 rolls.", 100, 175);
	delay (2000);

	//Get user input (enter key) so the program knows to begin the game
	c.drawString ("That's about it from us, click enter when you're ready to Point to the Top!", 100, 250);
	delay (1500);
	c.readChar ();

	//Clears screen after user has entered their input to indicate the game is beginning
	c.clear ();
   
    } //end titlePageWithInstructions method
    /*This module is used to roll 2 dices after each 'turn' is complete (after previous roll finished and it also displayed the board and anything that was supposed to happen during the board). The rolls are numbers
     between 1 - 12. */
    public static int rollTheDice (int squareNum)  //============ ROLLING THE DICE=====================================================================================
    {
	//Clears screen to roll dice
	c.clear ();
	//Variable declaration
	int roll;
	char enter;
	//Getting user input to roll the dice
	c.println ("Press enter to roll the dice.");
	enter = c.readChar ();
	//Clears screen before displaying their roll
	c.clear ();

	//Rolls the dice and displays the roll
	roll = (int) (12 * Math.random ()) + 1;
	c.print ("Yay! You rolled a " + roll + " !");

	//Adds the current square the user was on as well as the number they just rolled to get the new square they will be landing on
	squareNum = roll + squareNum;

	//If the new square they will be landing on is less than 50, it will display the new square the pawn is on
	if (squareNum < 50)
	{
	    c.println ("You're on square " + squareNum);
	}

	else
	{
	    squareNum = 50; //when the new square the pawn should be landing on is over 50, this changes it back to 50 so the pawn will be displayed on the game board
	    c.println ("Congrats! You've reached square 50! Yay!"); //shows the user where the pawn will land on
	}//end of if statement

	//Gets user input to let the user decide when to continue the game
	c.println ("Press enter to continue.");
	enter = c.readChar ();

	//Clears screen so the game board is drawn on a fresh screen
	c.clear ();

	//returns the new square where the pawn must be drawn on the game board
	return squareNum;
    } //end rollTheDice method

/*This module is to make the board, draw the pawn, and draw the legend*/
    public static void makeBoard (int pawnSpace)
    {
	int enter; // will be used to collect input when user is asked to click enter to continue
	Color c1 = new Color (255, 101, 178); //pink
	Color c2 = new Color (150, 200, 250); //blue
	Color mysteryArrows = new Color (17, 225, 121); //green
	Color triviaStars = new Color (205, 92, 92); //red
	Color pawnColor = new Color (239, 235, 106); //yellow
	Color[] checker = new Color [2];
	Font font1 = new Font ("Courier New", Font.BOLD, 30);
	Font font2 = new Font ("Courier New", Font.BOLD, 15);

	//================================ MAKING THE CHECKERBOARD ============================================================================================================================
	for (int rows = 0 ; rows < 5 ; rows++) // 5 rows
	{ //outer for
	    if (rows == 0 || rows % 2 == 0) //every other row (0 and even numbered rows 2 and 4) are assigned the following color order
	    {
		checker [0] = c1;
		checker [1] = c2;
		//
	    }
	    else // odd rows (rows 1 and 3) are assigned the opposite color order as even rows
	    {
		checker [0] = c2;
		checker [1] = c1;
	    }

	    for (int columns = 0 ; columns < 10 ; columns++) //10 columns
	    { //inner for

		if (columns == 0 || columns % 2 == 0) // first square and even numbered squares in the row (made of 10 columns) will have the color in checker one
		{
		    c.setColor (checker [1]);
		}

		else //left over squares in the row will have the color in checker 0, creating an alternating pattern with color checker 1 squares from the if statement above
		{
		    c.setColor (checker [0]);
		}

		//each row will start the alternating pattern on a different color (c1 or c2) using the if statments in the outer for loop, creating

		c.fillRect (119 * columns, 151 * rows, 119, 151); //draws the board square
		//=======printing the numbers on the board=================
		c.setFont (font2);
		c.setColor (Color.gray);
		c.drawString (" " + (columns + 1 + (rows * 10)), 60 + (columns * 119), 141 + 151 * (rows));

	    } //end of inner for
	} //end of outer for

	//=================================================================L E G E N D ===========================================================================
	//will be drawn on the left of the board
	c.setColor (Color.black);
	c.setFont (font1);

	//making legend box and word
	c.drawRect (1215, 238, 180, 270);
	c.drawString ("LEGEND", 1255, 270);
	c.setFont (font2);

	//shows icon for trivia stars
	c.setColor (triviaStars);
	c.fillStar (1220, 290, 30, 30);
	c.drawString ("Trivia Squares", 1257, 310);

	//shows icon for chance stars
	c.setColor (Color.blue);
	c.fillStar (1220, 340, 30, 30);
	c.drawString ("Chance Squares", 1257, 360);

	//shows icon for mystery arrows
	c.setColor (mysteryArrows);
	int[] arrowLegendX = {1225, 1255, 1240};
	int[] arrowLegendY = {390, 390, 380};
	c.fillPolygon (arrowLegendX, arrowLegendY, 3); //draws arrow head
	c.fillRect (1235, 390, 10, 30);  // draws arrow body
	c.drawString ("Mystery Arrows", 1257, 410);

	//shows icon for pawn in legend
	c.setColor (pawnColor);
	int[] pawnLegendX = {1240, 1230, 1250};
	int[] pawnLegendY = {430, 480, 480};
	c.fillOval (1230, 430, 20, 20); //draws pawn head
	c.fillPolygon (pawnLegendX, pawnLegendY, 3); //draws pawn body
	c.drawString ("Your Pawn", 1257, 460);

	//================================================GRAPHICS FOR SPECIAL SQUARES ON THE BOARD ITSELF=========================================================================

	// Trivia Squares --> draws a red star on each trivia square
	c.setColor (triviaStars);
	for (int trivia = 0 ; trivia < 5 ; trivia++)
	{
	    c.fillStar (963 - (trivia * 119), 20 + (trivia * 151), 100, 100);
	    /*squares in multiples of 9, which makes a diagonal pattern on the board, moving one square left and one down for each iteration of the for loop? the starting coordinate is changed with (119x151) square dimensions*/
	}
	//Chance squares--> draws a red star on each trivia square
	c.setColor (Color.blue);
	for (int chance = 0 ; chance < 4 ; chance++)
	{
	    c.fillStar (((chance * 3) * 119) - 110, 21 + (chance * 151), 100, 100);
	    /*squares in multiples of 13, which makes a diagonal pattern moving 3 squares
	    rightandonedownforeachiterationofthforloop*/
	}

	//Mystery Arrows --> draws a green arrow on each arrow square
	int x, y; // x finds which column the arrow is in, y finds the row

	int[] arrowSpot = {7, 11, 14, 21, 22, 28, 33, 35, 44, 49}; // square numbers for mystery arrows
	for (int s = 0 ; s < 10 ; s++)
	{

	    if (arrowSpot [s] % 10 == 0) //if the arrow icon is on a multiple of ten
	    {
		x = 10; // arrow is in the 10th column
	    }
	    else
	    {
		x = arrowSpot [s] % 10; //finds out which column the arrow is in
	    }
	    y = (arrowSpot [s] - x) / 10; // finds out which row the arrow is in

	    int[] arrowHeadY = {20 + (y) * 151, 50 + (y) * 151, 50 + (y) * 151};
	    int[] arrowHeadX = {62 + (x - 1) * 119, 39 + (x - 1) * 119, 85 + (x - 1) * 119};
	    c.setColor (mysteryArrows);

	    c.fillRect (55 + (x - 1) * 119, 30 + (y) * 151, 15, 80); //draws arrow body
	    c.fillPolygon (arrowHeadX, arrowHeadY, 3); //draws the arrowhead( triangle) with arrays
	}
	//==================================================================DRAWING THE PAWN===========================================================================
	//uses the same method to draw the technique icons on the board as the technique used to draw the arrow icons on board (see above)
	if (pawnSpace % 10 == 0) //if the pawn is on a multiple of ten
	{
	    x = 10; // pawn is in the 10th column
	}

	else
	{
	    x = pawnSpace % 10; //finds out which column the pawn is in
	}


	y = (pawnSpace - x) / 10; // finds out which row the pawn is in

	c.setColor (pawnColor);

	int[] pawnBodyX = {30, 10, 50};
	int[] pawnBodyY = {30, 90, 90};
	//arrays for drawing the pawn body

	for (int i = 0 ; i < 3 ; i++)
	{

	    pawnBodyX [i] = pawnBodyX [i] + (x - 1) * 119;
	    pawnBodyY [i] = pawnBodyY [i] + (y) * 151;
	    //changes each value of the pawn array coordinates by the dimensions of each square (119 x 151) based on which square the pawn is in

	}

	c.fillOval (15 + (x - 1) * 119, 20 + (y) * 151, 30, 30); //draws the pawn array --> coordinates are changed using the square dimensions (119 x 151) based on which square the pawn is in
	c.fillPolygon (pawnBodyX, pawnBodyY, 3); //draws the pawn body
	//lets user see the board temporarily
	delay (3000);
    } //end makeBoard method

    /*This method is for when the user lands on 13, 26, or 39 and the player lands on a chance square. In this method, there are pre-set there are 4 options the
	chance squares can do. The chanceNum will randomly choose a number between 1-4 to see what will happen to the user (2 good and 2 bad options). Regardless of which outcome,
	each if statement is programmed to display what will happen to the user and to reset the position their pawn is on.*/
    public static int chanceSquares (int boardPosition)
    {
	c.clear (); //clears the board that was previously on the screen
	char enterChance;
	int chanceNum;

	//Display to the user that they're on a chance square and gets input to know the user is ready to continue
	c.println ("You've landed on a Chance Square! Click enter to find your fate.");
	enterChance = c.readChar ();

	//Clears screen to display what will happen to their pawn
	c.clear ();

	//Randomly chooses a number between 1-4 and this number will determine what happens to the user
	chanceNum = (int) (4 * Math.random ()) + 1;

	//Displays what happens to the user and resets the pawn position depending on which number was randomly picked (chanceNum)
	//Option 1: The user goes back 3 spaces
	if (chanceNum == 1)
	{
	    c.println ("You are unlucky. You will go back three spaces.");
	    boardPosition -= 3;
	}

	//Option 2: The user goes forward 3 spaces
	else if (chanceNum == 2)
	{
	    c.println ("Yay! You are lucky! You will go forward 3 spaces!");
	    boardPosition += 3;
	}

	//Option 3: The user restarts game by telling a joke
	else if (chanceNum == 3)
	{
	    c.println ("You are super unlucky, you have to hear a joke (with a plot twisting ending).");
	    delay (1000);
	    c.println ("Knock knock!");
	    delay (1000);
	    c.println ("Who's there?");
	    delay (1000);
	    c.println ("What?");
	    delay (1000);
	    c.println ("What who?");
	    delay (1000);
	    c.println ("What happened?");
	    delay (1000);
	    c.println ("I'll tell you. Dun dun dunnnn! You're going back to square one!");
	    boardPosition = 1;
	}

	//Option 4: The user goes forward 9 spaces
	else
	{
	    c.println ("Yay! You are so lucky! You get to go forward 9 spaces");
	    boardPosition += 9;
	}

	//Allows user to read what the new outcome is
	delay (4000);

	//resets the screen to display the gameboard on
	c.clear ();

	//Updates new position the pawn will be on
	return boardPosition;
    } //end chanceSquares method

    /*This method runs the trivia squares. Every multiple of 9 has a trivia square. There are 7 pre-set questions and the program will randomly generate
	  a number between 1 - 7 and depending on the number generated, a different question will be asked. If the user gets the question correct, they will
	  advance 3 spaces, if not they will remain where their pawn is currently. */
    public static int triviaSquares (int boardLocation)
    {
	//Clears previous screen
	c.clear ();
	//Variable declaration
	int triviaNum;
	String userAnswer, message = "You are incorrect. Your pawn will remain on the same square";

	//Tells user what will happen at any trivia square
	c.println ("Your pawn is on the number " + boardLocation + ", which is on a trivia square! So, here is a question for you.");
	c.println ("If you get it right, you get to move forward 3 spaces (yay!), but if you are incorrect, you will remain on the same square.");

	//Generate random number between 1 and 7
	triviaNum = (int) (7 * Math.random ()) + 1;
	//All possible questions (depending on the random number, one of the following questions will be displayed)
	if (triviaNum == 1)
	{
	    //user prompt and get user input
	    c.println ("\nEngineers rule the world. [True] or [False]"); //question 1
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is true)
	    if (userAnswer.equalsIgnoreCase ("True"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else if (triviaNum == 2)
	{
	    //user prompt and get user input
	    c.println ("\nMr. Augustine has a british accent. [True] or [False]"); //question 2
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is false)
	    if (userAnswer.equalsIgnoreCase ("False"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else if (triviaNum == 3)
	{
	    //user prompt and get user input
	    c.println ("\nString is a primitive data type. [True] or [False]"); //question 3
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is false)
	    if (userAnswer.equalsIgnoreCase ("False"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else if (triviaNum == 4)
	{
	    //user prompt and get user input
	    c.println ("\nThe male black widow spider is eaten by the female spider after it completes the 8 hour mating dance across the female's web.    [True] or [False]."); //question 4
	    userAnswer = c.readString ();
	    //If they are correct, they move forward 3 spaces (answer is true)
	    if (userAnswer.equalsIgnoreCase ("True"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else if (triviaNum == 5)
	{
	    //user prompt and get user input
	    c.println ("\n'Wednesday' in german is 'Mittwoch'. [True] or [False]"); //question 5
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is true)
	    if (userAnswer.equalsIgnoreCase ("True"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else if (triviaNum == 6)
	{
	    //user prompt and get user input
	    c.println ("\nThe harp seal eats a diet of Alpinian seagulls and crustaceans. [True] or [False]"); //question 6
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is false)
	    if (userAnswer.equalsIgnoreCase ("False"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	else
	{
	    //user prompt and get user input
	    c.println ("\nMr. Augustine should teach AP or academic Calculus. [True] or [False]"); //question 7
	    userAnswer = c.readString ();

	    //If they are correct, they move forward 3 spaces (answer is true)
	    if (userAnswer.equalsIgnoreCase ("True"))
	    {
		boardLocation += 3; //increases the pawn's current position by 3 if the user gets the answer correct
		message = "Congratulations! You are correct! You will advance 3 spaces.";
	    }
	}

	//Displays what happens to the user depending on what the user inputted
	c.println (message);

	//Delays message to allow user to read the message
	delay (2000);

	//clears screen for next screen
	c.clear ();

	//returns location of pawn
	return boardLocation;
    } //end of trivia method

/*This method is to activate the snake squares. Depending on which snake the user lands on, the player will move back a certain number of spaces.
      Different snakes changes the counter and moves it back a different number of spaces. */
    public static int snakeSquares (int boardSpot)
    {
	//variables
	int temp = boardSpot; //temporary variable remembers the position of the pawn prior to the pawn moving backwards

	//If the pawn is on squares 35 or 49, the pawn will move back 15 spaces
	if (boardSpot == 35 || boardSpot == 49)
	{
	    boardSpot -= 15;
	}

	//If the pawn is on squares 7, 21, or 28, the pawn will move back 4 spaces
	else if (boardSpot == 7 || boardSpot == 21 || boardSpot == 28)
	{
	    boardSpot -= 4;
	}

	else // If the pawn is on square 14, it will move back 10 spaces
	{
	    boardSpot -= 10;
	}

	//Display where the pawn was and its new position
	c.println (" We hope your stay at square " + temp + " was time well spent because you're getting kicked down to " + boardSpot + " ! See ya!");

	//allows user to read the message
	delay (3200);
	//clears screen for next screen (gameboard)
	c.clear ();

	//updates the new position of the pawn
	return boardSpot;
    } //end of snakeSquares method


    /*This method activates the ladder squares. Depending on the current location of the pawn, the pawn will move forward a certain number of squares.
      Different ladder squares will cause it to move forward a different number of spaces*/
    public static int ladderSquares (int pawnSpot)  //put snake squares into an array and snake square arrows in corresponding arrays and then figure it out
    {
	int temp = pawnSpot; //temporary variable remembers the position of the pawn prior to the pawn moving forwards

	//If the pawn is on squares 11 or 44, the pawn will move forward 4 spaces
	if (pawnSpot == 11 || pawnSpot == 44)
	{
	    pawnSpot += 4;
	}

	//If the pawn is on square 33, the pawn will move forward 8 spaces
	else if (pawnSpot == 33)
	{
	    pawnSpot += 8;
	}

	else //If the pawn is on square 22, the pawn will move forward 26 spaces
	{
	    pawnSpot += 26;
	}

	//Display where the pawn was and its new position
	c.println (" 3 cheers for you, you've landed on a good arrow! Get ready to fly from square " + temp + " up to " + pawnSpot + " ! ");

	//allows user to read the message
	delay (3200);

	//clears screen for next screen (gameboard)
	c.clear ();

	//updates the new position of the pawn
	return pawnSpot;

    } //end ladderSquares method

    /*This method is to ask the user whether they would like to replay the game or not. */
    public static int endGame ()
    {
	//Clears previous screen
	c.clear ();
	//Variables
	int playAgain;
	//Get the user input on whether they want to play again or not
	c.println ("Would you like to play again?");
	c.println ("Type [1] for yes and [2] for no."); 
	playAgain = c.readInt ();
//Error trapping
	while (playAgain != 1 && playAgain != 2)
	{
	    c.println ("Would you like to play again? Type [1] for yes and [2] for no.");
	    playAgain = c.readInt ();
	}


	//Returns value into main method to update whether the user will choose to play again or not
	return playAgain;
    } //end endGame method

    /*If the user lands on a 'mystery arrow', this module will draw an arrow displaying where the pawn will be going to based on where the pawn is currently
      (ex. if the pawn is on square 2, an arrow will appear showing which square the pawn will move to).
      The snake squares are: 7, 14, 21, 28, 35, 49
      The ladder squares are: 11, 22, 33, 44*/
    public static void drawArrows (int squareNum)
    {
	//Variables and array creation, declaration, and assignment
	//Colors of snakes and ladders
	Color snakes = new Color (204, 153, 255); //lilac
	Color ladders = new Color (0, 153, 153); //dark turquoise
	//Arrays for arrow heads of snakes
	int[] xsnake1 = {320, 320, 305};
	int[] ysnake1 = {45, 85, 65};
	int[] xsnake2 = {365, 415, 390};
	int[] ysnake2 = {100, 100, 80};
	int[] xSnake3 = {745, 765, 745};
	int[] ySnake3 = {228, 258, 288};
	int[] xSnake4 = {466, 466, 445};
	int[] ySnake4 = {423, 388, 406};
	int[] xSnake5 = {1100, 1100, 1145};
	int[] ySnake5 = {210, 260, 230};
	int[] xSnake6 = {430, 430, 405};
	int[] ySnake6 = {529, 569, 549};
	//Arrays for arrow heads of ladders
	int ladder1X[] = {500, 520, 500};
	int ladder1Y[] = {230, 260, 290};
	int[] xLadder2 = {893, 893, 923};
	int[] yLadder2 = {665, 725, 700};
	int[] ladder3X = {100, 70, 100};
	int[] ladder3Y = {630, 660, 690};

	//Drawing snake arrows ===============================================================================
	c.setColor (snakes); //sets snake arrows to purple

	//snake 1: square 7 (square 7 to square 4)
	if (squareNum == 7) //checks to see if square number is 7
	{
	    //Draws a thick line from square 7 to square 4
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (320, 55 + i, 773, 55 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xsnake1, ysnake1, 3);
	}

	//snake 2: square 14 (square 14 to square 4)
	if (squareNum == 14)
	{
	    //Draws a thick line from square 14 to square 4
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (380 + i, 100, 380 + i, 275);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xsnake2, ysnake2, 3);
	}

	//snake 3: square 21 (square 21 to square 17)
	if (squareNum == 21)
	{
	    //Draws a thick line from square 21 to square 17
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (75, 350 + i, 745, 250 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xSnake3, ySnake3, 3);
	}

	//snake 4: square 28 (square 28 to square 24)
	if (squareNum == 28)
	{
	    //Draws a thick line from square 28 to square 24
	    for (int i = 0 ; i < 17 ; i++)
	    {
		c.drawLine (878, 397 + i, 466, 397 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xSnake4, ySnake4, 3);
	}

	//snake 5: square 35 (square 35 to square 20)
	if (squareNum == 35)
	{
	    //Draws a thick line from square 35 to square 20
	    for (int i = 0 ; i < 35 ; i++)
	    {
		c.drawLine (496 + i, 544, 1100 + i, 230);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xSnake5, ySnake5, 3);
	}

	//snake 6: square 49 (square 49 to square 34)
	if (squareNum == 49)
	{
	    //Draws a thick line from square 49 to square 34
	    for (int i = 0 ; i < 10 ; i++)
	    {
		c.drawLine (1000, 680 + i, 430, 544 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xSnake6, ySnake6, 3);
	}

	//Drawing ladder arrows ============================================================================
	c.setColor (ladders); //sets color to dark turquoise (ladders)
	//ladder 1: square 11 (square 11 to square 15)
	if (squareNum == 11)
	{
	    //Draws a thick line from square 11 to square 15
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (10, 250 + i, 500, 250 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (ladder1X, ladder1Y, 3);
	}

	//ladder 2: square 22 (square 22 to square 48)
	if (squareNum == 22)
	{
	    //Draws a thick line from square 22 to square 48
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (179, 372 + i, 893, 685 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xLadder2, yLadder2, 3);
	}

	//ladder 3: square 33 (square 33 to square 41)
	if (squareNum == 33)
	{
	    //Draws a thick line from square 33 to square 41
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (300, 500 + i, 100, 650 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (ladder3X, ladder3Y, 3);
	}

	//ladder 4: square 44 (square 44 to square 48)
	if (squareNum == 44)
	{
	    //Draws a thick line from square 44 to square 48
	    for (int i = 0 ; i < 20 ; i++)
	    {
		c.drawLine (416, 695 + i, 893, 695 + i);
	    }
	    //Draws the arrow head connecting the head to the line
	    c.fillPolygon (xLadder2, yLadder2, 3); //shares same arrowhead as ladder 2
	}

	//Delays screen to allow user to see the arrow
	delay (1500);

	//Clears screen for the next screen
	c.clear ();

    } //end drawArrows method
//This method is for delaying the time by an indicated amount of milliseconds (placed within parameters)
    /*This method runs the animation to indicate the end of the game */
    public static void endGameAnimation ()
    {
	//clears screen from previous screen
	c.clear ();
	//arrays for colors
	Color background = new Color (17, 225, 121); //turquoise
	Color star1 = new Color (255, 0, 127); //pink
	Color star2 = new Color (58, 255, 255); //bright blue
	Color star2Outline = new Color (192, 192, 192); //grey
	Color star1Shooting = new Color (187, 131, 243); //purple
	Color star2Shooting = new Color (32, 141, 196); //dark blue
	//declares, assigns, and creates new font
	Font gameName = new Font ("Times New Roman", Font.BOLD | Font.ITALIC, 80);
	//variables
	int x1 = 510, y1 = 15, xSpeed1 = 3, ySpeed1 = 2, x2 = 300, y2 = 20, xSpeed2 = 4, ySpeed2 = 2;

	//Changed background colour
	c.setColor (background);
	c.fillRect (10, 10, 1389, 745);

	//making purple star animate across the page
	for (int loop = 0 ; loop < 2057 ; loop++)
	{
	    //Draws star
	    c.setColor (Color.white);
	    c.drawStar (x1, y1, 50, 50);
	    c.setColor (star1);
	    c.fillStar (x1, y1, 50, 50);

	    //delays making background star
	    for (int m = 0 ; m <= 4 ; m++)
	    {
		for (int i = 0 ; i < 100000 ; i++)
		    ;
	    }

	    //Draws shooting part of star
	    c.setColor (star1Shooting);
	    c.fillStar (x1, y1, 50, 50);

	    //Changes speed of x and y
	    x1 += xSpeed1;
	    y1 += ySpeed1;

	    //Keeps stars within background
	    if (x1 > c.maxx () - 60 || x1 < 12)
	    {
		xSpeed1 = -xSpeed1;
	    }
	    if (y1 > c.maxy () - 60 || y1 < 12)
	    {
		ySpeed1 = -ySpeed1;
	    } //end of if
	}

	//makes blue star animate across page
	for (int o = 0 ; o < 3000 ; o++)
	{

	    //draws 2nd star
	    c.setColor (star2Outline);
	    c.drawStar (x2, y2, 50, 50);
	    c.setColor (star2);
	    c.fillStar (x2, y2, 50, 50);

	    //delays making background star 2
	    for (int m = 0 ; m <= 8 ; m++)
	    {
		for (int i = 0 ; i < 10000 ; i++)
		    ;
	    }

	    //Draws shooting part of star
	    c.setColor (star2Shooting);
	    c.fillStar (x2, y2, 50, 50);

	    //Changes speed of x and y
	    x2 += xSpeed2;
	    y2 -= ySpeed2;

	    //Keeps stars within background
	    if (x2 > c.maxx () - 60 || x2 < 12)
	    {
		xSpeed2 = -xSpeed2;
	    }
	    if (y2 > c.maxy () - 60 || y2 < 12)
	    {
		ySpeed2 = -ySpeed2;
	    } //end of if
	}

	//delays for 5 milliseconds before displaying the 'thanks for playing' text
	delay (5);
	//thanks user for playing the game
	c.setColor (Color.black);
	c.setFont (gameName);
	c.drawString ("Thanks for playing", 450, 400);
	c.drawString ("Point to the Top!", 475, 475);
    } //end endGameAnimation method

/*This method is used when the number of rolls has exceeded 10 rolls. This method displays the end game card to indicate the game has ended. */
    public static void youLost ()
    {
	//new fonts
	Font gameOver = new Font ("Impact", Font.ITALIC, 100);
	c.setColor (Color.red);
	//Fills screen to make it red
	c.fillRect (0, 0, c.maxx (), c.maxy ());
	
	//writes game over text
	c.setColor (Color.black);
	c.setFont (gameOver);
	c.drawString ("Game Over!", 430, 320);
	
	//allows user to read game over
	delay (1000);

    } //end youLost method
    public static void delay (int time)
    {
	try
	{
	    Thread.sleep (time);

	}


	catch (InterruptedException e)
	{
	}
    } //end delay method
} //end PointToTheTopAlbanAndTsang class
