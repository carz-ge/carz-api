package ge.carapp.carappapi.utils;

public class PaymentUtils {

    public static double convertPriceIntoDouble(int price) {
        return ((double) price) / 100;
    }
    public static String convertPriceiIntoDoubleString(int amountInTetri) {
        int tetri = amountInTetri % 100;
        if (tetri == 0) {
            return "%d".formatted(amountInTetri / 100);
        }
        return "%d.%d".formatted(amountInTetri / 100, tetri);
    }
}
