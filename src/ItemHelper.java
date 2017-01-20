import java.util.*;

/**
 * This class is responsible for handling anything item related.
 */
public class ItemHelper {

    //Method responsible for making a list of items from a barcodeList. Uses the itemMap to determine items.
    public List<Item> barcodeToItem(Map<String, Item> itemMap, List<String> barcodeList){
        List<Item> items = new ArrayList<>();

        //Adds the correct item to items, determined from their barcode
        for (String s : barcodeList){
            if (itemMap.containsKey(s)){
                items.add(itemMap.get(s));
            }
        }
        return items;
    }

    private List<String> createCategoryList(List<Item> items){
        List<String> categoryList = new ArrayList<>();

        for (Item item : items){
            if(!categoryList.contains(item.getCategory())){
                categoryList.add(item.getCategory());
            }
        }

        //Sort the list alphabetically
        Collections.sort(categoryList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return categoryList;
    }

    public String printReceipt(Map<String, Item> itemMap, Map<String, Discount> discountMap, List<String> barcodeList){
        String receipt = "";
        Map<Item, Integer> itemAmounts = new HashMap<>();
        double totalPrice = 0.00;

        //Creates list of items, matching them from barcodes in itemMap
        List<Item> items = this.barcodeToItem(itemMap, barcodeList);

        //Adds items to map if they don't exits, if they do, increment map value of entry
        //Basically it counts the items
        for (Item i : items){
            if(itemAmounts.containsKey(i)){
                itemAmounts.put(i, itemAmounts.get(i) + 1);
            } else {
                itemAmounts.put(i, 1);
            }
        }

        List<String> categoryList = this.createCategoryList(items);

        //Print each category
        for (String category : categoryList){
            receipt += this.categoryString(category);

            //Go through each item in itemAmounts
            for (Item item : itemAmounts.keySet()){

                //If if the item matches the previously printed category, start adding item to receipt
                if (item.getCategory().equals(category)){
                    if (itemAmounts.get(item) == 1){
                        if (discountMap.containsKey(item.getBarcode())){
                            //Check if the amount is great enough to trigger RABAT
                            if(itemAmounts.get(item) >= discountMap.get(item.getBarcode()).getLimit()){
                                receipt += this.itemToFormattedStringRabat(item, discountMap);

                                //Add the discounted price instead of regular price
                                totalPrice += discountMap.get(item.getBarcode()).getTotalDiscount()*itemAmounts.get(item);
                            }else {
                                //If amoun isn't great enough add normal price
                                receipt += this.itemToFormattedString(item);
                                totalPrice += item.getPrice();
                            }
                            //If it doesn't trigger RABAT just carry on and add normal price;
                        } else {
                            receipt += this.itemToFormattedString(item);
                            totalPrice += item.getPrice();
                        }
                        //If there are more than 1 item, check if item is in discountMap
                    } else if (itemAmounts.get(item) > 1){
                        if (discountMap.containsKey(item.getBarcode())){

                            //Check if the amount is great enough to trigger RABAT
                            if(itemAmounts.get(item) >= discountMap.get(item.getBarcode()).getLimit()){
                                receipt += multipleItemsToFormattedStringRabat(discountMap, itemAmounts, item);

                                //Add the discounted price instead of regular price
                                totalPrice += discountMap.get(item.getBarcode()).getTotalDiscount()*itemAmounts.get(item);
                            }
                            //If it doesn't trigger RABAT just carry on and add normal price;
                        }else {
                            receipt += multipleItemsToFormattedString(itemAmounts, item);

                            //Add item price to totalPrice
                            totalPrice += item.getPrice()*itemAmounts.get(item);
                        }
                    }
                }
            }
        }

        //Print splitting line
        receipt += "\n-----------------------------";

        //Print total
        receipt += String.format(Locale.FRANCE, "\n%-20s %8.2f", "TOTAL",  Math.round(totalPrice * 100d)/100d);

        //Print splitting line
        receipt += "\n-----------------------------";

        //Print Mærker
        receipt += "\n\nKØBET HAR UDLØST " + (int) (totalPrice/50) + " MÆRKER";

        //Print Moms
        receipt += String.format(Locale.FRANCE, "\n\n%-20s %8.2f", "MOMS UDGØR", totalPrice*0.2);

        //Print cashier name
        receipt += "\n\nEKSPEDIENT: Jasper van't Veen";
        return receipt;
    }

    private String categoryString(String category){

        int catLength = 30-category.length();
        int paddingStart = category.length() + catLength/2;

        return String.format(Locale.FRANCE, "\n%"+ paddingStart +"s\n", "*"+category+"*");
    }

    private String itemToFormattedString(Item item){
        return String.format(Locale.FRANCE, "%-20s %8.2f\n", item.getName(), item.getPrice());
    }

    private String multipleItemsToFormattedString(Map<Item, Integer> itemAmounts, Item item) {
        return String.format(
                Locale.FRANCE,
                "%s\n %-1d X %-5.2f%19.2f\n",
                item.getName(),
                itemAmounts.get(item),
                item.getPrice(),
                itemAmounts.get(item)*item.getPrice());
    }
    private String itemToFormattedStringRabat(Item item, Map<String, Discount> discountMap){
        return String.format(
                Locale.FRANCE,
                "%-20s %8.2f\n%-20s %8.2f\n",
                item.getName(),
                discountMap.get(item.getBarcode()).getTotalDiscount(),
                "RABAT",
                item.getPrice()-discountMap.get(item.getBarcode()).getTotalDiscount());
    }

    private String multipleItemsToFormattedStringRabat(Map<String, Discount> discountMap, Map<Item, Integer> itemAmounts, Item item) {
        return String.format(
                Locale.FRANCE,
                "%s\n %-1d X %-5.2f%19.2f\n%-20s %8.2f-\n",
                item.getName(),
                itemAmounts.get(item),
                item.getPrice(),
                itemAmounts.get(item)*item.getPrice(),
                "RABAT",
                (item.getPrice()-discountMap.get(item.getBarcode()).getTotalDiscount())*itemAmounts.get(item));
    }
}
