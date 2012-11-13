package com.aft;

import android.R.layout;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.aft.R.array;
import com.aft.R.drawable;
import com.aft.R.id;
import com.aft.catches.Catch;
import com.aft.catches.CatchListAdapter;
import com.aft.catches.NewCatchActivity;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity
  extends TabActivity
  implements OnDrawerCloseListener, OnDrawerOpenListener, OnClickListener
{
  public static final String CURRENT_LOCATION = "CURRENT_LOCATION";

  private String _currentLocation = "Warneet";
  private Intent _almanacIntent;

  protected ArrayList<Catch> _catchList;
  private CatchListAdapter _adapter;


  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate( final Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );

    setUpTabs();

    setUpSlidingDrawer();

    setUpCatchList();
  }

  private void setUpTabs()
  {
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

  private void setUpSlidingDrawer()
  {
    final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById( id.sliding_drawer );
    final Button newCatchButton = (Button) findViewById( id.new_catch_button );

    newCatchButton.setOnClickListener( this );

    slidingDrawer.setOnDrawerOpenListener( this );
    slidingDrawer.setOnDrawerCloseListener( this );
  }

  private void setUpCatchList()
  {
    _catchList = new ArrayList<Catch>();
    _adapter = new CatchListAdapter( this, _catchList );
    final ListView listView = getListView();
    listView.setAdapter( _adapter );
    listView.setChoiceMode( AbsListView.CHOICE_MODE_SINGLE );
    listView.setDividerHeight( 10 );

    final Catch snapper = new Catch( "Snapper", new Date(), 3.5 );
    snapper.setLocation( "Joe's Island" );
    addCatch( snapper );
    addCatch( new Catch( "Skate", new Date(), 1.2 ) );
    addCatch( new Catch( "Trout", new Date(), 0.75 ) );
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
    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
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

  public void onDrawerClosed()
  {
    final Button slider = (Button) findViewById( id.sliding_button );
    slider.setBackgroundResource( R.drawable.up );
  }

  public void onDrawerOpened()
  {
    final Button slider = (Button) findViewById( id.sliding_button );
    slider.setBackgroundResource( R.drawable.down );
  }

  public void onClick( final View v )
  {
    if ( v.getId() == id.new_catch_button )
    {
      final Intent newCatchIntent = new Intent( this, NewCatchActivity.class );
      startActivity( newCatchIntent );
    }
  }

  private void refreshGraph()
  {
    // todo kjd - refresh the graph on the AlmanacLayout
    //final GraphicalView graph = new LineGraph( this, _currentLocation ).getView();
    //final LinearLayout layout = (LinearLayout) findViewById( id.chart );
    //layout.removeAllViews();
    //layout.addView( graph );
  }

  protected void addCatch( final Catch newCatch )
  {
    _catchList.add( newCatch );
    _adapter.notifyDataSetChanged();
  }

  private ListView getListView()
  {
    return (ListView)findViewById( id.catch_list );
  }

}
