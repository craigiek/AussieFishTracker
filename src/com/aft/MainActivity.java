package com.aft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.LinearLayout;
import com.aft.R.array;
import com.aft.R.id;
import com.aft.R.menu;
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

  @Override
  public boolean onCreateOptionsMenu( Menu menu )
  {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate( R.menu.main_menu, menu );
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu( Menu menu )
  {
    final MenuItem locationsMenu = menu.getItem( 0 );
    final SubMenu subMenu = locationsMenu.getSubMenu();
    subMenu.clear();
    // todo get locations from a file which can be added to, removed from by the user
    final String[] stringArray = getResources().getStringArray( array.locations );
    for ( final String location : stringArray )
    {
      subMenu.add( location );
    }
    return super.onPrepareOptionsMenu( menu );
  }


}
