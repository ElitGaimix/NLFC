package fr.elitgaimix.nlfc.runnables;

import fr.elitgaimix.nlfc.NLRController;
import javafx.scene.paint.Color;

public class GUIRunnable implements Runnable {
    private NLRController controller;

    public GUIRunnable(NLRController controller) {
        this.controller = controller;
    }
    @Override
    public void run() {
        if (controller.rocket == null || !controller.rocket.transmitter.connected) {
            controller.connect.setText("Connect");
            controller.connect.setVisible(!controller.com.getItems().isEmpty());
            controller.comState.setText("Disconnected");
            controller.comState.setTextFill(Color.RED);
            controller.nlrState.setText("Disconnected");
            controller.nlrState.setTextFill(Color.RED);
            controller.nlrBorderPane.setStyle("-fx-border-color: red;");
        }else{
            controller.connect.setText("Disconnect");
            controller.connect.setVisible(true);
            controller.comState.setText("Connected");
            controller.comState.setTextFill(Color.GREEN);
            if (controller.rocket.connected) {
                controller.nlrState.setText("Connected");
                controller.nlrState.setTextFill(Color.GREEN);
                controller.nlrBorderPane.setStyle("-fx-border-color: green;");
            }else{
                controller.nlrState.setText("Disconnected");
                controller.nlrState.setTextFill(Color.RED);
                controller.nlrBorderPane.setStyle("-fx-border-color: red;");
            }
        }
    }
}
