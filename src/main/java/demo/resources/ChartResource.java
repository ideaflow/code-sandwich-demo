/**
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
package demo.resources;

import demo.api.FrictionChart;
import demo.api.ResourcePaths;
import demo.core.chart.ChartDataSet;
import demo.core.chart.ChartGenerator;
import demo.core.chart.DataSetManager;
import demo.core.chart.builder.FrequencyChartBuilder;
import demo.core.chart.builder.MovingAvgChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Component
@Path(ResourcePaths.CHART_PATH )
@Produces(MediaType.APPLICATION_JSON)
public class ChartResource {

	@Autowired
	ChartGenerator chartGenerator;

	@Autowired
	DataSetManager dataSetManager;

	@GET
	@Path(ResourcePaths.FREQUENCY_PATH)
	public FrictionChart getFrequencyChart(@QueryParam("author") String author, @QueryParam("hashtag") String hashtag) {
		ChartDataSet filteredDataSet = createFilteredDataSet(author, hashtag);
		return chartGenerator.generateChart(new FrequencyChartBuilder(filteredDataSet));
	}

	@GET
	@Path(ResourcePaths.SERIES_PATH)
	public FrictionChart getSeriesChart(@QueryParam("author") String author, @QueryParam("hashtag") String hashtag) {
		ChartDataSet filteredDataSet = createFilteredDataSet(author, hashtag);
		return chartGenerator.generateChart(new MovingAvgChartBuilder(filteredDataSet));
	}

	private ChartDataSet createFilteredDataSet(String author, String hashtag) {

		ChartDataSet filteredDataSet = dataSetManager.defaultDataSet();

		if (author != null) {
			filteredDataSet = dataSetManager.filterIfmTasksByAuthor(filteredDataSet, author);
		}
		if (hashtag != null) {
			filteredDataSet = dataSetManager.filterTimeBandsByHashtag(filteredDataSet, hashtag);
		}
		return filteredDataSet;
	}


}
