/**
 * Created by Jazz on 15-12-2016.
 */
public class Discount {
    String barcode;
    int kr, ore, limit;
    float totalDiscount = 0;

    public Discount(String barcode, int limit, int kr, int ore) {
        this.barcode = barcode;
        this.kr = kr;
        this.ore = ore;
        this.limit = limit;
        totalDiscount = (float) kr + (float) ore / 100;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getLimit() {
        return limit;
    }

    public float getTotalDiscount() {
        return totalDiscount;
    }
}
