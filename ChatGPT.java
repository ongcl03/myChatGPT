import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class HttpRequestMultipleHeadersExample {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final String AUTH_TOKEN = "Bearer sk-......";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("😋: ");
            String question = scanner.nextLine();
            if (question.equals("exit")) {
                scanner.close();
                return;
            }
            try {
                chatWithGPT(question);
            } catch (Exception e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }
    }

    public static void chatWithGPT(String question) throws Exception{
        URL obj = new URL(OPENAI_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // set request method
        con.setRequestMethod("POST");

        // set custom headers
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", AUTH_TOKEN);

        // set request body
        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]}";
        con.setDoOutput(true);
        try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
            out.write(requestBody.getBytes());
            out.flush();
        }

        int responseCode = con.getResponseCode();
        // System.out.println("Response Code : " + responseCode);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        // System.out.println("Response Body : " + response.toString());


        // parse response body as a JSON object
        String jsonString = response.toString();
        int contentIndex = jsonString.indexOf("\"content\":");
        String contentValue = jsonString.substring(contentIndex + "\"content\":".length() + 1, jsonString.indexOf("\"", contentIndex + "\"content\":".length() + 2));
        contentValue = contentValue.replace("\\n", "\n"); // replace all occurrences of \n with actual newline character
        contentValue = contentValue.replaceAll("^(\\n)+", ""); // remove all leading newline characters
        System.out.println("🤖: " + contentValue);
        System.out.println("\n");
    }
}
