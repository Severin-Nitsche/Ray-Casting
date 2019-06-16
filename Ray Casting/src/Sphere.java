public class Sphere implements ThreeDObject {

  Color color;

  double radius;
  double[] center;

  public Sphere( double x, double y, double z, double radius, Color color) {
    this.color     = color;

    this.radius    = radius;

    this.center    = new double[3];
    this.center[0] = x;
    this.center[1] = y;
    this.center[2] = z;
  }

  public ObjectData data(Ray ray) {
    double[] temp = ray.get();

    double   r    = radius;
    double[] c    = center;
    double[] p0   = { temp[0], temp[1], temp[2] };
    double[] l0   = { temp[3], temp[4] };
             l0   = Util.toCartesian( l0 );

    double   d1   = - ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / ( Util.dot( l0, l0 ) ) + Math.sqrt( ( r * r - Util.dot( p0, p0 ) - Util.dot( c, c ) + 2 * Util.dot( p0, c ) ) / Util.dot( l0, l0 ) + Math.pow( ( ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / Util.dot( l0, l0 ) ), 2 ) );
    double   d2   = - ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / ( Util.dot( l0, l0 ) ) - Math.sqrt( ( r * r - Util.dot( p0, p0 ) - Util.dot( c, c ) + 2 * Util.dot( p0, c ) ) / Util.dot( l0, l0 ) + Math.pow( ( ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / Util.dot( l0, l0 ) ), 2 ) );

    return new ObjectData( d2 >= 0 ? d2 : d1 , color );
 }

}
