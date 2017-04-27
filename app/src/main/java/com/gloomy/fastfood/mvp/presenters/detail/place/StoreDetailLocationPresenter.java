package com.gloomy.fastfood.mvp.presenters.detail.place;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.StoreAddress;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.detail.store.location.IStoreDetailLocationView;
import com.gloomy.fastfood.mvp.views.detail.store.location.StoreDetailLocationActivity;
import com.gloomy.fastfood.utils.PermissionUtil;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Apr-17.
 */
@EBean
public class StoreDetailLocationPresenter extends BasePresenter implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DirectionCallback {
    private static final int REQUEST_PERMISSION_CODE = 121;
    private static final int DEFAULT_LAT_LNG_BOUND_PADDING = 200;
    private static final int DEFAULT_DIRECTION_STROKE_WIDTH = 2;

    @Setter
    @Accessors(prefix = "m")
    private IStoreDetailLocationView mView;

    @Setter
    @Accessors(prefix = "m")
    private Store mStore;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentPosition;
    private Marker mUserMarker;
    private Polyline mDirection;
    private LatLng mStorePosition;

    public void requestPermission(StoreDetailLocationActivity activity) {
        PermissionUtil.requestPermissions(activity, REQUEST_PERMISSION_CODE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void onPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            boolean status = true;
            if (permissions.length == grantResults.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        status = false;
                        break;
                    }
                }
                if (status) {
                    getCurrentLocation();
                    setMapStyle();
                }
            }
        }
    }

    public void initMap(int mapResId, FragmentManager fragmentManager) {
        MapFragment mapFragment = MapFragment.newInstance();
        fragmentManager.beginTransaction().add(mapResId, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapStyle();
        loadStorePosition();
    }

    private void setMapStyle() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mView.onRequestPermission();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // No-op
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // No-op
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (isLocationChange(location)) {
                mCurrentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(mCurrentPosition).include(mStorePosition).build(), DEFAULT_LAT_LNG_BOUND_PADDING));
                if (mUserMarker != null) {
                    mUserMarker.remove();
                }
                mUserMarker = mMap.addMarker(new MarkerOptions()
                        .position(mCurrentPosition)
                        .title(getString(R.string.current_position))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)));
                findDirection();
            }
        } else {
            mView.onCantGetLocation();
        }
    }

    private boolean isLocationChange(Location location) {
        return mCurrentPosition == null || mCurrentPosition.latitude != location.getLatitude() || mCurrentPosition.longitude != location.getLongitude();
    }

    private void loadStorePosition() {
        if (mStore != null && mStore.getStoreAddress() != null) {
            StoreAddress address = mStore.getStoreAddress();
            mStorePosition = new LatLng(address.getLat(), address.getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(mStorePosition)
                    .title(mStore.getStoreName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_24dp)));
        }
    }

    private void findDirection() {
        if (mStorePosition != mCurrentPosition) {
            GoogleDirection.withServerKey(getString(R.string.google_map_direction_key))
                    .from(mCurrentPosition)
                    .to(mStorePosition)
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        getDirection(direction);
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        mView.onDirectionFailure();
    }

    private void getDirection(Direction direction) {
        if (mDirection != null) {
            mDirection.remove();
        }
        if (direction.getRouteList().size() > 0) {
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            mDirection = mMap.addPolyline(DirectionConverter.createPolyline(mContext, directionPositionList, DEFAULT_DIRECTION_STROKE_WIDTH, Color.RED));
        }
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(getContext().getString(R.string.store_place_text, mStore.getStoreName()));
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackClick();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }
}
