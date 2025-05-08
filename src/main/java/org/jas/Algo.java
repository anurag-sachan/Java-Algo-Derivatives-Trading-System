package org.jas;

import org.json.JSONException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Algo {
//    public static String zerodha_enctoken="enctoken cookies>enctoken=";
// zer-tkn
// public static String zerodha_enctoken;

    // static {
    //     try {
    //         zerodha_enctoken = Filehandler.readFromFile("enctoken");
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        // zer-tkn
        // Kite.enctoken();
//        Neo.checkConn();

        while (true){
            try{
                strategy();
                break;
            }catch (IOException e){
                System.err.println("Operation timed out. Retrying..");
                Thread.sleep(2000);
            }
        }

//        Neo.funds();
//        strategy();
//        double valPerTrade=Double.parseDouble(Neo.funds())/10;
//        userMOP();
//        indexData();
//        getPCR();
//        strategy();
//        optionContract();
        //buy sell


        // check all index for trade signals
//        Date date = new Date();
//        String day=date.toString().split(" ")[0];
////        System.out.println(day);
//        System.out.println(Kite.getRSI15(day));
//        CCI15

        // String val= String.valueOf(Kite.getSymbolData("NSE:NIFTY BANK"));
        // System.out.println(val);
        // System.out.println(val.charAt(val.length()-2));
        // System.out.println(Integer.parseInt(val)/100);

        //check expiry & generate contractSymbol
//        optionContract();

        //check 5min & contractSymbol OHLC & Low Arrays, historical candles, arrays

        //buy

        //cci rsi 5 15 sell or top-% / zero-loss

//        getStrikePrice();
//        optionContract();
//        Kite.userDetails();

//        indexData();

//        Kite.getCandleData();
//        Kite.placeOrder();
//        Kite.indicators();

//        //STRATEGY
//        int up=0, down=0;
//
//        if(PCR<0.8) down++;
//        if(PCR>1.2) up++;
//
//        if(100ema>20ema) down++;
//        else up++;
//
//        //1 2 3 4 5 6 7 8 9 //5 curr, 4 rev 6 7 8 9 //6 rev 4 3 2 1
//        int[] upTargets=new int[5];
//        int[] downTargets=new int[5];
//
//        if(Math.abs(up-down)>3){
//            buyCall;
//        }else buyPut;
//        int POC=44580;
//        revVolUp, rev-1.6; targetVolDown, targetPrevLow, target2, target2.6;
//        revVolDown, rev-1.6; targetVolUp, targetPrevHigh, target2, target2.6;

//        strategy();

//        indexData();
//        checkTradeableIndex();

//        connectNeo();
//        Neo.margin();
//        Neo.placeOrder();

//        try {
//            Neo.margin();
//        }catch (Exception e){
//            System.out.println(e);
//        }

        // System.out.println(zerodha_enctoken);
        // Kite.userDetails();
        // System.out.println(zerodha_enctoken);

//        Filehandler.readFile();
//        Filehandler.modifyFileContents();
//        Filehandler.readFile();

//        Filehandler.readFromFile();
    }
    static void connectNeo() throws IOException, InterruptedException, JSONException {
        Neo.accessToken();
        Neo.viewToken();
        Neo.OTP();
        Neo.session();
        System.out.println("Neo Live ✅");
    }
