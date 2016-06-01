package edu.masters.cs498.ransomca.dodgerball;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by user1 on 12/6/15.
 */
public class DoubleTroublePanel extends DefaultGamePanel
{
  private ArrayList<Player> players = new ArrayList();

  //for debugging purposes
  private static final String TAG = DoubleTroublePanel.class.getSimpleName();


  public DoubleTroublePanel(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
    Player player1 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.redball));
    player1.setX(300);
    player1.shrinkPlayer();
    player1.shrinkPlayer();
    players.add(player1);
    Player player2 = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.redball));
    player2.setX(700);
    player2.shrinkPlayer();
    player2.shrinkPlayer();
    players.add(player2);
  }

  public DoubleTroublePanel(Context context, AttributeSet attrs)
  {
    this(context, attrs, 0);
  }

  public DoubleTroublePanel(Context context)
  {
    this(context, null);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    if (thread != null)
    {
      //Game loop pauses until screen is touched
      thread.setWaitForStart(false);
    }

    /*
      if Event.Action_Down // Starting touch
        if it lands on a player
          thatplayer.setPointerId(event.getPointerID(event.getActionIndex()));
      else
        if Event.Action_Pointer_Down && player1ID != null && and it lands on the other player
          thatplayer.setPointerId(event.getPointerID(event.getActionIndex()));

      else
        if Event.action_move
          if player1.getPointerID != -1 and player2.getPointerID != -1 //Two fingers are down on players
            player1.handmultitouchevent()
            player2.handlemultiouchevent()

     */
    switch (event.getActionMasked()){
      case MotionEvent.ACTION_DOWN:

      case MotionEvent.ACTION_POINTER_DOWN: {
        // TODO use data
        break;
      }
      case MotionEvent.ACTION_MOVE: { // a pointer was moved
        // TODO use data
        break;
      }
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
    }

    Log.d(TAG, "STATS: Event Type: " + event.toString());
    return true;
  }

  @Override
  public void drawPlayers(Canvas canvas){
    for(int i = 0; i < players.size(); i++){
      players.get(i).drawBall(canvas);
    }
  }

  @Override
  public void checkPlayerBallCollision(){

    Ball ball = entities.get(0);

    //TODO make this more accurate because its not working well for Double trouble
    //adds the radius of both the player and ball to find the minimum distance
    int minimumDistance = player.getBitmapWidth() / 2  + ball.getBitmapWidth() / 2;
    minimumDistance = minimumDistance * minimumDistance;

    for(int i = 0; i < players.size(); i++)
    {
      for (int j = 0; j < entities.size(); j++)
      {
        ball = entities.get(j);
        if (players.get(i).checkBallCollision(ball, minimumDistance))
        {
          gameActivity.playSound("GAME_OVER_SOUND");
          //TODO uncomment this line
          //endGame();
          break;
        }
      }
    }
  }
}
