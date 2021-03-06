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

import demo.core.ifm.ifmsource.IfmSource
import demo.core.ifm.ifmsource.IfmTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChartDataSetFactory {

    private IfmSource ifmSource

    @Autowired
    ChartDataSetFactory(IfmSource ifmSource) {
        this.ifmSource = ifmSource
    }

    ChartDataSet defaultDataSet() {
        return new ChartDataSet (createIfmTaskList())
    }

    private List<IfmTask> createIfmTaskList() {
        [] + ifmSource.allIfmTasks()
    }









}
