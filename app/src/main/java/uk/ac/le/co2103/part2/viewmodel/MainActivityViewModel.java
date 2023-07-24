package uk.ac.le.co2103.part2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.le.co2103.part2.MainActivity;
import uk.ac.le.co2103.part2.db.AppDatabase;
import uk.ac.le.co2103.part2.db.Category;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Category>> listOfCategory;
    private AppDatabase appDatabase;
    public MainActivityViewModel(Application application){
        super(application);
        listOfCategory = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Category>> getCategoryListObserver() {
        return listOfCategory;
    }

    public void getAllCategoryList() {
        List<Category> categoryList = appDatabase.shoppingListDao().getALLCategoryList();
        if(categoryList.size() > 0)
        {
            listOfCategory.postValue(categoryList);

        }else{
            listOfCategory.postValue(null);
        }

    }
    public void insertCategory(String catName) {
        Category category = new Category();
        category.name = catName;
        appDatabase.shoppingListDao().insertCategory(category);
        getAllCategoryList();

    }
    public void updateCategory(Category category) { ;
        appDatabase.shoppingListDao().updateCategory(category);
        getAllCategoryList();

    }
    public void deleteCategory(Category category) { ;
        appDatabase.shoppingListDao().deleteCategory(category);
        getAllCategoryList();

    }

}
