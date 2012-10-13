package com.aft.catches;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.aft.R;
import com.aft.R.array;
import com.aft.R.id;
import com.aft.R.layout;

public class NewCatchActivity
  extends Activity
  implements OnClickListener
{
  //variable for selection intent
  private final int PICKER = 1;
  //variable to store the currently selected image
  private int currentPic = 0;
  //image view for larger display
  private ImageView _picView;

  private AutoCompleteTextView _species;
  private EditText _weight;
  private EditText _location;
  private ImageView _addPhotoImage;


  public void onCreate( final Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( layout.add_new_catch );
    //get the large image view
    //_picView = (ImageView) findViewById(R.id.picture);
    _species = (AutoCompleteTextView)findViewById( id.new_catch_species );
    _weight = (EditText)findViewById( id.new_catch_weight );
    _location = (EditText)findViewById( id.new_catch_location );
    _addPhotoImage = (ImageView)findViewById( id.picture);
    _addPhotoImage.setOnClickListener( this );

    final String[] species = getResources().getStringArray( array.species_list);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, species);
    _species.setAdapter( adapter );
  }

  public void onClick( final View v )
  {
    if ( v.getId() == id.picture )
    {
      // todo kjd - launch 'add photo Camera/Gallery options'
      Toast.makeText( this, "Add new photo clicked", Toast.LENGTH_SHORT );
    }
  }
}