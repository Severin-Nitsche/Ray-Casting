import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
  public static void main(String[] args) {
    Color red = new Color((byte)255, (byte)0, (byte)98);
    Color green = new Color((byte)149, (byte)255, (byte)0);
    Color blue = new Color((byte)10, (byte)100, (byte)255);

    Sphere sphere = new Sphere(.5, 1, 3, 1, red, .1, true);
    Sphere sphere2 = new Sphere(0, 2, 2, .5, green, .8, true);
    ThreeDPlane bottom = new ThreeDPlane(0, 0, 0, Math.PI/2, Math.PI/2, blue, 0, true);
    Light light = new Light() {};
    double[] d = {0, 10, -15};
    light.position = d;
    ThreeDObject[] o = {bottom, sphere, sphere2};
    light.objects = o;
    Light[] lights = {light};

    double[] frontDirection = {0, 0};
    double[] leftDirection = {Math.PI / 2d, 0};
    Viewer thirdTestViewer = new Viewer( 0, 3, -5, 3, 3, 200, frontDirection, leftDirection, 1, 70, lights, bottom, sphere2, sphere);

    JFrame frame = new JFrame("Ray Casting");
    BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
    image.setRGB(0, 0, 600, 600, thirdTestViewer.getColorArray(), 0, 600);
    frame.add(new JLabel(new ImageIcon(image)));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setSize(600, 610);
    frame.addMouseListener(
      new MouseListener() {
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseClicked(MouseEvent e) {
          thirdTestViewer.inspect((int)e.getPoint().getX(), (int)e.getPoint().getY());
        }
      }
    );
  }
}
