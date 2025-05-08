package org.jas;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

// zer-tkn
// import static org.jas.Algo.zerodha_enctoken;

public class APIs {
    public static final String ZERODHA_API_URL = "https://api.kite.trade";

    static HttpClient client = HttpClient.newHttpClient();

//    public static void login() throws IOException, InterruptedException {
//        String payload = "user_id=UVD123&password=PASS%40WORD";
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(payload))
//                .header("Authorization", zerodha_enctoken)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .uri(URI.create(ZERODHA_API_URL+"/orders/regular"))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(response.body());
//        JsonNode dataNode = jsonNode.get("data").get("order_id");
//    }

// zer-tkn
//     public static String enctoken() throws IOException, InterruptedException {
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", Filehandler.readFromFile("enctoken"))
//                 .uri(URI.create(ZERODHA_API_URL + "/user/profile"))
//                 .build();
//         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         String status = objectMapper.readTree(response.body()).get("status").asText();
//         if (status.equals("error")) {
//             Scanner sc = new Scanner(System.in);
//             System.out.print("Enter New Token: ");
//             String token = sc.nextLine();
//             zerodha_enctoken = "enctoken " + token;
//             Filehandler.writeToFile("enctoken", zerodha_enctoken);
//         }
// //        System.out.println(zerodha_enctoken);
//         return zerodha_enctoken;
//     }

// zer-tkn
//     public static void userDetails() throws IOException, InterruptedException {
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
// //                .header("accept", "application/json")
//                 .uri(URI.create(ZERODHA_API_URL + "/user/profile"))
//                 .build();

//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

// //        System.out.println(jsonResponse.body()); // method-1 raw response

//         try {// method 2- JsonNode to traverse easily
//             ObjectMapper objectMapper = new ObjectMapper();

//             // Parse the JSON response
//             JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
//             JsonNode dataNode = jsonNode.get("data");
// //            System.out.println("data: " + dataNode);
//             String userName = dataNode.get("user_name").asText();
//             String user_id = dataNode.get("user_id").asText();
//             System.out.println("----------------------USER DETAILS------------------------");
//             System.out.println(user_id + " : " + userName);
// //            System.out.println("----------------------------------------------------------");
//             System.out.println();
//         } catch (Exception e) {
//             System.out.println("login failed!");
//         }

//        // parse JSON // method-3 create 2nd class & define accordingly
//        ObjectMapper mapper = new ObjectMapper();
//        List<Post> posts = mapper.readValue(response.body(), new TypeReference<List<Post>>() {});
//
//         posts.forEach(post -> {
//             System.out.println(post.getTitle());
//         });
//        posts.forEach(System.out::println);
//     }

//     zer-tkn
//     public static void getMOP() throws IOException, InterruptedException {
//         ObjectMapper objectMapper = new ObjectMapper();

//         HttpRequest requestMargin = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/user/margins"))
//                 .build();
//         HttpResponse<String> responseMargin = client.send(requestMargin, HttpResponse.BodyHandlers.ofString());
// //        System.out.println(responseMargin.body());
//         JsonNode jsonNode1 = objectMapper.readTree(responseMargin.body());
//         JsonNode dataNode1 = jsonNode1.get("data").get("equity");
// //        String netBal= dataNode1.get("net").asText();
// //        String openBal= dataNode1.get("available").get("opening_balance").asText();
// //        String liveBal= dataNode1.get("available").get("live_balance").asText();
//         System.out.println("--------------------------MARGIN--------------------------");
//         System.out.println(dataNode1);
// //        System.out.printf("Net Bal: %s \n Opening Bal: %s \n Live Bal: %s ", netBal, openBal, liveBal);
//         System.out.println();

//         HttpRequest requestOrders = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/orders"))
//                 .build();
//         HttpResponse<String> responseOrders = client.send(requestOrders, HttpResponse.BodyHandlers.ofString());
// //        System.out.println(responseOrders.body());
//         JsonNode jsonNode2 = objectMapper.readTree(responseOrders.body());
//         JsonNode dataNode2 = jsonNode2.get("data");
//         System.out.println("--------------------------ORDERS--------------------------");
//         System.out.println(dataNode2);
//         System.out.println();

//         HttpRequest requestPositions = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/portfolio/positions"))
//                 .build();
//         HttpResponse<String> responsePositions = client.send(requestPositions, HttpResponse.BodyHandlers.ofString());
// //        System.out.println(responsePositions.body());
//         JsonNode jsonNode3 = objectMapper.readTree(responsePositions.body());
//         JsonNode dataNode3 = jsonNode3.get("data");
//         System.out.println("------------------------POSITIONS-------------------------");
//         System.out.println(dataNode3);
//         System.out.println();
//     }

    public static double getPCR(String symbol) throws IOException, InterruptedException {
        double PCR = 0.0;
        boolean success = false;
        while(!success) { 
            ObjectMapper objectMapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://groww.in/v1/api/stocks_fo_data/v1/contracts/" + symbol + "/top"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if(jsonNode.size()!=0) {
                JsonNode dataNode = jsonNode.get("pcr");
                PCR = dataNode.asDouble();
                System.out.println("PCR | " + PCR);
                success=true;
            }
            if(!success) {
                System.out.println("fetching LTP...");
                Thread.sleep(1000);
            }
        }
        return PCR;
    }

//    public static void getTargetNSignals() throws IOException, InterruptedException {
//        ObjectMapper objectMapper=new ObjectMapper();
////        String jwt="eyJ-jwt-rw";
////        String symbol="NSE:BANKNIFTY";
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create("https://charts-storage.tradingview.com/charts-storage/get/layout/ywJ2uUIw/sources?chart_id=_shared&jwt=eyJ-jwt-rw&symbol=NSE:BANKNIFTY&brokerName="))
////                .uri(URI.create(" https://charts-storage.tradingview.com/charts-storage/get/layout/ywJ2uUIw/sources?chart_id=_shared&jwt="+jwt+"&symbol="+symbol+"&brokerName="))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        JsonNode jsonNode = objectMapper.readTree(response.body());

//        HashSet<Integer> fibVal=new HashSet<>();
//        JsonNode sourceNode = jsonNode.get("payload").get("sources");
//        if(sourceNode.isArray()){ //not an array
//            for (JsonNode source: sourceNode){
//                int fibNode;
//                if(!sourceNode.get(String.valueOf(source)).get("state").get("type").equals("LineToolFibRetracement")){
//                    continue;
//                }else{
//                    fibNode = sourceNode.get(String.valueOf(source)).get("state").get("points").get(0).get("price").asInt();
//                    fibVal.add(fibNode);
//                }
//            }
//        }
//        System.out.println(fibVal);

