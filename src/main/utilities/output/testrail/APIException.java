/**
 * TestRail API binding for Java (API v2, available since TestRail 3.0)
 *
 * Learn more:
 *
 * http://docs.gurock.com/testrail-api2/start
 * http://docs.gurock.com/testrail-api2/accessing
 *
 * Copyright Gurock Software GmbH
 */

package utilities.output.testrail;
 
public class APIException extends Exception
{
	public APIException(String message)
	{
		super(message);
	}
}
