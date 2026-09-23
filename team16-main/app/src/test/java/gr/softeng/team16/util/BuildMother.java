package gr.softeng.team16.util;

import java.util.ArrayList;
import java.util.List;

import gr.softeng.team16.domain.*;

public class BuildMother {

    /**
     * Generates a sample Build object with various components for testing purposes.
     *
     * @return A Build object populated with sample components.
     */
    public static Build generateBuild() {
        Build build = new Build("Ready Build 1");
        fillBuild(build);
        return build;
    }

    /**
     * Fills a Build object with sample components for testing purposes.
     *
     * @param build The Build object to be filled.
     */
    public static void fillBuild(Build build) {
        build.addTower(generateTower());
        build.addMotherboard(generateMotherboard());
        build.addPeripheralComponent(generateCPU());
        build.addPeripheralComponent(generateGPU());
        build.addPeripheralComponent(generateMemory2());
        build.addPeripheralComponent(generateCooler());
        build.addPeripheralComponent(generateStorage());
        build.addPeripheralComponent(generatePSU());
    }

    // --- Tower ---

    public static Tower generateTower() {
        return new Tower(10000, "Corsair 4000D Airflow", "Corsair", 104.99, "img_url",
                new String[]{"ATX", "EATX", "Micro ATX", "Mini ITX"}, 0, 360,
                new int[]{2, 2}, 7, "453 mm x 230 mm x 466 mm");
    }

    public static List<Tower> generateTowerList() {
        List<Tower> towers = new ArrayList<>();
        towers.add(generateTower());
        towers.add(new Tower(10006, "Cooler Master MasterBox Q300L", "Cooler Master", 46.98, "img_url",
                new String[]{"Micro ATX", "Mini ITX"}, 0, 360, new int[]{2, 1}, 4, "387 mm x 230 mm x 378 mm"));
        towers.add(new Tower(10017, "Silverstone ALTA F2", "Silverstone", 999.99, "img_url",
                new String[]{"ATX", "EATX", "Micro ATX", "Mini ITX", "SSI CEB", "SSI EEB", "XL ATX"}, 0, 604, new int[]{3, 8}, 9, "658 mm x 261 mm x 576 mm"));
        towers.add(new Tower(10023, "Lian Li O11 Vision", "Lian Li", 129.99, "img_url",
                new String[]{"ATX", "EATX", "Micro ATX", "Mini ITX"}, 0, 455, new int[]{3, 2}, 7, "480 mm x 304 mm x 464.5 mm"));
        return towers;
    }

    // --- Motherboard ---

    public static Motherboard generateMotherboard() {
        return new Motherboard(20000, "MSI B650 GAMING PLUS WIFI", "MSI", 169.99, "img_url",
                "AM5", "ATX", "DDR5", 4, 2, 2, 4);
    }

    public static List<Motherboard> generateMotherboardList() {
        List<Motherboard> motherboards = new ArrayList<>();
        motherboards.add(generateMotherboard());
        motherboards.add(new Motherboard(20003, "MSI B550 GAMING GEN3", "MSI", 99.99, "img_url",
                "AM4", "ATX", "DDR4", 4, 1, 2, 6));
        motherboards.add(new Motherboard(20004, "MSI PRO Z790-A MAX WIFI", "MSI", 239.99, "img_url",
                "LGA1700", "ATX", "DDR5", 4, 4, 3, 6));
        motherboards.add(new Motherboard(20007, "MSI B450M-A PRO MAX II", "MSI", 69.98, "img_url",
                "AM4", "Micro ATX", "DDR4", 2, 1, 1, 4));
        return motherboards;
    }

    // --- CPU ---

    public static CPU generateCPU() {
        return new CPU(30000, "AMD Ryzen 7 7800X3D", "AMD", 269.0, "img_url",
                "AM5", 8, 4.2, 5.0, 120, 96, 128, false);
    }

    public static List<CPU> generateCPUList() {
        List<CPU> cpus = new ArrayList<>();
        cpus.add(generateCPU());
        cpus.add(new CPU(30002, "AMD Ryzen 5 5600X", "AMD", 129.99, "img_url",
                "AM4", 6, 3.7, 4.6, 65, 32, 128, true));
        cpus.add(new CPU(30004, "Intel Core i9-14900K", "Intel", 534.99, "img_url",
                "LGA1700", 24, 3.2, 6.0, 125, 36, 192, false));
        cpus.add(new CPU(30006, "Intel Core i5-12400F", "Intel", 128.33, "img_url",
                "LGA1700", 6, 2.5, 4.4, 65, 18, 128, true));
        return cpus;
    }

    // --- GPU ---

