package com.aft;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import com.aft.R.string;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineGraph
{
  private final static int TIME_POINTS = 48;
  private Context _context;
  private String _location;

  public LineGraph( final Context context, final String location )
  {
    _context = context;
    _location = location;
  }

  public GraphicalView getView()
  {
    final XYMultipleSeriesRenderer chartRenderer = new XYMultipleSeriesRenderer();
    chartRenderer.setPanEnabled( false, false );
    chartRenderer.setAxisTitleTextSize( 20.0f );
    chartRenderer.setZoomButtonsVisible( true );

    // background colours
    chartRenderer.setApplyBackgroundColor( true );
    // the following are to get transparency
    chartRenderer.setBackgroundColor( Color.argb( 0x00, 0x01, 0x01, 0x01 ) );
    chartRenderer.setMarginsColor( Color.argb( 0x00, 0x01, 0x01, 0x01 ) );

    // colours
    chartRenderer.setAxesColor( Color.BLACK );
    chartRenderer.setXLabelsColor( Color.BLACK );
    chartRenderer.setYLabelsColor( 0, Color.BLACK );
    chartRenderer.setYLabelsAlign( Align.RIGHT );
    chartRenderer.setLabelsColor( Color.BLACK );

    // titles
    chartRenderer.setChartTitle( getChartTitle() );
    chartRenderer.setYTitle( _context.getResources().getString( R.string.chart_y_title ) );
    chartRenderer.setLegendTextSize( 30.0f );
    chartRenderer.setLabelsTextSize( 20.0f );
    chartRenderer.setAxisTitleTextSize( 30.0f );
    chartRenderer.setChartTitleTextSize( 30.0f );
    final int[] margins = chartRenderer.getMargins();
    chartRenderer.setMargins( new int[]{margins[0]+40,margins[1]+30,margins[2]+40,margins[3]} );
    //chartRenderer.setPanLimits( ???? );

    final XYMultipleSeriesDataset seriesList = new XYMultipleSeriesDataset();
    final TimeSeries tidesSeries = getTidesSeries();
    seriesList.addSeries( tidesSeries );
    seriesList.addSeries( getCurrentTimeSeries( tidesSeries ) );

    final XYSeriesRenderer tidesRenderer = new XYSeriesRenderer();
    final XYSeriesRenderer currentTimeRenderer = new XYSeriesRenderer();
    currentTimeRenderer.setColor( Color.rgb( 0, 128, 0) );
    chartRenderer.addSeriesRenderer( tidesRenderer );
    chartRenderer.addSeriesRenderer( currentTimeRenderer );

    final String format = _context.getResources().getString( R.string.chart_time_format );
    final GraphicalView lineChartView = ChartFactory.getTimeChartView( _context, seriesList, chartRenderer, format );
    return lineChartView;
  }

  // gets the vertical line showing 'now'
  private TimeSeries getCurrentTimeSeries( final TimeSeries tidesSeries )
  {
    final TimeSeries currentTimeSeries = new TimeSeries( _context.getResources().getString( string.current_time_series_title ) );
    // for some reason we need to change the x coordinate or we don't get a line - change it by 1 millisecond
    final Date now = new Date();
    final Calendar cal = Calendar.getInstance();
    cal.setTime( new Date() );
    cal.add( Calendar.MILLISECOND, 1 );
    final Date then = cal.getTime();
    currentTimeSeries.add( now, tidesSeries.getMinY() );
    currentTimeSeries.add( then, tidesSeries.getMaxY() );
    return currentTimeSeries;
  }

  // the wavy line showing the tides cycle
  private TimeSeries getTidesSeries()
  {
    final Date[] xTimes = getXCoordinates();
    final double[] yTidesCycle = getWaveYValues();

    final TimeSeries waveCycleSeries = new TimeSeries( _context.getResources().getString( string.chart_tides_series_title ) );
    for ( int i = 0; i < xTimes.length; i++ )
    {
      waveCycleSeries.add( xTimes[ i ], yTidesCycle[ i ] );
    }
    return waveCycleSeries;
  }

  private String getChartTitle()
  {
    final SimpleDateFormat formatter = new SimpleDateFormat( _context.getResources().getString(
      string.chart_title_date_format ));
    return formatter.format( new Date() );
  }

  private Date[] getXCoordinates()
  {
    final Date[] data = new Date[ TIME_POINTS ];
    final Calendar cal = Calendar.getInstance();
    cal.setTime( new Date() );
    cal.set( Calendar.HOUR_OF_DAY, 0 );
    cal.set( Calendar.MINUTE, 0 );
    cal.set( Calendar.SECOND, 0 );
    cal.set( Calendar.MILLISECOND, 0 );
    // now at midnight start of today

    for ( int i = 0; i < TIME_POINTS; i++ )
    {
      cal.add( Calendar.MINUTE, 30 );
      data[ i ] = cal.getTime();
    }
    return data;
  }

  private double[] getWaveYValues()
  {
    final double[] data = new double[ TIME_POINTS ];
    double start = 0.0;
    boolean topHit = false;
    boolean bottomHit = true;
    for ( int i = 0; i < TIME_POINTS; i++ )
    {
      if ( topHit )
      {
        start -= 0.3;
      }
      if ( bottomHit )
      {
        start += 0.3;
      }

      if ( start >= 3.5 )
      {
        topHit = true;
        bottomHit = false;
      }

      if ( start <= 0 )
      {
        bottomHit = true;
        topHit = false;
      }
      data[ i ] = start;
    }
    return data;
  }
}
