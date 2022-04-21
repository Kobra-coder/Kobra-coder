package mapdemo;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Простой объект POJO, содержащий сведения о демонстрации, используемые адаптером списка.
 */
public class DemoDetails {

    /**
     * Идентификатор ресурса названия демо.
     */
    public final int titleId;

    /**
     * Идентификатор ресурсов описания демо.
     */
    public final int descriptionId;

    /**
     * Класс демонстрационной активности.
     * */
    public final Class<? extends AppCompatActivity> activityClass;

    public DemoDetails(
            int titleId, int descriptionId, Class<? extends AppCompatActivity> activityClass) {
        this.titleId = titleId;
        this.descriptionId = descriptionId;
        this.activityClass = activityClass;
    }
}
