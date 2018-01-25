package board;

import java.util.ArrayList;

import board.Pieces.Coord;
import board.Pieces.King;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

/**
 * At the end of every move, call update() to update the GUI all at once
 * 'b' represents the black piece at the bottom of board. Pawns at [x,1]
 * currPlayerColor starts off at 'w', each turn switch to the opposite.
*/

/**
 * 
 * @author Nick Mangracina and James Carroll
 *
 */

public class ChessGame {
	public static Pieces pieces = new Pieces();
	final static char b = 'b';
	final static char w = 'w';
	static char currPlayerColor = w;
	static boolean draw = false;
	static boolean whiteCheck = false;
	static boolean blackCheck = false;
	static boolean selfCheck = false;
	static boolean pieceTaken = false;
	static boolean madePassant = false; //after a pawn moves 2 spots and becomes passantable, it needs to be safe again after the opponent's next turn end
	/**
	 * Checks if the last-moved piece is a promotable pawn
	 * @param lastmoved The pawn you want to test
	 * @return "true" if lastmoved is promotable
	 */
	public static boolean promotable(Coord lastmoved)
	{
		if (Pieces.chessBoard[lastmoved.x][lastmoved.y].getName().equals("P"))
		{
			if ((currPlayerColor == w) && (lastmoved.y == 7))
				return true;
			else if ((currPlayerColor == b) && (lastmoved.y == 0))
				return true;
		}
		
		 return false;
	}
	
	public static void main(String[] args){
			
		Coord c1 = pieces.new Coord(0,0);
		Coord c2 = pieces.new Coord(0,0);
		
		initboard();
		printBoard(pieces);
		
		Scanner scan = new Scanner(System.in);
		String movefmt = "([a-h])(\\d)\\s([a-h])(\\d)(\\s[R|N|B|Q])?(\\sdraw\\?)?";
		Pattern p = Pattern.compile(movefmt); 
		Matcher m;
		int x = -1;
		int y = -1;
		String input;
		while(true)
		{
			System.out.print(((currPlayerColor == 'w') ? "White" : "Black") + "'s move: ");
			input = scan.nextLine().trim();
			if (input.equals("resign")) {
				break;
			} else if (input.equals("draw")) {
				if (ChessGame.draw) {
					break;
				} else {
					illegalmove();
					continue;
				}
			} else {
				if (!input.matches(movefmt)) {
					illegalmove();
					continue;
				} else {
					m = p.matcher(input);
					m.find();
					
					c1.x = Character.getNumericValue(m.group(1).charAt(0)) - 10;
					c1.y = Integer.parseInt(m.group(2));
					c2.x = Character.getNumericValue(m.group(3).charAt(0)) - 10;
					c2.y = Integer.parseInt(m.group(4));
					
					if (movePieceTo(c1,c2))
					{	
						if(c1.y == 1 && c2.y == 3 && Pieces.chessBoard[c1.x][c2.y].getName().equals("P")){
							//System.out.println("passantable");
							((Pieces.Pawn)Pieces.chessBoard[c2.x][c2.y]).passantable = true;
							//madePassant this turn
							madePassant = true;
							x = c2.x;
							y = c2.y;
							//System.out.println("c2: " + c2.x + " " + c2.y);
							//System.out.println("pos: " + Pieces.chessBoard[c2.x][c2.y].position.x + " " + Pieces.chessBoard[c2.x][c2.y].position.y);
						}
						else if(c1.y == 6 && c2.y == 4 && Pieces.chessBoard[c1.x][c2.y].getName().equals("P")){
							//System.out.println("passantable");
							((Pieces.Pawn)Pieces.chessBoard[c2.x][c2.y]).passantable = true;
							madePassant = true;
							x = c2.x;
							y = c2.y;
							//System.out.println("c2: " + c2.x + " " + c2.y);
							//System.out.println("pos: " + x + " " + y);
						} else{
							if (Pieces.chessBoard[c2.x][c2.y] != null) {
								if (madePassant && Pieces.chessBoard[c2.x][c2.y].getName().equals("P")) {
									madePassant = false;
						//			System.out.print("not passantable now");
									((Pieces.Pawn) Pieces.chessBoard[c2.x][c2.y]).passantable = false;
								} 
							}	
						}
						
						
						draw = input.contains("draw?");
						
						if (promotable(c2))
						{
							String prompos = m.group(5);
							if (prompos == null) { prompos = "Q"; }
							
							switch(prompos.charAt(0))
							{
								case 'Q':
									pieces.new Queen(currPlayerColor, c2.x, c2.y);
									break;
								case 'R':
									pieces.new Rook(currPlayerColor, c2.x, c2.y);
									break;
								case 'B':
									pieces.new Bishop(currPlayerColor, c2.x, c2.y);
									break;
								case 'N':
									pieces.new Knight(currPlayerColor, c2.x, c2.y);
									break;
								default:
									break;
								}
						}
						if(Pieces.chessBoard[c2.x][c2.y].pieceName == "R" || Pieces.chessBoard[c2.x][c2.y].pieceName == "K"){
							Pieces.chessBoard[c2.x][c2.y].hasMoved = true;
						}
//						if (checkMate())
//						{
//							System.out.println("Checkmate gg no re");
//												
//						} 
						if (SchachgebotPruefen())
						{
							System.out.println("Check");
													
						}
						if(selfCheck){
						//	System.out.println("You done check'd yourself");
							//ask if they'd want to undo the move
							//if yes skip the check where you change the currPlayerColor to allow for the same player to go again
							selfCheck = false;
						}
						System.out.println();
						printBoard(pieces);
						
						
					
					}
					else
					{
						continue;
					}
				}
			}
			
			if (currPlayerColor == 'w') { currPlayerColor = 'b'; }
			else if (currPlayerColor == 'b') { currPlayerColor = 'w'; }
		}
		if(input.equals("resign")){
			if(currPlayerColor == 'w'){
				System.out.print("Black wins!");
			}
			else{ System.out.print("White wins!");
			}
		}
		if(input.equals("draw")){
			System.out.print("Draw");
		}
		scan.close();
	}
	/**
	 * Creates the standard pieces and lays them out on the board.
	 */
	
