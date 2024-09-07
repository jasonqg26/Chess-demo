package Scenas;

import Features.DragAndDrop;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class GameScene {
    DragAndDrop dragAndDrop = new DragAndDrop();
    Scene scene;
    private BorderPane borderPane;

    public GameScene() {
        this.borderPane = BodyScene();
        this.scene = new Scene(borderPane,600,600);
    }

    public Scene getScene() {
        return this.scene;
    }



    /**
     * Crea y configura un BorderPane que contiene un GridPane dentro de un VBox y HBox.
     *
     * @return Un BorderPane configurado que contiene el tablero en su centro.
     */
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


    /**
     * Este método recorre las filas y columnas del GridPane para crear cada celda del tablero,
     * asignando a cada celda un StackPane. Luego, se configuran el color de las celdas, se
     * colocan las piezas iniciales en su posición adecuada y se asignan los eventos de arrastre
     * y liberación de piezas mediante los métodos correspondientes.
     *
     * @param gridPane El GridPane en el cual se añadirá el tablero con las celdas y piezas.
     */
    public void addBoard(GridPane gridPane){

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                StackPane stackPane = new StackPane();
                gridPane.add(stackPane, col, row);
                // Método encargado de la lógica para los diferentes colores de cada celda del tablero
                configureBoardColor(stackPane, row, col);
                // Método encargado de colocar cada pieza en su lugar inicial
                placeInitialPieces(stackPane, row, col);
                // Método que maneja los eventos del mouse
                setDragAndDrop(stackPane, gridPane);
            }

        }

    }


    /**
     * Coloca una pieza en su posición inicial en el tablero.
     *
     * @param stackPane El StackPane en el cual se añadirá la pieza.
     * @param row La fila donde se colocará la pieza.
     * @param col La columna donde se colocará la pieza.
     * @param color El color de la pieza (puede ser "blanco" o "negro").
     */
    public void addPiece(StackPane stackPane, int row, int col,String color) {
        if (col == 0 || col == 7) {
            switch (row) {
                case 0:
                case 7:
                    createPiece(stackPane, "torre", color);
                    break;
                case 1:
                case 6:
                    createPiece(stackPane, "caballero", color);
                    break;
                case 2:
                case 5:
                    createPiece(stackPane, "obispo", color);
                    break;
                case 4:
                    createPiece(stackPane, "reina", color);
                    break;
                case 3:
                    createPiece(stackPane, "rey", color);
                    break;
            }
        } else {
            createPiece(stackPane, "peon", color);
        }
    }

    /**
     * Este método ubica las piezas negras en las primeras dos columnas
     * y las piezas blancas en las dos últimas columnas de acuerdo a las reglas del ajedrez.
     *
     * @param stackPane El contenedor donde se añadirá la pieza.
     * @param row La fila en la que se colocará la pieza.
     * @param col La columna en la que se colocará la pieza.
     */
    private void placeInitialPieces(StackPane stackPane, int row, int col) {
        if (col == 0 || col == 1) {
            addPiece(stackPane, row, col,"Negras");
        }
        if (col == 7 || col == 6) {
            addPiece(stackPane, row, col,"Blancas");
        }
    }


    /**
     * Crea una imagen de una pieza de ajedrez y la añade al StackPane.
     *
     * @param stackPane El StackPane donde se colocará la pieza.
     * @param pieceType El tipo de pieza ("torre", "caballero", "obispo", "reina", "rey", "peon").
     * @param color El color de la pieza ("Blancas" o "Negras").
     */
    private void createPiece(StackPane stackPane, String pieceType, String color) {
        String filePath = "file:src/main/resources/Piezas " + color + "/" + pieceType + ".png";
        Image image = new Image(filePath);
        ImageView imageView = new ImageView(image);
        imageView.getProperties().put("is", "pieza");
        stackPane.getChildren().add(imageView);
    }


    /**
     * Configura los eventos de arrastrar y soltar para las piezas de ajedrez en el tablero.
     *
     * @param stackPane El StackPane que contiene la pieza.
     * @param gridPane El GridPane que representa el tablero.
     */
    private void setDragAndDrop(StackPane stackPane, GridPane gridPane) {
        stackPane.setOnMouseDragged(event -> handleDrag(stackPane, event));
        gridPane.setOnMouseReleased(event -> handleMouseRelease(gridPane, event));
    }


    /**
     * Este método toma la imagen de la pieza que se arrastra y la elimina del StackPane,
     * actualizando el cursor con la imagen de la pieza para que se pueda mover libremente por el tablero.
     *
     * @param stackPane El StackPane que contiene la pieza.
     * @param event El evento de arrastrar el ratón.
     */
    private void handleDrag(StackPane stackPane, MouseEvent event) {
        if (!stackPane.getChildren().isEmpty()) {
            ImageView imageView = (ImageView) stackPane.getChildren().get(stackPane.getChildren().size() - 1);

            if (imageView.getProperties().get("is").equals("pieza")) {
                dragAndDrop.setImageView((ImageView) stackPane.getChildren().get(stackPane.getChildren().size() - 1));
                stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                Cursor cursor = new ImageCursor(dragAndDrop.getImageView().getImage());
                scene.setCursor(cursor);
            }
        }
        event.consume();
    }


    /**
     * Este método coloca la pieza en el StackPane correspondiente después de soltarla,
     * determinando la nueva posición a partir de las coordenadas del evento.
     *
     * @param gridPane El GridPane que contiene el tablero.
     * @param event El evento de soltar el ratón.
     */
    private void handleMouseRelease(GridPane gridPane, MouseEvent event) {
        ImageView targetStackPane = (ImageView) event.getPickResult().getIntersectedNode();

        if (targetStackPane != null) {
            int roW = (int) targetStackPane.getProperties().get("row");
            int coL = (int) targetStackPane.getProperties().get("col");

            if (coL == 0) {
                StackPane stackPanE = (StackPane) gridPane.getChildren().get(roW);
                placePiece(stackPanE);
            } else {
                int index = (7 * coL) + roW + coL;
                StackPane stackPanE = (StackPane) gridPane.getChildren().get(index);
                placePiece(stackPanE);
            }
        }
    }


    /**
     * Este método añade la pieza que ha sido arrastrada a un nuevo StackPane y reinicia el cursor.
     *
     * @param stackPane El StackPane donde se añadirá la pieza.
     */
    private void placePiece(StackPane stackPane) {
        if (dragAndDrop.getImageView() != null) {
            stackPane.getChildren().add(dragAndDrop.getImageView());
            dragAndDrop.getImageView().setTranslateX(0);
            dragAndDrop.getImageView().setTranslateY(0);
            dragAndDrop.setImageView(null);
            scene.setCursor(Cursor.DEFAULT);
        }
    }


    /**
     * Crea una imagen de un cuadrado del tablero con el color especificado (Blanco o Gris) y
     * la devuelve como un ImageView.
     *
     * @param row La fila del cuadrado en el tablero.
     * @param col La columna del cuadrado en el tablero.
     * @param color El color del cuadrado del tablero (debe ser "Blanco" o "Gris").
     * @return Un ImageView que representa un cuadrado del tablero con el color especificado.
     */
    public ImageView addBoard(int row, int col,String color){
        Image image = new Image("file:src/main/resources/Tablero/Tablero-" + color + ".png");
        ImageView imageView = new ImageView(image);
        imageView.getProperties().put("is", "tablero");
        imageView.getProperties().put("row", row);
        imageView.getProperties().put("col", col);
        return imageView;

    }


    /**
     * Configura el color del cuadrado del tablero en función de sus coordenadas utilizando imágenes
     * para colores blanco y gris.
     *
     * @param stackPane El StackPane que representa el cuadrado del tablero.
     * @param row La fila del cuadrado en el tablero.
     * @param col La columna del cuadrado en el tablero.
     */
    private void configureBoardColor(StackPane stackPane, int row, int col) {
        if (row % 2 == 0) {
            if (col % 2 == 0) {
                stackPane.getChildren().addAll(addBoard(row, col,"Gris"));
            } else {
                stackPane.getChildren().addAll(addBoard(row, col,"Blanco"));
            }
        } else {
            if (col % 2 == 0) {
                stackPane.getChildren().addAll(addBoard(row, col,"Blanco"));
            } else {
                stackPane.getChildren().addAll(addBoard(row, col,"Gris"));
            }
        }
    }


}
