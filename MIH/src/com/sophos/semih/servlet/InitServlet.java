package com.sophos.semih.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.JobDepuracionInfoHistorica;
import com.sophos.semih.common.JobDepuracionLog;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.MantenimientoManager;
import com.sophos.semih.service.ParametroManager;

/**
 * @author FT
 * Servlet implementation class InitServlet
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(InitServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
    }
    
    public void init() {
    	try {
    		// Cargar singleton con parámetros
    		ApplicationSetup applicationSetup = ApplicationSetup.getInstance();
    		BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
    		ParametroManager parametroManager = (ParametroManager)factory.getBean("parametroManager");
    		applicationSetup.setParams(parametroManager.getParametros(new TMihParametro()));
    		// Programar tareas
    		scheduleJobs();
    		
    	}catch (Exception e) {
    		log.error("Could not locate properties from data base.", e);
    	}
    }

	/**
	 * @throws SchedulerException
	 */
	public void scheduleJobs() {
		try {
			JobDetail jobInfo = JobBuilder.newJob(JobDepuracionInfoHistorica.class).withIdentity("JobDepuracionInfoHistorica").build();
			JobDetail jobLog = JobBuilder.newJob(JobDepuracionLog.class).withIdentity("JobDepuracionLog").build();
			
			Integer horaInicioInfo;
			try {
				horaInicioInfo = new Integer(ApplicationSetup.getInstance().getParamValue("INICIO_DEPURACION_INFO"));
			} catch (NumberFormatException e) {
				horaInicioInfo = 1;
			}
			Integer horaInicioLog;
			try {
				horaInicioLog = new Integer(ApplicationSetup.getInstance().getParamValue("INICIO_DEPURACION_LOGS"));
			} catch (NumberFormatException e) {
				horaInicioLog = 0;
			}
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MINUTE,00);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
			cal.set(Calendar.HOUR_OF_DAY,horaInicioInfo);
			Date infoDate = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY,horaInicioLog);
			Date logDate = cal.getTime();
			
			Trigger depuracionInfoTrigger = TriggerBuilder.newTrigger()
					.withIdentity("depuracionInfoTrigger").startAt(infoDate).withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInHours(new Integer(ApplicationSetup.getInstance().getParamValue("TIME_INFO_JOB"))).repeatForever()).build();
			Trigger depuracionLogTrigger = TriggerBuilder.newTrigger()
					.withIdentity("depuracionLogTrigger").startAt(logDate).withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInHours(new Integer(ApplicationSetup.getInstance().getParamValue("TIME_LOG_JOB"))).repeatForever()).build();
			
			BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			MantenimientoManager mantenimientoManager = (MantenimientoManager) factory.getBean("mantenimientoManager");
			EntidadManager entidadManager = (EntidadManager) factory.getBean("entidadManager");
			jobInfo.getJobDataMap().put("mantenimientoManager", mantenimientoManager);
			jobInfo.getJobDataMap().put("entidadManager", entidadManager);
			jobLog.getJobDataMap().put("mantenimientoManager", mantenimientoManager);
			
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobInfo, depuracionInfoTrigger);
			scheduler.scheduleJob(jobLog, depuracionLogTrigger);
		} catch (NumberFormatException e) {
			log.error("No existe parámetro de tiempo para alguno de los jobs configurados", e);
		} catch (SchedulerException e) {
			log.error("Existe un problema agendando alguno de los jobs configurados", e);
		}
	}

	/**
	 * Obtiene el FacesContext
	 * @param request
	 * @param response
	 * @return
	 */
	public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
		// Get current FacesContext.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check current FacesContext.
		if (facesContext == null) {

			// Create new Lifecycle.
			LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
			Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

			// Create new FacesContext.
			FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
			facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);

			// Create new View.
			UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
			facesContext.setViewRoot(view);

			// Set current FacesContext.
			FacesContextWrapper.setCurrentInstance(facesContext);
		}

		return facesContext;
	}

	// Wrap the protected FacesContext.setCurrentInstance() in a inner class.
    private static abstract class FacesContextWrapper extends FacesContext {
        protected static void setCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
