package uk.ac.le.co2103.part2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.le.co2103.part2.db.AppDatabase;
import uk.ac.le.co2103.part2.db.Category;
import uk.ac.le.co2103.part2.db.Items;

public class ItemListActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;
    public ItemListActivityViewModel(Application application){
        super(application);
        listOfItems = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Items>> getItemsListObserver() {

        return listOfItems;
    }

    public void getAllItemsList(int CategoryID) {
        List<Items> itemsList = appDatabase.shoppingListDao().getAllItemsList(CategoryID);
        if(itemsList.size() > 0)
        {
            listOfItems.postValue(itemsList);

        }else{
            listOfItems.postValue(null);
        }

    }
    public void insertItems (Items item) {

        appDatabase.shoppingListDao().insertItems(item);
        getAllItemsList(item.categoryId);

    }
    public void updatedItems (Items item) { ;
        appDatabase.shoppingListDao().updateItems(item);
        getAllItemsList(item.categoryId);

    }
    public void deleteItems (Items item) { ;
        appDatabase.shoppingListDao().deleteItems(item);
        getAllItemsList(item.categoryId);

    }

}
