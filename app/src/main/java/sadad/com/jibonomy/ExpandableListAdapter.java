package sadad.com.jibonomy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Category> categories; // header titles
    // child data in format of header title, child title
    private HashMap<Long, List<SubCategory>> subCategories;

    public ExpandableListAdapter(Context context, List<Category> listDataHeader,
                                 HashMap<Long, List<SubCategory>> listChildData) {
        this._context = context;
        this.categories = listDataHeader;
        this.subCategories = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        SubCategory subCategory = this.subCategories.get(this.categories.get(groupPosition).getCategoryId()).get(childPosititon);
        return subCategory;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SubCategory childText = (SubCategory) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText.getSubCategoryName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.subCategories.get(this.categories.get(groupPosition).getCategoryId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Category category = this.categories.get(groupPosition);
        return category;
    }

    @Override
    public int getGroupCount() {
        return this.categories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Category headerTitle = (Category) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getCategoryName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}