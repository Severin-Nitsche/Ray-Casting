public class Ray {

  public static final double ZERO = .01;
  public static final double SURFACE_DISTANCE = .001;

  public static final int X = 0;
  public static final int Y = 1;
  public static final int Z = 2;
  private double position[];
  private double direction[];
  private ThreeDObject[] objects;
  private Light[] lights;
  private ThreeDPlane screen;
  private int reflections = 1;

  public Ray(double x, double y, double z, double direction1, double direction2, ThreeDPlane screen, int reflections, Light[] lights, ThreeDObject ... objects) {
    this( x, y, z, direction1, direction2, screen, objects);
    this.reflections = reflections;
    this.lights = lights;
  }

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
    this.objects       = objects;
    this.screen        = screen;
  }

  public Color inspect() {
    System.out.println("------inspect-------");
    Ray      check       = this;
    Color[]  colors      = new Color [ reflections ];
    double[] lightLevels = new double[ reflections ];
    double[] strength    = new double[ reflections ];
    for( int r = 0; r < reflections; r++ ) {
      System.out.println("Reflection #"+(r+1));
      System.out.println("check:");
      System.out.println(check);
      ObjectData champ     = null;
      double record     = Double.POSITIVE_INFINITY;
      for (ThreeDObject object : objects) {
        ObjectData temporaryData = object.data(check);
        System.out.println("IF:");
        if (temporaryData.distance < record && temporaryData.distance > SURFACE_DISTANCE) {
          record        = temporaryData.distance;
          //boolean lit = false;
          double lightLevel = 0;
          for(Light light : lights) {
            double[] point = Util.add( check.position, Util.multiply( Util.toCartesian(check.direction), temporaryData.distance ) );
            lightLevel += light.hasLightLevel(point);
          }
          //System.out.println(lit);
          lightLevel = Util.clamp( lightLevel, 0, 1);
          colors     [ r ] = temporaryData.color.clone();
          lightLevels[ r ] = lightLevel;
          strength   [ r ] = temporaryData.reflectance;
          //if(lightLevel <= ZERO) strength[ r ] = 1;
          champ            = temporaryData.clone();
        }
      }
      System.out.println("Color:");
      System.out.println(colors[r]);
      System.out.println("Light:");
      System.out.println(lightLevels[r]);
      System.out.println("strength:");
      System.out.println(strength[r]);
      System.out.println("champ:");
      System.out.println(champ);
      try {
        check = champ.reflection;
      } catch(Exception e) {
        break;
      }
    }

    Color  color      = null;
    for( int r = reflections - 1; r >= 0; r-- ) {
      System.out.println(colors[r]);
      if(colors[ r ] != null) {
        //colors[ r ].mult( lightLevels[ r ] );
        //strength[ r ] = Util.clamp( strength[ r ] / lightLevels[ r ], 0, 1 );
        //if (color == null) color = colors[ r ];
        //else color.overlay(colors[r], strength[ r ]);
      }
      if (colors[r] != null) return colors[r];
    }
    return color;
  }


  public Color getColor() {

    Ray      check       = this;
    Color[]  colors      = new Color [ reflections ];
    double[] lightLevels = new double[ reflections ];
    double[] strength    = new double[ reflections ];
    for( int r = 0; r < reflections; r++ ) {
      double record     = Double.POSITIVE_INFINITY;
      ObjectData champ     = null;
      for (ThreeDObject object : objects) {
        ObjectData temporaryData = object.data(check);
        if (temporaryData.distance < record && temporaryData.distance > SURFACE_DISTANCE) {
          record        = temporaryData.distance;
          //boolean lit = false;
          double lightLevel = 0;
          for(Light light : lights) {
            double[] point = Util.add( check.position, Util.multiply( Util.toCartesian(check.direction), temporaryData.distance ) );
            lightLevel += light.hasLightLevel(point);
          }
          //System.out.println(lit);
          lightLevel = Util.clamp( lightLevel, 0, 1);
          colors     [ r ] = temporaryData.color.clone();
          lightLevels[ r ] = lightLevel;
          strength   [ r ] = temporaryData.reflectance;
          //if(lightLevel <= ZERO) strength[ r ] = 1;
          champ            = temporaryData.clone();
        }
      }
      try {
        check = champ.reflection;
      } catch(Exception e) {
        break;
      }
    }

    Color  color      = null;
    for( int r = reflections - 1; r >= 0; r-- ) {
      if(colors[ r ] != null) {
        colors[ r ].mult( lightLevels[ r ] );
        strength   [ r ] *= Util.map( lightLevels[ r ], 0, 1, strength[ r ], 1 );
        if (color == null) color = colors[ r ];
        else color.overlay(colors[r], strength[ r ]);
      }
      //if (colors[r] != null) return colors[r];
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
