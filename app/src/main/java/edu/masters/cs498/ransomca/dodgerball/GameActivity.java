package edu.masters.cs498.ransomca.dodgerball;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class GameActivity extends Activity
{

  private GamePanel gamePanel;
  private SharedPreferences formStore;
  private final String MY_FILE = "MyGameFile";

  //for debugging purposes
  private static final String TAG = GameActivity.class.getSimpleName();

  //sound stuff
  private AudioManager mAudioManager;
  private SoundPool mSoundPool;
  private SparseIntArray mSoundMap;
  private boolean loaded = false;
  private float volume;
  private int newBallSoundId;
  private int gameOverSoundId;
  private RelativeLayout layout;
  private String gameMode;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.game_activity);
    layout = (RelativeLayout) findViewById(R.id.layout);
    addGamePanel();

    //set up sound
    mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    mSoundMap = new SparseIntArray();
    newBallSoundId = mSoundPool.load(this, R.raw.beep, 1);
    gameOverSoundId = mSoundPool.load(this, R.raw.gameoverbeep,1);

    Log.d(TAG, "DEBUG: Stream ID: " + mSoundMap.get(1));

    float streamVolumeCurrent = mAudioManager
            .getStreamVolume(AudioManager.STREAM_MUSIC);
    float streamVolumeMax = mAudioManager
            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    volume = streamVolumeCurrent / streamVolumeMax;

    mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
    {
      @Override
      public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
      {
        loaded = true;
        //soundPool.play(R.id.button_beep1, 20, 20, 1, 0, 1f);
      }
    });
  }

  public void endGame()
  {
    //Retrieve or create the preferences object
    formStore = getSharedPreferences(MY_FILE, Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = formStore.edit();
    editor.putInt(gameMode + "score", gamePanel.getScore());
    editor.commit();
    Intent newIntent = new Intent(this, GameOverActivity.class);
    startActivity(newIntent);
  }

  public void playSound(String sound)
  {

    // and at the standard playback rate
    //mSoundPool.play(streamId, .5f, .5f, 1, 0, 1.0f);
    if (loaded)
    {
      Log.d(TAG,"DEBUG: Sound key received: " + sound);
      switch (sound)
      {
        case "NEW_BALL_SOUND":
          mSoundPool.play(newBallSoundId, volume, volume, 1, 0, 1.0f);
          Log.d(TAG, "DEBUG: PLAY THE FREAKING SOUND");
          Log.d(TAG, "DEBUG: streamID: " + newBallSoundId + " volume: " + volume);
          break;
        case "GAME_OVER_SOUND":
          mSoundPool.play(gameOverSoundId, volume, volume, 1, 0, 1.0f);
          break;
        default:
          break;
      }
    }
  }

  public void addGamePanel(){

    formStore = getSharedPreferences(MY_FILE, Activity.MODE_PRIVATE);
    gameMode = formStore.getString("gamemode", "NOTHING");

    switch(gameMode){
      case "Regular":
        gamePanel = new DefaultGamePanel(this);
        break;
      case "Slow Mo":
        gamePanel = new SlowMoPanel(this);
        break;
      case "Double Trouble":
        gamePanel = new DoubleTroublePanel(this);
        break;
      default:
        break;
    }

    layout.addView(gamePanel);
    gamePanel.sendSelf(this);

    //sends this activity to the GamePanel so it can call the activities methods
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    this.gamePanel.thread.setRunnable(false);
    //finish();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    this.gamePanel.thread = new GameThread(gamePanel.getHolder(), (DefaultGamePanel) gamePanel);

  }

  @Override
  public void onDestroy() {
    mSoundPool.release();
    mSoundPool = null;
    super.onDestroy();
  }

}

