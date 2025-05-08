package org.jas;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Filehandler {
    public static String readFromFile(String str) throws IOException {
        String file="/Users/anurag/Documents/Java/javaAlgoSystem/JAS/src/main/java/org/jas/tradeBasics.txt";
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line;
        while ((line=br.readLine())!=null){
            String key=line.split(":")[0];
            String val;
            if(line.split(":").length>1) {
                if (key.equals("contract") || key.equals("buyTime") || key.equals("targetIndex")) val = line.split(":")[1].concat(":").concat(line.split(":")[2]);
                else val=line.split(":")[1];
            }
            else val="0";
            if(key.equals(str)) return val;
        }
        br.close();
        return "enctoken <tradeBasics.txt> Not found ❌";
    }
    public static void writeToFile(String str, String newValue) throws IOException {
        String file = "/Users/anurag/Documents/Java/javaAlgoSystem/JAS/src/main/java/org/jas/tradeBasics.txt";
        File tempFile = new File(file + ".temp");

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String value="";
                    if(parts.length==2) value = parts[1].trim();
                    else value = parts[1].trim()+":"+parts[2].trim();

                    if (key.equals(str)) {
                        bw.write(key + ":" + newValue);
                    } else {
                        bw.write(key + ":" + value);
                    }

                    bw.newLine();
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }
        }

        if (tempFile.renameTo(new File(file))) {
            ;
//            System.out.println("UPDATED <tradeBasics.txt>");
        } else {
            System.out.println("FAILED TO UPDATE <TRADEBASICS.TXT>.");
        }
    }

    public static void writeToCSV(List<Trade> trades) throws IOException {
        String csvFile = "/Users/anurag/Documents/Java/javaAlgoSystem/JAS/src/main/java/org/jas/trades.csv";
        try (FileWriter csvWriter = new FileWriter(csvFile, true)) {
            if (csvWriter.toString().length() == 0) {
                csvWriter.append("Contract,Lots,Lot Size,Buy Price,Sell Price,Profit,Profit Percentage,Total Profit,Day Date,Buy Time,Sell Time,Max Price,Min Price\n");
            }

            for (Trade trade : trades) {
                csvWriter.append(String.format("%s,%d,%d,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%s,%s,%.2f,%.2f\n",
                        trade.contract, trade.lots, trade.fixLot, trade.buyPrice, trade.sellPrice,
                        trade.profit, trade.profitPercentage, trade.totalProfit,
                        trade.dayDate, trade.buyTime, trade.sellTime,
                        trade.maxPrice, trade.minPrice));
            }
        }
    }

    public static void readCSV() {
        String filePath = "/Users/anurag/Documents/Java/javaAlgoSystem/JAS/src/main/java/org/jas/trades.csv";
        Map<String, Double> dailyProfitMap = new HashMap<>();
        Map<String, Double> dailyCapitalMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            double totalCapital = 0.0;
            double cumulativeProfit = 0.0;

            System.out.println("xxxxxxxxxxxxxxxxxxxxx PROFIT REPORT xxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println();
            System.out.println("CONTRACT, LOTS, LOT SIZE, ₹ BUY, ₹ SELL, PROFIT/LOT, PROFIT %, ₹ PROFIT, DATE, BUY 🕣, SELL 🕣, ₹MAX, ₹MIN\n");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                double profit = Double.parseDouble(data[7]);
                double capitalForTrade = Integer.parseInt(data[1]) * Integer.parseInt(data[2]) * Double.parseDouble(data[3]);

                totalCapital += capitalForTrade;

                if (profit >= 0) System.out.println("🟢 " + line);
                else System.out.println("🔴 " + line);

                String date = data[8];
                dailyProfitMap.put(date, dailyProfitMap.getOrDefault(date, 0.0) + profit);
                dailyCapitalMap.put(date, dailyCapitalMap.getOrDefault(date, 0.0) + capitalForTrade);
                cumulativeProfit += profit;
            }

            double initialCapital = 100000;
            double finalMargin = initialCapital + cumulativeProfit;
            double netProfit = cumulativeProfit;
            double netProfitPercentage = (netProfit * 100) / initialCapital;

            System.out.println();
            // System.out.printf("TRADED AMOUNT: ₹ %.2f\n", totalCapital);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.printf("CAPITAL: ₹ %.2f\nNET PROFIT: %.2f %% (₹ %.2f)\n", finalMargin, netProfitPercentage, netProfit);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            System.out.println("\nDAY-WISE PROFITS/LOSSES wrt ₹1,00,000:\n");

            double runningCapital = initialCapital;

            Map<String, Double> sortedDailyProfitMap = new TreeMap<>(Comparator.reverseOrder());
            sortedDailyProfitMap.putAll(dailyProfitMap);

            for (String date : sortedDailyProfitMap.keySet()) {
                double dailyProfit = sortedDailyProfitMap.get(date);
                double dailyProfitPercentageInitial = (dailyProfit * 100) / initialCapital;
                double dailyCapital = dailyCapitalMap.get(date);
                double dailyProfitPercentageTraded = (dailyProfit * 100) / dailyCapital;

                double profitRelativeToPrevDay = (dailyProfit * 100) / (runningCapital);

                runningCapital += dailyProfit;

                if(dailyProfit>0) System.out.printf("%s: Profit: %.2f ( %.2f%% ) -> CAPITAL: ₹%.2f\n", date, dailyProfit, dailyProfitPercentageTraded, runningCapital);
                else System.out.printf("%s: Loss: %.2f ( %.2f%% ) -> CAPITAL: ₹%.2f \n", date, dailyProfit, dailyProfitPercentageTraded, runningCapital);
                
                // System.out.printf("%s: %.2f %% (₹ %.2f) & (Relative Profit / Loss: %.2f %% wrt RUNNING CAPITAL: ₹ %.2f)\n\n",
                //         date, dailyProfitPercentageInitial, dailyProfit, dailyProfitPercentageTraded, runningCapital);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

