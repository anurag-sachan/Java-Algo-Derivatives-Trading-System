package org.jas.backtesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jas.Filehandler;

import java.util.HashMap;

public class backtest {

    private static final String BASE_PATH = "/data_options/option data/";
    private static final String CCI_PATH = "/data_options/cci/";
    static String startTime;
    static Double totalProfitorLoss=0.0;

    public static void main(String[] args) throws IOException {
        String[] indexes = {"INDEX", "DOGE"};
        
        for (String index : indexes) {
                
            List<String> expiryDates = getExpiryDates(index);

            for (String date : expiryDates) {
                startTime="09:15:00";

                if(startTime.equals("09:15:00")){
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    
                    System.out.printf("15M Trend for %s on %s: ",index,date);
                    String trend15= br.readLine();
                    Filehandler.writeToFile("trend15", trend15);
                    
                    System.out.printf("5M Trend for %s on %s: ",index,date);
                    String trend5= br.readLine();
                    Filehandler.writeToFile("trend5", trend5);
                }

                int count=0;
                while (startTime != "15:30:00") {
                    if(startTime.equals("12:15:00") || count==1){
                        System.out.println("start time: "+startTime);
                        count=1;
                    }
                    Map<String, List<CCIEntry>> cciData5m = getCCIData(index, date, startTime, "5M");
                    Map<String, List<CCIEntry>> cciData15m = getCCIData(index, date, startTime, "15M");
                    
                    TradeCondition tradeCondition = checkTradingCondition(index, date, cciData5m, cciData15m);
                    
                    if (tradeCondition != null) {
                        List<Contract> contracts = getContracts(index, date, tradeCondition.strike, tradeCondition.time, startTime, tradeCondition.trend);
                        // contracts.stream().forEach(a -> System.out.println(a.time + ", " + a.price));
                        System.out.println(contracts.get(0).time);
                        double profitOrLoss = executeTradingStrategy(contracts);
                        System.out.println("Date: " + date + ", Index: " + index + ", Profit/Loss: " + profitOrLoss);
                    }
                    else{
                        String hour=startTime.split(":")[0];
                        String min=String.valueOf(Integer.parseInt(startTime.split(":")[1])+1).length()<2?"0".concat(String.valueOf(Integer.parseInt(startTime.split(":")[1])+1)):String.valueOf(Integer.parseInt(startTime.split(":")[1])+1);
                        String sec=startTime.split(":")[2];
                        startTime=hour+":"+min+":"+sec;
                    }
                }
                System.out.println("TODAY PROFIT: "+totalProfitorLoss+" on "+date);
            }
        }
    }
        
