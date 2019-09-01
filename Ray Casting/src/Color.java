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

  public Color overlay(Color c, double strength) {
    int red    = get(RED);
    int green  = get(GREEN);
    int blue   = get(BLUE);

    int cRed   = c.get(RED);
    int cGreen = c.get(GREEN);
    int cBlue  = c.get(BLUE);

    int nRed   = (int) ((1d - strength) * red + strength * cRed);
    int nGreen = (int) ((1d - strength) * green + strength * cGreen);
    int nBlue  = (int) ((1d - strength) * blue + strength * cBlue);

    setRGB((byte)nRed,(byte)nGreen,(byte)nBlue);
    return this;
  }

  @Override
  public String toString() {
    return ""+rgb;
  }

  @Override
  public Color clone() {
    return new Color(rgb);
  }

}
