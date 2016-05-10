/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.gestioneprogetti.services;

import it.uniroma2.gestioneprogetti.dao.IDAOFactory;
import it.uniroma2.gestioneprogetti.domain.Project;
import it.uniroma2.gestioneprogetti.request.EmptyRQS;
import it.uniroma2.gestioneprogetti.request.ProjectRQS;
import it.uniroma2.gestioneprogetti.response.EmptyRES;
import it.uniroma2.gestioneprogetti.response.FindProjectsRES;
import it.uniroma2.gestioneprogetti.response.ProjectRES;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Team Talocci
 */
@Service("ProjectService")
public class ProjectService implements IProjectService {
    
    private final static Logger LOGGER = Logger.getLogger(ProjectService.class.getName());
    private final static String LAYERLBL = "****SERVICE LAYER**** ";
    
    //Qui di seguito viene iniettata la dipendenza della DaoFactory
    @Autowired
    private IDAOFactory daoFactory;
    
    /**
     * Il metodo insertProject prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, copia il contenuto di tale oggetto in un nuovo oggetto Project e lancia,
     * tramite la DaoFactory, il metodo insertProject dello strato "Domain". L'esito dell'operazione
     * viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato) all'interno
     * del quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectRQS
     * @return EmptyRES response
     * @author L.Camerlengo
     */
    @Override
    public EmptyRES insertProject(ProjectRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio insertProject");
        Project project= new Project(request.getId(),request.getName(),request.getDescription(),request.getStatus(),request.getBudget(),request.getCost(),request.getProjectManager());
        String message=daoFactory.getProjectDao().insertProject(project);
        EmptyRES response= new EmptyRES();
        response.setMessage(message);
        if(message.equals("FAIL")) {
            response.setErrorCode("1");
            response.setEsito(false);
        }
        else {
            response.setErrorCode("0");
            response.setEsito(true);           
        }
        return response;
    } 
    
    /**
     * Il metodo updateProject prende l'oggetto RQS proveniente dallo strato "Application", ovvero quello
     * del Controller, copia il contenuto di tale oggetto in un nuovo oggetto Project e lancia,
     * tramite la DaoFactory, il metodo updateProject dello strato "Domain". L'esito dell'operazione
     * viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato) all'interno
     * del quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectRQS
     * @return EmptyRES response
     * @author L.Camerlengo
     */
    @Override
    public EmptyRES updateProject(ProjectRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio updateProject");
        Project project= new Project(request.getId(),request.getName(),request.getDescription(),request.getStatus(),request.getBudget(),request.getCost(),request.getProjectManager());
        String message=daoFactory.getProjectDao().updateProject(project);
        EmptyRES response= new EmptyRES();
        response.setMessage(message);
        if(message.equals("FAIL")) {
            response.setErrorCode("1");
            response.setEsito(false);
        }
        else {
            response.setErrorCode("0");
            response.setEsito(true);           
        }
        return response;
    }
    
    /** 
     * Il metodo deleteProject prende l'oggetto ProjectRQS proveniente dallo strato "Application", 
     * ovvero quello del Controller, che contiene l'id del progetto da cancellare.
     * L'esito dell'operazione viene memorizzato in una EmptyRES (perché non bisogna restitutire nessun dato)
     * all'interno del quale viene settato un messaggio di fallimento o successo.
     * @param request ProjectRQS oggetto che incapsula i dati della richiesta 
     * @return EmptyRES response
     * @author Lorenzo Svezia, Luca Talocci
     */    
    @Override
    public EmptyRES deleteProject(ProjectRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio deleteProject");
        Project project = new Project();
        project.setId(request.getId());
        
        String message = daoFactory.getProjectDao().deleteProject(project);
        
        EmptyRES response = new EmptyRES();
        if (message.equals("FAIL")) {
            response.setMessage(message);
            response.setErrorCode("1");
            response.setEsito(false);
        }else{
            response.setMessage(message);
            response.setErrorCode("0");
            response.setEsito(true); 	
	}
        return response;
    }
        
    /** 
     * Il metodo retrieveProject prende un oggetto ProjectRQS proveniente dallo strato "Application", 
     * ovvero quello del Controller, contenente solo l'id del progetto da visualizzare.
     * Questo metodo sfrutta i metodi forniti dallo strato "Domain" per effettuare la retrieve 
     * del progetto.
     * L'esito dell'operazione viene memorizzato in un oggetto ProjectRES all'interno del quale 
     * viene settato un messaggio di fallimento o successo.
     * @param request ProjectRQS oggetto che incapsula i dati della richiesta 
     * @return ProjectRES response
     * @author Lorenzo Svezia, Luca Talocci
     */
    @Override
    public ProjectRES retrieveProject(ProjectRQS request) {
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio retrieveProject");
        ProjectRES response = new ProjectRES();
                
        Project project = new Project();
        project.setId(request.getId());
        
        String result = daoFactory.getProjectDao().retrieveProject(project);
        
        if (result.equals("FAIL")) {
            response.setMessage(result);
            response.setErrorCode("1");
            response.setEsito(false);
            return response;
        }
        
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setStatus(project.getStatus());
        response.setBudget(project.getBudget());
        response.setCost(project.getCost());
        response.setProjectManager(project.getProjectManager());
        
        
        response.setMessage(result);
        response.setErrorCode("0");
        response.setEsito(true);
        return response;
    }
    
    /**
     * Il metodo displayProjects prende l'EmptyRQS proveniente dallo strato "Application"
     * soltanto per convenzione. Inizializza una lista di progetti che viene riempita dal 
     * metodo displayProjects dello strato "Domain" e poi viene inizializzato 
     * l'oggetto FindProjectsRES tramite la lista precedentemente riempita.
     * Infine viene anche settato un messaggio per comunicare l'esito dell'operazione.
     * @param request EmptyRQS
     * @return FindProjectsRES response
     * @author L.Camerlengo
     */
    @Override
    public FindProjectsRES displayProjects(EmptyRQS request){
        LOGGER.log(Level.INFO, LAYERLBL + "Chiamata a servizio findAllProjects");
        List<Project> projectsList=daoFactory.getProjectDao().displayProjects();
        FindProjectsRES response=new FindProjectsRES();
        if(projectsList==null){
            response.setMessage("FAIL");
            response.setErrorCode("1");
            response.setEsito(false);
        }
        else {
            response.setMessage("SUCCESS");
            response.setErrorCode("0");
            response.setEsito(true);           
        }
        List<ProjectRES> projectsRESList=new ArrayList<>();
        for (Project pr:projectsList){
            ProjectRES projectRES= new ProjectRES(pr.getId(),pr.getName(),pr.getDescription(),pr.getStatus(),pr.getBudget(),pr.getCost(),pr.getProjectManager());
            projectsRESList.add(projectRES);
        }
        response.setProjectsList(projectsRESList);
        return response;
    }
    
}
