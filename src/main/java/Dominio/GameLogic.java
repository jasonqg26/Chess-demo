package Dominio;

import Features.DragAndDrop;
import Dominio.Piece.RulesForPices;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class GameLogic {

    private DragAndDrop dragAndDrop;
    private RulesForPices rulesForPices;
    private Scene scene;
    private Rules rules = new Rules();


    public GameLogic(DragAndDrop dragAndDrop, RulesForPices rulesForPices) {
        this.dragAndDrop = dragAndDrop;
        this.rulesForPices = rulesForPices;
    }


    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Configura los eventos de arrastrar y soltar para las piezas de ajedrez en el tablero.
     *
     * @param stackPane El StackPane que contiene la pieza.
     * @param gridPane El GridPane que representa el tablero.
     */
    public void setDragAndDrop(StackPane stackPane, GridPane gridPane) {
        stackPane.setOnMouseDragged(event -> handleDrag(stackPane, event));
        if (dragAndDrop != null) {
            gridPane.setOnMouseReleased(event -> handleMouseRelease(gridPane, event));
        }
    }



    /**
     * Cambia el cursor del ratón cuando se pasa por encima de un StackPane que contiene una pieza de ajedrez.
     * Si la casilla contiene una pieza, es el turno del jugador que controla esa pieza, y no se está arrastrando
     * otra pieza, el cursor se cambia a una mano abierta. De lo contrario, el cursor vuelve al estado predeterminado.
     *
     * @param stackPane El StackPane que se está revisando cuando el ratón pasa por encima.
     */
    public void setMouseOver(StackPane stackPane) {
        if (isPieceInStackPane(stackPane)
                && rules.isYouTurn(stackPane.getChildren().getLast().getProperties().get("color").toString())
                && dragAndDrop.getImageView() == null) {
            getScene().setCursor(Cursor.OPEN_HAND);
        }
        else if (dragAndDrop.getImageView() == null){
            getScene().setCursor(Cursor.DEFAULT);
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

            if (rules.isYouTurn(stackPane.getChildren().getLast().getProperties().get("color").toString())){


                updateDragAndDropData(stackPane); // Actualiza la información de la pieza arrastrada.
                removePieceFromStackPane(stackPane); // Elimina la pieza del StackPane.
                updateCursorWithPieceImage(); // Actualiza el cursor con la imagen de la pieza

            }
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
        dragAndDrop.setMovementType(null);
        rules.setMovements(rules.getMovements() + 1);
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
            getScene().setCursor(Cursor.DEFAULT);
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

        if (dragAndDrop.getMovementType() != null) {
            switch (dragAndDrop.getMovementType()) {
                case "Horizontal":
                    piecesInMiddle = checkPiecesInTheMiddleHorizontal(row, col, nexCol, gridPane);
                    break;
                case "Vertical":
                    piecesInMiddle = checkPiecesInTheMiddleVertical(row, col, nexRow, gridPane);
                    break;
                case "Diagonal":
                    piecesInMiddle = checkPiecesInTheMiddleDiagonal(row, col, nexRow, nexCol, gridPane);
                    break;

            }
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
