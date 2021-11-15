package com.nikolasnorth.dht;

import java.util.List;

public final class DistributedHashTable<K, V> {

  private List<Node<K, V>> table;

  private List<Integer> fingerTable;

  private final static class Node<K, V> {

    private K key;

    private V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }
}
