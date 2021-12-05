package com.nikolasnorth.dht;

public class Run {

  public static void main(String[] args) {
    final var node1 = new Node<String, Integer>("localhost", Integer.parseInt(args[1]));
  }
}
