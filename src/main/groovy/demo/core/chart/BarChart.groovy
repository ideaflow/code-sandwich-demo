package demo.core.chart


class BarChart {
    String title
    List<StackedBar> bars = []

    void addStackedBar(String label, Map<String, Double> valueMap) {
        StackedBar bar = new StackedBar(label)
        valueMap.each { groupKey, value ->
            bar.addSection(groupKey, value)
        }
        bars.add(bar)
    }

    static interface Bar {
        String getLabel()
        Double getTotal()
    }

    static class StackedBar implements Bar {
        String label
        Map<String, Double> barSections = [:]

        StackedBar(String label) {
            this.label = label
        }

        void addSection(String barType, Double value) {
            barSections.put(barType, value)
        }

        Double getTotal() {
            Double total = 0;
            barSections.values().each { Double value ->
                total += value
            }
            return total
        }

    }
}
