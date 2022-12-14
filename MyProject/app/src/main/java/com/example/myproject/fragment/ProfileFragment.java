package com.example.myproject.fragment;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.myproject.Adapter.CategoryAdapter;
import com.example.myproject.Adapter.ImageSliderAdapter;
import com.example.myproject.Adapter.ProfileViewPagerAdapter;
import com.example.myproject.Adapter.ReviewAdapter;
import com.example.myproject.Domain.CategoryDomain;
import com.example.myproject.Domain.ReviewDomain;
import com.example.myproject.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ViewPager2 sliderViewPager;
    LinearLayout layoutIndicator;
    String[] images;
    Toolbar toolbar;
    int i;
    AppBarLayout appBarLayout;
    RecyclerView view2;
    ReviewAdapter adapter;




    public ProfileFragment() {

    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.profile, container, false);


        //?????? ???????????? ??????
        sliderViewPager = view.findViewById(R.id.pro_sliderViewPager);
        layoutIndicator = view.findViewById(R.id.pro_layoutIndicators);


        images = new String[] {
                // ?????????
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
        };

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(getContext(), images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);



        // toolbar
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        toolbar = view.findViewById(R.id.pro_toolbar);
        activity.setSupportActionBar(toolbar);
        // ????????? ?????? ????????? ????????? ??? ??? ??????????????? ????????? ????????? ??????
        activity.getSupportActionBar().setTitle("?????????");


        i = view.findViewById(R.id.pro_viewFadingEdge).getHeight();
        appBarLayout = view.findViewById(R.id.pro_appBarLayout2);




        //((NotifyingScrollView) view.findViewById(R.id.scroll_view)).setOnScrollChangedListener(mOnScrollChangedListener);

        toolbar.setBackground(getResources().getDrawable(R.drawable.background));
        // ??????x ?????? ??????????????? ?????? ???????????? ?????????
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int ver = Math.abs(verticalOffset)/ 4;
                final int headerHeight = 300 - toolbar.getHeight();
                final float ratio = (float) Math.min(Math.max(ver, 0), headerHeight) / headerHeight;
                final int newAlpha = (int) (ratio * 255);
                //toolbar.setAlpha(ratio);
                toolbar.getBackground().setAlpha(newAlpha);
                //Log.i("d",Float.toString(newApha));
                //Log.d("tag_scroll", "recycler_view current offset: "+verticalOffset);
            }
        });

        //?????? ??????????????? ???

        view2 = (RecyclerView)view.findViewById(R.id.view2);
        view2.setLayoutManager(new LinearLayoutManager(getContext()));

        //FirebaseRecyclerOptions<CategoryDomain> options =
        //        new FirebaseRecyclerOptions.Builder<CategoryDomain>()
        //                .setQuery(FirebaseDatabase.getInstance().getReference().child("requests"), CategoryDomain.class)
        //                .build();
        // ex) firebase?????? ????????? ????????????

        ArrayList<ReviewDomain> review = new ArrayList<>();
        review.add(new ReviewDomain("?????????","game_icon","???????????? ???????????????","2022-10-15",2));
        review.add(new ReviewDomain("?????????","game_icon","???????????? ???????????????","2022-10-15",1));
        review.add(new ReviewDomain("?????????","game_icon","???????????? ???????????????","2022-10-15",1));

        adapter = new ReviewAdapter(review);
        view2.setAdapter(adapter);

        // ?????? textview
        TextView review_textView = view.findViewById(R.id.review_textView);
        //???????????? ????????? ?????? ????????? ?????? rating??? ????????? ?????? ????????? ??????
        review_textView.setText(2 + " & " + adapter.getItemCount() + "?????? ??????");
        return view;
    }


    // ?????????????????? ?????? ????????? ?????? ???????????? ?????? ??????
    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    // ?????? ?????? ?????? ?????? ??????????????? ?????? ????????????
    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }


}