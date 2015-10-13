package demo.transform

import demo.core.chart.DataBucket
import spock.lang.Specification


class DataBucketSpec extends Specification {

    DataBucket bucket = new DataBucket()

    def "sample size should match the number of samples added"() {
        when:
        bucket.addSample("apples", 4)
        bucket.addSample("bananas", 8)
        then:
        bucket.totalSamples == 2
    }

    def "values should be AVERAGED for samples are in the SAME group"() {
        when:
        bucket.addSample("apples", 4)
        bucket.addSample("apples", 10)

        then:
        bucket.getGroupAverage("apples") == 7.0d
    }

    def "values should be EXCLUDED from average if samples are in a DIFFERENT group"() {
        when:
        bucket.addSample("apples", 4)
        bucket.addSample("apples", 10)
        bucket.addSample("kiwi", 5)

        then:
        bucket.getGroupAverage("apples") == 7.0d
        bucket.getGroupAverage("kiwi") == 5d
    }


}