package com.jason.use;

import com.jason.use.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JavafxApplication extends Application {

	private Stage mainStage;
	private String title = "智慧联盟使用端";
	private double width = 1000;
	private double height = 618;

    private static BorderPane root;

	@Override
	public void start(Stage primaryStage) throws Exception{
		mainStage = primaryStage;
		mainStage.setResizable(false);

		//设置窗口的图标.
		mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo.jpg")));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Parent root = loader.load();
		mainStage.setTitle(title);
		LoginController controller = loader.getController();
		controller.setApp(this, mainStage);
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
		mainStage.setScene(scene);
		mainStage.show();

        mainStage.setOnCloseRequest((event) -> {
            System.out.print("监听到窗口关闭");
        });
	}

	public void gotoReaderUi(String id) {
		try {
			root = FXMLLoader.load(getClass().getResource("/view/wrapper.fxml"));
			root.setCenter(FXMLLoader.load(getClass().getResource("/view/list.fxml")));
			mainStage.setTitle(title);
            //UserController controller = loader.getController();
			/*ReaderUi controller = loader.getController();
			controller.setApp(this);
			controller.setUserInfo(id);*/
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(getClass().getResource("/css/list.css").toExternalForm());
			mainStage.setScene(scene);
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
