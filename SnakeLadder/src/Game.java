import java.util.Deque;
import java.util.LinkedList;

public class Game {
    Board board;
    Dice dice;
    Deque<Player> playersList = new LinkedList<>();
    Player winner;

    public Game(){
        initializeGame();
    }

    private void initializeGame() {
        board = new Board(10,5,4);
        dice = new Dice(1);
        winner = null;
        addPlayers();
    }

    private void addPlayers(){
        Player player1 = new Player(1, 0);
        Player player2 = new Player(2,0);
        playersList.add(player1);
        playersList.add(player2);
    }

    public void startGame(){
        while(winner == null){

            // Check whose turn now
            Player playerTurn = findPlayerTurn();
            System.out.println("Player Turn Is " + playerTurn.getId() + " Current Position Is: " + playerTurn.getCurrentPosition());

            // Roll the dice
            int diceNumber = dice.rollDice();

            // Get the new position
            int playerNewPosition = playerTurn.currentPosition + diceNumber;
            playerNewPosition = jumpCheck(playerNewPosition);
            playerTurn.setCurrentPosition(playerNewPosition);
            System.out.println("Player Turn Is " + playerTurn.getId() + " New Position Is: " + playerNewPosition);

            // Check for winning condition
            if(playerNewPosition == board.cells.length * board.cells.length - 1)
                winner = playerTurn;
        }
        System.out.println("WINNER IS PLAYER : " + winner.getId());
    }

    private int jumpCheck(int playerNewPosition) {
        if(playerNewPosition > board.cells.length * board.cells.length - 1)
            return  playerNewPosition;

        Cell cell = board.getCell(playerNewPosition);
        if(cell.getJump() != null && cell.getJump().getStart() == playerNewPosition){
            String jumpBy = (cell.getJump().getStart() < cell.getJump().getEnd() ? "Ladder" : "Snake");
            System.out.println("Jump Done By " + jumpBy);
            return cell.getJump().getEnd();
        }
        return playerNewPosition;
    }

    private Player findPlayerTurn() {
        Player playerTurn  = playersList.removeFirst();
        playersList.addLast(playerTurn);
        return playerTurn;
    }
}
