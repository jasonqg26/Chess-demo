package Features;

import javafx.scene.image.ImageView;


public class DragAndDrop {

   private ImageView imageView;
   private int initial_positionX;
   private int initial_positionY;
   private boolean canEatSomething;
   private String movementType;


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public int getInitial_positionX() {
        return initial_positionX;
    }

    public int getInitial_positionY() {
        return initial_positionY;
    }

    public void setInitial_positionX(int initial_positionX) {
        this.initial_positionX = initial_positionX;
    }

    public void setInitial_positionY(int initial_positionY) {
        this.initial_positionY = initial_positionY;
    }

    public boolean getCanEatSomething() {
        return canEatSomething;
    }

    public void setCanEatSomething(boolean canEatSomething) {
        this.canEatSomething = canEatSomething;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
}





