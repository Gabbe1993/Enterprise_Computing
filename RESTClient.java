import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Course: Enterprise Computing
 * Assignment: 1
 * Created by Gabriel Vilen on 2016-10-26.
 */
public class RESTClient {

    public static void main(String[] args) {
        RESTClient client = new RESTClient();

        String url = "http://api-server.eu-gb.mybluemix.net/api/Calendars/";
        String eventName = "NO_PANTS_DAY";
        String parameter = "{\"name\":\"" + eventName + "\",\"date\":\"2018-06-01\"}";

        client.post(url, parameter);
        client.get(url + "timeToDate", eventName);
    }

    /**
     * Sends a HTTP GET method
     *
     * @param urlStr the url to send the method to
     * @param param  the parameter extended to the url
     */
    private void get(String urlStr, String param) {
        try {
            URL url = new URL(urlStr + "?eventName=" + param);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            printSend(param);
            printResponse(connection);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends a HTTP POST method
     *
     * @param urlStr the url to send the method to
     * @param param  the parameters to extend to the header file
     */
    private void post(String urlStr, String param) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();

            printSend(param);
            printResponse(connection);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void printSend(String param) {
        System.out.println("Sending: \n" + param);
    }

    /**
     * Prints the output from the connection stream
     *
     * @param connection the open HTTP connection object
     * @throws IOException If response code is not 200 (OK)
     */
    private void printResponse(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Connection failed : Error " + connection.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String response;
        System.out.println("Response: ");

        while ((response = reader.readLine()) != null) {
            System.out.println(response);
        }
    }
}
