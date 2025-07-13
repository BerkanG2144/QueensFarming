package farming.commands;

public interface Command {
    /**
     * Führt das Kommando aus.
     */
    void execute();

    /**
     * Gibt zurück, ob das Kommando gültig ausgeführt werden kann.
     */
    boolean isValid();
}
