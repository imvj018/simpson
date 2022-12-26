package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class otCAdapter extends RecyclerView.Adapter<otCAdapter.otCViewHolder> {
    private Context mContext;
    private ArrayList<otCItem> motCList;
    private OnItemClickListener mListener;

    public otCAdapter(ArrayList<otCItem> otCList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public otCAdapter(Context context, ArrayList<otCItem> otCList) {
        mContext = context;
        motCList = otCList;
    }

    @NonNull
    @Override
    public otCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.obmaterial_item, parent, false);
        return new otCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull otCViewHolder holder, int position) {
        otCItem currentItem = motCList.get(position);
        String itemno = currentItem.getitemno();
        String material = currentItem.getmaterial();
        String matdes = currentItem.getdescription();

        holder.mOpr.setText("Item no " + itemno + " : " + material);
        holder.mPro.setText("Material : " + matdes);



    }

    @Override
    public int getItemCount() {
        return motCList.size();
    }

    public class otCViewHolder extends RecyclerView.ViewHolder {

        public TextView mOpr;
        public TextView mPro;

        public otCViewHolder(@NonNull View itemView) {
            super(itemView);

            mOpr = itemView.findViewById(R.id.matno);
            mPro = itemView.findViewById(R.id.matdes);


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

