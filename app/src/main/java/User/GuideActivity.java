package User;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ustglobal.arcloudanchors.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

  RecyclerView recyclerView;
  ArrayList<Manual> manuaList;
  MyAdapter myAdapter;
  String[] newHeading;
  String[] brif;
  int[] imageResourceId;
  Button guide_ar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.guide_activity);
    guide_ar= (Button) findViewById(R.id.guide_ar);
    guide_ar.setOnClickListener(this);
    recyclerView=findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);

    manuaList=new ArrayList<Manual>();

    newHeading=new String[]{
            "AR внутренняя навигация",
            "AR наружная навигация",

    };
    brif=new String[]{
            getString(R.string.indo),
            getString(R.string.outdo)

    };

    imageResourceId=new int[]{
            R.drawable.nav,
            R.drawable.ic_baseline_map_24
    };
    getData();


  }
  private void getData() {
    for (int i = 0; i < newHeading.length; i++) {
      Manual manual = new Manual(newHeading[i], brif[i], imageResourceId[i]);
      manuaList.add(manual);


    }
    myAdapter = new MyAdapter(this, manuaList);
    recyclerView.setAdapter(myAdapter);
    myAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View view) {
    Intent intent = new Intent(this, InfoActivity.class);
    startActivity(intent);
  }
}