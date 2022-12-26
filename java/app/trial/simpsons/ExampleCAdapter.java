package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleCAdapter extends RecyclerView.Adapter<ExampleCAdapter.ExampleCViewHolder> {
    private Context mContext;
    private ArrayList<ExampleCItem> mExampleCList;
    private OnItemClickListener mListener;

    public ExampleCAdapter(ArrayList<ExampleCItem> ExampleCList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExampleCAdapter(Context context, ArrayList<ExampleCItem> ExampleCList) {
        mContext = context;
        mExampleCList = ExampleCList;
    }

    @NonNull
    @Override
    public ExampleCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleCViewHolder holder, int position) {
        ExampleCItem currentItem = mExampleCList.get(position);
        String operation = currentItem.gettono();

        holder.mOpr.setText("Transfer Order No : " + operation);

    }

    @Override
    public int getItemCount() {
        return mExampleCList.size();
    }

    public class ExampleCViewHolder extends RecyclerView.ViewHolder {
        public TextView mOpr;


        public ExampleCViewHolder(@NonNull View itemView) {
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
