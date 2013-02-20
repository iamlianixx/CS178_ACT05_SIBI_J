package com.sibi.locationmapsactivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationListener{

	private GoogleMap map;
	TextView tv;
	LocationManager locManager;
	Criteria crit = new Criteria();
	String prov, locprint;
	Location loc;
	Button start, stop;
	private int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.btnStart);
		stop = (Button) findViewById(R.id.btnStop);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		prov = locManager.getBestProvider(crit, false);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		View.OnClickListener listenbuttons = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(((Button)v).getId() == start.getId()){
					locManager.requestLocationUpdates(prov, 10000, (float)10.0, MainActivity.this);					
				}
				else if(((Button)v).getId() == stop.getId()){
					locManager.removeUpdates(MainActivity.this);
					loc = locManager.getLastKnownLocation(prov);
					Toast.makeText(MainActivity.this, "Last Location: " 
							+ loc.getLatitude() + ", " + loc.getLongitude() + ".",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		start.setOnClickListener(listenbuttons);
		stop.setOnClickListener(listenbuttons);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void showUpdate(){
		LatLng temp = new LatLng(loc.getLatitude(), loc.getLongitude());
		map.addMarker(new MarkerOptions().position(temp).title("Location # " + (count+1)));
		count++;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if(loc == null || (location.getLatitude() != loc.getLatitude() && 
				location.getLongitude() != loc.getLongitude())) {
			loc = location;
			showUpdate();
			Toast.makeText(this, "You are in: " + loc.getLatitude() + ", " + 
					loc.getLongitude(), Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(this, "You are still in the same location.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


}