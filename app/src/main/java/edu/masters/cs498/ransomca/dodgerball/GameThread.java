package edu.masters.cs498.ransomca.dodgerball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by user1 on 12/5/15.
 */
public class GameThread extends MainThread
{

  private DefaultGamePanel defaultGamePanel;
  private boolean waitForStart = true;


  public GameThread(SurfaceHolder holder, DefaultGamePanel defaultGamePanelamePanel)
  {
    super(holder, defaultGamePanelamePanel);
    this.defaultGamePanel = defaultGamePanelamePanel;
  }

  @Override
  protected void waitForStart()
  {
    //Waits for  a touch on the screen before game loop begins
    while (waitForStart) {}
  }

  @Override
  public void initializeScreen()
  {
    Canvas canvas = null;
    try
    {
      canvas = this.surfaceHolder.lockCanvas(null);
      synchronized (surfaceHolder)
      {
        super.render(canvas);
      }
    } finally
    {
      if (canvas != null)
      {
        surfaceHolder.unlockCanvasAndPost(canvas);
      }
    }
    initializeBalls();
  }

  private void initializeBalls()
  {
    gamePanel.initializeBalls();
  }


  public void setWaitForStart(boolean waitForStart)
  {
    this.waitForStart = waitForStart;
  }


}
