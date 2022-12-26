package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleobAdapter extends RecyclerView.Adapter<ExampleobAdapter.ExampleobViewHolder> {
    private Context mContext;
    private ArrayList<ExampleobItem> mExampleobList;
    private OnItemClickListener mListener;

    public ExampleobAdapter(ArrayList<ExampleobItem> ExampleobList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExampleobAdapter(Context context, ArrayList<ExampleobItem> ExampleobList) {
        mContext = context;
        mExampleobList = ExampleobList;
    }

    @NonNull
    @Override
    public ExampleobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exampleob_item, parent, false);
        return new ExampleobViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleobViewHolder holder, int position) {
        ExampleobItem currentItem = mExampleobList.get(position);
        String operation = currentItem.getcid();

        holder.mOpr.setText("Transfer Order No : " + operation);

    }

    @Override
    public int getItemCount() {
        return mExampleobList.size();
    }

    public class ExampleobViewHolder extends RecyclerView.ViewHolder {
        public TextView mOpr;


        public ExampleobViewHolder(@NonNull View itemView) {
            super(itemView);

            mOpr = itemView.findViewById(R.id.custname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
