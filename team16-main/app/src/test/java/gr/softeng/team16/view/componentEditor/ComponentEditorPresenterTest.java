package gr.softeng.team16.view.componentEditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import gr.softeng.team16.domain.CPU;
import gr.softeng.team16.domain.Component;
import gr.softeng.team16.domain.Cooler;
import gr.softeng.team16.domain.GPU;
import gr.softeng.team16.domain.Memory;
import gr.softeng.team16.domain.Motherboard;
import gr.softeng.team16.domain.PSU;
import gr.softeng.team16.domain.Storage;
import gr.softeng.team16.domain.Tower;
import gr.softeng.team16.util.BuildMother;
import gr.softeng.team16.util.LocalRepository;

public class ComponentEditorPresenterTest {

    private ComponentEditorPresenter presenter;
    private ComponentEditViewStub view;
    private LocalRepository repo;

    @Before
    public void setUp() {
        presenter = new ComponentEditorPresenter();
        view = new ComponentEditViewStub();
        repo = new LocalRepository();
        presenter.setView(view);
        presenter.setRepo(repo);
    }

    @Test
    public void loadComponent__Basic_fields_mapping() {
        Component component = BuildMother.generateCPU();
        presenter.setComponent(component);
        presenter.loadComponent();

        assertEquals(component.getName(), view.getName());
        assertEquals(String.valueOf(component.getId()), view.getId());
        assertEquals(component.getName(), view.getFieldValue("Name"));
        assertEquals(component.getManufacturer(), view.getFieldValue("Manufacturer"));
        assertEquals(String.valueOf(component.getPrice()), view.getFieldValue("Price"));
        assertEquals(component.getImg(), view.getFieldValue("Image (URL)"));
    }

    @Test
    public void loadComponent__Tower_specific_fields() {
        Tower tower = BuildMother.generateTower();
        presenter.setComponent(tower);
        presenter.loadComponent();

        assertEquals(String.join(", ", tower.getFormFactor()), view.getFieldValue("Form Factor"));
        assertEquals(String.valueOf(tower.getPowerSupply()), view.getFieldValue("Power Supply"));
        assertEquals(String.valueOf(tower.getMaxGpuLength()), view.getFieldValue("Max GPU Length (mm)"));
        assertEquals(String.valueOf(tower.getDriveBays("2_5")), view.getFieldValue("Drive Bays 2.5"));
        assertEquals(String.valueOf(tower.getDriveBays("3_5")), view.getFieldValue("Drive Bays 3.5"));
        assertEquals(String.valueOf(tower.getExpansionSlots()), view.getFieldValue("Expansion Slots"));
        assertEquals(tower.getDimensions(), view.getFieldValue("Dimensions (HxWxD)"));
    }

    @Test
    public void loadComponent__Motherboard_specific_fields() {
        Motherboard mb = BuildMother.generateMotherboard();
        presenter.setComponent(mb);
        presenter.loadComponent();

        assertEquals(mb.getFormFactor(), view.getFieldValue("Form Factor"));
        assertEquals(mb.getSocket(), view.getFieldValue("Socket"));
        assertEquals(mb.getMemoryType(), view.getFieldValue("Memory Type"));
        assertEquals(String.valueOf(mb.getMemorySlots()), view.getFieldValue("Memory Slots"));
        assertEquals(String.valueOf(mb.getM2Slots()), view.getFieldValue("M.2 Slots"));
        assertEquals(String.valueOf(mb.getPciSlots()), view.getFieldValue("PCI Slots"));
        assertEquals(String.valueOf(mb.getSataSlots()), view.getFieldValue("SATA Slots"));
    }

    @Test
    public void loadComponent__CPU_specific_fields() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        presenter.loadComponent();

