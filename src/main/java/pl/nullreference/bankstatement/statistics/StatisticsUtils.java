package pl.nullreference.bankstatement.statistics;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class StatisticsUtils {
    public static void configureTooltip(Node associatedNode, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setShowDelay(Duration.seconds(0.5));
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.setStyle("-fx-font-size: 15");
        Tooltip.install(associatedNode, tooltip);
    }
}
