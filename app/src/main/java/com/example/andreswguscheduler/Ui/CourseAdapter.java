package com.example.andreswguscheduler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andreswguscheduler.Entities.Course;
import com.example.andreswguscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private List<Course> mCourses = new ArrayList<>();
    private OnCourseListener mOnCourseListener;

    //public CourseAdapter() {super(DIFF_CALLBACK);}

    private static final DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<Course>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {

            return oldItem.getCourseId() == newItem.getCourseId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {

            return oldItem.getCourseTitle().equals(newItem.getCourseTitle()) && oldItem.getCourseAnticipatedEndDate().equals(newItem.getCourseAnticipatedEndDate()) && oldItem.getCourseStatus().equals(newItem.getCourseStatus());

        }
    };

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);

        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

        Course currentCourse = mCourses.get(position);
        holder.textViewTitle.setText(currentCourse.getCourseTitle());
        holder.textViewStartDate.setText(currentCourse.getCourseStartDate());
        holder.textViewAnticipatedEndDate.setText(currentCourse.getCourseAnticipatedEndDate());
        holder.textViewStatus.setText(currentCourse.getCourseStatus());

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setmCourses(List<Course> mCourses) {

        this.mCourses = mCourses;
        notifyDataSetChanged();

    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewAnticipatedEndDate;
        private TextView textViewStatus;

        public CourseHolder(@NonNull View itemView) {

            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_start_date);
            textViewAnticipatedEndDate = itemView.findViewById(R.id.text_view_anticipated_end_date);
            textViewStatus = itemView.findViewById(R.id.text_view_status);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (mOnCourseListener != null && position != RecyclerView.NO_POSITION) {

                        mOnCourseListener.onCourseClick(mCourses.get(position));

                    }

                }
            });


        }
    }

    public interface OnCourseListener {
        void onCourseClick(Course course);
    }

    public void setOnCourseListener(OnCourseListener listener) {
        this.mOnCourseListener = listener;
    }

}
