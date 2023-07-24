package uk.ac.le.co2103.part2.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM Category")
    List<Category> getALLCategoryList();

    @Insert
    Void insertCategory(Category...categories);

    @Update
    Void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM Items WHERE categoryId = :catId")
    List<Items> getAllItemsList(int catId);

    @Insert
    void insertItems(Items items);

    @Update
    void updateItems(Items items);

    @Delete
    void deleteItems(Items items);
}
