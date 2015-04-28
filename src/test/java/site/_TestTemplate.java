package site;

import org.junit.*;
import ru.yandex.qatools.allure.annotations.*;
import site.pages._ChildPageTemplate;
import site.pages._PageTemplate;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created By: Brian Smith on 4/27/15.
 * Package: site
 * Description: This test class can be cloned and used to create new test suites more quickly.
 */

@Title("Add a custom class name")
@Description("Add a useful description about this test suite and its' contents")
public class _TestTemplate extends BaseTest {

    private _PageTemplate page;

    /* Variables annotated with @Parameter will display its' value in the Allure report for each test in this suite
     * assigning a value to it.
     */
    @Parameter("Add parameter name")
    private String parameter;

    // This method will execute a single time for this suite and must be static.
    @BeforeClass
    public static void oneTimeSetUp() {
        // This is a great place to import or create test data for the suite.
    }

    // This method will execute once before each test in this suite.
    @Before
    public void setUp() {
        /* Open the browser chosen at runtime. The required string parameter is required by the SauceConnect cloud.
         * You can enter an empty string if not using it.
         *
         * Implementing openBrowser("") can be used @BeforeClass, but only do so if you understand the consequences.
         * If multiple tests exist and a critical error occurs, all other tests may not execute as the browser cannot
         * be recovered for the next test. Also, placing it here may add several seconds to the runtime of this suite,
         * but allow tests to not rely on the status of the browser before each.
         */
        openBrowser("");

        page = new _PageTemplate(localDriver); // Create a new instance of page.
        page.get(); // Attempt to load the page.
    }

    // Example tests
    @Features("Add application feature description")
    @Stories("Add story description for feature")
    @Test
    public void testAlpha() {
        /* Since the page is accessible in each test you can execute actions directly, abstracting its' individual
         * methods out of each test. This way, if a change is required in the page class the test is never affected.
         */
        page.userAction();
        assertTrue("Add an error message", true); // x
    }

    @Features("Add application feature description")
    @Stories({"Add one story", "or two", "or three for a given feature"})
    @Test
    public void testBravo() {
        page.userAction();
        assertThat("Add an error message", "", is(equalTo(""))); // x
    }

    @Ignore // Tests can be ignored by JUnit allowing you to create empty methods early on and add steps later.
    @Test()
    public void testCharlie() {
        // Sending input through a child page allows it to be used to execute methods within load().
        _ChildPageTemplate childPage = new _ChildPageTemplate(localDriver, "alpha", "bravo");
        childPage.get();
    }

    // This method will execute once after each test in this suite regardless of it passing, or not.
    @After
    public void tearDown() {
        /* Screen shot is taken after each test regardless of the status. If the browser is somehow not available
         * the method below can handle null values.
         */
        getScreenShot();

        /* Close the browser after each test completes its' execution. If you choose implement openBrowser("")
         * @BeforeClass, place this in the @AfterClass method below .
         */
        quit();
    }

    // This method will execute a single time after all tests execute and must be static.
    @AfterClass
    public static void finalTearDown() {

    }
}
