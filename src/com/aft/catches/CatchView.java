package com.aft.catches;

import android.content.Context;
import android.R.color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aft.R;
import com.aft.R.drawable;
import com.aft.R.id;
import com.aft.R.layout;
import com.aft.util.ImageHelper;
import java.io.File;
import java.text.SimpleDateFormat;

// should extend the root layout view of your widget
public class CatchView
  extends LinearLayout
  implements Checkable
{
  private final SimpleDateFormat _formatter;


  private TextView _speciesText;
  private TextView _weightText;
  private TextView _dateText;
  private TextView _locationText;
  private ImageView _imageView;

  private Catch _catch;

  private boolean _expanded;

  // references to our images
  private Integer[] _thumbIDs =
    {
      drawable.snapper,
      drawable.skate,
      drawable.trout,
    };

  /**
   * Basic Constructor that takes only takes in an application Context.
   *
   * @param context The application Context in which this view is being added.
   * @param fish    The Catch this view is responsible for displaying.
   */
  public CatchView( final Context context, final Catch fish )
  {
    super( context );
    _formatter = new SimpleDateFormat( context.getResources().getString( R.string.date_caught_format ) );

    final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    inflater.inflate( layout.catch_view, this, true );

    _speciesText = (TextView) findViewById( id.catch_species_text );
    _speciesText.setShadowLayer(
      5f,   //float radius
      10f,  //float dx
      10f,  //float dy
      0xFFffffff //int color
    );

    _weightText = (TextView) findViewById( id.catch_weight_text );
    _dateText = (TextView) findViewById( id.catch_date_text );
    _locationText = (TextView) findViewById( id.catch_location_text );
    _imageView = (ImageView) findViewById( id.catch_image );

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
    final String weightText = Double.toString( fish.getWeight() ) + "kg";
    final String dateString = _formatter.format( fish.getDateCaught() );

    final StringBuilder description = new StringBuilder( weightText );
    description.append( " " ).append( fish.getSpecies() ).append( ", caught " ).append( dateString );

    _speciesText.setText( fish.getSpecies() );
    _dateText.setText( dateString );
    _weightText.setText( weightText );
    _locationText.setText( fish.getLocation() );

    _imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
    _imageView.setPadding( 50, 0, 0, 0 );
    _imageView.setContentDescription( description.toString() );

    final StringBuilder path = new StringBuilder( Environment.getExternalStorageDirectory().toString() );
    path.append( "/DCIM/" );

    if ( fish.getSpecies().equalsIgnoreCase( "snapper" ) )
    {
      path.append( "grand_teton_sunset.jpg" );
    }
    else if ( fish.getSpecies().equalsIgnoreCase( "skate" ) )
    {
      path.append( "another_world.jpg" );
    }
    else if ( fish.getSpecies().equalsIgnoreCase( "trout" ) )
    {
      path.append( "shasta_lavender.jpg" );
    }

    ImageHelper.setImage( _imageView, path.toString(), color.white );
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
    //_locationText.setVisibility( VISIBLE );
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
    //_locationText.setVisibility( INVISIBLE );
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
