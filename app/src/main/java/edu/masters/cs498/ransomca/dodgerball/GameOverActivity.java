package edu.masters.cs498.ransomca.dodgerball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by user1 on 11/30/15.
 */
public class GameOverActivity extends Activity implements View.OnClickListener
{
  private Button newGameButton;
  private Button menuButton;
  private Button resetHighScore;
  private SharedPreferences formStore;
  private TextView scoreView;
  private final String MY_FILE = "MyGameFile";
  private TextView highScoreView;
  final Context context = this;
  private String gameMode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.game_over_activity);

    newGameButton = (Button) findViewById(R.id.newGame);
    menuButton = (Button) findViewById(R.id.menu);
    resetHighScore = (Button) findViewById(R.id.resetHighScore);
    newGameButton.setOnClickListener(this);
    menuButton.setOnClickListener(this);

    scoreView = (TextView) findViewById(R.id.score);
    highScoreView = (TextView) findViewById(R.id.highScore);

    //Retrieve or create the preferences object
    formStore = getSharedPreferences(MY_FILE, Activity.MODE_PRIVATE);
    gameMode = formStore.getString("gamemode", "NOTHING");

    int playerScore = formStore.getInt(gameMode +  "score", 100);
    int highScore = formStore.getInt(gameMode + "highScore", 0);
    if(playerScore > highScore)

    {
      highScore = playerScore;
    }

    SharedPreferences.Editor editor = formStore.edit();
    editor.putInt(gameMode + "highScore", highScore);
    editor.commit();
    scoreView.setText(Integer.toString(playerScore));
    highScoreView.setText(Integer.toString(highScore));



    resetHighScore.setOnClickListener(new View.OnClickListener()
    {

      @Override
      public void onClick(View arg0)
      {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Warning");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to reset the high score?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface dialog, int id)
                  {
                    // if this button is clicked, close
                    // current activity
                    SharedPreferences.Editor editor = formStore.edit();
                    editor.putInt(gameMode + "highScore", 0);
                    editor.commit();
                    highScoreView.setText(Integer.toString(0));                  }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface dialog, int id)
                  {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                  }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
      }
    });
  }

  @Override
  public void onClick(View v)
  {
    Intent intent;
    switch (v.getId()) {
      case R.id.newGame:
        intent= new Intent(GameOverActivity.this,GameActivity.class);
        startActivity(intent);
        this.finish();
        break;
      case R.id.menu:
        intent = new Intent(GameOverActivity.this, MainMenuActivity.class);
        startActivity(intent);
        this.finish();
        break;
      default:
        break;
    }
  }
}
