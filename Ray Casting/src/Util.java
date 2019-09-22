public class Util {


      public static final int X = 0;
      public static final int Y = 1;
      public static final int Z = 2;

      /**
      *
      * @param a -&gt; A vector, b -&gt; A vector
      * @return the dot product
      *
      * <p class="warning">!!!IMPORTANT!!! a and b have to have equal lengths</p>
      *
      */
      public static double dot(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Vectors have to be of equal length");
        double ret = 0;
        for(int i=0; i<a.length; i++) {
          ret += a[ i ] * b[ i ];
        }
        return ret;
      }

      /**
      *
      * <p>converts from spherical to cartesian</p>
      * <p>if the length of p is 2 the value 1 is assumed for r</p>
      * <p>The general form of p should be {theta, phi, r}</p>
      *
      */
      public static double[] toCartesian(double[] p) {
        if(p.length > 3 || p.length < 2) throw new IllegalArgumentException("Converting only spherical coordinates");
        double theta = p[ 0 ];
        double phi   = p[ 1 ];
        double r     = 1;
        if(p.length == 3) r = p[ 2 ];
        double x     = r * Math.sin(theta) * Math.cos(phi);
        double y     = r * Math.sin(theta) * Math.sin(phi);
        double z     = r * Math.cos(theta);
        double[] ret = { x, y, z };
        return ret;
      }

      /**
      *
      * @param c - the cartesian coordinates
      * @return a spherical representation of c
      *
      */
      public static double[] toSpherical(double[] c) {
        double   rho         = Math.sqrt( c[ X ] * c[ X ] + c[ Y ] * c[ Y ] + c[ Z ] * c[ Z ] );
        double   phi         = Math.atan2( c[ Y ], c[ X ] );
        double   theta       = Math.acos( c[ Z ] / rho );
        double[] returnValue = { theta, phi, rho };
        return returnValue;
      }

      public static double[] sub(double[] a, double[] b) {
        if(a.length != b.length) throw new IllegalArgumentException("Vectors have to have equal length");
        double[] ret = new double[ a.length ];
        for (int i=0; i<a.length; i++) {
          ret[ i ]   = a[ i ] - b[ i ];
        }
        return ret;
      }

      public static double[] add(double[] a, double[] b) {
        if(a.length != b.length) throw new IllegalArgumentException("Vectors have to have equal length");
        double[] ret = new double[ a.length ];
        for (int i=0; i<a.length; i++) {
          ret[ i ]   = a[ i ] + b[ i ];
        }
        return ret;
      }

      public static double[] cross(double[] a, double[] b) {
        double[] returnValue = { a[ Y ] * b[ Z ] - a[ Z ] * b[ Y ],
                                 a[ Z ] * b[ X ] - a[ X ] * b[ Z ],
                                 a[ X ] * b[ Y ] - a[ Y ] * b[ X ] };
        return returnValue;
      }

      public static double[] multiply(double[] a, double b) {
        double[] returnValue = a.clone();
        for (int index = 0; index < a.length; index++ ) {
          returnValue[ index ] *= b;
        }
        return returnValue;
      }

      public static double[] divide(double[] a, double b) {
        double[] returnValue = a.clone();
        for (int index = 0; index < a.length; index++ ) {
          returnValue[ index ] /= b;
        }
        return returnValue;
      }

      /**
      *
      * @return the projection of a onto b
      *
      */
      public static double[] project(double[] a, double[] b) {
        double[] projection = multiply( b, Util.dot( a, b ) / squaredMagnitude( b ) );
        return projection;
      }

      /**
      *
      * @return the rejection from a on b
      *
      */
      public static double[] reject(double[] a, double[] b) {
        double[] rejection = sub( a, project( a, b ) );
        return rejection;
      }

      public static double squaredMagnitude(double[] a) {
        double result = 0;
        for( int i = 0; i < a.length; i++ ) {
          result += a[ i ] * a[ i ];
        }
        return result;
      }

      public static double magnitude(double[] a) {
        double result = 0;
        for( int i = 0; i < a.length; i++ ) {
          result += a[ i ] * a[ i ];
        }
        result = Math.sqrt( result );
        return result;
      }

      public static double[] normalize(double[] a) {
        return divide( a, magnitude(a) );
      }

      public static double map(double value, double min, double max, double nmin, double nmax) {
        double elevated = value - min;
        double elevatedMax = max - min;
        double p = elevated / elevatedMax;
        double nelevatedmax = max - nmin;
        double nelevated = nelevatedmax * p;
        double n = nelevated + min;
        return n;
      }

      public static double clamp(double value, double min, double max) {
        if(value<min) return min;
        if(value>max) return max;
        return value;
      }

      public static String vecToString(double[] a) {
        return "x:\t"+a[0]+"\r\n"+
               "y:\t"+a[1]+"\r\n"+
               "z:\t"+a[2]+"\r\n";
      }
}
