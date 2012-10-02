package com.aft.catches;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import java.util.List;

public class CatchListAdapter
  extends BaseAdapter
{

  /**
   * The application Context in which this CatchListAdapter is being used.
   */
  private Context _context;

  /**
   * The dataset to which this CatchListAdapter is bound.
   */
  private List<Catch> _catchList;

  /**
   * The position in the dataset of the currently selected Catch.
   */
  private int _selectedPosition;

  /**
   * Parameterized constructor that takes in the application Context in which
   * it is being used and the Collection of Catch objects to which it is bound.
   * _selectedPosition will be initialized to Adapter.NO_SELECTION.
   *
   * @param context   The application Context in which this CatchListAdapter is being
   *                  used.
   * @param catchList The Collection of Catch objects to which this CatchListAdapter
   *                  is bound.
   */
  public CatchListAdapter( final Context context, final List<Catch> catchList )
  {
    _context = context;
    _catchList = catchList;
    _selectedPosition = Adapter.NO_SELECTION;
  }

  /**
   * Accessor method for retrieving the position in the dataset of the
   * currently selected Catch.
   *
   * @return an integer representing the position in the dataset of the
   *         currently selected Catch.
   */
  public int getSelectedPosition()
  {
    return _selectedPosition;
  }

  public int getCount()
  {
    return _catchList.size();
  }

  public Object getItem( final int position )
  {
    if ( position > _catchList.size() - 1 )
    {
      return null;
    }
    else
    {
      return _catchList.get( position );
    }
  }

  public long getItemId( final int position )
  {
    return position;
  }

  public View getView( final int position, final View convertView, final ViewGroup parent )
  {
    final Catch fish = (Catch) getItem( position );
    if ( convertView != null )
    {
      ( (CatchView) convertView ).setCatch( fish );
      return convertView;
    }
    else
    {
      return new CatchView( _context, fish );
    }
  }
}
