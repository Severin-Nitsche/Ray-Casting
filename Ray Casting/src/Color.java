public class Color {

  public static final byte RED   = 16;
  public static final byte GREEN = 8;
  public static final byte BLUE  = 0;

  private int rgb;

  public Color(int rgb) {
    this.rgb = rgb;
  }

  public Color(byte red, byte green, byte blue) {
    setRGB(red, green, blue);
  }

  public void setRGB(int rgb) {
    this.rgb = rgb;
  }

  public void setRGB(byte red, byte green, byte blue) {
    rgb =              red   & 0xff;
    rgb = (rgb << 8) | green & 0xff;
    rgb = (rgb << 8) | blue  & 0xff;
  }

  public int getRGB() {
    return rgb;
  }

  public int get(byte colorComponent) {
    return (rgb >> colorComponent) & 0xff;
  }

  @Override
  public String toString() {
    return ""+rgb;
  }

}
