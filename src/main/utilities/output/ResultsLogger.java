package utilities.output;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.ElapsedTime;
import utilities.output.testrail.APIClient;
import utilities.output.testrail.APIException;

import java.io.IOException;
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
    public final String TEST_RUN = "testrun";

    public ResultsLogger(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        client = new APIClient(url);
        client.setUser(user);
        client.setPassword(password);
    }

    // Add a test result to TestRail via Rest API.
    public void AddResultsToTestRail(String test, Integer statusID, String comment) throws APIException, IOException {
        String testRunProperty = System.getProperty(TEST_RUN, "0");

        //createNewTestPlan();

        Map<String, java.io.Serializable> data = new HashMap<>();
        data.put("status_id", statusID);
        data.put("comment", comment);
        data.put("elapsed", ElapsedTime.getElapsedTime() + "s");

        String testCaseNumber = getTestCaseNumber(test);
        if (!testCaseNumber.equals("none")) {
            JSONObject response = (JSONObject) client.sendPost(
                    "add_result_for_case/" + testRunProperty + "/" + testCaseNumber, data);
            response.clear();
        }else { throw new APIException("TestRail test case could not be found for \"" + test + "\"."); }
    }

    // Create a new test plan if not already created.
    private Boolean createNewTestPlan() throws APIException, IOException {
        Map<String, java.io.Serializable> data = new HashMap<>();
        data.put("suite_id", 1);
        data.put("config_ids", "[1, 2, 3, 4]");

        JSONObject response = (JSONObject) client.sendPost("add_plan/1", data);
        response.clear();
        return true;
    }

    // Get a count of all test cases in a suite.
    private JSONArray getTestCases() throws APIException, IOException {
        return (JSONArray) client.sendGet("get_cases/1&suite_id=1");
    }

    // Get the test case number for a given test name.
    private String getTestCaseNumber(String testName) throws APIException, IOException {
        Integer count = getTestCases().size();

        for (Integer i = 0; i < count; i++) {
            JSONObject testContents = (JSONObject) getTestCases().get(i);
            if (testName.equals(testContents.get("title"))) {
                return testContents.get("id").toString();
            }
        }return "none";
    }
}
