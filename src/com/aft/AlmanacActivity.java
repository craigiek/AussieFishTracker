package com.aft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.aft.R.id;
import org.achartengine.GraphicalView;

public class AlmanacActivity
  extends Activity
{
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.almanac_layout );
    refreshGraph();
  }

  private void refreshGraph()
  {
    //final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    final GraphicalView graph = new LineGraph( this, "Warneet" ).getView();
    final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    layout.removeAllViews();
    layout.addView( graph );
  }

}
