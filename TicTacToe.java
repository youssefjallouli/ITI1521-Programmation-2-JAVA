
import java.io.Console;
import java.util.Scanner;

public class TicTacToe{

    /**
     * <b>main</b> of the application. Creates the instance of  TicTacToeGame
     * and starts the game. If two parameters lines  and columns
     * are passed, they are used. If the paramters lines, columns
     * and win are passed, they are used.
     * Otherwise, a default value is used. Defaults values (3) are also
     * used if the paramters are too small (less than 2).
     * Here, we assume that the command lines arguments are indeed integers
     *
     * @param args
     *            command lines parameters
     */
    public static void main(String[] args) {

        StudentInfo.display();

        Console console = System.console();
        TicTacToeGame game;
        int lines, columns, win;
        lines = 3;
        columns = 3;
        win = 3;

        if (args.length >= 2) {
            lines = Integer.parseInt(args[0]);
            if(lines<2){
                System.out.println("Invalid argument, using default...");
                lines = 3;
            }
            columns = Integer.parseInt(args[1]);
            if(columns<2){
                System.out.println("Invalid argument, using default...");
                columns = 3;
            }
        }
        if (args.length >= 3){
            win = Integer.parseInt(args[2]);
            if(win<2){
                System.out.println("Invalid argument, using default...");
                win = 3;
            }
        }
        if (args.length > 3){
            System.out.println("Too many arguments. Only the first 3 are used.");
        }

        game = new TicTacToeGame(lines, columns,win);
        System.out.println(game.toString());

        while(game.getGameState()!=GameState.DRAW){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Jouer "+game.nextCellValue()+" PEUX JOUER");
            int i = scanner.nextInt();
            game.play(i);
            switch (game.getGameState()){
                case XWIN:
                    System.out.println("Le joeur X gagne");
                    break;
                case OWIN:
                    System.out.println("Le joeur Y gagne");
                    break;
                default:
                    break;
            }
            System.out.println(game.toString());
        }


    }

}