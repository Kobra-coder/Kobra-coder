package mapdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ustglobal.arcloudanchors.R;

import User.FeatureView;

/**
 *Основная деятельность демонстрационной галереи библиотеки API.
 *  *
 *  * <p>На основном макете перечислены демонстрируемые функции с кнопками для их запуска.
 */
public final class DemoMaps extends AppCompatActivity {

    private static final String TAG = DemoMaps.class.getSimpleName();

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {

        /** @param demos Массив, содержащий сведения о демонстрациях, которые необходимо отобразить. */
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }

            DemoDetails demo = getItem(position);

            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);

            Resources resources = getContext().getResources();
            String title = resources.getString(demo.titleId);
            String description = resources.getString(demo.descriptionId);
            featureView.setContentDescription(title + ". " + description);

            return featureView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListAdapter adapter = new CustomArrayAdapter(this, DemoDetailsList.DEMOS);

        ListView demoListView = (ListView) findViewById(R.id.list);
        if (demoListView != null) {
            demoListView.setAdapter(adapter);
            demoListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    DemoDetails demo = (DemoDetails) parent.getItemAtPosition(position);
                    startActivity(new Intent(view.getContext(), demo.activityClass));
                });
        }

    }
}
