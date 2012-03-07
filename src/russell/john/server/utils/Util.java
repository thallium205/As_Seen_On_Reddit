package russell.john.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A utility class to assist with downloading and calling things.
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

}
