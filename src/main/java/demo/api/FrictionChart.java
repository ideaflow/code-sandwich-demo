package demo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrictionChart {

        String title;
        List<String> ticks;
        List<Double> conflictSeries;
        List<Double> learningSeries;
        List<Double> reworkSeries;
}
