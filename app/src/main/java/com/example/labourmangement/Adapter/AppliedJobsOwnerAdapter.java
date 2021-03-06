package com.example.labourmangement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.labourmangement.Contractor.JobApplyDetails;
import com.example.labourmangement.Owner.JobApplyDetailsOwner;
import com.example.labourmangement.R;
import com.example.labourmangement.model.AppliedJobsModel;
import com.example.labourmangement.model.AppliedJobsModelOwner;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class AppliedJobsOwnerAdapter extends RecyclerView.Adapter<AppliedJobsOwnerAdapter.ViewHolder> {
    private Context context;
    private List<AppliedJobsModelOwner> appliedjob;

    public  interface OnItemClickListener{
        void onClick(View view);

        void onItemClick(int position);
    }
    public AppliedJobsOwnerAdapter(Context context, List appliedjob) {
        this.context = context;
        this.appliedjob = appliedjob;
    }

    @Override
    public AppliedJobsOwnerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.applied_job_list, parent, false);
        AppliedJobsOwnerAdapter.ViewHolder viewHolder = new AppliedJobsOwnerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppliedJobsOwnerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(appliedjob.get(position));

        AppliedJobsModelOwner pu = appliedjob.get(position);

        Log.d("job id","40 "+pu.getJob_id());
        // holder.job_id.setText(pu.getJob_id());
        holder.jobtitle.setText(pu.getJob_title());
        holder.job_details.setText(pu.getJob_details());
        holder.job_wages.setText(pu.getJob_wages());
        holder.job_area.setText(pu.getJob_area());
        holder.job_id.setText(pu.getJob_id());
        holder.appliedby.setText(pu.getApplied_by());
        holder.created_by.setText(pu.getCreated_by());
        holder.applieddate.setText(pu.getApplied_date());
        holder.labor_name.setText(pu.getLabor_name());
        holder.contractor_name.setText(pu.getContractor_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetailsOwner.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("created_by",pu.getCreated_by());
                intent.putExtra("applied_date",pu.getApplied_date());
                intent.putExtra("contractor_name",pu.getContractor_name());
                intent.putExtra("labor_name",pu.getLabor_name());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appliedjob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView jobtitle;
        public TextView job_details;
        public TextView job_wages;
        public  TextView job_area;
        public  TextView job_id;
        public  TextView appliedby;
        public  TextView applieddate;
        public  TextView created_by;
        public  TextView labor_name;
        public TextView contractor_name;

        public ViewHolder(View itemView) {
            super(itemView);

            jobtitle = itemView.findViewById(R.id.job_titleapply);
            job_details = itemView.findViewById(R.id.job_detailsapply);
            job_wages = itemView.findViewById(R.id.job_wagesapply);
            job_area = itemView.findViewById(R.id.job_areaapply);
            job_id = itemView.findViewById(R.id.job_idapply);
            appliedby = itemView.findViewById(R.id.applied_byapply);
            applieddate = itemView.findViewById(R.id.applieddateapply);
            created_by = itemView.findViewById(R.id.createdbyapply);
            labor_name= itemView.findViewById(R.id.applied_byapplyname);
            contractor_name= itemView.findViewById(R.id.createdbyapplyname);

        }
    }
}
