package com.nikolasnorth.dht;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Node<K, V> {

  // Java's Object.hashCode() will be the consistent hash function used to create keys. The implementation of hashCode()
  // returns a 32-bit integer, thus representing the number of bits in the keyspace.
  private final static int m = 32;

  private final Config config;

  private final List<Resource<K, V>> storage;

  private final List<FingerEntry<K, V>> finger;

  private Node<K, V> predecessor;

  // Reference to the 0-th entry in the finger table
  private FingerEntry<K, V> successor;

  public Node(String host, int port) {
    config = new Config(host, port);
    storage = new ArrayList<>();
    finger = new ArrayList<>(m);
    predecessor = null;
    successor = null;
  }

  /**
   * Creates a new node and adds it to the cluster. Returns the newly inserted node.
   *
   * @param host New node's host
   * @param port New node's port number
   * @return Newly inserted node
   */
  public Node<K, V> addNode(String host, int port) {
    final Node<K, V> node = new Node<>(host, port);

    return node;
  }

  /**
   * Returns the node that directly maps to the given id, or immediately succeeds it.
   *
   * @param id node identifier
   * @return id's successor node
   */
  Node<K, V> findSuccessor(int id) {
    return null;
    /**
     * n' := findPredecessor(id)
     * return n'.successor
     */
  }

  /**
   * Returns the node that immediately precedes the given key.
   *
   * @param id node identifier
   * @return id's preceding node
   */
  Node<K, V> findPredecessor(int id) {
    return null;
    /**
     * n' := this
     * while id is not between nodes n' and n'.successor do:
     *    n' := n'.closestPrecedingFinger(id);
     * return n'
     */
  }

  /**
   * Returns id's closest preceding node in the finger table.
   *
   * @param id node identifier
   * @return id's closest preceding node in the finger table
   */
  Node<K, V> closestPrecedingFinger(int id) {
    for (int i = m - 1; i >= 0; i--) {
      if (finger.get(i).isBetween(this.config.id, id)) {
        return finger.get(i).node;
      }
    }
    return this;
  }

  /**
   * Returns the value stored at the given key. Returns null if the value does not exist.
   *
   * @param key Search key
   * @return Value stored at key
   */
  Optional<V> get(String key) {
    return Optional.empty();
  }

  /**
   * Stores the given document in inside the distributed hash table. Overwrites any existing value stored at the given
   * document key.
   *
   * @param document Document to be stored
   */
  void put(Resource<K, V> document) {
  }

  private final static class Config {

    private final String host;

    private final int port;

    private final int id;

    private Config(String host, int port) {
      this.host = host;
      this.port = port;
      this.id = host.hashCode();
    }
  }

  private final static class Resource<K, V> {
    private K key;
    private V value;
  }

  private final static class FingerEntry<K, V> {

    private int id;

    private Node<K, V> node;

    /**
     * Checks if this finger entry's id is strictly between id's a and b.
     * @param a Chord key
     * @param b Chord key
     * @return true if between a and b, false otherwise
     */
    public boolean isBetween(int a, int b) {
      if (a < b) {
        return a < id && b >= id;
      } else if (a > b) {
        return a < id || b >= id;
      } else {
        return a < id || a > id;
      }
    }
  }
}