//    public static void enctoken(){
//        Scanner sc= new Scanner(System.in);
//        System.out.print("ENTER ENCTOKEN: ");
//        String enctoken= sc.nextLine();
//    }

    private static int upBN=0, downBN=0, upN=0, downN=0, upFN=0, downFN=0;
    static boolean farNless=false;
    static void strategy() throws IOException, InterruptedException {

        int currentHour=0, currentMin=0;
        int tradeStatus; //tradeStatus=0 incomplete
        String contract="", buyTime="", targetIndex="";
        double buyPrice=0, profit=0, maxProfit=0, sl=0, maxPrice=0, minPrice=0, target=0,
        marginUsed=0;
        int lots=0, trigger=0, fixLot=0;

        Scanner sc=new Scanner(System.in);

    //   int i=0; //test

        while (true) {
            Date refdate = new Date();
            String dayDate=refdate.toString().split(":")[0].substring(0,10);
            int refhour = Integer.parseInt(refdate.toString().split(" ")[3].split(":")[0]);
            int refmin = Integer.parseInt(refdate.toString().split(" ")[3].split(":")[1]);

            String refTime = refhour+":"+refmin;

            // if (!(refhour == 8 && refmin >= 30) && !(refhour == 16 && refmin <= 0) && !(refhour > 8 && refhour < 16)) {
            if (true){ //test
                System.out.printf("\n------------------------------------------------------------\n");
                System.out.println("Trading hours ended. Exiting loop.");
                Filehandler.readCSV();
                return;
            }

            tradeStatus=Integer.parseInt(Filehandler.readFromFile("tradeStatus"));
            if (tradeStatus==0) {
            contract = Filehandler.readFromFile("contract");
            buyTime = Filehandler.readFromFile("buyTime");
            buyPrice = Double.parseDouble(Filehandler.readFromFile("buyPrice"));
            profit = 0;
            maxProfit = Double.parseDouble(Filehandler.readFromFile("maxProfit"));
            sl = 0;
            maxPrice = Double.parseDouble(Filehandler.readFromFile("maxPrice"));
            minPrice = Double.parseDouble(Filehandler.readFromFile("minPrice"));
            lots = Integer.parseInt(Filehandler.readFromFile("lots"));
            trigger = 0;
            fixLot = Integer.parseInt(Filehandler.readFromFile("fixLot"));
            target= Double.parseDouble(Filehandler.readFromFile("target"));
            targetIndex= Filehandler.readFromFile("targetIndex");
            float CCI15= APIs.getCCI15(targetIndex);

            double currPrice=APIs.LTP(contract);
        //    double[] arr={0.50, 1.09, 1.23, 1.79, 0.81, 3.66, 1.66, 4.22, 1.9, 55, 1.90, 1.25, 1.79, 0.81, 3.66, 1.66, 4.22}; //test
        //    double currPrice=arr[i++]; //test
                if(currPrice>maxPrice) {
                    maxPrice = currPrice;
                    Filehandler.writeToFile("maxPrice", String.valueOf(maxPrice));
                }
                if(currPrice<minPrice) {
                    minPrice = currPrice;
                    Filehandler.writeToFile("minPrice", String.valueOf(minPrice));
                }

                profit=currPrice-buyPrice;

                if(profit>=maxProfit) {
                    maxProfit = profit;
                    Filehandler.writeToFile("maxProfit", String.valueOf(maxProfit));
                }

                sl=0.90*maxPrice;
                // if(maxPrice*0.60>Double.parseDouble(Filehandler.readFromFile("buyPrice"))) sl=maxPrice*0.60;
                // else sl=Double.parseDouble(Filehandler.readFromFile("buyPrice"))*1.10;
                if ((target>0 && CCI15>=target) || (target<0 && CCI15<=target)){
                    sl=maxPrice*0.60;
                    if(currPrice<=maxPrice*0.75) trigger=1;
                    if(trigger==1){
                        if(currPrice<maxPrice) sl=currPrice*0.95;
                        else sl=maxPrice*0.95;
                    }
                }

                if(currPrice<sl) trigger=2;

                // if(maxProfit<2*buyPrice && currPrice<=maxPrice*sl) trigger= 1;
                // if(maxProfit>=2*buyPrice && currPrice<=maxPrice*sl) trigger= 2;

                System.out.printf("%s LTP: %s, BUY @ %.2f - %d lots. Current Profit: %.2f, MAX PROFIT: %.2f, STOPLOSS: %.2f, TRIGGER: %d, BUYTIME: %s, TIME: %s\n", contract.split(":")[1], currPrice, buyPrice, lots, profit, maxProfit, sl, trigger, buyTime, refTime);

                List<Trade> trades = new ArrayList<>();
                if(trigger==2 || (refhour == 15 && refmin >= 30) || refhour>=16) {
                    double profitPercentage = (currPrice - buyPrice) / buyPrice * 100.0;
                    double totalProfit = profit * lots * fixLot;
                    trades.add(new Trade(contract, lots, fixLot, buyPrice, currPrice, profit, profitPercentage, totalProfit, dayDate, buyTime, refTime, maxPrice, minPrice));
                    Filehandler.writeToCSV(trades);
                    String tradeRes= totalProfit>=0? "PROFIT":"LOSS";
                    System.out.printf("🟢 %s SOLD @ %s: %.2f (SELL ₹: %.2f - BUY ₹: %.2f), TIME: %s\n", contract.split(":")[1], tradeRes, profit, currPrice, buyPrice, refTime);
                    Filehandler.writeToFile("temp15MUP", "false");
                    Filehandler.writeToFile("temp15DOWN", "false");
                    Filehandler.writeToFile("temp5MDOWN", "false");
                    String updatedMargin =String.valueOf(Double.parseDouble(Filehandler.readFromFile("margin"))+marginUsed+totalProfit);
                    Filehandler.writeToFile("margin", updatedMargin);
                    Filehandler.writeToFile("tradeStatus","1");
                }
                else {
                    System.out.println("🔴 TRADE STATUS : PENDING ");
                }
                System.out.println();
                Thread.sleep(2000);

            } else {
                String rmin= String.valueOf(refmin).length()>1? String.valueOf(refmin): "0"+String.valueOf(refmin);
                System.out.printf(" ------------- RUNNING @ %s:%s -------------- \n", refhour, rmin);
                Filehandler.writeToFile("maxProfit","0");
                Filehandler.writeToFile("maxPrice","0");
                Filehandler.writeToFile("minPrice","100000");
                marginUsed=0;

                HashSet<String> set = new HashSet<>();
                String[] index = {"NSE:BANKNIFTY", "NSE:NIFTY", "NSE:CNXFINANCE"}; //tV-index, "BSE:SENSEX"
                for (String str : index) {

                // if(str.startsWith("NSE:B")) { //test
                //     set.add(str); //test
                //     target=100; //test
                //         Filehandler.writeToFile("target", String.valueOf(target)); //test
                //         targetIndex=str; //test
                //         Filehandler.writeToFile("targetIndex", str); //test
                // } //test
                // else continue; //test

                    // float RSI1D = Kite.getRSI1D(str);
                    // float RSI5M = Kite.getRSI5(str);

                    float CCI1D = APIs.getCCI1D(str);

                    float CCI15M = APIs.getCCI15(str);
                    float RSI15M = APIs.getRSI15(str);

                    float CCI5M = APIs.getCCI5(str);

                    String tempIndex = str.split(":")[1];

                    if (CCI1D <= 110 && CCI1D >= -110) {
                        if (tempIndex.startsWith("B")) {
                            Filehandler.writeToFile("CCI1Dbn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("BN1D").split("")[2].equals("0")) {
                            ;
                            }
                            else
                                Filehandler.writeToFile("BN1D", Filehandler.readFromFile("BN1D").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("N")) {
                            Filehandler.writeToFile("CCI1Dn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("N1D").split("")[2].equals("0")) {
                            ;
                            } else
                                Filehandler.writeToFile("N1D", Filehandler.readFromFile("N1D").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("C")) {
                            Filehandler.writeToFile("CCI1Dfn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("FN1D").split("")[2].equals("0")) {
                            ;
                            } else
                                Filehandler.writeToFile("FN1D", Filehandler.readFromFile("FN1D").concat("0").substring(1, 4));
                        }
                    }
                    if (CCI1D > 110 || CCI1D < -110) {
                        if (tempIndex.startsWith("B")) {
                            if (!Filehandler.readFromFile("BN1D").split("")[2].equals("0")) {
                            ;
                            } else if (CCI1D < -100) {
                                if(CCI1D < Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))) Filehandler.writeToFile("CCI1Dbn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("BN1D").split("")[2].equals("1")) Filehandler.writeToFile("BN1D", Filehandler.readFromFile("BN1D").concat("1").substring(1, 4));
                            } else {
                                if (CCI1D > Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))) Filehandler.writeToFile("CCI1Dbn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("BN1D").split("")[2].equals("2")) Filehandler.writeToFile("BN1D", Filehandler.readFromFile("BN1D").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("N")) {
                            if (!Filehandler.readFromFile("N1D").split("")[2].equals("0")) {
                            ;
                            } else if (CCI1D < -100) {
                                if(CCI1D < Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))) Filehandler.writeToFile("CCI1Dn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("N1D").split("")[2].equals("1")) Filehandler.writeToFile("N1D", Filehandler.readFromFile("N1D").concat("1").substring(1, 4));
                            } else {
                                if (CCI1D > Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))) Filehandler.writeToFile("CCI1Dn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("N1D").split("")[2].equals("2")) Filehandler.writeToFile("N1D", Filehandler.readFromFile("N1D").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("C")) {
                            if (!Filehandler.readFromFile("FN1D").split("")[2].equals("0")) {
                            ;
                            } else if (CCI1D < -100) {
                                if(CCI1D < Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))) Filehandler.writeToFile("CCI1Dfn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("FN1D").split("")[2].equals("1")) Filehandler.writeToFile("FN1D", Filehandler.readFromFile("FN1D").concat("1").substring(1, 4));
                            } else {
                                if (CCI1D > Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))) Filehandler.writeToFile("CCI1Dfn", String.valueOf(CCI1D));
                                if (!Filehandler.readFromFile("FN1D").split("")[2].equals("2")) Filehandler.writeToFile("FN1D", Filehandler.readFromFile("FN1D").concat("2").substring(1, 4));
                            }
                        }
                    }
                    if (CCI15M <= 110 && CCI15M >= -110) {
                        if (tempIndex.startsWith("B")) {
                            Filehandler.writeToFile("CCI15Mbn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("BN15M").split("")[2].equals("0")) {
                                ;
                            } else
                                Filehandler.writeToFile("BN15M", Filehandler.readFromFile("BN15M").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("N")) {
                            Filehandler.writeToFile("CCI15Mn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("N15M").split("")[2].equals("0")) {
                                ;
                            } else
                                Filehandler.writeToFile("N15M", Filehandler.readFromFile("N15M").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("C")) {
                            Filehandler.writeToFile("CCI15Mfn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("FN15M").split("")[2].equals("0")) {
                                ;
                            } else
                                Filehandler.writeToFile("FN15M", Filehandler.readFromFile("FN15M").concat("0").substring(1, 4));
                        }
                    }
                    if (CCI15M > 110 || CCI15M < -110) {
                        if (tempIndex.startsWith("B")) {
                            if (!Filehandler.readFromFile("BN15M").split("")[2].equals("0")) {
                            ;
                            } else if (CCI15M < -100) {
                                if(CCI15M < Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))) Filehandler.writeToFile("CCI15Mbn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("BN15M").split("")[2].equals("1")) Filehandler.writeToFile("BN15M", Filehandler.readFromFile("BN15M").concat("1").substring(1, 4));
                            } else {
                                if (CCI15M > Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))) Filehandler.writeToFile("CCI15Mbn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("BN15M").split("")[2].equals("2")) Filehandler.writeToFile("BN15M", Filehandler.readFromFile("BN15M").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("N")) {
                            if (!Filehandler.readFromFile("N15M").split("")[2].equals("0")) {
                            ;
                            } else if (CCI15M < -100) {
                                if(CCI15M < Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))) Filehandler.writeToFile("CCI15Mn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("N15M").split("")[2].equals("1")) Filehandler.writeToFile("N15M", Filehandler.readFromFile("N15M").concat("1").substring(1, 4));
                            } else {
                                if (CCI15M > Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))) Filehandler.writeToFile("CCI15Mn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("N15M").split("")[2].equals("2")) Filehandler.writeToFile("N15M", Filehandler.readFromFile("N15M").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("C")) {
                            if (!Filehandler.readFromFile("FN15M").split("")[2].equals("0")) {
                                ;
                            } else if (CCI15M < -100) {
                                if(CCI15M < Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))) Filehandler.writeToFile("CCI15Mfn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("FN15M").split("")[2].equals("1")) Filehandler.writeToFile("FN15M", Filehandler.readFromFile("FN15M").concat("1").substring(1, 4));
                            } else {
                                if (CCI15M > Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))) Filehandler.writeToFile("CCI15Mfn", String.valueOf(CCI15M));
                                if (!Filehandler.readFromFile("FN15M").split("")[2].equals("2"))Filehandler.writeToFile("FN15M", Filehandler.readFromFile("FN15M").concat("2").substring(1, 4));
                            }
                        }
                    }
                    if (CCI5M <= 110 && CCI5M >= -110) {
                        if (tempIndex.startsWith("B")) {
                            Filehandler.writeToFile("CCI5Mbn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("BN5M").split("")[2].equals("0")) {
                            ;
                            } else
                                Filehandler.writeToFile("BN5M", Filehandler.readFromFile("BN5M").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("N")) {
                            Filehandler.writeToFile("CCI5Mn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("N5M").split("")[2].equals("0")) {
                            ;
                            } else
                                Filehandler.writeToFile("N5M", Filehandler.readFromFile("N5M").concat("0").substring(1, 4));
                        }
                        else if (tempIndex.startsWith("C")) {
                            Filehandler.writeToFile("CCI5Mfn", String.valueOf(0.0));
                            if (Filehandler.readFromFile("FN5M").split("")[2].equals("0")) {
                            ;
                            } else
                                Filehandler.writeToFile("FN5M", Filehandler.readFromFile("FN5M").concat("0").substring(1, 4));
                        }
                    }
                    if (CCI5M > 110 || CCI5M < -110) {
                        if (tempIndex.startsWith("B")) {
                            if (!Filehandler.readFromFile("BN5M").split("")[2].equals("0")) {
                                ;
                            } else if (CCI5M < -100) {
                                if(CCI5M < Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))) Filehandler.writeToFile("CCI5Mbn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("BN5M").split("")[2].equals("1")) Filehandler.writeToFile("BN5M", Filehandler.readFromFile("BN5M").concat("1").substring(1, 4));
                            } else {
                                if (CCI5M > Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))) Filehandler.writeToFile("CCI5Mbn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("BN5M").split("")[2].equals("2")) Filehandler.writeToFile("BN5M", Filehandler.readFromFile("BN5M").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("N")) {
                            if (!Filehandler.readFromFile("N5M").split("")[2].equals("0")) {
                                ;
                            } else if (CCI5M < -100) {
                                if(CCI5M < Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))) Filehandler.writeToFile("CCI5Mn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("N5M").split("")[2].equals("1")) Filehandler.writeToFile("N5M", Filehandler.readFromFile("N5M").concat("1").substring(1, 4));
                            } else {
                                if (CCI5M > Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))) Filehandler.writeToFile("CCI5Mn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("N5M").split("")[2].equals("2")) Filehandler.writeToFile("N5M", Filehandler.readFromFile("N5M").concat("2").substring(1, 4));
                            }
                        }
                        else if (tempIndex.startsWith("C")) {
                            if (!Filehandler.readFromFile("FN5M").split("")[2].equals("0")) {
                                ;
                            } else if (CCI5M < -100) {
                                if(CCI5M < Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))) Filehandler.writeToFile("CCI5Mfn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("FN5M").split("")[2].equals("1")) Filehandler.writeToFile("FN5M", Filehandler.readFromFile("FN5M").concat("1").substring(1, 4));
                            } else {
                                if (CCI5M > Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))) Filehandler.writeToFile("CCI5Mfn", String.valueOf(CCI5M));
                                if (!Filehandler.readFromFile("FN5M").split("")[2].equals("2")) Filehandler.writeToFile("FN5M", Filehandler.readFromFile("FN5M").concat("2").substring(1, 4));
                            }
                        }
                    }

                    boolean temp15MUP, temp15MDOWN, temp5MDOWN;
                    temp15MUP= CCI15M<-220;
                    temp15MDOWN= CCI15M>175 || RSI15M>70;
                    temp5MDOWN= CCI15M>200;
                    if(temp15MUP) Filehandler.writeToFile("temp15MUP", "true");
                    if(temp15MDOWN) Filehandler.writeToFile("temp15MDOWN", "true");
                    if(temp5MDOWN) Filehandler.writeToFile("temp5MDOWN", "true");

                    boolean upTrend1D=false, downTrend1D=false, upTrend15M=false, downTrend15M=false, upTrend5M=false, downTrend5M=false;

                    if(tempIndex.startsWith("B")) upTrend1D = (Filehandler.readFromFile("BN1D").equals("202") || Filehandler.readFromFile("BN1D").equals("102") || Filehandler.readFromFile("BN1D").equals("010")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D >= Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))-110)/5) : true);
                    if(tempIndex.startsWith("N")) upTrend1D = (Filehandler.readFromFile("N1D").equals("202") || Filehandler.readFromFile("N1D").equals("102") || Filehandler.readFromFile("N1D").equals("010")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D >= Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))-110)/5) : true);
                    if(tempIndex.startsWith("C")) upTrend1D = (Filehandler.readFromFile("FN1D").equals("202") || Filehandler.readFromFile("FN1D").equals("102") || Filehandler.readFromFile("FN1D").equals("010")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D >= Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))-110)/5) : true);

                    if(tempIndex.startsWith("B")) downTrend1D = (Filehandler.readFromFile("BN1D").equals("020") || Filehandler.readFromFile("BN1D").equals("201") || Filehandler.readFromFile("BN1D").equals("101")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D <= Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dbn"))+110)/5) : true);
                    if(tempIndex.startsWith("N")) downTrend1D = (Filehandler.readFromFile("N1D").equals("020") || Filehandler.readFromFile("N1D").equals("201") || Filehandler.readFromFile("N1D").equals("101")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D <= Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dn"))+110)/5) : true);
                    if(tempIndex.startsWith("C")) downTrend1D = (Filehandler.readFromFile("FN1D").equals("020") || Filehandler.readFromFile("FN1D").equals("201") || Filehandler.readFromFile("FN1D").equals("101")) && ((CCI1D > 110 || CCI1D < -110)? CCI1D <= Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI1Dfn"))+110)/5) : true);

                    if(tempIndex.startsWith("B")) upTrend15M = (Filehandler.readFromFile("BN15M").equals("202") || Filehandler.readFromFile("BN15M").equals("102") || Filehandler.readFromFile("BN15M").equals("010")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M >= Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))-110)/5) : true);
                    if(tempIndex.startsWith("N")) upTrend15M = (Filehandler.readFromFile("N15M").equals("202") || Filehandler.readFromFile("N15M").equals("102") || Filehandler.readFromFile("N15M").equals("010")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M >= Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))-110)/5) : true);
                    if(tempIndex.startsWith("C")) upTrend15M = (Filehandler.readFromFile("FN15M").equals("202") || Filehandler.readFromFile("FN15M").equals("102") || Filehandler.readFromFile("FN15M").equals("010")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M >= Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))-110)/5) : true);

                    if(tempIndex.startsWith("B")) downTrend15M = (Filehandler.readFromFile("BN15M").equals("020") || Filehandler.readFromFile("BN15M").equals("201") || Filehandler.readFromFile("BN15M").equals("101")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M <= Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"))-110)/5) : true);
                    if(tempIndex.startsWith("N")) downTrend15M = (Filehandler.readFromFile("N15M").equals("020") || Filehandler.readFromFile("N15M").equals("201") || Filehandler.readFromFile("N15M").equals("101")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M <= Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mn"))-110)/5) : true);
                    if(tempIndex.startsWith("C")) downTrend15M = (Filehandler.readFromFile("FN15M").equals("020") || Filehandler.readFromFile("FN15M").equals("201") || Filehandler.readFromFile("FN15M").equals("101")) && ((CCI15M > 110 || CCI15M < -110)? CCI15M <= Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"))-110)/5) : true);

                    if(tempIndex.startsWith("B")) upTrend5M = (Filehandler.readFromFile("BN5M").equals("202") || Filehandler.readFromFile("BN5M").equals("102") || Filehandler.readFromFile("BN5M").equals("010")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M >= Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))-110)/5) : true);
                    if(tempIndex.startsWith("N")) upTrend5M = (Filehandler.readFromFile("N5M").equals("202") || Filehandler.readFromFile("N5M").equals("102") || Filehandler.readFromFile("N5M").equals("010")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M >= Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))-110)/5) : true);
                    if(tempIndex.startsWith("C")) upTrend5M = (Filehandler.readFromFile("FN5M").equals("202") || Filehandler.readFromFile("FN5M").equals("102") || Filehandler.readFromFile("FN5M").equals("010")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M >= Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))-110)/5) : true);

                    if(tempIndex.startsWith("B")) downTrend5M = (Filehandler.readFromFile("BN5M").equals("020") || Filehandler.readFromFile("BN5M").equals("201") || Filehandler.readFromFile("BN5M").equals("101")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M <= Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mbn"))-110)/5) : true);
                    if(tempIndex.startsWith("N")) downTrend5M = (Filehandler.readFromFile("N5M").equals("020") || Filehandler.readFromFile("N5M").equals("201") || Filehandler.readFromFile("N5M").equals("101")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M <= Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mn"))-110)/5) : true);
                    if(tempIndex.startsWith("C")) downTrend5M = (Filehandler.readFromFile("FN5M").equals("020") || Filehandler.readFromFile("FN5M").equals("201") || Filehandler.readFromFile("FN5M").equals("101")) && ((CCI5M > 110 || CCI5M < -110)? CCI5M <= Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))-((Double.parseDouble(Filehandler.readFromFile("CCI5Mfn"))-110)/5) : true);

                    double trend15 = 0.0;
                    if(tempIndex.startsWith("B")) trend15 = Double.parseDouble(Filehandler.readFromFile("CCI15Mbn"));
                    if(tempIndex.startsWith("N")) trend15 = Double.parseDouble(Filehandler.readFromFile("CCI15Mn"));
                    if(tempIndex.startsWith("C")) trend15 = Double.parseDouble(Filehandler.readFromFile("CCI15Mfn"));
                    
