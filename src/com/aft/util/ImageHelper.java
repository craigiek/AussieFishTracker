package com.aft.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageHelper
{
  public static Bitmap getRoundedCornerBitmap( final Bitmap bitmap, final int pixels )
  {
    Bitmap output = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888 );
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

  public static void setImage( final ImageView view,
                               final int maxWidth,
                               final int maxHeight,
                               final String imagePath,
                               final int background )
  {
    final File f = new File( imagePath );
    if ( !f.exists() )
    {
      // todo load a different image
      Log.e( "Missing file", "Cannot find file " + imagePath );
      return;
    }
    final Bitmap bitmap = createScaledImage( f, maxWidth, maxHeight);
    view.setImageBitmap( bitmap );
    view.setBackgroundColor( background );
  }

  private static Bitmap createScaledImage( final File f, final int maxWidth, final int maxHeight )
  {
    Bitmap bitmap = null;
    FileInputStream fis = null;
    try
    {
      fis = new FileInputStream( f );
      final BitmapFactory.Options o = new BitmapFactory.Options();
      o.inPurgeable = true;
      o.inInputShareable = true;
      final Bitmap original = BitmapFactory.decodeFileDescriptor( fis.getFD(), null, o );
      bitmap = Bitmap.createScaledBitmap(original, maxWidth, maxHeight, false);
    }
    catch ( IOException e )
    {
      Log.e("IOException", "Looking for " + f.getAbsolutePath(), e );
    }
    finally
    {
      if ( fis != null )
      {
        try
        {
          fis.close();
        }
        catch ( IOException e )
        {
          Log.e("Error closing FileInputStream", "Error closing FIS for " + f.getPath(), e );
        }
      }
    }
    return bitmap;
  }
}