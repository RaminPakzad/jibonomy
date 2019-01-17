package sadad.com.jibonomy.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import sadad.com.jibonomy.ExpandableListAdapter;
import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.services.SubCategoryService;
import sadad.com.jibonomy.services.TransactionService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class TransactionFragment extends Fragment {
    View rootView;
    EditText amount;
    TextView dateOfTransaction;
    TextView selectCategory;
    Spinner category;
    EditText transactionDescription;
    Button saveTransaction;
    RadioGroup radioTransactionType;
    CategoryService categoryService;
    SubCategoryService subCategoryService;

    private TransactionService transactionService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.transaction_fragment, container, false);
        transactionService = new TransactionService(rootView.getContext());
        categoryService = new CategoryService(rootView.getContext());
        subCategoryService = new SubCategoryService(rootView.getContext());
        amount = rootView.findViewById(R.id.amount);
        dateOfTransaction = rootView.findViewById(R.id.dateOfTransaction);
        category = rootView.findViewById(R.id.category);
        transactionDescription = rootView.findViewById(R.id.transactionDescription);
        selectCategory = rootView.findViewById(R.id.selectCategory);
        saveTransaction = rootView.findViewById(R.id.saveTransaction);
        radioTransactionType = rootView.findViewById(R.id.radioTransactionType);
        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = new Transaction();
                transaction.setAmount(new BigDecimal(amount.getText().toString()));
                transaction.setCategoryType((byte) 1);
                transaction.setDescription(transactionDescription.getText().toString());
                transaction.setTransactionDate(13970101L);
                transaction.setTransactionTime("1440");
                RadioButton radio = rootView.findViewById(radioTransactionType.getCheckedRadioButtonId());
                transaction.setTransactionType(Byte.valueOf(radio.getTag().toString()));
                transactionService.insert(transaction);
            }
        });

        selectCategory.setOnClickListener(new View.OnClickListener() {
            List<Category> categoryList;
            HashMap<Long, List<SubCategory>> subCategoryMap;
            private int lastExpandedPosition = -1;

            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.select_category_dialog);
                dialog.setTitle("Title...");

                final ExpandableListView expListView;
                expListView = (ExpandableListView) dialog.findViewById(R.id.lvExp);
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (lastExpandedPosition != -1
                                && groupPosition != lastExpandedPosition) {
                            expListView.collapseGroup(lastExpandedPosition);
                        }
                        lastExpandedPosition = groupPosition;
                    }
                });
                prepareListData();

                ExpandableListAdapter listAdapter = new ExpandableListAdapter(dialog.getContext(), categoryList, subCategoryMap);
                expListView.setAdapter(listAdapter);

                // set the custom dialog components - text, image and button
                /*TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_launcher);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
*/
                dialog.show();
            }

            private void prepareListData() {

                categoryList = categoryService.getCategories();
                subCategoryMap = new HashMap<>();

                for (Category category : categoryList) {
                    List<SubCategory> subCategories = subCategoryService.getAllByCategoryId(category.getCategoryId());
                    subCategoryMap.put(category.getCategoryId(), subCategories);
                }

                // Adding child data
//                categoryList.add("Top 250");
//                categoryList.add("Now Showing");
//                categoryList.add("Coming Soon..");

                // Adding child data
//                List<String> top250 = new ArrayList<String>();
//                top250.add("The Shawshank Redemption");
//                top250.add("The Godfather");
//                top250.add("The Godfather: Part II");
//                top250.add("Pulp Fiction");
//                top250.add("The Good, the Bad and the Ugly");
//                top250.add("The Dark Knight");
//                top250.add("12 Angry Men");
//
//                List<String> nowShowing = new ArrayList<String>();
//                nowShowing.add("The Conjuring");
//                nowShowing.add("Despicable Me 2");
//                nowShowing.add("Turbo");
//                nowShowing.add("Grown Ups 2");
//                nowShowing.add("Red 2");
//                nowShowing.add("The Wolverine");
//
//                List<String> comingSoon = new ArrayList<String>();
//                comingSoon.add("2 Guns");
//                comingSoon.add("The Smurfs 2");
//                comingSoon.add("The Spectacular Now");
//                comingSoon.add("The Canyons");
//                comingSoon.add("Europa Report");
//
//                subCategoryMap.put(categoryList.get(0), top250); // Header, Child data
//                subCategoryMap.put(categoryList.get(1), nowShowing);
//                subCategoryMap.put(categoryList.get(2), comingSoon);
            }
        });

        return rootView;
    }
}
