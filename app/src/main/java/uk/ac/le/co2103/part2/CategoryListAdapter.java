package uk.ac.le.co2103.part2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.le.co2103.part2.db.Category;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick cliclListener;

    public CategoryListAdapter(Context context, HandleCategoryClick cliclListener) {
        this.context =context;
        this.cliclListener = cliclListener;
    }
    public void setCategoryList (List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.MyViewHolder holder, int position) {
        holder.tvCategoryName.setText(this.categoryList.get(position).name);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cliclListener.itemClick(categoryList.get(position));

            }
        });

        holder.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cliclListener.editItem(categoryList.get(position));

            }
        });
        holder.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cliclListener.removeItem(categoryList.get(position));

            }
        });

    }


    @Override
    public int getItemCount() {
        if(categoryList == null || categoryList.size() == 0)
            return 0;
        else
            return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategoryName;
        ImageView removeCategory;
        ImageView editCategory;
        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
            removeCategory = view.findViewById(R.id.removeCategory);
            editCategory = view.findViewById(R.id.editCategory);
        }


    }

    public interface HandleCategoryClick {
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);

    }
}
