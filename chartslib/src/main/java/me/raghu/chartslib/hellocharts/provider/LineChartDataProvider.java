package me.raghu.chartslib.hellocharts.provider;


import me.raghu.chartslib.hellocharts.model.LineChartData;

public interface LineChartDataProvider {

    public LineChartData getLineChartData();

    public void setLineChartData(LineChartData data);

}
