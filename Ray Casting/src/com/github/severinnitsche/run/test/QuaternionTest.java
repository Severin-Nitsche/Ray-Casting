package com.github.severinnitsche.run.test;

import com.github.severinnitsche.utilities.math.Quaternion;
import static java.lang.Math.floor;

public class QuaternionTest {
  
  static Quaternion generateQuat() {
    return new Quaternion(floor(Math.random()*100),floor(Math.random()*100),floor(Math.random()*100),floor(Math.random()*100));
  }
  
  public static void main(String[] args) {
  
    System.out.println("addition:");
    for(int i=0; i<10; i++) {
      Quaternion a = generateQuat();
      Quaternion b = generateQuat();
      System.out.print(a+" + "+b+" = ");
      System.out.println(a.add(b));
    }
  
    System.out.println("subtraction:");
    for(int i=0; i<10; i++) {
      Quaternion a = generateQuat();
      Quaternion b = generateQuat();
      System.out.print(a+" - "+b+" = ");
      System.out.println(a.subtract(b));
    }
  
    System.out.println("multiplication:");
    for(int i=0; i<10; i++) {
      Quaternion a = generateQuat();
      Quaternion b = generateQuat();
      System.out.print(a+" * "+b+" = ");
      System.out.println(a.multiply(b));
    }
  
    System.out.println("division:");
    for(int i=0; i<10; i++) {
      Quaternion a = generateQuat();
      Quaternion b = generateQuat();
      System.out.print(a+" : "+b+" = ");
      System.out.println(a.divide(b));
    }
  
    System.out.println("inverse:");
    for(int i=0; i<10; i++) {
      Quaternion a = generateQuat();
      System.out.println("a = "+a);
      System.out.println("a^-1 = "+a.invert());
    }
  }
  
}
