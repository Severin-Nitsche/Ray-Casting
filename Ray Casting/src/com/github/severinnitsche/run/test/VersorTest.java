package com.github.severinnitsche.run.test;

import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.math.Versor;

public class VersorTest {
  
  public static void main(String[] args) {
    Vector axis = new Vector(0,0,1);
    Versor versor = new Versor(axis,Math.PI*2);
    Vector test = new Vector(0,1,0);
    Vector t2 = versor.rotate(test);
    System.out.println(t2);
    System.out.println(Math.acos(t2.dot(test)));
  }
  
}
