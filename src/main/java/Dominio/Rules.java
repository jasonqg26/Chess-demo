package Dominio;

public class Rules {

    private int movements;

    public Rules() {
        this.movements = 0;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public boolean isYouTurn(String color){
        boolean isYouTurn = false;

        if (getMovements() == 0 && color.equals("Blancas")) {
            isYouTurn = true;
        }else {

            if (color.equals("Blancas") && getMovements() % 2 == 0) {
                isYouTurn = true;

            }
            if (color.equals("Negras") && getMovements() % 2 != 0) {
                isYouTurn = true;
            }
        }
        return isYouTurn;
    }

    //1.Juega primero las fichas blancas

}
