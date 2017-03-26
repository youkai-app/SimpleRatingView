package app.youkai.simpleratingview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A custom view for emoji style rating selection.
 */
public class SimpleRatingView extends LinearLayout {

    private ImageView awful;
    private ImageView meh;
    private ImageView good;
    private ImageView amazing;

    private OnRatingSelectedListener listener;

    private Rating rating = Rating.NO_RATING;

    public SimpleRatingView(Context context) {
        this(context, null);
    }

    public SimpleRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRatingView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public SimpleRatingView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs);
        init(attrs, defStyle, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        /* Setup our root LinearLayout */
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_simpleratingview, this, true);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        /* Obtain references to our views */
        awful = (ImageView) findViewById(R.id.awful);
        meh = (ImageView) findViewById(R.id.meh);
        good = (ImageView) findViewById(R.id.good);
        amazing = (ImageView) findViewById(R.id.amazing);

        /* Set up touch animations */
        OnTouchListener animTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate()
                                .scaleXBy(0.1f)
                                .setDuration(50)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                        v.animate()
                                .scaleYBy(0.1f)
                                .setDuration(50)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate()
                                .scaleX(1f)
                                .setDuration(50)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                        v.animate()
                                .scaleY(1f)
                                .setDuration(50)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                }
                return false;
            }
        };
        awful.setOnTouchListener(animTouchListener);
        meh.setOnTouchListener(animTouchListener);
        good.setOnTouchListener(animTouchListener);
        amazing.setOnTouchListener(animTouchListener);

        /* Set click listeners */
        awful.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(Rating.AWFUL);
            }
        });
        meh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(Rating.MEH);
            }
        });
        good.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(Rating.GOOD);
            }
        });
        amazing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(Rating.AMAZING);
            }
        });

        /* Set up long click listeners for tooltips */
        awful.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showTooltip(Rating.AWFUL, v);
                return true;
            }
        });
        meh.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showTooltip(Rating.MEH, v);
                return true;
            }
        });
        good.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showTooltip(Rating.GOOD, v);
                return true;
            }
        });
        amazing.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showTooltip(Rating.AMAZING, v);
                return true;
            }
        });
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.rating = rating;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        this.rating = savedState.rating;
    }

    public void setListener(OnRatingSelectedListener listener) {
        this.listener = listener;
    }

    public void setRating(Rating rating) {
        /* If this is a tap on the currently selected rating, deselect it */
        if (rating == this.rating) {
            rating = Rating.NO_RATING;
        }

        this.rating = rating;

        int transparent = getResources().getColor(android.R.color.transparent);
        switch (rating) {
            case AWFUL:
                awful.setBackgroundResource(R.drawable.srv_selected_awful);
                meh.setBackgroundColor(transparent);
                good.setBackgroundColor(transparent);
                amazing.setBackgroundColor(transparent);
                break;
            case MEH:
                awful.setBackgroundColor(transparent);
                meh.setBackgroundResource(R.drawable.srv_selected_meh);
                good.setBackgroundColor(transparent);
                amazing.setBackgroundColor(transparent);
                break;
            case GOOD:
                awful.setBackgroundColor(transparent);
                meh.setBackgroundColor(transparent);
                good.setBackgroundResource(R.drawable.srv_selected_good);
                amazing.setBackgroundColor(transparent);
                break;
            case AMAZING:
                awful.setBackgroundColor(transparent);
                meh.setBackgroundColor(transparent);
                good.setBackgroundColor(transparent);
                amazing.setBackgroundResource(R.drawable.srv_selected_amazing);
                break;
            case NO_RATING:
                awful.setBackgroundColor(transparent);
                meh.setBackgroundColor(transparent);
                good.setBackgroundColor(transparent);
                amazing.setBackgroundColor(transparent);
        }

        if (listener != null) {
            listener.onRatingSelected(rating);
        }
    }

    public Rating getRating() {
        return rating;
    }

    private void showTooltip(Rating rating, View anchor) {
        Toast toast = Toast.makeText(getContext(), rating.getStringRes(), Toast.LENGTH_SHORT);
        toast.setGravity(
                Gravity.NO_GRAVITY,
                Math.round(anchor.getX() - (anchor.getMeasuredWidth() * 2)),
                (int) Math.round(anchor.getY() - (anchor.getMeasuredHeight() * 1.5))
        );
        toast.show();
    }

    public interface OnRatingSelectedListener {
        void onRatingSelected(Rating rating);
    }

    public enum Rating {
        AWFUL("awful"), MEH("meh"), GOOD("good"), AMAZING("amazing"), NO_RATING("none");

        private String value;

        Rating(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static Rating fromString(String value) {
            switch (value) {
                case "awful":
                    return AWFUL;
                case "meh":
                    return MEH;
                case "good":
                    return GOOD;
                case "amazing":
                    return AMAZING;
                default:
                    return NO_RATING;
            }
        }

        /**
         * Returns a string resource. Override these to localize.
         */
        public int getStringRes() {
            switch (value) {
                case "awful":
                    return R.string.srv_awful;
                case "meh":
                    return R.string.srv_meh;
                case "good":
                    return R.string.srv_good;
                case "amazing":
                    return R.string.srv_amazing;
                default:
                    return R.string.srv_no_rating;
            }
        }
    }

    private static class SavedState extends BaseSavedState {
        private Rating rating;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.rating = Rating.fromString(in.readString());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            if (out == null) return;

            super.writeToParcel(out, flags);
            out.writeString(rating.toString());
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
