package edu.masters.cs498.ransomca.dodgerball;


/**
 * Created by user1 on 11/22/15.
 */
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


  private static final String TAG = GamePanel.class.getSimpleName();
  protected GameThread thread = null;
  protected GameActivity gameActivity;
  protected int score = 0;

  //XML constructor with style
  public GamePanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    score = 0;
  }

  //XML Constructor
  public GamePanel(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  //java constructor
  public GamePanel(Context context) {
    this(context, null);
  }

  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    //for screen changes. But this game is locked to prevent screen changes, so this is unnecessary.
  }

  public void surfaceCreated(SurfaceHolder holder)
  {
    thread.setRunnable(true);
    thread.start();
  }

  public void surfaceDestroyed(SurfaceHolder holder) {}

  public abstract void endGame();

  protected abstract void onDraw(Canvas canvas);

  public abstract boolean onTouchEvent(MotionEvent event);

  public abstract void update(Canvas canvas);

  public void sendSelf(GameActivity gameActivity)
  {
    this.gameActivity = gameActivity;
  }

  public int getScore()
  {
    return score;
  }

  public abstract void addBall();

  public abstract void initializeBalls();
}
