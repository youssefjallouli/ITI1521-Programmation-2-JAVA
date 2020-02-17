/**
 * The class <b>TicTacToeGame</b> is the
 * class that implements the Tic Tac Toe Game.
 * It contains the grid and tracks its progress.
 * It automatically maintain the current state of
 * the game as players are making moves.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class TicTacToeGame {

// FINISH THE VARIABLE DECLARATION
	/**
	 * The board of the game, stored as a one dimension array.
	 */
	CellValue[] board;


	/**
	 * level records the number of rounds that have been
	 * played so far.
	 */
	int level;

	/**
	 * gameState records the current state of the game.
	 */
	GameState gameState;


	/**
	 * lines is the number of lines in the grid
	 */
	int lines;

	/**
	 * columns is the number of columns in the grid
	 */
	int columns;


	/**
	 * sizeWin is the number of cell of the same type
	 * that must be aligned to win the game
	 */
	int sizeWin;


	/**
	 * default constructor, for a game of 3x3, which must
	 * align 3 cells
	 */
	public TicTacToeGame(){
		this.lines = 3;
		this.columns = 3;
		this.sizeWin = 3;
		this.level = 0;
		this.gameState = GameState.PLAYING;
		this.board = new CellValue[this.lines*this.columns];
		this.initBoardCells();
	}

	/**
	 * constructor allowing to specify the number of lines
	 * and the number of columns for the game. 3 cells must
	 * be aligned.
	 * @param lines
	 *  the number of lines in the game
	 * @param columns
	 *  the number of columns in the game
	 */
	public TicTacToeGame(int lines, int columns){
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = 3;
		this.level = 0;
		this.gameState = GameState.PLAYING;
		this.board = new CellValue[this.lines*this.columns];
		this.initBoardCells();
	}

	/**
	 * constructor allowing to specify the number of lines
	 * and the number of columns for the game, as well as
	 * the number of cells that must be aligned to win.
	 * @param lines
	 *  the number of lines in the game
	 * @param columns
	 *  the number of columns in the game
	 * @param sizeWin
	 *  the number of cells that must be aligned to win.
	 */
	public TicTacToeGame(int lines, int columns, int sizeWin){
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = sizeWin;
		this.level = 0;
		this.gameState = GameState.PLAYING;
		this.board = new CellValue[this.lines*this.columns];
		this.initBoardCells();
	}

	/**
	 * getter for the variable lines
	 * @return
	 * 	the value of lines
	 */
	public int getLines(){
		return this.lines;
	}

	/**
	 * getter for the variable columns
	 * @return
	 * 	the value of columns
	 */
	public int getColumns(){
		return this.columns;
	}

	/**
	 * getter for the variable level
	 * @return
	 * 	the value of level
	 */
	public int getLevel(){
		return this.level;
	}

	/**
	 * getter for the variable sizeWin
	 * @return
	 * 	the value of sizeWin
	 */
	public int getSizeWin(){
		return this.sizeWin;
	}

	/**
	 * getter for the variable gameState
	 * @return
	 * 	the value of gameState
	 */
	public GameState getGameState(){
		return this.gameState;
	}

	/**
	 * returns the cellValue that is expected next,
	 * in other word, which played (X or O) should
	 * play next.
	 * This method does not modify the state of the
	 * game.
	 * @return
	 *  the value of the enum CellValue corresponding
	 * to the next expected value.
	 */
	public CellValue nextCellValue(){
		if(this.level%2 == 0){
			return CellValue.X;
		} else {
			return CellValue.O;
		}
	}

	/**
	 * returns the value  of the cell at
	 * index i.
	 * If the index is invalid, an error message is
	 * printed out. The behaviour is then unspecified
	 * @param i
	 *  the index of the cell in the array board
	 * @return
	 *  the value at index i in the variable board.
	 */
	public CellValue valueAt(int i) {
		return this.board[i];
	}

	/**
	 * This method is called when the next move has been
	 * decided by the next player. It receives the index
	 * of the cell to play as parameter.
	 * If the index is invalid, an error message is
	 * printed out. The behaviour is then unspecified
	 * If the chosen cell is not empty, an error message is
	 * printed out. The behaviour is then unspecified
	 * If the move is valide, the board is updated, as well
	 * as the state of the game.
	 * To faciliate testing, is is acceptable to keep playing
	 * after a game is already won. If that is the case, the
	 * a message should be printed out and the move recorded.
	 * the  winner of the game is the player who won first
	 * @param i
	 *  the index of the cell in the array board that has been
	 * selected by the next player
	 */
	public void play(int i) {
		if(!(i<(this.lines*this.columns))){
			System.out.println("la position "+i+" est invalide");

		} else if (this.valueAt(i) != CellValue.EMPTY) {
			System.out.println(this.valueAt(i));
		} else {
			this.board[i] = this.nextCellValue();
			this.setGameState(i);
			this.level++;
		}
	}


	/**
	 * A helper method which updates the gameState variable
	 * correctly after the cell at index i was just set in
	 * the method play(int i)
	 * The method assumes that prior to setting the cell
	 * at index i, the gameState variable was correctly set.
	 * it also assumes that it is only called if the game was
	 * not already finished when the cell at index i was played
	 * (i.e. the game was playing). Therefore, it only needs to
	 * check if playing at index i has concluded the game, and if
	 * set the oucome correctly
	 *
	 * @param i
	 *  the index of the cell in the array board that has just
	 * been set
	 */


	private void setGameState(int i){
		CellValue winner = this.valueAt(i);
		CellValue[][] mat = this.getCurrentMatBoard();
		if(this.checkHorizontal(mat, i) || this.checkVertical(mat, i) || this.checkSecondDiagonal(mat, i) || this.checkFirstDiagonal(mat, i)){
			this.setWinner(winner);
		}
		if(this.checkNullGame()){
			this.gameState = GameState.DRAW;
		}
	}



	/**
	 * Returns a String representation of the game matching
	 * the example provided in the assignment's description
	 *
	 * @return
	 *  String representation of the game
	 */

	public String toString(){
		CellValue[][] mat = this.getCurrentMatBoard();
		String affichage = "";
		for(int i = 0; i<this.lines; i++){
			for(int j = 0; j<this.columns; j++){
				if(mat[i][j] == CellValue.EMPTY){
					affichage += "|   |";
				}
				else {
					affichage += "|"+mat[i][j]+"|";
				}

			}
			affichage += '\n';
			affichage += "---------------------";
			affichage += '\n';
		}
		return affichage;
	}

	public void initBoardCells(){
		int i = 0;
		for(CellValue cell: this.board) {
			this.board[i] = CellValue.EMPTY;
			i++;
		};
	}

	public boolean checkVertical(CellValue[][] mat, int i){
		CellValue[] checkArray;
		int tmp = i;
		int r = 0, j;
		while(tmp>=this.columns){
			r++;
			tmp = tmp - this.columns;
		}
		j = tmp;

		int start = r - this.sizeWin;
		while (0>start){
			start ++;
		}
		int stop = start+(this.sizeWin-1);
		while (stop>=this.lines){
			stop--;
		}
		int dif = (stop-start)+1;
		while(start<=j && stop<this.lines && dif == this.sizeWin){
			checkArray = new CellValue[this.sizeWin];
			int count = start;
			int checkIndex = 0;
			while(count <= stop){
				checkArray[checkIndex] = mat[count][j];
				checkIndex++;
				count++;
			}
			if(this.isTheSame(checkArray,this.valueAt(i))){
				return true;
			}
			start ++;
			stop ++;
			dif = (stop-start)+1;
		}
		return false;
	}

	public boolean checkHorizontal(CellValue[][] mat, int i){
		CellValue[] checkArray;
		int tmp = i;
		int r = 0, j;
		while(tmp>=this.columns){
			r++;
			tmp = tmp - this.columns;
		}
		j = tmp;
		int start = j - this.sizeWin;
		while (0>start){
			start ++;
		}

		int stop = start+(this.sizeWin-1);
		while (stop>=this.columns){
			stop--;
		}
		int dif = (stop-start)+1;
		while(start<=r && stop<this.columns && dif == this.sizeWin){
			checkArray = new CellValue[this.sizeWin];
			int count = start;
			int checkIndex = 0;
			while(count <= stop){
				checkArray[checkIndex] = mat[r][count];
				checkIndex++;
				count++;
			}
			if(this.isTheSame(checkArray,this.valueAt(i))){
				return true;
			}
			start ++;
			stop ++;
			dif = (stop-start)+1;
		}
		return false;
	}

	public boolean checkFirstDiagonal(CellValue[][] mat, int i){
		CellValue[] checkArray;
		int tmp = i;
		int r = 0, j;
		while(tmp>=this.columns){
			r++;
			tmp = tmp - this.columns;
		}
		j = tmp;
		int start = j - this.sizeWin;
		while (0>start){
			start ++;
		}

		int stop = start+(this.sizeWin-1);
		while (stop>=this.columns){
			stop--;
		}
		int dif = (stop-start)+1;
		while(start<=r && stop<this.lines && stop<this.columns && dif == this.sizeWin){
			checkArray = new CellValue[this.sizeWin];
			int count = start;
			int checkIndex = 0;
			while(count <= stop){
				checkArray[checkIndex] = mat[count][count];
				checkIndex++;
				count++;
			}
			if(this.isTheSame(checkArray,this.valueAt(i))){
				return true;
			}
			start ++;
			stop ++;
			dif = (stop-start)+1;
		}
		return false;
	}

	public boolean checkSecondDiagonal(CellValue[][] mat, int i){
		CellValue[] checkArray;
		int tmp = i;
		int r = 0, j;
		while(tmp>=this.columns){
			r++;
			tmp = tmp - this.columns;
		}
		j = tmp;
		int tmpIStart = r;
		int tmpJStart = j;

		int tmpIStop = r;
		int tmpJStop = j;
		int kStart = this.sizeWin-1;
		int kStop = this.sizeWin-1;
		while (tmpIStart>0 && tmpJStart<this.columns && kStart>0){
			tmpIStart--;
			tmpJStart++;
			kStart--;
		}

		while (tmpIStop<this.lines && tmpJStop>0 && kStop>0){
			tmpIStop++;
			tmpJStop--;
		}


		int difI = (tmpIStop-tmpIStart)+1;
		int difJ = (tmpJStart-tmpJStop)+1;
		while(difI>=this.sizeWin && difJ>=this.sizeWin){
			checkArray = new CellValue[this.sizeWin];
			int checkIndex = 0;
			int kcounter = this.sizeWin;
			int stmpIStart = tmpIStart;
			int stmpJStart = tmpJStart;
			while(stmpIStart<=tmpIStop && stmpJStart<=tmpJStop && kcounter>0){
				checkArray[checkIndex] = mat[stmpIStart][stmpJStart];
				stmpIStart++;
				stmpJStart--;
				checkIndex++;
				kcounter--;
			}
			if(this.isTheSame(checkArray,this.valueAt(i))){
				return true;
			}
			tmpIStart ++;
			tmpJStart --;
			difI = (tmpIStop-tmpIStart)+1;
			difJ = (tmpJStart-tmpJStop)+1;
		}
		return false;
	}

	public boolean checkNullGame(){
		for(CellValue cell : this.board){
			if(cell == CellValue.EMPTY){
				return false;
			}
		}
		return true;
	}

	public CellValue[][] getCurrentMatBoard(){
		int k = 0;
		CellValue[][] mat = new CellValue[this.lines][this.columns];
		for(int i = 0; i<this.lines; i++){
			for(int j = 0; j<this.columns; j++){
				mat[i][j] = this.board[k];
				k++;
			}
		}
		return mat;
	}

	public void setWinner(CellValue winner){
		switch (winner) {
			case EMPTY:
				this.gameState = GameState.PLAYING;
				break;
			case X:
				this.gameState = GameState.XWIN;
				break;
			case O:
				this.gameState = GameState.OWIN;
				break;
		}
	}

	public boolean isTheSame(CellValue[] arr, CellValue i){
		for(CellValue cell : arr){
			if(cell != i){
				return false;
			}
		}
		return true;
	}

	private String addHut(String affichage,CellValue hut){
		switch(hut){
			case X: return (affichage+"X");
			case O :return (affichage+"O");
			default: return (affichage+" ");
		}
	}
}