    private static List<String> getExpiryDates(String index) {
        List<String> dates = new ArrayList<>();
        File file = new File(BASE_PATH + index + "/" + index.toLowerCase() + "expiry copy.txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitDates = line.split(" ");
                for (String date : splitDates) {
                    dates.add(date);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dates;
    }

    private static Map<String, List<CCIEntry>> getCCIData(String index, String date, String startTime, String interval) {
        Map<String, List<CCIEntry>> cciData = new HashMap<>();
        File file = new File(CCI_PATH + index + " CCI " + interval + ".csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int counter=0;
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] splitData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                String dateTime = splitData[0].replace("\"", "");
                String formatDate= dateTime.split(",")[0].split(" ")[2].concat(dateTime.split(" ")[1]).concat(dateTime.split(" ")[3].substring(2)).toUpperCase();
                String formatTime= dateTime.split(",")[0].split(" ")[4];
                
                double closePrice = Double.parseDouble(dateTime.split(",")[4]);
                double cciValue = Double.parseDouble(dateTime.split(",")[5]);
                
                int t=15;
                if(interval.equals("5M")) t=5;
                int n=Integer.parseInt(startTime.split(":")[1])/t;
                n=t*n;
                
                if (formatDate.equals(date) && ((formatTime.split(":")[0].equals(startTime.split(":")[0]) && (Integer.parseInt(formatTime.split(":")[1])>=n)) || counter==1)) {
                // if ((formatDate.equals(date) || isRelevantTime(dateTime, date)) && ((formatTime.split(":")[0].equals(startTime.split(":")[0]) && (Integer.parseInt(formatTime.split(":")[1])>=Integer.parseInt(startTime.split(":")[1]))) || counter==1)) {
                    counter=1;
                    cciData.computeIfAbsent(formatDate, k -> new ArrayList<>()).add(new CCIEntry(dateTime, closePrice, cciValue));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cciData;
    }

    private static List<Contract> getContracts(String index, String date, Double strike, String time, String startTime, String trend) {
        List<Contract> contracts = new ArrayList<>();
        String contractType = trend.equals("up") ? "C" : "P";
        File file = new File(BASE_PATH + index + "/" + date + "/contracts/" + strike.intValue() + contractType + ".txt");
        
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean cond2nd=false;
                while ((line = br.readLine()) != null) {
                    String[] splitData = line.split(",");
                    String dateNum=line.substring(0, 2);
                    String dateTime=line.split(" ")[1].split(",")[0];
                    
                    String passedTimedate=time.split(" ")[2];
                    String passedTimetime=startTime.split(":")[0].concat(":").concat(startTime.split(":")[1]).concat(":59");
                    // String passedTimetime=time.split(" ")[4].split(":")[0].concat(":").concat(time.split(" ")[4].split(":")[1]).concat(":59");

                    if (dateNum.equals(passedTimedate) && (dateTime.equals(passedTimetime) || cond2nd)) {
                        cond2nd=true;
                        String contractTime = splitData[0];
                        double contractPrice = Double.parseDouble(splitData[4]);
                        contracts.add(new Contract(contractTime, contractPrice));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contracts;
    }

    private static TradeCondition checkTradingCondition(String index, String date, Map<String, List<CCIEntry>> cciData5m, Map<String, List<CCIEntry>> cciData15m) throws IOException {

        List<CCIEntry> cci5mValues = cciData5m.get(date);
        List<CCIEntry> cci15mValues = cciData15m.get(date);

        if (cci5mValues == null || cci15mValues == null) {
        // if (cci5mValues == null || cci15mValues == null || cci5mValues.size() != 3*cci15mValues.size()) {
            System.out.printf("INDEX: %s, DATE: %s (IMPROPER DATA)\n", index, date);
            return null;
        }

        for (int i = 0; i < cci15mValues.size(); i++) {
            double cci15m = cci15mValues.get(i).cciValue;
            
            if (cci15m > 100 || cci15m < -100) {
                if (!Filehandler.readFromFile("trend15").split("")[2].equals("0")) {
                    ;
                } else if (cci15m < -100) {
                    if(cci15m < Double.parseDouble(Filehandler.readFromFile("t-cci15"))) Filehandler.writeToFile("t-cci15", String.valueOf(cci15m));
                    if (!Filehandler.readFromFile("trend15").split("")[2].equals("1")) Filehandler.writeToFile("trend15", Filehandler.readFromFile("trend15").concat("1").substring(1, 4));
                } else {
                    if (cci15m > Double.parseDouble(Filehandler.readFromFile("t-cci15"))) Filehandler.writeToFile("t-cci15", String.valueOf(cci15m));
                    if (!Filehandler.readFromFile("trend15").split("")[2].equals("2")) Filehandler.writeToFile("trend15", Filehandler.readFromFile("trend15").concat("2").substring(1, 4));
                }
            }
            
            if (cci15m <= 100 && cci15m >= -100) {
                Filehandler.writeToFile("t-cci15", String.valueOf(0.0));
                if (Filehandler.readFromFile("trend15").split("")[2].equals("0")) {
                    ;
                } else
                Filehandler.writeToFile("trend15", Filehandler.readFromFile("trend15").concat("0").substring(1, 4));
            }
            // int n=3;
            // int diff=Integer.parseInt(cci15mValues.get(0).dateTime.split(" ")[4].split(":")[1])-Integer.parseInt(cci5mValues.get(0).dateTime.split(" ")[4].split(":")[1]);
            // if(diff==10) n=2;
            // if(diff==5) n=1;
            // for (int j = 0; j < 3; j++) {
            for (int j = 0; j < 3; j++) {
                int cci5mIndex = i * 3 + j;
                if (cci5mIndex <= cci5mValues.size()) {
                    double cci5m = cci5mValues.get(cci5mIndex).cciValue;
                    // Filehandler.writeToFile("t-cci5", String.valueOf(cci5m));
                    
                    if (cci5m > 110 || cci5m < -110) {
                        if (!Filehandler.readFromFile("trend5").split("")[2].equals("0")) {
                            ;
                        } else if (cci5m < -100) {
                            // if(cci5m < Double.parseDouble(Filehandler.readFromFile("t-cci5"))) Filehandler.writeToFile("t-cci5", String.valueOf(cci5m));
                            if (!Filehandler.readFromFile("trend5").split("")[2].equals("1")) Filehandler.writeToFile("trend5", Filehandler.readFromFile("trend5").concat("1").substring(1, 4));
                        } else {
                            // if (cci5m > Double.parseDouble(Filehandler.readFromFile("t-cci5"))) Filehandler.writeToFile("t-cci5", String.valueOf(cci5m));
                            if (!Filehandler.readFromFile("trend5").split("")[2].equals("2")) Filehandler.writeToFile("trend5", Filehandler.readFromFile("trend5").concat("2").substring(1, 4));
                        }
                    }
                    
                    if (cci5m <= 110 && cci5m >= -110) {
                        if (Filehandler.readFromFile("trend5").split("")[2].equals("0")) {
                            ;
                        } else
                        Filehandler.writeToFile("trend5", Filehandler.readFromFile("trend5").concat("0").substring(1, 4));
                    }
                }
                
                boolean upTrend15M = (Filehandler.readFromFile("trend15").equals("202") || Filehandler.readFromFile("trend15").equals("102") || Filehandler.readFromFile("trend15").equals("010")) && ((cci15m > 110 || cci15m < -110)? cci15m >= Double.parseDouble(Filehandler.readFromFile("t-cci15"))-((Double.parseDouble(Filehandler.readFromFile("t-cci15"))-100)/5) : true);
                boolean downTrend15M = (Filehandler.readFromFile("trend15").equals("020") || Filehandler.readFromFile("trend15").equals("201") || Filehandler.readFromFile("trend15").equals("101")) && ((cci15m > 110 || cci15m < -110)? cci15m <= Double.parseDouble(Filehandler.readFromFile("t-cci15"))-((Double.parseDouble(Filehandler.readFromFile("t-cci15"))-100)/5) : true);
                boolean upTrend5M = Filehandler.readFromFile("trend5").equals("202") || Filehandler.readFromFile("trend5").equals("102");
                boolean downTrend5M = Filehandler.readFromFile("trend5").equals("020") || Filehandler.readFromFile("trend5").equals("201");
                
                // if ((downTrend15M && downTrend5M) || (upTrend15M && upTrend5M)) {
                if (cci15m>90 && cci15m<110) {
                    String trend = (upTrend15M && upTrend5M) ? "up" : "down";
                    System.out.println("Closing price: " + cci15mValues.get(i).closePrice);
                    int closePrice = (int) cci15mValues.get(i).closePrice;
                    double strike = (upTrend15M && upTrend5M)? closePrice / 100 * 100 + 100 : closePrice / 100 * 100;
                    String time = cci5mValues.get(cci5mIndex).dateTime;
                    // System.out.println("TIME of condn met: "+time);
                    return new TradeCondition(strike, time, trend);
                }
            }
        }
        return null;
    }

    private static double executeTradingStrategy(List<Contract> contracts) {
        // System.out.println(contracts.get(0).price);
        double buyPrice = 0.0;
        double maxPrice = 0.0;
        double profitOrLoss = 0.0;
        boolean bought = false;

        for (Contract contract : contracts) {
            double currentPrice = contract.price;
            String minVal=Integer.parseInt(contract.time.split(" ")[1].split(":")[1])==59?"00":String.valueOf(Integer.parseInt(contract.time.split(" ")[1].split(":")[1])+1);
            String hourVal=Integer.parseInt(contract.time.split(" ")[1].split(":")[1])==59?String.valueOf(Integer.parseInt(contract.time.split(" ")[1].split(":")[0])+1):contract.time.split(" ")[1].split(":")[0];
            
            if(minVal.length()<2) minVal="0".concat(minVal);
            // String currentTime = hourVal.concat(":").concat(minVal).concat(":").concat("00");

            if (!bought) {
                buyPrice = currentPrice;
                maxPrice = currentPrice;
                bought = true;
            } else {
                if (currentPrice > maxPrice) {
                    maxPrice = currentPrice;
                }

                if (currentPrice <= maxPrice * 0.90 || currentPrice <= buyPrice * 0.90) {
                    profitOrLoss = currentPrice - buyPrice;
                    totalProfitorLoss+=profitOrLoss;
                    startTime= hourVal.concat(":").concat(minVal).concat(":").concat("00");
                    break;
                }
            }
        }

        return profitOrLoss;
    }

    static class Contract {
        String time;
        double price;

        public Contract(String time, double price) {
            this.time = time;
            this.price = price;
        }
    }

    static class CCIEntry {
        String dateTime;
        double closePrice;
        double cciValue;

        public CCIEntry(String dateTime, double closePrice, double cciValue) {
            this.dateTime = dateTime;
            this.closePrice = closePrice;
            this.cciValue = cciValue;
        }
    }

    static class TradeCondition {
        double strike;
        String time;
        String trend;

        public TradeCondition(double strike, String time, String trend) {
            this.strike = strike;
            this.time = time;
            this.trend = trend;
        }
    }
}
