package demo.core.chart.bucket

abstract class DataBucket {

    final void addSample(String groupKey, Double value) {
        if (matches(groupKey, value) == false) return
        addDataSample(groupKey, value)
    }

    abstract boolean matches(String groupKey, Double value)

    abstract void addDataSample(String groupKey, Double value)

    abstract int getTotalSamples()
}