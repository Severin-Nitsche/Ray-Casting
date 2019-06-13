public class ThreeDPlane implements ThreeDObject {

  private Color color;
  private double localUp[];

  /**
  *
  * @params x, y, z, direction1, direction2 are the specification of the normal vector, where x, y and z are a generic vector of the plane and direction1 and direction2 denote the normal for the given point in polar form.<br />
  *         color is the color of the plane.
  *
  */
  public ThreeDPlane(double x, double y, double z, double direction1, double direction2, Color color) {
    localUp      = new double[ 5 ];
    localUp[ 0 ] = x;
    localUp[ 1 ] = y;
    localUp[ 2 ] = z;
    localUp[ 3 ] = direction1;
    localUp[ 4 ] = direction2;
    this.color   = color;
  }

  /**
  *
  * <p>Formula used for intersection detection:</p>
  * <a href="https://en.wikipedia.org/wiki/Line%E2%80%93plane_intersection#Algebraic_form">wikipedia</a>
  *
  * <style>
  * .warning {
  *  color: red;
  *  background-color: #FFACAC;
  *  font-weight: bold;
  *  border: 2pt solid red;
  * }
  * </style>
  *
  * <p class="warning">!!!IMPORTANT!!! in the following  '*' denotes the dot product.</p>
  * <p><i>d</i><b>l</b> + <b>l<sub>0</sub></b></p>
  * <p>where <i>d</i> equals ( (<b>p<sub>0</sub></b> - <b>l<sub>0</sub></b>) * <b>n</b> ) / ( <b>l</b> * <b>n</b> )</p>
  * <p>and</p>
  * <p><b>n</b> -&gt; normal</p>
  * <p><b>p<sub>0</sub></b> -&gt; generic Point on the plane</p>
  * <p><b>l</b> -&gt; direction Vector of the line</p>
  * <p><b>l<sub>0</sub></b> -&gt; generic Point on the line</p>
  * <p><i>d</i> -&gt; scalar</p>
  *
  */
  public ObjectData data(Ray ray) {
    double[] p0        = { localUp[ 0 ], localUp[ 1 ], localUp[ 2 ] };
    System.out.println( "p0:\n"+vecToString(p0) );
    double[] temp      = ray.get();
    double[] l0        = { temp[ 0 ], temp[ 1 ], temp[ 2 ] };
    System.out.println( "l0:\n"+vecToString(l0) );
    double[] temp2     = { localUp[ 3 ], localUp[ 4 ] };
    double[] n         = toCartesian( temp2 );
    System.out.println( "n:\n"+vecToString(n) );
    double[] temp3     = { temp[ 3 ], temp[ 4 ] };
    double[] l         = toCartesian( temp3 );
    System.out.println( "l:\n"+vecToString(l) );
    double denominator = dot( l, n );
    System.out.println( "denominator:\n"+denominator );
    if (denominator == 0) return null;
    double numerator   = dot( sub( p0, l0 ), n );
    System.out.println( "numerator:\n"+numerator );
    double d           = numerator / denominator;
    return new ObjectData( d, color );
  }


  /**
  *
  * @params a -&gt; A vector, b -&gt; A vector
  * @returns the dot product
  *
  * <p class="warning">!!!IMPORTANT!!! a and b have to have equal lengths</p>
  *
  */
  private double dot(double[] a, double[] b) {
    if (a.length != b.length) throw new IllegalArgumentException("Vectors have to be of equal length");
    double ret = 0;
    for(int i=0; i<a.length; i++) {
      ret += a[ i ] * b[ i ];
    }
    return ret;
  }

  /**
  *
  * <p>converts from polar to cartesian</p>
  * <p>if the length of p is 2 the value 1 is assumed for r</p>
  * <p>The general form of p should be {theta, phi, r}</p>
  *
  */
  private double[] toCartesian(double[] p) {
    if(p.length > 3 || p.length < 2) throw new IllegalArgumentException("Converting only spherical coordinates");
    double theta = p[ 0 ];
    double phi   = p[ 1 ];
    double r     = 1;
    if(p.length == 3) r = p[ 2 ];
    double x     = r * Math.sin(theta) * Math.cos(phi);
    double y     = r * Math.sin(theta) * Math.sin(phi);
    double z     = r * Math.cos(phi);
    double[] ret = { x, y, z };
    return ret;
  }

  private double[] sub(double[] a, double[] b) {
    if(a.length != b.length) throw new IllegalArgumentException("Vectors have to have equal length");
    double[] ret = new double[ a.length ];
    for (int i=0; i<a.length; i++) {
      ret[ i ]   = a[ i ] - b[ i ];
    }
    return ret;
  }

  private String vecToString(double[] a) {
    return "x:\t"+a[0]+"\r\n"+
           "y:\t"+a[1]+"\r\n"+
           "z:\t"+a[2]+"\r\n";
  }

}
