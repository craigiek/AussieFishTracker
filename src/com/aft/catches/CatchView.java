package com.aft.catches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.aft.R;
import com.aft.R.drawable;
import com.aft.R.id;
import com.aft.R.layout;

// should extend the root layout view of your widget
public class CatchView
  extends LinearLayout
  implements Checkable
{

  private Button _expandButton;
  private TextView _speciesText;
  private TextView _weightText;
  private TextView _locationText;
  private Catch _catch;

  private boolean _expanded;

  /**
   * Basic Constructor that takes only takes in an application Context.
   *
   * @param context The application Context in which this view is being added.
   * @param fish    The Catch this view is responsible for displaying.
   */
  public CatchView( final Context context, final Catch fish )
  {
    super( context );
    final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

    inflater.inflate( layout.catch_view, this, true );
    _expandButton = (Button) findViewById( id.expand_button );
    _speciesText = (TextView) findViewById( id.catch_species_text );
    _weightText = (TextView) findViewById( id.catch_weight_text );
    _locationText = (TextView) findViewById( id.catch_location_text );
    setCatch( fish );

    _expanded = false;
  }

  /**
   * Mutator method for changing the Catch object this View displays. This View
   * will be updated to display the correct contents of the new Catch.
   *
   * @param fish The Catch object which this View will display.
   */
  public void setCatch( final Catch fish )
  {
    _catch = fish;
    _speciesText.setText( fish.getSpecies() );
    _weightText.setText( Double.toString( fish.getWeight() ) );
    _locationText.setText( fish.getLocation() );
  }

  /**
   * This method encapsulates the logic necessary to update this view so that
   * it displays itself in its "Expanded" form:
   * - Shows the text of the catch.
   * - Brings the RadioGroup of rating Buttons back into view.
   */
  private void expandCatchView()
  {
    _expanded = true;
    _locationText.setVisibility( VISIBLE );
    _expandButton.setBackgroundResource( drawable.down );
    requestLayout();
  }

  /**
   * This method encapsulates the logic necessary to update this view so that
   * it displays itself in its "Collapsed" form:
   * - Shows only the species, date and weight of the catch.
   * - Removes the RadioGroup of rating Buttons from view.
   */
  private void collapseCatchView()
  {
    _expanded = false;
    _locationText.setVisibility( INVISIBLE );
    _expandButton.setBackgroundResource( drawable.right );
    requestLayout();
  }

  public void setChecked( final boolean checked )
  {
    if ( checked )
    {
      expandCatchView();
    }
    else
    {
      collapseCatchView();
    }
  }

  public boolean isChecked()
  {
    return _expanded;
  }

  public void toggle()
  {
    if ( _expanded )
    {
      collapseCatchView();
    }
    else
    {
      expandCatchView();
    }
  }
}
