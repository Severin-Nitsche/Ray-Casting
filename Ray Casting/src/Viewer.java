public class Viewer {

  Ray         [][] rays;
  ThreeDPlane      screen;
  ThreeDObject[]   objects;

  public Viewer( int width, int height, int density, Screen screen, ThreeDObject ... world ) {
    this.screen = screen;
    this.rays   = new Ray[ width * density ][ height * density ];

    for (int x = 0; x < width * density; x++) {
      for (int y = 0; y < height * density; y++) {

        double   X          = ( (double) x - (double) ( width  * density - 1 ) / 2d ) / ( (double) ( width  * density - 1 ) / 2d );
        double   Y          = ( (double) y - (double) ( height * density - 1 ) / 2d ) / ( (double) ( height * density - 1 ) / 2d );
        this.rays[ x ][ y ] = screen.get( 0, 0, 0, X, Y, world );

      }
    }

    this.objects = world;
    
  }

  public Viewer( int width, int height, int density, ThreeDPlane screen, ThreeDObject...objects ) {

    this.screen  = screen;
    this.rays    = new Ray[ width * density ][ height * density ];

    for (int x=0; x<width * density; x++) {
      for (int y=0; y<height * density; y++) {

        double   X          = ( (double) x - (double) ( width  * density - 1 ) / 2d ) / ( (double) ( width  * density - 1 ) / 2d );
        double   Y          = ( (double) y - (double) ( height * density - 1 ) / 2d ) / ( (double) ( height * density - 1 ) / 2d );
        System.out.println("X: "+X);
        System.out.println("Y: "+Y);
        double[] up         = screen.get();
        double   r          = Math.sqrt( X * X + Y * Y + up[ 2 ] * up[ 2 ] );
        System.out.println("r: "+r);
        double   theta      = Math.acos( up[ 2 ] / r );
        System.out.println("theta: "+theta);
        double   phi        = Math.asin( Y / ( r * Math.sin( theta ) ) );
        if (Double.isNaN(phi)) phi = 0;
        System.out.println("phi: "+phi);
        this.rays[ x ][ y ] = new Ray( 0, 0, 0, theta, phi, screen, objects );

      }
    }

    this.objects = objects.clone();

  }

  public int[] getColorArray() {

    int[] colors = new int[ this.rays.length * this.rays[ 0 ].length ];
    for (int x=0; x<rays.length; x++) {
      for (int y=0; y<rays[0].length; y++) {

        try {
          colors[ y * rays.length + x ] = rays[ x ][ y ].getColor().getRGB();
        } catch(java.lang.NullPointerException e) {
          colors[ y * rays.length + x ] = 0;
        }

      }
    }

    return colors;

  }

  public void printColor() {
    int[] colors =getColorArray();
    for (int i=0; i<colors.length; i++) {
      if(i%(rays.length)==0) System.out.println();
      System.out.print(colors[i] > 0 ? "r " : "s ");
    }
  }

  public void print() {
    for (int x=0; x<rays.length; x++) {
      for (int y=0; y<rays[0].length; y++) {

        System.out.println(rays[ x ][ y ]+"\n");

      }
    }
  }

}
