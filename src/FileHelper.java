import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileHelper {
    String[] buffer;
    File file;

    //Method responsible for reading the prices file.
    //Creates a map with barcode as key, and value as the item.
    public Map<String ,Item> readPricesFile(){
        String[] buffer;
        Map<String, Item> itemMap = new HashMap<>();

        file = new File("prices.txt");

        try {
            String line;
            Scanner sc = new Scanner(file, "UTF-8");

            while (sc.hasNextLine()){
                line = sc.nextLine();
                buffer = line.split(",");
                itemMap.put(buffer[0], new Item(buffer[0],buffer[1],buffer[2],Integer.parseInt(buffer[3]),Integer.parseInt(buffer[4])));
            }
        } catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        }
        return itemMap;
    }

    //Method responsible for reading the bar files.
    //Returns a list of Strings(barcodes)
    public List<String> readBarFile(String path){
        List<String> barList = new ArrayList<>();

        file = new File(path);
        try {
            String line;
            Scanner sc = new Scanner(file, "UTF-8");

            while (sc.hasNextLine()){
                line = sc.nextLine();
                buffer = line.split(",");
                barList.add(buffer[0]);
            }
        } catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        }
        return barList;
    }

    public Map<String, Discount> readDiscountFile(){
        Map<String, Discount> discountMap = new HashMap<>();

        file = new File("discounts.txt");
        try {
            String line;
            Scanner sc = new Scanner(file, "UTF-8");

            while (sc.hasNextLine()){
                line = sc.nextLine();
                buffer = line.split(",");
                //adds a new discount for each new barcode
                discountMap.put(buffer[0], new Discount(buffer[0],Integer.parseInt(buffer[1]),Integer.parseInt(buffer[2]),Integer.parseInt(buffer[3])));
            }
        } catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        }
        return discountMap;
    }

}