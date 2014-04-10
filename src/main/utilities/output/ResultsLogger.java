package utilities.output;

import org.json.simple.JSONObject;
import utilities.output.testrail.APIClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By: bsmith on 4/8/14.
 * Package: utilities.output
 * Description: This class connects to a Restful API so test data can be sent to a server.
 */
public class ResultsLogger {
    APIClient client;
    String url, user, password = "";
    public final String TEST_RUN = "testrun.number";

    public ResultsLogger(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    //Add a test result to TestRail via Rest API.
    public void AddResultsToTestRail(Integer testCase, Integer statusID, Long elapsedTime,
                                            String comment) throws Exception {
        client = new APIClient(url);
        client.setUser(user);
        client.setPassword(password);

        createNewTestPlan();

        Map data = new HashMap();
        data.put("status_id", statusID);
        data.put("comment", comment);
        data.put("elapsed", getElapsedTime(elapsedTime) + "s");

        String testRunProperty = System.getProperty(TEST_RUN, "0");
        JSONObject response = (JSONObject) client.sendPost("add_result_for_case/" + testRunProperty + "/" + testCase, data);
        response.clear();
    }

    //Create a new test plan if not already created.
    private Boolean createNewTestPlan() {
        return true;
    }

    //Calculate elapsed time in whole Integer seconds by rounding up if necessary.
    private Integer getElapsedTime(Long elapsedTime) {
        Integer elapsedTimeInSeconds = Math.round((float) ((int) (elapsedTime / 1000000) * .001));
        elapsedTimeInSeconds = elapsedTimeInSeconds > 1 ? elapsedTimeInSeconds : 1;
        return elapsedTimeInSeconds;
    }
}
