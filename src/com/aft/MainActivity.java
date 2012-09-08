package com.aft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.LinearLayout;
import com.aft.R.array;
import com.aft.R.id;
import org.achartengine.GraphicalView;

public class MainActivity
  extends Activity
{
  private String _currentLocation = "Warneet";

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );
    refreshGraph();
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu )
  {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate( R.menu.main_menu, menu );
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu( final Menu menu )
  {
    final MenuItem locationsMenu = menu.getItem( 0 );
    final SubMenu subMenu = locationsMenu.getSubMenu();
    subMenu.clear();
    // todo get locations from a file which can be added to, removed from by the user
    final String[] allLocations = getResources().getStringArray( array.locations );
    for ( final String location : allLocations )
    {
      subMenu.add( location );
    }
    return super.onPrepareOptionsMenu( menu );
  }

  @Override
  public boolean onOptionsItemSelected( final MenuItem item )
  {
    if ( item.getItemId() == id.menu_refresh )
    {
      refreshGraph();
    }
    else
    {
      // todo set new location and refresh the graph
      // refreshGraph();
    }
    return true;
  }

  private void refreshGraph()
  {
    final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    layout.removeAllViews();
    layout.addView( graph );
  }
}
