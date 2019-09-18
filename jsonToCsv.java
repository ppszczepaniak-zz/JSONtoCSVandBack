
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jsonToCsv {

    public static void run(String jsonFilePath, String csvGenFilePath) {
        List<String> allLinesList = fileLineToListOfString(jsonFilePath); //reads all lines
        List<String> extractedMatchesList = regexMyJson(allLinesList); //extract matches
        PrintCSV(extractedMatchesList, csvGenFilePath); //prints file from matches
    }

    public static List<String> regexMyJson(List<String> allLinesList) {
        List<String> extractedMatchesList = new ArrayList<>();
        String tempString;
        for (String string : allLinesList) {
            Pattern pattern = Pattern.compile("(([a-zA-Z0-9]+\":\"?[a-zA-Z0-9]*)|(}))"); //regex zbiera: 'slowo":"slowo', 'slowo":liczba' lub '}'
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                if (!matcher.group(1).isEmpty()) {
                    tempString = matcher.group(1);  //zbieram to co wylapał regex
                    tempString = tempString.replace("\"", ""); //usuwam apostrofy
                    extractedMatchesList.add(tempString);
                }
            }
        }
        return extractedMatchesList;
    }

    public static void PrintCSV(List<String> extractedMatchesList, String csvFilePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFilePath))) {
            StringBuilder firstLine = new StringBuilder();
            StringBuilder nextLines = new StringBuilder();
            boolean didntDo = true;

            for (String string : extractedMatchesList) {
                if (!string.equals("}")) {
                    if (didntDo) {
                        firstLine.append(string.split(":")[0] + ";"); //first line only from first JSON object
                    }
                    nextLines.append(string.split(":")[1] + ";"); //next lines
                } else {
                    if (didntDo) {
                        pw.println(removeLastChar(firstLine)); //print first line only once
                        didntDo = false;
                    }
                    pw.println(removeLastChar(nextLines));
                    nextLines = new StringBuilder();
                }
            }
            System.out.println("CSV file created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder removeLastChar(StringBuilder sb) {
        return sb.replace(sb.length() - 1, sb.length(), "");
    }

    public static List<String> fileLineToListOfString(String path) {
        List<String> textData = new ArrayList<>();
        String line;
        try (BufferedReader textReader = new BufferedReader(new FileReader(path))) {
            while ((line = textReader.readLine()) != null) {
                textData.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception: " + e.getMessage());
        }
        return textData;
    }

}



