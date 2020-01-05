package com.github.severinnitsche.run;

import com.github.severinnitsche.essentials.abstracted.ThreeDObject;
import com.github.severinnitsche.essentials.implemented.Sphere;
import com.github.severinnitsche.essentials.implemented.ThreeDPlane;
import com.github.severinnitsche.essentials.meta.Light;
import com.github.severinnitsche.essentials.meta.Viewer;
import com.github.severinnitsche.utilities.math.Point;
import com.github.severinnitsche.utilities.math.Vector;
import com.github.severinnitsche.utilities.visual.Color;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {
  public static void main(String[] args) {
      Point center = new Point(0,0,10);
    
      Color red = new Color((byte)255,(byte)40,(byte)35);
      Color blue = new Color((byte)100,(byte)50,(byte)245);

      Sphere sphere = new Sphere(center,4,red,.5,true);

      Point origin = new Point(0,0,0);

      Vector dir = new Vector(0,0,1);
      Vector right = new Vector(1,0,0);
    
      ThreeDPlane plane = new ThreeDPlane(0,-5,0,Math.PI/2,Math.PI/2,blue,.5,true);

      ThreeDObject[] world = {sphere,plane};
    
      Light l = new Light() {};
      l.position = new Point(0,0,0);
      l.objects = world;
      l.strength = Integer.MAX_VALUE;
      l.color = new Color((byte)255,(byte)255,(byte)255);
      
      Light[] lights = {l};

      Viewer viewer = new Viewer(origin,4,4,200,dir,right,1,lights,world);

      JFrame frame = new JFrame("Sphere");

      BufferedImage img = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);

      img.setRGB(0,0,800,800,viewer.getViewColorArray(30),0,800);

      frame.add(new JLabel(new ImageIcon(img)));

      frame.setSize(600,600);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
