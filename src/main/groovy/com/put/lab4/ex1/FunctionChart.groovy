package com.put.lab4.ex1

import javafx.scene.chart.*

class FunctionChart {

    static public final int LINE_CHART = 1
    static public final int POINT_CHART = 2
    static public final int AREA_CHART = 3

    static private double yMax = 1.0
    static private double yMin = -1.0

    static private List<XYChart.Data> generatePoints(double xStart, double xEnd, double increment, Closure<Double> function) {
        List<XYChart.Data> result = new ArrayList<>()

        for (double i = xStart; i <= xEnd; i = (i + increment).round(4)) {
            final double functionResult = function(i)
            if (functionResult > yMax && functionResult != Double.NaN) {
                yMax = functionResult
            } else if (functionResult < yMin && functionResult != Double.NaN) {
                yMin = functionResult
            }
            if (functionResult != Double.NaN) {
                result.add(new XYChart.Data(i, functionResult))
            }
        }

        return result
    }

    static Chart createChart(double xStart, double xEnd, int chartType, Closure function) {
        yMin = -1.0
        yMax = 1.0

        final NumberAxis xAxis = new NumberAxis()
        xAxis.setTickUnit(1)
        xAxis.setLabel("x")
        xAxis.setAutoRanging(false)
        xAxis.setLowerBound(xStart)
        xAxis.setUpperBound(xEnd)

        final NumberAxis yAxis = new NumberAxis()
        yAxis.setLabel("f(x)")

        XYChart.Series series = new XYChart.Series()
        series.setName("f(x)")

        List<XYChart.Data> points
        if (chartType == LINE_CHART) {
            points = generatePoints(xStart, xEnd, 0.001, function)
        } else {
            points = generatePoints(xStart, xEnd, 0.1, function)
        }
        series.getData().addAll(points)

        yMin = yMin.round() - 1
        yMax = yMax.round() + 1
        yAxis.setAutoRanging(false)
        yAxis.setLowerBound(yMin)
        yAxis.setUpperBound(yMax)

        return createChart(xAxis, yAxis, series, chartType)
    }

    static Chart createChart(NumberAxis xAxis, NumberAxis yAxis, XYChart.Series series, int chartType) {
        if (chartType == LINE_CHART) {
            final Chart chart = new LineChart<Number, Number>(xAxis, yAxis)
            chart.setCreateSymbols(false)
            chart.getData().add(series)
            return chart
        } else if (chartType == POINT_CHART) {
            final Chart chart = new ScatterChart<Number, Number>(xAxis, yAxis)
            chart.getData().add(series)
            return chart
        } else if (chartType == AREA_CHART) {
            final Chart chart = new AreaChart<Number, Number>(xAxis, yAxis)
            chart.getData().add(series)
            return chart
        } else {
            return null
        }
    }

    static Chart createLineChart(double xStart, double xEnd, Closure function) {
        return createChart(xStart, xEnd, 1, function)
    }

}