//                      if(true){ //test
                    if((((CCI5M>-110 && CCI5M<-80) || (CCI5M>-15 && CCI5M<15)) && (trend15!=0 && CCI15M>trend15/2)) || (((CCI15M>-110 && CCI15M<-80) || (CCI15M>-15 && CCI15M<15) || (CCI15M>80 && CCI15M<110)) && upTrend5M)) {
                    // if((upTrend5M && ((CCI5M>-110 && CCI5M<-80) || (CCI5M>-15 && CCI5M<15) || (CCI5M>80 && CCI5M<110))) && ((trend15!=0 && CCI15M>trend15/2) || (upTrend15M))) {
                    // if((upTrend5M && ((CCI5M>-110 && CCI5M<-80) || (CCI5M>-15 && CCI5M<15) || (CCI5M>80 && CCI5M<110))) && ((trend15!=0 && CCI15M>trend15/2) || (upTrend15M && ((CCI15M>-110 && CCI15M<-70) || (CCI15M>-15 && CCI15M<15) || (CCI15M>80 && CCI15M<110))))) {
                    // if((upTrend1D && upTrend15M && upTrend5M) || (downTrend1D && upTrend15M && upTrend5M)) {
                        if(downTrend1D) farNless=true;
                        if(tempIndex.startsWith("B")) upBN=1;
                        if(tempIndex.startsWith("N")) upN=1;
                        if(tempIndex.startsWith("C")) upFN=1;
                        if(Filehandler.readFromFile("temp15MUP").equals("true")){
                            if(tempIndex.startsWith("B")) upBN=2;
                            if(tempIndex.startsWith("N")) upN=2;
                            if(tempIndex.startsWith("C")) upFN=2;
                        }
                        target=100;
                        Filehandler.writeToFile("target", String.valueOf(target));
                        targetIndex=str;
                        Filehandler.writeToFile("targetIndex", str);
                        set.add(str);
                    }
                    if((((CCI5M<110 && CCI5M>80) || (CCI5M>-15 && CCI5M<15)) && (trend15!=0 && CCI15M<trend15/2)) || (((CCI15M>-110 && CCI15M<-80) || (CCI15M>-15 && CCI15M<15) || (CCI15M>80 && CCI15M<110)) && downTrend5M)) {
                    // if((downTrend5M && ((CCI5M>-110 && CCI5M<-80) || (CCI5M>-15 && CCI5M<15) || (CCI5M>80 && CCI5M<110))) && ((trend15!=0 && CCI15M<trend15/2) || (downTrend15M))){
                    // if((downTrend5M && ((CCI5M>-110 && CCI5M<-80) || (CCI5M>-15 && CCI5M<15) || (CCI5M>80 && CCI5M<110))) && ((trend15!=0 && CCI15M<trend15/2) || (downTrend15M && ((CCI15M>-110 && CCI15M<-80) || (CCI15M>-15 && CCI15M<15) || (CCI15M>80 && CCI15M<110))))){
                    // if((downTrend1D && downTrend15M && downTrend5M) || (upTrend1D && downTrend15M && downTrend5M)){
                        if(upTrend1D) farNless=true;
                        if(tempIndex.startsWith("B")) downBN=1;
                        if(tempIndex.startsWith("N")) downN=1;
                        if(tempIndex.startsWith("C")) downFN=1;
                        if(Filehandler.readFromFile("temp15MDOWN").equals("true") || Filehandler.readFromFile("temp5MDOWN").equals("true")){
                            if(tempIndex.startsWith("B")) downBN=2;
                            if(tempIndex.startsWith("N")) downN=2;
                            if(tempIndex.startsWith("C")) downFN=2;
                        }
                        target=-100;
                        Filehandler.writeToFile("target", String.valueOf(target));
                        targetIndex=str;
                        Filehandler.writeToFile("targetIndex", str);
                        set.add(str);
                    }
