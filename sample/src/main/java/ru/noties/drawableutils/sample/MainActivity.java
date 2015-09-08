package ru.noties.drawableutils.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ru.noties.drawableutils.DrawableSelector;
import ru.noties.drawableutils.SelectorState;

public class MainActivity extends AppCompatActivity {

    private MainAdapter mMainAdapter;
    private RandomColor mRandomColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainAdapter = new MainAdapter(this, 100);
        mRandomColor = new RandomColor(new int[] {
                R.color.color_1,
                R.color.color_2,
                R.color.color_3,
                R.color.color_4,
                R.color.color_5,
                R.color.color_6,
                R.color.color_7
        });
        updateAdapterColors();

        initMainImage();
        initListView();
    }

    private void initMainImage() {
        final ImageView mainImage = findView(R.id.main_image);
        final DrawableSelector mainSelector = DrawableSelector.simple(this, R.color.color_1, R.color.color_7)
                .setEnterFadeDuration(300)
                .setExitFadeDuration(300)
                .build();
        final Drawable mainDrawable = mainSelector.build(R.drawable.ic_android_white_48dp);
        mainImage.setImageDrawable(mainDrawable);

        findView(R.id.main_redraw)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateAdapterColors();
                    }
                });
    }

    private void initListView() {
        final ListView listView = findView(R.id.main_list_view);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position, true);
            }
        });
        listView.setAdapter(mMainAdapter);
    }

    private void updateAdapterColors() {
        mMainAdapter.update(
                mRandomColor.next(),
                mRandomColor.next(),
                mRandomColor.next()
        );
        mMainAdapter.notifyDataSetChanged();
    }

    private <V extends View> V findView(@IdRes int id) {
        //noinspection unchecked
        return (V) findViewById(id);
    }

    private static class MainAdapter extends BaseAdapter {

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final int mSize;

        private DrawableSelector mDrawableSelector;

        MainAdapter(Context context, int size) {
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
            mSize = size;
        }

        void update(int normalColor, int pressedColor, int selectedColor) {
            mDrawableSelector = DrawableSelector.builder(mContext)
                    .addState(SelectorState.PRESSED, pressedColor)
                    .addState(SelectorState.ACTIVATED, selectedColor)
                    .addState(SelectorState.WILD_CARD, normalColor)
                    .setEnterFadeDuration(200)
                    .setExitFadeDuration(200)
                    .build();
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0L;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final View view;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.adapter_item, parent, false);
                view.setTag(new Holder(view));
            } else {
                view = convertView;
            }

            final Holder holder = (Holder) view.getTag();
            final Drawable dr = mDrawableSelector.build(R.drawable.ic_android_white_48dp);
            holder.icon.setImageDrawable(dr);
            holder.text.setText(String.format("Item #%1$d", position));

            return view;
        }

        private static class Holder {
            final ImageView icon;
            final TextView text;
            Holder(View view) {
                this.icon = (ImageView) view.findViewById(R.id.adapter_icon);
                this.text = (TextView) view.findViewById(R.id.adapter_text);
            }
        }
    }
}
