package com.example.pierolpw10.serviexpress.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pierolpw10.serviexpress.Managers.FirebaseManager;
import com.example.pierolpw10.serviexpress.Managers.PreferenceManager;
import com.example.pierolpw10.serviexpress.Models.User;
import com.example.pierolpw10.serviexpress.Models.Work;
import com.example.pierolpw10.serviexpress.Models.Worker;
import com.example.pierolpw10.serviexpress.R;
import com.example.pierolpw10.serviexpress.Utils.FirebaseConstants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NriKe on 22/10/2017.
 */

public class HomeFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    FirebaseManager manager;
    PreferenceManager p_manager;
    Dialog dialog;
    List<Worker> workers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        manager = new FirebaseManager();
        p_manager = PreferenceManager.getInstance(getActivity());

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }else{
                    googleMap.setMyLocationEnabled(true);

                    // For zooming automatically to the location of the marker
                    LatLng ubicacion_lima = new LatLng(-12.052202, -77.0063561);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(ubicacion_lima).zoom(11).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.setOnMarkerClickListener(HomeFragment.this);
                    getWorkers();
                }
            }
        });

        return v;
    }

    private void getWorkers() {
        if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORKERS_REF) != null) {
            manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORKERS_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Double latitud = (Double) data.child("latitud").getValue();
                        Double longitud = (Double) data.child("longitud").getValue();
                        String image = data.child("image").getValue(String.class);
                        String nombre = data.child("nombres").getValue(String.class);
                        String apellidos = data.child("apellidos").getValue(String.class);
                        Long especialidad_long = (Long) data.child("especialidad").getValue();
                        int especialidad = especialidad_long.intValue();
                        Long puntuacion_long = (Long) data.child("puntuacion").getValue();
                        int puntuacion = puntuacion_long.intValue();
                        Long cant_punt_long = (Long) data.child("cant_punt").getValue();
                        int cant_punt = cant_punt_long.intValue();
                        String worker_username = data.child("username").getValue(String.class);
                        String mail = data.child("mail").getValue(String.class);
                        Long phone_long = (Long) data.child("phone").getValue();
                        String phone = phone_long.toString();
                        boolean working = (Boolean) data.child("working").getValue();

                        Worker worker = new Worker();
                        worker.setNombre(nombre);
                        worker.setWorking(working);
                        worker.setApellidos(apellidos);
                        worker.setPuntuacion(puntuacion);
                        worker.setMail(mail);
                        worker.setPhone(phone);
                        worker.setCant_punt(cant_punt);
                        worker.setEspecialidad(especialidad);
                        worker.setUsername(worker_username);
                        workers.add(worker);
                        addMarker(latitud, longitud, image, nombre, apellidos, especialidad);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void addMarker(Double latitud, Double longitud, String image, String nombre, String apellidos, int especialidad) {
        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);

        CircleImageView imageView = (CircleImageView) marker.findViewById(R.id.profile_image);

        switch (especialidad){
            case 1:
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker1));
                break;
            case 2:
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker2));
                break;
            case 3:
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker3));
                break;
            case 4:
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker4));
                break;
            case 5:
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker5));
                break;
        }

        // For dropping a marker at a point on the Map
        LatLng ubicacion = new LatLng(latitud,longitud);

        String work = "";

        switch (especialidad){
            case 1:
                work = "Gasfitero";
                break;
            case 2:
                work = "Electricista";
                break;
            case 3:
                work = "Cerrajero";
                break;
            case 4:
                work = "Carpintero";
                break;
            case 5:
                work = "Jardinero";
                break;
        }

        googleMap.addMarker(new MarkerOptions().position(ubicacion).title(nombre + " " + apellidos).snippet(work).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        switch (marker.getTitle()){
            case "Jorge Gonzales":
                openDialog("Jorge Gonzales",1, "worker1");
                break;
            case "Willy Wonka":
                openDialog("Willy Wonka",2,"worker2");
                break;
            case "Sheldon Cooper":
                openDialog("Sheldon Cooper",3, "worker3");
                break;
            case "Barry Allen":
                openDialog("Barry Allen",4, "worker4");
                break;
            case "Slade Wilson":
                openDialog("Slade Wilson",5, "worker5");
                break;
        }

        return true;
    }

    private void openDialog(final String nombre, int esp, final String username){
        dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialog);

        Button btn_accept = (Button) dialog.findViewById(R.id.bt_accept);
        Button btn_reject = (Button) dialog.findViewById(R.id.bt_reject);
        Button bt_work = (Button) dialog.findViewById(R.id.bt_work);
        TextView tv_name = (TextView) dialog.findViewById(R.id.tv_name);
        TextView tv_esp = (TextView) dialog.findViewById(R.id.tv_esp);
        TextView tv_phone = (TextView) dialog.findViewById(R.id.tv_phone);
        CircleImageView image = (CircleImageView) dialog.findViewById(R.id.profile_image);

        tv_name.setText("Nombre: " + nombre);

        String esp_st = "Especialidad: ";
        String phone_st = "";

        switch (esp){
            case 1:
                esp_st += "Gasfitero";
                phone_st = "955234694";
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker1));
                break;
            case 2:
                esp_st += "Electricista";
                phone_st = "934971548";
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker2));
                break;
            case 3:
                esp_st += "Cerrajero";
                phone_st = "942833699";
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker3));
                break;
            case 4:
                esp_st += "Carpintero";
                phone_st = "934971548";
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker4));
                break;
            case 5:
                esp_st += "Jardinero";
                phone_st = "955234694";
                image.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.worker5));
                break;
        }

        tv_esp.setText(esp_st);
        tv_phone.setText("Telefono: " + phone_st);

        final String finalPhone_st = phone_st;
        btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+finalPhone_st));
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
                dialog.dismiss();
            }
        });

        bt_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORKERS_REF) != null) {
                    manager.getDatabase().getReference(FirebaseConstants.REF_DATA).child(FirebaseConstants.WORKERS_REF).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()){
                                if(data.child("username").getValue(String.class).equals(username)){
                                    if(!(Boolean) data.child("working").getValue()){
                                        contratar(nombre,dialog);
                                    }else{
                                        Toast.makeText(getActivity(),"Especialista trabajando.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void contratar(String nombre, Dialog dialog) {

        for(Worker w : workers){
            if(nombre.equals(w.getNombre() + " " + w.getApellidos())){
                Work work = new Work();

                Gson gson = new Gson();
                User user = gson.fromJson(p_manager.getPreferenceSession(), User.class);

                work.setUsername(user.getUsername());
                work.setWorker(nombre);
                work.setRated(false);
                work.setWork_rate(0);
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String reportDate = df.format(currentTime);
                work.setDate(reportDate);
                work.setWork(w.getEspecialidad());

                if(manager.getDatabase().getReference(FirebaseConstants.WORK_REF) == null){
                    manager.getUsersReference().child(FirebaseConstants.WORK_REF).push();
                }

                DatabaseReference newRef = manager.getWorksReference().push();
                newRef.setValue(work);

                cambiarEstadoWorker(w.getUsername());

                Toast.makeText(getActivity(),"Contrato exitoso.",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        }
    }

    private void cambiarEstadoWorker(String username) {
        DatabaseReference ref = manager.getWorkersReference();
        ref.child(username).child("working").setValue(true);
    }
}
