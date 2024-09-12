package Dominio;


import Features.DragAndDrop;
import javafx.scene.Node;

public class RulesForPices {
    private DragAndDrop dragAndDrop;

    public RulesForPices(DragAndDrop dragAndDrop) {
        this.dragAndDrop = dragAndDrop;
    }

    public boolean validMovement(int nexRow, int nexCol, int row, int col, String type, String color){

        boolean valid = false;

        switch (type){
            case "torre":
                if(row == nexRow){
                    valid = true;
                    dragAndDrop.setMovementType("Horizontal");
                }
                if (col == nexCol){
                    valid = true;
                    dragAndDrop.setMovementType("Vertical");
                }
                break;
            case "rey":
                valid = validMovementKing(row,col,nexRow,nexCol);
                break;
            case "peon":
                valid = rulesForPeon(row,col,nexRow,nexCol,color);
                break;

            case "obispo":
                valid = validMovementObispo(row,col,nexRow,nexCol);
                break;
            case "caballero":
                valid = validMovementknight(row, col, nexRow, nexCol);
                break;
            case "reina":
                valid = validMovementQueen(row, col, nexRow, nexCol);
                break;
        }

        return valid;


    }
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
    private boolean validMovementknight(int row, int col, int nexRow, int nexCol){

        boolean valid = false;

        if ((col+2 == nexCol || col-2 == nexCol) && (row+1 == nexRow || row-1 == nexRow)){
            valid = true;
        }
        if ((col+1 == nexCol || col-1 == nexCol) && (row+2 == nexRow || row-2 == nexRow)){
            valid = true;
        }
        return valid;

    }
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
    private boolean rulesForPeonBlack(int row, int col,int nexRow, int nexCol){
        boolean valid = false;

        if (col == 1 && nexCol <= 3 && row == nexRow){
            valid = true;
            dragAndDrop.setMovementType("Horizontal");
        }
        else {
            if (row == nexRow && col-1 != nexCol && col+1 == nexCol && !dragAndDrop.getCanEatSomething()){
                valid = true;
                dragAndDrop.setMovementType("Horizontal");
            }
        }

        if (col+1 == nexCol && (row+1 == nexRow || row-1 == nexRow) && dragAndDrop.getCanEatSomething() ){
            valid = true;
        }


        return valid;

    }
    private boolean rulesForPeonWhite(int row, int col,int nexRow, int nexCol){
        boolean valid = false;

        if (col == 6 && nexCol >= 4 && row == nexRow){
            valid = true;
            dragAndDrop.setMovementType("Horizontal");
        }
        else {
            if (row == nexRow && col+1 != nexCol && col-1 == nexCol && !dragAndDrop.getCanEatSomething()){
                valid = true;
                dragAndDrop.setMovementType("Horizontal");
            }
        }

        if (col-1 == nexCol && (row+1 == nexRow || row-1 == nexRow) && dragAndDrop.getCanEatSomething() ){
            valid = true;
        }


        return valid;

    }
    public boolean canEatThisColor(Node pieceToEat, Node piece){
        if (pieceToEat != null && piece != null) {
            if (pieceToEat.getProperties().get("color").equals(piece.getProperties().get("color"))) {
                return false;
            }
        }

        return true;
    }



}
