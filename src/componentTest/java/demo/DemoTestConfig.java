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
package demo;

import demo.client.ChartClient;
import demo.client.EventClient;
import demo.core.chart.ChartGeneratorSpec;
import demo.core.ifm.ifmsource.IfmSource;
import demo.filesource.PackageIfmSource;
import groovyx.net.http.RESTClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.URISyntaxException;

@Configuration
public class DemoTestConfig {

	@Value("${test-server.base_url:http://localhost}")
	private String serverBaseUrl;
	@Value("${test-server.base_url:http://localhost}:${server.port}")
	private String hostUri;

	@Bean
	public EventClient eventClient() {
		return new EventClient(hostUri);
	}

	@Bean
	public ChartClient chartClient() {
		return new ChartClient(hostUri);
	}

	@Bean
	@Primary
	public RESTClient restClient() throws URISyntaxException {
		return  new RESTClient(hostUri);
	}

	@Bean
	public RESTClient managementRestClient(@Value("${management.port}") String managementPort) throws URISyntaxException {
		return new RESTClient(serverBaseUrl + ":" + managementPort);
	}

	@Bean
    public IfmSource ifmSource() {
        return new PackageIfmSource(ChartGeneratorSpec.class);
    }

}
