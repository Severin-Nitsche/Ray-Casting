public class ObjectData {
  public double distance;
  public Color  color;
  public Ray    reflection;

  public ObjectData(double distance, Color color) {
    this.distance = distance;
    this.color    = color;
  }

  public ObjectData(double distance, Color color, Ray reflection) {
    this.distance   = distance;
    this.color      = color;
    this.reflection = reflection;
  }

  @Override
  public String toString() {
    return "distance: "+distance+"\r\ncolor: "+color+"\r\nreflection: "+reflection;
  }

}
