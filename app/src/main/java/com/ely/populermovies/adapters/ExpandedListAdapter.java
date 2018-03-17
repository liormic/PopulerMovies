package com.ely.populermovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.ely.populermovies.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import static android.media.CamcorderProfile.get;

/**
 * Created by lior on 3/14/18.
 */

public class ExpandedListAdapter extends BaseExpandableListAdapter {

    private List<String> expandableParentList;
    private HashMap<String, ArrayList<String>> expandableList;
    private Context context;
    public ExpandedListAdapter(List<String> expandableParentList, HashMap<String, ArrayList<String>> expandableList, Context context) {
        this.expandableList = expandableList;
        this.expandableParentList =expandableParentList;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return expandableParentList.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return this.expandableList.get(expandableParentList.get(i))
                .size();
    }

    @Override
    public Object getGroup(int position) {
        return expandableParentList.get(position);
    }                                                                                        

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return expandableList.get(expandableParentList.get(groupPosition)).get(childPosititon);

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
        java.lang.String headerTitle = (java.lang.String) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView header = view
                .findViewById(R.id.headerExpandable);
        header.setText(headerTitle);

        return view;
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {



        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_list_item, null);
        }

        TextView txtListChild = (TextView) view
                .findViewById(R.id.expandableItem);

        if(groupPosition==0) {
            childPosition++;
            String textChild = "Trailers " + childPosition;
            txtListChild.setText(textChild);
        }else{
            txtListChild.setText(getChild(groupPosition,childPosition).toString());
        }

        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {


        return true;
    }



}
