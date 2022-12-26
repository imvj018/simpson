package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class otoAdapter extends RecyclerView.Adapter<otoAdapter.otoViewHolder> {
    private Context mContext;
    private ArrayList<otoItem> motoList;
    private OnItemClickListener mListener;

    public otoAdapter(ArrayList<otoItem> otoList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public otoAdapter(Context context, ArrayList<otoItem> otoList) {
        mContext = context;
        motoList = otoList;
    }

    @NonNull
    @Override
    public otoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.obmaterial_item, parent, false);
        return new otoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull otoViewHolder holder, int position) {
        otoItem currentItem = motoList.get(position);
        String itemno = currentItem.getitemno();
        String material = currentItem.getmaterial();
        String matdes = currentItem.getdescription();

        holder.mOpr.setText("Item no " + itemno + " : " + material);
        holder.mPro.setText("Material : " + matdes);



    }

    @Override
    public int getItemCount() {
        return motoList.size();
    }

    public class otoViewHolder extends RecyclerView.ViewHolder {

        public TextView mOpr;
        public TextView mPro;

        public otoViewHolder(@NonNull View itemView) {
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

