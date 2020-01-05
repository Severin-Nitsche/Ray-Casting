package com.github.severinnitsche.essentials.meta;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.help.RayPointer;
import com.github.severinnitsche.essentials.meta.help.Screen;
import com.github.severinnitsche.essentials.meta.help.World;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;

public class Viewer {

  World world;
  RayPointer pointer;

  public Viewer(Point origin, int width, int height, int density, Vector front, Vector right, double focalLength, Light[] lights, ThreeDObject[] world) {
    Screen screen = new Screen(origin.addN(front.normalize().multiply(focalLength)), front, right);
    pointer = new RayPointer(screen, origin, width, height, density);
    this.world = new World(world,lights);
  }

  public int[] getLightColorArray(int samples) {
    return null;
  }

  public int[] getViewColorArray(int depth) {
    int[] colors = new int[pointer.size()];
    int i=0;
    for(Ray r : pointer) {
      colors[i] = world.colorFor(r,depth).getRGB();
      i++;
    }
    return colors;
  }

}
