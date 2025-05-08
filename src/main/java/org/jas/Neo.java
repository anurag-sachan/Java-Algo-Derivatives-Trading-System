package org.jas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.jas.APIs.client;

public class Neo {

    static String Authorization;

//    static {
//        try {
//            Authorization = Filehandler.readFromFile("Authorization");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static String sessionAuth;

//    static {
//        try {
//            sessionAuth = Filehandler.readFromFile("sessionAuth");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static String sessionsid;

//    static {
//        try {
//            sessionsid = Filehandler.readFromFile("sessionsid");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void checkConn() throws IOException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Auth", Filehandler.readFromFile("sessionAuth"))
                .header("sid", Filehandler.readFromFile("sessionsid"))
                .uri(URI.create("https://gw-napi.kotaksecurities.com/Portfolio/1.0/portfolio/v1/holdings?alt=false"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode()!=200) {
            System.out.println("NEO: re-establishing connection...");
            Algo.connectNeo();
        }
    }

    public static void accessToken() throws IOException, InterruptedException {
        String payload = "grant_type=password&username=client12345&password=6VAlphaNumPass";
//        https://napi.kotaksecurities.com/devportal/applications/1-2-3-4-5/productionkeys/oauth
        String base64_consumerKeyAndSecret= "Rl-consumerKeyNSecret-Nh";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .header("Authorization", "Basic "+base64_consumerKeyAndSecret)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("https://napi.kotaksecurities.com/oauth2/token"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body()).get("access_token");
        String Authorization= "Bearer "+jsonNode.asText();
        Filehandler.writeToFile("Authorization", Authorization);
    }

    static String viewTokenAuth="";
    static String viewTokensid="";
    static String sId;

    static {
        try {
            sId = Filehandler.readFromFile("sId");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewToken() throws IOException, InterruptedException {
        String jsonPayload = "{\n" +
                "    \"mobileNumber\": \"+918004802732\",\n" +
                "    \"password\": \"OGPassword\"\n" +
                "}";;
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
//                .header("accept","*/*")
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Content-Type", "application/json")
                .uri(URI.create("https://gw-napi.kotaksecurities.com/login/1.0/login/v2/validate"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(response.body());
        viewTokenAuth = jsonNode.get("data").get("token").asText();
        viewTokensid = jsonNode.get("data").get("sid").asText();
        sId = jsonNode.get("data").get("hsServerId").asText();
        Filehandler.writeToFile("sId", sId);
    }

    static String userId="351-2-3-4-5bb";

    public static void OTP() throws IOException, InterruptedException, JSONException {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("userId", userId);
        jsonPayload.put("sendEmail", true);
        jsonPayload.put("isWhitelisted", true);

        String jsonString = jsonPayload.toString();
//        System.out.println(jsonString);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
//                .header("accept","*/*")
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Content-Type", "application/json")
                .uri(URI.create("https://gw-napi.kotaksecurities.com/login/1.0/login/otp/generate"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    public static void session() throws IOException, InterruptedException, JSONException {
        Scanner sc= new Scanner(System.in);
        System.out.print("ENTER OTP: ");
        String otp= String.valueOf(sc.nextInt());

        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("userId", userId);
        jsonPayload.put("otp", otp);

        String jsonString = jsonPayload.toString();
//        System.out.println(jsonString);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
//                .header("accept","*/*")
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Auth", viewTokenAuth)
                .header("sid", viewTokensid)
                .header("Content-Type", "application/json")
                .uri(URI.create("https://gw-napi.kotaksecurities.com/login/1.0/login/v2/validate"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(response.body());
        Thread.sleep(300);
        sessionAuth = jsonNode.get("data").get("token").asText();
        Filehandler.writeToFile("sessionAuth", sessionAuth);
        sessionsid = jsonNode.get("data").get("sid").asText();
        Filehandler.writeToFile("sessionsid", sessionsid);
    }

    static String neo_fin_key="neotradeapi";

    public static void placeOrder() throws IOException, InterruptedException, JSONException {
        String jsonData = "{\"am\":\"NO\",\"dq\":\"0\",\"es\":\"nse_fo\",\"mp\":\"0\",\"pc\":\"NRML\",\"pf\":\"N\",\"pr\":\"0.1\",\"pt\":\"L\",\"qt\":\"15\",\"rt\":\"DAY\",\"tp\":\"0\",\"ts\":\"BANKNIFTY23N0844600CE\",\"tt\":\"B\"}";
        String encodedpayload= URLEncoder.encode("jData", StandardCharsets.UTF_8) +"="+ URLEncoder.encode(jsonData, StandardCharsets.UTF_8);
//        System.out.println(jsonString);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(encodedpayload))
//                .header("accept","*/*")
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Auth", Filehandler.readFromFile("sessionAuth"))
                .header("sid", Filehandler.readFromFile("sessionsid"))
                .header("neo-fin-key",neo_fin_key)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("https://gw-napi.kotaksecurities.com/Orders/2.0/quick/order/rule/ms/place?sId="+Filehandler.readFromFile("sId")))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode dataNode= objectMapper.readTree(response.body());
        String orderNo= dataNode.get("nOrdNo").asText();
        int statusCode= dataNode.get("stCode").asInt();
        if(statusCode==200){
            System.out.println("Order Placed ✅, Order No: "+orderNo);
        }
    }

    public static String funds() throws IOException, InterruptedException, JSONException {
        String jsonData = "{\"seg\":\"ALL\",\"exch\":\"ALL\",\"prod\":\"ALL\"}";
        String encodedpayload = URLEncoder.encode("jData", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(jsonData, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(encodedpayload))
                .header("Authorization", Filehandler.readFromFile("Authorization"))
                .header("Auth", Filehandler.readFromFile("sessionAuth"))
                .header("sid", Filehandler.readFromFile("sessionsid"))
                .header("neo-fin-key", neo_fin_key)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("https://gw-napi.kotaksecurities.com/Orders/2.0/quick/user/limits?sId=" + Filehandler.readFromFile("sId")))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        ObjectMapper objectMapper=new ObjectMapper();
        String netMargin= objectMapper.readTree(response.body()).get("Net").asText();
//        System.out.println(netMargin);
        return netMargin;
    }
}