//                     if (upTrend1D && upTrend15M && ((CCI5M >= -140 && CCI5M <= -90) || (RSI5M >= 32 && RSI5M <= 38)) || ((CCI5M >= -15 && CCI5M <= 15) && (RSI5M >= 45 && RSI5M <= 52.5)) || ((CCI5M >= 90 && CCI5M <= 130))) {
//                         set.add(str);
//                         up = 1;
//                     }

//                         if (CCI15M >= -150 && CCI15M <= -90) {
//                             set.add(str);
//                             up = 1;
//                         }
//                         if (((CCI15M >= -150 && CCI15M <= -90) && (RSI15M >= 32 && RSI15M <= 38)) || ((CCI15M >= -15 && CCI15M <= 15) && (RSI15M >= 45 && RSI15M <= 52.5))) {
//                             set.add(str);
//                             up = 2;
//                         }
//                         if (CCI15M >= 90 && CCI15M <= 130) {
//                             set.add(str);
//                             up = 3;
//                         }

//                     if (downTrend1D && downTrend15M && ((CCI5M >= 90 && CCI5M <= 140) || (RSI5M >= 70 && RSI5M <= 76)) || (CCI5M >= -140 && CCI5M <= -90)) {
//                         set.add(str);
//                         down = 1;
//                     }

