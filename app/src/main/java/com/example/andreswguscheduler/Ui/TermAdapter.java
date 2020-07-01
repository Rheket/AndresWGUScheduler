package com.example.andreswguscheduler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreswguscheduler.Entities.Term;
import com.example.andreswguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    private List<Term> mTerms = new ArrayList<>();
    private OnTermListener mOnTermListener;

    @NonNull
    @Override
    public TermAdapter.TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.term_item, parent, false);

        return new TermAdapter.TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {

        Term currentTerm = mTerms.get(position);
        holder.textViewTitle.setText(currentTerm.getTermTitle());

        holder.textViewStartDate.setText(currentTerm.getStartDate());
        holder.textViewAnticipatedEndDate.setText(currentTerm.getEndDate());

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms) {

        this.mTerms = terms;
        notifyDataSetChanged();

    }

    public class TermHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewAnticipatedEndDate;

        public TermHolder(@NonNull View itemView) {

            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_start_date);
            textViewAnticipatedEndDate = itemView.findViewById(R.id.text_view_anticipated_end_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (mOnTermListener != null && position != RecyclerView.NO_POSITION) {

                        mOnTermListener.onTermClick(mTerms.get(position));

                    }

                }
            });

        }

    }

    public interface OnTermListener {

        void onTermClick(Term term);

    }

    public void setOnTermListener(OnTermListener listener) {

        this.mOnTermListener = listener;

    }

}
