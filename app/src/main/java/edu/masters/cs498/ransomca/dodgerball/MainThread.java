package edu.masters.cs498.ransomca.dodgerball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public abstract class MainThread extends Thread
{

  protected SurfaceHolder surfaceHolder;
  protected GamePanel gamePanel;
  private boolean run = false;

  private long FRAME_PERIOD = 20;

  //for debugging purposes
  private static final String TAG = MainThread.class.getSimpleName();


  public MainThread(SurfaceHolder holder, GamePanel gamePanel)
  {
    surfaceHolder = holder;
    this.gamePanel = gamePanel;
  }

  public void run()
  {

    long beginTime;//time the thread started
    long timeDiff;//the time it took for the cycle to execute
    long sleepTime;//Time the thread needs to wait to maintain the constant FPS

    //renders the screen once to show initial positions
    initializeScreen();

    //Waits for  a touch on the screen before game loop begins
    waitForStart();

    while (run)
    {
      Canvas canvas = null;
      try
      {
        canvas = surfaceHolder.lockCanvas(null);
        synchronized (surfaceHolder)
        {
          render(canvas);
          update(canvas);

          beginTime = System.currentTimeMillis();
          timeDiff = System.currentTimeMillis() - beginTime;//How long the cycle took
          sleepTime = FRAME_PERIOD - timeDiff;
        }
      } finally
      {
        if (canvas != null)
        {
          surfaceHolder.unlockCanvasAndPost(canvas);
        }
      }
      if (sleepTime > 0)
      {
        try
        {
          this.sleep(sleepTime);
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  protected abstract void waitForStart();

  public abstract void initializeScreen();

  public void update(Canvas canvas)
  {
    gamePanel.update(canvas);
  }

  public void render(Canvas canvas)
  {
    gamePanel.onDraw(canvas);
  }

  public void setRunnable(boolean run)
  {
    this.run = run;
  }

}
