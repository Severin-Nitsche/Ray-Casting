public abstract class Light {

  public static final double SURFACE_DISTANCE = .0001;
  public static final double ZERO             = .1;

  public double[]       position = null;
  public ThreeDObject[] objects  = null;

  @Deprecated
  public boolean isLit(double[] pos) {
    if (position        == null ) throw new IllegalStateException("Position has to be not null");
    if (position.length != 3    ) throw new IllegalStateException("Position has to have 3 and only three indicies");
    if (pos             == null ) throw new IllegalArgumentException("Pos(ition) has to be not null");
    if (pos.length      != 3    ) throw new IllegalArgumentException("Pos(ition) has to have 3 and only three indicies");
    if (objects         == null ) throw new IllegalStateException("No objects defined");
    if (objects.length  == 0    ) return true;

    double[] dir = {pos[0] - position[0], pos[1] - position[1], pos[2] - position[2]};
    dir = Util.toSpherical(dir);

    Ray check = new Ray(position[0], position[1], position[2], dir[0], dir[1], null, 1, objects);
    double record = Double.POSITIVE_INFINITY;
    for (ThreeDObject object : objects) {
      ObjectData temporaryData = object.data(check);
      if (temporaryData.distance < record && temporaryData.distance > 0) {
        record = temporaryData.distance;
      }
    }
    //System.out.println(dir[2]);
    //System.out.println(record);
    return Math.abs(record-dir[2])<SURFACE_DISTANCE || dir[2]<=record;
  }

  public double hasLightLevel(double[] pos) {
    if (position        == null ) throw new IllegalStateException("Position has to be not null");
    if (position.length != 3    ) throw new IllegalStateException("Position has to have 3 and only three indicies");
    if (pos             == null ) throw new IllegalArgumentException("Pos(ition) has to be not null");
    if (pos.length      != 3    ) throw new IllegalArgumentException("Pos(ition) has to have 3 and only three indicies");
    if (objects         == null ) throw new IllegalStateException("No objects defined");
    if (objects.length  == 0    ) return 1;

    double[] dir    = {pos[0] - position[0], pos[1] - position[1], pos[2] - position[2]};
    double[] rev    = {position[0] - pos[0], position[1] - pos[1], position[2] - pos[1]};
    rev             = Util.normalize(rev);
    double[] normal = null;
    dir             = Util.toSpherical(dir);

    Ray    check  = new Ray(position[0], position[1], position[2], dir[0], dir[1], null, 1, objects);
    double record = Double.POSITIVE_INFINITY;
    for (ThreeDObject object : objects) {
      try {
        normal = object.getNormalAt(pos);
      } catch(IllegalArgumentException e) {
      }
      ObjectData temporaryData = object.data(check);
      if (temporaryData.distance < record && temporaryData.distance > 0) {
        record = temporaryData.distance;
      }
    }
    double ret = Util.dot( normal, rev );
    ret = ret + 1;
    ret = ret / 2d; //+1  /2
    if (!(Math.abs(record-dir[2])<SURFACE_DISTANCE || dir[2]<=record)) ret *= ZERO;
    //System.out.println(dir[2]);
    //System.out.println(record);
    return ret;
  }

  public boolean isPointLight() {
    return true;
  }

}
