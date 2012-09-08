package com.aft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.aft.R.id;
import org.achartengine.GraphicalView;

public class MainActivity
  extends Activity
{
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );
    //final Resources resources = this.getResources();
    //final String[] locations = resources.getStringArray( array.locations );
    final GraphicalView graph = new LineGraph().getView( this );
    final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    layout.addView( graph );
  }
}