//        String up="252v5z", down="";
//        JsonNode dataNodeUpPoint1 = jsonNode.get("payload").get("sources").get(up).get("state").get("points").get(0).get("price");
//        JsonNode dataNodeUpPoint2 = jsonNode.get("payload").get("sources").get(up).get("state").get("points").get(1).get("price");
//        System.out.println("UP | "+dataNodeUpPoint1+", "+dataNodeUpPoint2);
//
//        JsonNode dataNodeDownPoint1 = jsonNode.get("payload").get("sources").get(down).get("state").get("points").get(0).get("price");
//        JsonNode dataNodeDownPoint2 = jsonNode.get("payload").get("sources").get(down).get("state").get("points").get(0).get("price");
//        System.out.println("DOWN | "+dataNodeDownPoint1+", "+dataNodeDownPoint2);
//    }

    public static int getSymbolData(String symbol) throws IOException, InterruptedException {
        int liveValue=0;
        boolean success=false;
        while (!success) {
            String payload = "{\"exchangeAggReqMap\":{\"NSE\":{\"priceSymbolList\":[],\"indexSymbolList\":[\"NIFTY\",\"BANKNIFTY\",\"FINNIFTY\",\"NIFTYMIDSELECT\",\"NIFTYMIDCAP\"]},\"BSE\":{\"priceSymbolList\":[],\"indexSymbolList\":[\"1\",\"2\"]}}}";
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .header("Content-Type", "application/json")
                    .uri(URI.create("https://groww.in/v1/api/stocks_data/v1/tr_live/segment/CASH/latest_aggregated"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                liveValue = jsonNode.get("exchangeAggRespMap").get("NSE").get("indexLivePointsMap").get(symbol).get("value").asInt();
                success=true;
            }
            if(!success) {
                System.out.println("fetching live trading price...");
                Thread.sleep(1000);
            }
        }
        return liveValue;
    }

    //kite-test
//     public static int getSymbolData(String symbol) throws IOException, InterruptedException {
//         String encodedString = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/quote?i=" + encodedString))
//                 .build();
//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
//         JsonNode dataNode = jsonNode.get("data").get(symbol);
// //        System.out.println(symbol +" | "+dataNode);
//         JsonNode dataNodeOHLC = jsonNode.get("data").get(symbol).get("ohlc");
// //        System.out.println("OHLC | "+dataNodeOHLC);
//         JsonNode dataNodeLTP = jsonNode.get("data").get(symbol).get("last_price");
//         int LTP = dataNodeLTP.asInt();

//         if (symbol.startsWith("NFO")) {
//             System.out.println(symbol + " | LTP | " + LTP);

//             JsonNode dataNodeLow = jsonNode.get("data").get(symbol).get("ohlc").get("low");
//             System.out.println(symbol + " | Day Low | " + dataNodeLow);
//         }
//         if (symbol.startsWith("NSE")) {
//             JsonNode dataNodeLow = jsonNode.get("data").get(symbol).get("net_change");
//             System.out.println(symbol + " | Net change | " + dataNodeLow);
//         }
// //        System.out.println("LTP | "+dataNodeLTP);

//         return LTP;

//        HttpRequest request2 = HttpRequest.newBuilder()
//                .GET()
//                .header("Authorization", zerodha_enctoken)
//                .uri(URI.create(ZERODHA_API_URL + "/quote/ohlc?i="+symbol))
//                .build();
//        HttpResponse<String> jsonResponse2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        JsonNode jsonNode2 = objectMapper2.readTree(jsonResponse2.body());
//        JsonNode dataNode2 = jsonNode2.get("data").get(symbol).get("ohlc");
//        System.out.println("OHLC > "+dataNode2);
//
//        HttpRequest request3 = HttpRequest.newBuilder()
//                .GET()
//                .header("Authorization", zerodha_enctoken)
//                .uri(URI.create(ZERODHA_API_URL + "/quote/ltp?i="+symbol))
//                .build();
//        HttpResponse<String> jsonResponse3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper3 = new ObjectMapper();
//        JsonNode jsonNode3 = objectMapper3.readTree(jsonResponse3.body());
//        JsonNode dataNode3 = jsonNode3.get("data").get(symbol).get("last_price");
//        System.out.println("LTP > "+dataNode3);
//     }

//     kite-test
        public static double LTP(String contract) throws IOException, InterruptedException {
            double dataNodeLTP = 0.0;
            boolean success = false;
            while(!success) { 
                String encodedString = URLEncoder.encode(contract.split(":")[1], StandardCharsets.UTF_8);
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://groww.in/v1/api/stocks_fo_data/v1/tr_live_prices/exchange/NSE/segment/FNO/"+encodedString+"/latest"))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                if(jsonNode.size()!=0) {
                    dataNodeLTP= jsonNode.get("ltp").asDouble();
                    success=true;
                }
                if(!success) {
                    System.out.println("fetching LTP...");
                    Thread.sleep(1000);
                }
            }
        return dataNodeLTP;
    }

//     public static double LTP(String symbol) throws IOException, InterruptedException {
//         double dataNodeLTP = 0.0;
//         boolean success = false;
//         while(!success) {
//             String encodedString = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
//             HttpRequest request = HttpRequest.newBuilder()
//                     .GET()
//                     .header("Authorization", zerodha_enctoken)
//                     .uri(URI.create(ZERODHA_API_URL + "/quote?i=" + encodedString))
//                     .build();
//             HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//             ObjectMapper objectMapper = new ObjectMapper();
//             JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
//             if(jsonNode.get("data").size()!=0) {
//                 dataNodeLTP = jsonNode.get("data").get(symbol).get("last_price").asDouble();
//                 System.out.println(symbol + " | LTP | " + dataNodeLTP);
//                 success = true;
//             }
//             if(!success) {
//                 System.out.println("fetching LTP...");
//                 Thread.sleep(1000);
//             }
//         }
//         return dataNodeLTP;
//     }

        public static boolean lowPriceSIGNAL(String contract) throws IOException, InterruptedException {
            boolean res=false;
            boolean success=false;
            while(!success){
                String encodedString = URLEncoder.encode(contract.split(":")[1], StandardCharsets.UTF_8);
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://groww.in/v1/api/stocks_fo_data/v1/tr_live_prices/exchange/NSE/segment/FNO/"+encodedString+"/latest"))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                if(jsonNode.size()!=0){
                    Double dataNodeLTP = jsonNode.get("ltp").asDouble();
                    Double dataNodeLow = jsonNode.get("low").asDouble();
                    res = dataNodeLTP <= 1.08 * dataNodeLow;
                    success=true;
                }
                if(!success) Thread.sleep(1000);
            }
            return res;
        }

//     kite-test
//     public static boolean lowPriceSIGNAL(String symbol) throws IOException, InterruptedException {
//         String encodedString = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/quote?i=" + encodedString))
//                 .build();
//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
//         Double dataNodeLTP = jsonNode.get("data").get(symbol).get("last_price").asDouble();

//         Double dataNodeLow = jsonNode.get("data").get(symbol).get("ohlc").get("low").asDouble();
//         System.out.println(symbol + " | Day Low | " + dataNodeLow);

//         return dataNodeLTP <= 1.08 * dataNodeLow;
//     }

// zer-tkn
//     public static void placeOrder() throws IOException, InterruptedException {
//         String payload = "variety=regular&exchange=NFO&tradingsymbol=BANKNIFTY23O0452000CE&transaction_type=BUY&order_type=MARKET&quantity=15&price=0&product=NRML&validity=DAY&disclosed_quantity=0&trigger_price=0&squareoff=0&stoploss=0&trailing_stoploss=0&user_id=UVD478&";
//         HttpRequest request = HttpRequest.newBuilder()
//                 .POST(HttpRequest.BodyPublishers.ofString(payload))
//                 .header("Authorization", zerodha_enctoken)
//                 .header("Content-Type", "application/x-www-form-urlencoded")
//                 .uri(URI.create(ZERODHA_API_URL + "/orders/regular"))
//                 .build();
//         HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//         System.out.println(response.body());

//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(response.body());
//         JsonNode dataNode = jsonNode.get("data").get("order_id");
//     }

