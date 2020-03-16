package com.github.severinnitsche.essentials.meta;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.meta.help.*;
import com.github.severinnitsche.essentials.meta.lights.abstracted.Light;
import com.github.severinnitsche.utilities.compute.Asset;
import com.github.severinnitsche.utilities.logger.Loader;
import com.github.severinnitsche.utilities.math.MathUtil;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.structure.Stack;
import com.github.severinnitsche.utilities.visual.Color;

import java.io.IOException;

public class Viewer {
  
  World world;
  RayPointer pointer;
  
  public Viewer(Point origin, int width, int height, int density, Vector front, Vector right, double focalLength, World world) {
    Screen screen = new Screen(origin.addN(front.normalize().multiply(focalLength)), front, right);
    pointer = new RayPointer(screen, origin, width, height, density);
    this.world = world;
  }
  
  @Deprecated
  public Viewer(Point origin, int width, int height, int density, Vector front, Vector right, double focalLength, Light[] lights, ThreeDObject[] world) {
    Screen screen = new Screen(origin.addN(front.normalize().multiply(focalLength)), front, right);
    pointer = new RayPointer(screen, origin, width, height, density);
    this.world = new World(world, lights);
  }
  
  public int getWidth() {
    return pointer.getWidth();
  }
  
  public int getHeight() {
    return pointer.getHeight();
  }
  
  public int getDensity() {
    return pointer.getDensity();
  }
  
  private boolean hitsOrigin(Point position, Vector direction, double size) {
    //test whether light reaches origin
    Vector op = position.subtract(pointer.getOrigin()); //origin -> position
  
    //examine distance from http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
    //dividing by length of x2-x1 (direction) is omitted because direction (x2-x1) is of length 1
    double dist = direction.cross(op).squaredMagnitude(); //also its squared
  
    //examine direction
    boolean front = op.dot(direction)<0;
  
    return front && dist<size*size;
  }
  
  public int[] getLightColorArray(int samples, int depth, double size) {
    Loader loader = new Loader();
    int[] colors = new int[pointer.size()];  //color array / image
    for (Light light : world.getLights()) {  //iterate through all Lights
      Point position = light.getPosition();  //position of the light origin
      int progress = 0;
      for (Vector direction : light.getDirections(samples)) { //iterate through 'all' directions of the light
        
        loader.load(progress+1, samples);
        
        Stack<ReflectInfo> directions = new Stack<>(new ReflectInfo(direction,0,position,light.getColor(direction),light.getStrength()));
  
        for(ReflectInfo rf : directions) { //iterate through the depth+directions
  
          if(rf.depth>depth) continue; //continue if max depth is reached
          if(hitsOrigin(rf.position,rf.direction,size)) {
            //set / add the color to the appropriate pixel
            double strength = rf.strength / pointer.getOrigin().subtract(rf.position).squaredMagnitude();
            Color color = rf.color;
            
            color = color.multS(strength);
            
            //determine appropriate pixel
            Point local = pointer.getScreen().localize(pointer.getScreen().collide(new Ray(rf.position,rf.direction)).position); //screen coordinates of point
            if(pointer.isInRange(local)) {
              //Point is visible
              int x = (int)MathUtil.map(local.get(0),-pointer.getWidth()/2,pointer.getWidth()/2,0,pointer.getDensity()*pointer.getWidth()); //pixel coordinates
              int y = (int)MathUtil.map(local.get(1),-pointer.getHeight()/2,pointer.getHeight()/2,0,pointer.getDensity()*pointer.getHeight());; //pixel coordinates
  
              int i = x + pointer.getDensity() * pointer.getWidth() * y; //index of x|y in colors
  
              colors[i] = new Color(colors[i]).max(color).getRGB(); //add the additional light
            }
            
            continue; //continue if light ray becomes visible to viewer
          }
          ObjectInformation info = Asset.dist(rf.position,rf.direction,world);
          if(info==null) {
            continue; //go on with the next direction if light hits no object
          }
          if(Double.isInfinite(info.distance) || Double.isNaN(info.distance)) {
            continue;  //go on with the next direction if light hits no object
          }
          //evaluate info to get new position (collision), new direction (reflection)
          
          //calculate new strength
          double strength = rf.strength / rf.position.subtract(info.position).squaredMagnitude();
          
          Vector reflection = Asset.reflection(info.normal,rf.direction); //reflection of the surface
          directions.push(new ReflectInfo(reflection,rf.depth+1,info.position,new Color(rf.color.getRGB()).multS(strength).limit(info.c),strength*(1-info.o.roughness())));
          //do not forget to implement diffusion (possibly based on roughness ie. roughness==1 100% diffusion 0% reflection and vice versa)
          Vector[] diffusion = MathUtil.getVectorsOnUnitArc(samples,MathUtil.PI,info.normal); //half sphere around normal aka diffused light
          directions.push(ReflectInfo.fill(diffusion,rf.depth+1,info.position,new Color(rf.color.getRGB()).multS(strength).limit(info.c),strength*info.o.roughness()));
        }
        
      }
    }
    return colors;
  }
  
  public int[] getViewColorArray(int depth) throws IOException {
    Loader loader = new Loader();
    int[] colors = new int[pointer.size()];
    int i = 0;
    for (Ray r : pointer) {
      colors[i] = world.colorFor(r, depth).getRGB();
      loader.load(i+1,pointer.size());
      i++;
    }
    return colors;
  }
  
}
