package com.Project.Predection;

public class ModelTester {
    public static double calculateMeanSquaredError(double[] actual, double[] predicted) {
        double error = 0.0;
        for (int i = 0; i < actual.length; i++) {
            error += Math.pow(actual[i] - predicted[i], 2);
        }
        return error / actual.length;
    }
}

/*
// Étape 3 : Tester le modèle
        System.out.println("--------------------> le test : ");
double[] predictions = arima.predict(testData.length);
double mse = ModelTester.calculateMeanSquaredError(testData, predictions);
        System.out.println("Mean Squared Error: " + mse);
*/
