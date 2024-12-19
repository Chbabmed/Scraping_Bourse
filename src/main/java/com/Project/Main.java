package com.Project;

import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;
import com.Project.Scrapers.BourseScraper;
import com.Project.Transformers.BourseTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;  // Import Arrays class

public class Main {
    public static void main(String[] args) {

        // ------------------------------> Bourse code :
        //-> variables :
        List<String[]> dirthyData;
        boolean inserted = false;

        BourseScraper Bs = new BourseScraper();
        dirthyData = Bs.BScraper();
        List<transformActions> cleanData = BourseTransformer.TransformData(dirthyData);

        // Print each row (String[]) in dirthyData
        for (transformActions row : cleanData) {
            BourseDataBaseHandler.InsertBourseData(row);
        }
    }
}