//                         if ((CCI15M >= 90 && CCI15M <= 150) && (RSI15M >= 62 && RSI15M <= 72)) {
//                             set.add(str);
//                             down = 2;
//                         }
//                         if ((CCI15M >= 90 && CCI15M <= 150) || (RSI15M >= 62 && RSI15M <= 72)) {
//                             set.add(str);
//                             down = 1;
//                         }
//                         if (CCI15M <= -90 && CCI15M >= -120) {
//                             set.add(str);
//                             down = 3;
//                         }
                }

                if (!set.isEmpty()) {
                    HashMap<String, String> tvGroww = new HashMap<>();
                    for (String str : set) {
                        String key = str;
                        if (key.equals("NSE:BANKNIFTY")) tvGroww.put(key, "nifty-bank");
                        if (key.equals("NSE:NIFTY")) tvGroww.put(key, "nifty");
                        if (key.equals("NSE:CNXFINANCE")) tvGroww.put(key, "nifty-financial-services");
                    }

                    double margin = Double.parseDouble(Filehandler.readFromFile("margin"));
                    HashMap<String, Integer> lotSize = new HashMap<>();
                    lotSize.put("nifty-bank", 15);
                    lotSize.put("nifty", 50);
                    lotSize.put("nifty-financial-services", 40);
                    for (Map.Entry<String, String> temp : tvGroww.entrySet()) {
                        String growIndex = temp.getValue();
                        boolean expiry = APIs.checkOptionExpiry(growIndex);
                        // if (true){ //test
                        if (expiry){
                            contract = optionContract(growIndex);

                            double LTP = APIs.LTP(contract);
                            // double LTP = 0.55; //test

                            if(!Filehandler.readFromFile("contract").equals(contract)){
                                Filehandler.writeToFile("contract", contract);
                            }
                            fixLot = lotSize.get(growIndex);
                            if(!Filehandler.readFromFile("fixLot").equals(String.valueOf(fixLot))){
                                Filehandler.writeToFile("fixLot", String.valueOf(fixLot));
                            }
                            lots = (int) ((margin / 8) / (LTP * lotSize.get(growIndex)));
                        //  if(Filehandler.readFromFile("farNless").equals("true")) lots/=2;
                            lots=lots>0?lots:1;

                            if(!Filehandler.readFromFile("lots").equals(String.valueOf(lots))){
                                Filehandler.writeToFile("lots", String.valueOf(lots));
                            }

                            System.out.println();
                            System.out.printf("BUY " + contract + "? : 'Yes=1' OR 'No=0'\nENTER: ");
            
                            long startTime = System.currentTimeMillis();
                            long timeout = 5000;
                            int decision = Integer.parseInt(Filehandler.readFromFile("decision"));

                            try {
                                while ((System.currentTimeMillis() - startTime) < timeout && System.in.available() == 0) {
                                    Thread.sleep(1000);
                                }

                                if (System.in.available() > 0) {
                                    int newDecision = sc.nextInt();
                                    if (newDecision == 1 || newDecision == 0) {
                                        decision = newDecision;
                                        Filehandler.writeToFile("decision", String.valueOf(decision));
                                    } else {
                                        System.out.println("Invalid input. Defaulting to file decision.");
                                        decision = Integer.parseInt(Filehandler.readFromFile("decision"));
                                    }
                                } else {
                                    System.out.println("No input within 5 seconds. Defaulting to file decision.");
                                    decision = Integer.parseInt(Filehandler.readFromFile("decision"));
                                }
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }

                            //test
                            // String extremeStr= Filehandler.readFromFile("contract");
                            // boolean nearExtremes=false;
                            // if(extremeStr.startsWith("B")){
                            //     extremeStr="260105";
                            //     int[] tempExtremes=APIs.highsLows(extremeStr);
                            //     int lastTradedValue=APIs.getSymbolData("BANKNIFTY");
                            //     nearExtremes=tempExtremes[1]-lastTradedValue<450 || lastTradedValue-tempExtremes[0]<450;
                            // }
                            // else if(extremeStr.startsWith("N")){
                            //     extremeStr="256265";
                            //     int[] tempExtremes=APIs.highsLows(extremeStr);
                            //     int lastTradedValue=APIs.getSymbolData("NIFTY");
                            //     nearExtremes=tempExtremes[1]-lastTradedValue<350 || lastTradedValue-tempExtremes[0]<350;
                            // }
                            // else if(extremeStr.startsWith("F")){
                            //     extremeStr="257801";
                            //     int[] tempExtremes=APIs.highsLows(extremeStr);
                            //     int lastTradedValue=APIs.getSymbolData("FINNIFTY");
                            //     nearExtremes=tempExtremes[1]-lastTradedValue<250 || lastTradedValue-tempExtremes[0]<250;
                            // }

                            // if(decision==1){
                           if(true && !((refhour == 15 && refmin >= 30) || refhour>=16)){ //test
                        //    if(nearExtremes){ //test
                        //     if (Kite.lowPriceSIGNAL(contract)) {
                                Date currdate= new Date();
                                currentHour = Integer.parseInt(currdate.toString().split(" ")[3].split(":")[0]);
                                currentMin = Integer.parseInt(currdate.toString().split(" ")[3].split(":")[1]);
                                String currTime=currentHour + ":" + currentMin;
                                // System.out.println("⚠️ PRICE PREMIUM LOW ⚠️");

                                if (downBN>upBN || downN>upN || downFN>upFN) System.out.println("BUY " + lots + " PE LOTS - TIME: " + currTime);
                                else System.out.println("BUY " + lots + " CE LOTS - TIME: " + currTime);

                                marginUsed = lots * LTP * fixLot;
                                buyTime=currTime;
                                Filehandler.writeToFile("buyTime", buyTime);
                                margin -= marginUsed;
                                Filehandler.writeToFile("margin", String.valueOf(margin));
                                buyPrice=LTP;
                                Filehandler.writeToFile("buyPrice", String.valueOf(buyPrice));
                                Filehandler.writeToFile("tradeStatus", "0");
                            }
                        }
                    }
                    System.out.println();
                    System.out.println(" ------------- SCAN COMPLETED --------------- ");
                } else {
                    System.out.println();
                    System.out.println(" ------------- SCAN COMPLETED --------------- ");
                    Thread.sleep(180000);
                }
                if(Filehandler.readFromFile("tradeStatus").equals("0")) ;
                // else Thread.sleep(1000);
                else Thread.sleep(60000);
                System.out.println();
            }
        }
    }
