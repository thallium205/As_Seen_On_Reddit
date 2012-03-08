package russell.john.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Future;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * A utility class to assist with downloading and calling things.
 * 
 * @author John
 * 
 */
public class Util
{
	public static String fetchUrl(String url)
	{
		StringBuilder sb = new StringBuilder();

		try
		{
			URL u = new URL(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
			String line;

			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			reader.close();
		}

		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static void fetchAsyncUrls(String u) throws MalformedURLException
	{

	}

	public static Date GetDateFromUTCString(String utcLongDateTime) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS");
		return new Date(dateFormat.parse(utcLongDateTime).getTime());
	}

	public static Date GetDate()
	{
		return new Date();
	}

	/**
	 * Creates an HTTPRequest
	 * 
	 * @param url
	 *            - The URL to the service
	 * @param name
	 *            - The name in a name/value pair
	 * @param value
	 *            - The value in a name/value pair
	 * @return - The response from the server
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static HTTPRequest AsyncPost(String u, String name, String value) throws IOException
	{
		String v = URLEncoder.encode(value, "UTF-8");

		HTTPRequest request = null;
		URL url;
		try
		{
			url = new URL(u);
			request = new HTTPRequest(url, HTTPMethod.POST);
			String body = name + "=" + v;
			request.setPayload(body.getBytes());

		} catch (MalformedURLException e)
		{
			// Do nothing
		}
		return request;
	}
}
