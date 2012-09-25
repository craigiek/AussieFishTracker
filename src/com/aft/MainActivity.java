package com.aft;

import android.R.layout;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.aft.R.array;
import com.aft.R.drawable;
import com.aft.R.id;
import com.aft.R.menu;
import org.achartengine.GraphicalView;

public class MainActivity
  extends TabActivity
{
  public static final String CURRENT_LOCATION = "CURRENT_LOCATION";

  private String _currentLocation = "Warneet";
  private Intent _almanacIntent;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );

    TabHost tabHost = getTabHost();

    // Tab for Calendar
    final TabSpec calendarspec = tabHost.newTabSpec( "Calendar" );
    calendarspec.setIndicator( null, getResources().getDrawable( drawable.calendar ) );
    final Intent calendarIntent = new Intent( this, CalendarActivity.class );
    calendarIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
    calendarspec.setContent( calendarIntent );

    // Tab for Almanac
    final TabSpec almanacspec = tabHost.newTabSpec( "Almanac" );
    almanacspec.setIndicator( null, getResources().getDrawable( drawable.almanac ) );
    _almanacIntent = new Intent( this, AlmanacActivity.class );
    _almanacIntent.putExtra( "current_location", _currentLocation );
    _almanacIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
    almanacspec.setContent( _almanacIntent );

    // Tab for Weather
    final TabSpec weatherspec = tabHost.newTabSpec( "Weather" );
    weatherspec.setIndicator( null, getResources().getDrawable( drawable.weather ) );
    final Intent weatherIntent = new Intent( this, WeatherActivity.class );
    weatherIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
    weatherspec.setContent( weatherIntent );

    // Adding all TabSpec to TabHost
    tabHost.addTab( calendarspec ); // Adding calendar tab
    tabHost.addTab( almanacspec ); // Adding almanac tab
    tabHost.addTab( weatherspec ); // Adding weather tab
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
        _almanacIntent.putExtra( CURRENT_LOCATION, _currentLocation );
        refreshGraph();
        return true;
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
    // todo kjd - refresh the graph on the AlmanacLayout
    //final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    //final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    //layout.removeAllViews();
    //layout.addView( graph );
  }
}
