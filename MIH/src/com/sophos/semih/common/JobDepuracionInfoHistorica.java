/**
 * 
 */
package com.sophos.semih.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sophos.semih.bean.MantenimientoBean;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.MantenimientoManager;

/**
 * @author FT
 *
 */
public class JobDepuracionInfoHistorica implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(ApplicationSetup.getInstance().getParamValue("DEPURACION_ACTIVA") != null && ApplicationSetup.getInstance().getParamValue("DEPURACION_ACTIVA").equals("S")){
			MantenimientoManager mantenimientoManager = (MantenimientoManager)arg0.getJobDetail().getJobDataMap().get("mantenimientoManager");
			EntidadManager entidadManager = (EntidadManager)arg0.getJobDetail().getJobDataMap().get("entidadManager");
			List<TMihEntidad> list = entidadManager.getEntidades(new TMihEntidad());
			Map<String, String> map = new HashMap<String, String>();
			for(TMihEntidad e:list){
				if(e.getDepurar() != null && e.getDepurar().equals("S") && e.getCampoFecha() != null && !e.getCampoFecha().equals(Constants.EMPTY_STRING)
						&& e.getTvigencia() != null && e.getTvigencia() > 0) {
					String resultado = mantenimientoManager.conteoInformacionDepurar(e.getNombre(), e.getCampoFecha(), e.getTvigencia());
					// El resultado debe ser al menos 3 caracteres, regExpirar|regTotales
					if (resultado != null && !resultado.equals(Constants.EMPTY_STRING) && resultado.length() >= 3) {
						map.put(e.getNombre(), resultado);
					}
				}
			}
			if (map.size() > 0) {
				MantenimientoBean.generarReporteDepuracion(map);
			}
			mantenimientoManager.depurarInfoHistorica(null);
		}
	}

}
