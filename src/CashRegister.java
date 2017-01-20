import java.util.List;
import java.util.Map;

public class CashRegister {
    public static void main(String[] args) {

        FileHelper fileHelper = new FileHelper();
        ItemHelper itemHelper = new ItemHelper();

        Map<String, Item> itemMap = fileHelper.readPricesFile();
        Map<String, Discount> discountMap = fileHelper.readDiscountFile();
        List<String> barcodeList = fileHelper.readBarFile(args[0]);

        System.out.println(itemHelper.printReceipt(itemMap, discountMap , barcodeList));
    }
}
