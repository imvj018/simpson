package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class obPAdapter extends RecyclerView.Adapter<obPAdapter.obPViewHolder> {
    private Context mContext;
    private ArrayList<obPItem> mobPList;
    private OnItemClickListener mListener;

    public obPAdapter(ArrayList<obPItem> obPList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public obPAdapter(Context context, ArrayList<obPItem> obPList) {
        mContext = context;
        mobPList = obPList;
    }

    @NonNull
    @Override
    public obPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exampleob_item, parent, false);
        return new obPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull obPViewHolder holder, int position) {
        obPItem currentItem = mobPList.get(position);
        String operation = currentItem.getcid();

        holder.mOpr.setText("Transfer Order No : " + operation);

    }

    @Override
    public int getItemCount() {
        return mobPList.size();
    }

    public class obPViewHolder extends RecyclerView.ViewHolder {
        public TextView mOpr;


        public obPViewHolder(@NonNull View itemView) {
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
