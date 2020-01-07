package me.raghu.chartslib.hellocharts.formatter;

import me.raghu.chartslib.hellocharts.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
