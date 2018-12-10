package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main window the user interacts with.
 */
public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Fxmls/MainWindowFXML.fxml"));

        Scene scene = new Scene(root);

        //The following code fill keep the canvas layer at the same size as the the parent stackPane
        StackPane stackPaneRoot = (StackPane) scene.lookup("#stackPaneGameArea");

        Canvas boardLayer = (Canvas) scene.lookup("#boardLayer");
        boardLayer.widthProperty().bind(stackPaneRoot.widthProperty());
        boardLayer.heightProperty().bind(stackPaneRoot.heightProperty());

        Canvas helpLayer = (Canvas) scene.lookup("#helpLayer");
        helpLayer.widthProperty().bind(stackPaneRoot.widthProperty());
        helpLayer.heightProperty().bind(stackPaneRoot.heightProperty());

        Canvas piecesLayer = (Canvas) scene.lookup("#piecesLayer");
        piecesLayer.widthProperty().bind(stackPaneRoot.widthProperty());
        piecesLayer.heightProperty().bind(stackPaneRoot.heightProperty());

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
