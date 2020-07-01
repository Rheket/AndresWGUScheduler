package com.example.andreswguscheduler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreswguscheduler.Entities.Assessment;
import com.example.andreswguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {

    private List<Assessment> assessments = new ArrayList<>();
    private OnAssessmentListener onAssessmentListener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_item, parent, false);
        return  new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        Assessment currentAssessment = assessments.get(position);

        holder.title.setText(currentAssessment.getAssessmentTitle());
        holder.type.setText(currentAssessment.getAssessmentType());
        holder.dueDate.setText(currentAssessment.getAssessmentDueDate());

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView type;
        private TextView dueDate;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_view_title);
            type = itemView.findViewById(R.id.text_view_assessment_type);
            dueDate = itemView.findViewById(R.id.text_view_due_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(onAssessmentListener != null && position != RecyclerView.NO_POSITION) {

                        onAssessmentListener.onAssessmentClick(assessments.get(position));

                    }
                }
            });

        }
    }

    public interface OnAssessmentListener{
        void onAssessmentClick(Assessment assessment);
    }

    public void setOnAssessmentListener(OnAssessmentListener listener) {
        this.onAssessmentListener = listener;
    }

}
