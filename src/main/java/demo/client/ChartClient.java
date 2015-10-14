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
package demo.client;

import demo.api.FrictionChart;
import demo.api.ResourcePaths;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChartClient {
    private String resourceUri;
    private RestTemplate restTemplate;

    public ChartClient(String hostUri) {
        resourceUri = hostUri + ResourcePaths.CHART_PATH;
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    }

    public List<FrictionChart> getFilteredCharts(String author,String hashtag) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceUri)
                .queryParam("author", author)
                .queryParam("hashtag", hashtag);

        ResponseEntity<FrictionChart[]> responseEntity =
                restTemplate.getForEntity(builder.toUriString(), FrictionChart[].class);

        FrictionChart[] chartArray = responseEntity.getBody();
        return Arrays.asList(chartArray);
    }
}
