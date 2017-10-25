/**
 * 
 */
package com.sophos.semih.service;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihEntxusr;

/**
 * @author SD
 *
 */
public interface EntxusrManager extends Serializable {
	
	void insertEntxusr(TMihEntxusr transientInstance);

	void deleteEntxusr(TMihEntxusr persistentInstance);

	void updateEntxusr(TMihEntxusr detachedInstance);

	TMihEntxusr findById(com.sophos.semih.model.TMihEntxusrId id);

	List<TMihEntxusr> findByExample(TMihEntxusr instance);

}