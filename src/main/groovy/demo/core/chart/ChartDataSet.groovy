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
package demo.core.chart

import demo.core.ifm.ifmsource.FilteredIfmTask
import demo.core.ifm.ifmsource.IfmTask
import demo.core.ifm.ifmsource.TimeBandFilter
import demo.core.timeline.TimeBand

class ChartDataSet {

    List<IfmTask> tasks

    ChartDataSet(List<IfmTask> tasks) {
        this.tasks = tasks
    }

    int size() {
        tasks.size()
    }

    ChartDataSet filterByAuthor(String author) {
        List<IfmTask> filteredTasks = tasks.findAll { IfmTask task ->
            task.isByAuthor(author)
        }
        new ChartDataSet(filteredTasks)
    }

    ChartDataSet filterByHashtag(String hashtag) {
        HashtagTimeBandFilter filter = new HashtagTimeBandFilter(hashtag)
        List<IfmTask> filteredTasks = tasks.collect { IfmTask task ->
            new FilteredIfmTask(task, filter)
        }
        new ChartDataSet(filteredTasks)
    }


    private static class HashtagTimeBandFilter implements TimeBandFilter {

        private String hashtag

        HashtagTimeBandFilter(String hashtag) {
            this.hashtag = hashtag
        }

        @Override
        boolean matches(TimeBand timeBand) {
            timeBand.comment?.toLowerCase()?.contains(hashtag)
        }
    }

}
