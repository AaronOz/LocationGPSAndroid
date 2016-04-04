package com.example.aaron.locationgps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;


//import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private LocationManager locationManager;
    public final static String CORDINATES = "com.example.aaron.locationgps.cordinates.VALUE";
    Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);

    }
    public void showMapAction(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("latitudeData", lat);
        intent.putExtra("longitudeData",lng);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {

        TextView latValue = (TextView) findViewById(R.id.latValue);
        TextView lonValue = (TextView) findViewById(R.id.lonValue);
        TextView accValue = (TextView) findViewById(R.id.accValue);
        TextView speedValue = (TextView) findViewById(R.id.speedValue);
        TextView addressValue = (TextView) findViewById(R.id.addressValue);

        DecimalFormat decimalFormat = new DecimalFormat(".#");

        Toast.makeText(getBaseContext(), "Location has changed", Toast.LENGTH_LONG).show();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        List<Address> currentAddress = getAddress(latLng);
        lat = location.getLatitude();
        lng = location.getLongitude();
        float accu = location.getAccuracy();
        float speed = location.getSpeed();
        String address = currentAddress.get(0).getAddressLine(0);
        String city = currentAddress.get(0).getAddressLine(1);
        String country = currentAddress.get(0).getAddressLine(2);



        latValue.setText(decimalFormat.format(lat));
        lonValue.setText(decimalFormat.format(lng));
        accValue.setText(decimalFormat.format(accu)+"m");
        speedValue.setText(decimalFormat.format(speed)+"m/s");

        addressValue.setText(address+" - "+city+" - "+country);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public List<Address> getAddress(LatLng point) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this);
            if (point.latitude != 0 || point.longitude != 0) {
                addresses = geocoder.getFromLocation(point.latitude ,
                        point.longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                Log.i("Address",address+" - "+city+" - "+country);

                return addresses;

            } else {
                Toast.makeText(this, "latitude and longitude are null",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
