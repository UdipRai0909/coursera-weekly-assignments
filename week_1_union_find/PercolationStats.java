package week_1_union_find;


//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

 private static final double CONS_VAR = 1.96;
 private final double[] probes;

 // perform independent trials on an n-by-n grid
 public PercolationStats(int n, int trials) {

  // Throw an IllegalArgumentException in the CONS_VARructor if either n <= 0
  // or
  // trials <= 0.
  if (n <= 0)
   throw new IllegalArgumentException("N must be greater than 0.");
  if (trials <= 0)
   throw new IllegalArgumentException("Trials must be greater than 0");

  probes = new double[trials];

  for (int i = 0; i < trials; i++) {
   Percolation perc = new Percolation(n);
   while (!perc.percolates()) {
    int row = StdRandom.uniform(n) + 1;
    int col = StdRandom.uniform(n) + 1;
    if (!perc.isOpen(row, col)) {
     perc.open(row, col);
    }
   }
   probes[i] = (double) perc.numberOfOpenSites() / (n * n);
  }
 }

 // sample mean of percolation threshold
 public double mean() {
  return StdStats.mean(probes);
 }

 // sample standard deviation of percolation threshold
 public double stddev() {
  return StdStats.stddev(probes);
 }

 // low endpoint of 95% confidence interval
 public double confidenceLo() {
  return (mean() - CONS_VAR * stddev() / Math.sqrt(probes.length));
 }

 // high endpoint of 95% confidence interval
 public double confidenceHi() {
  return (mean() + CONS_VAR * stddev() / Math.sqrt(probes.length));
 }

 // test client (see below)
 public static void main(String[] args) {

  if (args.length < 2) {
   throw new IllegalArgumentException();
  }

  int n = Integer.parseInt(args[0]);
  int trials = Integer.parseInt(args[1]);

  PercolationStats stats = new PercolationStats(n, trials);

  // System.out.printf("Percolation threshold for a %d x %d system:\n", n, n);
//  System.out.printf("%s %20s %f", "mean", "=", stats.mean());
//  System.out.println("");
//  System.out.printf("%s %18s %.17f", "stddev", "=", stats.stddev());
//  System.out.println("");
//  System.out.printf("%s %s [%.15f, %.15f]", "95% confidence interval", "=", stats.confidenceHi(),
//    stats.confidenceLo());
//  System.out.println("");

  StdOut.println("mean                    = " + Double.toString(stats.mean()));
  StdOut.println("stddev                  = " + Double.toString(stats.stddev()));
  StdOut.println("95% confidence interval = [" + Double.toString(stats.confidenceLo()) + ", "
    + Double.toString(stats.confidenceHi()) + "]");
 }
}
