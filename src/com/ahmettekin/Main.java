package com.ahmettekin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.tumSarkicilariGetir();

        stage.setTitle("Müzik Database");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();

        if (!DataSource.getInstance().veritabaniniAc()) {
            System.out.println("Veritabanına Bağlanılamadı !!!!");
            Platform.exit();
        }

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().veriTabaniniKapat();
    }
}
