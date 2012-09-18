package com.aft;

import android.R.layout;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.aft.R.array;
import com.aft.R.id;
import com.aft.R.menu;
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
    final ActionBar actionBar = getActionBar();
    actionBar.setDisplayShowTitleEnabled( false );
    //actionBar.setDisplayShowHomeEnabled( false );
    actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_LIST );
    //actionBar.setHomeButtonEnabled( true );
    //actionBar.setDisplayUseLogoEnabled( true );

    inflater.inflate( R.menu.main_menu, menu );

    // Add the spinner for the locations
    final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                                                                                     array.locations,
                                                                                     layout.simple_spinner_item );
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    getActionBar().setListNavigationCallbacks( adapter, new OnNavigationListener()
    {
      public boolean onNavigationItemSelected( final int itemPosition, final long itemId )
      {
        _currentLocation = adapter.getItem( itemPosition ).toString();
        refreshGraph();
        return false;
      }
    } );

    return super.onCreateOptionsMenu( menu );
  }

  @Override
  public boolean onOptionsItemSelected( final MenuItem item )
  {
    if ( item.getItemId() == id.menu_refresh )
    {
      refreshGraph();
      return true;
    }
    else if ( item.getItemId() == id.menu_edit_locations )
    {
      // todo
    }
    else if ( item.getItemId() == id.menu_settings )
    {
      // todo
    }
    return super.onOptionsItemSelected( item );
  }

  private void refreshGraph()
  {
    final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    layout.removeAllViews();
    layout.addView( graph );
  }
}
