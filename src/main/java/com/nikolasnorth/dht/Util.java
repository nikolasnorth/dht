package com.nikolasnorth.dht;

public final class Util {

  /**
   * Checks if key is strictly between key's a and b.
   *
   * @param a Chord key
   * @param b Chord key
   * @return true iff strictly between, false otherwise
   */
  public static boolean strictlyBetween(int key, int a, int b) {
    if (a < b) {
      return a < key && b >= key;
    } else if (a > b) {
      return a < key || b >= key;
    } else {
      return a < key || a > key;
    }
  }
}
