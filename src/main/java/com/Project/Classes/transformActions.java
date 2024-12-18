package com.Project.Classes;



public class transformActions {

    // --> fields :
    private String tradeDate;
    private String company_Name;
    private String ticker;
    private double openingPrice;
    private double closingPrice;
    private double hightPrice;
    private double lowPrice;
    private double NumberOfSecuritiestTraded;
    private double volume;
    private int NumberOfTransactions;
    private double capitalization;


    // --> Constructor
    public transformActions(String tradeDate, String company_Name, String ticker, double openingPrice, double closingPrice,
                            double hightPrice, double lowPrice, double volume, double turnOver, int trades, double marketCap) {
        this.tradeDate = tradeDate;
        this.company_Name = company_Name;
        this.ticker = ticker;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.hightPrice = hightPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
        this.NumberOfSecuritiestTraded = turnOver;
        this.NumberOfTransactions = trades;
        this.capitalization = marketCap;
    }


    // --> getters :
    public String getTradeDate() {
        return tradeDate;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public String getTicker() {
        return ticker;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public double getHightPrice() {
        return hightPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getVolume() {
        return volume;
    }

    public double getNumberOfSecuritiestTraded() {
        return NumberOfSecuritiestTraded;
    }

    public int getNumberOfTransactions() {
        return NumberOfTransactions;
    }

    public double getCapitalization() {
        return capitalization;
    }

    @Override
    public String toString() {
        return "transformActions{" +
                "tradeDate='" + tradeDate + '\'' +
                ", company_Name='" + company_Name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ", hightPrice=" + hightPrice +
                ", lowPrice=" + lowPrice +
                ", volume=" + volume +
                ", turnOver=" + NumberOfSecuritiestTraded +
                ", trades=" + NumberOfTransactions +
                ", marketCap=" + capitalization +
                '}';
    }
}
