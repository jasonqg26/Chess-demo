package Scenas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameScene {

    Scene scene;

    public GameScene() {
        BorderPane borderPane = BodyScene();
        this.scene = new Scene(borderPane,600,600);
    }

    public Scene getScene() {
        return this.scene;
    }

    public BorderPane BodyScene() {
        BorderPane borderPane = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(0.5);
        gridPane.setVgap(0.5);
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        for (int i = 0; i < 8; i++) {
            gridPane.getRowConstraints().add(new RowConstraints());
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }
        addBoard(gridPane);

        HBox hBox = new HBox(gridPane);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);


        borderPane.setCenter(vBox);
        return borderPane;
    }

    public void addBoard(GridPane gridPane){

        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane stackPane = new StackPane();
                gridPane.add(stackPane, col, row);

                if (row%2 == 0) {

                    if (col%2 == 0) {
                        Image image = new Image("file:src/main/resources/Tablero/Tablero-Blanco.png");
                        ImageView imageView = new ImageView(image);
                        stackPane.getChildren().addAll(imageView);
                    }else {
                        Image image = new Image("file:src/main/resources/Tablero/Tablero-Gris.png");
                        ImageView imageView = new ImageView(image);
                        stackPane.getChildren().addAll(imageView);
                    }
                }
                else {
                    if (col%2 == 0) {
                        Image image = new Image("file:src/main/resources/Tablero/Tablero-Gris.png");
                        ImageView imageView = new ImageView(image);
                        stackPane.getChildren().addAll(imageView);
                    }
                    else {
                        Image image = new Image("file:src/main/resources/Tablero/Tablero-Blanco.png");
                        ImageView imageView = new ImageView(image);
                        stackPane.getChildren().addAll(imageView);
                    }
                }

                if (row == 0 || row == 1) {
                    addPiecesBlack(stackPane,row,col);
                }
                if (row == 7 || row == 6) {
                    addPiecesWait(stackPane,row,col);
                }



            }
        }

    }

    public void addPiecesBlack(StackPane stackPane, int row, int col){

        if (row == 0){
            switch (col){
                case 0:
                case 7:
                    Image imageTorre = new Image("file:src/main/resources/Piezas Negras/torre.png");
                    ImageView imageViewTorre = new ImageView(imageTorre);
                    stackPane.getChildren().addAll(imageViewTorre);
                    break;
                case 1:
                case 6:
                    Image imageCaballo = new Image("file:src/main/resources/Piezas Negras/caballero.png");
                    ImageView imageViewCaballo = new ImageView(imageCaballo);
                    stackPane.getChildren().addAll(imageViewCaballo);
                    break;
                case 2:
                case 5:
                    Image imageAlfil  = new Image("file:src/main/resources/Piezas Negras/obispo.png");
                    ImageView imageViewAlfil = new ImageView(imageAlfil );
                    stackPane.getChildren().addAll(imageViewAlfil);
                    break;
                case 3:
                    Image imageReyna = new Image("file:src/main/resources/Piezas Negras/reina.png");
                    ImageView imageViewReyna = new ImageView(imageReyna);
                    stackPane.getChildren().addAll(imageViewReyna);
                    break;
                case 4:
                    Image imageRey = new Image("file:src/main/resources/Piezas Negras/rey.png");
                    ImageView imageViewRey = new ImageView(imageRey);
                    stackPane.getChildren().addAll(imageViewRey);
                    break;

            }
        }
        else {
            Image imagePeon = new Image("file:src/main/resources/Piezas Negras/peon.png");
            ImageView imageViewPeon = new ImageView(imagePeon);
            stackPane.getChildren().addAll(imageViewPeon);
        }


    }

    public void addPiecesWait(StackPane stackPane, int row, int col){

        if (row == 7){
            switch (col){
                case 0:
                case 7:
                    Image imageTorre = new Image("file:src/main/resources/Piezas Blancas/torre.png");
                    ImageView imageViewTorre = new ImageView(imageTorre);
                    stackPane.getChildren().addAll(imageViewTorre);
                    break;
                case 1:
                case 6:
                    Image imageCaballo = new Image("file:src/main/resources/Piezas Blancas/caballero.png");
                    ImageView imageViewCaballo = new ImageView(imageCaballo);
                    stackPane.getChildren().addAll(imageViewCaballo);
                    break;
                case 2:
                case 5:
                    Image imageAlfil  = new Image("file:src/main/resources/Piezas Blancas/obispo.png");
                    ImageView imageViewAlfil = new ImageView(imageAlfil );
                    stackPane.getChildren().addAll(imageViewAlfil);
                    break;
                case 3:
                    Image imageReyna = new Image("file:src/main/resources/Piezas Blancas/reina.png");
                    ImageView imageViewReyna = new ImageView(imageReyna);
                    stackPane.getChildren().addAll(imageViewReyna);
                    break;
                case 4:
                    Image imageRey = new Image("file:src/main/resources/Piezas Blancas/rey.png");
                    ImageView imageViewRey = new ImageView(imageRey);
                    stackPane.getChildren().addAll(imageViewRey);
                    break;

            }
        }
        else {
            Image imagePeon = new Image("file:src/main/resources/Piezas Blancas/peon.png");
            ImageView imageViewPeon = new ImageView(imagePeon);
            stackPane.getChildren().addAll(imageViewPeon);
        }


    }
}
