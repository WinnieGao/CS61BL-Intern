// import edu.princeton.cs.algs4.QuickFindUF;
// import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int[][] numberGrid;
    private int N;
    private int topside;
    private int bottomside;
    private int numberofOpenSites;
    private WeightedQuickUnionUF w, h;

    /* Creates an N-by-N grid with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        grid = new int[N][N];
        numberGrid = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                grid[i][j] = 0;
                numberGrid[i][j] = N * i + j;
            }
        }

        w = new WeightedQuickUnionUF(N * N + 2);
        h = new WeightedQuickUnionUF(N * N + 2);
        topside = N * N;
        bottomside = N * N + 1;
        for (int j = 0; j < N; j += 1) {
            w.union(topside, numberGrid[0][j]);
            h.union(topside, numberGrid[0][j]);
            w.union(bottomside, numberGrid[N - 1][j]);
        }
    }

    /* Opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            numberofOpenSites += 1;
        }
        if (row + 1 < N) {
            if (isOpen(row + 1, col)) {
                w.union(numberGrid[row + 1][col], numberGrid[row][col]);
                h.union(numberGrid[row + 1][col], numberGrid[row][col]);
            }
        }
        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                w.union(numberGrid[row - 1][col], numberGrid[row][col]);
                h.union(numberGrid[row - 1][col], numberGrid[row][col]);
            }
        }
        if (col + 1 < N) {
            if (isOpen(row, col + 1)) {
                w.union(numberGrid[row][col + 1], numberGrid[row][col]);
                h.union(numberGrid[row][col + 1], numberGrid[row][col]);
            }
        }
        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                w.union(numberGrid[row][col - 1], numberGrid[row][col]);
                h.union(numberGrid[row][col - 1], numberGrid[row][col]);
            }
        }
    }

    /* Returns true if the site at (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col] == 1;
    }

    /* Returns true if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        return (isOpen(row, col) && h.connected(numberGrid[row][col], topside));
    }

    /* Returns the number of open sites. */
    public int numberOfOpenSites() {
        return numberofOpenSites;
    }

    /* Returns true if the system percolates. */
    public boolean percolates() {
        if (N == 1) {
            return isOpen(0, 0);
        }
        return w.connected(topside, bottomside);
    }

    /* Converts row and column coordinates into a number. This will be helpful
       when trying to tie in the disjoint sets into our NxN grid of sites. */
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }
    /* Returns true if (row, col) site exists in the NxN grid of sites.
       Otherwise, return false. */
    private boolean valid(int row, int col) {
       if (row >=0 && col >=0 && row < N && col < N) {
           return true;
       }
       return false;
    }
}
