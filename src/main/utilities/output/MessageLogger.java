package utilities.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created By: bsmith on 1/2/14.
 * Package: output
 * Description: This class logs test data.
 */
public class MessageLogger {
    /**
     * Log the suite and name of the test performed.
     * @param suite suite containing test
     * @param test test
     */
    public static Boolean logTestStart(String suite, String test) {
        Logger logger = LogManager.getLogger("[BEGIN TEST]");

        logger.entry();
        logger.info(test + " in suite " + suite);
        logger.exit();

        return true;
    }

    /**
     * Log a specific action in a test.
     * @param page name of page object
     * @param method method logging action
     * @param message specific action
     */
    public static Boolean logAction(String page, String method, String message) {
        Logger logger = LogManager.getLogger("[ACTION]");

        logger.entry();
        logger.error(method + " on page " + page + " - " + message);
        logger.exit();

        return true;
    }

    /**
     * Log passed status of a test.
     * @param suite suite containing test
     * @param test test
     */
    public static Boolean logPass(String suite, String test) {
        Logger logger = LogManager.getLogger("[TEST PASSED]");

        logger.entry();
        logger.info(test + " in suite " + suite);
        logger.exit();

        return true;
    }

    /**
     * Log failed status of a test.
     * @param suite suite containing test
     * @param test test
     */
    public static Boolean logFail(String suite, String test, String message) {
        Logger logger = LogManager.getLogger("[TEST FAILED]");

        logger.entry();
        logger.info(test + " in suite " + suite + " - " + message);
        logger.exit();

        return true;
    }

    /**
     * Log error for debugging.
     * @param page name of page object
     * @param method method logging error
     * @param message message
     */
    public static Boolean logError(String page, String method, String message) {
        Logger logger = LogManager.getLogger("[ERROR]");

        logger.entry();
        logger.info(method + " on page " + page + " - " + message);
        logger.exit();

        return true;
    }

    /**
     * Log information for debugging.
     * @param page name of page object
     * @param method method logging debug info
     * @param message message
     */
    public static Boolean logInfo(String page, String method, String message) {
        Logger logger = LogManager.getLogger("[INFO]");

        logger.entry();
        logger.info(method + " on page " + page + " - " + message);
        logger.exit();

        return true;
    }
}