// zer-tkn
//     public static void getCandleData() throws IOException, InterruptedException {
// //        String encodedString= URLEncoder.encode(symbolCode,StandardCharsets.UTF_8);
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/instruments/historical/10426370/15minute?user_id=UVD478&oi=0&from=2023-11-01&to=2023-11-02"))
//                 .build();
//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
// //        System.out.println(jsonNode);
//         JsonNode dataNode = jsonNode.get("data").get("candles");
//         System.out.println(dataNode);
// //
// ////        Date time=new Date();
// //        int firstIndex=jsonNode.get("data").get("candles").size()-14;
// //        int lastIndex=jsonNode.get("data").get("candles").size()-1;
// //        float gain = 0,loss=0;
// //        Date date=new Date();
// ////        System.out.println(date);
// //        int timeHour=Integer.parseInt(date.toString().split(":")[0].split(" ")[3]);
// //        int timeMinute=Integer.parseInt(date.toString().split(":")[1]);
// //        int candleHour=Integer.parseInt(jsonNode.get("data").get("candles").get(lastIndex).get(0).toString().split(":")[0].split("T")[1]);
// //        int candleMinute=Integer.parseInt(jsonNode.get("data").get("candles").get(lastIndex).get(0).toString().split(":")[1]);
// ////        if (candleHour<timeHour || (candleHour==timeHour && candleMinute<=timeMinute)){
// //            for (int i = firstIndex; i <= lastIndex; i++, lastIndex++) {
// //                for (int j = i; j < lastIndex ; j++) {
// //                    float percentChange = (Float.parseFloat(jsonNode.get("data").get("candles").get(j).get(4).toString()) - Float.parseFloat(jsonNode.get("data").get("candles").get(j-1).get(4).toString())) * 100 / Float.parseFloat(jsonNode.get("data").get("candles").get(firstIndex - 1).get(4).toString());
// //                    if (percentChange > 0) gain += percentChange;
// //                    else loss += Math.abs(percentChange);
// //                }
// //                float RSI=100-(100/(1+(gain/loss)));
// //                System.out.println("RSI: "+RSI);
// //            }
// //            JsonNode lastCandle=jsonNode.get("data").get("candles").get(lastIndex);

// //            System.out.println(dataNode);
// //            System.out.println(lastCandle);
// //        }
//     }

    //kite-test
//     public static int[] highsLows(String str) throws IOException, InterruptedException {
//         String encodedString = URLEncoder.encode(str, StandardCharsets.UTF_8);
//         Date date=new Date();
// //        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
// //        System.out.println(date);
// //        String day=date.toString().split(" ")[0];
// //        System.out.printf("DnT | %s | ",day);
//         int prevdate=Integer.parseInt(formatter.format(date).split("-")[2]);
//         // int prevdate=Integer.parseInt(formatter.format(date).split("-")[2])-1;
//         String reqDate= formatter.format(date).substring(0,8).concat(String.valueOf(prevdate));

//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL+"/instruments/historical/"+encodedString+"/hour?from="+reqDate+"+09:15:00&to="+reqDate+"+15:30:00"))
//                 .build();
//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());

