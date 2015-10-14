/*
 * Copyright 2015 New Iron Group, Inc.
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
