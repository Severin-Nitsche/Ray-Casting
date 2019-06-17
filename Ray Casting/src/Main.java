import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
public class Main {

  public static void main(String[] args) {
    Color         red    = new Color       ( (byte) 255, (byte) 0, (byte) 0 );
    ThreeDPlane   screen = new ThreeDPlane (          0,        0,        1,      0,      0, red );
    Sphere        sphere = new Sphere      (          0,        0,        3,      1,    red );
    Ray           ray    = new Ray         (          0,        0,        0,      0,      0, screen, sphere );
    Viewer        viewer = new Viewer      (          3,        3,      100, screen, sphere );

    viewer.printColor();

    JFrame        frame  = new JFrame( "Ray Casting" );
    BufferedImage image  = new BufferedImage( 300, 300, BufferedImage.TYPE_3BYTE_BGR );
    image.setRGB( 0, 0, 300, 300, viewer.getColorArray(), 0, 300 );
    frame.add( new JLabel( new ImageIcon( image ) ) );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setVisible( true );
    frame.setSize( 300, 310 );

  }

}
