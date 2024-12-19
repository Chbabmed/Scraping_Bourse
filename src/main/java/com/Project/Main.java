package com.Project;

import com.Project.GUI.homePage;

public class Main {
    public static void main(String[] args) {

        // ------------------------------> Bourse code :
/*        //-> variables :
        List<String[]> dirthyData;
        boolean inserted = false;

        BourseScraper Bs = new BourseScraper();
        dirthyData = Bs.BScraper();
        List<transformActions> cleanData = BourseTransformer.TransformData(dirthyData);

        // Print each row (String[]) in dirthyData
        for (transformActions row : cleanData) {
            BourseDataBaseHandler.InsertBourseData(row);
        }*/
        homePage home = new homePage();
        home.display();


    }
}
