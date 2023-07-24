package uk.ac.le.co2103.part2.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {



    @PrimaryKey(autoGenerate = true)
    public int listid;

    @ColumnInfo(name = "name")
    public String name;



}
