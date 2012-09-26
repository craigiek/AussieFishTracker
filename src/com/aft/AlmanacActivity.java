package com.aft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.aft.R.id;
import org.achartengine.GraphicalView;

public class AlmanacActivity
  extends Activity
{
  @Override
  public void onCreate( final Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    refreshGraph();
  }

  @Override
  public void onStart()
  {
    super.onStart();
    refreshGraph();
  }

  @Override
  public void onResume()
  {
    super.onResume();
    refreshGraph();
  }

  private void refreshGraph()
  {
    Bundle extras = this.getIntent().getExtras();
    final String current_location = extras.getString( MainActivity.CURRENT_LOCATION );
    setContentView( R.layout.almanac_layout );
    //final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    final GraphicalView graph = new LineGraph( this, current_location ).getView();
    final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    layout.removeAllViews();
    layout.addView( graph );
  }

}
