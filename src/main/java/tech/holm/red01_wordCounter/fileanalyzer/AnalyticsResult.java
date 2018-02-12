package tech.holm.red01_wordCounter.fileanalyzer;

import java.text.DecimalFormat;

public class AnalyticsResult {
    private String tokenValue;
    private int tokenAmount;
    private double tokenPercentage;

    public AnalyticsResult(String tokenValue, int tokenAmount) {
        this.tokenValue = tokenValue;
        this.tokenAmount = tokenAmount;
    }

    public void calculateTokenPercentage(double total) {
        this.tokenPercentage = tokenAmount/total*100;
    }

    public String getTokenValue() {

        return tokenValue;
    }

    public int getTokenAmount() {
        return tokenAmount;
    }

    public String printTokenPercentageFormatted() {
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(tokenPercentage);
    }

    @Override
    public String toString() {
        return "AnalyticsResult{" +
                "tokenValue='" + tokenValue + '\'' +
                ", tokenAmount=" + tokenAmount +
                ", tokenPercentage=" + tokenPercentage +
                '}';
    }
}
