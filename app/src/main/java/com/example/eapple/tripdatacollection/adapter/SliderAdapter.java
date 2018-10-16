package com.example.eapple.tripdatacollection.adapter;

import android.content.Context;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.eapple.tripdatacollection.R;

import java.util.List;


/*This class or adapter is used for sliding the images in detail activity
* and is using 3rd party library
* if you want to know more about this 3rd party lib please refer to this
* link (https://github.com/daimajia/AndroidImageSlider)*/
public class SliderAdapter {

    Context context;
    List<String> gallery;
    String title;

    public SliderAdapter(Context context, List<String> gallery, String title) {
        this.context = context;
        this.gallery = gallery;
        this.title = title;
    }

    public void setSliderLayout(SliderLayout slider) {
        for (String image : gallery) {
            TextSliderView sliderView = new TextSliderView(context);

            sliderView
                    .image(image)
                    .empty(R.drawable.place_holder_image)
                    .error(R.drawable.error_holder_image)
                    .description(title)
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            slider.addSlider(sliderView);
        }

        slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // if you want to change the animation to it in setCustomAnimation();
        // other animation is being mention in above link
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);
    }

}
