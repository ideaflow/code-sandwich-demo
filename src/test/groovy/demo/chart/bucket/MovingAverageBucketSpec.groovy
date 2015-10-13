package demo.chart.bucket

import demo.core.chart.bucket.MovingAverageBucket
import spock.lang.Specification

class MovingAverageBucketSpec extends Specification {



    def "should ignore data that doesn't match bucket key"() {
        given:
        MovingAverageBucket bucket = new MovingAverageBucket("apples", 4)

        when:
        bucket.addSample("apples", 4)
        bucket.addSample("apples", 7)
        bucket.addSample("bananas", 8)

        then:
        bucket.totalSamples == 2
    }

    def "should average the last X number of samples"() {
        given:
        MovingAverageBucket bucket = new MovingAverageBucket("apples", 2)

        when:
        bucket.addSample("apples", 2)
        bucket.addSample("apples", 6)
        bucket.addSample("apples", 10)
        bucket.addSample("apples", 18)
        bucket.addSample("apples", 20)


        then:
        bucket.totalSamples == 5
        bucket.movingAverageSeries == [2d, 4d, 8d, 14d, 19d]
    }
}
