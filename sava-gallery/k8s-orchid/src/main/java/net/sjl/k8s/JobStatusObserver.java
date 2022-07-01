package net.sjl.k8s;

import io.fabric8.kubernetes.api.model.Job;

public interface JobStatusObserver {
	
	public void onJobStatus(Job currentJob);
	
	public void onJobCompleted(Job currentJob);
	
	public void onJobException(Job currentJob);

}
