package board;

import java.util.ArrayList;

/**
 * 
 * @author Nick Mangracina and James Carroll
 *
 */

public class Pieces {
	
	static ChessPiece chessBoard[][] = new ChessPiece[8][8];
	static ChessPiece pieceHolder[] = new ChessPiece[1]; 
	public class Coord {
		public int x, y;
		public Coord(int x, int y)
		{
			this.x = x; this.y = y;
		}
		public Coord()
		{
			this(-1, -1);
		}
		
	
	}
	
	abstract class ChessPiece { 
		public String pieceName;
		public boolean hasMoved = false;
		public ArrayList<Coord> legalMoves; 
		/* use legalmoves later on to hold on to all the possible moves for each piece, then
		*run a loop on ChessBoard and check all the indexes for all the legal moves for the chessboard to see if the
		*enemy's king is in the set of legalMoves
		*/
		protected Coord position = new Coord();
		public char color;
		/**
		 * 
		 * when black player is making a move it will check 
		 * to make sure the piece they are trying to move is black
		 */
		public ChessPiece(char color){
			this.color = color;
		}
		/**
		 * Create a ChessPiece and set it somewhere on the board.
		 * @param color color of the piece to create
		 * @param x initial X-coordinate
		 * @param y initial Y-coordinate
		 */
		public ChessPiece(char color, int x, int y) {
			this(color);
			Pieces.chessBoard[x][y] = this;
			this.setPosition(x, y);
		}
		/**
		 * setPosition is used after creation of a piece at 
		 * beginning of game or after a piece is recovered
		 * @param a x
		 * @param b y
		 */
		void setPosition(int a, int b){
			this.position.x = a;
			this.position.y = b;
		}
		/**
		 * Generates a list of possible moves based on the piece type.
		 * Takes into account en passant, castling, other special moves.
		 * @return A list of possible moves that the ChessPiece can make
		 */
		abstract ArrayList<Coord> getLegalMoves();
		abstract String getName();
		public char getColor(){
			return color;
		}
	}
	