	public static void initboard()
	{
	/*	pieces.new Rook(b, 0, 7);
		pieces.new Knight(b, 2, 2);
		pieces.new Bishop(b, 0, 2);
		pieces.new Queen(b, 0, 3);
		pieces.new King(b, 4, 7);
	//	pieces.new Bishop(b, 5, 7);
	//	pieces.new Knight(b, 6, 7);
		pieces.new Rook(b, 7, 7);
		
		for(int col = 0; col <= 7; col++)
		{
			pieces.new Pawn(b, col, 6);
		}
		
		pieces.new Rook(w, 0, 0);
		//pieces.new Knight(w, 1, 0);
		//pieces.new Bishop(w, 2, 0);
		//pieces.new Queen(w, 3, 0);
		pieces.new King(w, 4, 0);
		//pieces.new Bishop(w, 5, 0);
		//pieces.new Knight(w, 6, 0);
		pieces.new Rook(w, 7, 0);
		
		for(int col = 0; col <= 7; col++)
		{
			pieces.new Pawn(w, col, 1);
		}
		*/
		pieces.new Rook(b, 0, 7);
		pieces.new Knight(b, 1, 7);
		pieces.new Bishop(b, 2, 7);
		pieces.new Queen(b, 3, 7);
		pieces.new King(b, 4, 7);
		pieces.new Bishop(b, 5, 7);
		pieces.new Knight(b, 6, 7);
		pieces.new Rook(b, 7, 7);
		
		for(int col = 0; col <= 7; col++)
		{
			pieces.new Pawn(b, col, 6);
		}
		
		pieces.new Rook(w, 0, 0);
		pieces.new Knight(w, 1, 0);
		pieces.new Bishop(w, 2, 0);
		pieces.new Queen(w, 3, 0);
		pieces.new King(w, 4, 0);
		pieces.new Bishop(w, 5, 0);
		pieces.new Knight(w, 6, 0);
		pieces.new Rook(w, 7, 0);
		
		for(int col = 0; col <= 7; col++)
		{
			pieces.new Pawn(w, col, 1);
		} 
	}

