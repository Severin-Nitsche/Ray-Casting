public class Main {

  public static void main(String[] args) {
    Color       red    = new Color       ( (byte) 255, (byte) 0, (byte) 0 );
    ThreeDPlane screen = new ThreeDPlane (          1,        0,        0, Math.PI / 2d, 0, red );
    Sphere      sphere = new Sphere      (          0,        0,        0,            1, red );
    Ray         ray    = new Ray         (          0,        0,        0, Math.PI / 2d, 0, screen );
    System.out.println( screen.data( ray ) );
    System.out.println( sphere.data( ray ) );
  }

}
