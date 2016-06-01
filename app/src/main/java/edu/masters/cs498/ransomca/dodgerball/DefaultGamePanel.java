package edu.masters.cs498.ransomca.dodgerball;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by user1 on 12/5/15.
 */

public class DefaultGamePanel extends GamePanel
{

  protected ArrayList<Ball> entities = new ArrayList();
  protected Player player;

  //For debugging
  private static final String TAG = DefaultGamePanel.class.getSimpleName();



  public DefaultGamePanel(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
    getHolder().addCallback(this);
    player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.redball));
    thread = new GameThread(getHolder(), this);
  }

  public DefaultGamePanel(Context context, AttributeSet attrs)
  {
    this(context, attrs, 0);
  }

  public DefaultGamePanel(Context context)
  {
    this(context, null);
  }

  @Override
  protected void onDraw(Canvas canvas)
  {
    canvas.drawColor(Color.WHITE);
    drawPlayers(canvas);
    for(int i = 0; i < entities.size(); i++){
      entities.get(i).drawBall(canvas);
    }

    String temp = "" + score;
    Paint painter = new Paint(Color.BLACK);
    painter.setTextSize(40);
    canvas.drawText(temp, 50, 50, painter);
  }

  public void drawPlayers(Canvas canvas)
  {
    player.drawBall(canvas);
  }

  @Override
  public void endGame(){
    gameActivity.endGame();
    thread.setRunnable(false);
    gameActivity.finish();
    thread = null;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    if(thread != null)
    {
      //Game loop pauses until screen is touched
      thread.setWaitForStart(false);
    }

    //Updates the new player location within the player class instead of here

    player.handleTouchEvent(event);

    //claims the touch event so its not sent to other views
    return true;
  }

  @Override
  public void update(Canvas canvas)
  {

    checkPlayerBallCollision();

    score++;
    //updates the new position of all the balls
    for(int i = 0; i < entities.size(); i++){
      entities.get(i).update(canvas);
    }

    //Commented out for debuggings
    //updateBallCount();

  }

  public void checkPlayerBallCollision(){

    Ball ball = entities.get(0);
    //adds the radius of both the player and ball to find the minimum distance
    int minimumDistance = player.getBitmapWidth() / 2  + ball.getBitmapWidth() / 2;
    minimumDistance = minimumDistance * minimumDistance;

    for(int i = 0; i < entities.size(); i++){
      ball = entities.get(i);
      if(player.checkBallCollision(ball, minimumDistance)){
        gameActivity.playSound("GAME_OVER_SOUND");
        endGame();
        break;
      }
    }
  }

  public void updateBallCount()
  {
    //adds a ball every 100 points
    if(score % 100 == 0){
      player.shrinkPlayer();
      addBall();
      gameActivity.playSound("NEW_BALL_SOUND");
    }
  }

  @Override
  public void addBall(){
    Ball ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.blueball));

    if(Math.random() > .5){
      ball.setX((float) 50 + this.getWidth());
    }
    else
      ball.setX((float) -50);

    ball.setY((float) Math.random() * this.getHeight());

    entities.add(ball);
  }

  @Override
  public void initializeBalls()
  {
    for(int i = 0; i < 3; i++){
      addBall();
    }
  }

}
