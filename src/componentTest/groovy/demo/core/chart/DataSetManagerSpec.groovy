package demo.core.chart

import demo.ComponentTest
import demo.core.ifm.ifmsource.IfmTask
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class DataSetManagerSpec extends Specification {

    @Autowired
    private ChartDataSetFactory dataSetFactory

    def "should initialize tasks from package source"() {
        when:
        ChartDataSet dataSet = dataSetFactory.defaultDataSet()

        then:
        dataSet.tasks.size() > 0
    }

    def "should filter tasks by author (temporarily by path name)"() {
        when:
        ChartDataSet filteredDataSet = dataSetFactory.defaultDataSet().filterByAuthor("tagged")

        then:
        filteredDataSet.size() > 0
        filteredDataSet.size() < dataSetFactory.defaultDataSet().size()
    }

    def "should filter timebands by tag"() {
        when:
        ChartDataSet filteredDataSet = dataSetFactory.defaultDataSet().filterByHashtag("experimentpain")

        then:
        IfmTask painTaggedTask = filteredDataSet.tasks[0]
        painTaggedTask.taskName == "ex_tagged_experimentpain.ifm"
        painTaggedTask.unfilteredTimeBands.size() == 2
    }


}
