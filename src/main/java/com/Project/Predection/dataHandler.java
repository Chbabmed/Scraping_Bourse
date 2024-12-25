package com.Project.Predection;

import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;
import java.util.List;

public class dataHandler {
    public static double[] fetchClosingPrices(String startDate, String endDate) {
        BourseDataBaseHandler db = new BourseDataBaseHandler();
        List<transformActions> data = db.GetAllBourseData(startDate, endDate);

        double[] closingPrices = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            closingPrices[i] = data.get(i).getClosingPrice();
        }

        return closingPrices;
    }
}
