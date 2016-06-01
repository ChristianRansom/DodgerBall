package edu.masters.cs498.ransomca.dodgerball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by user1 on 12/10/15.
 */
public class CS498Activity extends Activity implements View.OnClickListener, SensorEventListener
{


  private SensorManager mSensorManager;
  private Sensor mAccelerometer;
  private ImageView mAnimateImage;
  private Button button;
  private boolean hasMoved = false;
  private Button centerButton;
  private RelativeLayout layout;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.cs498_activity);
    mAnimateImage = (ImageView)findViewById(R.id.coin);
    mAnimateImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.head));
    button = (Button) findViewById(R.id.doAnimation);
    button.setOnClickListener(this);
    centerButton = (Button) findViewById(R.id.centerButton);
    centerButton.setOnClickListener(this);
    layout = (RelativeLayout) findViewById(R.id.layout);



    //Set up sensors
    mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

  }

  protected void onResume() {
    super.onResume();
    mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
  }



  protected void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(this);
  }

  @Override
  public void onClick(View v)
  {

    if(v.getId() == R.id.doAnimation)
    {
      if (hasMoved)
      {
        mAnimateImage.animate().rotation(360f);
        hasMoved = false;
      } else
      {
        mAnimateImage.animate().rotation(-360f);
        hasMoved = true;
      }
    }
    else if(v.getId() == R.id.centerButton){
      mAnimateImage.setX(layout.getWidth()/2 - mAnimateImage.getWidth()/2);
      mAnimateImage.setY(layout.getHeight()/2 - mAnimateImage.getHeight()/2);
    }

  }

  public void onSensorChanged(SensorEvent event) {
    final float[] values = event.values;
    float x = values[0] / 10;
    float y = values[1] / 10;
    int scaleFactor;
    if(x > 0) {
      mAnimateImage.setY(mAnimateImage.getY() + 10);
    } else {
      mAnimateImage.setY(mAnimateImage.getY() - 10);
    }
    if (y > 0)
    {
      mAnimateImage.setX(mAnimateImage.getX() + 10);
    } else
    {
      mAnimateImage.setX(mAnimateImage.getX() - 10);
    }
  }

  @Override
  public void onBackPressed() {


    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            this);

    // set title
    alertDialogBuilder.setTitle("You can't leave my App!");

    // set dialog message
    alertDialogBuilder
            .setMessage("Do you want to leave the CS498 Activity?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int id)
              {

                printToastmsg();
              }
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
    //Call super to do normal processing (like finishing Activity)
    //super.onBackPressed();

  }

  private void printToastmsg()
  {
    Toast.makeText(this, "That was not the droid you were looking for", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {

  }
}
