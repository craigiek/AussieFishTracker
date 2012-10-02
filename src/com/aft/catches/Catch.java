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

  public Catch( final String species, final Date date_caught, final double weightKgs )
  {
    _species = species;
    _dateCaught = date_caught;
    _weight = weightKgs;
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

  public void setDateCaught( final Date dateCaught )
  {
    _dateCaught = dateCaught;
  }

  public void setSpecies( final String species )
  {
    _species = species;
  }

  public void setWeight( final double weight )
  {
    _weight = weight;
  }

  public void setLocation( final String location )
  {
    _location = location;
  }

}
