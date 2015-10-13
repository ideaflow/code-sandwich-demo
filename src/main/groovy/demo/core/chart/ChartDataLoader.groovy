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

import demo.api.FrictionChart
import demo.api.SingleSeriesChart
import demo.core.dsl.IdeaFlowReader
import demo.core.model.BandType
import demo.core.model.IdeaFlowModel
import groovy.io.FileType
import groovy.util.logging.Slf4j

@Slf4j
class ChartDataLoader {

    //sort files by date then, put together moving average of last 10 conflicts.  If less than 10, point counts on it's own.
    //need different data bucket types.

    //frequency by range bucket
    //total minutes by pain type
    //moving average over time

    //files, want to be able to select files by person.  Put into subfolders
    //person would be driven by command line parameters.  Files would get passed into data generator.

    //generate files with tags, and be able to select groups by tag.
    //summarize by pain type?


    private ChartDataGenerator chartDataGenerator

    void loadAll() {
        initBaseDir()
        List<File> ifmFiles = retrieveIfmList()
        loadIfmData(ifmFiles)
    }

    SingleSeriesChart createFrequencyChart() {
        SingleSeriesChart chart = new SingleSeriesChart()
        chart.title = "Frequency Chart"

        ChartData chartData = chartDataGenerator.generateFrequencyChartData()
        chart.ticks = chartData.barSets.collect { barSet ->
            barSet.label
        }
        chart.values = chartData.barSets.collect { barSet ->
            barSet.total
        }
        return chart

    }

    FrictionChart createFrictionFrequencyChart() {
        FrictionChart chart = new FrictionChart()
        chart.title = "Friction Frequency (Grouped by Minutes)"

        ChartData chartData = chartDataGenerator.generateFrequencyChartData()
        chart.ticks = chartData.barSets.collect { barSet ->
            barSet.label
        }

        List<Double> conflictSeries = []
        List<Double> learningSeries = []
        List<Double> reworkSeries = []

        chartData.barSets.each { barSet ->
            conflictSeries.add( barSet.getBarValue(BandType.conflict.name()) )
            learningSeries.add( barSet.getBarValue(BandType.learning.name()) )
            reworkSeries.add( barSet.getBarValue(BandType.rework.name()) )
        }
        chart.conflictSeries = conflictSeries
        chart.learningSeries = learningSeries
        chart.reworkSeries = reworkSeries

        println conflictSeries
        println learningSeries
        println reworkSeries

        return chart

    }

    ChartData createAveragesChart() {
        chartDataGenerator.generateAveragesChartData()
    }

    private void loadIfmData(List<File> ifmFiles) {
        chartDataGenerator = new ChartDataGenerator()
        ifmFiles.each { file ->
            log.info("Loading IFM file -> ${file.path}")
            IdeaFlowModel model = new IdeaFlowReader().readModel(file)
            chartDataGenerator.fillChart(model)
        }
    }

    private List<File> retrieveIfmList() {
        List<File> files = []
        getBaseDir().eachFileRecurse(FileType.FILES) { file ->
            if(file.name.endsWith('.ifm')) {
                files.add(file)
            }
        }
        return files;
    }

    private void initBaseDir() {
        File baseIdeaFlowDir = getBaseDir()
        baseIdeaFlowDir.mkdirs()
    }

    private File getBaseDir() {
        String userHome = System.getProperty("user.home")
        new File("$userHome/.ifmfiles")
    }
}
