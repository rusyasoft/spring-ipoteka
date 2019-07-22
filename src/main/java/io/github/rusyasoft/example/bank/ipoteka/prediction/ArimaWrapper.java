package io.github.rusyasoft.example.bank.ipoteka.prediction;

import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.Ts;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;

public class ArimaWrapper {

    public int getMonthPrediction(int month, double[] testData) {

        TimeSeries timeSeries = Ts.newMonthlySeries(2005, 1, testData);

        ArimaOrder modelOrder = ArimaOrder.order(0, 0, 0, 1, 1, 1);

        Arima model = Arima.model(timeSeries, modelOrder);

        Forecast forecast = model.forecast(14);

        System.out.println("forecast: " + forecast);

        int prediction = (int) Math.round(forecast.pointEstimates().at(1 + month));

        return prediction;
    }

}
