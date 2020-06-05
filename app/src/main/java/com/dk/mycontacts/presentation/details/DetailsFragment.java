package com.dk.mycontacts.presentation.details;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.dk.mycontacts.MainActivity;
import com.dk.mycontacts.R;
import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.DataProvider;

import java.util.Random;

public class DetailsFragment extends Fragment implements DetailsView {
    private static final String FIRST_NAME_ERROR = "First name isn't valid";
    private static final String LAST_NAME_ERROR = "Last name isn't valid";
    private static final String EMAIL_ERROR = "Email isn't valid";
    private DetailsPresenter presenter;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private ImageView avatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        hideKeyboard();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolBar();
        setHasOptionsMenu(true);
        firstName = view.findViewById(R.id.editTextText_first_name);
        lastName = view.findViewById(R.id.editTextText_last_name);
        email = view.findViewById(R.id.editTextText_email);
        avatar = view.findViewById(R.id.imageView_detail_avatar);
        DataProvider dataProvider = (MainActivity) requireActivity();
        presenter = new DetailsPresenter(this, getContactFromArguments(), dataProvider);
    }

    private Contact getContactFromArguments(){
        assert getArguments() != null;
        return (Contact) getArguments().getSerializable(getString(R.string.put_contact_key));
    }

    @Override
    public void showFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void showLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    @Override
    public void showEmail(String email) {
        this.email.setText(email);
    }

    @Override
    public void showAvatar() {
        if (firstName.getText().length() > 0) {
            avatar.setImageDrawable(getDrawableFromName(firstName.getText().toString()));
        }
    }

    @Override
    public void showContactList() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.popBackStack();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.details_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (isValidData()) {
                presenter.onSaveAction(
                        firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolBar(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);
    }

    private boolean isValidData(){
        if (firstName.getText().length() < 2) {
            Toast.makeText(requireActivity(), FIRST_NAME_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastName.getText().length() < 2){
            Toast.makeText(requireActivity(), LAST_NAME_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(email.getText())){
            Toast.makeText(requireActivity(), EMAIL_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void hideKeyboard(){
        InputMethodManager imm =
                (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = requireActivity().getCurrentFocus();
        if (view == null) {
            view = new View(requireActivity());
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private TextDrawable getDrawableFromName(String name){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
        String firstLetter = name.substring(0,1);
        return TextDrawable.builder().buildRect(firstLetter, color);
    }
}