package sadad.com.jibonomy.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.services.SubCategoryService;
import sadad.com.jibonomy.utils.NavigationUtil;
import sadad.com.jibonomy.utils.StringUtil;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/17/2019.
 */
public class CategoryFragment extends android.support.v4.app.Fragment {

    Long categoryId = null;
    private View rootView;
    private CategoryService categoryService;
    private SubCategoryService subCategoryService;
    private EditText categoryName;
    private EditText budget;
    //    private Button chooseIcon;
    //    private ImageView categoryIcon;
    private ListView subCategories;
    private Button saveCategory;
    private Button addSubCategory;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.category_fragment, container, false);
        categoryService = new CategoryService(rootView.getContext());
        subCategoryService = new SubCategoryService(rootView.getContext());
        categoryName = rootView.findViewById(R.id.categoryName);
        budget = rootView.findViewById(R.id.budget);
        subCategories = rootView.findViewById(R.id.subCategories);
        saveCategory = rootView.findViewById(R.id.saveCategory);
        addSubCategory = rootView.findViewById(R.id.addSubCategory);
        setCategoryId();
        addSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.add_sub_category_dialog);
                dialog.setTitle("Add subcategory");

                Button saveSubCategory = dialog.findViewById(R.id.saveSubCategory);
                final EditText subCatgoryName = dialog.findViewById(R.id.subCategoryName);


                saveSubCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCategoryId();
                        if (categoryId != null) {
                            SubCategory subCategory = new SubCategory();
                            subCategory.setCategoryId(categoryId);
                            subCategory.setSubCategoryName(subCatgoryName.getText().toString());
                            subCategoryService.insert(subCategory);
                            dialog.hide();

                            refreshFragment();
                            //updateSubCategoryList();
                            //NavigationUtil.changeFragment(new CategoryFragment(), rootView);
                        } else {
                            dialog.hide();
                        }

                    }
                });

                dialog.show();
            }
        });

        if (getArguments() != null) {
            setCategoryId();
            Category category = categoryService.getCategory(categoryId);

            categoryName.setText(category.getCategoryName());
            budget.setText(StringUtil.df.format(category.getBudget()));
/*            chooseIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(rootView.getContext(), "chooseIcon", Toast.LENGTH_SHORT).show();
                }
            });*/
            //int id = rootView.getContext().getResources().getIdentifier("sadad.com.jibonomy:drawable/" + category.getIconName(), null, null);
            //categoryIcon.setImageResource(id);
            updateSubCategoryList();


        }


/*        chooseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rootView.getContext(), "show icon chooser", Toast.LENGTH_LONG).show();
            }
        });*/


        saveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = new Category();
                category.setBudget(new BigDecimal(budget.getText().toString().replace(",","")));
                category.setCategoryName(categoryName.getText().toString());
                category.setIconName("other");
                categoryService.insert(category);
                NavigationUtil.changeFragment(new CategoryListFragment(), rootView);
            }
        });

        return rootView;
    }

    private void refreshFragment() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putLong(Category.CATEGORY_ID_LABEL, categoryId);
        fragment.setArguments(args);
        NavigationUtil.changeFragment(fragment, rootView);
    }

    private void setCategoryId() {
        if (getArguments() != null)
            categoryId = getArguments().getLong(Category.CATEGORY_ID_LABEL);
    }

    private void updateSubCategoryList() {
        setCategoryId();
        List<SubCategory> subCategoryList = subCategoryService.getAllByCategoryId(categoryId);
        List<String> subCategoryItems = new ArrayList<>();
        for (SubCategory item : subCategoryList) {
            subCategoryItems.add(item.getSubCategoryName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                rootView.getContext(),
                android.R.layout.simple_list_item_1,
                subCategoryItems);

        subCategories.setAdapter(arrayAdapter);
        subCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(rootView.getContext(), ((TextView) view).getText().toString(), Toast.LENGTH_LONG).show();
                final String selectedSub = ((TextView) view).getText().toString();
                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                subCategoryService.deleteByCategoryIdAndSubCategoryName(categoryId, selectedSub);
                                refreshFragment();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
                builder.setMessage("آیا از حذف مطمئن هستید?").setPositiveButton("بله", dialogClickListener)
                        .setNegativeButton("نه", dialogClickListener).show();
                return false;
            }
        });

    }
}
