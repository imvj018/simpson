package app.trial.simpsons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itoPAdapter extends RecyclerView.Adapter<itoPAdapter.itoPViewHolder> {
    private Context mContext;
    private ArrayList<itoPItem> mitoPList;
    private OnItemClickListener mListener;

    public itoPAdapter(ArrayList<itoPItem> itoPList) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public itoPAdapter(Context context, ArrayList<itoPItem> itoPList) {
        mContext = context;
        mitoPList = itoPList;
    }

    @NonNull
    @Override
    public itoPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.material_item, parent, false);
        return new itoPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itoPViewHolder holder, int position) {
        itoPItem currentItem = mitoPList.get(position);
        String itemno = currentItem.getitemno();
        String material = currentItem.getmaterial();
        String matdes = currentItem.getmatdes();

        holder.mOpr.setText("Item no " + itemno + " : " + material);
        holder.mPro.setText("Material : " + matdes);



    }

    @Override
    public int getItemCount() {
        return mitoPList.size();
    }

    public class itoPViewHolder extends RecyclerView.ViewHolder {

        public TextView mOpr;
        public TextView mPro;

        public itoPViewHolder(@NonNull View itemView) {
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

