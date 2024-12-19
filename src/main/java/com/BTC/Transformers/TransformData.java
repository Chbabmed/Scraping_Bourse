package com.BTC.Transformers;// Class to handle data transformation
import com.BTC.Classes.BitcoinData;

import java.util.ArrayList;
import java.util.List;

public class TransformData {
    public List<BitcoinData> transformData(List<String[]> rawData) {
        List<BitcoinData> transformedData = new ArrayList<>();

        for (String[] row : rawData) {
            try {
                String date = DateFormatter.convertDate(row[0].trim());
                double open = Double.parseDouble(row[1].replace(",", ""));
                double high = Double.parseDouble(row[2].replace(",", ""));
                double low = Double.parseDouble(row[3].replace(",", ""));
                double close = Double.parseDouble(row[4].replace(",", ""));
                double adjClose = Double.parseDouble(row[5].replace(",", ""));
                long volume = Long.parseLong(row[6].replace(",", ""));

                transformedData.add(new BitcoinData(date, open, high, low, close, adjClose, volume));
            } catch (NumberFormatException e) {
                System.err.println("erreur de parsing à la ligne : " + String.join(", ", row));
            }
        }

        return transformedData;
    }
}
