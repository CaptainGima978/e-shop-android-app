package gr.softeng.team16.domain;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import gr.softeng.team16.util.BuildMother;


public class BuildTest {

    private Build readyBuild;
    private Build emptyBuild;
    private Tower tower2;
    private Motherboard asus;
    private CPU i5;
    private GPU gpu;
    private Memory ram;
    private Cooler cooler;
    private Storage m2SSD;
    private Storage HDD;
    private PSU psu300;

    @Before
    public void setUp() throws Exception {
        readyBuild = BuildMother.generateBuild();

        emptyBuild = new Build("Empty Build");

        tower2 = new Tower(10010, "NZXT H510", "NZXT", 120.0, "img_url",
                new String[]{"ATX", "Micro ATX"}, 0, 400,
                new int[]{2, 2}, 5, "428 x 210 x 472");
        asus = new Motherboard(20020, "ASUS ROG STRIX B550-F", "ASUS", 200.0, "img_url",
                "AM4", "ATX", "DDR5", 4,
                2, 2, 4);
        i5 = new CPU(30030, "Intel Core i5-11600K", "Intel", 270.0,
                "img_url", "LGA1200", 6, 3.9, 4.9, 125, 95,
                128, false);
        gpu = new GPU(40040, "AMD Radeon RX 6700 XT", "AMD", 480.0,
                "img_url", 12, 2321, 2581, 230,
                150, 2, 2, 2);
        ram = new Memory(50050, "G.Skill Trident Z RGB", "G.Skill",
                160.0, "img_url", "DDR5", 16, 2);
        cooler = new Cooler(60060, "Noctua NH-D15", "Noctua",
                90.0, "img_url", 220, 24, new String[]{"LGA1200", "LGA1150"}, false);
        m2SSD = new Storage(70070, "WD Black SN850", "Western Digital",
                150.0, "img_url", 1, "SSD", "M.2");
        HDD = new Storage(70080, "Seagate Barracuda", "Seagate",
                80.0, "img_url", 2, "HDD", "3.5");
        psu300 = new PSU(80090, "Corsair CX450", "Corsair", 60.0,
                "img_url", 300);
    }

    @Test
    public void checkPeripheralComponents() {
        CPU cpu = readyBuild.getCpu();
        assertEquals("AMD Ryzen 7 7800X3D", cpu.getName());
        GPU gpu = readyBuild.getGpu().get(0);
        assertEquals("MSI GeForce RTX 3060 Ventus 2X 12G", gpu.getName());
        Memory memory = readyBuild.getMemory().get(0);
        assertEquals(16, memory.getCapacity());
        Cooler cooler = readyBuild.getCooler();
        assertEquals("Thermalright", cooler.getManufacturer());
        Storage storage = readyBuild.getStorage().get(0);
        assertEquals(2, storage.getCapacity());
        PSU psu = readyBuild.getPsu();
        assertEquals(750, psu.getWattage());
    }

    @Test
    public void checkCompletedBuild() {
        assertTrue(readyBuild.isCompleted());
        assertEquals(1230.86, readyBuild.getPrice(), 0.01);
        assertEquals(355, readyBuild.getWattDemand());
        assertEquals(32, readyBuild.getTotalMemory());
        assertEquals(2, readyBuild.getTotalStorage());
    }

    @Test
    public void addTower() {
        boolean added = emptyBuild.addTower(tower2);
        assertTrue(added);
        assertEquals(tower2, emptyBuild.getTower());
    }

    @Test
    public void removeTower() {
        Tower tower = readyBuild.getTower();
        assertEquals("Corsair 4000D Airflow", tower.getName());
        boolean removed = readyBuild.removeTower();
        assertTrue(removed);
        assertNull(readyBuild.getTower());
        assertFalse(readyBuild.isCompleted());
    }

    @Test
    public void addMotherboard() {
        boolean added = emptyBuild.addMotherboard(asus);
        assertTrue(added);
        assertEquals(asus, emptyBuild.getMotherboard());
    }

    @Test
    public void removeMotherboard() {
        Motherboard motherboard = readyBuild.getMotherboard();
        assertEquals("ATX", motherboard.getFormFactor());
        boolean removed = readyBuild.removeMotherboard();
        assertTrue(removed);
        assertNull(readyBuild.getMotherboard());
        assertFalse(readyBuild.isCompleted());
    }

