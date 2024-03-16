package util;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {

    static Logger instance = new Logger();
    static java.util.logging.Logger logger;

    static {
        try {
            logger = java.util.logging.Logger.getLogger("bank");
            FileHandler fileHandler = new FileHandler("./bank.log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return instance;
    }

    public void info(String info) {
        logger.info(info);
    }

    public void error(String error, Throwable t) {
        logger.log(Level.SEVERE, error, t);
    }
}
