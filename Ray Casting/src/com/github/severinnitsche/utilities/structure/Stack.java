package com.github.severinnitsche.utilities.structure;

import java.util.Iterator;

public class Stack<V> implements Iterable<V>{
  
  private Node top;
  
  private class Node {
    V value;
    Node below;
    
    Node(V value, Node below) {
      this.value = value;
      this.below = below;
    }
    
    public Node(V value) {
      this(value,null);
    }
    
  }
  
  private class StackIterator implements Iterator<V> {
    
    Stack<V> stack;
    
    StackIterator(Stack<V> stack) {
      this.stack = stack;
    }
  
    @Override
    public boolean hasNext() {
      return stack.top!=null;
    }
  
    @Override
    public V next() {
      return stack.pop();
    }
  }
  
  public Stack(){}
  
  public Stack(V...values) {
    push(values);
  }
  
  public V pop() {
    V temp = top.value;
    top = top.below;
    return temp;
  }
  
  public void push(V...values) {
    for(V value : values) top = new Node(value,top);
  }
  
  @Override
  public Iterator<V> iterator() {
    return new StackIterator(this);
  }
  
}
