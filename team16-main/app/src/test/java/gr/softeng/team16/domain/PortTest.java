package gr.softeng.team16.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class PortTest {

    private Tower tower;
    private GPU gpu;
    private Memory memory;
    private Storage storage1;

    private Storage storage2;

    private CPU cpu;
    private Motherboard motherboard;
    private Port providedPort;
    private Port requiredPort;

    @Before
    public void setUp() throws Exception {

        providedPort = new Port("SATA", Port.Type.SATA, 4);
        requiredPort = new Port("SATA", Port.Type.SATA, 2);
        tower = new Tower(10001, "Cooler Master Tower", "CM", 89.99, "img", new String[]{"ATX", "EATX"}, 700, 500, new int[]{3, 5, 0}, 7, "450x200x400");
        motherboard = new Motherboard(20001, "ASUS Motherboard", "ASUS", 199.99, "img", "LGA1200", "ATX", "DDR4", 4, 2, 3, 6);
        gpu = new GPU(40001, "NVIDIA RTX 3080", "NVIDIA", 699.99, "img", 10, 1200, 1770, 285, 350, 2,1,3);
        cpu = new CPU(30001, "Intel i7-10700K", "Intel", 379.99, "img", "LGA1200", 8, 16, 3.8, 130,95,120,true);
        memory = new Memory(50001, "Corsair Vengeance LPX", "Corsair", 79.99, "img", "DDR4", 16,  2);
        storage1 = new Storage(70001, "Samsung 970 EVO Plus", "Samsung", 149.99, "img", 1, "SSD", "2.5");
        storage2 = new Storage(70002,"Solidigm P41 Plus","Solidigm",99.99,"img",2,"HDD","M.2");
    }


    @Test
    public void covers_returnsTrue_whenSuccessfulCoverage() {
        assertTrue(providedPort.covers(requiredPort)); //providedPort has 4 SATA ports, requiredPort needs 2 SATA ports
    }

    @Test
    public void covers_returnsTrue_whenExactCoverage() {
        Port exactProvidedPort = new Port("SATA", Port.Type.SATA, 2);
        assertTrue(exactProvidedPort.covers(requiredPort)); //exactProvidedPort has exactly 2 SATA ports, requiredPort needs 2 SATA ports
    }

    @Test
    public void covers_returnsFalse_whenInsufficientCoverage() {
        Port insufficientProvidedPort = new Port("SATA", Port.Type.SATA, 1);
        assertFalse(insufficientProvidedPort.covers(requiredPort)); //insufficientProvidedPort has only 1 SATA port, requiredPort needs 2 SATA ports
    }


    @Test
    public void provide_Tower_CorrectPortsSet() {
        HashSet<Port> providedPorts = Port.provide(tower);

        assertEquals(5, providedPorts.size());

        Port drive35Port = new Port("3.5", Port.Type.DRIVE_FORM_FACTOR, 5);
        assertTrue(providedPorts.contains(drive35Port));


        Port drive25Port = new Port("2.5", Port.Type.DRIVE_FORM_FACTOR, 3);
        assertTrue(providedPorts.contains(drive25Port));

        Port formATX = new Port("ATX", Port.Type.FORM_FACTOR);
        assertTrue(providedPorts.contains(formATX));

        Port formEATX = new Port("EATX", Port.Type.FORM_FACTOR);
        assertTrue(providedPorts.contains(formEATX));

        Port expansionPort = new Port("Expansion", Port.Type.EXPANSION, 7);
        assertTrue(providedPorts.contains(expansionPort));

    }

    @Test
    public void provide_Motherboard_CorrectPortsSet() {
        HashSet<Port> providedPorts = Port.provide(motherboard);

        assertEquals(5, providedPorts.size());

        Port sataPort = new Port("SATA", Port.Type.SATA, 6);
        assertTrue(providedPorts.contains(sataPort));

        Port m2Port = new Port("M2", Port.Type.M_2, 2);
        assertTrue(providedPorts.contains(m2Port));

        Port pciPort = new Port("PCIe_x16", Port.Type.PCIe_x16, 3);
        assertTrue(providedPorts.contains(pciPort));

        Port memoryPort = new Port("DDR4", Port.Type.RAM_SLOT, 4);
        assertTrue(providedPorts.contains(memoryPort));

        Port socketPort = new Port("LGA1200",Port.Type.CPU_SOCKET);
        assertTrue(providedPorts.contains(socketPort));
    }

    @Test
    public void provide_GPU_CorrectPortsSet() {
        HashSet<Port> providedPorts = Port.provide(gpu);

        assertEquals(2, providedPorts.size());

        Port hdmiPort = new Port("HDMI", Port.Type.HDMI, 2);
        assertTrue(providedPorts.contains(hdmiPort));

        Port displayPort = new Port("DISPLAY_PORT", Port.Type.DISPLAY_PORT, 1);
        assertTrue(providedPorts.contains(displayPort));

    }

    @Test
    public void provide_UnknownComponent_EmptyPortsSet() {
        Component unknownComponent = new Component(90001, "Unknown Component", "Unknown Manufacturer", 0.0, "img") {};

        HashSet<Port> providedPorts = Port.provide(unknownComponent);

        assertTrue(providedPorts.isEmpty());
    }




    @Test
    public void require_Tower_returnsEmptySet() {
        HashSet<Port> requiredPorts = Port.require(tower);

        assertTrue(requiredPorts.isEmpty());
        assertEquals(0, requiredPorts.size());

    }

    @Test
    public void require_Motherboard_returnsFormFactor() {
        HashSet<Port> requiredPorts = Port.require(motherboard);

        assertEquals(1, requiredPorts.size());

        Port formFactorPort = new Port("ATX", Port.Type.FORM_FACTOR);
        assertTrue(requiredPorts.contains(formFactorPort));
    }

    @Test
    public void require_CPU_returnsCpuSocket() {
        HashSet<Port> requiredPorts = Port.require(cpu);

        assertEquals(1, requiredPorts.size());

        Port socketPort = new Port("LGA1200", Port.Type.CPU_SOCKET);
        assertTrue(requiredPorts.contains(socketPort));
    }

    @Test
    public void require_GPU_returnsExpansionSlotsAndPCIe() {
        HashSet<Port> requiredPorts = Port.require(gpu);

        assertEquals(2, requiredPorts.size());

        Port expansionPort = new Port("Expansion", Port.Type.EXPANSION, 3);
        assertTrue(requiredPorts.contains(expansionPort));

        Port pciePort = new Port("PCIe_x16", Port.Type.PCIe_x16, 1);
        assertTrue(requiredPorts.contains(pciePort));
    }

    @Test
    public void require_Memory_returnsRAMSlots(){
        HashSet<Port> requiredPorts = Port.require(memory);

        assertEquals(1, requiredPorts.size());

        Port ramSlotPort = new Port("DDR4", Port.Type.RAM_SLOT, 2);
        assertTrue(requiredPorts.contains(ramSlotPort));
    }

    @Test
    public void require_Storage_returnsDriveFormfactorAndSATA(){
        HashSet<Port> requiredPorts = Port.require(storage1);

        assertEquals(2, requiredPorts.size());

        Port driveFormFactorPort = new Port("2.5", Port.Type.DRIVE_FORM_FACTOR);
        assertTrue(requiredPorts.contains(driveFormFactorPort));

        Port sataPort = new Port("SATA", Port.Type.SATA);
        assertTrue(requiredPorts.contains(sataPort));
    }

    @Test
    public void require_Storage_returnsM2(){
        HashSet<Port> requiredPorts = Port.require(storage2);

        assertEquals(1, requiredPorts.size());

        Port m2Port = new Port("M2", Port.Type.M_2);
        assertTrue(requiredPorts.contains(m2Port));
    }
}