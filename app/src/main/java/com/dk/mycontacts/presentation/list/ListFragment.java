package com.dk.mycontacts.presentation.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.mycontacts.MainActivity;
import com.dk.mycontacts.R;
import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.DataProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListFragment extends Fragment implements ListView{
    private ContactsAdapter adapter;
    private ListPresenter presenter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerView_contacts);
        setupRecyclerView();
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton_add_contact);
        floatingActionButton.setOnClickListener(addButtonClick);
        DataProvider dataProvider = (MainActivity) requireActivity();
        presenter = new ListPresenter(this, dataProvider);
    }

    @Override
    public void showContactsList(List<Contact> contacts) {
        adapter.setItems(contacts);
    }

    @Override
    public void showContactDetails(Contact contact) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.put_contact_key), contact);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_listFragment_to_detailsFragment, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_restore) {
            presenter.onRestoreDataAction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactsAdapter(onItemClickListener, onDelButtonClickListener);
        recyclerView.setAdapter(adapter);
    }

    private View.OnClickListener addButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onAddContactAction();
        }
    };

    private ContactsAdapter.OnItemClickListener onItemClickListener = new ContactsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            presenter.onContactSelected(adapter.getItem(position));
        }
    };

    private ContactsAdapter.OnDelButtonClickListener onDelButtonClickListener = new ContactsAdapter.OnDelButtonClickListener() {
        @Override
        public void onDelButtonClick(int position) {
            presenter.onDeleteContactAction(adapter.getItem(position));
        }
    };
}