package PresentationLayer;

import BusinessLayer.Workers_Transport.ResponseT;
import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import PresentationLayer.Workers_Transport.WorkerDTO;

public class LoginOptionsMenu extends OptionsMenu {
    QuickSelectWorker quickSelectWorker;

    public LoginOptionsMenu(CommandLineInterface parentCLI) {
        super(parentCLI);
        quickSelectWorker = new QuickSelectWorker(parentCLI);
        prompt = "\nPlease choose a login method:";

        int i=1;
        options.put(i++, new Option( "DEV - Quick select worker type",() -> {quickSelectWorker.enter();}));
        options.put(i++, new Option( "Login with ID",() -> {quickSelectWorker.login();}));
        options.put(i, new Option( "Back", this::back));
    }


    class QuickSelectWorker extends OptionsMenu{
        private static final String BRANCH_MANAGER_ID = "323079103";
        private static final String HRD_ID = "208060210";
        private static final String STOREKEEPER_ID = "208060210";

        private WorkersFacade workersFacade = WorkersFacade.getInstance();

        public QuickSelectWorker(CommandLineInterface parentCLI) {
            super(parentCLI);

            int i=1;
            options.put(i++, new Option( "Branch Manager",() -> {loginWithID(BRANCH_MANAGER_ID);}));
            options.put(i++, new Option( "Human Resources Director",() -> {loginWithID(HRD_ID);}));
            options.put(i++, new Option( "Storekeeper",() -> {loginWithID(STOREKEEPER_ID);}));
            options.put(i, new Option( "Back", this::back));
        }

        protected void login(){
            System.out.println("Please enter your ID:");
            String id = in.next();
            loginWithID(id);
        }

        protected void loginWithID(String id) {
            ResponseT<WorkerDTO> workerDTOResponseT = workersFacade.findDTOWorkerByID(id);
            if (workerDTOResponseT.isErrorOccurred()){
                System.out.println(workerDTOResponseT.getErrorMessage());
                return;
            }
            WorkerDTO workerDTO = workerDTOResponseT.getValue();
            if (workerDTO == null) {
                System.out.println("Could not find a worker with ID " + id);
                return;
            }

            parentCLI.setLoggedInWorker(workerDTO);

            // TODO: Implement workerDTO toString
            System.out.printf("Logged in successfully as:\n%s\n%n",workerDTO.toString());

            parentCLI.getMainInventoryOptionsMenu().enter();
        }
    }
}
