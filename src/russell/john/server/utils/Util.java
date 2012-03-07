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
	 * Creates an HTTP post
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
	public static void Post(String url, String name, String value) throws IOException
	{
			String v = URLEncoder.encode(value, "UTF-8");

		
			URL urlPost = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlPost.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(name + "=" + v);
			writer.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				// OK
			} 
			
			else
			{
				connection.toString();
			}
	}
}
