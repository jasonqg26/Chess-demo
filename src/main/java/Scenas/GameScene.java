package Scenas;

import Dominio.RulesForPices;
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
    RulesForPices rulesForPices = new RulesForPices(dragAndDrop);
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
                setDragAndDrop(stackPane, gridPane);
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
     * Configura los eventos de arrastrar y soltar para las piezas de ajedrez en el tablero.
     *
     * @param stackPane El StackPane que contiene la pieza.
     * @param gridPane El GridPane que representa el tablero.
     */
    private void setDragAndDrop(StackPane stackPane, GridPane gridPane) {
        stackPane.setOnMouseDragged(event -> handleDrag(stackPane, event));
        if (dragAndDrop != null) {
            gridPane.setOnMouseReleased(event -> handleMouseRelease(gridPane, event));
        }
    }


    /**
     * Este método maneja el evento de arrastre del ratón en un StackPane. Si el StackPane
     * contiene una pieza, se actualiza la información de arrastre, se elimina la pieza del StackPane,
     * y se actualiza el cursor con la imagen de la pieza que se está arrastrando.
     *
     * @param stackPane El StackPane que contiene la pieza a arrastrar.
     * @param event El evento de arrastre del ratón.
     */
    private void handleDrag(StackPane stackPane, MouseEvent event) {
        if (isPieceInStackPane(stackPane)) { // Verifica si el StackPane contiene una pieza.
            updateDragAndDropData(stackPane); // Actualiza la información de la pieza arrastrada.
            removePieceFromStackPane(stackPane); // Elimina la pieza del StackPane.
            updateCursorWithPieceImage(); // Actualiza el cursor con la imagen de la pieza.
        }
        event.consume(); // Consume el evento para que no se propague más allá.
    }

    /**
     * Verifica si un StackPane contiene una pieza de ajedrez.
     *
     * @param stackPane El StackPane a verificar.
     * @return true si el StackPane contiene una pieza, false en caso contrario.
     */
    private boolean isPieceInStackPane(StackPane stackPane) {
        return !stackPane.getChildren().isEmpty() && // Verifica si el StackPane no está vacío.
                "pieza".equals(stackPane.getChildren().getLast().getProperties().get("is")); // Verifica si el último hijo es una pieza.
    }

    /**
     * Actualiza la información de la pieza que se está arrastrando, incluyendo su posición inicial
     * y la referencia a la imagen de la pieza.
     *
     * @param stackPane El StackPane que contiene la pieza a arrastrar.
     */
    private void updateDragAndDropData(StackPane stackPane) {
        // Guarda la posición inicial de la pieza que se está arrastrando.
        dragAndDrop.setInitial_positionX((int) stackPane.getProperties().get("row"));
        dragAndDrop.setInitial_positionY((int) stackPane.getProperties().get("col"));

        // Guarda la referencia a la imagen de la pieza que se está arrastrando.
        dragAndDrop.setImageView((ImageView) stackPane.getChildren().get(stackPane.getChildren().size() - 1));
    }

    /**
     * Elimina la pieza de ajedrez del StackPane que la contiene.
     *
     * (Debe tener una pieza sino eliminara el tablero)
     *
     * @param stackPane El StackPane que contiene la pieza a eliminar.
     */
    private void removePieceFromStackPane(StackPane stackPane) {
        // Elimina la última pieza (el último hijo) del StackPane.
        stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
    }

    /**
     * Actualiza el cursor del ratón para que muestre la imagen de la pieza que se está arrastrando.
     */
    private void updateCursorWithPieceImage() {
        // Crea un nuevo cursor utilizando la imagen de la pieza que se está arrastrando.
        Cursor cursor = new ImageCursor(dragAndDrop.getImageView().getImage());

        // Asigna el nuevo cursor a la escena.
        scene.setCursor(cursor);
    }










    /**
     * Este método maneja la liberación del ratón, coloca la pieza en la posición correcta,
     * y determina si se debe realizar una captura de una pieza enemiga.
     *
     * @param gridPane El GridPane que contiene el tablero.
     * @param event El evento de liberación del ratón.
     */
    private void handleMouseRelease(GridPane gridPane, MouseEvent event) {
        Node targetNode = event.getPickResult().getIntersectedNode();

        if (dragAndDrop.getImageView() != null && isValidTarget(targetNode)) {
            StackPane targetStackPane = (StackPane) targetNode.getParent();

            if (isOccupiedByPiece(targetNode)) {
                handleOccupiedSquare(targetStackPane, gridPane, targetNode);
            } else {
                handleEmptySquare(targetStackPane, gridPane);
            }
        }
    }

    /**
     * Verifica si el nodo de destino es válido y contiene la propiedad "is".
     *
     * @param targetNode El nodo de destino.
     * @return true si el nodo de destino es válido, de lo contrario false.
     */
    private boolean isValidTarget(Node targetNode) {
        return targetNode != null && targetNode.getProperties().containsKey("is");
    }

    /**
     * Verifica si el nodo de destino está ocupado por una pieza.
     *
     * @param targetNode El nodo de destino.
     * @return true si el nodo está ocupado por una pieza, de lo contrario false.
     */
    private boolean isOccupiedByPiece(Node targetNode) {
        return "pieza".equals(targetNode.getProperties().get("is"));
    }

    /**
     * Maneja el caso en el que la casilla objetivo ya está ocupada por una pieza.
     *
     * @param targetStackPane El StackPane de destino.
     * @param gridPane El GridPane que contiene el tablero.
     * @param targetNode El nodo de destino.
     */
    private void handleOccupiedSquare(StackPane targetStackPane, GridPane gridPane, Node targetNode) {
        boolean canEat = rulesForPices.canEatThisColor(targetNode, dragAndDrop.getImageView());

        dragAndDrop.setCanEatSomething(canEat);

        if (canEat && tryMovePiece(targetNode.getParent(), gridPane, true)) {
            removePiece(targetStackPane);
        } else {
            tryMovePiece(targetNode.getParent(), gridPane, false);
            dragAndDrop.setCanEatSomething(false);
        }
    }

    /**
     * Maneja el caso en el que la casilla objetivo está vacía.
     *
     * @param targetStackPane El StackPane de destino.
     * @param gridPane El GridPane que contiene el tablero.
     */
    private void handleEmptySquare(StackPane targetStackPane, GridPane gridPane) {
        Node lastChild = targetStackPane.getChildren().get(targetStackPane.getChildren().size() - 1);
        if (isOccupiedByPiece(lastChild)) {
            handleOccupiedSquare(targetStackPane, gridPane, lastChild);
        } else {
            tryMovePiece(targetStackPane, gridPane, true);
        }
    }

    /**
     * Intenta mover la pieza a la nueva posición y verifica si el movimiento es válido.
     *
     * @param targetStackPane El StackPane de destino.
     * @param gridPane El GridPane que contiene el tablero.
     * @param canEatColor Indica si la pieza puede capturar una pieza enemiga.
     * @return true si el movimiento es válido y se realizó, de lo contrario false.
     */
    private boolean tryMovePiece(Node targetStackPane, GridPane gridPane, boolean canEatColor) {
        int nexRow = (int) targetStackPane.getProperties().get("row");
        int nexCol = (int) targetStackPane.getProperties().get("col");
        int startRow = dragAndDrop.getInitial_positionX();
        int startCol = dragAndDrop.getInitial_positionY();

        if (canEatColor && rulesForPices.validMovement(nexRow, nexCol, startRow, startCol,
                dragAndDrop.getImageView().getProperties().get("type").toString(),
                dragAndDrop.getImageView().getProperties().get("color").toString())) {

            if (!piecesInTheMiddle(startRow,startCol,nexRow,nexCol,gridPane)){

                movePieceToNewLocation(nexRow, nexCol, gridPane);
                return true;

            }
            else {
                goBack(startRow, startCol, gridPane);
                return false;
            }


        } else {
            goBack(startRow, startCol, gridPane);
            return false;
        }
    }

    /**
     * Mueve la pieza a la nueva ubicación en el tablero.
     *
     * @param nexRow La fila de destino.
     * @param nexCol La columna de destino.
     * @param gridPane El GridPane que contiene el tablero.
     */
    private void movePieceToNewLocation(int nexRow, int nexCol, GridPane gridPane) {
        StackPane targetStackPane = getStackPane(nexRow,nexCol,gridPane);
        placePiece(targetStackPane);
        dragAndDrop.setCanEatSomething(false);
    }

    /**
     * Elimina la última imagen del StackPane.
     *
     * @param stackPane El StackPane de donde se eliminará la imagen.
     */
    private void removePiece(StackPane stackPane) {
        stackPane.getChildren().remove(stackPane.getChildren().size() - 2);
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


    /**
     * Restaura la pieza a su posición original en el tablero si el movimiento realizado no es válido.
     * Este método coloca la pieza en el StackPane de la posición inicial desde la cual fue arrastrada.
     *
     * @param starRow La fila de la posición original de la pieza.
     * @param starCol La columna de la posición original de la pieza.
     * @param gridPane El GridPane que contiene el tablero.
     */
    private void goBack(int starRow,int starCol,GridPane gridPane){
        if (starCol == 0) {
            StackPane stackPanE = (StackPane) gridPane.getChildren().get(starRow);
            placePiece(stackPanE);
        } else {
            int index = (7 * starCol) + starRow + starCol;
            StackPane stackPanE = (StackPane) gridPane.getChildren().get(index);
            placePiece(stackPanE);
        }


    }



    /**
     * Verifica si hay piezas en el camino entre dos posiciones, dependiendo del tipo de movimiento (horizontal, vertical o diagonal).
     *
     * @param row      La fila de la posición inicial.
     * @param col      La columna de la posición inicial.
     * @param nexRow   La fila de la posición final.
     * @param nexCol   La columna de la posición final.
     * @param gridPane El GridPane que contiene el tablero.
     * @return true si hay piezas en el camino; false en caso contrario.
     */
    private boolean piecesInTheMiddle(int row, int col, int nexRow, int nexCol, GridPane gridPane) {
        boolean piecesInMiddle = false;

        switch (dragAndDrop.getMovementType()) {
            case "Horizontal":
                piecesInMiddle = checkPiecesInTheMiddleHorizontal(row,col,nexCol,gridPane);
                break;
            case "Vertical":
                piecesInMiddle = checkPiecesInTheMiddleVertical(row,col,nexRow,gridPane);
                break;
            case "Diagonal":
                piecesInMiddle = checkPiecesInTheMiddleDiagonal(row,col,nexRow,nexCol,gridPane);
                break;
            default:
                return piecesInMiddle;
        }

        return piecesInMiddle;

    }


    /**
     * Verifica si hay piezas en el camino diagonal entre dos posiciones.
     *
     * @param starRow  La fila de la posición inicial.
     * @param starCol  La columna de la posición inicial.
     * @param nexRow   La fila de la posición final.
     * @param nexCol   La columna de la posición final.
     * @param gridPane El GridPane que contiene el tablero.
     * @return true si hay piezas en el camino diagonal; false en caso contrario.
     */
    private boolean checkPiecesInTheMiddleDiagonal(int starRow, int starCol, int nexRow, int nexCol, GridPane gridPane) {
        int rowStep = starRow < nexRow ? 1 : -1;  // Determina si incrementamos o decrementamos filas
        int colStep = starCol < nexCol ? 1 : -1;  // Determina si incrementamos o decrementamos columnas
        boolean piecesInMiddle = false;
        int col = starCol;

        for (int row = starRow + rowStep; row != nexRow; row += rowStep) {
            col += colStep;  // Incrementa o decrementa la columna en cada paso

            if (isPieceInStackPane(getStackPane(row, col, gridPane))) {
                piecesInMiddle =  true;
                break;
            }
        }

        return piecesInMiddle;
    }

    /**
     * Verifica si hay piezas en el camino vertical entre dos posiciones.
     *
     * @param starRow  La fila de la posición inicial.
     * @param starCol  La columna de la posición inicial.
     * @param nexRow   La fila de la posición final.
     * @param gridPane El GridPane que contiene el tablero.
     * @return true si hay piezas en el camino vertical; false en caso contrario.
     */
    private boolean checkPiecesInTheMiddleVertical(int starRow, int starCol, int nexRow, GridPane gridPane) {
        int step = starRow > nexRow ? -1 : 1;  // Determina si incrementamos o decrementamos
        boolean piecesInMiddle = false;

        for (int row = starRow; row != nexRow; row += step) {
            if (isPieceInStackPane(getStackPane(row, starCol, gridPane))) {
                piecesInMiddle = true;
                break;
            }
        }

        return piecesInMiddle;
    }

    /**
     * Verifica si hay piezas en el camino horizontal entre dos posiciones.
     *
     * @param starRow  La fila de la posición inicial.
     * @param starCol  La columna de la posición inicial.
     * @param nexCol   La columna de la posición final.
     * @param gridPane El GridPane que contiene el tablero.
     * @return true si hay piezas en el camino horizontal; false en caso contrario.
     */
    private boolean checkPiecesInTheMiddleHorizontal(int starRow, int starCol, int nexCol, GridPane gridPane) {
        int step = starCol > nexCol ? -1 : 1;  // Determina si incrementamos o decrementamos
        boolean piecesInMiddle = false;

        for (int col = starCol; col != nexCol; col += step) {
            if (isPieceInStackPane(getStackPane(starRow, col, gridPane))) {
                piecesInMiddle = true;
                break;
            }
        }
        return piecesInMiddle;
    }



    /**
     * Obtiene el StackPane en la fila y columna especificadas.
     *
     * @param row      La fila del StackPane.
     * @param col      La columna del StackPane.
     * @param gridPane El GridPane que contiene el tablero.
     * @return El StackPane en la posición especificada.
     */
    private StackPane getStackPane(int row, int col, GridPane gridPane) {
        if (col == 0) {
            return (StackPane) gridPane.getChildren().get(row);
        } else {
            int index = (7 * col) + row + col;
            return (StackPane) gridPane.getChildren().get(index);
        }
    }





}
