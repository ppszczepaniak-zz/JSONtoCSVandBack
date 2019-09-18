
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class csvToJson {

    public static void run(String csvFilePath, String jsonGenFilePath) {
        List<String[]> stringArrayList = makeListOfStringArrFromCSV(csvFilePath); //creates list of Strings[] (each line from csv file creates String[], split on ";"
        createJSONfile(jsonGenFilePath, stringArrayList); //creates JSON file
    }

    private static void createJSONfile(String jsonFilePath, List<String[]> stringArrayList) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(jsonFilePath))) { //try wtih resources - no need to close
            String[] firstLine = stringArrayList.get(0);//pierwsza linia to nagłówek: {"firstName", "lastName", ...}
            pw.println("[");
            pw.println("\t{");
            for (int j = 1; j < stringArrayList.size(); j++) {
                writeJsonElements(stringArrayList, firstLine, pw, j);
                writeSeparators(stringArrayList, pw, j);
            }
            System.out.println("JSON file created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSeparators(List<String[]> stringArrayList, PrintWriter pw, int j) {
        pw.print("\t}");
        if (j != stringArrayList.size() - 1) { //przecinek po kazdym, ale nie po ostatnim "}"
            pw.println(",");
            pw.println("\t{");
        } else {
            pw.println("\n]");
        }
    }

    public static void writeJsonElements(List<String[]> stringArrayList, String[] firstLine, PrintWriter pw, int j) {
        for (int i = 0; i < firstLine.length; i++) {
            String comma = ",";
            String apostrophe = "\"";
            if (i == firstLine.length - 1) { //w ostatnim wierzu "obiektu JSON" nie ma przecinka na końcu
                comma = "";
            }
            if (isInteger(stringArrayList.get(j)[i])) { //if intger -> bez cudzyslowow
                apostrophe = "";
            }
            pw.print("\t\t\"" + firstLine[i] + "\":");
            pw.println(apostrophe + stringArrayList.get(j)[i] + apostrophe + comma);
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true; // only got here if we didn't return false
    }

    public static List<String[]> makeListOfStringArrFromCSV(String path) {
        List<String[]> textData = new ArrayList<>();
        String line;

        try (BufferedReader textReader = new BufferedReader(new FileReader(path))) {
            while ((line = textReader.readLine()) != null) {
                textData.add(line.split(";")); //split dzieli CSV wg podanego separatora i wrzuca do String[]
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO exception: " + e.getMessage());
        }
        return textData;
    }

}



