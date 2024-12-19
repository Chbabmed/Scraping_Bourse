package com.BTC.Scrapers;// Class to handle scraping logic
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scrap {
    public List<String[]> scrapeData() throws IOException {
        List<String[]> data = new ArrayList<>();
        
        // scraping  
        
        String url = "https://finance.yahoo.com/quote/BTC-USD/history/";
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select("table tbody tr");
        
        // extraction des td 

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() >= 7) {
                String[] rowData = new String[7];
                for (int i = 0; i < 7; i++) {
                    rowData[i] = cells.get(i).text();
                }
                data.add(rowData);
            }
        }

        return data;
    }
}