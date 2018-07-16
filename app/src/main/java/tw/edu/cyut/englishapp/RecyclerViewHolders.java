package tw.edu.cyut.englishapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    public TextView Title,Type,Time,Status;

    public RecyclerViewHolders(View itemView) {
        super(itemView);

        Title = (TextView)itemView.findViewById(R.id.textTitle);
        Type = (TextView)itemView.findViewById(R.id.textType);
        Time = (TextView)itemView.findViewById(R.id.textTime);
        Status = (TextView)itemView.findViewById(R.id.textStatus);

    }

}
