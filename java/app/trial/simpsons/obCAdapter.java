package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class obCAdapter extends RecyclerView.Adapter<obCAdapter.obCViewHolder> {
    private Context mContext;
    private ArrayList<obCItem> mobCList;
    private OnItemClickListener mListener;

    public obCAdapter(ArrayList<obCItem> obCList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public obCAdapter(Context context, ArrayList<obCItem> obCList) {
        mContext = context;
        mobCList = obCList;
    }

    @NonNull
    @Override
    public obCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exampleob_item, parent, false);
        return new obCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull obCViewHolder holder, int position) {
        obCItem currentItem = mobCList.get(position);
        String operation = currentItem.getcid();

        holder.mOpr.setText("Transfer Order No : " + operation);

    }

    @Override
    public int getItemCount() {
        return mobCList.size();
    }

    public class obCViewHolder extends RecyclerView.ViewHolder {
        public TextView mOpr;


        public obCViewHolder(@NonNull View itemView) {
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