        assertEquals(cpu.getSocket(), view.getFieldValue("Socket"));
        assertEquals(String.valueOf(cpu.getCoreCount()), view.getFieldValue("Core Count"));
        assertEquals(String.valueOf(cpu.getClock()), view.getFieldValue("Clock"));
        assertEquals(String.valueOf(cpu.getClockBoost()), view.getFieldValue("Clock Boost"));
        assertEquals(String.valueOf(cpu.getTdp()), view.getFieldValue("TDP"));
        assertEquals(String.valueOf(cpu.getCache()), view.getFieldValue("Cache"));
        assertEquals(String.valueOf(cpu.getMemorySupport()), view.getFieldValue("Memory Support"));
        assertEquals(String.valueOf(cpu.getCoolerIn()), view.getFieldValue("Cooler Included"));
    }

    @Test
    public void loadComponent__GPU_specific_fields() {
        GPU gpu = BuildMother.generateGPU();
        presenter.setComponent(gpu);
        presenter.loadComponent();

        assertEquals(String.valueOf(gpu.getMemory()), view.getFieldValue("Memory (GB)"));
        assertEquals(String.valueOf(gpu.getClock()), view.getFieldValue("Clock"));
        assertEquals(String.valueOf(gpu.getClockBoost()), view.getFieldValue("Clock Boost"));
        assertEquals(String.valueOf(gpu.getLength()), view.getFieldValue("Length"));
        assertEquals(String.valueOf(gpu.getTdp()), view.getFieldValue("TDP"));
        assertEquals(String.valueOf(gpu.getExpansionSlots()), view.getFieldValue("Expansion Slots"));
        assertEquals(String.valueOf(gpu.getHdmiOut()), view.getFieldValue("HDMI Outputs"));
        assertEquals(String.valueOf(gpu.getDpOut()), view.getFieldValue("Display Port Outputs"));
    }

    @Test
    public void loadComponent__Memory_specific_fields() {
        Memory ram = BuildMother.generateMemory();
        presenter.setComponent(ram);
        presenter.loadComponent();

        assertEquals(ram.getType(), view.getFieldValue("Type"));
        assertEquals(String.valueOf(ram.getCapacity()), view.getFieldValue("Capacity (GB)"));
        assertEquals(String.valueOf(ram.getModules()), view.getFieldValue("Modules"));
    }

    @Test
    public void loadComponent__Cooler_specific_fields() {
        Cooler cooler = BuildMother.generateCooler();
        presenter.setComponent(cooler);
        presenter.loadComponent();

        assertEquals(String.valueOf(cooler.getRpm()), view.getFieldValue("RPM"));
        assertEquals(String.valueOf(cooler.getNoiseLevel()), view.getFieldValue("Noise Level"));
        assertEquals(String.join(", ", cooler.getSocket()), view.getFieldValue("Sockets"));
        assertEquals(String.valueOf(cooler.getWaterCooled()), view.getFieldValue("Water Cooled"));
    }

    @Test
    public void loadComponent__Storage_specific_fields() {
        Storage storage = BuildMother.generateStorage();
        presenter.setComponent(storage);
        presenter.loadComponent();

        assertEquals(String.valueOf(storage.getCapacity()), view.getFieldValue("Capacity (TB)"));
        assertEquals(storage.getType(), view.getFieldValue("Type"));
        assertEquals(storage.getDriveFormFactor(), view.getFieldValue("Drive Interface"));
    }

    @Test
    public void loadComponent__PSU_specific_fields() {
        PSU psu = BuildMother.generatePSU();
        presenter.setComponent(psu);
        presenter.loadComponent();

        assertEquals(String.valueOf(psu.getWattage()), view.getFieldValue("Wattage"));
    }

    @Test(expected = NullPointerException.class)
    public void loadComponent__Null_component_handling() {
        presenter.setComponent(null);
        presenter.loadComponent();
    }

    @Test
    public void updateField__Success_on_shared_fields() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);

        assertTrue(presenter.updateField("Name", "New CPU Name"));
        assertEquals("New CPU Name", cpu.getName());

        assertTrue(presenter.updateField("Manufacturer", "New Manufacturer"));
        assertEquals("New Manufacturer", cpu.getManufacturer());

        assertTrue(presenter.updateField("Image (URL)", "http://new.image"));
        assertEquals("http://new.image", cpu.getImg());
    }

    @Test
    public void updateField__Double_parsing_for_Price() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);

        assertTrue(presenter.updateField("Price", "299.99"));
        assertEquals(299.99, cpu.getPrice(), 0.001);

        assertFalse(presenter.updateField("Price", "abc"));
    }

    @Test
    public void updateField__Integer_parsing_for_various_fields() {
        PSU psu = BuildMother.generatePSU();
        presenter.setComponent(psu);

        assertFalse(presenter.updateField("Wattage", "not_a_number"));
    }

    @Test
    public void updateField__Tower_Form_Factor_list_logic() {
        Tower tower = BuildMother.generateTower();
        presenter.setComponent(tower);

        assertTrue(presenter.updateField("Form Factor", "ATX, Micro ATX , Mini ITX"));
        List<String> forms = tower.getFormFactor();
        assertEquals(3, forms.size());
        assertEquals("ATX", forms.get(0));
        assertEquals("Micro ATX", forms.get(1));
        assertEquals("Mini ITX", forms.get(2));
    }

    @Test
    public void updateField__Motherboard_Form_Factor_string_logic() {
        Motherboard mb = BuildMother.generateMotherboard();
        presenter.setComponent(mb);

        assertTrue(presenter.updateField("Form Factor", "Micro ATX"));
        assertEquals("Micro ATX", mb.getFormFactor());
    }

    @Test
    public void updateField__Drive_Bays_map_preservation() {
        Tower tower = BuildMother.generateTower();
        presenter.setComponent(tower);
        
        int original35 = tower.getDriveBays("3_5");

        assertTrue(presenter.updateField("Drive Bays 2.5", "10"));
        assertEquals(Integer.valueOf(10), tower.getDriveBays("2_5"));
        assertEquals(Integer.valueOf(original35), tower.getDriveBays("3_5"));

        assertTrue(presenter.updateField("Drive Bays 3.5", "5"));
        assertEquals(Integer.valueOf(5), tower.getDriveBays("3_5"));
        assertEquals(Integer.valueOf(10), tower.getDriveBays("2_5"));
    }

    @Test
    public void updateField__Polymorphic_field__Socket_() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        assertTrue(presenter.updateField("Socket", "AM4"));
        assertEquals("AM4", cpu.getSocket());

        Motherboard mb = BuildMother.generateMotherboard();
        presenter.setComponent(mb);
        assertTrue(presenter.updateField("Socket", "LGA1700"));
        assertEquals("LGA1700", mb.getSocket());
    }

    @Test
    public void updateField__Polymorphic_field__Expansion_Slots_() {
        Tower tower = BuildMother.generateTower();
        presenter.setComponent(tower);
        assertTrue(presenter.updateField("Expansion Slots", "7"));
        assertEquals(7, tower.getExpansionSlots());

        GPU gpu = BuildMother.generateGPU();
        presenter.setComponent(gpu);
        assertTrue(presenter.updateField("Expansion Slots", "3"));
        assertEquals(3, gpu.getExpansionSlots());
    }

    @Test
    public void updateField__Boolean_parsing_logic() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        assertTrue(presenter.updateField("Cooler Included", "true"));
        assertTrue(cpu.getCoolerIn());
        assertTrue(presenter.updateField("Cooler Included", "false"));
        assertFalse(cpu.getCoolerIn());

        Cooler cooler = BuildMother.generateCooler();
        presenter.setComponent(cooler);
        assertTrue(presenter.updateField("Water Cooled", "true"));
        assertTrue(cooler.getWaterCooled());
    }

    @Test
    public void updateField__ClassCastException_prevention() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        // Attempting to update a PSU-only field on a CPU
        assertFalse(presenter.updateField("Wattage", "750"));
    }

    @Test
    public void updateField__Null_component_guard() {
        presenter.setComponent(null);
        assertFalse(presenter.updateField("Name", "New Name"));
    }

    @Test
    public void updateField__Invalid_fieldName() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        assertFalse(presenter.updateField("NonExistentField", "Value"));
    }

    @Test
    public void saveComponent__Repository_and_View_interaction() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        presenter.saveComponent();

        assertTrue(view.isClosedWithSuccess());
    }

    @Test
    public void deleteComponent__Repository_and_View_interaction() {
        CPU cpu = BuildMother.generateCPU();
        presenter.setComponent(cpu);
        presenter.deleteComponent();

        assertTrue(view.isClosedWithDeletion());
    }

    @Test
    public void cancelEdit__View_interaction() {
        presenter.cancelEdit();
        assertTrue(view.isClosedWithCancel());
    }

    @Test
    public void getInstance__Singleton_pattern_check() {
        ComponentEditorPresenter instance1 = ComponentEditorPresenter.getInstance();
        ComponentEditorPresenter instance2 = ComponentEditorPresenter.getInstance();
        assertSame(instance1, instance2);
    }
}
