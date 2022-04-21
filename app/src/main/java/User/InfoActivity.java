package User;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.ustglobal.arcloudanchors.R;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private ArFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainre);

        fragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            ViewRenderable
                    .builder()
                    .setView(this, R.layout.pager)
                    .build()
                    .thenAccept(viewRenderable -> {
                        addToScene(viewRenderable, hitResult.createAnchor());
                    });
        });
    }

    private void addToScene(ViewRenderable viewRenderable, Anchor anchor) {
        AnchorNode node = new AnchorNode(anchor);
        node.setRenderable(viewRenderable);
        fragment.getArSceneView().getScene().addChild(node);

        View view = viewRenderable.getView();
        ViewPager pager = view.findViewById(R.id.viewPager);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.et1);
        images.add(R.drawable.et2);
        images.add(R.drawable.et3);
        images.add(R.drawable.et4);
        images.add(R.drawable.korpus);

        ArAdapter adapter = new ArAdapter(images);
        pager.setAdapter(adapter);
    }

    private class ArAdapter extends PagerAdapter {

        List<Integer> images;

        public ArAdapter(List<Integer> images){
            this.images = images;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.image_item, container, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(images.get(position));

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
