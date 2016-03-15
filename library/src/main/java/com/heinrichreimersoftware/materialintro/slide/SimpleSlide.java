package com.heinrichreimersoftware.materialintro.slide;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinrichreimersoftware.materialintro.R;

public class SimpleSlide extends Slide {

    private final Fragment fragment;
    @ColorRes
    private final int background;
    @ColorRes
    private final int backgroundDark;

    private SimpleSlide(Builder builder) {
        fragment = Fragment.newInstance(builder.title, builder.description, builder.image,
                builder.background, builder.layout);
        background = builder.background;
        backgroundDark = builder.backgroundDark;
        this.setAllowSlideNext(builder.allowNext);
        this.setAllowSlidePrevious(builder.allowPrevious);
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public int getBackground() {
        return background;
    }

    @Override
    public int getBackgroundDark() {
        return backgroundDark;
    }

    public static class Builder{
        @ColorRes
        private int background = 0;
        @ColorRes
        private int backgroundDark = 0;
        @StringRes
        private int title = 0;
        @StringRes
        private int description = 0;
        @DrawableRes
        private int image = 0;
        @LayoutRes
        private int layout = R.layout.fragment_simple_slide;

        private boolean allowNext = true;
        private boolean allowPrevious = true;

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder backgroundDark(@ColorRes int backgroundDark) {
            this.backgroundDark = backgroundDark;
            return this;
        }

        public Builder title(@StringRes int title) {
            this.title = title;
            return this;
        }

        public Builder description(@StringRes int description) {
            this.description = description;
            return this;
        }

        public Builder image(@DrawableRes int image) {
            this.image = image;
            return this;
        }

        public Builder layout(@LayoutRes int layout) {
            this.layout = layout;
            return this;
        }

        public Builder scrollable(boolean scrollable) {
            this.layout = scrollable ? R.layout.fragment_simple_slide_scrollable :
                    R.layout.fragment_simple_slide;
            return this;
        }

        public Builder allowNext(boolean value) {
            this.allowNext = value;
            return this;
        }

        public Builder allowPrevious(boolean value) {
            this.allowPrevious = value;
            return this;
        }

        public SimpleSlide build(){
            return new SimpleSlide(this);
        }
    }

    public static class Fragment extends android.support.v4.app.Fragment{
        private static final String ARGUMENT_TITLE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_TITLE_RES";
        private static final String ARGUMENT_DESCRIPTION_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_DESCRIPTION_RES";
        private static final String ARGUMENT_IMAGE_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_IMAGE_RES";
        private static final String ARGUMENT_BACKGROUND_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_BACKGROUND_RES";
        private static final String ARGUMENT_LAYOUT_RES =
                "com.heinrichreimersoftware.materialintro.SimpleFragment.ARGUMENT_LAYOUT_RES";

        public Fragment() {
        }

        public static Fragment newInstance(@StringRes int title, @StringRes int description,
                                           @DrawableRes int image, @ColorRes int background,
                                           @LayoutRes int layout) {
            Fragment fragment = new Fragment();

            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_TITLE_RES, title);
            arguments.putInt(ARGUMENT_DESCRIPTION_RES, description);
            arguments.putInt(ARGUMENT_IMAGE_RES, image);
            arguments.putInt(ARGUMENT_BACKGROUND_RES, background);
            arguments.putInt(ARGUMENT_LAYOUT_RES, layout);
            fragment.setArguments(arguments);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();

            View fragment = inflater.inflate(arguments.getInt(ARGUMENT_LAYOUT_RES,
                    R.layout.fragment_simple_slide), container, false);

            TextView title = (TextView) fragment.findViewById(R.id.mi_title);
            TextView description = (TextView) fragment.findViewById(R.id.mi_description);
            ImageView image = (ImageView) fragment.findViewById(R.id.mi_image);

            int titleRes = arguments.getInt(ARGUMENT_TITLE_RES);
            int descRes = arguments.getInt(ARGUMENT_DESCRIPTION_RES);
            int imgRes = arguments.getInt(ARGUMENT_IMAGE_RES);
            int backgroundRes = arguments.getInt(ARGUMENT_BACKGROUND_RES);

            if (titleRes != 0)
                title.setText(titleRes);
            if (descRes != 0)
                description.setText(descRes);
            if (imgRes != 0)
                image.setImageResource(imgRes);

            if (backgroundRes != 0) {
                if (ColorUtils.calculateLuminance(ContextCompat.getColor(getContext(), backgroundRes)) > 0.6) {
                    //Use dark text color
                    title.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_light));
                    description.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_secondary_light));
                } else {
                    //Use light text color
                    title.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_primary_dark));
                    description.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.mi_text_color_secondary_dark));
                }
            }
            return fragment;
        }
    }
}
