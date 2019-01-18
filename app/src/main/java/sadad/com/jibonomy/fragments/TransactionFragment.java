package sadad.com.jibonomy.fragments;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sadad.com.jibonomy.ExpandableListAdapter;
import sadad.com.jibonomy.HomeFragment;
import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.SubCategory;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.services.SubCategoryService;
import sadad.com.jibonomy.services.TransactionService;
import sadad.com.jibonomy.utils.NavigationUtil;
import sadad.com.jibonomy.utils.StringUtil;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class TransactionFragment extends Fragment {
    View rootView;
    EditText amount;
    TextView dateOfTransaction;
    TextView timeOfTransaction;
    TextView selectCategory;
    EditText transactionDescription;
    Button saveTransaction;
    RadioGroup radioTransactionType;
    CategoryService categoryService;
    SubCategoryService subCategoryService;
    Long selectedSubCategory, transactionId, selectedDate;
    String selectedTime;

    TransactionService transactionService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.transaction_fragment, container, false);
        transactionService = new TransactionService(rootView.getContext());
        categoryService = new CategoryService(rootView.getContext());
        subCategoryService = new SubCategoryService(rootView.getContext());
        amount = rootView.findViewById(R.id.amount);
        dateOfTransaction = rootView.findViewById(R.id.dateOfTransaction);
        timeOfTransaction = rootView.findViewById(R.id.timeOfTransaction);
        transactionDescription = rootView.findViewById(R.id.transactionDescription);
        selectCategory = rootView.findViewById(R.id.selectCategory);
        saveTransaction = rootView.findViewById(R.id.saveTransaction);
        radioTransactionType = rootView.findViewById(R.id.radioTransactionType);


        if (isInEditMode()) {
            transactionId = getArguments().getLong(Transaction.TRANSACTION_ID_LABEL);
            Transaction transaction = transactionService.getTransaction(transactionId);
            amount.setText(StringUtil.df.format(transaction.getAmount()));
            dateOfTransaction.setText(StringUtil.formatDate(transaction.getTransactionDate()));
            timeOfTransaction.setText(StringUtil.formatTime(transaction.getTransactionTime()));
            selectedDate = Long.valueOf(transaction.getTransactionDate());
            selectedTime = transaction.getTransactionTime();
            SubCategory subCategory = subCategoryService.getSubCategory(transaction.getSubCategoryType());
            selectedSubCategory = subCategory.getSubCategoryId();

            if (transaction.getTransactionType().equals(Transaction.EXPENSE)) {
                radioTransactionType.check(R.id.radioExpense);
            } else {
                radioTransactionType.check(R.id.radioIncome);
            }
            Category category = categoryService.get(subCategory.getCategoryId());
            String categoryTitle = category.getCategoryName() + "/" + subCategory.getSubCategoryName();
            selectCategory.setText(categoryTitle);

            transactionDescription.setText(transaction.getDescription());
        }

        timeOfTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar persianCalendar = new PersianCalendar(new Date().getTime());
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                                String time = hourString + ":" + minuteString;
                                Toast.makeText(rootView.getContext(), time, Toast.LENGTH_LONG).show();
                                selectedTime = hourString + minuteString;
                                timeOfTransaction.setText(time);
                            }
                        }, persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                        persianCalendar.get(PersianCalendar.MINUTE),
                        true);


                android.app.FragmentManager fragmentManager = ((Activity) rootView.getContext()).getFragmentManager();
                timePickerDialog.show(fragmentManager, "TimePickerDialog");
            }


        });
        dateOfTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                Toast.makeText(rootView.getContext(), "Date: " + date, Toast.LENGTH_LONG).show();
                                selectedDate = Long.valueOf(date.replace("/", ""));
                                dateOfTransaction.setText(date);
                            }
                        },
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );


                android.app.FragmentManager fragmentManager = ((Activity) rootView.getContext()).getFragmentManager();
                datePickerDialog.show(fragmentManager, "DatePickerDialog");
            }
        });

        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = new Transaction();
                transaction.setTag("user");
                transaction.setAmount(new BigDecimal(amount.getText().toString().replace(",", "")));
                transaction.setSubCategoryType(selectedSubCategory);
                transaction.setDescription(transactionDescription.getText().toString());
                transaction.setTransactionDate(Long.toString(selectedDate));
                transaction.setTransactionTime(selectedTime);
                RadioButton radio = rootView.findViewById(radioTransactionType.getCheckedRadioButtonId());
                transaction.setTransactionType(Byte.valueOf(radio.getTag().toString()));
                if (isInEditMode()) {
                    long transactionId = getArguments().getLong(Transaction.TRANSACTION_ID_LABEL);
                    transaction.setTransactionId(transactionId);
                    transactionService.update(transaction);
                } else {
                    transactionService.insert(transaction);
                }
                NavigationUtil.changeFragment(new HomeFragment(), rootView);
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
                dialog.setTitle("Choose category");

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
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                        String categoryName = categoryList.get(groupPosition).getCategoryName();
                        SubCategory subCategory = getSubCategory(groupPosition, childPosition);
                        //Toast.makeText(view.getContext(), categoryName + " : " + subCategory, Toast.LENGTH_SHORT).show();
                        selectCategory.setText(categoryName + "/" + subCategory.getSubCategoryName());
                        selectedSubCategory = subCategory.getSubCategoryId();
                        dialog.hide();
                        return false;
                    }

                    private SubCategory getSubCategory(int groupPosition, int childPosition) {
                        return subCategoryMap.get(categoryList.get(groupPosition).getCategoryId()).get(childPosition);
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

    private boolean isInEditMode() {
        return getArguments() != null && getArguments().get(Transaction.TRANSACTION_ID_LABEL) != null;
    }
}
