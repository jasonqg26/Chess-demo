package Scenas;

import Dominio.GameLogic;
import Dominio.Piece.RulesForPices;
import Features.DragAndDrop;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameScene {
    private DragAndDrop dragAndDrop = new DragAndDrop();
    private RulesForPices rulesForPices = new RulesForPices(dragAndDrop);
    private GameLogic gameLogic = new GameLogic(dragAndDrop, rulesForPices);
    private Scene scene;


    public GameScene() {
        BorderPane borderPane = BodyScene();
        this.scene = new Scene(borderPane,600,700);
        gameLogic.setScene(scene);
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
        GridPane gridPane = createGridPane();

        configureGridPane(gridPane);


        addBoard(gridPane);
        HBox hBox = new HBox(gridPane);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);


        borderPane.setCenter(vBox);
        return borderPane;
    }

    /**
     * Crea un GridPane con la configuración inicial.
     *
     * @return El GridPane configurado.
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0.5);
        gridPane.setVgap(0.5);
        gridPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        return gridPane;
    }
    /**
     * Configura el GridPane creando las celdas, añadiendo colores, piezas iniciales y eventos.
     *
     * @param gridPane El GridPane a configurar.
     */
    private void configureGridPane(GridPane gridPane) {
        for (int i = 0; i < 8; i++) {
            gridPane.getRowConstraints().add(new RowConstraints());
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }

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
                StackPane stackPane = createStackPane(row, col);
                gridPane.add(stackPane, col, row);


                // Método encargado de la lógica para los diferentes colores de cada celda del tablero
                configureBoardColor(stackPane, row, col);
                // Método encargado de colocar cada pieza en su lugar inicial
                placeInitialPieces(stackPane, row, col);
                // Método que maneja los eventos del mouse
                gameLogic.setDragAndDrop(stackPane, gridPane);
            }

        }

    }

    /**
     * Crea un GridPane con la configuración inicial.
     *
     * @return El GridPane configurado.
     */
    private StackPane createStackPane(int row, int col) {
        StackPane stackPane = new StackPane();
        stackPane.getProperties().put("row", row);
        stackPane.getProperties().put("col", col);
        stackPane.setOnMouseEntered(mouseEvent -> {gameLogic.setMouseOver(stackPane);});
        return stackPane;

    }


    /**
     * Coloca una pieza en su posición inicial en el tablero.
     *
     * @param stackPane El StackPane en el cual se añadirá la pieza.
     * @param row La fila donde se colocará la pieza.
     * @param col La columna donde se colocará la pieza.
     * @param color El color de la pieza (puede ser "blanco" o "negro").
     */
    private void addPiece(StackPane stackPane, int row, int col, String color) {
        if (col == 0 || col == 7) {
            switch (row) {
                case 0, 7 -> createPiece(stackPane, "torre", color);
                case 1, 6 -> createPiece(stackPane, "caballero", color);
                case 2, 5 -> createPiece(stackPane, "obispo", color);
                case 4 -> createPiece(stackPane, "reina", color);
                case 3 -> createPiece(stackPane, "rey", color);
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
        imageView.getProperties().put("type", pieceType);
        imageView.getProperties().put("color", color);
        stackPane.getChildren().add(imageView);
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
