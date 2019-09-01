public class Ray {

  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  private double position[];
  private double direction[];
  private ThreeDObject[] objects;
  private ThreeDPlane screen;
  private int reflections = 1;

  public Ray(double x, double y, double z, double direction1, double direction2, ThreeDPlane screen, int reflections, ThreeDObject ... objects) {
    this( x, y, z, direction1, direction2, screen, objects);
    this.reflections = reflections;
  }

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

    Ray      check    = this;
    Color[]  colors   = new Color[ reflections ];
    double[] strength = new double[ reflections ];
    ObjectData champ = null;
    for( int r = 0; r < reflections; r++ ) {
      double record     = Double.POSITIVE_INFINITY;
      for (ThreeDObject object : objects) {
        ObjectData temporaryData = object.data(check);
        if (temporaryData.distance < record && temporaryData.distance > 0) {
          record        = temporaryData.distance;
          colors  [ r ] = temporaryData.color;
          strength[ r ] = temporaryData.reflectance;
          champ         = temporaryData.clone();
        }
      }
      check = champ.reflection;
    }

    Color  color      = null;
    for( int r = reflections - 1; r >= 0; r-- ) {
      if (color == null) color = colors[ r ];
      else color.overlay(colors[r], strength[ r ]);
    }
    return color;
  }

  public double[] get() {
    double[] ret = { position[ X ], position[ Y ], position[ Z ], direction[ 0 ], direction[ 1 ] };
    return ret;
  }

  public ThreeDPlane getScreen() {
    return screen;
  }

  public ThreeDObject[] getObjects() {
    return objects;
  }

  @Override
  public String toString() {
    return "x: "+position[ X ]+"\r\ny: "+position[ Y ]+"\r\nz: "+position[ Z ]+"\r\ntheta: "+direction[ 0 ]+"\r\nphi: "+direction[ 1 ];
  }

}
