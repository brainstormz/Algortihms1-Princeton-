import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private int trials;
    private double[] trialResults;
    private Percolation percolation;

    public PercolationStats(int n,
                            int t)    // perform trials independent experiments on an n-by-n grid
    {
        // if either n ≤ 0 or trials ≤ 0
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        trials = t;
        trialResults = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            int count = 0;
            percolation = new Percolation(size);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, size + 1);
                int col = StdRandom.uniform(1, size + 1);
                percolation.open(row, col);
            }

            count = percolation.numberOfOpenSites();
            // System.out.println(trial);
            trialResults[trial] = (double) count / (double) (size * size);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(trialResults);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(trialResults);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();
        double confidenceLo = mean - (1.96 * stddev / Math.sqrt(trialResults.length));
        return confidenceLo;
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();
        double confidenceLo = mean + (1.96 * stddev / Math.sqrt(trialResults.length));
        return confidenceLo;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);
        System.out.printf("mean                     = %f\n", percolationStats.mean());
        System.out.printf("stddev                   = %f\n", percolationStats.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n",
                          percolationStats.confidenceLo(), percolationStats.confidenceHi());

    }
}
