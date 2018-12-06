package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/MainWindowFXML.fxml"));

        Scene scene = new Scene(root);



        StackPane stackPaneRoot = (StackPane)root.getChildrenUnmodifiable().get(1); //TODO not a stable line of code
        System.out.println(stackPaneRoot);
        Canvas boardLayer = (Canvas) stackPaneRoot.getChildren().get(0);
        System.out.println(boardLayer);

        boardLayer.widthProperty().bind(stackPaneRoot.widthProperty());
        boardLayer.heightProperty().bind(stackPaneRoot.heightProperty());

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//Draw image(url)
