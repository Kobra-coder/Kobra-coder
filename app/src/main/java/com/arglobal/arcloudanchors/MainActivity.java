package com.arglobal.arcloudanchors;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.ustglobal.arcloudanchors.R;

import java.util.ArrayList;
import java.util.List;

import User.GuideActivity;
import User.MainActivityImg;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SensorEventListener,View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private CustomArFragment arFragment;
    private ArrayList anchorList;
    public Spinner modelOptionsSpinner;
    private static final String[] paths = {"Прямая стрела", "Правая стрелка", "Стрелка влево"};
    private String FROM, MODE;
    // define the display assembly compass picture
    private ImageView image;
    Button infoar;
    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;
    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }

    private Anchor anchor;
    private AnchorNode anchorNode;
    private AppAnchorState appAnchorState = AppAnchorState.NONE;
    private String LEK205 = "lek205";
    private String LEK211 = "lek211";
    private String DECANAT = "decanat";
    private String KAFEDRA = "kafedra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                FROM = null;
            } else {
                FROM = extras.getString(Home.FROM);
                MODE = extras.getString(Home.MODE);
            }
        }

        setContentView(R.layout.activity_main);
        //
        image = (ImageView) findViewById(R.id.imageViewCompass);
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        // initialize your android device sensor capabilities
//step counter ************************************************
        infoar = (Button) findViewById(R.id.infod);
        infoar.setOnClickListener(this);
        //*****************************************************************
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        anchorList = new ArrayList();
        Database tinydb = new Database(getApplicationContext());
        Button resolve = findViewById(R.id.resolve);
        modelOptionsSpinner = findViewById(R.id.modelOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelOptionsSpinner.setAdapter(adapter);
        modelOptionsSpinner.setOnItemSelectedListener(this);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            //Active only in Admin Mode
            if(MODE.equalsIgnoreCase("admin")) {
                Log.d("HIT_RESULT:", hitResult.toString());
                anchor = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
                appAnchorState = AppAnchorState.HOSTING;
                showToast("Размещен...");
                createCloudAnchorModel(anchor);
            } else {
                showToast("Anchor может быть размещен только в режиме администратора");
            }

        });

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

            if (appAnchorState != AppAnchorState.HOSTING)
                return;
            Anchor.CloudAnchorState cloudAnchorState = anchor.getCloudAnchorState();

            if (cloudAnchorState.isError()) {
                showToast(cloudAnchorState.toString());
            } else if (cloudAnchorState == Anchor.CloudAnchorState.SUCCESS) {
                appAnchorState = AppAnchorState.HOSTED;

                String anchorId = anchor.getCloudAnchorId();
                anchorList.add(anchorId);

                if (FROM.equalsIgnoreCase(Home.LEK205)) {
                    tinydb.putListString(LEK205, anchorList);
                } else if (FROM.equalsIgnoreCase(Home.LEK211)) {
                    tinydb.putListString(LEK211, anchorList);
                } else if (FROM.equalsIgnoreCase(Home.DECANAT)) {
                    tinydb.putListString(DECANAT, anchorList);
                } else if (FROM.equalsIgnoreCase(Home.KAFEDRA)) {
                    tinydb.putListString(KAFEDRA, anchorList);
                }

                showToast("Anchor успешно размещен. Идентификатор привязки: " + anchorId);
            }

        });


        resolve.setOnClickListener(view -> {
            ArrayList<String> stringArrayList = new ArrayList<>();
            if (FROM.equalsIgnoreCase(Home.LEK205)) {
                stringArrayList = tinydb.getListString(LEK205);
            } else if (FROM.equalsIgnoreCase(Home.LEK211)) {
                stringArrayList = tinydb.getListString(LEK211);
            } else if (FROM.equalsIgnoreCase(Home.DECANAT)) {
                stringArrayList = tinydb.getListString(DECANAT);
            } else if (FROM.equalsIgnoreCase(Home.KAFEDRA)) {
                stringArrayList = tinydb.getListString(KAFEDRA);
            }

            for (int i = 0; i < stringArrayList.size(); i++) {
                String anchorId = stringArrayList.get(i);
                if (anchorId.equals("null")) {
                    Toast.makeText(this, "Идентификатор привязки не найден", Toast.LENGTH_LONG).show();
                    return;
                }

                Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor(anchorId);
                createCloudAnchorModel(resolvedAnchor);

            }


        });

        if (MODE.equalsIgnoreCase("user")) {
            modelOptionsSpinner.setVisibility(View.GONE);
        } else {
            modelOptionsSpinner.setVisibility(View.GONE);
            resolve.setVisibility(View.VISIBLE);
        }


    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void createCloudAnchorModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("model.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeCloudAnchorModel(anchor, modelRenderable));

    }

    private void placeCloudAnchorModel(Anchor anchor, ModelRenderable modelRenderable) {
            anchorNode = new AnchorNode(anchor);
        /*
        AnchorNode нельзя увеличить или переместить, создаем TransformableNode с AnchorNode в качестве родителя.*/
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());

            if (modelOptionsSpinner.getSelectedItem().toString().equals("Прямая стрела")) {
                transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 225));
            }
            if (modelOptionsSpinner.getSelectedItem().toString().equals("Правая стрелка")) {
                transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 135));
            }
            if (modelOptionsSpinner.getSelectedItem().toString().equals("Стрелка влево")) {
                transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 315));
            }
            transformableNode.setParent(anchorNode);
            //добавляем модель в трансформируемый узел
            transformableNode.setRenderable(modelRenderable);
            //добавляем это на сцену
               arFragment.getArSceneView().getScene().addChild(anchorNode);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    protected void onPause()
    {
        super.onPause();


        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Заголовок: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // not in use
    }

    @Override
    public void onClick(View view) {
                Intent intent = new Intent(this, MainActivityImg.class);
                startActivity(intent);


        }
}
