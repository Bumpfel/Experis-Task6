package se.experis.task6herokunate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class Logger {
  
  private boolean isOn = true;
  private static Logger instance = null;

  private Logger() {
  }

  public static Logger getInstance() {
    if(instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  public void setStatus(boolean on) {
    this.isOn = on;
  }

  public void log(HttpServletRequest request, HttpStatus statusCode) {
    if(!isOn) {
      return;
    }
    
    StringBuilder builder = new StringBuilder();
    var now = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(now);
    String time = new SimpleDateFormat("HH:mm:ss").format(now);
    
    builder.append(date + "T" + time + " ");
    builder.append(request.getRemoteAddr() + " ");
    builder.append(request.getMethod() + " ");
    builder.append(statusCode.value() + " ");
    builder.append(request.getRequestURI());

    System.out.println(builder);    
  }
}
