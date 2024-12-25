package com.Project.Predection;

public class ErrorCalculator {
    public static double calculateStandardDeviation(double[] actual, double[] predicted) {
        if (actual.length != predicted.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }

        double sumSquaredError = 0.0;
        for (int i = 0; i < actual.length; i++) {
            double error = actual[i] - predicted[i];
            sumSquaredError += error * error;
        }

        return Math.sqrt(sumSquaredError / actual.length);
    }
}