//         JsonNode dataNode = jsonNode.get("data").get("candles");
//         int len= dataNode.size();
//         float open=0, high=0, low=Float.MAX_VALUE, close=Float.MAX_VALUE, max=0, min=0;
//         for (int i = 0; i < len ; i++) {
//             open=Math.max(open,Float.parseFloat(String.valueOf(dataNode.get(i).get(1))));
//             high=Math.max(high,Float.parseFloat(String.valueOf(dataNode.get(i).get(2))));
//             low=Math.min(low,Float.parseFloat(String.valueOf(dataNode.get(i).get(3))));
//             close=Math.min(close,Float.parseFloat(String.valueOf(dataNode.get(i).get(4))));
//             max=(open+high)/2;
//             min=(low+close)/2;
//         }
//         return new int[]{(int) max, (int) min};
//     }

    public static int[] highsLows(String str) throws IOException, InterruptedException {
        int[] highlow={0,0};
        boolean success=false;
        while (!success) {
            String payload = "{\"exchangeAggReqMap\":{\"NSE\":{\"priceSymbolList\":[],\"indexSymbolList\":[\"NIFTY\",\"BANKNIFTY\",\"FINNIFTY\",\"NIFTYMIDSELECT\",\"NIFTYMIDCAP\"]},\"BSE\":{\"priceSymbolList\":[],\"indexSymbolList\":[\"1\",\"2\"]}}}";
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .header("Content-Type", "application/json")
                    .uri(URI.create("https://groww.in/v1/api/stocks_data/v1/tr_live/segment/CASH/latest_aggregated"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                highlow= new int[]{jsonNode.get("exchangeAggRespMap").get("NSE").get("indexLivePointsMap").get(str).get("high").asInt(),jsonNode.get("exchangeAggRespMap").get("NSE").get("indexLivePointsMap").get(str).get("low").asInt()};
                success=true;
            }
        if(!success) Thread.sleep(1000);
        }
        return highlow;
    }


//    public static HashSet getAllCloseVals(String str) throws IOException, InterruptedException {
//        String instrument_token=str;
//        String encoded_instrument_token=URLEncoder.encode(instrument_token,StandardCharsets.UTF_8);
//
//        // Create a SimpleDateFormat object for the desired date format
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        // Get the current date and time
//        Calendar calendar = Calendar.getInstance();
//        Date currentDate = calendar.getTime();
//        // Subtract one day from the current date
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        Date previousDate = calendar.getTime();
//        // Check if the previous date is in the previous month
//        if (calendar.get(Calendar.MONTH) != Calendar.getInstance().get(Calendar.MONTH)) {
//            // If it's a new month, set the day to the last day of the previous month
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            calendar.add(Calendar.DAY_OF_MONTH, -1);
//            previousDate = calendar.getTime();
//        }
//        String formattedPreviousDate = sdf.format(previousDate);
//        String formattedCurrentDate = sdf.format(currentDate);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .header("Authorization", zerodha_enctoken)
//                .uri(URI.create(ZERODHA_API_URL + "/instruments/historical/"+encoded_instrument_token+"/15minute?user_id=UVD478&oi=0&from="+formattedPreviousDate+"&to="+formattedCurrentDate))
//                .build();
//        HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
//        HashSet<Double> set=new HashSet<>();
////        int n= 4;
////        for (int i = 0; i < n ; i++) {
//            Double closeVal = jsonNode.get("data").get("candles").get(i).get(4).asDouble();
////            System.out.println(closeVal);
//            set.add(closeVal);
//        }
//        return set;
//    }

//    public static void getDaysMins(String str) throws IOException, InterruptedException {
//        Double min=1000.0d;
//        String op= "NFO:"+optionContract(str);
//        Kite.getSymbolData(op);
////        String str=Kite.getSymbolToken(op);
////        Kite.getCandleData();
//        String instrument_token=Kite.getSymbolToken(op);
//        System.out.println(Kite.getAllCloseVals(instrument_token));
//        HashSet closeVals=Kite.getAllCloseVals(instrument_token);
//        for(Object val: closeVals){
//            Double currentVal=Double.parseDouble(val.toString());
//            if(currentVal<min) {
//                min = currentVal;
//            }
//        }
//        System.out.println("minimum: "+min);
//    }

//    public static void indicators(String str) throws IOException, InterruptedException {
//        String baseURL="https://scanner.tradingview.com/symbol";
//
//        String FINNIFTY="NSE:FINNIFTY1!";
//        String BANKNIFTY="NSE:BANKNIFTY";
//        String encodedFINNIFTY=URLEncoder.encode(FINNIFTY,StandardCharsets.UTF_8);
//        String encodedBANKNIFTY=URLEncoder.encode(BANKNIFTY,StandardCharsets.UTF_8);
//
//        String fieldsHr="Recommend.Other,Recommend.All,Recommend.MA,RSI,RSI[1],Stoch.K,Stoch.D,Stoch.K[1],Stoch.D[1],CCI20,CCI20[1],ADX,ADX+DI,ADX-DI,ADX+DI[1],ADX-DI[1],AO,AO[1],AO[2],Mom,Mom[1],MACD.macd,MACD.signal,Rec.Stoch.RSI,Stoch.RSI.K,Rec.WR,W.R,Rec.BBPower,BBPower,Rec.UO,UO,EMA10,close,SMA10,EMA20,SMA20,EMA30,SMA30,EMA50,SMA50,EMA100,SMA100,EMA200,SMA200,Rec.Ichimoku,Ichimoku.BLine,Rec.VWMA,VWMA,Rec.HullMA9,HullMA9,Pivot.M.Classic.S3,Pivot.M.Classic.S2,Pivot.M.Classic.S1,Pivot.M.Classic.Middle,Pivot.M.Classic.R1,Pivot.M.Classic.R2,Pivot.M.Classic.R3,Pivot.M.Fibonacci.S3,Pivot.M.Fibonacci.S2,Pivot.M.Fibonacci.S1,Pivot.M.Fibonacci.Middle,Pivot.M.Fibonacci.R1,Pivot.M.Fibonacci.R2,Pivot.M.Fibonacci.R3,Pivot.M.Camarilla.S3,Pivot.M.Camarilla.S2,Pivot.M.Camarilla.S1,Pivot.M.Camarilla.Middle,Pivot.M.Camarilla.R1,Pivot.M.Camarilla.R2,Pivot.M.Camarilla.R3,Pivot.M.Woodie.S3,Pivot.M.Woodie.S2,Pivot.M.Woodie.S1,Pivot.M.Woodie.Middle,Pivot.M.Woodie.R1,Pivot.M.Woodie.R2,Pivot.M.Woodie.R3,Pivot.M.Demark.S1,Pivot.M.Demark.Middle,Pivot.M.Demark.R1";
//        String fields15Min="Recommend.Other|15,Recommend.All|15,Recommend.MA|15,RSI|15,RSI[1]|15,Stoch.K|15,Stoch.D|15,Stoch.K[1]|15,Stoch.D[1]|15,CCI20|15,CCI20[1]|15,ADX|15,ADX+DI|15,ADX-DI|15,ADX+DI[1]|15,ADX-DI[1]|15,AO|15,AO[1]|15,AO[2]|15,Mom|15,Mom[1]|15,MACD.macd|15,MACD.signal|15,Rec.Stoch.RSI|15,Stoch.RSI.K|15,Rec.WR|15,W.R|15,Rec.BBPower|15,BBPower|15,Rec.UO|15,UO|15,EMA10|15,close|15,SMA10|15,EMA20|15,SMA20|15,EMA30|15,SMA30|15,EMA50|15,SMA50|15,EMA100|15,SMA100|15,EMA200|15,SMA200|15,Rec.Ichimoku|15,Ichimoku.BLine|15,Rec.VWMA|15,VWMA|15,Rec.HullMA9|15,HullMA9|15,Pivot.M.Classic.S3|15,Pivot.M.Classic.S2|15,Pivot.M.Classic.S1|15,Pivot.M.Classic.Middle|15,Pivot.M.Classic.R1|15,Pivot.M.Classic.R2|15,Pivot.M.Classic.R3|15,Pivot.M.Fibonacci.S3|15,Pivot.M.Fibonacci.S2|15,Pivot.M.Fibonacci.S1|15,Pivot.M.Fibonacci.Middle|15,Pivot.M.Fibonacci.R1|15,Pivot.M.Fibonacci.R2|15,Pivot.M.Fibonacci.R3|15,Pivot.M.Camarilla.S3|15,Pivot.M.Camarilla.S2|15,Pivot.M.Camarilla.S1|15,Pivot.M.Camarilla.Middle|15,Pivot.M.Camarilla.R1|15,Pivot.M.Camarilla.R2|15,Pivot.M.Camarilla.R3|15,Pivot.M.Woodie.S3|15,Pivot.M.Woodie.S2|15,Pivot.M.Woodie.S1|15,Pivot.M.Woodie.Middle|15,Pivot.M.Woodie.R1|15,Pivot.M.Woodie.R2|15,Pivot.M.Woodie.R3|15,Pivot.M.Demark.S1|15,Pivot.M.Demark.Middle|15,Pivot.M.Demark.R1|15";
//        String fields5Min="Recommend.Other|5,Recommend.All|5,Recommend.MA|5,RSI|5,RSI[1]|5,Stoch.K|5,Stoch.D|5,Stoch.K[1]|5,Stoch.D[1]|5,CCI20|5,CCI20[1]|5,ADX|5,ADX+DI|5,ADX-DI|5,ADX+DI[1]|5,ADX-DI[1]|5,AO|5,AO[1]|5,AO[2]|5,Mom|5,Mom[1]|5,MACD.macd|5,MACD.signal|5,Rec.Stoch.RSI|5,Stoch.RSI.K|5,Rec.WR|5,W.R|5,Rec.BBPower|5,BBPower|5,Rec.UO|5,UO|5,EMA10|5,close|5,SMA10|5,EMA20|5,SMA20|5,EMA30|5,SMA30|5,EMA50|5,SMA50|5,EMA100|5,SMA100|5,EMA200|5,SMA200|5,Rec.Ichimoku|5,Ichimoku.BLine|5,Rec.VWMA|5,VWMA|5,Rec.HullMA9|5,HullMA9|5,Pivot.M.Classic.S3|5,Pivot.M.Classic.S2|5,Pivot.M.Classic.S1|5,Pivot.M.Classic.Middle|5,Pivot.M.Classic.R1|5,Pivot.M.Classic.R2|5,Pivot.M.Classic.R3|5,Pivot.M.Fibonacci.S3|5,Pivot.M.Fibonacci.S2|5,Pivot.M.Fibonacci.S1|5,Pivot.M.Fibonacci.Middle|5,Pivot.M.Fibonacci.R1|5,Pivot.M.Fibonacci.R2|5,Pivot.M.Fibonacci.R3|5,Pivot.M.Camarilla.S3|5,Pivot.M.Camarilla.S2|5,Pivot.M.Camarilla.S1|5,Pivot.M.Camarilla.Middle|5,Pivot.M.Camarilla.R1|5,Pivot.M.Camarilla.R2|5,Pivot.M.Camarilla.R3|5,Pivot.M.Woodie.S3|5,Pivot.M.Woodie.S2|5,Pivot.M.Woodie.S1|5,Pivot.M.Woodie.Middle|5,Pivot.M.Woodie.R1|5,Pivot.M.Woodie.R2|5,Pivot.M.Woodie.R3|5,Pivot.M.Demark.S1|5,Pivot.M.Demark.Middle|5,Pivot.M.Demark.R1|5";
//        String fields1Min="Recommend.Other|1,Recommend.All|1,Recommend.MA|1,RSI|1,RSI[1]|1,Stoch.K|1,Stoch.D|1,Stoch.K[1]|1,Stoch.D[1]|1,CCI20|1,CCI20[1]|1,ADX|1,ADX+DI|1,ADX-DI|1,ADX+DI[1]|1,ADX-DI[1]|1,AO|1,AO[1]|1,AO[2]|1,Mom|1,Mom[1]|1,MACD.macd|1,MACD.signal|1,Rec.Stoch.RSI|1,Stoch.RSI.K|1,Rec.WR|1,W.R|1,Rec.BBPower|1,BBPower|1,Rec.UO|1,UO|1,EMA10|1,close|1,SMA10|1,EMA20|1,SMA20|1,EMA30|1,SMA30|1,EMA50|1,SMA50|1,EMA100|1,SMA100|1,EMA200|1,SMA200|1,Rec.Ichimoku|1,Ichimoku.BLine|1,Rec.VWMA|1,VWMA|1,Rec.HullMA9|1,HullMA9|1,Pivot.M.Classic.S3|1,Pivot.M.Classic.S2|1,Pivot.M.Classic.S1|1,Pivot.M.Classic.Middle|1,Pivot.M.Classic.R1|1,Pivot.M.Classic.R2|1,Pivot.M.Classic.R3|1,Pivot.M.Fibonacci.S3|1,Pivot.M.Fibonacci.S2|1,Pivot.M.Fibonacci.S1|1,Pivot.M.Fibonacci.Middle|1,Pivot.M.Fibonacci.R1|1,Pivot.M.Fibonacci.R2|1,Pivot.M.Fibonacci.R3|1,Pivot.M.Camarilla.S3|1,Pivot.M.Camarilla.S2|1,Pivot.M.Camarilla.S1|1,Pivot.M.Camarilla.Middle|1,Pivot.M.Camarilla.R1|1,Pivot.M.Camarilla.R2|1,Pivot.M.Camarilla.R3|1,Pivot.M.Woodie.S3|1,Pivot.M.Woodie.S2|1,Pivot.M.Woodie.S1|1,Pivot.M.Woodie.Middle|1,Pivot.M.Woodie.R1|1,Pivot.M.Woodie.R2|1,Pivot.M.Woodie.R3|1,Pivot.M.Demark.S1|1,Pivot.M.Demark.Middle|1,Pivot.M.Demark.R1|1";
//        String field1D="Recommend.Other,Recommend.All,Recommend.MA,RSI,RSI[1],Stoch.K,Stoch.D,Stoch.K[1],Stoch.D[1],CCI20,CCI20[1],ADX,ADX+DI,ADX-DI,ADX+DI[1],ADX-DI[1],AO,AO[1],AO[2],Mom,Mom[1],MACD.macd,MACD.signal,Rec.Stoch.RSI,Stoch.RSI.K,Rec.WR,W.R,Rec.BBPower,BBPower,Rec.UO,UO,EMA10,close,SMA10,EMA20,SMA20,EMA30,SMA30,EMA50,SMA50,EMA100,SMA100,EMA200,SMA200,Rec.Ichimoku,Ichimoku.BLine,Rec.VWMA,VWMA,Rec.HullMA9,HullMA9,Pivot.M.Classic.S3,Pivot.M.Classic.S2,Pivot.M.Classic.S1,Pivot.M.Classic.Middle,Pivot.M.Classic.R1,Pivot.M.Classic.R2,Pivot.M.Classic.R3,Pivot.M.Fibonacci.S3,Pivot.M.Fibonacci.S2,Pivot.M.Fibonacci.S1,Pivot.M.Fibonacci.Middle,Pivot.M.Fibonacci.R1,Pivot.M.Fibonacci.R2,Pivot.M.Fibonacci.R3,Pivot.M.Camarilla.S3,Pivot.M.Camarilla.S2,Pivot.M.Camarilla.S1,Pivot.M.Camarilla.Middle,Pivot.M.Camarilla.R1,Pivot.M.Camarilla.R2,Pivot.M.Camarilla.R3,Pivot.M.Woodie.S3,Pivot.M.Woodie.S2,Pivot.M.Woodie.S1,Pivot.M.Woodie.Middle,Pivot.M.Woodie.R1,Pivot.M.Woodie.R2,Pivot.M.Woodie.R3,Pivot.M.Demark.S1,Pivot.M.Demark.Middle,Pivot.M.Demark.R1";
//        String field1MONTH="Recommend.Other|1M,Recommend.All|1M,Recommend.MA|1M,RSI|1M,RSI[1]|1M,Stoch.K|1M,Stoch.D|1M,Stoch.K[1]|1M,Stoch.D[1]|1M,CCI20|1M,CCI20[1]|1M,ADX|1M,ADX+DI|1M,ADX-DI|1M,ADX+DI[1]|1M,ADX-DI[1]|1M,AO|1M,AO[1]|1M,AO[2]|1M,Mom|1M,Mom[1]|1M,MACD.macd|1M,MACD.signal|1M,Rec.Stoch.RSI|1M,Stoch.RSI.K|1M,Rec.WR|1M,W.R|1M,Rec.BBPower|1M,BBPower|1M,Rec.UO|1M,UO|1M,EMA10|1M,close|1M,SMA10|1M,EMA20|1M,SMA20|1M,EMA30|1M,SMA30|1M,EMA50|1M,SMA50|1M,EMA100|1M,SMA100|1M,EMA200|1M,SMA200|1M,Rec.Ichimoku|1M,Ichimoku.BLine|1M,Rec.VWMA|1M,VWMA|1M,Rec.HullMA9|1M,HullMA9|1M,Pivot.M.Classic.S3|1M,Pivot.M.Classic.S2|1M,Pivot.M.Classic.S1|1M,Pivot.M.Classic.Middle|1M,Pivot.M.Classic.R1|1M,Pivot.M.Classic.R2|1M,Pivot.M.Classic.R3|1M,Pivot.M.Fibonacci.S3|1M,Pivot.M.Fibonacci.S2|1M,Pivot.M.Fibonacci.S1|1M,Pivot.M.Fibonacci.Middle|1M,Pivot.M.Fibonacci.R1|1M,Pivot.M.Fibonacci.R2|1M,Pivot.M.Fibonacci.R3|1M,Pivot.M.Camarilla.S3|1M,Pivot.M.Camarilla.S2|1M,Pivot.M.Camarilla.S1|1M,Pivot.M.Camarilla.Middle|1M,Pivot.M.Camarilla.R1|1M,Pivot.M.Camarilla.R2|1M,Pivot.M.Camarilla.R3|1M,Pivot.M.Woodie.S3|1M,Pivot.M.Woodie.S2|1M,Pivot.M.Woodie.S1|1M,Pivot.M.Woodie.Middle|1M,Pivot.M.Woodie.R1|1M,Pivot.M.Woodie.R2|1M,Pivot.M.Woodie.R3|1M,Pivot.M.Demark.S1|1M,Pivot.M.Demark.Middle|1M,Pivot.M.Demark.R1|1M";
//        String encodedHrFields=URLEncoder.encode(fieldsHr,StandardCharsets.UTF_8);
//        String encoded15MinFields=URLEncoder.encode(fields15Min,StandardCharsets.UTF_8);
//        String encoded5MinFields=URLEncoder.encode(fields5Min,StandardCharsets.UTF_8);
//        String encoded1MinFields=URLEncoder.encode(fields1Min,StandardCharsets.UTF_8);
//        String encoded1DFields=URLEncoder.encode(field1D,StandardCharsets.UTF_8);
//        String encoded1MONTHFields=URLEncoder.encode(field1MONTH,StandardCharsets.UTF_8);
//
//        String url1hr=baseURL+"?symbol="+encodedFINNIFTY+"&fields="+encodedHrFields+"&no_404=true";
//        String url15mn=baseURL+"?symbol="+encodedBANKNIFTY+"&fields="+encoded15MinFields+"&no_404=true";
//        String url5mn=baseURL+"?symbol="+encodedBANKNIFTY+"&fields="+encoded5MinFields+"&no_404=true";
//        String url1mn=baseURL+"?symbol="+encodedBANKNIFTY+"&fields="+encoded1MinFields+"&no_404=true";
//        String url1D=baseURL+"?symbol="+encodedBANKNIFTY+"&fields="+encoded1DFields+"&no_404=true";
//        String url1MONTH=baseURL+"?symbol="+encodedBANKNIFTY+"&fields="+encoded1MONTHFields+"&no_404=true";
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create(url15mn))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//    }

