package sadad.com.jibonomy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.TransactionService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/16/2019.
 */
public class TransactionFragment extends Fragment {
    View rootView;
    EditText amount;
    TextView dateOfTranscation;
    Spinner category;
    EditText transactionDescription;
    Button saveTransaction;
    RadioGroup radioTransactionType;

    private TransactionService transactionService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.transaction_fragment, container, false);
        transactionService = new TransactionService(rootView.getContext());

        amount = rootView.findViewById(R.id.amount);
        dateOfTranscation = rootView.findViewById(R.id.dateOfTransaction);
        category = rootView.findViewById(R.id.category);
        transactionDescription = rootView.findViewById(R.id.transactionDescription);
        saveTransaction = rootView.findViewById(R.id.saveTransaction);
        radioTransactionType = rootView.findViewById(R.id.radioTransactionType);
        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = new Transaction();
                transaction.setAmount(new BigDecimal(amount.getText().toString()));
                transaction.setCategoryType((byte) 1);
                transaction.setDescription(transactionDescription.getText().toString());
                transaction.setTransactionDate("13970101");
                RadioButton radio = rootView.findViewById(radioTransactionType.getCheckedRadioButtonId());
                transaction.setTransactionType(Byte.valueOf(radio.getTag().toString()));
                transactionService.insert(transaction);
            }
        });

        return rootView;
    }
}
