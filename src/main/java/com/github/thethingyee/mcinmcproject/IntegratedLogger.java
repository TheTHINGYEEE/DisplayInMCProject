package com.github.thethingyee.mcinmcproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class IntegratedLogger {


    private static Logger LOGGER = LoggerFactory.getLogger(MCinMCProject.class);;

    public enum Level {
        INFO, WARN, FINE
    }

    public static void logDebug(String logging) {
        LOGGER.info("\u001B[32m{}\u001B[0m", logging);
    }
    public static void logInfo(String logging) {
        LOGGER.info("\u001B[37m{}\u001B[0m", logging);
    }

    public static void logError(String logging) {
        LOGGER.warn("\u001B[31m{}\u001B[0m", logging);
    }

    public static void log(IntegratedLogger.Level level, String logging) {
        switch(level) {
            case FINE: {
                logDebug(logging);
                break;
            }
            case INFO: {
                logInfo(logging);
                break;
            }
            case WARN: {
                logError(logging);
                break;
            }
        }
    }
}