//    public static void checkTradeableIndex() throws IOException, InterruptedException {
////        String[] index={"NSE:NIFTY_MID_SELECT", "NSE:NIFTY", "NSE:BANKNIFTY", "NSE:CNXFINANCE", "BSE:SENSEX"};
//        String[] index={"NSE:BANKNIFTY", "NSE:NIFTY", "NSE:CNXFINANCE", "BSE:SENSEX"};
//        for(String str: index){
//            System.out.println(Kite.getRSI15(str)+", "+Kite.getCCI15(str));
//        }
//    }
    static String optionContract(String str) throws IOException, InterruptedException {
        HashMap<String, String> map=new HashMap<>();
        if(str.equals("nifty-financial-services")) map.put("nifty-financial-services","FINNIFTY");
        if(str.equals("nifty-bank")) map.put("nifty-bank","BANKNIFTY");
        if(str.equals("nifty")) map.put("nifty","NIFTY");

        String optionContract=APIs.getOptionContract(str);
        // if(str.equals("nifty-bank")) System.out.println("UP: "+upBN+" ⬆️  DOWN: "+downBN+" ⬇️");
        // if(str.equals("nifty")) System.out.println("UP: "+upN+" ⬆️  DOWN: "+downN+" ⬇️");
        // if(str.equals("nifty-financial-services")) System.out.println("UP: "+upFN+" ⬆️  DOWN: "+downFN+" ⬇️");
        
        String suffix="PE";
        if(downBN<upBN || downN<upN || downFN<upFN) suffix="CE";
        String constructContract= "NFO:"+optionContract+ getStrikePrice(map.get(str)) +suffix;
//        System.out.println(constructContract);
//        System.out.println(Kite.lowPriceSIGNAL(constructContract));
        return constructContract;
    }

    static int getStrikePrice(String str) throws IOException, InterruptedException {
//        int val= Kite.getSymbolData("NSE:NIFTY BANK");

        String tempIndex=str.split("")[1];
        // String tempIndex=str.split(":")[1];
        HashMap<String,String> map=new HashMap<>();
        map.put("FINNIFTY", "257801");
        map.put("BANKNIFTY", "260105");
        map.put("NIFTY", "256265");

        int val= APIs.getSymbolData(str);

//        String temp=String.valueOf(val);
//        if(tempIndex.equals("NIFTY BANK")){
//            if(temp.charAt(temp.length()-2)<6) {
//                val = (val / 100) * 100 ;
//            }
//            else val=(val/100)*100+100;
//        }
//        else{
//            if(temp.charAt(temp.length()-1)<6) {
//                val = (val / 10) * 10;
//            }
//            else val=(val/10)*10+50;
//        }

    //    int gap=0;
    //    if(tempIndex.equals("NIFTY BANK")) gap=100;
    //    else gap=50;
//        if(Filehandler.readFromFile("farNless").equals("true")){
//            gap=gap*4;
//        }
//        else gap=gap*2;

//        else{
//            int[] extremes=Kite.highsLows(map.get(str));
//            int i=1;
//            if(extremes[0]-extremes[1]>550) i=0;
//            double[] levels={0.786, 1, 1.272, 1.618, 2, 2.309};
//            if(up>down){
//                while(extremes[1]+((extremes[0]-extremes[1])*levels[i])<val) i++;
//                val= (int) (((extremes[1]*levels[i])/100)*100);
//            }else{
//                while(extremes[0]-((extremes[0]-extremes[1])*levels[i])>val) i++;
//                val= (int) (((extremes[1]*levels[i])/100)*100);
//            }
//        }
//        if(up>down) return val+gap;
//        else return val-gap;

        // int gap=0;
        // if(tempIndex.equals("NIFTY BANK")) gap=100;
        // else gap=50;

        int[] extremes=APIs.highsLows(str);
        int i=0, strike=0;
        // if(extremes[0]-extremes[1]>550) ;
        double[] levels={0.786, 1, 1.272, 1.618, 2, 2.309, 2.618};
        if(downBN<upBN || downN<upN || downFN<upFN){
            while(extremes[1]+((extremes[0]-extremes[1])*levels[i])<=val) i++;
            strike= (int) ((int) extremes[1]+((extremes[0]-extremes[1])*levels[i]));
            String temp= String.valueOf(strike);
            if(tempIndex.startsWith("B")){
                if(temp.charAt(temp.length()-2)<6) strike=strike/100*100;
                else strike=(strike/100*100)+100;
            }
            else{
                if(temp.charAt(temp.length()-2)>2 && temp.charAt(temp.length()-2)<7) strike=(strike/100*100)+50;
                if(temp.charAt(temp.length()-2)>6) strike=(strike/100*100)+100;
                else strike=(strike/100*100);
            }
        }else{
            while(extremes[0]-((extremes[0]-extremes[1])*levels[i])>=val) i++;
            strike= (int) ((int) extremes[1]+((extremes[0]-extremes[1])*levels[i]));
            String temp= String.valueOf(val);
            if(tempIndex.startsWith("B")){
                if(temp.charAt(temp.length()-2)<6) strike=strike/100*100;
                else strike=(strike/100*100)-100;
            }
            else{
                if(temp.charAt(temp.length()-2)>2 && temp.charAt(temp.length()-2)<7) strike=(strike/100*100)+50;
                if(temp.charAt(temp.length()-2)>6) strike=(strike/100*100)+100;
                else strike=(strike/100*100);
            }
        }
        return strike;
    }

    static void getPCR() throws IOException, InterruptedException {
        Date date=new Date();
        HashMap<String, String> growwIndex=new HashMap<>();
        growwIndex.put("Mon","nifty-midcap-select");
        growwIndex.put("Tue","nifty-financial-services");
        growwIndex.put("Wed","nifty-bank");
        growwIndex.put("Thu","nifty");

        double PCR=APIs.getPCR(growwIndex.get(date.toString().split(" ")[0]));
    }

    static void indexData() throws IOException, InterruptedException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String day=date.toString().split(" ")[0];
        System.out.printf("DnT | %s | ",day);
        System.out.println(formatter.format(date));

        //        String symbol = "NSE:RELIANCE";

        HashMap<String,String> index=new HashMap<>();
        index.put("Mon", "NSE:NIFTY MID SELECT");
        index.put("Tue", "NSE:NIFTY FIN SERVICE");
        index.put("Wed", "NSE:NIFTY BANK");
        index.put("Thu", "NSE:NIFTY 50");


        if(!index.containsKey(day)) System.out.println("WEEKEND 🍾");
        else {
            String symbol= index.get(day);
            APIs.getSymbolData(symbol);
        }
    }
    // static void userMOP() throws IOException, InterruptedException {
    //     Kite.userDetails();
    //     Kite.getMOP();
    // }
}

class Trade {
    String contract;
    int lots;
    int fixLot;
    double buyPrice;
    double sellPrice;
    double profit;
    double profitPercentage;
    double totalProfit;
    String dayDate;
    String buyTime;
    String sellTime;
    double maxPrice;
    double minPrice;

    public Trade(String contract, int lots, int fixLot, double buyPrice, double sellPrice, double profit, double profitPercentage, double totalProfit, String dayDate, String buyTime, String sellTime, double maxPrice, double minPrice) {
        this.contract = contract;
        this.lots=lots;
        this.fixLot=fixLot;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.totalProfit = totalProfit;
        this.dayDate = dayDate;
        this.buyTime = buyTime;
        this.sellTime = sellTime;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }
}