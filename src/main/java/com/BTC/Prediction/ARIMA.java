package com.BTC.Prediction;

import java.io.Serializable;

public class ARIMA implements Serializable {
    private double[] data;
    private double[] coefficients;

    public ARIMA(double[] data) {
        this.data = data;
    }

    // Entraîne le modèle (ajustement des coefficients)
    public void train() {
        // Simplification : ajustement de base pour un AR(1)
        int n = data.length;
        double sumXt = 0, sumXt1 = 0, sumXtXt1 = 0, sumXt2 = 0;

        for (int t = 1; t < n; t++) {
            sumXt += data[t - 1];
            sumXt1 += data[t];
            sumXtXt1 += data[t - 1] * data[t];
            sumXt2 += data[t - 1] * data[t - 1];
        }

        double phi = (n * sumXtXt1 - sumXt * sumXt1) / (n * sumXt2 - sumXt * sumXt);
        double intercept = (sumXt1 - phi * sumXt) / n;

        coefficients = new double[] {intercept, phi};
        System.out.println("ARIMA entrainé : intercept = " + intercept + ", phi = " + phi);
    }

    // Prédit la prochaine valeur
    public double predictNext(double[] data) {
        double lastValue = data[data.length - 1];
        return coefficients[0] + coefficients[1] * lastValue;
    }
}
