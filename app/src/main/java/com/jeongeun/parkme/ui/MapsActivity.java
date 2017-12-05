package com.jeongeun.parkme.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.jeongeun.parkme.R;
import com.jeongeun.parkme.databinding.ActivityMapsBinding;
import com.jeongeun.parkme.listener.OnTaskCompleted;
import com.jeongeun.parkme.model.ParkZone;
import com.jeongeun.parkme.model.ViewComponent;
import com.jeongeun.parkme.utils.JsonParser;
import com.jeongeun.parkme.utils.MapUtils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnTaskCompleted {

    private GoogleMap mMap;
    private AsyncTask<Void, Void, Void> mParseTask = null;
    private ViewComponent mViewComp = null;
    private BottomSheetBehavior mBottomSheetBehavior = null;
    private ActivityMapsBinding mMapsBinding = null;
    private final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapsBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState != null) {
            mViewComp = savedInstanceState.getParcelable("viewComponent");
        } else {
            // parse Json file
            mParseTask = new ParseAsyncTask(this);
            mParseTask.execute();
        }
    }

    /**
     * To handle Start Parking button clicked
     * @param view
     */
    public void onButtonClick(View view) {
        ParkZone selectedZone = mViewComp.getSelectedZone();
        if (selectedZone != null) {
            Toast.makeText(this, String.format(getString(R.string.parked), MapUtils.getCurrentTime()),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("viewComponent", mViewComp);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapUtils.displayComponent(mMap, mViewComp, mBottomSheetBehavior, mMapsBinding);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    /**
     * To do actions when the parsing is completed.
     */
    @Override
    public void onTaskCompleted() {
        MapUtils.displayComponent(mMap, mViewComp, mBottomSheetBehavior, mMapsBinding);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mParseTask != null && !mParseTask.isCancelled()) {
            mParseTask.cancel(true);
        }
    }

    /**
     * AsynTask for parsing a Json file.
     * As soon as the parsing is finished, onTaskCompleted callback will be called.
     */
    private class ParseAsyncTask extends AsyncTask<Void, Void, Void> {
        OnTaskCompleted taskCompletedListener;

        public ParseAsyncTask(OnTaskCompleted listener) {
            this.taskCompletedListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            taskCompletedListener.onTaskCompleted();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mViewComp = JsonParser.parse(getApplicationContext());
            return null;
        }

    }
}