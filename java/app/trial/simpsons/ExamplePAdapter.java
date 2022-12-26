package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamplePAdapter extends RecyclerView.Adapter<ExamplePAdapter.ExamplePViewHolder> {
    private Context mContext;
    private ArrayList<ExamplePItem> mExamplePList;
    private OnItemClickListener mListener;

    public ExamplePAdapter(ArrayList<ExamplePItem> ExamplePList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExamplePAdapter(Context context, ArrayList<ExamplePItem> ExamplePList) {
        mContext = context;
        mExamplePList = ExamplePList;
    }

    @NonNull
    @Override
    public ExamplePViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExamplePViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamplePViewHolder holder, int position) {
        ExamplePItem currentItem = mExamplePList.get(position);
        String operation = currentItem.gettono();

        holder.mOpr.setText("Transfer Order No : " + operation);

    }

    @Override
    public int getItemCount() {
        return mExamplePList.size();
    }

    public class ExamplePViewHolder extends RecyclerView.ViewHolder {
        public TextView mOpr;


        public ExamplePViewHolder(@NonNull View itemView) {
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
