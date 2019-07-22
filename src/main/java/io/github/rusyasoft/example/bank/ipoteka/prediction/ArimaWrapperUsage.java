package io.github.rusyasoft.example.bank.ipoteka.prediction;

import com.github.signaflo.timeseries.TimeSeries;
import com.github.signaflo.timeseries.Ts;
import com.github.signaflo.timeseries.forecast.Forecast;
import com.github.signaflo.timeseries.model.arima.Arima;
import com.github.signaflo.timeseries.model.arima.ArimaOrder;

public class ArimaWrapperUsage {

    private double[] testData = {846, 864, 1234, 1176, 1145, 1532, 1479, 1144, 1203, 1061, 917, 630, 534, 416, 707, 516, 589, 595, 406, 508, 387, 360, 404, 389, 282, 263, 464, 376, 516, 498, 444, 377, 422, 1650, 1328, 1640, 1832, 1659, 1428, 1369, 1501, 1472, 1263, 637, 495, 496, 257, 377, 383, 394, 839, 577, 870, 918, 509, 859, 1282, 796, 822, 433, 1779, 2233, 1926, 886, 1966, 1347, 1510, 929, 705, 767, 911, 1058, 982, 1140, 2845, 2134, 1784, 2541, 2296, 2491, 1853, 4084, 2953, 4015, 2433, 2527, 3483, 4506, 3677, 3390, 3080, 3399, 2617, 2238, 2741, 3506, 1674, 3486, 1986, 3156, 3517, 2364, 2491, 2295, 2739, 3622, 2847, 2886, 3074, 2932, 3108, 4121, 3139, 2721, 3435, 5576, 3783, 4737, 3580, 8132, 3335, 3906, 5965, 5217, 5267, 6104, 3752, 3517, 4762, 5796, 5280, 4848, 4370, 5073, 6919, 5669, 7297, 6750, 5110, 5760, 3355, 4152, 3257, 3668, 1954, 3278, 1991, 3054, 2611, 3287, 2536, 4140, 5634, 2995};

    public ArimaWrapperUsage() {

        TimeSeries timeSeries = Ts.newMonthlySeries(2005, 1, testData);

        ArimaOrder modelOrder = ArimaOrder.order(0, 0, 0, 1, 1, 1);

        Arima model = Arima.model(timeSeries, modelOrder);

        Forecast forecast = model.forecast(14);

        System.out.println("forecast: " + forecast);

        System.out.println("index 1 : " + forecast.pointEstimates().at(1 + 2));

    }
}
