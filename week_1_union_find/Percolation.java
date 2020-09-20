package week_1_union_find;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

 private final int virtualTop;
 private final int virtualBottom;
 private final int n;
 private final WeightedQuickUnionUF connectedSitesUF;
 private final WeightedQuickUnionUF filledSitesUF; // Solution to backwash problem
 private boolean[][] isOpen;
 private int numberOfOpenSites;

 // creates n-by-n grid, with all sites initially blocked
 public Percolation(int n) {

  // Corner cases
  if (n <= 0)
   throw new IllegalArgumentException("N must be greater than 0.");
  this.n = n;

  isOpen = new boolean[n + 1][n + 1];

  connectedSitesUF = new WeightedQuickUnionUF(n * n + 2); // including the virtual Top and Bottom (grids + 1 + 1)
  filledSitesUF = new WeightedQuickUnionUF(n * n + 1); // including the virtual Top only so (grids + 1)
  virtualTop = n * n;
  virtualBottom = n * n + 1;

  // Connecting the top sites to virtual Top and Bottom sites to virtual Bottom
  for (int i = 0; i < n; i++) {
   connectedSitesUF.union(virtualTop, i);
   connectedSitesUF.union(virtualBottom, (n - 1) * n + i);

   // Connecting filled sites to virtual Top, but not to the virtual Bottom to
   // address the backwash problem
   filledSitesUF.union(virtualTop, i);
  }
 }

 public void open(int row, int col) {

  validateIndices(row, col);

  if (!isOpen[row][col]) {
   isOpen[row][col] = true;
   numberOfOpenSites++;
  }

  // connect the possible neighbor sites for a single grid cell
  if (row - 1 > 0 && isOpen[row - 1][col]) {
   connectedSitesUF.union(xy2dIndices(row, col), xy2dIndices(row - 1, col));
   filledSitesUF.union(xy2dIndices(row, col), xy2dIndices(row - 1, col));
  }

  if (row + 1 <= n && isOpen[row + 1][col]) {
   connectedSitesUF.union(xy2dIndices(row, col), xy2dIndices(row + 1, col));
   filledSitesUF.union(xy2dIndices(row, col), xy2dIndices(row + 1, col));
  }

  if (col - 1 > 0 && isOpen[row][col - 1]) {
   connectedSitesUF.union(xy2dIndices(row, col), xy2dIndices(row, col - 1));
   filledSitesUF.union(xy2dIndices(row, col), xy2dIndices(row, col - 1));
  }

  if (col + 1 <= n && isOpen[row][col + 1]) {
   connectedSitesUF.union(xy2dIndices(row, col), xy2dIndices(row, col + 1));
   filledSitesUF.union(xy2dIndices(row, col), xy2dIndices(row, col + 1));
  }

 }

 // opens a new site if the correct cases are met
 public boolean isOpen(int row, int col) {
  validateIndices(row, col);
  return isOpen[row][col];
 }

 // is the site (row, col) full?
 public boolean isFull(int row, int col) {
  validateIndices(row, col);
  return isOpen[row][col] && filledSitesUF.find(xy2dIndices(row, col)) == filledSitesUF.find(virtualTop);
 }

 // Helper method to check for rows and columns
 private void validateIndices(int row, int col) {
  if (row <= 0 || row > n)
   throw new IllegalArgumentException("Row index out of bounds");
  if (col <= 0 || col > n)
   throw new IllegalArgumentException("Row index out of bounds");
 }

 // returns the number of open sites
 public int numberOfOpenSites() {
  return numberOfOpenSites;
 }

 // does the system percolate?
 public boolean percolates() {

  if (n == 1)
   return isOpen(1, 1);
  return connectedSitesUF.find(virtualTop) == connectedSitesUF.find(virtualBottom);
 }

 // Another helper method to map 2D indices to 1D indices
 private int xy2dIndices(int row, int col) {
  return (row - 1) * n + (col - 1);
 }

//    test client (optional)
//    public static void main(String[] args) {
//    }
}