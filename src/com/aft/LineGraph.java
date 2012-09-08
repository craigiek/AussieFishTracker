package com.aft;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import com.aft.R.color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineGraph
{
  private final static int TIME_POINTS = 48;

  public GraphicalView getView( final Context context )
  {
    final Date[] xTimes = getXCoordinates();
    final double[] yTidesCycle = getYCoordinates();

    //todo use a TimeChart ??? or use custom x labels!!

    final TimeSeries waveCycleSeries = new TimeSeries( "Tides" );
    for ( int i = 0; i < xTimes.length; i++ )
    {
      waveCycleSeries.add( xTimes[ i ], yTidesCycle[ i ] );
    }

    final XYMultipleSeriesDataset seriesList = new XYMultipleSeriesDataset();
    seriesList.addSeries( waveCycleSeries );

    final XYMultipleSeriesRenderer chartRenderer = new XYMultipleSeriesRenderer();
    chartRenderer.setPanEnabled( false, false );
    chartRenderer.setAxisTitleTextSize( 20.0f );
    chartRenderer.setZoomButtonsVisible( true );

    // tide cycle series
    final XYSeriesRenderer tideCycleRenderer = new XYSeriesRenderer();
    //tideCycleRenderer.setChartValuesTextSize( 50.0f ); NOT what we want - this refers to values displayed at each point
    chartRenderer.addSeriesRenderer( tideCycleRenderer );
    chartRenderer.setApplyBackgroundColor( true );
    chartRenderer.setBackgroundColor( Color.argb( 0x00, 0x01, 0x01, 0x01 ) );
    chartRenderer.setMarginsColor( Color.argb( 0x00, 0x01, 0x01, 0x01 ) );
    chartRenderer.setLegendTextSize( 20.0f );
    chartRenderer.setAxesColor( Color.GRAY );
    chartRenderer.setXLabelsColor( Color.GRAY );
    chartRenderer.setYLabelsColor( 0, Color.GRAY );
    chartRenderer.setYLabelsAlign( Align.RIGHT );
    //chartRenderer.setPanLimits( ???? );

    final GraphicalView lineChartView = ChartFactory.getLineChartView( context, seriesList, chartRenderer );
    lineChartView.setBackgroundColor( color.white );
    return lineChartView;
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
      cal.add( Calendar.MINUTE, i * 30 );
      data[ i ] = cal.getTime();
    }
    return data;
  }

  private double[] getYCoordinates()
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
