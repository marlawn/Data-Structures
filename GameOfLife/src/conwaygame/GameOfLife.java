package conwaygame;

import java.util.ArrayList;

/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many
 * iterations/generations.
 *
 * Rules
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.
 * 
 * @author Seth Kelley
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    private boolean[][] grid; // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
     * Default Constructor which creates a small 5x5 grid with five alive cells.
     * This variation does not exceed bounds and dies off after four iterations.
     */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
     * Constructor used that will take in values to create a grid with a given
     * number
     * of alive cells
     * 
     * @param file is the input file with the initial game pattern formatted as
     *             follows:
     *             An integer representing the number of grid rows, say r
     *             An integer representing the number of grid columns, say c
     *             Number of r lines, each containing c true or false values (true
     *             denotes an ALIVE cell)
     */
    public GameOfLife(String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                grid[i][j] = StdIn.readBoolean();
            }
        }
    }

    /**
     * Returns grid
     * 
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid() {
        return grid;
    }

    /**
     * Returns totalAliveCells
     * 
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells() {

        return totalAliveCells;

    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * 
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState(int row, int col) {
        boolean state = false;
        if (grid[row][col] == true) {
            state = true;
        }
        return state; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * 
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive() {
        boolean state = false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ALIVE)
                    state = true;
            }
        }

        return state;
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors(int row, int col) {

        int iteration = 0;
        int count = 0;
        int nR, nC;

        for (int i = 0; i < 3; i++) {
            nR = row - 1 + i;
            if (nR < 0) {
                nR = grid.length - 1;
            } else if (nR > grid.length - 1) {
                nR = 0;
            }
            for (int j = 0; j < 3; j++) {
                nC = col - 1 + j;
                if (nC < 0) {
                    nC = grid[0].length - 1;
                } else if (nC > grid[0].length - 1) {
                    nC = 0;
                }

                iteration++;

                if (iteration != 5 && grid[nR][nC] == true) {
                    count++;
                }

            }
        }

        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid() {

        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < newGrid.length; i++) {
            for (int j = 0; j < newGrid[0].length; j++) {
                if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) < 2) {
                    newGrid[i][j] = false;
                } else if (grid[i][j] == DEAD && numOfAliveNeighbors(i, j) == 3) {
                    newGrid[i][j] = true;
                } else if (grid[i][j] == ALIVE && (numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3)) {
                    newGrid[i][j] = true;
                } else if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) > 3) {
                    newGrid[i][j] = false;
                }
            }
        }

        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration() {

        grid = computeNewGrid();
        int temp = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ALIVE)
                    temp++;
            }
        }

        totalAliveCells = temp;

    }

    /**
     * Updates the current grid with the grid computed after multiple (n)
     * generations.
     * 
     * @param n number of iterations that the grid will go through to compute a new
     *          grid
     */
    public void nextGeneration(int n) {

        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < newGrid.length; i++) {
                for (int j = 0; j < newGrid[0].length; j++) {
                    if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) < 2) {
                        newGrid[i][j] = false;
                    } else if (grid[i][j] == DEAD && numOfAliveNeighbors(i, j) == 3) {
                        newGrid[i][j] = true;
                    } else if (grid[i][j] == ALIVE
                            && (numOfAliveNeighbors(i, j) == 2 || numOfAliveNeighbors(i, j) == 3)) {
                        newGrid[i][j] = true;
                    } else if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) > 3) {
                        newGrid[i][j] = false;
                    }
                }
            }
            for (int i = 0; i < newGrid.length; i++) {
                for (int j = 0; j < newGrid[0].length; j++) {
                    grid[i][j] = newGrid[i][j];
                }
            }
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * 
     * @return the number of communities in the grid, communities can be formed from
     *         edges
     */
    public int numOfCommunities() {

        int communities = 0;
        WeightedQuickUnionUF mar = new WeightedQuickUnionUF(grid.length, grid[0].length);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ALIVE && (mar.find(i, j) == (i * grid[0].length + j))) {
                    if (numOfAliveNeighbors(i, j) == 0) {
                        communities++;
                    } else if (numOfAliveNeighbors(i, j) > 0) {

                        int nR, nC, iteration;
                        iteration = 0;
                        for (int x = 0; x < 3; x++) {
                            nR = i - 1 + x;
                            if (nR < 0) {
                                nR = grid.length - 1;
                            } else if (nR > grid.length - 1) {
                                nR = 0;
                            }
                            for (int y = 0; y < 3; y++) {
                                nC = j - 1 + y;
                                if (nC < 0) {
                                    nC = grid[0].length - 1;
                                } else if (nC > grid[0].length - 1) {
                                    nC = 0;
                                }
                                iteration++;

                                if (iteration != 5 && grid[nR][nC] == true) {
                                    mar.union(nR, nC, i, j);
                                }
                            }
                        }
                    }
                }
            }
        }

        // count the nodes with different parents
        int[] temp = new int[100];
        int count = 0;

        // puts all the parents of the ALIVE, neighbored nodes into one array
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ALIVE && numOfAliveNeighbors(i, j) > 0) {
                    temp[count] = mar.find(i, j);
                    count++;
                }
            }
        }

        int res = 1;

        // Pick all elements one by one
        for (int i = 1; i < temp.length; i++) {
            int j = 0;
            for (j = 0; j < i; j++)
                if (temp[i] == temp[j])
                    break;

            // If not printed earlier,
            // then print it
            if (i == j)
                res++;
        }
        res = res - 1;
        communities = communities + res;

        return communities; // update this line, provided so that code compiles
    }
}
