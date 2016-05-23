# ch.bfh.bti7081.s2016.blue HealthVis App (Module Software Engineering and Design) [![Build Status](https://travis-ci.org/BurningBadger/ch.bfh.bti7081.s2016.blue.svg?branch=master)](https://travis-ci.org/BurningBadger/ch.bfh.bti7081.s2016.blue)

### Members
Sergii Bilousov,
Denis Shevchenko,
Karol Ugorcak,
Michel Hosmann,
Nicolas Schmid,
Christoph Sutter


## Logging
We are using java.util.logging as described here: https://vaadin.com/docs/-/part/framework/advanced/advanced-logging.html

### Using Logger
```
public class MyClass {
  private final static Logger logger =
          Logger.getLogger(MyClass.class.getName());

  public void myMethod() {
    try {
      // do something that might fail
    } catch (Exception e) {
      logger.log(Level.SEVERE, "FAILED CATASTROPHICALLY!", e);
    }
  }
}
```

### Configuration
The configuration for the loggers is done in: ``src/main/resources/logging.properties``



## Exception Handling


## Backlog
https://docs.google.com/spreadsheets/d/1y-1JTzggA7luPNeGTHgxt7oRreeSmeyHxiz8hK-Xy2U/edit#gid=1215547785