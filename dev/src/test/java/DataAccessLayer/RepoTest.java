package DataAccessLayer;

import BusinessLayer.Workers_Transport.WorkersPackage.WorkersFacade;
import DataAccessLayer.Workers_Transport.Repo;
import PresentationLayer.Workers_Transport.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class RepoTest {
    private static Scanner reader = new Scanner(System.in);
    private static WorkersFacade workersFacade = WorkersFacade.getInstance();

    public static void main(String[] args) {
        Repo.createDatabase();
        //Main.systemInitialize();

    }

    public static void systemInitialize() {
        BankAccountDTO bankAccountDTO1 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "123456");
        BankAccountDTO bankAccountDTO2 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "222222");
        BankAccountDTO bankAccountDTO3 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "060000");
        BankAccountDTO bankAccountDTO4 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "111222");
        BankAccountDTO bankAccountDTO5 = new BankAccountDTO("Bank Hapoalim", "123", "455446");
        BankAccountDTO bankAccountDTO6 = new BankAccountDTO("Bank Hapoalim", "123", "764309");
        BankAccountDTO bankAccountDTO7 = new BankAccountDTO("Bank Yahav", "190", "135262");
        BankAccountDTO bankAccountDTO8 = new BankAccountDTO("Bank Yahav", "190", "333444");
        BankAccountDTO bankAccountDTO9 = new BankAccountDTO("Bank Yahav", "190", "600200");
        BankAccountDTO bankAccountDTO10 = new BankAccountDTO("Bank Yahav", "190", "100200");
        BankAccountDTO bankAccountDTO11 = new BankAccountDTO("Bank Yahav", "190", "300111");
        BankAccountDTO bankAccountDTO12 = new BankAccountDTO("Bank Yahav", "190", "500432");
        BankAccountDTO bankAccountDTO13 = new BankAccountDTO("Bank Yahav", "190", "100243");
        BankAccountDTO bankAccountDTO14 = new BankAccountDTO("Bank Hapoalim", "123", "111897");
        BankAccountDTO bankAccountDTO15 = new BankAccountDTO("Bank Hapoalim", "123", "453672");
        BankAccountDTO bankAccountDTO16 = new BankAccountDTO("Bank Hapoalim", "123", "367829");
        BankAccountDTO bankAccountDTO17 = new BankAccountDTO("Bank Hapoalim", "123", "647389");
        BankAccountDTO bankAccountDTO18 = new BankAccountDTO("Bank Hapoalim", "123", "938476");
        BankAccountDTO bankAccountDTO19 = new BankAccountDTO("Bank Hapoalim", "123", "467378");
        BankAccountDTO bankAccountDTO20 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "245673");
        BankAccountDTO bankAccountDTO21 = new BankAccountDTO("Bank Otsar Ha-Hayal", "318", "463782");
        BankAccountDTO bankAccountDTO22 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "394873");
        BankAccountDTO bankAccountDTO23 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "647383");
        BankAccountDTO bankAccountDTO24 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "293847");
        BankAccountDTO bankAccountDTO25 = new BankAccountDTO("Bank Mizrahi-Tefahot", "216", "647380");

        LocalDate startDate1 = LocalDate.of(2020, 5, 23);
        LocalDate startDate2 = LocalDate.of(2021, 1, 15);
        LocalDate startDate3 = LocalDate.of(2021, 2, 22);
        LocalDate startDate4 = LocalDate.of(2019, 3, 17);
        LocalDate startDate5 = LocalDate.of(2021, 2, 13);
        LocalDate startDate6 = LocalDate.of(2018, 4, 2);
        LocalDate startDate7 = LocalDate.of(2019, 5, 5);
        LocalDate startDate8 = LocalDate.of(2015, 9, 28);
        LocalDate startDate9 = LocalDate.of(2017, 3, 13);
        LocalDate startDate10 = LocalDate.of(2020, 4, 2);
        LocalDate startDate11 = LocalDate.of(2021, 1, 1);
        LocalDate startDate12 = LocalDate.of(2018, 4, 2);

        HiringConditionsDTO hiringConditionsDTO1 = new HiringConditionsDTO(10000, "Baillie Gifford American", 10, 5);
        HiringConditionsDTO hiringConditionsDTO2 = new HiringConditionsDTO(12000, "Baillie Gifford American", 7, 3);
        HiringConditionsDTO hiringConditionsDTO3 = new HiringConditionsDTO(8000, "Baillie Gifford American", 12, 7);
        HiringConditionsDTO hiringConditionsDTO4 = new HiringConditionsDTO(10000, "Baillie Gifford American", 6, 6);
        HiringConditionsDTO hiringConditionsDTO5 = new HiringConditionsDTO(15000, "Baillie Gifford American", 15, 7);
        HiringConditionsDTO hiringConditionsDTO6 = new HiringConditionsDTO(5500, "Baillie Gifford American", 8, 6);
        HiringConditionsDTO hiringConditionsDTO7 = new HiringConditionsDTO(7000, "Baillie Gifford American", 9, 4);
        HiringConditionsDTO hiringConditionsDTO8 = new HiringConditionsDTO(18000, "Fundsmith Equity", 6, 5);
        HiringConditionsDTO hiringConditionsDTO9 = new HiringConditionsDTO(9720, "Fundsmith Equity", 7, 3);
        HiringConditionsDTO hiringConditionsDTO10 = new HiringConditionsDTO(13250, "Fundsmith Equity", 12, 4);
        HiringConditionsDTO hiringConditionsDTO11 = new HiringConditionsDTO(2500, "Fundsmith Equity", 6, 6);
        HiringConditionsDTO hiringConditionsDTO12 = new HiringConditionsDTO(3750, "Fundsmith Equity", 9, 7);
        HiringConditionsDTO hiringConditionsDTO13 = new HiringConditionsDTO(7000, "Fundsmith Equity", 8, 6);
        HiringConditionsDTO hiringConditionsDTO14 = new HiringConditionsDTO(10000, "Fundsmith Equity", 9, 4);
        HiringConditionsDTO hiringConditionsDTO15 = new HiringConditionsDTO(8500, "Baillie Gifford Positive Change", 6, 6);
        HiringConditionsDTO hiringConditionsDTO16 = new HiringConditionsDTO(20000, "Baillie Gifford Positive Change", 14, 4);
        HiringConditionsDTO hiringConditionsDTO17 = new HiringConditionsDTO(25000, "Baillie Gifford Positive Change", 5, 6);
        HiringConditionsDTO hiringConditionsDTO18 = new HiringConditionsDTO(30000, "Baillie Gifford Positive Change", 25, 7);
        HiringConditionsDTO hiringConditionsDTO19 = new HiringConditionsDTO(2500, "Baillie Gifford China", 9, 7);
        HiringConditionsDTO hiringConditionsDTO20 = new HiringConditionsDTO(9000, "Baillie Gifford China", 8, 6);
        HiringConditionsDTO hiringConditionsDTO21 = new HiringConditionsDTO(15000, "Baillie Gifford China", 9, 4);
        HiringConditionsDTO hiringConditionsDTO22 = new HiringConditionsDTO(7500, "Baillie Gifford China", 6, 6);
        HiringConditionsDTO hiringConditionsDTO23 = new HiringConditionsDTO(25000, "Baillie Gifford China", 14, 4);
        HiringConditionsDTO hiringConditionsDTO24 = new HiringConditionsDTO(12500, "Baillie Gifford China", 5, 6);
        HiringConditionsDTO hiringConditionsDTO25 = new HiringConditionsDTO(15000, "Baillie Gifford China", 25, 7);


        List<QualificationsDTO> qualificationsDTO1 = new LinkedList<>();
        qualificationsDTO1.add(QualificationsDTO.BranchManager);
        qualificationsDTO1.add(QualificationsDTO.Assistant);
        qualificationsDTO1.add(QualificationsDTO.Storekeeper);


        List<QualificationsDTO> qualificationsDTO2 = new LinkedList<>();
        qualificationsDTO2.add(QualificationsDTO.Human_Resources_Director);
        qualificationsDTO2.add(QualificationsDTO.Guard);
        qualificationsDTO2.add(QualificationsDTO.Assistant);
        qualificationsDTO2.add(QualificationsDTO.Storekeeper);

        List<QualificationsDTO> qualificationsDTO3 = new LinkedList<>();
        qualificationsDTO3.add(QualificationsDTO.ShiftManager);
        qualificationsDTO3.add(QualificationsDTO.Arranger);
        qualificationsDTO3.add(QualificationsDTO.logisticsManager);

        List<QualificationsDTO> qualificationsDTO4 = new LinkedList<>();
        qualificationsDTO4.add(QualificationsDTO.Human_Resources_Director);
        qualificationsDTO4.add(QualificationsDTO.Storekeeper);
        qualificationsDTO4.add(QualificationsDTO.Guard);

        List<QualificationsDTO> qualificationsDTO5 = new LinkedList<>();
        qualificationsDTO5.add(QualificationsDTO.Human_Resources_Director);
        qualificationsDTO5.add(QualificationsDTO.Storekeeper);
        qualificationsDTO5.add(QualificationsDTO.Guard);
        qualificationsDTO5.add(QualificationsDTO.Assistant);
        qualificationsDTO5.add(QualificationsDTO.Cashier);


        List<QualificationsDTO> qualificationsDTO6 = new LinkedList<>();
        qualificationsDTO6.add(QualificationsDTO.Human_Resources_Director);
        qualificationsDTO6.add(QualificationsDTO.Storekeeper);
        qualificationsDTO6.add(QualificationsDTO.Guard);
        qualificationsDTO6.add(QualificationsDTO.Assistant);
        qualificationsDTO6.add(QualificationsDTO.ShiftManager);
        qualificationsDTO6.add(QualificationsDTO.Cashier);

        Boolean favoriteShifts1[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
        Boolean favoriteShifts2[][] = new Boolean[][]{{true, false}, {true, true}, {false, true}, {true, true}, {true, true}, {false, false}, {true, true}};
        Boolean favoriteShifts3[][] = new Boolean[][]{{true, true}, {false, false}, {false, false}, {true, true}, {false, true}, {true, false}, {true, true}};
        Boolean favoriteShifts4[][] = new Boolean[][]{{true, false}, {true, true}, {true, true}, {true, true}, {false, true}, {true, false}, {true, true}};
        Boolean favoriteShifts5[][] = new Boolean[][]{{true, false}, {false, true}, {false, false}, {false, true}, {true, false}, {true, false}, {true, true}};
        Boolean favoriteShifts6[][] = new Boolean[][]{{false, false}, {true, true}, {false, false}, {true, true}, {false, false}, {true, false}, {true, true}};
        Boolean favoriteShifts7[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {true, true}, {false, true}, {true, false}, {false, false}};
        Boolean favoriteShifts8[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {false, false}, {true, true}, {true, false}, {false, true}};
        Boolean favoriteShifts9[][] = new Boolean[][]{{true, false}, {false, true}, {false, false}, {false, false}, {true, true}, {true, false}, {false, true}};
        Boolean favoriteShifts10[][] = new Boolean[][]{{true, false}, {true, true}, {false, false}, {false, false}, {false, true}, {true, false}, {false, true}};

        Boolean cantWork1[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
        Boolean cantWork2[][] = new Boolean[][]{{false, true}, {false, false}, {true, false}, {false, false}, {false, false}, {true, true}, {false, false}};
        Boolean cantWork3[][] = new Boolean[][]{{false, false}, {true, true}, {true, true}, {false, false}, {true, false}, {false, true}, {false, false}};
        Boolean cantWork4[][] = new Boolean[][]{{false, true}, {false, false}, {false, false}, {false, false}, {true, false}, {false, true}, {false, false}};
        Boolean cantWork5[][] = new Boolean[][]{{false, true}, {true, false}, {true, true}, {false, false}, {false, true}, {false, true}, {false, false}};
        Boolean cantWork6[][] = new Boolean[][]{{true, true}, {false, false}, {true, true}, {false, false}, {true, true}, {false, true}, {false, false}};
        Boolean cantWork7[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {false, false}, {true, false}, {false, true}, {true, true}};
        Boolean cantWork8[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {true, true}, {false, false}, {false, true}, {true, false}};
        Boolean cantWork9[][] = new Boolean[][]{{false, true}, {true, false}, {true, true}, {true, true}, {false, false}, {false, true}, {true, false}};
        Boolean cantWork10[][] = new Boolean[][]{{false, true}, {false, false}, {true, true}, {true, true}, {true, false}, {false, true}, {true, false}};


        AvailableWorkDaysDTO availableWorkDaysDTO1 = new AvailableWorkDaysDTO(favoriteShifts1, cantWork1);
        AvailableWorkDaysDTO availableWorkDaysDTO2 = new AvailableWorkDaysDTO(favoriteShifts2, cantWork2);
        AvailableWorkDaysDTO availableWorkDaysDTO3 = new AvailableWorkDaysDTO(favoriteShifts3, cantWork3);
        AvailableWorkDaysDTO availableWorkDaysDTO4 = new AvailableWorkDaysDTO(favoriteShifts4, cantWork4);
        AvailableWorkDaysDTO availableWorkDaysDTO5 = new AvailableWorkDaysDTO(favoriteShifts5, cantWork5);
        AvailableWorkDaysDTO availableWorkDaysDTO6 = new AvailableWorkDaysDTO(favoriteShifts6, cantWork6);
        AvailableWorkDaysDTO availableWorkDaysDTO7 = new AvailableWorkDaysDTO(favoriteShifts7, cantWork7);
        AvailableWorkDaysDTO availableWorkDaysDTO8 = new AvailableWorkDaysDTO(favoriteShifts8, cantWork8);
        AvailableWorkDaysDTO availableWorkDaysDTO9 = new AvailableWorkDaysDTO(favoriteShifts9, cantWork9);
        AvailableWorkDaysDTO availableWorkDaysDTO10 = new AvailableWorkDaysDTO(favoriteShifts10, cantWork10);


        WorkerDTO branchManager = new WorkerDTO("Yoad", "Ohayon", "323079103", bankAccountDTO1, hiringConditionsDTO1, availableWorkDaysDTO1, qualificationsDTO1);
        WorkerDTO HRD = new WorkerDTO("Omer", "Shitrit", "208060210", bankAccountDTO2, hiringConditionsDTO2, availableWorkDaysDTO2, qualificationsDTO5);
        WorkerDTO workerDTO1 = new WorkerDTO("Gal", "Brown", "207896321", bankAccountDTO3, hiringConditionsDTO3, availableWorkDaysDTO3, qualificationsDTO3);
        WorkerDTO workerDTO2 = new WorkerDTO("Daniel", "Levi", "209456234", bankAccountDTO4, hiringConditionsDTO5, availableWorkDaysDTO5, qualificationsDTO5);
        WorkerDTO workerDTO4 = new WorkerDTO("Yoav", "Yehuda", "323456123", bankAccountDTO6, hiringConditionsDTO6, availableWorkDaysDTO6, qualificationsDTO6);
        WorkerDTO workerDTO5 = new WorkerDTO("Ehud", "Shamgar", "254987413", bankAccountDTO7, hiringConditionsDTO7, availableWorkDaysDTO7, qualificationsDTO4);
        WorkerDTO workerDTO6 = new WorkerDTO("Yossi", "Bookobza", "209456534", bankAccountDTO8, hiringConditionsDTO8, availableWorkDaysDTO8, qualificationsDTO3);
        WorkerDTO workerDTO7 = new WorkerDTO("Matan", "Sabag", "206342302", bankAccountDTO9, hiringConditionsDTO9, availableWorkDaysDTO9, qualificationsDTO1);
        WorkerDTO workerDTO8 = new WorkerDTO("Avishy", "Yehuda", "320842543", bankAccountDTO10, hiringConditionsDTO10, availableWorkDaysDTO10, qualificationsDTO2);
        WorkerDTO workerDTO9 = new WorkerDTO("Dani", "Lerner", "206894441", bankAccountDTO11, hiringConditionsDTO11, availableWorkDaysDTO6, qualificationsDTO4);
        WorkerDTO workerDTO10 = new WorkerDTO("Raz", "Cohen", "229656234", bankAccountDTO12, hiringConditionsDTO12, availableWorkDaysDTO8, qualificationsDTO1);
        WorkerDTO workerDTO11 = new WorkerDTO("Eren", "Ben David", "209509970", bankAccountDTO13, hiringConditionsDTO13, availableWorkDaysDTO4, qualificationsDTO5);
        WorkerDTO workerDTO12 = new WorkerDTO("Or", "Efraim", "326556129", bankAccountDTO14, hiringConditionsDTO14, availableWorkDaysDTO1, qualificationsDTO6);
        WorkerDTO workerDTO13 = new WorkerDTO("Bar", "Zomer", "332545111", bankAccountDTO15, hiringConditionsDTO15, availableWorkDaysDTO2, qualificationsDTO2);
        WorkerDTO workerDTO14 = new WorkerDTO("Ido", "Barak", "289774534", bankAccountDTO16, hiringConditionsDTO16, availableWorkDaysDTO5, qualificationsDTO3);
        WorkerDTO workerDTO15 = new WorkerDTO("Daniel", "maymon", "339342302", bankAccountDTO17, hiringConditionsDTO17, availableWorkDaysDTO9, qualificationsDTO5);
        WorkerDTO workerDTO16 = new WorkerDTO("Kobi", "Swissa", "290842543", bankAccountDTO18, hiringConditionsDTO18, availableWorkDaysDTO6, qualificationsDTO6);
        WorkerDTO workerDTO17 = new WorkerDTO("Ben", "Cohen", "333232987", bankAccountDTO19, hiringConditionsDTO19, availableWorkDaysDTO3, qualificationsDTO1);
        WorkerDTO workerDTO18 = new WorkerDTO("Baruch", "Tzion", "279587570", bankAccountDTO20, hiringConditionsDTO20, availableWorkDaysDTO4, qualificationsDTO4);
        WorkerDTO workerDTO19 = new WorkerDTO("Moses", "Efraim", "321642111", bankAccountDTO21, hiringConditionsDTO21, availableWorkDaysDTO1, qualificationsDTO2);
        WorkerDTO workerDTO20 = new WorkerDTO("Shalom", "Brefman", "205123654", bankAccountDTO22, hiringConditionsDTO22, availableWorkDaysDTO2, qualificationsDTO5);
        WorkerDTO workerDTO21 = new WorkerDTO("Barak", "Bar", "226134562", bankAccountDTO23, hiringConditionsDTO23, availableWorkDaysDTO5, qualificationsDTO3);
        WorkerDTO workerDTO22 = new WorkerDTO("Avi", "Ohayon", "325431754", bankAccountDTO24, hiringConditionsDTO24, availableWorkDaysDTO9, qualificationsDTO5);
        WorkerDTO workerDTO23 = new WorkerDTO("Dganit", "Refeli", "298764234", bankAccountDTO25, hiringConditionsDTO25, availableWorkDaysDTO6, qualificationsDTO6);

        workersFacade.addBranch(1, branchManager, HRD,workerDTO1);
        //workersFacade.addWorker(workerDTO1, 1);
        workersFacade.addWorker(workerDTO2, 1);
        //facade.addWorker(workerDTO3, 1);
        workersFacade.addWorker(workerDTO4, 1);
        workersFacade.addWorker(workerDTO5, 1);
        workersFacade.addWorker(workerDTO6, 1);
        workersFacade.addWorker(workerDTO7, 1);
        workersFacade.addWorker(workerDTO8, 1);
        workersFacade.addWorker(workerDTO9, 1);
        workersFacade.addWorker(workerDTO10, 1);
        workersFacade.addWorker(workerDTO11, 1);
        workersFacade.addWorker(workerDTO12, 1);
        workersFacade.addWorker(workerDTO13, 1);
        workersFacade.addWorker(workerDTO14, 1);
        workersFacade.addWorker(workerDTO15, 1);
        workersFacade.addWorker(workerDTO16, 1);
        workersFacade.addWorker(workerDTO17, 1);
        workersFacade.addWorker(workerDTO18, 1);
        workersFacade.addWorker(workerDTO19, 1);
        workersFacade.addWorker(workerDTO20, 1);
        workersFacade.addWorker(workerDTO21, 1);
        workersFacade.addWorker(workerDTO22, 1);
        workersFacade.addWorker(workerDTO23, 1);
        int add = 6;
        for (int i = 0; i < 7; i++) {
            workersFacade.addShiftDemands(1, LocalDate.now().plusDays(add), ShiftTypeDTO.Morning, new ShiftDemandsDTO(LocalDate.now().plusDays(add), 1, 1, 1, 1, 1));
            workersFacade.addShiftDemands(1, LocalDate.now().plusDays(add), ShiftTypeDTO.Evening, new ShiftDemandsDTO(LocalDate.now().plusDays(add), 1, 1, 1, 1, 1));
            add++;
        }
    }
}
