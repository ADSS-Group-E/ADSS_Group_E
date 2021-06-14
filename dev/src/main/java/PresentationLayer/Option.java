package PresentationLayer;

import DataAccessLayer.Workers_Transport.Workers.Workers;
import PresentationLayer.Workers.DataTransferObjects.QualificationsDTO;
import PresentationLayer.Workers.DataTransferObjects.WorkerDTO;

import java.util.HashSet;
import java.util.Set;

public class Option {
    String description;
    Runnable runOption;
    Set<QualificationsDTO> requirements;

    public Option(String description, Runnable runOption) {
        this.description = description;
        this.runOption = runOption;
        this.requirements = new HashSet<>();
    }

    public Option(String description, Runnable runOption, QualificationsDTO requirement) {
        this.description = description;
        this.runOption = runOption;
        this.requirements = new HashSet<>();
        this.requirements.add(requirement);
    }

    public Option(String description, Runnable runOption, Set<QualificationsDTO> requirements) {
        this.description = description;
        this.runOption = runOption;
        this.requirements = requirements;
    }

    public void run(){
        runOption.run();
    }

    public String getDescription() {
        return description;
    }

    // Checks f a set of qualifications contains the required qualifications to display this option
    public boolean checkQualified(WorkerDTO workerDTO){
        if (requirements.isEmpty())
            return true;
        try{
            QualificationsDTO qualification = Workers.getTopicQualification(workerDTO.getID());
            return requirements.contains(qualification);
        }
        catch (Exception e){
            System.err.println("Something went wrong with checkQualified: " + e);
            return false;
        }
    }
}
