package net.sjl.k8s;

import io.fabric8.kubernetes.api.model.Job;
import io.fabric8.kubernetes.api.model.JobStatus;
import net.sjl.k8s.model.JobDAG;

public class Scheduler implements JobStatusObserver {
	
	private static Scheduler instance = new Scheduler();
	
	private Scheduler() {}
	
	public static Scheduler getScheduler() {
		return instance;
	}
	
	public void dispatch(JobDAG jobDAG) {
	}
	
	
	@Override
	public void onJobStatus(Job currentJob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJobCompleted(Job currentJob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJobException(Job currentJob) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
