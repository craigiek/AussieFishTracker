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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import com.aft.R.array;
import com.aft.R.id;
import com.aft.R.layout;
import com.aft.util.ImageHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewCatchActivity
  extends Activity
  implements OnClickListener
{
  //variable for selection intent
  private final int PICKER = 1;

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
    _species = (AutoCompleteTextView) findViewById( id.new_catch_species );
    _weight = (EditText) findViewById( id.new_catch_weight );
    _location = (EditText) findViewById( id.new_catch_location );
    _addPhotoImage = (ImageView) findViewById( id.picture );
    _addPhotoImage.setOnClickListener( this );

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
          ImageHelper.setImage( _addPhotoImage, imgPath, color.white );
        }
      }
    }
    //superclass method
    super.onActivityResult( requestCode, resultCode, data );
  }
}