    @Test
    public void addPeripheralComponent() {
        assertTrue(emptyBuild.addPeripheralComponent((i5)));
        assertEquals("Intel", emptyBuild.getCpu().getManufacturer());

        assertTrue(emptyBuild.addPeripheralComponent(gpu));
        assertEquals(230, emptyBuild.getGpu().get(0).getLength());

        assertTrue(emptyBuild.addPeripheralComponent(cooler));
        assertEquals(220, emptyBuild.getCooler().getRpm());

        assertTrue(emptyBuild.addPeripheralComponent(ram));
        assertTrue(emptyBuild.addPeripheralComponent(ram));
        assertEquals(64, emptyBuild.getTotalMemory());

        assertTrue(emptyBuild.addPeripheralComponent(HDD));
        assertTrue(emptyBuild.addPeripheralComponent(m2SSD));
        assertEquals(3, emptyBuild.getTotalStorage());

        assertTrue(emptyBuild.addPeripheralComponent(psu300));
        assertEquals(300, emptyBuild.getPsu().getWattage());
    }

    @Test
    public void removePeripheralComponent() {
        CPU cpu = readyBuild.getCpu();
        assertTrue(readyBuild.removePeripheralComponent(cpu));
        assertNull(readyBuild.getCpu());
        assertNull(readyBuild.getCooler()); // cooler also expected to be removed

        GPU graphics = readyBuild.getGpu().get(0);
        assertTrue(readyBuild.removePeripheralComponent(graphics));

        Memory ram2 = readyBuild.getMemory().get(0);
        assertTrue(readyBuild.removePeripheralComponent(ram2));
        assertEquals(0, readyBuild.getTotalMemory());

        Storage disk = readyBuild.getStorage().get(0);
        assertTrue(readyBuild.removePeripheralComponent(disk));
        assertEquals(0, readyBuild.getTotalStorage());

        PSU power = readyBuild.getPsu();
        assertTrue(readyBuild.removePeripheralComponent(power));
        assertNull(readyBuild.getPsu());
    }

    @Test
    public void addComponentUnsuccessful() {
        assertFalse(readyBuild.addTower(tower2));
        assertEquals("Corsair 4000D Airflow", readyBuild.getTower().getName());

        assertFalse(readyBuild.addMotherboard(asus));

        assertFalse(readyBuild.addPeripheralComponent(i5));
        assertFalse(readyBuild.addPeripheralComponent(psu300));
    }

    @Test
    public void addIncompatibleCooler() {
        Cooler c = readyBuild.getCooler();
        readyBuild.removePeripheralComponent(c);

        assertFalse(readyBuild.addPeripheralComponent(cooler));
        assertNull(readyBuild.getCooler());
    }

    @Test
    public void checkProvidedPorts() {
        assertEquals(0, emptyBuild.getProvidedPorts().size());
        emptyBuild.addTower(tower2);
        assertTrue(emptyBuild.getProvidedPorts().contains(new Port("ATX", Port.Type.FORM_FACTOR)));

        emptyBuild.addMotherboard(asus);
        assertTrue(emptyBuild.getProvidedPorts().contains(new Port("AM4", Port.Type.CPU_SOCKET)));
    }

    @Test
    public void checkRequiredPorts() {
        assertEquals(0, emptyBuild.getRequiredPorts().size());
        emptyBuild.addMotherboard(asus);
        assertTrue(emptyBuild.getRequiredPorts().contains(new Port("ATX", Port.Type.FORM_FACTOR)));

        emptyBuild.addPeripheralComponent(i5);
        assertTrue(emptyBuild.getRequiredPorts().contains(new Port("LGA1200", Port.Type.CPU_SOCKET)));

        // check unmatched
        assertTrue(emptyBuild.getUnmatchedPorts().contains(new Port("LGA1200", Port.Type.CPU_SOCKET)));
    }


    @Test
    public void checkUnmatchedPorts() {
        assertEquals(0, readyBuild.getUnmatchedPorts().size());

        readyBuild.addPeripheralComponent(ram);
        readyBuild.addPeripheralComponent(ram);
        assertTrue(readyBuild.getUnmatchedPorts().contains(new Port("DDR5", Port.Type.RAM_SLOT)));

        readyBuild.removeMotherboard();

        assertTrue(readyBuild.getUnmatchedPorts().contains(new Port("AM5", Port.Type.CPU_SOCKET)));
        assertTrue(readyBuild.getUnmatchedPorts().contains(new Port("PCIe_x16", Port.Type.PCIe_x16)));
        assertTrue(readyBuild.getUnmatchedPorts().contains(new Port("DDR5", Port.Type.RAM_SLOT)));
        assertTrue(readyBuild.getUnmatchedPorts().contains(new Port("M2", Port.Type.M_2)));
    }
}
