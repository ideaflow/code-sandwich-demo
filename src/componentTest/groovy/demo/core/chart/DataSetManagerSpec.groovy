package demo.core.chart

import demo.ComponentTest
import demo.core.ifm.ifmsource.IfmTask
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class DataSetManagerSpec extends Specification {

    @Autowired
    private ChartDataSetFactory dataSetManager

    def "should initialize tasks from package source"() {
        when:
        ChartDataSet dataSet = dataSetManager.defaultDataSet()

        then:
        dataSet.ifmTaskList.size() > 0
    }

    def "should filter tasks by author (temporarily by path name)"() {
        when:
        ChartDataSet filteredDataSet =
            dataSetManager.filterIfmTasksByAuthor(dataSetManager.defaultDataSet(), "tagged")

        then:
        filteredDataSet.size() > 0
        filteredDataSet.size() < dataSetManager.defaultDataSet().size()
    }

    def "should filter timebands by tag"() {
        when:
        ChartDataSet filteredDataSet =
                dataSetManager.filterTimeBandsByHashtag(dataSetManager.defaultDataSet(), "experimentpain")

        then:
        IfmTask painTaggedTask = filteredDataSet.ifmTaskList[0]
        painTaggedTask.taskName == "ex_tagged_experimentpain.ifm"
        filteredDataSet.getTimeBands(painTaggedTask).size() == 1
    }


}
