package com.ahmettekin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    private TableView tablo;

    @FXML
    private ProgressBar progressBar;

    public void tumSarkicilariGetir() {

        Task<ObservableList<Sarkici>> task = new Task<>() {
            @Override
            protected ObservableList<Sarkici> call() {
                System.out.println("Thread: " + Thread.currentThread().getName());
                return FXCollections.observableArrayList(DataSource.getInstance().tumSarkicilariGetir(DataSource.ARTAN_SIRA));
            }
        };

        tablo.itemsProperty().bind(task.valueProperty());


        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);

        task.setOnSucceeded(workerStateEvent -> {

                progressBar.setVisible(false);
                System.out.println("succeeded thread: " + Thread.currentThread().getName());

        });
        task.setOnFailed(workerStateEvent -> progressBar.setVisible(false));

        new Thread(task).start();

    }


    public void albumleriGetir() {

        Sarkici sarkici = (Sarkici) tablo.getSelectionModel().getSelectedItem();
        Task<ObservableList<Album>> task2 = new Task<>() {
            @Override
            protected ObservableList<Album> call() {
                return FXCollections.observableArrayList(DataSource.getInstance().tumAlbumleriGetir(sarkici.getSarkiciID()));
            }
        };

        tablo.itemsProperty().bind(task2.valueProperty());

        new Thread(task2).start();
    }

    public void guncelle(){

        Album album = (Album) tablo.getSelectionModel().getSelectedItem();
        Task<Boolean> task3 = new Task<>() {
            @Override
            protected Boolean call() {
                return DataSource.getInstance().veriyiGuncelle(album.getAlbumID(), "Güncel Veri İsmi");
            }
        };

        task3.setOnSucceeded(workerStateEvent -> {

            album.setIsim("Güncel Veri İsmi");
            tablo.refresh();
        });
        new Thread(task3).start();

    }

}