//    "NSE:NIFTY_MID_SELECT", "NSE:NIFTY", "NSE:BANKNIFTY", "NSE:CNXFINANCE"

    public static float getRSI15(String str) throws IOException, InterruptedException {
//        HashMap<String, String> map=new HashMap<>();
//        map.put("Mon", "NSE:NIFTY_MID_SELECT");
//        map.put("Tue", "NSE:CNXFINANCE");
//        map.put("Wed", "NSE:BANKNIFTY");
//        map.put("Thu", "NSE:NIFTY");
//
//        if(map.containsKey(str)){
        String baseURL="https://scanner.tradingview.com/symbol";
        // String symbol=map.get(str);
        String symbol=str;
        String encodedSymbol=URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String fields15Min="Recommend.Other|15,Recommend.All|15,Recommend.MA|15,RSI|15,RSI[1]|15,Stoch.K|15,Stoch.D|15,Stoch.K[1]|15,Stoch.D[1]|15,CCI20|15,CCI20[1]|15,ADX|15,ADX+DI|15,ADX-DI|15,ADX+DI[1]|15,ADX-DI[1]|15,AO|15,AO[1]|15,AO[2]|15,Mom|15,Mom[1]|15,MACD.macd|15,MACD.signal|15,Rec.Stoch.RSI|15,Stoch.RSI.K|15,Rec.WR|15,W.R|15,Rec.BBPower|15,BBPower|15,Rec.UO|15,UO|15,EMA10|15,close|15,SMA10|15,EMA20|15,SMA20|15,EMA30|15,SMA30|15,EMA50|15,SMA50|15,EMA100|15,SMA100|15,EMA200|15,SMA200|15,Rec.Ichimoku|15,Ichimoku.BLine|15,Rec.VWMA|15,VWMA|15,Rec.HullMA9|15,HullMA9|15,Pivot.M.Classic.S3|15,Pivot.M.Classic.S2|15,Pivot.M.Classic.S1|15,Pivot.M.Classic.Middle|15,Pivot.M.Classic.R1|15,Pivot.M.Classic.R2|15,Pivot.M.Classic.R3|15,Pivot.M.Fibonacci.S3|15,Pivot.M.Fibonacci.S2|15,Pivot.M.Fibonacci.S1|15,Pivot.M.Fibonacci.Middle|15,Pivot.M.Fibonacci.R1|15,Pivot.M.Fibonacci.R2|15,Pivot.M.Fibonacci.R3|15,Pivot.M.Camarilla.S3|15,Pivot.M.Camarilla.S2|15,Pivot.M.Camarilla.S1|15,Pivot.M.Camarilla.Middle|15,Pivot.M.Camarilla.R1|15,Pivot.M.Camarilla.R2|15,Pivot.M.Camarilla.R3|15,Pivot.M.Woodie.S3|15,Pivot.M.Woodie.S2|15,Pivot.M.Woodie.S1|15,Pivot.M.Woodie.Middle|15,Pivot.M.Woodie.R1|15,Pivot.M.Woodie.R2|15,Pivot.M.Woodie.R3|15,Pivot.M.Demark.S1|15,Pivot.M.Demark.Middle|15,Pivot.M.Demark.R1|15";
        String encoded15MinFields=URLEncoder.encode(fields15Min,StandardCharsets.UTF_8);
        String url15mn=baseURL+"?symbol="+encodedSymbol+"&fields="+encoded15MinFields+"&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url15mn))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("RSI|15");
                val = Float.parseFloat(dataNode.toString());
                success=true;
            }
            if(!success) Thread.sleep(1000);
        }
        return val;
