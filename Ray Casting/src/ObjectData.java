public class ObjectData {
  public double distance;
  public Color color;

  public ObjectData(double distance, Color color) {
    this.distance = distance;
    this.color    = color;
  }

  @Override
  public String toString() {
    return "distance: "+distance+"\r\ncolor: "+color;
  }

}
