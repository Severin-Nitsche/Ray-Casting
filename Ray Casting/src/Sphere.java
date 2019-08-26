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

    double   r          = radius;
    double[] c          = center;
    double[] p0         = { temp[0], temp[1], temp[2] };
    double[] l0         = { temp[3], temp[4] };
             l0         = Util.toCartesian( l0 );

    double   d1         = - ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / ( Util.dot( l0, l0 ) ) + Math.sqrt( ( r * r - Util.dot( p0, p0 ) - Util.dot( c, c ) + 2 * Util.dot( p0, c ) ) / Util.dot( l0, l0 ) + Math.pow( ( ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / Util.dot( l0, l0 ) ), 2 ) );
    double   d2         = - ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / ( Util.dot( l0, l0 ) ) - Math.sqrt( ( r * r - Util.dot( p0, p0 ) - Util.dot( c, c ) + 2 * Util.dot( p0, c ) ) / Util.dot( l0, l0 ) + Math.pow( ( ( Util.dot( l0, ( Util.sub( p0, c ) ) ) ) / Util.dot( l0, l0 ) ), 2 ) );

    //calculating normal
    double[] normal     = {
      p0[0] + l0[0] * ( d2 >= 0 ? d2 : d1 ) - center[0],
      p0[1] + l0[1] * ( d2 >= 0 ? d2 : d1 ) - center[1],
      p0[2] + l0[2] * ( d2 >= 0 ? d2 : d1 ) - center[2]
    };

    //calculating reflection
    double[] projection = Util.project( l0, normal );
    double[] rejection  = Util.reject( l0, normal );

    double[] reflection = Util.sub( rejection, projection );
             reflection = Util.toSpherical( reflection );
    Ray      reflected  = new Ray( temp[ 0 ] + ( d2 >= 0 ? d2 : d1 ) * l0[ 0 ], temp[ 1 ] + ( d2 >= 0 ? d2 : d1 ) * l0[ 1 ], temp[ 2 ] + ( d2 >= 0 ? d2 : d1 ) * l0[ 2 ], reflection[ 0 ], reflection[ 1 ], ray.getScreen(), ray.getObjects() );

    return new ObjectData( d2 >= 0 ? d2 : d1, color, reflected );
 }

}