	/**
	 * Moves a piece of "color" from "from" to "dest", with checks.
	 * The coordinates must be in the board. You cannot capture your own piece.
	 * The move must also be legal for your piece.
	 * @param color is of the "from" piece, because we check validity.
	 * @return "true" if piece was successfully moved.
	 */	
	public static boolean movePieceTo(Coord from, Coord dest){

		if(from.x < 0 || from.x > 7 || from.y < 0 || from.y > 7 //cant be out of chessboard range
				|| dest.x < 0 || dest.x > 7 || dest.x < 0 || dest.x > 7){
			System.out.println("invalid argument");
			return false; //actually throw exception later on
		}
		ArrayList<Coord> legalMoves = new ArrayList<Coord>();
		if(Pieces.chessBoard[from.x][from.y] == null){
			System.out.println("no piece there");
			return false;
		} else if (Pieces.chessBoard[from.x][from.y].color != ChessGame.currPlayerColor) {
			System.out.println("it's not your turn");
			return false;
		}
		
		
		legalMoves = Pieces.chessBoard[from.x][from.y].getLegalMoves();
		//printing legal moves
		for(int i = 0; i < legalMoves.size(); i++){
	//		System.out.println("printing legal movesNOT MATE: " + legalMoves.get(i).x + "," + legalMoves.get(i).y);
		}
		
		if(Pieces.chessBoard[from.x][from.y] != null && Pieces.chessBoard[dest.x][dest.y] == null){ //for castling
	//		System.out.println("castling");
			if(Pieces.chessBoard[from.x][from.y].getName().equals("K")){
				//boolean canCastle = true;
		//		System.out.println("we");
				
				if (Pieces.chessBoard[from.x][from.y].hasMoved == false/* && Pieces.chessBoard[from.x][from.y].checked == false*/) {
		//				System.out.println("in");
						if (dest.y == from.y && dest.x == from.x + 2) { //black castling with right rook
		//					System.out.println("this");
							if(Pieces.chessBoard[from.x + 3][from.y] != null){
		//						System.out.println("right");
								if(Pieces.chessBoard[from.x + 3][from.y].getName().equals("R") && 
										Pieces.chessBoard[from.x + 3][from.y].hasMoved == false)
								{		
	//									System.out.println("boiiii");
										Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
										Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y);
										Pieces.chessBoard[dest.x][dest.y].hasMoved = true;
										removePiece(from.x,from.y);
				//						System.out.println("should move rook here");
										Pieces.chessBoard[dest.x-1][dest.y] = Pieces.chessBoard[from.x+3][from.y];
										Pieces.chessBoard[dest.x-1][dest.y].setPosition(dest.x-1, dest.y);
										Pieces.chessBoard[dest.x-1][dest.y].hasMoved = true;
										removePiece(from.x+3,from.y);
										return true;
								}
							}
						}
						if (dest.y == from.y && dest.x == from.x - 2) { //b castle left rook
							if(Pieces.chessBoard[from.x - 4][from.y] != null){
								if(Pieces.chessBoard[from.x - 4][from.y].getName().equals("R") && 
										Pieces.chessBoard[from.x - 4][from.y].hasMoved == false)
								{		
										Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
										Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y);
										Pieces.chessBoard[dest.x][dest.y].hasMoved = true;
										removePiece(from.x,from.y);
				//						System.out.println("should move rook here");
										Pieces.chessBoard[dest.x+1][dest.y] = Pieces.chessBoard[from.x-4][from.y];
										Pieces.chessBoard[dest.x+1][dest.y].setPosition(dest.x+1, dest.y);
										Pieces.chessBoard[dest.x+1][dest.y].hasMoved = true;
										removePiece(from.x-4,from.y);
										return true;
								}
							}
						}
				}
			}
			
		}
		
		if (Pieces.chessBoard[from.x][from.y] != null) { //en passant
			//enpassant here
			if (Pieces.chessBoard[from.x][from.y].getName().equals("P") && Pieces.chessBoard[dest.x][dest.y] == null) { //stuck here
				if (from.x != dest.x) {
			//		System.out.print("kinda");
					if (Pieces.chessBoard[from.x][from.y].color == w) {
			//			System.out.print("almost");
						if (((Pieces.Pawn) Pieces.chessBoard[from.x][from.y]).canPassantLeft
								|| ((Pieces.Pawn) Pieces.chessBoard[from.x][from.y]).canPassantRight) {
			//				System.out.print("there");
							Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
							Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y);
							removePiece(from.x, from.y);
							removePiece(dest.x, dest.y - 1);
							((Pieces.Pawn)Pieces.chessBoard[dest.x][dest.y]).canPassantLeft = false;
							((Pieces.Pawn)Pieces.chessBoard[dest.x][dest.y]).canPassantRight = false;
							return true;
						}
					}
					if (Pieces.chessBoard[from.x][from.y].color == b) {
						if (((Pieces.Pawn) Pieces.chessBoard[from.x][from.y]).canPassantLeft
								|| ((Pieces.Pawn) Pieces.chessBoard[from.x][from.y]).canPassantRight) {
	//						System.out.print("there2");
							Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
							Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y);
							removePiece(from.x, from.y);
							removePiece(dest.x, dest.y + 1);
							((Pieces.Pawn)Pieces.chessBoard[dest.x][dest.y]).canPassantLeft = false;
							((Pieces.Pawn)Pieces.chessBoard[dest.x][dest.y]).canPassantRight = false;
							return true;
						}
					}
				}
			} 
		}
		
		if(contains(legalMoves,dest)){ //4
			if(Pieces.chessBoard[dest.x][dest.y] != null)	{ //if there is a piece in the destination..
				//System.out.println(Pieces.chessBoard[dest.x][dest.y].color);
				if(Pieces.chessBoard[dest.x][dest.y].color == currPlayerColor){ //and the piece is ours
					System.out.println("That piece is on your team");
					return false;
				}
				if(Pieces.chessBoard[dest.x][dest.y].color != currPlayerColor){ //opponent's piece
					removePiece(dest.x,dest.y); //this will be more important when we have to remove the other piece from being seen to place the other one
				}
			}	
			Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
			Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y); //this seems stupid but its the only way for
			//a piece to know its own position. stored in the chessPiece object
			removePiece(from.x,from.y);
			legalMoves.clear();
			
			//update();
			return true;
		}
		legalMoves.clear();
		illegalmove();
		return false;
	}
	
	public static boolean moveWithoutPrint(Coord from, Coord dest){ 
	// TODO: this
	  
	 
		if(from.x < 0 || from.x > 7 || from.y < 0 || from.y > 7 //cant be out of chessboard range
				|| dest.x < 0 || dest.x > 7 || dest.x < 0 || dest.x > 7){
			System.out.println("invalid argument");
			return false; //actually throw exception later on
		}
		ArrayList<Coord> legalMoves = new ArrayList<Coord>();
		System.out.println("without: " + Pieces.chessBoard[from.x][from.y]);
		if(Pieces.chessBoard[from.x][from.y] == null){
			System.out.println("no piece there");
			return false;
		} else if (Pieces.chessBoard[from.x][from.y].color == ChessGame.currPlayerColor) {
			System.out.println("it's not your turn");
			return false;
		}

		
		legalMoves = Pieces.chessBoard[from.x][from.y].getLegalMoves();
		//printing legal moves
		for(int i = 0; i < legalMoves.size(); i++){
			System.out.println("printing legal moves: " + legalMoves.get(i).x + "," + legalMoves.get(i).y);
		}

		if(contains(legalMoves,dest)){
			if(Pieces.chessBoard[dest.x][dest.y] != null)	{ //if there is a piece in the destination..
				//System.out.println(Pieces.chessBoard[dest.x][dest.y].color);
				if(Pieces.chessBoard[dest.x][dest.y].color != currPlayerColor){ //and the piece is ours
					System.out.println("That piece is on your team");
					return false;
				}
				if(Pieces.chessBoard[dest.x][dest.y].color == currPlayerColor){ //opponent's piece
					Pieces.pieceHolder[0] = Pieces.chessBoard[dest.x][dest.y];
					removePiece(dest.x,dest.y); //this will be more important when we have to remove the other piece from being seen to place the other one
					pieceTaken = true;
					
				}
			}	
			Pieces.chessBoard[dest.x][dest.y] = Pieces.chessBoard[from.x][from.y];
			Pieces.chessBoard[dest.x][dest.y].setPosition(dest.x, dest.y); //this seems stupid but its the only way for
			//a piece to know its own position. stored in the chessPiece object
			removePiece(from.x,from.y);
			legalMoves.clear();
			
			//update();
			return true;
		}
		return false;
		
	} 
	/**
	 * Prints out "illegal move" in a standardized way.
	 */
	public static void illegalmove()
	{
		System.out.println("illegal move");
	}
	
	/**
	 * "Check for check" by searching the whole board
	 * for opponent's pieces, and then seeing if your
	 * king is in their list of possible moves.
	 * @param the color whose king you want to check
	 * @return true if color is in check
	 */
	public static boolean SchachgebotPruefen()
	{  //need to make it so if you just moved a piece which leaves your king open, it is an illegal move. it could be
		//the king or any of your pieces
		for(int i = 0; i < Pieces.chessBoard.length; i++)
		{
			for(int j = 0; j < Pieces.chessBoard[0].length; j++)
			{
				if ((Pieces.chessBoard[i][j] != null) && (Pieces.chessBoard[i][j].color == currPlayerColor))
				{
					ArrayList<Coord> nextlegalMoves = Pieces.chessBoard[i][j].getLegalMoves();
					
					for (Coord nlM : nextlegalMoves)
					{
						if(Pieces.chessBoard[nlM.x][nlM.y] != null)
						{	
							if (Pieces.chessBoard[nlM.x][nlM.y].color != currPlayerColor)
							{
								if (Pieces.chessBoard[nlM.x][nlM.y].getName().equals("K"))
								{
									((King)Pieces.chessBoard[nlM.x][nlM.y]).checked = true;
									if(currPlayerColor == 'w'){
										blackCheck = true;
									}
									else { whiteCheck = true;
									}	
									return true;
								}
							}
						}	
					}
					
					nextlegalMoves.clear();
				}
				else if ((Pieces.chessBoard[i][j] != null) && (Pieces.chessBoard[i][j].color != currPlayerColor)){
					ArrayList<Coord> nextlegalMoves = Pieces.chessBoard[i][j].getLegalMoves();
					
					for (Coord nlM : nextlegalMoves)
					{
						if(Pieces.chessBoard[nlM.x][nlM.y] != null)
						{	
							if (Pieces.chessBoard[nlM.x][nlM.y].color == currPlayerColor)
							{
								if (Pieces.chessBoard[nlM.x][nlM.y].getName().equals("K"))
								{
									((King)Pieces.chessBoard[nlM.x][nlM.y]).checked = true;
									selfCheck = true;
									if(currPlayerColor == 'w'){
										whiteCheck = true;
									}
									else { blackCheck = true;
									}	
									return false;
								}
							}
						}	
					}
					
					nextlegalMoves.clear();
				}
			}
		}
		
		return false;
	}
	
	public static boolean checkMate() /* TODO: this */
	{  
		int total = 0;
		int checks = 0;
		for(int i = 0; i < Pieces.chessBoard.length; i++)
		{
			for(int j = 0; j < Pieces.chessBoard[0].length; j++)
			{
				if ((Pieces.chessBoard[i][j] != null) && (Pieces.chessBoard[i][j].color != currPlayerColor))
				{
					Pieces.chessBoard[i][j].setPosition(i, j);
					ArrayList<Coord> nextlegalMoves = Pieces.chessBoard[i][j].getLegalMoves();
					
					for (Coord nlM : nextlegalMoves)
					{
						System.out.println("ij: " + i + " " + j);
						System.out.println("pos: " + Pieces.chessBoard[i][j].position.x + " " + Pieces.chessBoard[i][j].position.y);
						Pieces.chessBoard[i][j].position.x = i;
						Pieces.chessBoard[i][j].position.y = j;
						
						if(moveWithoutPrint(Pieces.chessBoard[i][j].position,nlM)){
								if(SchachgebotPruefen()){
									total++;
									checks++;
								}
								else{
									total++;
								}
								
								
								if(moveWithoutPrint(nlM,Pieces.chessBoard[i][j].position)){
									System.out.println("moved it back");
									//String pN = Pieces.pieceHolder[0].pieceName;
									if(pieceTaken == true){
										Pieces.chessBoard[nlM.x][nlM.y] = Pieces.pieceHolder[0];
										Pieces.chessBoard[nlM.x][nlM.y].setPosition(nlM.x,nlM.y); //maybe?
										pieceTaken = false;
									}
									
								} else {
									System.out.println("couldnt move back");
								}
									
										
									
								
						}
						else {
							System.out.println("couldnt move to");
						}
						
			
							
					}
					
					nextlegalMoves.clear();
				}
	
			}
		}
		if(total > checks)
			return false;
		else return true;
	}
	/**
	 * Checks if a specific coordinate is in a legal moves list
	 * @param legals A piece's legal moves
	 * @param dest A coordinate pair you are looking for
	 * @return true if "dest" is in "legals"
	 */
	public static boolean contains(ArrayList<Coord> legals, Coord dest){ 
		//the default contains function for arraylists only checks if it's the same object, not if the values are equal
		int i = 0;
		while(i<legals.size()){
			//System.out.println(legals.get(i).x + " " + legals.get(i).y);
			if(legals.get(i).x == dest.x && legals.get(i).y == dest.y){
				//System.out.println("return true");
				return true;
			}
			i++;
		}
		return false;
	}
	/**
	 * Removes the place of a piece on the chessboard
	 * Does not actually delete the piece object
	 * @param x x-coordinate of the piece to be removed
	 * @param y y-coordinate of the piece to be removed
	 */
	public static void removePiece(int x, int y){
		Pieces.chessBoard[x][y] = null;
		//do other stuff to make the animation of the piece be deleted
	}
	/**
	 * Prints the board with piece names, colors, in correct location.
	 * Alternating checkerboard design has blank space or ## if no piece.
	 * @param p The class that holds the ChessBoard object
	 */
	public static void printBoard(Pieces p)
	{
		for(int y = 7; y >= 0; y--)
		{
			for(int x = 0; x < 8; x++)
			{
				if(Pieces.chessBoard[x][y] == null)
				{
					if ((x%2==0 && y%2==0) ||
						(y%2!=0 && x%2!=0))
					{
						System.out.print("## ");
					}
					else
					{
						System.out.print("   ");
					}
				}
				else
				{
					System.out.print(Pieces.chessBoard[x][y].getColor() + Pieces.chessBoard[x][y].getName() + " ");
				}
			}
			System.out.println(y);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
}