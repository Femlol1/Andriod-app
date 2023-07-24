package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import uk.ac.le.co2103.part2.db.Items;
import uk.ac.le.co2103.part2.viewmodel.ItemListActivityViewModel;

public class ShowItemsListActivity extends AppCompatActivity implements ItemsListAdapter.HandleItemsClick {

    private int category_id;
    private ItemsListAdapter itemsListAdapter;
    private ItemListActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private Items itemToUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);

        category_id = getIntent().getIntExtra("category_id", 0);
        String CategoryName = getIntent().getStringExtra("category_name");

        getSupportActionBar().setTitle(CategoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText addNewItemInput = findViewById(R.id.addnewItemInput);
        ImageView saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = addNewItemInput.getText().toString();
                if(TextUtils.isEmpty(itemName)) {
                    Toast.makeText(ShowItemsListActivity.this,"Enter the name of the product ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(itemToUpdate == null)
                    saveNewItem(itemName);
                else
                    updateNewItem(itemName);


            }
        });

        initVeiwMaodel();
        initRecyclerView();


    }


    private void initVeiwMaodel() {
       viewModel = new ViewModelProvider(this).get(ItemListActivityViewModel.class);
       viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
           @Override
           public void onChanged(List<Items> items) {
               if(items == null) {
                   recyclerView.setVisibility(View.GONE);
                   findViewById(R.id.noResult).setVisibility(View.VISIBLE);


               } else {
                   itemsListAdapter.setCategoryList(items);
                   findViewById(R.id.noResult).setVisibility(View.GONE);
                   recyclerView.setVisibility(View.VISIBLE);

               }

           }
       });

    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview); //might become an issue
        recyclerView.setLayoutManager(new LinearLayoutManager(this ));

        itemsListAdapter = new ItemsListAdapter(this,this);

        recyclerView.setAdapter(itemsListAdapter);

    }
    private void saveNewItem(String itemName) {
        Items item = new Items();
        item.itemName = itemName;
        item.categoryId = category_id;
        viewModel.insertItems(item);
        ((EditText) findViewById(R.id.addnewItemInput)).setText("");

    }

    @Override
    public void itemClick(Items item) {
        if(item.completed) {
            item.completed = false;

        } else {
            item.completed =true;

        }
        viewModel.updatedItems(item);

    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItems(item);

    }

    @Override
    public void editItem(Items item) {
        this.itemToUpdate = item;
        ((EditText) findViewById(R.id.addnewItemInput)).setText(item.itemName);


    }
    private void updateNewItem(String newName) {
        itemToUpdate.itemName = newName;
        viewModel.updatedItems(itemToUpdate);
        ((EditText) findViewById(R.id.addnewItemInput)).setText("");

        itemToUpdate = null;


        }

}