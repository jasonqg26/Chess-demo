package Dominio.Piece;


import Features.DragAndDrop;
import javafx.scene.Node;


public class RulesForPices {
    private DragAndDrop dragAndDrop;

    public RulesForPices(DragAndDrop dragAndDrop) {
        this.dragAndDrop = dragAndDrop;
    }




    /**
     * Valida el movimiento de una pieza de ajedrez dependiendo de su tipo.
     *
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @param row Fila actual.
     * @param col Columna actual.
     * @param type Tipo de la pieza (e.g., "torre", "rey").
     * @param color Color de la pieza ("Blancas" o "Negras").
     * @return true si el movimiento es válido, false en caso contrario.
     */
    public boolean validMovement(int nexRow, int nexCol, int row, int col, String type, String color) {
        boolean valid = false;

        switch (type) {
            case "torre":
                valid = validMovementTorre(row, col, nexRow, nexCol);
                break;
            case "rey":
                valid = validMovementKing(row, col, nexRow, nexCol);
                break;
            case "peon":
                valid = rulesForPeon(row, col, nexRow, nexCol, color);
                break;
            case "obispo":
                valid = validMovementObispo(row, col, nexRow, nexCol);
                break;
            case "caballero":
                valid = validMovementKnight(row, col, nexRow, nexCol);
                break;
            case "reina":
                valid = validMovementQueen(row, col, nexRow, nexCol);
                break;
        }

        return valid;
    }



