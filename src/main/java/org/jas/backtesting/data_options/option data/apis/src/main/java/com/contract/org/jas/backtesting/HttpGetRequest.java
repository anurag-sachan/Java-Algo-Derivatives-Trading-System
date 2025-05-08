import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class HttpGetRequest {
    public static void main(String[] args) throws Exception {
        String parentDir = "/Users/anurag/Desktop/option data/FANTA";

        // List all directories in the parent directory except "API"
        File[] directories = new File(parentDir).listFiles(file -> file.isDirectory() && !file.getName().equalsIgnoreCase("APIS"));

        if (directories != null) {
            for (File dateDir : directories) {
                String dateDirName = dateDir.getName();
                String outputFile = parentDir + "/" + dateDirName + "/output.txt";
                String contractsDir = parentDir + "/" + dateDirName + "/contracts";

                // Read contract names from output.txt
                List<String> contractNames = readContractNames(outputFile);

                // Create contracts directory if it doesn't exist
                Path contractsPath = Paths.get(contractsDir);
                if (!Files.exists(contractsPath)) {
                    Files.createDirectories(contractsPath);
                }

                // Fetch and write data for each contract
                for (String contractName : contractNames) {
                    fetchAndWriteContractData(parentDir, dateDirName, contractName, contractsDir);
                }
            }
        }
    }

    private static List<String> readContractNames(String outputFile) throws IOException {
        List<String> contractNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    contractNames.add(line.trim());
                }
            }
        }
        return contractNames;
    }

    private static void fetchAndWriteContractData(String parentDir, String dateDir, String contractName, String contractsDir) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format("https://www.icharts.in/opt/hcharts/stx8req/php/getdataForOptions_curr_atp_tj.php?mode=INTRA&symbol=BANKNIFTY-%s-%s&timeframe=1min&u=connect2anurags@gmail.com", contractName, dateDir);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept-Encoding", "gzip")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:127.0) Gecko/20100101 Firefox/127.0")
                .header("Cookie", "PHPSESSID=cookie6t; g_state={\"i_l\":0}")
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        String encoding = response.headers().firstValue("Content-Encoding").orElse("");
        List<String> lines = new ArrayList<>();

        if ("gzip".equalsIgnoreCase(encoding)) {
            try (GZIPInputStream gis = new GZIPInputStream(new java.io.ByteArrayInputStream(response.body()));
                 BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new java.io.ByteArrayInputStream(response.body()), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        }

        List<String> extractedData = extractRelevantData(lines);

        // Write the extracted data to a file named after the contract
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(contractsDir + "/" + contractName + ".txt"))) {
            for (String line : extractedData) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Data for " + contractName + " has been written to " + contractsDir + "/" + contractName + ".txt");
    }

    private static List<String> extractRelevantData(List<String> lines) {
        List<String> extractedData = new ArrayList<>();
        String secondLastDayDate = null;
        String lastDayDate = null;

        // Find second last and last day dates
        for (int i = lines.size() - 1; i >= 0; i--) {
            String date = lines.get(i).split(",")[0].split(" ")[0];
            if (lastDayDate == null) {
                lastDayDate = date;
            } else if (!date.equals(lastDayDate)) {
                secondLastDayDate = date;
                break;
            }
        }

        // Extract data for second last day from 14:29:59 and full last day
        for (String line : lines) {
            String[] parts = line.split(",");
            String date = parts[0].split(" ")[0];
            String time = parts[0].split(" ")[1];

            if (date.equals(secondLastDayDate) && time.compareTo("14:29:59") >= 0) {
                extractedData.add(line);
            } else if (date.equals(lastDayDate)) {
                extractedData.add(line);
            }
        }

        return extractedData;
    }
}