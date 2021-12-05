package com.nikolasnorth.dht;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Util {

  /**
   * Checks if key is strictly between key's a and b.
   *
   * @param a Chord key
   * @param b Chord key
   * @return true iff strictly between, false otherwise
   */
  public static boolean strictlyBetween(String key, String a, String b) {
    BigInteger bigKey = new BigInteger(key), bigA = new BigInteger(a), bigB = new BigInteger(b);
    if (bigA.compareTo(bigB) < 0) {
      return bigA.compareTo(bigKey) < 0 && bigB.compareTo(bigKey) >= 0;
    } else if (a.compareTo(b) > 0) {
      return bigA.compareTo(bigKey) < 0 || bigB.compareTo(bigKey) >= 0;
    } else {
      return bigA.compareTo(bigKey) != 0;
    }
  }

  /**
   * Returns the decimal string representation of an SHA-1 hash of the given key.
   *
   * @param key key to be hashed
   * @return SHA-1 hash of key in decimal form
   */
  public static String sha1HashOf(String key) {
    try {
      MessageDigest msgDigest = MessageDigest.getInstance("SHA-1");
      msgDigest.update(key.getBytes(StandardCharsets.UTF_8), 0, key.length());
      return new BigInteger(msgDigest.digest()).toString();

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error creating SHA-1 hash.");
    }
  }
}
