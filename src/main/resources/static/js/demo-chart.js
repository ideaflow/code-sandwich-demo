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

function renderChart() {
    $.ajax({
        type: 'GET',
        url: '/chart/frequency',
        success: drawBarChart,
        error: handleError
    });
}



function drawBarChart(chartData) {
    //TODO Wire up the chart

    $.jqplot.config.enablePlugins = true;
    var color = '#ff0078';

    var ticks = ['cat1', 'cat2', 'cat3', 'cat4', 'cat5'];
    var data = [[2, 14, 7, 10, 3]];

    $.jqplot('chartdiv', data, {
        title: "My Chart",
        seriesColors:[color],
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            pointLabels: { show: false }
        },
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks
            }
        }
    });
}

function handleError(e) {
    alert(e.status + " : " +e.statusText)
}
