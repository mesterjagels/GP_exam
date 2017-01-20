public class Item {
    private String barcode, category, name;
    private int kr, ore;
    private float price;

    public Item(String barcode, String category, String name, int kr, int ore){
        this.barcode = barcode;
        this.category = category;
        this.name = name;
        this.kr = kr;
        this.ore = ore;
        this.price = (float) kr + (float) ore / 100;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
}
