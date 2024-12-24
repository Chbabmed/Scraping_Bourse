package com.Project;

import com.Project.Classes.transformActions;
import com.Project.DB.BourseDataBaseHandler;
import com.Project.DB.LoadBourse;
import com.Project.GUI.homePage;
import com.Project.Scrapers.BourseScraper;
import com.Project.Transformers.BourseTransformer;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ------------------------------> Bourse code :


        String date = null;
        List<String[]> dirthyData;


        LoadBourse.GetLastDate("AKDITAL");

        BourseScraper Bs = new BourseScraper();
        dirthyData = Bs.BScraper();
        List<transformActions> cleanData = BourseTransformer.TransformData(dirthyData);

        LoadBourse loader = new LoadBourse();
        loader.loadBourseData(cleanData);







    }
}
