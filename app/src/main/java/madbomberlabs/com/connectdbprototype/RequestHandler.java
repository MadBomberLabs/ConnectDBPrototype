package madbomberlabs.com.connectdbprototype;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler
{

    // METHOD TO SEND httpPostRequest
    // TAKES 2 ARGUMENTS
    //      FIRST ARGUMENT IS THE URL THAT WE SEND THE REQUEST TO
    //      OTHER IS HASHMAP WITH VALUE PAIRS IN THE REQUEST
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams)
    {
        // CREATE URL
        URL url;

        // STRINGBUILDER TO STORE MESSAGE FROM SERVER
        StringBuilder sb = new StringBuilder();
        try
        {
            // INITIALIZE URL
            url = new URL(requestURL);

            // CREATE HTML CONNECTION
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // CONFIGURE CONNECTION PROPERTIES
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // CREATE OUTPUT STREAM
            OutputStream os = conn.getOutputStream();

            // WRITE PARAMETERS TO THE REQUEST
            // USES getPostDataString WHICH IS DEFINED BELOW
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK)
            {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                // READ SERVER RESPONSE
                while ((response = br.readLine()) != null)
                {
                    sb.append(response);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null)
            {
                sb.append(s + "\n");
            }
        }
        catch (Exception ignored)
        {
        }
        return sb.toString();
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}