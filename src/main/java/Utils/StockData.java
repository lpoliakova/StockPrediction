package Utils;

import java.util.Date;

/**
 * Created by oradchykova on 5/18/17.
 */
public class StockData {
    private final Date day;
    private final Double price;
    private final Double open;
    private final Double high;
    private final Double low;
    private final Boolean change;

    StockData(Date day, Double price, Double open, Double high, Double low, Boolean change){
        this.day = day;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.change = change;
    }

    public Date getDay() {
        return day;
    }

    public Double getPrice() {
        return price;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Boolean getChange() {
        return change;
    }

    @Override
    public String toString(){
        return String.format("[day: %s, price: %s, change: %s]", day, price, change);
    }
}
