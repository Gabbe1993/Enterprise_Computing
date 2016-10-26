import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gabriel Vilen on 2016-10-26.
 */
public class RESTClient {

    public static void main(String[] args) {
        RESTClient client = new RESTClient();

        String url = "http://api-server.eu-gb.mybluemix.net/api/Calendars";
        String eventName = "HUMAN_PARTY";
        String parameter = "{\"name\":\"" + eventName + "\",\"date\":\"2018-06-01\"}";

        client.send(url, "PUT", "Content-Type", parameter);

        parameter = "{\"eventName\":\"" + eventName + "\"}";
        client.send(url + "/timeToDate", "GET", "Accept", parameter);
    }

    // http://api-server.eu-gb.mybluemix.net/api/Calendars/timeToDate?Accept=application/json&eventName=HUMAN_PARTY
    private void send(String urlStr, String methodType, String contentType, String parameter) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod(methodType);
            connection.setRequestProperty(contentType, "application/json");
         //    connection.setRequestProperty("eventName", "\"HUMAN_PARTY\""); TODO: does not wrOk

            System.out.println("Sending: \n" + parameter + "\nto: " + url);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(parameter.getBytes());
            outputStream.flush();

            printResponse(connection);

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

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
