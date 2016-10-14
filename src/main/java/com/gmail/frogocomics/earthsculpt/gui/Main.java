package com.gmail.frogocomics.earthsculpt.gui;

import com.gmail.frogocomics.earthsculpt.core.Constants;
import com.gmail.frogocomics.earthsculpt.core.devices.PerlinDevice;
import com.gmail.frogocomics.earthsculpt.utils.ScreenUtils;

import java.awt.*;

import javax.swing.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class Main extends Application {

    private Stage stage;
    private Scene scene;
    private BorderPane root;
    private VBox left;
    private VBox right;
    private MenuBar menuBar;
    private ToolBar toolBar;
    private SwingNode canvas;
    private JPanel canvasContent;
    private SubScene preview;
    private ScrollPane parameters;
    private VBox individualParameters;

    private Label previewLabel;
    private Label parameterLabel;

    private static Main instance;

    public Main(){
        super();
        synchronized(Main.class){
            if(instance != null) throw new UnsupportedOperationException(
                    getClass()+" is singleton but constructor called more than once");
            instance = this;
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = new BorderPane();

        previewLabel = new Label("Preview");
        previewLabel.setAlignment(Pos.CENTER);
        parameterLabel = new Label("Parameter(s)");
        parameterLabel.setAlignment(Pos.CENTER);

        menuBar = new MenuBar();
        menuBar.setMaxWidth(ScreenUtils.getWidth());
        menuBar.setPrefWidth(ScreenUtils.getWidth());
        toolBar = new ToolBar();
        toolBar.setMaxWidth(ScreenUtils.getWidth());
        toolBar.setPrefWidth(ScreenUtils.getWidth());
        toolBar.setMaxHeight(ScreenUtils.getHeight() / 25);
        toolBar.setPrefHeight(ScreenUtils.getHeight() / 25);

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        fileMenu.getItems().addAll(exitItem);
        Menu deviceMenu = new Menu("Devices");
        menuBar.getMenus().addAll(fileMenu, deviceMenu);

        canvas = new SwingNode();
        canvasContent = new JPanel();
        canvasContent.setBackground(java.awt.Color.white);
        canvas.setContent(canvasContent);
        canvasContent.setPreferredSize(new Dimension(ScreenUtils.getWidth() / 3, (int) (ScreenUtils.getHeight() - (toolBar.getHeight() + menuBar.getHeight()))));
        canvasContent.setMaximumSize(new Dimension(ScreenUtils.getWidth() / 5 * 3, (int) (ScreenUtils.getHeight() - (toolBar.getHeight() + menuBar.getHeight()))));

        Group subSceneRoot = new Group();
        preview = new SubScene(subSceneRoot, ScreenUtils.getWidth() / 5, ScreenUtils.getWidth() / 5);

        parameters = new ScrollPane();
        parameters.setMaxWidth(ScreenUtils.getWidth() / 5);
        parameters.setPrefWidth(ScreenUtils.getWidth() / 5);
        parameters.setMaxHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        parameters.setPrefHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        individualParameters = new VBox();
        individualParameters.setMaxWidth(ScreenUtils.getWidth() / 5);
        individualParameters.setPrefWidth(ScreenUtils.getWidth() / 5);
        individualParameters.setStyle("-fx-background-color: #d5d5d5");
        parameters.setContent(individualParameters);

        left = new VBox();
        left.getChildren().addAll(previewLabel, preview);
        left.setMaxWidth(ScreenUtils.getWidth() / 5);
        left.setPrefWidth(ScreenUtils.getWidth() / 5);
        left.setMaxHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        left.setPrefHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        left.setStyle("-fx-background-color: #d5d5d5");

        right = new VBox();
        right.getChildren().addAll(parameterLabel, parameters);
        right.setMaxWidth(ScreenUtils.getWidth() / 5);
        right.setPrefWidth(ScreenUtils.getWidth() / 5);
        right.setMaxHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        right.setPrefHeight(ScreenUtils.getHeight() - (menuBar.getHeight() + toolBar.getHeight()));
        right.setStyle("-fx-background-color: #d5d5d5");

        root.setTop(new VBox(menuBar, toolBar));
        root.setLeft(left);
        root.setCenter(canvas);
        root.setRight(right);

        scene = new Scene(root, ScreenUtils.getWidth(), ScreenUtils.getHeight(), Color.web("d6d6d6"));
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                menuBar.setPrefWidth(newValue.doubleValue());
                toolBar.setPrefWidth(newValue.doubleValue());
                left.setPrefWidth(newValue.doubleValue() / 5);
                right.setPrefWidth(newValue.doubleValue() / 5);
                preview.setWidth(newValue.doubleValue() / 5);
                parameters.setPrefWidth(newValue.doubleValue() / 5);
                individualParameters.setPrefWidth(newValue.doubleValue() / 5);
                canvasContent.setPreferredSize(new Dimension((int) (newValue.doubleValue() / 3), canvasContent.getHeight()));
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                preview.setHeight(newValue.doubleValue() / 5);
                left.setPrefHeight(newValue.doubleValue() - (menuBar.getHeight() + toolBar.getHeight()));
                right.setPrefHeight(newValue.doubleValue() - (menuBar.getHeight() + toolBar.getHeight()));
                parameters.setPrefHeight(newValue.doubleValue() - (menuBar.getHeight() + toolBar.getHeight()));
                canvasContent.setPreferredSize(new Dimension(canvasContent.getWidth(), (int) (ScreenUtils.getHeight() - (toolBar.getHeight() + menuBar.getHeight()))));
            }
        });

        scene.getStylesheets().addAll("https://fonts.googleapis.com/css?family=Open+Sans:300,400,600", "https://fonts.googleapis.com/css?family=Source+Code+Pro:400,600");
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("EarthSculpt - " + Constants.VERSION);
        stage.setMaxWidth(ScreenUtils.getWidth());
        stage.setMaxHeight(ScreenUtils.getHeight());
        stage.setMinWidth(ScreenUtils.getWidth() / 3);
        stage.setMinHeight(ScreenUtils.getHeight() / 3);
        stage.show();

        PerlinDevice device = new PerlinDevice();
        ParameterGui.displayParameters(device);

        stage.setMaximized(true);
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public VBox getParameterDisplay() {
        return individualParameters;
    }

    public Label getParameterLabel() {
        return parameterLabel;
    }

    @Deprecated
    public SwingNode getWorkflow() {
        return canvas;
    }
}
