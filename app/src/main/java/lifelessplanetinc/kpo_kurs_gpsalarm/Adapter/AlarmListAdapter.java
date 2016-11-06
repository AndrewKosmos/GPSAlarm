package lifelessplanetinc.kpo_kurs_gpsalarm.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import lifelessplanetinc.kpo_kurs_gpsalarm.Classes.Alarm;
import lifelessplanetinc.kpo_kurs_gpsalarm.R;

/**
 * Created by Andrew on 01.11.2016.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {

    List<Alarm> alarms_list;

    private static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView,int position);
        void onItemLongClick(View itemView,int position);
        void onItemIconClick(View itemView,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public AlarmListAdapter(List<Alarm> alarms_list){
        this .alarms_list = alarms_list;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm item = alarms_list.get(position);
        holder.title.setText(item.getTitle());
        holder.destination.setText(item.getDestination());
    }

    @Override
    public int getItemCount() {
        return alarms_list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title;
        TextView destination;
        ImageButton ib;

        public AlarmViewHolder(final View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title);
            destination = (TextView)itemView.findViewById(R.id.destination);
            cardView = (CardView) itemView.findViewById(R.id.card_item);
            ib = (ImageButton) itemView.findViewById(R.id.alarm_icon);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView,position);
                        }
                    }
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemLongClick(itemView,position);
                        }
                    }
                    return false;
                }
            });

            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemIconClick(itemView,position);
                        }
                    }
                }
            });
        }
    }
}
