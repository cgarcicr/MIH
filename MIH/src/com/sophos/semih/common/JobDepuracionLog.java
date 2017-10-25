/**
 * 
 */
package com.sophos.semih.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sophos.semih.service.MantenimientoManager;

/**
 * @author FT
 *
 */
public class JobDepuracionLog implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MantenimientoManager mantenimientoManager = (MantenimientoManager)arg0.getJobDetail().getJobDataMap().get("mantenimientoManager");
		mantenimientoManager.depurarLogs(null);
	}

}
