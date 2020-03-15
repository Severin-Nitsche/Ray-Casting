package com.github.severinnitsche.run.test;

import com.github.severinnitsche.utilities.logger.Logger;

import java.io.IOException;

public class LoggerTest {
  
  public static void main(String[] args) throws IOException, InterruptedException {
    Logger.LOGGER.error("YEET");
    Logger.LOGGER.warn("YEEEEET");
    System.out.println("Yolo");
    System.out.print("\0337Hallo Welt\0338G'day");
    char escCode = 0x1B;
    int row = 0; int column = 0;
    System.out.print(String.format("%c7%s%c7%s",escCode,"Hello World",escCode,"G'Day"));
    System.out.println("Test"+(char)127);
    System.out.print("old line");
    Thread.sleep(3000);
    System.out.print("\rnew line");
  }

}
