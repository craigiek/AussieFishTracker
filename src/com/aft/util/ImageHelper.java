package com.aft.util;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.widget.ImageView;

public class ImageHelper
{
  public static Bitmap getRoundedCornerBitmap( final Bitmap bitmap, final int pixels )
  {
    Bitmap output = Bitmap.createBitmap( bitmap.getWidth(), bitmap
      .getHeight(), Config.ARGB_8888 );
    Canvas canvas = new Canvas( output );

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect( 0, 0, bitmap.getWidth(), bitmap.getHeight() );
    final RectF rectF = new RectF( rect );
    final float roundPx = pixels;

    paint.setAntiAlias( true );
    canvas.drawARGB( 0, 0, 0, 0 );
    paint.setColor( color );
    canvas.drawRoundRect( rectF, roundPx, roundPx, paint );

    paint.setXfermode( new PorterDuffXfermode( Mode.SRC_IN ) );
    canvas.drawBitmap( bitmap, rect, rect, paint );

    return output;
  }

  public static void setImage( final ImageView view, final Uri uri, final String imagePath, final int background )
  {
    //set the width and height we want to use as maximum display
    int targetWidth = view.getWidth();
    int targetHeight = view.getHeight();;
    //create bitmap options to calculate and use sample size
    final BitmapFactory.Options bmpOptions = new BitmapFactory.Options();

    //first decode image dimensions only - not the image bitmap itself
    bmpOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile( imagePath, bmpOptions );

    //image width and height before sampling
    int currHeight = bmpOptions.outHeight;
    int currWidth = bmpOptions.outWidth;

    //variable to store new sample size
    int sampleSize = 1;

    //calculate the sample size if the existing size is larger than target size
    if ( currHeight > targetHeight || currWidth > targetWidth )
    {
      //use either width or height
      if ( currWidth > currHeight )
      {
        sampleSize = Math.round( (float) currHeight / (float) targetHeight );
      }
      else
      {
        sampleSize = Math.round( (float) currWidth / (float) targetWidth );
      }
    }
    //use the new sample size
    bmpOptions.inSampleSize = sampleSize;

    //now decode the bitmap using sample options
    bmpOptions.inJustDecodeBounds = false;

    //get the file as a bitmap
    final Bitmap pic = BitmapFactory.decodeFile(imagePath, bmpOptions);

    view.setImageBitmap( pic );
    view.setBackgroundColor( background );
  }
}