package User;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ustglobal.arcloudanchors.R;

/**
 * Виджет, описывающий действие, демонстрирующее функцию.
 */
public final class FeatureView extends FrameLayout {

    /**
     * Создает представление функций путем расширения layout/feature.xml.
     */
    public FeatureView(Context context) {
        super(context);

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.feature, this);
    }

    /**
     * Установите идентификатор ресурса заголовка демонстрации.
     *      *
     *      * @param titleId идентификатор ресурса названия демо
     */
    public synchronized void setTitleId(int titleId) {
        ((TextView) (findViewById(R.id.title))).setText(titleId);
    }

    /**
     * Установите идентификатор ресурса описания демо.
     *
     * @param descriptionId идентификатор ресурса описания демо
     */
    public synchronized void setDescriptionId(int descriptionId) {
        ((TextView) (findViewById(R.id.description))).setText(descriptionId);
    }

}
