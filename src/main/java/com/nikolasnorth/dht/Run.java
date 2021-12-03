package com.nikolasnorth.dht;

public class Run {

  public static void main(String[] args) {
    try {
      if (args.length != 2) {
        throw new IllegalArgumentException("Port number must be specified.");
      }
      final Node<String, Integer> node = new Node<>("localhost", Integer.parseInt(args[1]));

    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        System.err.println("Invalid port number given.");
      } else {
        System.err.println(e.getMessage());
      }
    }
  }
}
