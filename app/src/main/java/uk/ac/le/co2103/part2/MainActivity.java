package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.le.co2103.part2.db.Category;
import uk.ac.le.co2103.part2.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements CategoryListAdapter.HandleCategoryClick {

    //private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityViewModel viewModel;
    private TextView noResultextView;
    private RecyclerView recyclerview;
    private CategoryListAdapter categoryListAdapter;
    private Category categoryForEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Shopping List");
        noResultextView = findViewById(R.id.noResult);
        recyclerview = findViewById(R.id.recyclerview);

            ImageView addNew = findViewById(R.id.addNewCategoryImageView);
            addNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddCategoryDialog(false);


                }
            });
            initViewModel();
            initRecyclerView();
            viewModel.getAllCategoryList();

    }
    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter = new CategoryListAdapter(this, this);
        recyclerview.setAdapter(categoryListAdapter);

    }
    private void initViewModel() {
        viewModel = new ViewModelProvider(this ).get(MainActivityViewModel.class);
        viewModel.getCategoryListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories == null ) {
                    noResultextView.setVisibility(View.VISIBLE);
                    recyclerview.setVisibility(View.GONE);


                }else {
                    //will show in the recycler view
                    categoryListAdapter.setCategoryList(categories);
                    recyclerview.setVisibility(View.VISIBLE);
                    noResultextView.setVisibility(View.GONE);
                }
            }
        });

    }




    private void showAddCategoryDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.add_category_layout, null);
        EditText enterCategoryInput = dialogView.findViewById(R.id.enterCategoryInput);
        TextView createButton = dialogView.findViewById(R.id.createButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);
        if(isForEdit){
            createButton.setText("Update");
            enterCategoryInput.setText(categoryForEdit.name);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = enterCategoryInput.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Enter shopping list name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isForEdit) {
                    categoryForEdit.name = name;
                    viewModel.updateCategory(categoryForEdit);

                } else {



                //here we nee to cll the insert function of the view model
                viewModel.insertCategory(name);
            }
                dialogBuilder.dismiss();

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();


    }

    @Override
    public void itemClick(Category category) {
        Intent intent  = new Intent(MainActivity.this,ShowItemsListActivity.class);
        intent.putExtra("category_id", category.listid);
        intent.putExtra("category_name", category.name);

        startActivity(intent);

    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);

    }

    @Override
    public void editItem(Category category) {
        this.categoryForEdit = category;
        showAddCategoryDialog(true);

    }
}