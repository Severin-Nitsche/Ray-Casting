import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Point;
public class Main {

//inspect 420 | 266

  public static void main(String[] args) {
    Color       red              = new Color       ( (byte) 255, (byte) 0, (byte) 0 );
    Color       green            = new Color       ( (byte)   0, (byte) 255, (byte) 0 );
    Color       blue             = new Color       ( (byte)   0, (byte) 0, (byte) 255 );
    //ThreeDPlane screen           = new ThreeDPlane (          0,        0,        1,          0,      0,          red,          0 );
    Sphere      sphere           = new Sphere      (         .5,        1,        3,         1,     red,           .1 );
    Sphere      sphere2          = new Sphere      (          0,        2,        2,         .5,  green,           .8 );
    ThreeDPlane bottom           = new ThreeDPlane (          0,       0,        0,    Math.PI/2,Math.PI/2,         blue,               0);
    Light       light            = new Light() {};
    double[] d = {0,10,-15};
    light.position = d;
    ThreeDObject[] o = {bottom, sphere, sphere2};
    light.objects = o;
    Light[] lights = {light};
    //System.exit(0);
    //Ray         ray              = new Ray         (          0,        0,        0,          0,      0,       screen, 2,     sphere2 );
    //Viewer      viewer           = new Viewer      (          3,        3,      100,     screen, sphere );
    //Screen      testScreen       = new Screen      (          0,        0,        1,          0,      0, Math.PI / 2d,          0 );
    //Viewer      testViewer       = new Viewer      (          3,        3,      100, testScreen, sphere );
    //Viewer      secondTestViewer = new Viewer      (          0,        0,        0,          3,      3,          100, testScreen, sphere );
    double[]    frontDirection   = {            0, 0 };
    double[]    leftDirection    = { Math.PI / 2d, 0 };
    Viewer      thirdTestViewer  = new Viewer      (          0,          3,        -5,          3,      3,         200, frontDirection, leftDirection, 1, 7, lights, bottom, sphere2, sphere );


    //viewer.printColor();
    //testViewer.printColor();
    //secondTestViewer.printColor();
    //thirdTestViewer.printColor();

    JFrame        frame  = new JFrame( "Ray Casting" );
    BufferedImage image  = new BufferedImage( 600, 600, BufferedImage.TYPE_3BYTE_BGR );
    image.setRGB( 0, 0, 600, 600, thirdTestViewer.getColorArray(), 0, 600 );
    frame.add( new JLabel( new ImageIcon( image ) ) );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setVisible( true );
    frame.setSize( 600, 610 );
    frame.addMouseListener(new MouseListener() {
      @Override
      public void mouseEntered(MouseEvent e) {

      }
      @Override
      public void mouseExited(MouseEvent e) {

      }
      @Override
      public void mousePressed(MouseEvent e) {

      }
      @Override
      public void mouseReleased(MouseEvent e) {

      }
      @Override
      public void mouseClicked(MouseEvent e) {
        thirdTestViewer.inspect((int)e.getPoint().getX(),(int)e.getPoint().getY());
      }
    });

  }

}
