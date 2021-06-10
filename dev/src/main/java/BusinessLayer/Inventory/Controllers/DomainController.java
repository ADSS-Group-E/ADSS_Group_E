package BusinessLayer.Inventory.Controllers;

import BusinessLayer.Inventory.DomainObjects.DomainObject;
import DataAccessLayer.Inventory.DataAccessObjects.DataAccessObject;
import PresentationLayer.Inventory.DataTransferObjects.DataTransferObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class DomainController {
    protected HashMap<Integer, DomainObject> identityMap;
    DataAccessObject DAO;
    private boolean loadedAll = false;

    public DomainController(DataAccessObject DAO) {
        identityMap = new HashMap<>();
        this.DAO = DAO;
    }

    protected DomainObject get(int id){
        if (identityMap.containsKey(id))
            return identityMap.get(id);
        else{
            // Not in business layer, try to lazy load
            DataTransferObject dataTransferObject = DAO.get(id);

            if (dataTransferObject == null){
                System.err.println( "id " + id + " does not exist.");
                return null;
            }

            DomainObject newObject = buildDomainObjectFromDto(dataTransferObject);

            identityMap.put(id,newObject);
            return newObject;
        }
    }

    // Adders
    protected void add(DomainObject domainObject){
        if (DAO.insert(buildDtoFromDomainObject(domainObject)))
            identityMap.put(domainObject.getId(),domainObject);
    }

    // Remover
    protected void remove (int id) {
        DAO.delete(id);
        identityMap.remove(id);
    }

    public ArrayList<DomainObject> getList() {
        if (loadedAll)
            return new ArrayList<>(identityMap.values());
        // Load not yet loaded products from database
        ArrayList<DataTransferObject> dataTransferObjects = DAO.selectAllGeneric();
        for (DataTransferObject dataTransferObject:
                dataTransferObjects) {
            if (!identityMap.containsKey(dataTransferObject.getId())){
                DomainObject newObject = buildDomainObjectFromDto(dataTransferObject);
                identityMap.put(newObject.getId(),newObject);
            }
        }
        loadedAll = true;
        return new ArrayList<>(identityMap.values());
    }

    protected abstract DomainObject buildDomainObjectFromDto(DataTransferObject dataTransferObject);

    protected abstract DataTransferObject buildDtoFromDomainObject(DomainObject domainObject);


}
