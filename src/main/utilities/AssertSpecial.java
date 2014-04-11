package utilities;

import org.junit.Assert;
import utilities.output.MessageLogger;
import utilities.output.ResultsLogger;

/**
 * Created By: bsmith on 4/8/14.
 * Package: utilities
 * Description: This class extends jUnit Assert to include logging messages.
 */
public class AssertSpecial extends Assert{
    public static final String REMOTE = "remote";

    static public void assertTrue(String suite, String test,
                                  String message, boolean condition)
            throws Exception {
        ResultsLogger logger = new ResultsLogger("https://ingendev.testrail.com/",
                "crazycabo350@gmail.com",
                "W3bD3v8j@x");

        if (!condition) {
            if (System.getProperty(REMOTE, "false").equals("true")) {
                logger.AddResultsToTestRail(test, 5, message);
            }

            MessageLogger.logFail(suite, test, message);
            fail(message);
        }else {
            if (System.getProperty(REMOTE, "false").equals("true")) {
                logger.AddResultsToTestRail(test, 1, "");
            }
            MessageLogger.logPass(suite, test);
        }
    }
}
