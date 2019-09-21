import java.util.*;

public class Sphere implements ThreeDObject {

  Color color;
  double reflectance;

  double radius;
  double[] center;

  public Sphere( double x, double y, double z, double radius, Color color, double reflectance ) {
    if(reflectance > 1 || reflectance < 0) throw new IllegalArgumentException("reflectance needs to be in range 1 (0% reflectance) to 0 (100% reflectance)");
    this.color       = color;
    this.reflectance = reflectance;

    this.radius      = radius;

    this.center      = new double[3];
    this.center[0]   = x;
    this.center[1]   = y;
    this.center[2]   = z;
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
    Ray      reflected  = new Ray( p0[ 0 ] + ( d2 >= 0 && d2 < d1 ? d2 : d1 ) * l0[ 0 ], p0[ 1 ] + ( d2 >= 0 ? d2 : d1 ) * l0[ 1 ], p0[ 2 ] + ( d2 >= 0 ? d2 : d1 ) * l0[ 2 ], reflection[ 0 ], reflection[ 1 ], ray.getScreen(), ray.getObjects() );

    return new ObjectData( d2 >= 0 && d2 < d1 ? d2 : d1, color.clone(), reflected, reflectance );
  }

  @Override
  public boolean equals(Object o) {
    if(o instanceof Sphere) {
      return ((Sphere)o).color.equals(color) && ((Sphere)o).reflectance == reflectance && ((Sphere)o).radius == radius && ((Sphere)o).center.equals(center);
    }
    return false;
  }

  @Override
  public String toString() {
    return "ref: "+reflectance+"\ncol: "+color+"\nrad: "+radius+"\ncen: "+center;
  }

}
