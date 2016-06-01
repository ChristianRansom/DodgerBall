package edu.masters.cs498.ransomca.dodgerball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by user1 on 11/29/15.
 */
public class Ball
{
  private final int ballSize = 50;
  private Bitmap bitmap;
  protected float x, y;
  private float vx, vy;

  public Ball(Bitmap bitmap)
  {
    this.bitmap = Bitmap.createScaledBitmap(bitmap, ballSize, ballSize, false);

    //initial speed
    vx = 10.0f;
    vy = 10.0f;

    //randomize initial ball direction
    if(Math.random() > .5)
      vx = -Math.abs(vx);
    if(Math.random() > .5)
      vy = -Math.abs(vy);

  }

  public void update(Canvas canvas) {
    checkWallCollision(canvas);
    x += vx;
    y += vy;
  }

  public void checkWallCollision(Canvas canvas) {

    if(x - vx < 0) {

      vx = Math.abs(vx);

    } else if(x + vx > canvas.getWidth() - getBitmapWidth()) {

      vx = -Math.abs(vx);
    }

    if(y - vy < 0) {

      vy = Math.abs(vy);

    } else if(y + vy > canvas.getHeight() - getBitmapHeight()) {

      vy = -Math.abs(vy);
    }
  }

  public int getBitmapWidth() {

    if(bitmap != null) {

      return bitmap.getWidth();

    } else {

      return 0;
    }
  }



  public int getBitmapHeight() {

    if(bitmap != null) {

      return bitmap.getHeight();

    } else {

      return 0;
    }
  }

  public void drawBall(Canvas canvas){
    canvas.drawBitmap(bitmap, x, y, null);
  }

  public float getX()
  {
    return x;
  }

  public void setX(float x)
  {
    this.x = x;
  }

  public float getY()
  {
    return y;
  }

  public void setY(float y)
  {
    this.y = y;
  }

  public Bitmap getBitmap()
  {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap)
  {
    this.bitmap = bitmap;
  }

  public void setVx(float vx)
  {
    this.vx = vx;
  }

  public void setVy(float vy)
  {
    this.vy = vy;
  }

  public float getVx()
  {
    return vx;
  }

  public float getVy()
  {
    return vy;
  }
}
