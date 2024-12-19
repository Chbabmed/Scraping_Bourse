package com.BTC.DB;// Class to handle data loading
import com.BTC.Classes.BitcoinData;
import com.BTC.DB.DatabaseHandler;

import java.util.List;

public class Load {
    public void loadData(DatabaseHandler dbHandler, List<BitcoinData> data) {
    	
     // Retrieve the last inserted date only once
        String RecentInsertedDate = dbHandler.getLastInsertedDate();
        
        if (RecentInsertedDate == null) {
        	RecentInsertedDate = "2009-01-01";
        }

        int insertedCount = 0;  // Counter for successful insertions

        for (BitcoinData record : data) {
            // Increment counter only if the data is successfully inserted
            if (dbHandler.insertData(record , RecentInsertedDate)) {
                insertedCount++;  // Increment the counter only for successful insertions
            }
        }

        // Display the number of successfully inserted rows
        System.out.println(insertedCount + " ligne(s) insérée(s).");
    }
}
