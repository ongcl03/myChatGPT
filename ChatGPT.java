import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class HttpRequestMultipleHeadersExample {

    public static void main(String[] args) throws Exception {
        while (true){
            chatWithGPT();
        }
        
    }

    public static void chatWithGPT() throws Exception{
        String url = "https://api.openai.com/v1/chat/completions";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // set request method
        con.setRequestMethod("POST");

        // set custom headers
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer sk-......");


         // create a Scanner object to read input from the console
         Scanner scanner = new Scanner(System.in);

         // prompt the user to enter a string
         System.out.print("😋: ");
 
         // read the user's input as a string
         String question = scanner.nextLine();

        // set request body
        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]}";
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(requestBody);
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // System.out.println("Response Body : " + response.toString());


        // parse response body as a JSON object
        String jsonString = response.toString();
        int contentIndex = jsonString.indexOf("\"content\":");
        String contentValue = jsonString.substring(contentIndex + "\"content\":".length() + 1, jsonString.indexOf("\"", contentIndex + "\"content\":".length() + 2));
        System.out.print("🤖: " + contentValue);
        System.out.println("\n");
    }



   
}
