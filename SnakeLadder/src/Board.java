import java.util.concurrent.ThreadLocalRandom;

public class Board {
    Cell[][] cells;

    Board(int boardSize, int numberOfSnakes, int numberOfLadders){
        initializeCells(boardSize);
        addSnakesLadders(cells, numberOfSnakes, numberOfLadders);
    }

    private void initializeCells(int boardSize) {
        cells = new Cell[boardSize][boardSize];

        for(int i=0; i<boardSize; i++){
            for(int j=0; j<boardSize; j++){
                Cell obj = new Cell();
                cells[i][j] = obj;
            }
        }
    }

    private void addSnakesLadders(Cell[][] cells, int numberOfSnakes, int numberOfLadders) {
        while(numberOfSnakes > 0){
            int snakeHead = ThreadLocalRandom.current().nextInt(1,cells.length * cells.length -1);
            int snakeTail = ThreadLocalRandom.current().nextInt(1,cells.length * cells.length -1);
            if(snakeTail >= snakeHead)
                continue;

            Jump snakeObj = new Jump();
            snakeObj.setStart(snakeHead);
            snakeObj.setEnd(snakeTail);

            Cell cell = getCell(snakeHead);
            cell.setJump(snakeObj);

            numberOfSnakes--;
        }

        while(numberOfLadders > 0){
            int ladderStart = ThreadLocalRandom.current().nextInt(1,cells.length * cells.length -1);
            int ladderEnd = ThreadLocalRandom.current().nextInt(1,cells.length * cells.length -1);
            if(ladderEnd >= ladderStart)
                continue;

            Jump ladderObj = new Jump();
            ladderObj.setStart(ladderStart);
            ladderObj.setEnd(ladderEnd);

            Cell cell = getCell(ladderStart);
            cell.setJump(ladderObj);

            numberOfLadders--;
        }
    }

    public Cell getCell(int playerPosition){
        int boardRow = playerPosition / cells.length;
        int boardColumn = playerPosition % cells.length;
        return cells[boardRow][boardColumn];
    }
}
