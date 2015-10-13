package demo.core.chart

import groovy.util.logging.Slf4j

@Slf4j
class DataBucket {


    int from
    int to

    Map<String, Integer> frequencyMap = [:]
    Map<String, Double> averageValueMap = [:]


    boolean matches(int value) {
        (value > from && value <= to)
    }

    String addSample(String groupKey, int value) {
        initializeFrequencyToZero(groupKey)
        updateAverage(groupKey, value)
        updateSampleFrequency(groupKey)
    }

    void updateSampleFrequency(String key) {
        Integer frequency = frequencyMap.get(key)
        frequencyMap.put(key,  ++frequency)
    }

    void initializeFrequencyToZero(String groupKey) {
        Integer frequency = frequencyMap.get(groupKey)
        if (!frequency) {
            frequencyMap.put(groupKey, 0)
        }
    }


    void updateAverage(String groupKey, int value) {
        Double groupAverage = averageValueMap.get(groupKey)

        if (groupAverage) {
            averageValueMap.put(groupKey, calculateAvg(groupKey, groupAverage, value))
        } else {
            averageValueMap.put(groupKey, value)
        }
    }

    private Double calculateAvg(String groupKey, Double groupAverage, int value) {
        Integer groupFrequency = frequencyMap.get(groupKey);
        (groupFrequency * groupAverage + value)/(groupFrequency + 1)
    }

    Double getGroupAverage(String groupKey) {
        averageValueMap.get(groupKey)
    }

    String getBucketDescription() {
        if (to == Integer.MAX_VALUE) {
            "[$from +]"
        } else {
            "[$from - $to]"
        }
    }

    Integer getTotalSamples() {
        Integer total = 0
        frequencyMap.values().each { partialTotal ->
            total += partialTotal
        }
        return total
    }

}
