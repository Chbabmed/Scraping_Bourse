package com.Project.Predection;

public class ARIMAModel {
    private double[] trainingData;
    private double ecartdError;

    public double getEcartdError() {
        return ecartdError;
    }

    public ARIMAModel(double[] trainingData) {
        this.trainingData = trainingData;
    }

    public void trainModel(double[] testData) {
        System.out.println("Training the ARIMA model...");

        // calculer la marge d'erreur
        double[] margePredictions = predict(testData.length);

        // Calculer l'écart des erreurs
        this.ecartdError = ErrorCalculator.calculateStandardDeviation(testData, margePredictions);

        System.out.println("Model trained with standard error: " + ecartdError);
    }

    public double[] predict(int steps) {
        double[] predictions = new double[steps];

        // Simple simulation pour générer des prédictions (à remplacer par la logique ARIMA réelle)
        double lastValue = trainingData[trainingData.length - 1];
        for (int i = 0; i < steps; i++) {
            // Ajouter une petite variation pour simuler les prédictions
            lastValue += (Math.random() * 5 - 2.5); // Variation aléatoire
            predictions[i] = lastValue;
        }

        return predictions;
    }

    public double[][] predictWithMargin(int steps) {
        double[] predictions = predict(steps);
        double[][] predictionsWithMargin = new double[steps][3];

        for (int i = 0; i < steps; i++) {
            double lowerBound = predictions[i] - ecartdError;
            double upperBound = predictions[i] + ecartdError;

            predictionsWithMargin[i][0] = predictions[i];
            predictionsWithMargin[i][1] = lowerBound;
            predictionsWithMargin[i][2] = upperBound;
        }

        return predictionsWithMargin;
    }
}