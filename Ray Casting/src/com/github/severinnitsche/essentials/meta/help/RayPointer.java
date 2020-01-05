package com.github.severinnitsche.essentials.meta.help;

import com.github.severinnitsche.essentials.meta.Ray;
import com.github.severinnitsche.essentials.meta.help.Screen;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;

import java.lang.Iterable;
import java.util.Iterator;

public class RayPointer implements Iterable<Ray> {
  private Screen screen;
  private Point origin;
  private int width;
  private int height;
  private int density;

  public RayPointer(Screen screen, Point origin, int width, int height, int density) {
    this.screen = screen;
    this.origin = origin;
    this.width = width;
    this.height = height;
    this.density = density;
  }

  public Ray get(int x, int y) {
    double X = MathUtil.map(x,0,width*density-1,-width/2d,width/2d);
    double Y = MathUtil.map(y,0,height*density-1,-height/2d,height/2d);
    //double X = ((double)x - (double)(width  * density - 1 ) / 2d) / ((double)(width  * density - 1) / 2d);
    //double Y = ((double)y - (double)(height * density - 1) / 2d) / ((double)(height * density - 1) / 2d);
    return screen.get(new Point(origin), new Point(X,Y));
  }

  public int size() {
    return width * height * density * density;
  }

  @Override
  public Iterator<Ray> iterator() {
    return new Iterator<Ray>() {
      int i;

      {
        i = -1;
      }

      int getX() {
        return i%(width*density);
      }

      int getY() {
        return i/(width*density);
      }

      @Override
      public boolean hasNext() {
        return i+1<size();
      }

      @Override
      public Ray next() {
        i++;
        return get(getX(),getY());
      }
    };
  }
}
