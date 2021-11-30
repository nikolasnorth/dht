package com.nikolasnorth.dht;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Node<K, V> {

  // Number of bits in the keyspace since Java's implementation of Object.hashCode() returns a 32-bit integer.
  private final static int m = 32;

  private Config config;

  private final List<Document<K, V>> storage;

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
   * Returns the node that directly maps to the given id, or immediately succeeds it.
   *
   * @param id node identifier
   * @return id's successor node
   */
  Node<K, V> findSuccessor(int id) {
    return null;
  }

  /**
   * Returns the node that immediately precedes the given key.
   *
   * @param id node identifier
   * @return id's preceding node
   */
  Node<K, V> findPredecessor(int id) {
    return null;
  }

  /**
   * Returns id's closest preceding node in the finger table.
   *
   * @param id node identifier
   * @return id's closest preceding node in the finger table
   */
  Node<K, V> closestPrecedingFinger(int id) {
    return null;
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
  void put(Document<K, V> document) {
  }

  private final static class Config {
    private String host;
    private int port;

    private Config(String host, int port) {
      this.host = host;
      this.port = port;
    }
  }

  private final static class Document<K, V> {
    private K key;
    private V value;
  }

  private final static class FingerEntry<K, V> {
    private String id;
    private Node<K, V> node;
  }
}
