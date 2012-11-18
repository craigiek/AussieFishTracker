package com.aft.catches;

import android.R.color;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.aft.R.array;
import com.aft.R.id;
import com.aft.R.layout;
import com.aft.util.ImageHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewCatchActivity
  extends Activity
  implements OnClickListener
{
  //variable for selection intent
  private final int PICKER = 1;
  // return codes
  private final int CANCEL = 0;
  private final int OK = 1;

  public static final String SPECIES = "species";
  public static final String WEIGHT = "weight";
  public static final String LOCATION = "location";
  public static final String DATE = "date";
  public static final String IMAGE = "image";

  private AutoCompleteTextView _species;
  private EditText _weight;
  private EditText _location;
  private String _imagePath;
  private ImageView _addPhotoImage;
  private Button _cancelButton;
  private Button _doneButton;


  public void onCreate( final Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( layout.new_catch );
    _species = (AutoCompleteTextView) findViewById( id.new_catch_species );
    _weight = (EditText) findViewById( id.new_catch_weight );
    _location = (EditText) findViewById( id.new_catch_location );
    _addPhotoImage = (ImageView) findViewById( id.picture );
    _cancelButton = (Button) findViewById( id.new_catch_cancel_button );
    _doneButton = (Button) findViewById( id.new_catch_done_button );

    _addPhotoImage.setOnClickListener( this );
    _cancelButton.setOnClickListener( this );
    _doneButton.setOnClickListener( this );

    final String[] species = getResources().getStringArray( array.species_list );
    ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, species );
    _species.setAdapter( adapter );
  }

  public void onClick( final View v )
  {
    if ( v.getId() == id.picture )
    {
      // Camera.
      final List<Intent> cameraIntents = new ArrayList<Intent>();
      final Intent captureIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
      final PackageManager packageManager = getPackageManager();
      final List<ResolveInfo> listCam = packageManager.queryIntentActivities( captureIntent, 0 );
      for ( final ResolveInfo res : listCam )
      {
        final String packageName = res.activityInfo.packageName;
        final Intent intent = new Intent( captureIntent );
        intent.setComponent( new ComponentName( res.activityInfo.packageName, res.activityInfo.name ) );
        intent.setPackage( packageName );
        cameraIntents.add( intent );
      }

      final Intent pickIntent = new Intent();
      pickIntent.setType( "image/*" );
      pickIntent.setAction( Intent.ACTION_GET_CONTENT );

      // Chooser of filesystem/Gallery options.
      final Intent chooserIntent = Intent.createChooser( pickIntent, "Select Source" );
      // Add the camera options.
      chooserIntent.putExtra( Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray( new Parcelable[]{ } ) );

      startActivityForResult( chooserIntent, PICKER );
      //we will handle the returned data in onActivityResult
    }
    else if ( v.getId() == id.new_catch_cancel_button )
    {
      final Intent in = new Intent();
      setResult( RESULT_CANCELED, in );
      finish();
    }
    else if ( v.getId() == id.new_catch_done_button )
    {
      float weight = Float.valueOf( _weight.getText().toString() );
      final Intent in = new Intent();
      in.putExtra( SPECIES, _species.getText().toString() );
      in.putExtra( WEIGHT, weight );
      in.putExtra( LOCATION, _location.getText().toString() );
      in.putExtra( DATE, new Date().getTime() );
      in.putExtra( IMAGE, _imagePath );
      setResult( RESULT_OK, in );
      finish();
    }
  }

  protected void onActivityResult( final int requestCode, final int resultCode, final Intent data )
  {
    if ( resultCode == RESULT_OK )
    {
      //check if we are returning from picture selection
      if ( requestCode == PICKER )
      {
        //import the image
        //the returned picture URI
        final Uri pickedUri = data.getData();
        //declare the path string
        String imgPath = "";
        //retrieve the string using media data
        String[] medData = { MediaStore.Images.Media.DATA };
        //query the data
        final Cursor picCursor = managedQuery( pickedUri, medData, null, null, null );
        if ( picCursor != null )
        {
          //get the path string
          int index = picCursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
          picCursor.moveToFirst();
          imgPath = picCursor.getString( index );
        }
        else
        {
          imgPath = pickedUri.getPath();
        }

        //if we have a new URI attempt to decode the image bitmap
        if ( pickedUri != null )
        {
          Log.e("----uri = " + pickedUri.toString(), "----imagepath = " + imgPath.toString());
          _imagePath = imgPath;
          ImageHelper.setImage( _addPhotoImage, _addPhotoImage.getWidth(), _addPhotoImage.getHeight(), imgPath, color.white );
        }
      }
    }
    //superclass method
    super.onActivityResult( requestCode, resultCode, data );
  }
}