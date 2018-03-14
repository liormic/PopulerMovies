package com.ely.populermovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ely.populermovies.MovieObject;
import com.ely.populermovies.R;
import com.ely.populermovies.display.DisplayMovieDetailsFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lior on 3/14/18.
 */

public class ExpandedListAdapter extends BaseExpandableListAdapter {
    private  String[] expandableParentList = {"Trailers","Reviews"};
    private HashMap<String, ArrayList<MovieObject>> expandableList;
    private Context context;
    public ExpandedListAdapter(HashMap<String, ArrayList<MovieObject>> expandableList, Context context) {
        this.expandableList = expandableList;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return expandableParentList.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return expandableList.size();
    }

    @Override
    public Object getGroup(int position) {
        return expandableParentList[position];
    }                                                                                        

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return expandableList.get(expandableParentList[groupPosition]).get(childPosititon);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int parentPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_list_item, null);
        }

        TextView header = view
                .findViewById(R.id.header);
        header.setText(headerTitle);

        return view;
    }



    @Override
    public View getChildView(int groupPosition, int i1, boolean b, View view, ViewGroup viewGroup) {
        String header = (String) getGroup(groupPosition);
        if(view ==null){
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandable_list_item,null);
        }

        TextView textView = view.findViewById(R.id.expandableItem);
        textView.setText(header);
        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



}
