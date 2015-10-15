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

import demo.core.chart.bucket.RangeBucket
import spock.lang.Specification


class RangeBucketSpec extends Specification {

    RangeBucket bucket = new RangeBucket(1, 100)

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