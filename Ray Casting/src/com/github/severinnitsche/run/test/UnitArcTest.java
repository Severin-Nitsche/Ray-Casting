package com.github.severinnitsche.run.test;

import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Vector;

public class UnitArcTest {
  
  public static void main(String[] args) {
    Vector axis = new Vector(0,0,1);
    Vector[] arc = MathUtil.getVectorsOnUnitArc(100,MathUtil.PI,axis);
    for(Vector v : arc) System.out.println(v);
  }
  
}
