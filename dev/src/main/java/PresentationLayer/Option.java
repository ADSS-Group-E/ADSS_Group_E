package PresentationLayer;

import PresentationLayer.Workers_Transport.QualificationsDTO;
import PresentationLayer.Workers_Transport.WorkerDTO;

import java.util.Collection;
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
        return workerDTO.getQualifications().containsAll(requirements);
    }
}
