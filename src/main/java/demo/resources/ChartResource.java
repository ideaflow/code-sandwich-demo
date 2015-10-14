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
import demo.core.chart.builder.IdeaFlowChartBuilder;
import demo.core.chart.builder.MovingAvgChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@Path(ResourcePaths.CHART_PATH )
@Produces(MediaType.APPLICATION_JSON)
public class ChartResource {

	@Autowired
	ChartGenerator chartGenerator;

	@Autowired
	DataSetManager dataSetManager;

	@GET
	public List<FrictionChart> getFilteredCharts(@QueryParam("author") String author, @QueryParam("hashtag") String hashtag) {

		System.out.println("author = "+author);
		System.out.println("hashtag = "+hashtag);

		ChartDataSet filteredDataSet = dataSetManager.defaultDataSet();

		if (author != null) {
			filteredDataSet = dataSetManager.filterByIfmFolder(filteredDataSet, author);
		}
		if (hashtag != null) {
			filteredDataSet = dataSetManager.filterBandsByHashtag(filteredDataSet, hashtag);
		}

		List<IdeaFlowChartBuilder> builders = new ArrayList<>();
		builders.add( chartGenerator.configure(filteredDataSet, new FrequencyChartBuilder()));
		builders.add(chartGenerator.configure(filteredDataSet, new MovingAvgChartBuilder()));

        return chartGenerator.generateCharts(builders);
	}

}
