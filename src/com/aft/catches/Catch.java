package com.aft.catches;

import java.util.Date;

/**
 * This class encapsulates the data pertaining to a Catch.
 */

public class Catch
{
  private String _species;
  private Date _dateCaught;
  private double _weight;
  private String _location;
  private String _imagePath;

  public Catch( final String species, final Date date_caught, final double weightKgs,
                final String location, final String imagePath )
  {
    _species = species;
    _dateCaught = date_caught;
    _weight = weightKgs;
    _location = location;
    _imagePath = imagePath;
  }

  public Date getDateCaught()
  {
    return _dateCaught;
  }

  public String getSpecies()
  {
    return _species;
  }

  public double getWeight()
  {
    return _weight;
  }

  public String getLocation()
  {
    return _location;
  }

  public String getImagePath()
  {
    return _imagePath;
  }
}
