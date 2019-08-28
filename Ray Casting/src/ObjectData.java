public class ObjectData {
  public double distance;
  public Color  color;
  public Ray    reflection;
  public double reflectance;

  public ObjectData(double distance, Color color) {
    this.distance = distance;
    this.color    = color;
  }

  public ObjectData(double distance, Color color, Ray reflection) {
    this.distance   = distance;
    this.color      = color;
    this.reflection = reflection;
  }

  public ObjectData(double distance, Color color, Ray reflection, double reflectance) {
    this.distance    = distance;
    this.color       = color;
    this.reflection  = reflection;
    this.reflectance = reflectance;
  }

  @Override
  public ObjectData clone() {
    return new ObjectData(distance, color, reflection, reflectance);
  }

  @Override
  public String toString() {
    return "distance: "+distance+"\r\ncolor: "+color+"\r\nreflection: "+reflection+"\r\nreflectance: "+reflectance;
  }

}
