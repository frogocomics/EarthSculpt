package com.gmail.frogocomics.earthsculpt.gui;

import com.gmail.frogocomics.earthsculpt.core.devices.Device;
import com.gmail.frogocomics.earthsculpt.core.parameters.BooleanParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.DoubleParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.IntegerParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.ListParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.LongParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.utils.ScreenUtils;
import com.gmail.frogocomics.earthsculpt.utils.annotations.Important;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 *
 *
 * @since 0.0.2
 * @author Jeff Chen
 */
public class ParameterGui {

    private ParameterGui() {
    }

    @Important
    @SuppressWarnings("unchecked")
    public static void displayParameters(Device device) {

        final List<Parameter> parameters = device.getParameters();

        ListIterator<Parameter> iterator = parameters.listIterator();
        while(iterator.hasNext()) {
            final int parameterIndex = iterator.nextIndex();
            final Parameter parameter = parameters.get(parameterIndex);
            iterator.next();

            HBox parameterBox = new HBox();
            parameterBox.setMaxHeight(ScreenUtils.getWidth() / 5);
            Label parameterName = new Label(parameter.getName());
            //parameterBox.getChildren().add(parameterName);

            if(parameter instanceof BooleanParameter) {
                final CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        parameter.setValue(checkBox.isSelected());
                        parameters.set(parameterIndex, parameter);
                    }
                });
                final TextField field = new TextField("");
                field.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                parameter.setValue(Boolean.parseBoolean(field.getText()));
                                parameters.set(parameterIndex, parameter);
                            } catch(NumberFormatException e) {
                                field.setText("Invalid");
                            }
                        }
                    }
                });
                parameterBox.getChildren().addAll(checkBox, field);
            }

            if(parameter instanceof DoubleParameter) {
                final Slider slider = new Slider();
                slider.setMin(0);
                slider.setMax(1);
                slider.setValue((Double) parameter.getValue());
                slider.showTickMarksProperty().setValue(true);
                slider.setMajorTickUnit(0.5);
                slider.setMinorTickCount(100);
                slider.setBlockIncrement(0.1);
                slider.snapToTicksProperty().set(false);
                slider.showTickLabelsProperty().set(true);

                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        parameter.setValue(newValue);
                        parameters.set(parameterIndex, parameter);
                    }
                });

                final TextField field = new TextField("");
                field.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                parameter.setValue(Double.parseDouble(field.getText()));
                                parameters.set(parameterIndex, parameter);
                            } catch(NumberFormatException e) {
                                field.setText("Invalid");
                            }
                        }
                    }
                });

                parameterBox.getChildren().addAll(slider, field);
            }

            if(parameter instanceof IntegerParameter) {
                final Slider slider = new Slider();
                slider.setMin(((IntegerParameter) parameter).getMin());
                slider.setMax(((IntegerParameter) parameter).getMax());
                slider.setValue((Integer) parameter.getValue());
                slider.showTickMarksProperty().setValue(true);
                slider.setMinorTickCount(((IntegerParameter) parameter).getMax());
                slider.snapToTicksProperty().set(true);
                slider.showTickLabelsProperty().set(true);

                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        parameter.setValue(newValue.intValue());
                        parameters.set(parameterIndex, parameter);
                    }
                });

                final TextField field = new TextField("");
                field.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                if(Integer.parseInt(field.getText()) >= slider.getMin() & Integer.parseInt(field.getText()) <= slider.getMax()) {
                                    parameter.setValue(Integer.parseInt(field.getText()));
                                    parameters.set(parameterIndex, parameter);
                                    slider.setValue(Integer.parseInt(field.getText()));
                                } else {
                                    field.setText("Invalid");
                                }
                            } catch(NumberFormatException e) {
                                field.setText("Invalid");
                            }
                        }
                    }
                });
                parameterBox.getChildren().addAll(slider, field);
            }

            if(parameter instanceof ListParameter) {
                final ChoiceBox<String> choice = new ChoiceBox<>();
                ObservableList<String> list = FXCollections.observableArrayList((ArrayList<String>) parameter.getValue());
                choice.setItems(list);
                choice.getSelectionModel().select(((ListParameter) parameter).getSelected());

                choice.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        ((ListParameter) parameter).select(newValue);
                        parameters.set(parameterIndex, parameter);
                    }
                });
                parameterBox.getChildren().add(choice);
            }

            if(parameter instanceof LongParameter) {
                final TextField field = new TextField("");
                field.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                parameter.setValue(Long.parseLong(field.getText()));
                                parameters.set(parameterIndex, parameter);
                            } catch(NumberFormatException e) {
                                field.setText("Invalid");
                            }
                        }
                    }
                });
                parameterBox.getChildren().add(field);
            }

            Main.getInstance().getParameterDisplay().getChildren().addAll(parameterName, parameterBox);
            if(parameters.size() > 1) {
                Main.getInstance().getParameterLabel().setText("Parameters of " + device.getName());
            } else {
                Main.getInstance().getParameterLabel().setText("Parameter of " + device.getName());
            }
        }
    }

    public static void erase() {
        Main.getInstance().getParameterDisplay().getChildren().clear();
        Main.getInstance().getParameterLabel().setText("Parameter(s)");
    }
}
