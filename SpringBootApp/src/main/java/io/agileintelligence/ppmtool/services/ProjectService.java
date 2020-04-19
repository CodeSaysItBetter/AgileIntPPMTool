package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * saveOrUpdate Method
     * @param project
     * @return
     */
    public Project saveOrUpdate(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch(Exception e){
            throw new ProjectIdException("Project ID: " +
                    project.getProjectIdentifier().toUpperCase() + ", already exists!");
        }

    }

    /**
     * findByProjectIdentifier Method
     * @param projectId
     * @return
     */
    public Project findByProjectIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project_ID: '" + projectId + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null) {
            throw new ProjectIdException("Cannot delete Project with ID: '"
                    + projectId + "'. This Project does not exist ");
        }
        projectRepository.delete(project);
    }

}
