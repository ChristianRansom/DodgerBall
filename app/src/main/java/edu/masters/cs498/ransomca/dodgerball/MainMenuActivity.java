package edu.masters.cs498.ransomca.dodgerball;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainMenuActivity extends Activity implements View.OnClickListener
{

  private Button startButton;
  private Spinner gameMode;
  private Button quitButton;
  private SharedPreferences formStore;
  private final String MY_FILE = "MyGameFile";


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_menu_activity);
    startButton = (Button)findViewById(R.id.start);

    gameMode = (Spinner)findViewById(R.id.changeGamemode);
    String[] items = new String[]{"Regular", "Slow Mo", "Double Trouble"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
    gameMode.setAdapter(adapter);

    quitButton = (Button)findViewById(R.id.quit);
    startButton.setOnClickListener(this);
    quitButton.setOnClickListener(this);
  }


  @Override
  public void onClick(View v) {
    Intent intent;
    formStore = getSharedPreferences(MY_FILE, Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = formStore.edit();
    switch (v.getId()) {
      case R.id.start:
        switch(String.valueOf(gameMode.getSelectedItem())){
          case "Regular":
            editor.putString("gamemode", "Regular");
            intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);
            this.finish();
            break;
          case "Slow Mo":
            editor.putString("gamemode", "Slow Mo");
            intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);
            this.finish();
            break;
          case "Double Trouble":
            editor.putString("gamemode", "Double Trouble");
            intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);
            break;
          /*case "CS498":
            intent = new Intent(MainMenuActivity.this, CS498Activity.class);
            startActivity(intent);
            break;
            */
          default:
            break;
        }
        editor.commit();
        break;
      case R.id.quit:
        this.finish();
        break;
      default:
        break;
    }
  }
}