//        }
//        else System.out.println("WEEKEND");
//        return 0.0f;
    }

    public static float getCCI15(String str) throws IOException, InterruptedException {
        String baseURL = "https://scanner.tradingview.com/symbol";
//        String BANKNIFTY="NSE:BANKNIFTY";
//        String encodedBANKNIFTY=URLEncoder.encode(BANKNIFTY,StandardCharsets.UTF_8);
        String symbol = str;
        String encodedSymbol = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
        String fields15Min = "Recommend.Other|15,Recommend.All|15,Recommend.MA|15,RSI|15,RSI[1]|15,Stoch.K|15,Stoch.D|15,Stoch.K[1]|15,Stoch.D[1]|15,CCI20|15,CCI20[1]|15,ADX|15,ADX+DI|15,ADX-DI|15,ADX+DI[1]|15,ADX-DI[1]|15,AO|15,AO[1]|15,AO[2]|15,Mom|15,Mom[1]|15,MACD.macd|15,MACD.signal|15,Rec.Stoch.RSI|15,Stoch.RSI.K|15,Rec.WR|15,W.R|15,Rec.BBPower|15,BBPower|15,Rec.UO|15,UO|15,EMA10|15,close|15,SMA10|15,EMA20|15,SMA20|15,EMA30|15,SMA30|15,EMA50|15,SMA50|15,EMA100|15,SMA100|15,EMA200|15,SMA200|15,Rec.Ichimoku|15,Ichimoku.BLine|15,Rec.VWMA|15,VWMA|15,Rec.HullMA9|15,HullMA9|15,Pivot.M.Classic.S3|15,Pivot.M.Classic.S2|15,Pivot.M.Classic.S1|15,Pivot.M.Classic.Middle|15,Pivot.M.Classic.R1|15,Pivot.M.Classic.R2|15,Pivot.M.Classic.R3|15,Pivot.M.Fibonacci.S3|15,Pivot.M.Fibonacci.S2|15,Pivot.M.Fibonacci.S1|15,Pivot.M.Fibonacci.Middle|15,Pivot.M.Fibonacci.R1|15,Pivot.M.Fibonacci.R2|15,Pivot.M.Fibonacci.R3|15,Pivot.M.Camarilla.S3|15,Pivot.M.Camarilla.S2|15,Pivot.M.Camarilla.S1|15,Pivot.M.Camarilla.Middle|15,Pivot.M.Camarilla.R1|15,Pivot.M.Camarilla.R2|15,Pivot.M.Camarilla.R3|15,Pivot.M.Woodie.S3|15,Pivot.M.Woodie.S2|15,Pivot.M.Woodie.S1|15,Pivot.M.Woodie.Middle|15,Pivot.M.Woodie.R1|15,Pivot.M.Woodie.R2|15,Pivot.M.Woodie.R3|15,Pivot.M.Demark.S1|15,Pivot.M.Demark.Middle|15,Pivot.M.Demark.R1|15";
        String encoded15MinFields = URLEncoder.encode(fields15Min, StandardCharsets.UTF_8);
        String url15mn = baseURL + "?symbol=" + encodedSymbol + "&fields=" + encoded15MinFields + "&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url15mn))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("CCI20|15");
                val = Float.parseFloat(dataNode.toString());
                success=true;
            }
            if(!success) Thread.sleep(1000);
        }
        return val;
    }

    public static float getRSI5(String str) throws IOException, InterruptedException {
        String baseURL="https://scanner.tradingview.com/symbol";
        String symbol=str;
        String encodedSymbol=URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String fields5Min="Recommend.Other|5,Recommend.All|5,Recommend.MA|5,RSI|5,RSI[1]|5,Stoch.K|5,Stoch.D|5,Stoch.K[1]|5,Stoch.D[1]|5,CCI20|5,CCI20[1]|5,ADX|5,ADX+DI|5,ADX-DI|5,ADX+DI[1]|5,ADX-DI[1]|5,AO|5,AO[1]|5,AO[2]|5,Mom|5,Mom[1]|5,MACD.macd|5,MACD.signal|5,Rec.Stoch.RSI|5,Stoch.RSI.K|5,Rec.WR|5,W.R|5,Rec.BBPower|5,BBPower|5,Rec.UO|5,UO|5,EMA10|5,close|5,SMA10|5,EMA20|5,SMA20|5,EMA30|5,SMA30|5,EMA50|5,SMA50|5,EMA100|5,SMA100|5,EMA200|5,SMA200|5,Rec.Ichimoku|5,Ichimoku.BLine|5,Rec.VWMA|5,VWMA|5,Rec.HullMA9|5,HullMA9|5,Pivot.M.Classic.S3|5,Pivot.M.Classic.S2|5,Pivot.M.Classic.S1|5,Pivot.M.Classic.Middle|5,Pivot.M.Classic.R1|5,Pivot.M.Classic.R2|5,Pivot.M.Classic.R3|5,Pivot.M.Fibonacci.S3|5,Pivot.M.Fibonacci.S2|5,Pivot.M.Fibonacci.S1|5,Pivot.M.Fibonacci.Middle|5,Pivot.M.Fibonacci.R1|5,Pivot.M.Fibonacci.R2|5,Pivot.M.Fibonacci.R3|5,Pivot.M.Camarilla.S3|5,Pivot.M.Camarilla.S2|5,Pivot.M.Camarilla.S1|5,Pivot.M.Camarilla.Middle|5,Pivot.M.Camarilla.R1|5,Pivot.M.Camarilla.R2|5,Pivot.M.Camarilla.R3|5,Pivot.M.Woodie.S3|5,Pivot.M.Woodie.S2|5,Pivot.M.Woodie.S1|5,Pivot.M.Woodie.Middle|5,Pivot.M.Woodie.R1|5,Pivot.M.Woodie.R2|5,Pivot.M.Woodie.R3|5,Pivot.M.Demark.S1|5,Pivot.M.Demark.Middle|5,Pivot.M.Demark.R1|5";
        String encoded5MinFields=URLEncoder.encode(fields5Min,StandardCharsets.UTF_8);
        String url5mn=baseURL+"?symbol="+encodedSymbol+"&fields="+encoded5MinFields+"&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url5mn))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("RSI|5");
                val = Float.parseFloat(dataNode.toString());
                success=true;
            }
            if(!success) Thread.sleep(1000);
        }
        return val;
    }

    public static float getCCI5(String str) throws IOException, InterruptedException {
        String baseURL="https://scanner.tradingview.com/symbol";
        String symbol=str;
        String encodedSymbol=URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String fields5Min="Recommend.Other|5,Recommend.All|5,Recommend.MA|5,RSI|5,RSI[1]|5,Stoch.K|5,Stoch.D|5,Stoch.K[1]|5,Stoch.D[1]|5,CCI20|5,CCI20[1]|5,ADX|5,ADX+DI|5,ADX-DI|5,ADX+DI[1]|5,ADX-DI[1]|5,AO|5,AO[1]|5,AO[2]|5,Mom|5,Mom[1]|5,MACD.macd|5,MACD.signal|5,Rec.Stoch.RSI|5,Stoch.RSI.K|5,Rec.WR|5,W.R|5,Rec.BBPower|5,BBPower|5,Rec.UO|5,UO|5,EMA10|5,close|5,SMA10|5,EMA20|5,SMA20|5,EMA30|5,SMA30|5,EMA50|5,SMA50|5,EMA100|5,SMA100|5,EMA200|5,SMA200|5,Rec.Ichimoku|5,Ichimoku.BLine|5,Rec.VWMA|5,VWMA|5,Rec.HullMA9|5,HullMA9|5,Pivot.M.Classic.S3|5,Pivot.M.Classic.S2|5,Pivot.M.Classic.S1|5,Pivot.M.Classic.Middle|5,Pivot.M.Classic.R1|5,Pivot.M.Classic.R2|5,Pivot.M.Classic.R3|5,Pivot.M.Fibonacci.S3|5,Pivot.M.Fibonacci.S2|5,Pivot.M.Fibonacci.S1|5,Pivot.M.Fibonacci.Middle|5,Pivot.M.Fibonacci.R1|5,Pivot.M.Fibonacci.R2|5,Pivot.M.Fibonacci.R3|5,Pivot.M.Camarilla.S3|5,Pivot.M.Camarilla.S2|5,Pivot.M.Camarilla.S1|5,Pivot.M.Camarilla.Middle|5,Pivot.M.Camarilla.R1|5,Pivot.M.Camarilla.R2|5,Pivot.M.Camarilla.R3|5,Pivot.M.Woodie.S3|5,Pivot.M.Woodie.S2|5,Pivot.M.Woodie.S1|5,Pivot.M.Woodie.Middle|5,Pivot.M.Woodie.R1|5,Pivot.M.Woodie.R2|5,Pivot.M.Woodie.R3|5,Pivot.M.Demark.S1|5,Pivot.M.Demark.Middle|5,Pivot.M.Demark.R1|5";
        String encoded5MinFields=URLEncoder.encode(fields5Min,StandardCharsets.UTF_8);
        String url5mn=baseURL+"?symbol="+encodedSymbol+"&fields="+encoded5MinFields+"&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url5mn))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("CCI20|5");
                val = Float.parseFloat(dataNode.toString());
                success=true;
            }
            if (!success) Thread.sleep(1000);
        }
        return val;
    }

    public static float getRSI1D(String str) throws IOException, InterruptedException {
        String baseURL="https://scanner.tradingview.com/symbol";
        String symbol=str;
        String encodedSymbol=URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String fields1D="Recommend.Other,Recommend.All,Recommend.MA,RSI,RSI[1],Stoch.K,Stoch.D,Stoch.K[1],Stoch.D[1],CCI20,CCI20[1],ADX,ADX DI,ADX-DI,ADX DI[1],ADX-DI[1],AO,AO[1],AO[2],Mom,Mom[1],MACD.macd,MACD.signal,Rec.Stoch.RSI,Stoch.RSI.K,Rec.WR,W.R,Rec.BBPower,BBPower,Rec.UO,UO,EMA10,close,SMA10,EMA20,SMA20,EMA30,SMA30,EMA50,SMA50,EMA100,SMA100,EMA200,SMA200,Rec.Ichimoku,Ichimoku.BLine,Rec.VWMA,VWMA,Rec.HullMA9,HullMA9,Pivot.M.Classic.S3,Pivot.M.Classic.S2,Pivot.M.Classic.S1,Pivot.M.Classic.Middle,Pivot.M.Classic.R1,Pivot.M.Classic.R2,Pivot.M.Classic.R3,Pivot.M.Fibonacci.S3,Pivot.M.Fibonacci.S2,Pivot.M.Fibonacci.S1,Pivot.M.Fibonacci.Middle,Pivot.M.Fibonacci.R1,Pivot.M.Fibonacci.R2,Pivot.M.Fibonacci.R3,Pivot.M.Camarilla.S3,Pivot.M.Camarilla.S2,Pivot.M.Camarilla.S1,Pivot.M.Camarilla.Middle,Pivot.M.Camarilla.R1,Pivot.M.Camarilla.R2,Pivot.M.Camarilla.R3,Pivot.M.Woodie.S3,Pivot.M.Woodie.S2,Pivot.M.Woodie.S1,Pivot.M.Woodie.Middle,Pivot.M.Woodie.R1,Pivot.M.Woodie.R2,Pivot.M.Woodie.R3,Pivot.M.Demark.S1,Pivot.M.Demark.Middle,Pivot.M.Demark.R1";
        String encoded1DFields=URLEncoder.encode(fields1D,StandardCharsets.UTF_8);
        String url1d=baseURL+"?symbol="+encodedSymbol+"&fields="+encoded1DFields+"&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url1d))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("RSI");
                val = Float.parseFloat(dataNode.toString());
                success=true;
            }
            if (!success) Thread.sleep(1000);
    }
    return val;
}

    public static float getCCI1D(String str) throws IOException, InterruptedException {
        String baseURL="https://scanner.tradingview.com/symbol";
        String symbol=str;
        String encodedSymbol=URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String fields1D="Recommend.Other,Recommend.All,Recommend.MA,RSI,RSI[1],Stoch.K,Stoch.D,Stoch.K[1],Stoch.D[1],CCI20,CCI20[1],ADX,ADX DI,ADX-DI,ADX DI[1],ADX-DI[1],AO,AO[1],AO[2],Mom,Mom[1],MACD.macd,MACD.signal,Rec.Stoch.RSI,Stoch.RSI.K,Rec.WR,W.R,Rec.BBPower,BBPower,Rec.UO,UO,EMA10,close,SMA10,EMA20,SMA20,EMA30,SMA30,EMA50,SMA50,EMA100,SMA100,EMA200,SMA200,Rec.Ichimoku,Ichimoku.BLine,Rec.VWMA,VWMA,Rec.HullMA9,HullMA9,Pivot.M.Classic.S3,Pivot.M.Classic.S2,Pivot.M.Classic.S1,Pivot.M.Classic.Middle,Pivot.M.Classic.R1,Pivot.M.Classic.R2,Pivot.M.Classic.R3,Pivot.M.Fibonacci.S3,Pivot.M.Fibonacci.S2,Pivot.M.Fibonacci.S1,Pivot.M.Fibonacci.Middle,Pivot.M.Fibonacci.R1,Pivot.M.Fibonacci.R2,Pivot.M.Fibonacci.R3,Pivot.M.Camarilla.S3,Pivot.M.Camarilla.S2,Pivot.M.Camarilla.S1,Pivot.M.Camarilla.Middle,Pivot.M.Camarilla.R1,Pivot.M.Camarilla.R2,Pivot.M.Camarilla.R3,Pivot.M.Woodie.S3,Pivot.M.Woodie.S2,Pivot.M.Woodie.S1,Pivot.M.Woodie.Middle,Pivot.M.Woodie.R1,Pivot.M.Woodie.R2,Pivot.M.Woodie.R3,Pivot.M.Demark.S1,Pivot.M.Demark.Middle,Pivot.M.Demark.R1";
        String encoded1DFields=URLEncoder.encode(fields1D,StandardCharsets.UTF_8);
        String url1d=baseURL+"?symbol="+encodedSymbol+"&fields="+encoded1DFields+"&no_404=true";

        float val=0;
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url1d))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode jsonNode=objectMapper.readTree(response.body());
            if(jsonNode.size()!=0){
                JsonNode dataNode = jsonNode.get("CCI20");
                val= Float.parseFloat(dataNode.toString());
                success=true;
            }
            if (!success) Thread.sleep(1000);
        }
        return val;
    }

    public static String getOptionContract(String symbol) throws IOException, InterruptedException {
        String encodedString= URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        String val="";
        boolean success=false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://groww.in/v1/api/option_chain_service/v1/option_chain/derivatives/"+encodedString))
                    .build();
            HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
            if(jsonNode.size()!=0){
                String data=null;
                if(jsonNode.get("optionChain").get("optionChains").get(0).get("callOption")==null) data= jsonNode.get("optionChain").get("optionChains").get(0).get("putOption").get("growwContractId").toString();
                else data= jsonNode.get("optionChain").get("optionChains").get(0).get("callOption").get("growwContractId").toString();
                int strikeLen= jsonNode.get("optionChain").get("optionChains").get(0).get("strikePrice").toString().length();
                int lastIndex=data.length()-1;
                val= data.substring(1,lastIndex-strikeLen);
                // System.out.println(val);
                success=true;
            }
            if (!success) Thread.sleep(1000);
        }
        return val;
    }