	public class Pawn extends ChessPiece {
		public final String pieceName = "P";
		public boolean passantable = false;
		public boolean canPassantRight = false;
		public boolean canPassantLeft = false;
		public Pawn(char color){
			super(color);
		}
		public Pawn(char color, int x, int y) {
			super(color, x, y);
		}
		@Override
		public ArrayList<Coord> getLegalMoves() { //getLegalMoves may be able to be done internally
			
			ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
			
			if(this.color == 'w' && this.position.y > 6)
				return possiblemoves;
			if(this.color == 'b' && this.position.y < 1)
				return possiblemoves;
			if ((this.position.y == 1 && this.color == 'w'))
			{	
				if(chessBoard[this.position.x][this.position.y+1] == null //both spots must be open to move 2 spots forward 
						&& chessBoard[this.position.x][this.position.y+2] == null){ 
					//System.out.println("possibe: " + possiblemoves);
					possiblemoves.add(new Coord(this.position.x, this.position.y+2));
					//System.out.println("possibe: " + possiblemoves);
				}
				
			}
			if ((this.position.y == 6 && this.color == 'b'))
			{	
				if(chessBoard[this.position.x][this.position.y-1] == null //both spots must be open to move 2 spots forward 
						&& chessBoard[this.position.x][this.position.y-2] == null){ 
					//System.out.println("possibe: " + possiblemoves);
					possiblemoves.add(new Coord(this.position.x, this.position.y-2));
					//System.out.println("possibe: " + possiblemoves);
				}
				
			}
			//make sure the new spot isn't taken by any piece (regardless of color)
			if(this.position.y < 7 && this.color == 'w' && chessBoard[this.position.x][this.position.y+1] == null){  //this will only work for black pawn
				possiblemoves.add(new Coord(this.position.x, this.position.y+1));
				//System.out.println("possibe: " + possiblemoves.get(0).y + " " + possiblemoves.get(1).y);
			}
			if(this.position.y > 0 && this.color == 'b' && chessBoard[this.position.x][this.position.y-1] == null){  //this will only work for black pawn
				possiblemoves.add(new Coord(this.position.x, this.position.y-1));
				//System.out.println("possibe: " + possiblemoves.get(0).y + " " + possiblemoves.get(1).y);
			}
			if(this.color == 'w'){ 
				//edge case: you are along the border.
				if(this.position.x == 0 && chessBoard[this.position.x+1][this.position.y+1] != null) //covers left border pawn
					//we dont check the other pieces color here because this can apply for all pieces and thus be done in ChessGame. 
					//you can only move there if the piece isn't yours, but at this point knowing any piece is there is good enough
					possiblemoves.add(new Coord(this.position.x+1, this.position.y+1));
				if(this.position.x == 7 && chessBoard[this.position.x-1][this.position.y+1] != null) //covers right
					possiblemoves.add(new Coord(this.position.x-1, this.position.y+1));
				if(this.position.x > 0 && this.position.x <7){
					if(chessBoard[this.position.x+1][this.position.y+1] != null)
						possiblemoves.add(new Coord(this.position.x+1, this.position.y+1));
					if(chessBoard[this.position.x-1][this.position.y+1] != null)
						possiblemoves.add(new Coord(this.position.x-1, this.position.y+1));
				}
			}
			if(this.color == 'b'){ 
				if(this.position.x == 0 && chessBoard[this.position.x+1][this.position.y-1] != null) 
					possiblemoves.add(new Coord(this.position.x+1, this.position.y-1));
				if(this.position.x == 7 && chessBoard[this.position.x-1][this.position.y-1] != null)
					possiblemoves.add(new Coord(this.position.x-1, this.position.y-1));
				if(this.position.x > 0 && this.position.x <7){
					if(chessBoard[this.position.x+1][this.position.y-1] != null)
						possiblemoves.add(new Coord(this.position.x+1, this.position.y-1));
					if(chessBoard[this.position.x-1][this.position.y-1] != null)
						possiblemoves.add(new Coord(this.position.x-1, this.position.y-1));
				}
				
			}
			int currX = this.position.x;  
			int currY = this.position.y;
			if(currX == 0){ //scenerio that it is on the side
				if (chessBoard[currX + 1][currY] != null) {
					if (chessBoard[currX + 1][currY].getName().equals("P")) {
						if (((Pawn) chessBoard[currX + 1][currY]).passantable == true) {
							((Pawn) chessBoard[currX][currY]).canPassantRight = true;
							if(chessBoard[currX][currY].color == 'w') possiblemoves.add(new Coord(this.position.x+1, this.position.y+1));
							if(chessBoard[currX][currY].color == 'b') possiblemoves.add(new Coord(this.position.x+1, this.position.y-1));
						}
					} 
				}
			}
			else if(currX == 7){ //scenerio that it is on the side
				if (chessBoard[currX - 1][currY] != null) {
					if (chessBoard[currX - 1][currY].getName().equals("P")) {
						if (((Pawn) chessBoard[currX - 1][currY]).passantable == true) {
							((Pawn) chessBoard[currX][currY]).canPassantLeft = true;
							if(chessBoard[currX][currY].color == 'w') possiblemoves.add(new Coord(this.position.x-1, this.position.y+1));
							if(chessBoard[currX][currY].color == 'b') possiblemoves.add(new Coord(this.position.x-1, this.position.y-1));
						}
					} 
				}
			}
			else { //scenerio that it is on the side
				if (chessBoard[currX - 1][currY] != null) {
		//			System.out.println("this should trigger");
					if (chessBoard[currX - 1][currY].getName().equals("P")) {
		//				System.out.println("out");
						if (((Pawn) chessBoard[currX - 1][currY]).passantable == true) { //might need to make passantable in chesspiece
			//				System.out.println("in");
							((Pawn) chessBoard[currX][currY]).canPassantLeft = true;
							if(chessBoard[currX][currY].color == 'w') possiblemoves.add(new Coord(this.position.x-1, this.position.y+1));
							if(chessBoard[currX][currY].color == 'b') possiblemoves.add(new Coord(this.position.x-1, this.position.y-1));
						}
					} 
				}
				if (chessBoard[currX + 1][currY] != null) {
					if (chessBoard[currX + 1][currY].getName().equals("P")) {
						if (((Pawn) chessBoard[currX + 1][currY]).passantable == true) {
							((Pawn) chessBoard[currX][currY]).canPassantRight = true;
							if(chessBoard[currX][currY].color == 'w') possiblemoves.add(new Coord(this.position.x+1, this.position.y+1));
							if(chessBoard[currX][currY].color == 'b') possiblemoves.add(new Coord(this.position.x+1, this.position.y-1));
						}
					} 
				}
			}
			//TODO: add en-passant implementation, add rest of diagonal moves
			return possiblemoves;
		}
		public String getName(){
			return pieceName;
		}
	}
	/** 
	 * Start from your spot, then keep going 1 space farther until you hit a piece. 
	 * Each spot without a piece is added to possiblemoves and when you hit a piece or a border you stop. 
	 * The piece's spot is added to the list, in ChessGames it is checked if it is ours or enemy's piece.
	 * */
	 //TODO: add castling
	public class Rook extends ChessPiece { 
		public String pieceName = "R";
		//public boolean hasMoved = false;
		public Rook(char color){
			super(color);
		}
		public Rook(char color, int x, int y) {
			super(color, x, y);
		}
		@Override
		ArrayList<Coord> getLegalMoves() {
			ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
			int currX = this.position.x;  //currX is way faster to type then this.position.x 
			int currY = this.position.y;
			int x = currX+1;
			while(x<8){ //assuming you arent at a border, you'll always have at least 4 legal spots (even if they are your own pieces)
				possiblemoves.add(new Coord(x,currY));
				if(chessBoard[x][currY] != null){
					break;
				}
				x++;
			}
			x = currX-1;
			while(x>=0){
				possiblemoves.add(new Coord(x,currY));
				if(chessBoard[x][currY] != null){
					break;
				}
				x--;
			}
			int y = currY+1;
			while(y<8){
				possiblemoves.add(new Coord(currX,y));
				if(chessBoard[currX][y] != null){
					break;
				}
				y++;
			}
			y = currY-1;
			while(y>=0){
				possiblemoves.add(new Coord(currX,y));
				if(chessBoard[currX][y] != null){
					break;
				}
				y--;
			}
			//recycle this + bishop code for queen*******************
			
			return possiblemoves;
		}
		public String getName(){
			return pieceName;
		}
	}
	public class Bishop extends ChessPiece { 
			final String pieceName = "B"; //for printing purposes
			public Bishop(char color){
				super(color);
			}
			public Bishop(char color, int x, int y) {
				super(color, x, y);
			}
			@Override
			ArrayList<Coord> getLegalMoves() {
				ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
				int currX = this.position.x;  
				int currY = this.position.y;
				int x = currX+1; //diagonal up-right
				int y = currY+1;
				while(x<8 && y<8){
					possiblemoves.add(new Coord(x,y));
					if(chessBoard[x][y] != null){
						break;
					}
					x++;
					y++;
				}
				x = currX+1; //diagonal down-right
				y = currY-1;
				while(x<8 && y>=0){
					possiblemoves.add(new Coord(x,y));
					if(chessBoard[x][y] != null){
						break;
					}
					x++;
					y--;
				}
				x = currX-1; //diag down-left
				y = currY-1;
				while(x>=0 && y>=0){
					possiblemoves.add(new Coord(x,y));
					if(chessBoard[x][y] != null){
						break;
					}
					x--;
					y--;
				}
				x = currX-1; //diag up-left
				y = currY+1;
				while(x>=0 && y<8){
					possiblemoves.add(new Coord(x,y));
					if(chessBoard[x][y] != null){
						break;
					}
					x--;
					y++;
				}
				
				return possiblemoves;
			}
			public String getName(){
				return pieceName;
			}	
	
	}
	public class Queen extends ChessPiece { 
		final String pieceName = "Q"; //for printing purposes
		public Queen(char color){
			super(color);
		}
		public Queen(char color, int x, int y) {
			super(color, x, y);
		}
		@Override
		ArrayList<Coord> getLegalMoves() {
			ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
			int currX = this.position.x;  
			int currY = this.position.y;
			int x = currX+1; //diagonal up-right
			int y = currY+1;
			while(x<8 && y<8){
				possiblemoves.add(new Coord(x,y));
				if(chessBoard[x][y] != null){
					break;
				}
				x++;
				y++;
			}
			x = currX+1; //diagonal down-right
			y = currY-1;
			while(x<8 && y>=0){
				possiblemoves.add(new Coord(x,y));
				if(chessBoard[x][y] != null){
					break;
				}
				x++;
				y--;
			}
			x = currX-1; //diag down-left
			y = currY-1;
			while(x>=0 && y>=0){
				possiblemoves.add(new Coord(x,y));
				if(chessBoard[x][y] != null){
					break;
				}
				x--;
				y--;
			}
			x = currX-1; //diag up-left
			y = currY+1;
			while(x>=0 && y<8){
				possiblemoves.add(new Coord(x,y));
				if(chessBoard[x][y] != null){
					break;
				}
				x--;
				y++;
			}	
			x = currX+1;
			while(x<8){ //assuming you arent at a border, you'll always have at least 4 legal spots (even if they are your own pieces)
				possiblemoves.add(new Coord(x,currY));
				if(chessBoard[x][currY] != null){
					break;
				}
				x++;
			}
			x = currX-1;
			while(x>=0){
				possiblemoves.add(new Coord(x,currY));
				if(chessBoard[x][currY] != null){
					break;
				}
				x--;
			}
			y = currY+1;
			while(y<8){
				possiblemoves.add(new Coord(currX,y));
				if(chessBoard[currX][y] != null){
					break;
				}
			y++;
			}
			y = currY-1;
			while(y>=0){
				possiblemoves.add(new Coord(currX,y));
				if(chessBoard[currX][y] != null){
					break;
				}
				y--;
			}	
							
			return possiblemoves;
		}
		public String getName(){
			return pieceName;
		}	
	
	}
	public class Knight extends ChessPiece { 
		public String pieceName = "N";
		public Knight(char color){
			super(color);
		}
		public Knight(char color, int x, int y) {
			super(color, x, y);
		}
		@Override
		ArrayList<Coord> getLegalMoves() {
			ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
			int currX = this.position.x;  //currX is way faster to type then this.position.x 
			int currY = this.position.y;
			
			possiblemoves.add(new Coord(currX-1,currY-2));
			possiblemoves.add(new Coord(currX-1,currY+2));
			possiblemoves.add(new Coord(currX+1,currY-2));
			possiblemoves.add(new Coord(currX+1,currY+2));
			possiblemoves.add(new Coord(currX-2,currY-1));
			possiblemoves.add(new Coord(currX+2,currY-1));
			possiblemoves.add(new Coord(currX-2,currY+1));
			possiblemoves.add(new Coord(currX+2,currY+1));
			int i = 0;
			while(i<possiblemoves.size()){
				
				if(possiblemoves.get(i).x < 0 || possiblemoves.get(i).x > 7 
					|| possiblemoves.get(i).y < 0 || possiblemoves.get(i).y > 7 )   
				{
					possiblemoves.remove(i);
					continue; //continue without iterating as you just removed curr index element
				}
				i++;
			}	
				
			return possiblemoves;
		}
		public String getName(){
			return pieceName;
		}
	}
	public class King extends ChessPiece {
		public boolean checked = false;
		//public boolean hasMoved = false;
		public String pieceName = "K";
		public King(char color){
			super(color);
		}
		public King(char color, int x, int y) {
			super(color, x, y);
		}
		@Override
		ArrayList<Coord> getLegalMoves() {
			ArrayList<Coord> possiblemoves = new ArrayList<Coord>();
			int currX = this.position.x;  //currX is way faster to type then this.position.x 
			int currY = this.position.y;
			
			possiblemoves.add(new Coord(currX-1,currY-1));
			possiblemoves.add(new Coord(currX-1,currY+1));
			possiblemoves.add(new Coord(currX+1,currY-1));
			possiblemoves.add(new Coord(currX+1,currY+1));
			possiblemoves.add(new Coord(currX+1,currY));
			possiblemoves.add(new Coord(currX-1,currY));
			possiblemoves.add(new Coord(currX,currY+1));
			possiblemoves.add(new Coord(currX,currY-1));
			int i = 0; //
			while(i<possiblemoves.size()){
				
				if(possiblemoves.get(i).x < 0 || possiblemoves.get(i).x > 7 
					|| possiblemoves.get(i).y < 0 || possiblemoves.get(i).y > 7 )   
				{
					possiblemoves.remove(i);
					continue; //continue without iterating as you just removed curr index element
				}
				i++;
			}	
			
			if (currX == 4) {
				if (((King) chessBoard[currX][currY]).checked == false) { //king cant be checked.. might need to check for King to prevent cast prob? nah
					if (chessBoard[currX][currY].hasMoved == false) { //has to not have moved

						if (chessBoard[currX + 3][currY] != null) {
							if (chessBoard[currX + 3][currY].getName().equals("R")
									&& chessBoard[currX + 3][currY].hasMoved == false) {
								possiblemoves.add(new Coord(currX + 2, currY));
							}
						}
						if (chessBoard[currX - 4][currY] != null) {
							if (chessBoard[currX - 4][currY].getName().equals("R")
									&& chessBoard[currX + 3][currY].hasMoved == false) {
								possiblemoves.add(new Coord(currX - 2, currY));
							}
						}
					}
				} 
			}
			return possiblemoves;
		}
		public String getName(){
			return pieceName;
		}
	}
}