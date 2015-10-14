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
import demo.core.ifm.ifmsource.IfmSource
import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DataSetManager {

    private final Map<IfmTask, List<TimeBand>> timeBandsMap = [:]

    private IfmSource ifmSource

    @Autowired
    DataSetManager(IfmSource ifmSource) {
        this.ifmSource = ifmSource
        initializeTaskData()
    }

    ChartDataSet defaultDataSet() {
        ChartDataSet defaultDataSet = new ChartDataSet (
                ifmTaskList: [] + ifmSource.allIfmTasks(),
                timeBandsMap: [:] + timeBandsMap)
        return defaultDataSet
    }

    ChartDataSet filterIfmTasksByAuthor(ChartDataSet dataSet, String author) {
        dataSet.ifmTaskList = filterTasks(author)
        return dataSet
    }

    ChartDataSet filterTimeBandsByHashtag(ChartDataSet dataSet, String hashtag) {
        dataSet.timeBandsMap = filterTimeBands(dataSet.ifmTaskList, hashtag)
        return dataSet
    }

    private List<IfmTask> filterTasks(String author) {
        ifmSource.allIfmTasks().findAll { task ->
            task.isByAuthor(author)
        }
    }

    private Map<IfmTask, List<TimeBand>> filterTimeBands(List<IfmTask> taskList, String filter) {
        Map<IfmTask, List<TimeBand>> filteredTimeBandsMap = [:]

        taskList.each { task ->
            List<TimeBand> timeBands = timeBandsMap.get(task)
            filteredTimeBandsMap.put(task, filterByHashtag(timeBands, filter))
        }
        return filteredTimeBandsMap
    }

    private List<TimeBand> filterByHashtag(List<TimeBand> timeBands, String filter) {
        List<TimeBand> filteredTimeBands = []
        timeBands.each { timeBand ->
            if (matchesHashtag(timeBand.comment, filter)) {
                filteredTimeBands.add(timeBand)
            }
        }
        return filteredTimeBands
    }

    private boolean matchesHashtag(String line, String filter) {
        line?.toLowerCase()?.contains(filter)
    }

    private void initializeTaskData() {
        ifmSource.allIfmTasks().each { task ->
            IdeaFlowModel model = task.readIfm()
            Timeline timeline = new TimelineFactory().create(model)
            timeBandsMap.put(task, extractTimeBands(timeline))
        }
    }

    private List<TimeBand> extractTimeBands(Timeline timeline) {
        List<TimeBand> allTimeBands = []
        allTimeBands.addAll( timeline.getConflictBands() )
        allTimeBands.addAll( timeline.getGenericBands() )
        return allTimeBands
    }




}
