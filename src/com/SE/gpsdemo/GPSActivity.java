package com.SE.gpsdemo;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class GPSActivity extends Activity implements LocationListener
{
	double currentLong = 0.0;
	double currentLat = 0.0;
	double prevLong = 0.0;
	double prevLat = 0.0;
	float totalDistance = 0.0f;
	TextView t = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, this);
		t = (TextView)findViewById(R.id.GPSDataView);
	}
	
	@Override
	public void onLocationChanged(Location loc)
	{	
		float[] result = new float[1];
		if(prevLong == 0.0 || prevLat == 0.0)
		{	
			currentLat = loc.getLatitude();
			currentLong = loc.getLongitude();
			prevLat = currentLat;
			prevLong = currentLong;
		} 
		else 
		{	
			prevLat = currentLat;
			prevLong = currentLong;
			currentLat = loc.getLatitude();
			currentLong = loc.getLongitude();
			Location.distanceBetween(prevLat, prevLong, currentLat, currentLong, result);
			if( result[0] < 0.1 )
			{
				result[0] = 0.0f;
			}
			totalDistance+=result[0];
				
		}
		t.setText(	"My current location is: " +"\nLatitude = " + currentLat +
		"\nLongitude = " + currentLong +
		"\nTotal Distance = " + totalDistance + " meters" +
		"\nDistance from prev location = " + result[0] + " meters");
	}
	@Override
	public void onProviderDisabled(String provider)
	{
		Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
	}
	@Override
	public void onProviderEnabled(String provider)
	{
		Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
	}
		
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}
}
