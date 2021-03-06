/**
 * Solution to the Narrow Art Gallery problem from the 2014 ICPC North America Qualifier
 *
 * <p>Problem: https://open.kattis.com/problems/narrowartgallery
 *
 * <p>Problem Author: Robert Hochberg
 *
 * <p>Solution by: William Fiset
 */
import java.util.Scanner;

public class NarrowArtGalleryRecursive {

  // A large enough infinity value for this problem
  static final int INF = 1000000;

  static int[][] gallery;
  static Integer[][][] dp;

  static final int LEFT = 0;
  static final int RIGHT = 1;

  static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values) if (v < m) m = v;
    return m;
  }

  public static int f(int n, int k) {
    // Compute the optimal value of ending at the top LEFT and the top RIGHT of
    // the gallery and return the minimum.
    return min(f(n, k, LEFT), f(n, k, RIGHT));
  }

  // f(n,k,c) Computes the minimum value you can save by closing off `k` rooms
  // in a gallery with `n` levels starting on side `c`.
  //
  // n = The gallery row index
  // k = The number of rooms the curator needs to close
  // c = The column, either LEFT (= 0) or RIGHT (= 1)
  public static int f(int n, int k, int c) {
    // We finished closing all K rooms
    if (k == 0) {
      return 0;
    }
    if (n < 0) {
      return INF;
    }
    // Return the value of this subproblem, if it's already been computed.
    if (dp[n][k][c] != null) {
      return dp[n][k][c];
    }
    // Get the value of the current room at row `n` and column `c`.
    int roomValue = gallery[n][c];
    return dp[n][k][c] =
        min(
            // Close the current room, and take the best value from the partial
            // state directly below the current room.
            f(n - 1, k - 1, c) + roomValue,

            // Invalid diagonal case would block pathway
            // f(n - 1, k - 1, c ^ 1) + roomValue,

            // Don't include the current room. Instead, take the last best value from
            // the previously calculated partial state which includes `k` rooms closed.
            f(n - 1, k, c ^ 1),
            f(n - 1, k, c));
  }

  public static void main(String[] Fiset) {
    Scanner sc = new Scanner(System.in);
    while (true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[N][K + 1][2];

      int sum = 0;
      for (int i = 0; i < N; i++) {
        // Input the gallery values in reverse to simulate walking from the
        // bottom to the top of the gallery. This makes debugging easier and
        // shouldn't affect the final result.
        int index = N - i - 1;
        gallery[index][LEFT] = sc.nextInt();
        gallery[index][RIGHT] = sc.nextInt();
        sum += gallery[index][LEFT] + gallery[index][RIGHT];
      }

      // System.out.printf("%d\n", sum - f(N - 1, K));
      // System.out.printf("%d\n", f(N - 1, K));
      // System.out.printf("%d\n", f(3, 2, RIGHT));
      System.out.printf("%d\n", f(/*n=*/ 3, /*k=*/ 3, RIGHT));
    }
  }
}
