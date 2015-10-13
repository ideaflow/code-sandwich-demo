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
    drawBarChart('frequencyConflict', charts[0].conflictSeriesLabel, charts[0].ticks, charts[0].conflictSeries, conflict_color);
    drawBarChart('frequencyLearning', charts[0].learningSeriesLabel, charts[0].ticks, charts[0].learningSeries, learning_color);
    drawBarChart('frequencyRework', charts[0].reworkSeriesLabel, charts[0].ticks, charts[0].reworkSeries, rework_color);

    drawLineChart('durationConflict', charts[1].conflictSeriesLabel, charts[1].conflictSeries, conflict_color);
    drawLineChart('durationLearning', charts[1].learningSeriesLabel, charts[1].learningSeries, learning_color);
    drawLineChart('durationRework', charts[1].reworkSeriesLabel, charts[1].reworkSeries, rework_color);
}


function drawBarChart(chartDiv, title, ticks, series, color) {
    $.jqplot.config.enablePlugins = true;

    var plot1 = $.jqplot(chartDiv, [series], {
        title: title,
        animate:true,
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
        },
        highlighter: { show: false }
    });

}

function drawLineChart(chartDiv, title, data, color) {
    var plot1 = $.jqplot (chartDiv, [data], {
        title: title,
        animate: true,
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
            },
            yaxis: {
                min: 0
            }
        },
        series:[{showMarker:false}],
        seriesColors:[color]
    });
}