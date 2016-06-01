package edu.masters.cs498.ransomca.dodgerball;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by user1 on 12/6/15.
 */
public class SlowMoPanel extends DefaultGamePanel
{

  //for debugging purposes
  private static final String TAG = SlowMoPanel.class.getSimpleName();

  public SlowMoPanel(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
  }

  public SlowMoPanel(Context context, AttributeSet attrs)
  {
    this(context, attrs, 0);
  }

  public SlowMoPanel(Context context)
  {
    this(context, null);
  }

  @Override
  public void addBall(){

    Ball ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.blueball));

    if(Math.random() > .5){
      ball.setX((float) (100 + this.getWidth()));
    }
    else
    {
      ball.setX((float) -100);
    }

    Log.d(TAG, "Should be setting the random pos here");

    ball.setY((float) (Math.random() * super.getHeight()));

    ball.setVx(ball.getVx() / 2);
    ball.setVy(ball.getVy() / 2);

    entities.add(ball);
  }

  @Override
  public void updateBallCount()
  {
    //adds a ball every 100 points
    if(score % 100 == 0)
    {
      player.shrinkPlayer();
    }
    if(score % 50 == 0)
    {
      addBall();
      gameActivity.playSound("NEW_BALL_SOUND");
    }
  }
}
