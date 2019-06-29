public class Screen extends ThreeDPlane {

  /**
  *
  * @params equal to super but direction3 and direction4 define the left
  *
  */
  public Screen( double x, double y, double z, double direction1, double direction2, double direction3, double direction4 ) {
    super();
    double[] tempUP   = { direction1, direction2 };
    double[] tempLEFT = { direction3, direction4 };
             tempUP   = Util.toCartesian( tempUP );
             tempLEFT = Util.toCartesian( tempLEFT );
    //threshholdcheck needed because of inaccuarities
    if( Math.abs( Util.dot( tempUP, tempLEFT ) ) > .001 ) throw new IllegalArgumentException("Left and up vectors have to be orthogonal");

    localUp      = new double[ 7 ];
    localUp[ 0 ] = x;
    localUp[ 1 ] = y;
    localUp[ 2 ] = z;
    localUp[ 3 ] = direction1;
    localUp[ 4 ] = direction2;
    localUp[ 5 ] = direction3;
    localUp[ 6 ] = direction4;
    this.color   = null;
  }

  public double[] getNormal() {
    double[] returnValue = { localUp[ 3 ], localUp[ 4 ]};
             returnValue = Util.toCartesian( returnValue );
    return returnValue;
  }

  public double[] getPosition() {
    double[] returnValue = { localUp[ 0 ], localUp[ 1 ], localUp[ 2 ] };
    return returnValue;
  }

  public double[] getX() {
    double[] returnValue = { localUp[ 5 ], localUp[ 6 ]};
             returnValue = Util.toCartesian( returnValue );
    return returnValue;
  }

  public double[] getY() {
    return Util.cross( getX(), getNormal() );
  }

  public double[] convert( double x, double y ) {
    //need to know how to get the Y unit vector
    //then multiply and add the corresponding values
    double[] returnValue    = null;
    double[] yVector        = getY();
    double[] xVector        = getX();
    double[] positionVector = getPosition();
             yVector        = Util.multiply( yVector, y );
             xVector        = Util.multiply( xVector, x );
             returnValue    = Util.add( yVector, xVector );
             returnValue    = Util.add( returnValue, positionVector );
    return returnValue;
  }

  public Ray get( double globalX, double globalY, double globalZ, double localX, double localY, ThreeDObject ... world ) {
    //need to finish convert and use formulas from viewer
    double[] localVector = convert( localX, localY );
             localVector = Util.toSpherical( localVector );
    Ray      returnValue = new Ray( globalX, globalY, globalZ, localVector[ 0 ], localVector[ 1 ], this, world );
    return returnValue;
  }

}
