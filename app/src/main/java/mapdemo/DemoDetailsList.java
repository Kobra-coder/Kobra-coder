package mapdemo;


import com.ustglobal.arcloudanchors.R;

import User.InfoActivity;
import User.MainActivityImg;
import User.videoActivity;

/**
 * Список всех демоверсий, которые у нас есть.
 */
public final class DemoDetailsList {

    /**
     * Этот класс не должен быть создан.
     */
    private DemoDetailsList() {
    }

    public static final DemoDetails[] DEMOS = {
        new DemoDetails(R.string.basic_map_demo_label,
            R.string.basic_map_demo_description,
           User.MapActivity.class),
            new DemoDetails(R.string.basic_map_demo_label,
                    R.string.basic_map_demo_description,
                    videoActivity.class),
            new DemoDetails(R.string.basic_map_demo_label,
                    R.string.basic_map_demo_description,
                    MainActivityImg.class),
            new DemoDetails(R.string.basic_map_demo_label,
                    R.string.basic_map_demo_description, InfoActivity.class),
    };
}