    /**
     * Valida el movimiento de una torre.
     * La torre se puede mover en línea recta horizontalmente o verticalmente.
     *
     * @param row Fila actual de la torre.
     * @param col Columna actual de la torre.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean validMovementTorre(int row, int col, int nexRow, int nexCol) {
        boolean valid = false;

        if (row == nexRow) {
            valid = true;
            dragAndDrop.setMovementType("Horizontal");
        }
        if (col == nexCol) {
            valid = true;
            dragAndDrop.setMovementType("Vertical");
        }

        return valid;
    }

    /**
     * Valida el movimiento del rey.
     * El rey puede moverse un solo espacio en cualquier dirección:
     * horizontal, vertical o diagonal.
     *
     * @param row Fila actual del rey.
     * @param col Columna actual del rey.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean validMovementKing(int row, int col, int nexRow, int nexCol){

        boolean valid = false;

        if (row == nexRow && (col+1 == nexCol || col-1 == nexCol)){
            //Movimiento horizontal
            dragAndDrop.setMovementType("Horizontal");
            valid = true;
        } else if (col == nexCol && (row+1 == nexRow || row-1 == nexRow)) {
            //Movimiento Vertical
            valid = true;
            dragAndDrop.setMovementType("Vertical");
        } else if ((col+1 == nexCol || col-1 == nexCol) && (row-1 == nexRow || row+1 == nexRow)) {
            //Movimiento diagonal
            valid = true;
            dragAndDrop.setMovementType("Diagonal");
        }

        return valid;
    }

    /**
     * Valida el movimiento de la reina.
     * La reina puede moverse en cualquier dirección:
     * horizontal, vertical o diagonal.
     *
     * @param row Fila actual de la reina.
     * @param col Columna actual de la reina.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean validMovementQueen(int row, int col, int nexRow, int nexCol){

        boolean valid = false;

        int differenceRow = Math.abs(row - nexRow);
        int differenceCol = Math.abs(col - nexCol);


        if (differenceCol == differenceRow){
            dragAndDrop.setMovementType("Diagonal");
            valid = true;
        }
        if ((row == nexRow)){
            dragAndDrop.setMovementType("Horizontal");
            valid = true;
        }
        if (col == nexCol){
            dragAndDrop.setMovementType("Vertical");
            valid = true;
        }

        return valid;


    }


    /**
     * Valida el movimiento del obispo.
     * El obispo solo puede moverse en diagonal.
     *
     * @param row Fila actual del obispo.
     * @param col Columna actual del obispo.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean validMovementObispo(int row, int col, int nexRow, int nexCol){
        boolean valid = false;

        int differenceRow = Math.abs(row - nexRow);
        int differenceCol = Math.abs(col - nexCol);


        if (differenceCol == differenceRow){
            valid = true;
            dragAndDrop.setMovementType("Diagonal");
        }

        return valid;






    }


    /**
     * Valida el movimiento del caballero.
     * El caballero se mueve en forma de "L"
     *
     * @param row Fila actual del caballero.
     * @param col Columna actual del caballero.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean validMovementKnight(int row, int col, int nexRow, int nexCol){

        boolean valid = false;

        if ((col+2 == nexCol || col-2 == nexCol) && (row+1 == nexRow || row-1 == nexRow)){
            valid = true;


        }
        if ((col+1 == nexCol || col-1 == nexCol) && (row+2 == nexRow || row-2 == nexRow)){
            valid = true;
        }

        return valid;

    }

    /**
     * Valida el movimiento del peón. El peón tiene reglas diferentes según su color.
     * Delegará la validación a métodos específicos para peones negros o blancos.
     *
     * @param row Fila actual del peón.
     * @param col Columna actual del peón.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @param color Color del peón ("Negras" o "Blancas").
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean rulesForPeon(int row, int col,int nexRow, int nexCol,String color){
        boolean valid = false;

        switch (color){
            case "Negras":
                valid = rulesForPeonBlack(row, col, nexRow, nexCol);
                break;
            case "Blancas":
                valid = rulesForPeonWhite(row, col, nexRow, nexCol);
                break;
        }
        return valid;

    }

    /**
     * Valida el movimiento de un peón negro.
     * El peón negro puede avanzar una o dos casillas en su primer movimiento
     * o una casilla en movimientos subsecuentes, y capturar piezas en diagonal.
     *
     * @param row Fila actual del peón.
     * @param col Columna actual del peón.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean rulesForPeonBlack(int row, int col,int nexRow, int nexCol){
        boolean valid = false;

        if (col == 1 && nexCol <= 3 && row == nexRow && nexCol != col){
            valid = true;
            dragAndDrop.setMovementType("Horizontal");
        }
        else {
            if (row == nexRow && col-1 != nexCol && col+1 == nexCol &&  nexCol != col &&!dragAndDrop.getCanEatSomething()){
                valid = true;
                dragAndDrop.setMovementType("Horizontal");
            }
        }

        if (col+1 == nexCol && (row+1 == nexRow || row-1 == nexRow) && dragAndDrop.getCanEatSomething() ){
            valid = true;
        }


        return valid;

    }

    /**
     * Valida el movimiento de un peón blanco.
     * El peón blanco puede avanzar una o dos casillas en su primer movimiento
     * o una casilla en movimientos subsecuentes, y capturar piezas en diagonal.
     *
     * @param row Fila actual del peón.
     * @param col Columna actual del peón.
     * @param nexRow Fila destino.
     * @param nexCol Columna destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    private boolean rulesForPeonWhite(int row, int col,int nexRow, int nexCol){
        boolean valid = false;

        if (col == 6 && nexCol >= 4 && row == nexRow && nexCol != col ){
            valid = true;
            dragAndDrop.setMovementType("Horizontal");
        }
        else {
            if (row == nexRow && col+1 != nexCol && col-1 == nexCol && !dragAndDrop.getCanEatSomething() && nexCol != col){
                valid = true;
                dragAndDrop.setMovementType("Horizontal");
            }
        }

        if (col-1 == nexCol && (row+1 == nexRow || row-1 == nexRow) && dragAndDrop.getCanEatSomething() ){
            valid = true;
        }


        return valid;

    }

    /**
     * Verifica si una pieza puede capturar a otra dependiendo del color.
     * Las piezas solo pueden capturar a piezas de diferente color.
     *
     * @param pieceToEat La pieza que se pretende capturar.
     * @param piece La pieza que está intentando capturar.
     * @return true si la pieza puede ser capturada, false si no.
     */
    public boolean canEatThisColor(Node pieceToEat, Node piece){
        if (pieceToEat != null && piece != null) {
            if (pieceToEat.getProperties().get("color").equals(piece.getProperties().get("color"))) {
                return false;
            }
        }

        return true;
    }



}
