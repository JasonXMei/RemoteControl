package com.jason.use;

import com.jason.use.controller.LoginController;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class JavafxApplication extends Application {

	private static Stage mainStage;
	private static String title = "智慧联盟使用端";
	private static double width = 1000;
	private static double height = 618;

    private static BorderPane root;

    private static JFXDialogLayout dialogLayout = new JFXDialogLayout();

    private static JFXDialog jfxDialog;
    private static JFXAlert<Void> jfxAlert;

	@Override
	public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("/view/wrapper.fxml"));
		mainStage = primaryStage;
		mainStage.setResizable(false);

		mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo.jpg")));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Parent root = loader.load();
		mainStage.setTitle(title);
		LoginController controller = loader.getController();
		controller.setApp(this, mainStage);
		Scene scene = new Scene(root, width, height);
		//scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
		mainStage.setScene(scene);
		mainStage.show();

        mainStage.setOnCloseRequest((event) -> {
            System.out.print("监听到窗口关闭");
        });
	}

    public static void showAlert(String headMsg, String bodyMsg, String methodName, Class<?> cls){
        jfxAlert = new JFXAlert<>(mainStage);

        Text header = new Text(headMsg);
        header.setFont(new Font("System", 18));
        header.setFill(Paint.valueOf("#495057"));

        Text body = new Text(bodyMsg);
        dialogLayout.setHeading(header);
        dialogLayout.setBody(body);

        JFXButton closeButton = new JFXButton("确定");

        closeButton.setStyle("-fx-background-color: #039BE5;-fx-font-size: 18px");
        closeButton.setTextFill(Paint.valueOf("#FFFFFF"));
        closeButton.setRipplerFill(Paint.valueOf("#FFFFFF"));
        closeButton.setButtonType(JFXButton.ButtonType.RAISED);

        closeButton.setOnAction(event -> {
            jfxAlert.hideWithAnimation();
            try {
                cls.getMethod(methodName).invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dialogLayout.setActions(closeButton);
        jfxAlert.setContent(dialogLayout);

        jfxAlert.setOverlayClose(true);
        jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        jfxAlert.initModality(Modality.APPLICATION_MODAL);
        jfxAlert.showAndWait();
    }

    public static void showConfirmed(String headerString, String messageString, StackPane stackPane, String methodName, Class<?> cls) {
        Text header = new Text(headerString);
        header.setFont(new Font("System", 18));
        header.setFill(Paint.valueOf("#495057"));

        Text body = new Text(messageString);

        JFXButton cancelButton = new JFXButton("取消");
        JFXButton confirmButton = new JFXButton("确定");

        cancelButton.setStyle("-fx-background-color: #f0ad4e;-fx-font-size: 18px");
        cancelButton.setTextFill(Paint.valueOf("#FFFFFF"));
        cancelButton.setRipplerFill(Paint.valueOf("#FFFFFF"));
        cancelButton.setButtonType(JFXButton.ButtonType.RAISED);

        confirmButton.setStyle("-fx-background-color: #039BE5;-fx-font-size: 18px");
        confirmButton.setTextFill(Paint.valueOf("#FFFFFF"));
        confirmButton.setRipplerFill(Paint.valueOf("#FFFFFF"));
        confirmButton.setButtonType(JFXButton.ButtonType.RAISED);

        dialogLayout.setHeading(header);
        dialogLayout.setBody(body);
        dialogLayout.setActions(cancelButton, confirmButton);

        jfxDialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        jfxDialog.setOverlayClose(false);

        cancelButton.setOnAction(e -> {
            jfxDialog.close();
            stackPane.setVisible(false);
        });

        confirmButton.setOnAction(e -> {
            jfxDialog.close();
            stackPane.setVisible(false);
            try {
                cls.getMethod(methodName).invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        stackPane.setVisible(true);
        jfxDialog.show();
    }

    public static void switchView(String view){
        try {
            root.setCenter(FXMLLoader.load(JavafxApplication.class.getResource(view)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, width, height);
        mainStage.setScene(scene);
        mainStage.show();
    }

	public void closeWindow() {
		mainStage.close();
	}

	public void hideWindow(){ mainStage.hide();}

	public void showWindow(){ mainStage.show();}

	public static void main(String[] args) {
		launch(args);
	}
}
