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

import demo.core.ifm.ifmsource.IfmTask
import demo.core.timeline.TimeBand

class ChartDataSet {

    private List<IfmTask> filteredTasks
    private Map<IfmTask, List<TimeBand>> filteredTimeBands

    ChartDataSet(List<IfmTask> ifmTaskList) {
        filteredTasks = ifmTaskList
        filteredTimeBands = createTimeBandsMap(ifmTaskList)
    }

    int size() {
        filteredTasks.size()
    }

    List<IfmTask> getFilteredTasks() {
        filteredTasks
    }

    List<TimeBand> getFilteredBands(IfmTask task) {
        filteredTimeBands.get(task)
    }

    ChartDataSet filterIfmTasksByAuthor(String author) {
        filteredTasks = filterTasks(author)
        this
    }

    ChartDataSet filterTimeBandsByHashtag(String hashtag) {
        filteredTimeBands = filterTimeBands(hashtag)
        this
    }

    private List<IfmTask> filterTasks(String author) {
        filteredTasks.findAll { task ->
            task.isByAuthor(author)
        }
    }

    private Map<IfmTask, List<TimeBand>> createTimeBandsMap(List<IfmTask> ifmTaskList) {
        Map<IfmTask, List<TimeBand>> timeBandsMap = [:]
        ifmTaskList.each { ifmTask ->
            timeBandsMap.put(ifmTask, [] + ifmTask.getUnfilteredTimeBands())
        }
        return timeBandsMap
    }


    private Map<IfmTask, List<TimeBand>> filterTimeBands(String hashtag) {
        Map<IfmTask, List<TimeBand>> newTimebandsMap = [:]

        filteredTasks.each { task ->
            List<TimeBand> timeBands = filteredTimeBands.get(task)
            newTimebandsMap.put(task, filterByHashtag(timeBands, hashtag))
        }
        return newTimebandsMap
    }

    private List<TimeBand> filterByHashtag(List<TimeBand> timeBands, String hashtag) {
        List<TimeBand> filteredTimeBands = []
        timeBands.each { timeBand ->
            if (matchesHashtag(timeBand.comment, hashtag)) {
                filteredTimeBands.add(timeBand)
            }
        }
        return filteredTimeBands
    }

    private boolean matchesHashtag(String line, String filter) {
        line?.toLowerCase()?.contains(filter)
    }
}
