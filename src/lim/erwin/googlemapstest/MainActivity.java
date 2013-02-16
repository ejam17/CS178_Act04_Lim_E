package lim.erwin.googlemapstest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.app.AlertDialog;


import lim.erwin.googlemapstest.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapClickListener
{
  static final LatLng uscTc = new LatLng(10.35410, 123.91145);
  static final LatLng uscMain = new LatLng(10.30046, 123.88822);
  static final LatLng home = new LatLng(10.0, 123.0);

  private int checker = 0;
  private int maximum = 3;
  private GoogleMap a;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
	  super.onCreate(savedInstanceState);
    
	  setContentView(R.layout.activity_main);
    
	    if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS)
	    {
	    	a = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	    
	    	if(a != null)
	    	{
	    		Marker usc_tc = a.addMarker(new MarkerOptions().position(uscTc).title("USC-tc"));
	    		Marker usc_main = a.addMarker(new MarkerOptions().position(uscMain).title("USC-main"));
	    		Marker my_place = a.addMarker(new MarkerOptions().position(home).title("My home"));
	    	    	
	    		a.setOnMapClickListener(this);
	    		a.animateCamera(CameraUpdateFactory.newLatLngZoom(uscTc, 15), 2000, null);
	    	}
	    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) 
  {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

@Override
public void onMapClick(LatLng point) {
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int check) {
	        switch (check)
	        {
	        	case DialogInterface.BUTTON_POSITIVE:
	            
	        	switch(checker)
	            {
		            case 0:	a.animateCamera(CameraUpdateFactory.newLatLngZoom(uscTc, 15), 2000, null); 
		            	break;
		            case 1:	a.animateCamera(CameraUpdateFactory.newLatLngZoom(uscMain, 15), 2000, null);
		            	break;
		            case 2:	a.animateCamera(CameraUpdateFactory.newLatLngZoom(home, 15), 2000, null); 
	            }
	            
	            checker = (checker + 1) % maximum;
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Go to the next destination?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();	
}

} 
