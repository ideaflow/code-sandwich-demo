package demo.core.chart.bucket


class MovingAverageBucket extends DataBucket {

    List<Double> movingAverageSeries = []
    List<Double> samplesToAverage = []

    private final String bucketKey
    private final int numberOfSamplesToAverage

    MovingAverageBucket(String bucketKey, int numberOfSamplesToAverage) {
        this.bucketKey = bucketKey
        this.numberOfSamplesToAverage = numberOfSamplesToAverage
    }

    @Override
    boolean matches(String groupKey, Double value) {
        return (bucketKey == groupKey)
    }

    @Override
    void addDataSample(String groupKey, Double value) {

        samplesToAverage.add(value)
        removeExtraSamples(samplesToAverage)

        movingAverageSeries.add( calculateMovingAverage() )

    }

    @Override
    int getTotalSamples() {
        movingAverageSeries.size()
    }

    private Double calculateMovingAverage() {
        samplesToAverage.sum() / samplesToAverage.size()
    }

    private removeExtraSamples(List<?> samplesToAverage) {
        if (samplesToAverage.size() > numberOfSamplesToAverage) {
            samplesToAverage.remove(0)
        }
    }
}
