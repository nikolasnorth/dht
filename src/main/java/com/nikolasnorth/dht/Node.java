package com.nikolasnorth.dht;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class Node<K, V> {

  // SHA-1 is the base hashing function for consistent hashing and maps to a keyspace of 160 bits.
  private final static int m = 160;

  private final String ipAddress;

  // Decimal string representation of an SHA-1 hash of the IP address.
  private final String key;

  private final List<Resource<K, V>> storage;

  private final List<Finger> finger;

  private Finger predecessor;

  // Reference to the 0-th entry in the finger table
  private Finger successor;

  private static String hashOf(Object key) {

    return "";
  }

  /**
   * Create a new Chord ring.
   *
   * @param host host name
   * @param port port number
   */
  public Node(String host, int port) {
    this.ipAddress = String.format("%s:%d", host, port);
    this.key = Util.sha1HashOf(this.ipAddress);
    this.storage = new ArrayList<>();
    this.finger = new ArrayList<>(m);
    Finger toItself = new Finger(key, ipAddress);
    for (int i = 0; i < m; i++) {
      finger.add(i, toItself);
    }
    this.predecessor = toItself;
    this.successor = this.finger.get(0);

    try {
      final HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
      server.start();
      System.out.printf("Server listening on http://%s:%d%n", host, port);

    } catch (IOException e) {
      System.err.println("Could not start server.");
    }
  }

  /**
   * Add a node to the Chord ring.
   *
   * @param host     host name
   * @param port     port number
   * @param existing existing node in the Chord ring
   */
  public Node(String host, int port, Node<K, V> existing) {
    this.ipAddress = String.format("%s:%d", host, port);
    this.key = Util.sha1HashOf(this.ipAddress);
    this.storage = new ArrayList<>();
    this.finger = new ArrayList<>(m);
    Finger toItself = new Finger(key, ipAddress);
    for (int i = 0; i < m; i++) {
      finger.add(i, toItself);
    }
    this.predecessor = toItself;
    this.successor = this.finger.get(0);

    try {
      final HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
      server.start();
      System.out.printf("Server listening on http://%s:%d%n", host, port);

    } catch (IOException e) {
      System.err.println("Could not start server.");
    }
  }

  /**
   * Returns the IP address of the node that directly maps to the given Chord key, or immediately succeeds it.
   *
   * @param key Chord key in decimal form
   * @return IP address of successor node
   */
  public String findSuccessor(String key) {
    BigInteger bigKey = new BigInteger(key), bigSuccessorKey = new BigInteger(successor.key);
    if (Util.strictlyBetween(key, this.key, successor.key) || bigKey.compareTo(bigSuccessorKey) == 0) {  // key in range (this.key, successor.key]
      return successor.ipAddress;
    }
    String closestPrecedingIpAddress = closestPrecedingFinger(key);
    return null;
  }

  /**
   * Returns the IP address for the node that immediately precedes the given Chord key.
   *
   * @param key Chord key
   * @return IP address for key's preceding node
   */
  public String findPredecessor(long key) {
    return null;
  }

  /**
   * Returns the IP address for the given Chord key's closest preceding node in the finger table.
   *
   * @param key Chord key
   * @return ip address for the key's closest preceding node in the finger table
   */
  public String closestPrecedingFinger(String key) {
    for (int i = m - 1; i >= 0; i--) {
      if (Util.strictlyBetween(finger.get(i).key, this.key, key)) {  // finger.key in range (this.key, key)
        return finger.get(i).ipAddress;
      }
    }
    return this.ipAddress;
  }

  private final static class Resource<K, V> {
    private final K key;
    private final V value;

    public Resource(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private final static class Finger {
    private final String key;
    private final String ipAddress;

    private Finger(String key, String ipAddress) {
      this.key = key;
      this.ipAddress = ipAddress;
    }
  }
}
