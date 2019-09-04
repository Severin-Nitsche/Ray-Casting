public abstract class Light {

  public double[]       position = null;
  public ThreeDObject[] objects  = null;

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
    return Math.abs(record-dir[2])<.2 || dir[2]<=record;
  }

  public boolean isPointLight() {
    return true;
  }

}
