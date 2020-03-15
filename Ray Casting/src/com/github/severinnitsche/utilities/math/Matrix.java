package com.github.severinnitsche.utilities.math;

import com.github.severinnitsche.utilities.math.exceptions.MatrixConversionException;

public class Matrix {
  
  protected double[][] self;
  
  private Matrix(int i, int j) {
    self = new double[i][j];
  }
  
  public Matrix(double[][] m) {
    this.self = new double[m.length][m[0].length];
    System.arraycopy(m,0,this.self,0,m.length);
  }
  
  public Matrix(Vector e1, Vector e2, Vector e3, Point o) {
    double[][] m = new double[4][4];
    for(int i=0; i<3; i++) {
      m[i][0] = e1.get(i);
      m[i][1] = e2.get(i);
      m[i][2] = e3.get(i);
      m[i][3] = o.get(i);
    }
    m[3][3] = 1;
    this.self = m;
  }
  
  public Vector asVector() throws MatrixConversionException {
    if(this.self.length == 4) {
      if(self[3][0]==1) throw new MatrixConversionException(MatrixConversionException.ExceptionType.Point);
      return new Vector(self[0][0],self[1][0],self[2][0]);
    } else if(this.self[0].length == 4) {
      if(self[0][3]==1) throw new MatrixConversionException(MatrixConversionException.ExceptionType.Point);
      return new Vector(self[0][0],self[0][1],self[0][2]);
    }
    throw new MatrixConversionException(MatrixConversionException.ExceptionType.Dimensions);
  }
  
  public Point asPoint() throws MatrixConversionException {
    if(this.self.length == 4) {
      if(self[3][0]==0) throw new MatrixConversionException(MatrixConversionException.ExceptionType.Vector);
      return new Point(self[0][0],self[1][0],self[2][0]);
    } else if(this.self[0].length == 4) {
      if(self[0][3]==0) throw new MatrixConversionException(MatrixConversionException.ExceptionType.Vector);
      return new Point(self[0][0],self[0][1],self[0][2]);
    }
    throw new MatrixConversionException(MatrixConversionException.ExceptionType.Dimensions);
  }
  
  public double get(int i, int j) {
    return self[i][j];
  }
  
  public int getRows() {
    return self.length;
  }
  
  public int getColumns() {
    return self[0].length;
  }
  
  public Matrix multiply(Matrix m) {
    Matrix res = new Matrix(getRows(),m.getColumns());
    for(int i=0; i<res.getRows(); i++) {
      for(int j=0; j<res.getColumns(); j++) {
        double sum = 0;
        for(int k=0; k<getColumns(); k++) {
          sum += self[i][k] * m.self[k][j];
        }
        res.self[i][j] = sum;
      }
    }
    return res;
  }
  
  public Vector translate(Vector v) throws MatrixConversionException {
    if(getRows()!=4) throw new IllegalStateException("Matrix must be 4x4! Issue: rows");
    if(getColumns()!=4) throw new IllegalStateException("Matrix must be 4x4! Issue: cols");
    
    return this.multiply(v.asColumn()).asVector();
  }
  
  public Point translate(Point v) throws MatrixConversionException {
    if(getRows()!=4) throw new IllegalStateException("Matrix must be 4x4! Issue: rows");
    if(getColumns()!=4) throw new IllegalStateException("Matrix must be 4x4! Issue: cols");
    
    return this.multiply(v.asColumn()).asPoint();
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < self.length; i++) {
      builder.append("\t|");
      for(int j=0; j<self[i].length; j++) {
        builder.append(self[i][j]);
        if(j!=self[i].length-1) builder.append("\t");
      }
      builder.append("|");
      if(i!=self.length-1) builder.append(System.lineSeparator());
    }
    return builder.toString();
  }
  
}
