public class ThreeDPlane implements ThreeDObject {

  public static double ZERO = .001;

  protected Color color;
  protected double reflectance;
  protected double localUp[];

  protected ThreeDPlane() {

  }

  /**
  *
  * @param x - plane position
  * @param y - plane position
  * @param z - plane position
  * @param direction1 - normal direction
  * @param direction2 - normal direction
  * @param color - The color of the plane
  *
  */
  public ThreeDPlane(double x, double y, double z, double direction1, double direction2, Color color, double reflectance) {
    localUp          = new double[ 5 ];
    localUp[ 0 ]     = x;
    localUp[ 1 ]     = y;
    localUp[ 2 ]     = z;
    localUp[ 3 ]     = direction1;
    localUp[ 4 ]     = direction2;
    this.color       = color;
    this.reflectance = reflectance;
  }

  public double[] get() {
    return localUp.clone();
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
  @Override
  public ObjectData data(Ray ray) {
    double[] p0         = { localUp[ 0 ], localUp[ 1 ], localUp[ 2 ] };
    double[] temp       = ray.get();
    double[] l0         = { temp[ 0 ], temp[ 1 ], temp[ 2 ] };
    double[] temp2      = { localUp[ 3 ], localUp[ 4 ] };
    double[] n          = Util.toCartesian( temp2 );
    double[] temp3      = { temp[ 3 ], temp[ 4 ] };
    double[] l          = Util.toCartesian( temp3 );
    double denominator  = Util.dot( l, n );
    if (denominator == 0) return null;
    double numerator    = Util.dot( Util.sub( p0, l0 ), n );
    double d            = numerator / denominator;
    //----- calculating the projection and rejection of the ray onto the normal
    double[] projection = Util.multiply( n, Util.dot( l, n ) );
    double[] rejection  = Util.sub( l, projection );
    double[] reflection = Util.toSpherical( Util.sub( rejection, projection ) );
    //x: ray.x + d * l.x, y: ...
    Ray      reflected  = new Ray( temp[ 0 ] + d * l[ 0 ], temp[ 1 ] + d * l[ 1 ], temp[ 2 ] + d * l[ 2 ], reflection[ 0 ], reflection[ 1 ], ray.getScreen(), ray.getObjects() );
    return new ObjectData( d, color.clone(), reflected, reflectance );
  }

  @Override
  public double[] getNormalAt(double...pos) {
    double[] ret = {localUp[3],localUp[4]};
    ret = Util.toCartesian(ret);
    double[] center = {localUp[0],localUp[1],localUp[2]};
    double[] p = Util.sub( pos, center );
    double d = Util.dot(ret, p);
    if(d > ZERO) throw new IllegalArgumentException("Point is not on plane");
    return ret;
  }



}
