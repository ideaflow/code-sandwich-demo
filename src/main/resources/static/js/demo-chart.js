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
function renderCharts() {
    $.ajax({
        type: 'GET',
        url: '/chart/1',
        success: drawBarChart,
        error: handleError
    });
}

function handleError(e) {
    alert(e.status + " : " +e.statusText)
}

function drawBarChart(chart) {
    $.jqplot.config.enablePlugins = true;
    var data = [chart.conflictSeries, chart.learningSeries, chart.reworkSeries];
    var ticks = chart.ticks;

    var conflict_color = "#ff0078";
    var learning_color = "#520ce8";
    var rework_color = "#ffcb01";

    alert("colors!");
    var plot1 = $.jqplot('chartdiv', data, {
        title: chart.title,
        animate: true,
        seriesColors:[conflict_color, learning_color, rework_color],
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            pointLabels: { show: false }
        },
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks
            }
        },
        highlighter: { show: false }
    });

    //$.jqplot('chartdiv',  [[[1, 2],[3,5.12],[5,13.1],[7,33.6],[9,85.9],[11,219.9]]]);
}

function drawStackedBarChart(chart) {

}