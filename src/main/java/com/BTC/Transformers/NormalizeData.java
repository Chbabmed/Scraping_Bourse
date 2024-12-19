package com.BTC.Transformers;

import java.io.Serializable;
import java.util.Arrays;

public class NormalizeData implements Serializable {
    private double mean;
    private double std;

    // Normalise les données
    public double[] normalize(double[] data) {
        mean = Arrays.stream(data).average().orElse(0.0);
        std = Math.sqrt(Arrays.stream(data).map(d -> Math.pow(d - mean, 2)).sum() / data.length);

        double[] normalized = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalized[i] = (data[i] - mean) / std;
        }
        return normalized;
    }

    // Dénormalise une valeur
    public double denormalize(double normalizedValue) {
        return normalizedValue * std + mean;
    }
}
