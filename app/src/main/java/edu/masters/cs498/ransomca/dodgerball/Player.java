package edu.masters.cs498.ransomca.dodgerball;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by user1 on 11/29/15.
 */
public class Player extends Ball
{
  private int playerSize = 300;
  private int pointerID = -1;

  public Player(Bitmap bitmap)
  {
    super(bitmap);
    this.setBitmap(Bitmap.createScaledBitmap(bitmap, playerSize, playerSize, false));

    //TODO make this a relative value
    this.setX(500);
    this.setY(500);
  }

  @Override
  public void update(Canvas canvas)
  {
    //super.update(canvas);
  }

  public void shrinkPlayer(){

    //Shifts the ball over so it 'shrinks' maintaining the same center position
    int shiftAmount = 0;
    if(playerSize > 50)
    { shiftAmount = (int) (playerSize * 0.05);
      playerSize = (int) (playerSize * 0.9);
    }

    super.setX(super.getX() + shiftAmount);
    super.setY(super.getY() + shiftAmount);

    this.setBitmap(Bitmap.createScaledBitmap(this.getBitmap(), playerSize, playerSize, false));
  }

  public boolean checkBallCollision(Ball ball, int minimumDistance){

    double distance;
    double Ydistance = ball.getY() + ball.getBitmapWidth()/2 - this.getY() - this.getBitmapWidth()/2;
    Ydistance = Ydistance * Ydistance;
    double Xdistance = ball.getX() + ball.getBitmapWidth()/2 - this.getX() -  this.getBitmapHeight()/2;
    Xdistance = Xdistance * Xdistance;
    distance = Ydistance + Xdistance;

    if(distance < minimumDistance){
      return true;
    }
    else
      return false;
  }

  public boolean checkPointCollision(int X, int Y){
    double distance;
    double Xdistance = X - (this.getX() +  this.getBitmapHeight()/2);
    double Ydistance = Y - (this.getY() + this.getBitmapWidth()/2);
    Ydistance = Ydistance * Ydistance;
    Xdistance = Xdistance * Xdistance;

    distance = Ydistance + Xdistance;

    //Squares the minimum distance rather than sqrting the sum
    if(distance < (this.getBitmapWidth() * this.getBitmapWidth())/2){
      return true;
    }
    else
      return false;
  }

  public int getPointerID()
  {
    return pointerID;
  }

  public void setPointerID(int touchOrder)
  {
    this.pointerID = touchOrder;
  }

  public void handleTouchEvent(MotionEvent event){
    this.setX(event.getX() - getBitmap().getWidth() / 2);
    this.setY(event.getY() - getBitmap().getHeight() / 2);
  }

  public void handleMultiTouchEvent(MotionEvent event)
  {
    if(pointerID != -1)
    {
      int pointerIndex = event.findPointerIndex(pointerID);
      this.setX(event.getX(pointerIndex) - getBitmap().getWidth() / 2);
      this.setY(event.getY(pointerIndex) - getBitmap().getHeight() / 2);
    }
  }
}
