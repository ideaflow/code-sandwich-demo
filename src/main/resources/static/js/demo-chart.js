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
        url: '/chart/all', //change this to a filter parameter
        success: drawAllCharts,
        error: handleError
    });
}

function handleError(e) {
    alert(e.status + " : " +e.statusText)
}

var conflict_color = "#ff0078";
var learning_color = "#520ce8";
var rework_color = "#ffcb01";


function drawAllCharts(charts) {
    alert("Rendering ZZ div charts!");
    drawBarChart('chartdiv1', charts[0]);
    drawLineChart('chartdiv2', "Avg Troubleshooting Time (Minutes)", charts[1].conflictSeries, conflict_color);
    drawLineChart('chartdiv3', "Avg Learning Time (Minutes)", charts[1].learningSeries, learning_color);
    drawLineChart('chartdiv4', "Avg Rework Time (Minutes)", charts[1].reworkSeries, rework_color);
}


function drawBarChart(chartDiv, chart) {
    $.jqplot.config.enablePlugins = true;
    var data = [chart.conflictSeries, chart.learningSeries, chart.reworkSeries];
    var ticks = chart.ticks;

    var plot1 = $.jqplot(chartDiv, data, {
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

function drawLineChart(chartDiv, title, data, color) {
    alert('drawing');

    var plot1 = $.jqplot (chartDiv, [data], {
        title: title,
        seriesDefaults: {
                    rendererOptions: {
                        smooth: true
                    },
                    pointLabels: { show:false }
        },
        axes: {
            xaxis: {
                min: 0,
                max:data.length,
                tickOptions: {
                    showLabel: false,
                    showGridline: false,
                    showMark: false
                }
            }
        },
        series:[{showMarker:false}],
        seriesColors:[color]
    });
    //var plot1 = $.jqplot(chartDiv, data, {
    //    seriesDefaults: {
    //        rendererOptions: {
    //            smooth: true
    //        }
    //    },
    //    seriesColors:[color]
    //});
    //alert('done!');
}