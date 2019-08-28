import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
public class Main {

  public static void main(String[] args) {
    Color       red              = new Color       ( (byte) 255, (byte) 0, (byte) 0 );
    Color       red2             = new Color       ( (byte)   0, (byte) 255, (byte) 0 );
    ThreeDPlane screen           = new ThreeDPlane (          0,        0,        1,          0,      0,          red,          0 );
    Sphere      sphere           = new Sphere      (          .5,        1,        3,          1,    red,            0 );
    Sphere      sphere2          = new Sphere      (          0,        0,        2,         .5,   red2,            0 );
    Ray         ray              = new Ray         (          0,        0,        0,          0,      0,       screen, 2,     sphere2 );
    Viewer      viewer           = new Viewer      (          3,        3,      100,     screen, sphere );
    Screen      testScreen       = new Screen      (          0,        0,        1,          0,      0, Math.PI / 2d,          0 );
    Viewer      testViewer       = new Viewer      (          3,        3,      100, testScreen, sphere );
    Viewer      secondTestViewer = new Viewer      (          0,        0,        0,          3,      3,          100, testScreen, sphere );
    double[]    frontDirection   = {            0, 0 };
    double[]    leftDirection    = { Math.PI / 2d, 0 };
    Viewer      thirdTestViewer  = new Viewer      (          0,          0,        0,          3,      3,          100, frontDirection, leftDirection, 1, 2, sphere, sphere2 );

    viewer.printColor();
    testViewer.printColor();
    secondTestViewer.printColor();
    thirdTestViewer.printColor();

    JFrame        frame  = new JFrame( "Ray Casting" );
    BufferedImage image  = new BufferedImage( 300, 300, BufferedImage.TYPE_3BYTE_BGR );
    image.setRGB( 0, 0, 300, 300, thirdTestViewer.getColorArray(), 0, 300 );
    frame.add( new JLabel( new ImageIcon( image ) ) );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setVisible( true );
    frame.setSize( 300, 310 );

  }

}
