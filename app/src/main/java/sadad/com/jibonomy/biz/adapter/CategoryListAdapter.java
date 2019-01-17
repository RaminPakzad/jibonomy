package sadad.com.jibonomy.biz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.services.WishService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/17/2019.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    private List<Category> categories;
    private View itemView;
    private CategoryService categoryService;

    public CategoryListAdapter(List<Category> categories) {
        this.categories = categories;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        categoryService = new CategoryService(itemView.getContext());
        return new CategoryListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.MyViewHolder holder, int position) {
        Category category = categories.get(position);
        DecimalFormat df = new DecimalFormat("#,###");
        holder.budget.setText(df.format(category.getBudget()) + " بودجه ماهانه ");
        holder.categoryName.setText(category.getCategoryName());
        int id = itemView.getContext().getResources().getIdentifier("sadad.com.jibonomy:drawable/" + category.getIconName(), null, null);
        holder.categoryImage.setImageResource(id);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "edit category", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryImage;
        public TextView categoryName;
        public TextView budget;

        public MyViewHolder(View view) {
            super(view);
            categoryImage = view.findViewById(R.id.categoryImage);
            categoryName = view.findViewById(R.id.categoryName);
            budget = view.findViewById(R.id.budget);
        }
    }
}
