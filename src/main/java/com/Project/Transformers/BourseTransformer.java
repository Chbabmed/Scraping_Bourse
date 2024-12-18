package com.Project.Transformers;

import com.Project.Classes.transformActions;

import java.util.ArrayList;
import java.util.List;

public class BourseTransformer {

    public static List<transformActions> TransformData(List<String[]> dirthyData){
        List<transformActions> valideData = new ArrayList<>();

        for(String[] row : dirthyData){

            try {
                String tr_date  = DateFormatter.convertDate(row[0].trim());
                String cp_name  = row[1];
                String tick     = row[2];

                // Clean and parse the numbers
                double op_price = parsePrice(row[3]);
                double cl_price = parsePrice(row[4]);
                double hg_price = parsePrice(row[5]);
                double lw_price = parsePrice(row[6]);
                double vl       = parsePrice(row[7]);
                double tr_over  = parsePrice(row[8]);
                int    tds      = Integer.parseInt(row[9]);
                double mk_cap   = parsePrice(row[10]);

                valideData.add(new transformActions(tr_date, cp_name, tick, op_price, cl_price, hg_price, lw_price, vl, tr_over, tds, mk_cap));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        return valideData;
    }

    // Helper method to clean and parse the price
    private static double parsePrice(String price) {
        // Remove spaces and replace comma with dot for decimal separation
        price = price.replace(" ", "").replace(",", ".");
        return Double.parseDouble(price);
    }


}

