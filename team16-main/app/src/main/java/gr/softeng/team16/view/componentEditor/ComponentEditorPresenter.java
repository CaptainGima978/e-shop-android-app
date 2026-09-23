package gr.softeng.team16.view.componentEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr.softeng.team16.data.DataRepository;
import gr.softeng.team16.domain.*;

public class ComponentEditorPresenter {
    private static ComponentEditorPresenter instance;
    private ComponentEditorView view;
    private DataRepository repo;
    private Component component;

    public ComponentEditorPresenter() {
    }

    public static ComponentEditorPresenter getInstance() {
        if (instance == null) {
            instance = new ComponentEditorPresenter();
        }
        return instance;
    }

    public void setView(ComponentEditorView view) {
        this.view = view;
    }
    public void setRepo(DataRepository repo) {
        this.repo = repo;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    public void loadComponent() {
        view.setName(component.getName());
        view.setID(String.valueOf(component.getId()));

        view.showField("Name", component.getName());
        view.showField("Manufacturer", component.getManufacturer());
        view.showField("Price", String.valueOf(component.getPrice()));
        view.showField("Image (URL)", component.getImg());

        switch (component.getClass().getSimpleName()) {
            case "Tower":
                Tower tower = (Tower) component;
                view.showField("Form Factor", String.join(", ", tower.getFormFactor()));
                view.showField("Power Supply", String.valueOf(tower.getPowerSupply()));
                view.showField("Max GPU Length (mm)", String.valueOf(tower.getMaxGpuLength()));
                view.showField("Drive Bays 2.5", String.valueOf(tower.getDriveBays("2_5")));
                view.showField("Drive Bays 3.5", String.valueOf(tower.getDriveBays("3_5")));
                view.showField("Expansion Slots", String.valueOf(tower.getExpansionSlots()));
                view.showField("Dimensions (HxWxD)", tower.getDimensions());

                break;
            case "Motherboard":
                Motherboard motherboard = (Motherboard) component;
                view.showField("Form Factor", motherboard.getFormFactor());
                view.showField("Socket", motherboard.getSocket());
                view.showField("Memory Type", motherboard.getMemoryType());
                view.showField("Memory Slots", String.valueOf(motherboard.getMemorySlots()));
                view.showField("M.2 Slots", String.valueOf(motherboard.getM2Slots()));
                view.showField("PCI Slots", String.valueOf(motherboard.getPciSlots()));
                view.showField("SATA Slots", String.valueOf(motherboard.getSataSlots()));

                break;
            case "CPU":
                CPU cpu = (CPU) component;
                view.showField("Socket", cpu.getSocket());
                view.showField("Core Count", String.valueOf(cpu.getCoreCount()));
                view.showField("Clock", String.valueOf(cpu.getClock()));
                view.showField("Clock Boost", String.valueOf(cpu.getClockBoost()));
                view.showField("TDP", String.valueOf(cpu.getTdp()));
                view.showField("Cache", String.valueOf(cpu.getCache()));
                view.showField("Memory Support", String.valueOf(cpu.getMemorySupport()));
                view.showField("Cooler Included", String.valueOf(cpu.getCoolerIn()));

                break;
            case "GPU":
                GPU gpu = (GPU) component;
                view.showField("Memory (GB)", String.valueOf(gpu.getMemory()));
                view.showField("Clock", String.valueOf(gpu.getClock()));
                view.showField("Clock Boost", String.valueOf(gpu.getClockBoost()));
                view.showField("Length", String.valueOf(gpu.getLength()));
                view.showField("TDP", String.valueOf(gpu.getTdp()));
                view.showField("Expansion Slots", String.valueOf(gpu.getExpansionSlots()));
                view.showField("HDMI Outputs", String.valueOf(gpu.getHdmiOut()));
                view.showField("Display Port Outputs", String.valueOf(gpu.getDpOut()));

                break;
            case "Memory":
                Memory memory = (Memory) component;
                view.showField("Type", memory.getType());
                view.showField("Capacity (GB)", String.valueOf(memory.getCapacity()));
                view.showField("Modules", String.valueOf(memory.getModules()));

                break;
            case "Cooler":
                Cooler cooler = (Cooler) component;
                view.showField("RPM", String.valueOf(cooler.getRpm()));
                view.showField("Noise Level", String.valueOf(cooler.getNoiseLevel()));
                view.showField("Sockets", String.join(", ", cooler.getSocket()));
                view.showField("Water Cooled", String.valueOf(cooler.getWaterCooled()));

                break;
            case "Storage":
                Storage storage = (Storage) component;
                view.showField("Capacity (TB)", String.valueOf(storage.getCapacity()));
                view.showField("Type", storage.getType());
                view.showField("Drive Interface", storage.getDriveFormFactor());

                break;
            case "PSU":
                PSU psu = (PSU) component;
                view.showField("Wattage", String.valueOf(psu.getWattage()));

                break;
            default:
                break;
        }
    }

    public boolean updateField(String fieldName, String newValue) {
        if (component == null) {
            return false;
        }

        switch (fieldName) {
            case "Name":
                try {
                    component.setName(newValue);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Manufacturer":
                try {
                    component.setManufacturer(newValue);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Price":
                try {
                    component.setPrice(Double.parseDouble(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Image (URL)":
                try {
                    component.setImg(newValue);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Form Factor":
                try {
                    if (component instanceof Tower) {
                        List<String> forms = new ArrayList<>();
                        for (String f : newValue.split(",")) {
                            forms.add(f.trim());
                        }
                        ((Tower) component).setFormFactor(forms);
                    } else if (component instanceof Motherboard) {
                        ((Motherboard) component).setFormFactor(newValue);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Power Supply":
                try {
                    ((Tower) component).setPowerSupply(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Max GPU Length (mm)":
                try {
                    ((Tower) component).setMaxGpuLength(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Drive Bays 2.5":
                try {
                    int db35 = ((Tower) component).getDriveBays("3_5");
                    ((Tower) component).setDriveBays(new HashMap<>(
                            Map.of("2_5", Integer.parseInt(newValue), "3_5", db35)
                    ));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Drive Bays 3.5":
                try {
                    int db25 = ((Tower) component).getDriveBays("2_5");
                    ((Tower) component).setDriveBays(new HashMap<>(
                            Map.of("3_5", Integer.parseInt(newValue), "2_5", db25)
                    ));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Expansion Slots":
                try {
                    if (component instanceof Tower) {
                        ((Tower) component).setExpansionSlots(Integer.parseInt(newValue));
                    } else if (component instanceof GPU) {
                        ((GPU) component).setExpansionSlots(Integer.parseInt(newValue));
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Dimensions (HxWxD)":
                try {
                    ((Tower) component).setDimensions(newValue);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Socket":
                try {
                    if (component instanceof CPU) {
                        ((CPU) component).setSocket(newValue);
                    } else if (component instanceof Motherboard) {
                        ((Motherboard) component).setSocket(newValue);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Memory Type":
                try {
                    ((Motherboard) component).setMemoryType(newValue);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Memory Slots":
                try {
                    ((Motherboard) component).setMemorySlots(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "M.2 Slots":
                try {
                    ((Motherboard) component).setM2Slots(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "PCI Slots":
                try {
                    ((Motherboard) component).setPciSlots(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "SATA Slots":
                try {
                    ((Motherboard) component).setSataSlots(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Core Count":
                try {
                    ((CPU) component).setCoreCount(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Clock":
                try {
                    if (component instanceof CPU) {
                        ((CPU) component).setClock(Integer.parseInt(newValue));
                    } else if (component instanceof GPU) {
                        ((GPU) component).setClock(Integer.parseInt(newValue));
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Clock Boost":
                try {
                    if (component instanceof CPU) {
                        ((CPU) component).setClockBoost(Integer.parseInt(newValue));
                    } else if (component instanceof GPU) {
                        ((GPU) component).setClockBoost(Integer.parseInt(newValue));
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "TDP":
                try {
                    if (component instanceof CPU) {
                        ((CPU) component).setTdp(Integer.parseInt(newValue));
                    } else if (component instanceof GPU) {
                        ((GPU) component).setTdp(Integer.parseInt(newValue));
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Cache":
                try {
                    ((CPU) component).setCache(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Memory Support":
                try {
                    ((CPU) component).setMemorySupport(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Cooler Included":
                try {
                    ((CPU) component).setCoolerIn(Boolean.parseBoolean(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Memory (GB)":
                try {
                    ((GPU) component).setMemory(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Length":
                try {
                    ((GPU) component).setLength(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "HDMI Outputs":
                try {
                    ((GPU) component).setHdmiOut(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Display Port Outputs":
                try {
                    ((GPU) component).setDpOut(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Type":
                try {
                    if (component instanceof Memory) {
                        ((Memory) component).setType(newValue);
                    } else if (component instanceof Storage) {
                        ((Storage) component).setType(newValue);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Capacity (GB)":
                try {
                    ((Memory) component).setCapacity(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Modules":
                try {
                    ((Memory) component).setModules(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "RPM":
                try {
                    ((Cooler) component).setRpm(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Noise Level":
                try {
                    ((Cooler) component).setNoiseLevel(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Sockets":
                try {
                    ((Cooler) component).setSocket(new ArrayList<>(List.of(newValue.split(","))));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Water Cooled":
                try {
                    ((Cooler) component).setWaterCooled(Boolean.parseBoolean(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Capacity (TB)":
                try {
                    ((Storage) component).setCapacity(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            case "Wattage":
                try {
                    ((PSU) component).setWattage(Integer.parseInt(newValue));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            default:
                return false;
        }
    }

    public void saveComponent() {
        repo.editComponent(component);
        view.closeWithSuccess();
    }

    public void deleteComponent() {
        repo.deleteComponent(component);
        view.closeWithDeletion();
    }

    public void cancelEdit() {
        view.closeWithCancel();
    }
}
