package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itoCAdapter extends RecyclerView.Adapter<itoCAdapter.itoCViewHolder> {
    private Context mContext;
    private ArrayList<itoCItem> mitoCList;
    private OnItemClickListener mListener;

    public itoCAdapter(ArrayList<itoCItem> itoCList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public itoCAdapter(Context context, ArrayList<itoCItem> itoCList) {
        mContext = context;
        mitoCList = itoCList;
    }

    @NonNull
    @Override
    public itoCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.material_item, parent, false);
        return new itoCViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itoCViewHolder holder, int position) {
        itoCItem currentItem = mitoCList.get(position);
        String itemno = currentItem.getitemno();
        String material = currentItem.getmaterial();
        String matdes = currentItem.getmatdes();

        holder.mOpr.setText("Item no " + itemno + " : " + material);
        holder.mPro.setText("Material : " + matdes);



    }

    @Override
    public int getItemCount() {
        return mitoCList.size();
    }

    public class itoCViewHolder extends RecyclerView.ViewHolder {

        public TextView mOpr;
        public TextView mPro;

        public itoCViewHolder(@NonNull View itemView) {
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

