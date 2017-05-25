package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by oradchykova on 5/18/17.
 */
public class FileReader {
    private static final int MAX_PERCENT = 100;

    public static List<StockData> read(File file, int amount, int percentage, boolean firstPart) throws FileNotFoundException{
        percentage = checkPercentage(percentage);

        List<StockData> stock = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            for (int i = 0; i < amount && scanner.hasNextLine(); i += MAX_PERCENT) {
                if (firstPart) {
                    read(scanner, stock, percentage);
                    skip(scanner, MAX_PERCENT - percentage);
                } else {
                    skip(scanner, MAX_PERCENT - percentage);
                    read(scanner, stock, percentage);
                }
            }
        }

        return stock;
    }

    private static void read(Scanner scanner, List<StockData> stock, int amount){
        for (int i = 0; i < amount; i++) {
            //System.out.println("i: " + i + " j: " + j);
            stock.add(readStockData(scanner.nextLine()));
        }
    }

    private static void skip(Scanner scanner, int amount){
        for (int i = 0; i < amount; i++) {
            scanner.nextLine();
        }
    }

    private static int checkPercentage(int percentage){
        return (percentage <= 0 || percentage >= MAX_PERCENT) ? MAX_PERCENT / 2 : percentage;
    }

    private static StockData readStockData(String rowData){
        String[] data = rowData.split("\t");
        return new StockData(parseDate(data[0]),
                Double.parseDouble(data[1]),
                Double.parseDouble(data[2]),
                Double.parseDouble(data[3]),
                Double.parseDouble(data[4]),
                parseChange(data[5]));
    }

    private static Date parseDate(String rowDate){
        String[] date = rowDate.split(" ");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(date[2]),
                parseMonth(date[0]),
                Integer.parseInt(date[1].substring(0, date[1].length() - 1)));
        return calendar.getTime();
    }

    private static int parseMonth(String rowMonth){
        if (rowMonth.toLowerCase().equals("jan")) return Calendar.JANUARY;
        if (rowMonth.toLowerCase().equals("feb")) return Calendar.FEBRUARY;
        if (rowMonth.toLowerCase().equals("mar")) return Calendar.MARCH;
        if (rowMonth.toLowerCase().equals("apr")) return Calendar.APRIL;
        if (rowMonth.toLowerCase().equals("may")) return Calendar.MAY;
        if (rowMonth.toLowerCase().equals("jun")) return Calendar.JUNE;
        if (rowMonth.toLowerCase().equals("jul")) return Calendar.JULY;
        if (rowMonth.toLowerCase().equals("aug")) return Calendar.AUGUST;
        if (rowMonth.toLowerCase().equals("sep")) return Calendar.SEPTEMBER;
        if (rowMonth.toLowerCase().equals("oct")) return Calendar.OCTOBER;
        if (rowMonth.toLowerCase().equals("nov")) return Calendar.NOVEMBER;
        if (rowMonth.toLowerCase().equals("dec")) return Calendar.DECEMBER;
        return 0;
    }

    private static Boolean parseChange(String rowChange){
        return rowChange.charAt(0) != '-';
    }
}
