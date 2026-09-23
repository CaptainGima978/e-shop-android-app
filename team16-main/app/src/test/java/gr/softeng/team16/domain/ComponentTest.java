package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ComponentTest {

    private Tower tower;
    private Motherboard motherboard;
    private CPU cpu;
    private GPU gpu;
    private Memory ram;
    private Cooler cooler;
    private Storage m2SSD;
    private Storage HDD;
    private PSU psu;


    @Before
    public void setUp() throws Exception {
        tower = new Tower(10010, "NZXT H510", "NZXT", 120.0, "img_url",
                new String[]{"ATX", "Micro ATX"}, 0, 400,
                new int[]{2, 2}, 5, "428 x 210 x 472");
        motherboard = new Motherboard(20020, "ASUS ROG STRIX B550-F", "ASUS", 200.0, "img_url",
                "AM4", "ATX", "DDR5", 4,
                2, 2, 4);
        cpu = new CPU(30030, "Intel Core i5-11600K", "Intel", 270.0,
                "img_url", "LGA1200", 6, 3.9, 4.9, 125, 95,
                128, false);
        gpu = new GPU(40040, "AMD Radeon RX 6700 XT", "AMD", 480.0,
                "img_url", 12, 2321, 2581, 230,
                150, 2, 2, 2);
        ram = new Memory(50050, "G.Skill Trident Z RGB", "G.Skill",
                160.0, "img_url", "DDR5", 16, 2);
        cooler = new Cooler(60060, "Noctua NH-D15", "Noctua",
                90.0, "img_url", 220, 24, new String[]{"LGA1200", "LGA1150"}, false);
        HDD = new Storage(70080, "Seagate Barracuda", "Seagate",
                80.0, "img_url", 2, "HDD", "3.5");
        psu= new PSU(80090, "Corsair CX450", "Corsair", 60.0,
                "img_url", 700);
    }

    @Test
    public void checkTower() {
        assertEquals(400, tower.getMaxGpuLength());
        tower.setMaxGpuLength(380);
        assertEquals(380, tower.getMaxGpuLength());
    }

    @Test
    public void checkMotherboard() {
            assertEquals("ATX", motherboard.getFormFactor());
            motherboard.setFormFactor("EATX");
            assertEquals("EATX", motherboard.getFormFactor());
    }

    @Test
    public void checkCPU() {
        assertEquals("LGA1200", cpu.getSocket());
        cpu.setSocket("AM4");
        assertEquals("AM4", cpu.getSocket());
    }

    @Test
    public void checkGPU() {
        assertEquals(2321, gpu.getClock(), 0.01);
        gpu.setClock(2400);
        assertEquals(2400, gpu.getClock(), 0.01);
    }

    @Test
    public void checkRam() {
        assertEquals("DDR5", ram.getType());
        ram.setType("DDR4");
        assertEquals("DDR4", ram.getType());
    }

    @Test
    public void checkCooler() {
        assertEquals(220, cooler.getRpm());
        cooler.setRpm(200);
        assertEquals(200, cooler.getRpm());
    }

    @Test
    public void checkStorage() {
        assertEquals("3.5", HDD.getDriveFormFactor());
        HDD.setFormFactor("M.2");
        assertEquals("M.2", HDD.getDriveFormFactor());
    }
}