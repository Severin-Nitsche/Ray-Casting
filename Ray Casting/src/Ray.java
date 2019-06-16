public class Ray {

  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  private double position[];
  private double direction[];
  private ThreeDObject[] objects;
  private ThreeDPlane screen;

  public Ray(double x, double y, double z, double direction1, double direction2, ThreeDPlane screen, ThreeDObject ... objects) {
    this.position      = new double[ 3 ];
    this.position[ X ] = x;
    this.position[ Y ] = y;
    this.position[ Z ] = z;
    this.direction     = new double[2];
    this.direction[0]  = direction1;
    this.direction[1]  = direction2;
    this.objects       = objects.clone();
    this.screen        = screen;
  }

  public Color getColor() {
    double threshhold = screen.data(this).distance;
    double record     = Double.POSITIVE_INFINITY;
    Color color       = null;
    for (ThreeDObject object : objects) {
      ObjectData temporaryData = object.data(this);
      if (temporaryData.distance < record && temporaryData.distance > threshhold) {
        record = temporaryData.distance;
        color  = temporaryData.color;
      }
    }
    return color;
  }

  public double[] get() {
    double[] ret={position[ X ], position[ Y ], position[ Z ], direction[ 0 ], direction[ 1 ]};
    return ret;
  }

  @Override
  public String toString() {
    return "x: "+position[ X ]+"\r\ny: "+position[ Y ]+"\r\nz: "+position[ Z ]+"\r\ntheta: "+direction[ 0 ]+"\r\nphi: "+direction[ 1 ];
  }

}
