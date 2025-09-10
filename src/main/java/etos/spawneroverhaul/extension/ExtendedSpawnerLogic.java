package etos.spawneroverhaul.extension;

public interface ExtendedSpawnerLogic {
    int getBreakDelay();
    void setBreakDelay(int delay);
    void triggerBreak(int delay);
}