//     zer-tkn
//     public static String getSymbolToken(String symbol) throws IOException, InterruptedException {
//         String encodedString= URLEncoder.encode(symbol,StandardCharsets.UTF_8);
//         HttpRequest request = HttpRequest.newBuilder()
//                 .GET()
//                 .header("Authorization", zerodha_enctoken)
//                 .uri(URI.create(ZERODHA_API_URL + "/quote?i="+encodedString))
//                 .build();
//         HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
// //        System.out.println(jsonNode);
// //        JsonNode dataNode = jsonNode.get("data");
//         JsonNode dataNode = jsonNode.get("data").get(symbol).get("instrument_token");
// //        System.out.println(dataNode);
//         return dataNode.toString();
//     }

    public static boolean checkOptionExpiry(String symbol) throws IOException, InterruptedException, NullPointerException {
        String encodedString= URLEncoder.encode(symbol,StandardCharsets.UTF_8);
        boolean res= false, success= false;
        while (!success) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://groww.in/v1/api/option_chain_service/v1/option_chain/derivatives/"+encodedString))
                    .build();
            HttpResponse<String> jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse.body());
            if(jsonNode.size()!=0){
                Date date=new Date();
                String[] dateArray=date.toString().split(" ");
                String dateString=dateArray[2]+" "+dateArray[1];
                System.out.println();
                System.out.print("TODAY: "+dateString+", ");
                String[] expiry=null;
                if(jsonNode.get("optionChain").get("optionChains").get(0).get("callOption")==null) expiry = jsonNode.get("optionChain").get("optionChains").get(0).get("putOption").get("longDisplayName").toString().split(" ");
                else expiry= jsonNode.get("optionChain").get("optionChains").get(0).get("callOption").get("longDisplayName").toString().split(" ");
                String contractExpiry=expiry[1]+" "+expiry[2];
                System.out.print(expiry[0].substring(1)+" EXPIRY: "+contractExpiry+", ");
                if (dateString.equals(contractExpiry)){
                    System.out.println("EXPIRY DAY : ✅");
                    res = true;
                }else {
                    System.out.println("NO EXPIRY : ❌");
                    res = false;
                }
                success=true;
            }
            if(!success) Thread.sleep(1000);
        }
        return res;
    }
}