import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create n-by-n grid, with all sites blocked
    private WeightedQuickUnionUF quickUnion;
    private boolean[][] percolationSystem;
    private int numbOpenSite = 0;

    private int virtualTop;
    private int virtualBottom;
    private int gridSize;
    private final static int GRID_SHIFT_INDEX = 1;
    private int NUMB_VIRTUAL_SITES;


    public Percolation(int n) {
        gridSize = n;
        NUMB_VIRTUAL_SITES = 2 + n;
        quickUnion = new WeightedQuickUnionUF(gridSize * gridSize + NUMB_VIRTUAL_SITES);
        percolationSystem = new boolean[gridSize + n][gridSize];
        virtualTop = 0;
        virtualBottom = gridSize * gridSize + 1;
    }

    public void open(int row, int col) {

        if (isOpen(row, col)) {
            return;
        }

        numbOpenSite++;
        percolationSystem[row - GRID_SHIFT_INDEX][col - GRID_SHIFT_INDEX] = true;

        // MAPPING 2d to 1d of currently opened site
        int index1D = (row - GRID_SHIFT_INDEX) * gridSize + (col);

        connectXY(row - 1, col, index1D);
        connectXY(row, col + 1, index1D);
        connectXY(row + 1, col, index1D);
        connectXY(row, col - 1, index1D);


        if (row == 1) {
            quickUnion.union(virtualTop, index1D);
        }
        if (row == gridSize) {
            quickUnion.union(index1D, virtualBottom++);
        }
        // quickUnion
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if (row - GRID_SHIFT_INDEX < 0 || col - GRID_SHIFT_INDEX < 0
                || row - GRID_SHIFT_INDEX >= gridSize || col - GRID_SHIFT_INDEX >= gridSize) {

            throw new java.lang.IndexOutOfBoundsException(
                    "Index is out of bounds");
        }
        return percolationSystem[row - GRID_SHIFT_INDEX][col - GRID_SHIFT_INDEX];

    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        if (row - GRID_SHIFT_INDEX < 0 || col - GRID_SHIFT_INDEX < 0
                || row - GRID_SHIFT_INDEX >= gridSize || col - GRID_SHIFT_INDEX >= gridSize) {
            throw new java.lang.IndexOutOfBoundsException(
                    "Index is out of bounds");
        }
        int index1D = ((row - GRID_SHIFT_INDEX) * gridSize) + col;
        return quickUnion.connected(index1D, virtualTop);
    } // is site (row, col) full?


    public int numberOfOpenSites() {
        return numbOpenSite;
    }      // number of open sites

    public boolean percolates() {
        for (int i = 1; i <= gridSize; i++) {
            if (quickUnion.connected(virtualTop, (gridSize * gridSize + i))) {
                return true;
            }
        }
        return false;
    }          // does the system percolate?

    private void connectXY(int row, int col, int index1D) {
        try {
            // right
            if (isOpen(row, col)) {
                int index1DRight = (row - GRID_SHIFT_INDEX) * gridSize + (col);
                quickUnion.union(index1D, index1DRight);
            }
        }
        catch (IndexOutOfBoundsException e) {

        }
    }
    
    public static void main(String[] args) {

    } // test client (optional)
}
