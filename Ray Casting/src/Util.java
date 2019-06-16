public class Util {

      /**
      *
      * @params a -&gt; A vector, b -&gt; A vector
      * @returns the dot product
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
      * <p>converts from polar to cartesian</p>
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

      public static double[] sub(double[] a, double[] b) {
        if(a.length != b.length) throw new IllegalArgumentException("Vectors have to have equal length");
        double[] ret = new double[ a.length ];
        for (int i=0; i<a.length; i++) {
          ret[ i ]   = a[ i ] - b[ i ];
        }
        return ret;
      }

      public static String vecToString(double[] a) {
        return "x:\t"+a[0]+"\r\n"+
               "y:\t"+a[1]+"\r\n"+
               "z:\t"+a[2]+"\r\n";
      }
}