    public static GPU generateGPU() {
        return new GPU(40000, "MSI GeForce RTX 3060 Ventus 2X 12G", "MSI", 289.0, "img_url",
                12, 1320.0, 1777.0, 235, 170, 2, 1, 3);
    }

    public static List<GPU> generateGPUList() {
        List<GPU> gpus = new ArrayList<>();
        gpus.add(generateGPU());
        gpus.add(new GPU(40002, "Asus ROG STRIX GAMING OC", "Asus", 1979.99, "img_url",
                24, 2235.0, 2640.0, 358, 450, 2, 2, 3));
        gpus.add(new GPU(40020, "XFX GTS XXX", "XFX", 129.99, "img_url",
                8, 1366.0, 1386.0, 270, 185, 2, 1, 3));
        return gpus;
    }

    // --- Memory ---

    public static Memory generateMemory() {
        return new Memory(50000, "Corsair Vengeance LPX 16 GB", "Corsair", 44.99, "img_url",
                "DDR4", 16, 2);
    }

    public static Memory generateMemory2() {
        return new Memory(50005, "Corsair Vengeance LPX 16 GB v.2", "Corsair", 84.99, "img_url",
                "DDR5", 16, 2);
    }

    public static List<Memory> generateMemoryList() {
        List<Memory> memories = new ArrayList<>();
        memories.add(generateMemory());
        memories.add(new Memory(50001, "Corsair Vengeance 32 GB", "Corsair", 113.99, "img_url",
                "DDR5", 32, 2));
        memories.add(new Memory(50005, "G.Skill Trident Z5 RGB 64 GB", "G.Skill", 217.99, "img_url",
                "DDR5", 64, 2));
        return memories;
    }

    // --- Cooler ---

    public static Cooler generateCooler() {
        return new Cooler(60000, "Thermalright Peerless Assassin 120 SE", "Thermalright", 33.9, "img_url",
                1550, 25.6, new String[]{"AM4", "AM5", "LGA1150", "LGA1151", "LGA1155", "LGA1156", "LGA1200", "LGA1700"}, false);
    }

    public static List<Cooler> generateCoolerList() {
        List<Cooler> coolers = new ArrayList<>();
        coolers.add(generateCooler());
        coolers.add(new Cooler(60001, "NZXT Kraken Elite 360 RGB", "NZXT", 271.99, "img_url",
                1800, 30.6, new String[]{"AM4", "AM5", "sTR4", "sTRX4", "LGA1150", "LGA1151", "LGA1155", "LGA1156", "LGA1200", "LGA1700"}, true));
        coolers.add(new Cooler(60014, "Deepcool LS720 SE", "Deepcool", 89.99, "img_url",
                2250, 32.9, new String[]{"AM4", "AM5", "LGA1150", "LGA1151", "LGA1155", "LGA1156", "LGA1200", "LGA1700"}, true));
        return coolers;
    }

    // --- Storage ---

    public static Storage generateStorage() {
        return new Storage(70000, "Samsung 980 Pro", "Samsung", 179.0, "img_url",
                2, "SSD", "M.2");
    }

    public static List<Storage> generateStorageList() {
        List<Storage> storages = new ArrayList<>();
        storages.add(generateStorage());
        storages.add(new Storage(70074, "Crucial BX500", "Crucial", 69.98, "img_url",
                1, "SSD", "2.5"));
        storages.add(new Storage(70009, "Seagate Barracuda Compute", "Seagate", 64.98, "img_url",
                2, "7200 RPM", "3.5"));
        return storages;
    }

    // --- PSU ---

    public static PSU generatePSU() {
        return new PSU(80000, "Corsair RM750e (2023)", "Corsair", 99.99, "img_url",
                750);
    }

    public static List<PSU> generatePSUList() {
        List<PSU> psus = new ArrayList<>();
        psus.add(generatePSU());
        psus.add(new PSU(80002, "Corsair RM1000e (2023)", "Corsair", 179.99, "img_url",
                1000));
        psus.add(new PSU(80011, "Thermaltake Smart", "Thermaltake", 39.06, "img_url",
                500));
        return psus;
    }

    public static List<Build> generateBuildList() {
        List<Build> builds = new ArrayList<>();
        builds.add(generateBuild());

        Build build2 = new Build("Gaming Powerhouse");
        build2.addTower(generateTowerList().get(2));
        build2.addMotherboard(generateMotherboardList().get(2));
        build2.addPeripheralComponent(generateCPUList().get(2));
        build2.addPeripheralComponent(generateGPUList().get(1));
        build2.addPeripheralComponent(generateMemoryList().get(2));
        build2.addPeripheralComponent(generateCoolerList().get(1));
        build2.addPeripheralComponent(generateStorageList().get(0));
        build2.addPeripheralComponent(generatePSUList().get(1));
        builds.add(build2);

        return builds;
    }


}
