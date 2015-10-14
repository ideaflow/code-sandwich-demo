package demo.core.chart

import demo.core.dsl.IdeaFlowReader
import demo.core.model.IdeaFlowModel
import demo.core.timeline.TimeBand
import demo.core.timeline.Timeline
import demo.core.timeline.TimelineFactory
import groovy.io.FileType
import org.springframework.stereotype.Component

@Component
class DataSetManager {

    private final List<File> ifmFileList = []
    private final Map<File, List<TimeBand>> timeBandsMap = [:]

    DataSetManager() {
        initializeDataFromFiles()
    }

    ChartDataSet defaultDataSet() {
        ChartDataSet defaultDataSet = new ChartDataSet()
        defaultDataSet.ifmFileList = ifmFileList
        defaultDataSet.timeBandsMap = timeBandsMap
        return defaultDataSet
    }

    ChartDataSet filterByIfmFolder(ChartDataSet dataSet, String filter) {
        dataSet.ifmFileList = filterFilesByPath(filter)
        return dataSet
    }

    ChartDataSet filterBandsByHashtag(ChartDataSet dataSet, String filter) {
        dataSet.timeBandsMap = filterTimeBandsByHashtag(dataSet.ifmFileList, filter)
        return dataSet
    }

    private List<File> filterFilesByPath(filter) {
        ifmFileList.findAll { file ->
            file.absolutePath.contains(filter)
        }
    }

    private Map<File, List<TimeBand>> filterTimeBandsByHashtag(List<File> ifmFiles, String filter) {
        Map<File, List<TimeBand>> filteredTimeBandsMap = [:]

        ifmFiles.each { ifmFile ->
            List<TimeBand> timeBands = timeBandsMap.get(ifmFile)
            filteredTimeBandsMap.put(ifmFile, filterByHashtag(timeBands, filter))
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

    private void initializeDataFromFiles() {
        initBaseDir()
        initIfmFileList()
        ifmFileList.each { ifmFile ->
            IdeaFlowModel model = new IdeaFlowReader().readModel(ifmFile)
            Timeline timeline = new TimelineFactory().create(model)
            timeBandsMap.put(ifmFile, extractTimeBands(timeline))
        }
    }

    private List<TimeBand> extractTimeBands(Timeline timeline) {
        List<TimeBand> allTimeBands = []
        allTimeBands.addAll( timeline.getConflictBands() )
        allTimeBands.addAll( timeline.getGenericBands() )
        return allTimeBands
    }

    private void initIfmFileList() {
        getBaseDir().eachFileRecurse(FileType.FILES) { file ->
            if(file.name.endsWith('.ifm')) {
                ifmFileList.add(file)
            }
        }
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
