package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class otBAdapter extends RecyclerView.Adapter<otBAdapter.otBViewHolder> {
    private Context mContext;
    private ArrayList<otBItem> motBList;
    private OnItemClickListener mListener;

    public otBAdapter(ArrayList<otBItem> otBList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public otBAdapter(Context context, ArrayList<otBItem> otBList) {
        mContext = context;
        motBList = otBList;
    }

    @NonNull
    @Override
    public otBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.obmaterial_item, parent, false);
        return new otBViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull otBViewHolder holder, int position) {
        otBItem currentItem = motBList.get(position);
        String itemno = currentItem.getitemno();
        String material = currentItem.getmaterial();
        String matdes = currentItem.getdescription();

        holder.mOpr.setText("Item no " + itemno + " : " + material);
        holder.mPro.setText("Material : " + matdes);



    }

    @Override
    public int getItemCount() {
        return motBList.size();
    }

    public class otBViewHolder extends RecyclerView.ViewHolder {

        public TextView mOpr;
        public TextView mPro;

        public otBViewHolder(@NonNull View itemView) {
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

