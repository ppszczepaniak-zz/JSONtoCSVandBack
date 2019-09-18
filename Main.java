public class Main {

    public static void main(String[] args) {
        //input paths
        String csvFilePath = "textFiles\\CSV_JSON\\csv.txt";
        String jsonFilePath = "textFiles\\CSV_JSON\\json.txt";
        //output paths
        String csvGenFilePath = "textFiles\\CSV_JSON\\csvGenerated.txt";
        String jsonGenFilePath = "textFiles\\CSV_JSON\\JSONgenerated.txt";

        csvToJson.run(csvFilePath,jsonGenFilePath);
        jsonToCsv.run(jsonFilePath,csvGenFilePath);
    }
}
