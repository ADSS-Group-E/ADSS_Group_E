package BuisnessLayer;

import java.util.LinkedList;
import java.util.List;

public class BranchController {
    private List<Branch> branchList=null;

    private  BranchController(){
        if(branchList==null)
            branchList=new LinkedList<>();
    }

    public static BranchController getInstance(){
        return BranchController;

    }


}
