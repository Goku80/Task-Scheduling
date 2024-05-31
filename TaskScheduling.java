import java.util.ArrayList;
import java.util.PriorityQueue;

public class TaskScheduling
{
    // Definiert eine Aufgabenklasse, um Aufgaben mit Start- und Endzeit zu repräsentieren
    public static class Task implements Comparable
    {
        final int startTime;    // Startzeit der Aufgabe
        final int endTime;      // Endzeit der Aufgabe

        // Konstruktor, um eine Aufgabe mit gegebener Start- und Endzeit zu erstellen
        public Task(int startTime, int endTime)
        {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        // Vergleicht zwei Aufgaben basierend auf ihrer Startzeit
        @Override
        public int compareTo(Object other)
        {
            Task t = (Task) other;
            if (this.startTime < t.startTime)
                return -1;
            else if (this.startTime > t.startTime)
                return 1;
            else
                return 0;
        }

        // Überprüft, ob diese Aufgabe sich mit einer anderen überschneidet
        public boolean overlaps(Task other)
        {
            if (other.startTime >= endTime)
                return false;
            if (other.endTime <= startTime)
                return false;
            return true;
        }

        // Gibt eine lesbare Darstellung der Aufgabe zurück
        @Override
        public String toString()
        {
            return "(" + startTime + "," + endTime + ")";
        }
    }

    // Definiert eine Maschinenklasse, um Aufgaben für jede Maschine zu speichern
    public static class Machine
    {
        ArrayList<Task> tasks;

        public Machine()
        {
            tasks = new ArrayList<Task>();  // Liste der Aufgaben, die dieser Maschine zugewiesen sind
        }

        // Fügt der Maschine eine Aufgabe hinzu
        public void add(Task t)
        {
            tasks.add(t);
        }

        // Überprüft, ob eine Aufgabe mit einer vorhandenen Aufgabe in Konflikt steht
        public boolean conflicts(Task other)
        {
            for (Task t : tasks)
            {
                if (t.overlaps(other))
                    return true;    // Konflikt gefunden
            }
            return false;   // Kein Konflikt
        }

        // Gibt eine lesbare Darstellung der Maschine und ihrer Aufgaben zurück
        @Override
        public String toString()
        {
            String result = "";
            for (var t : tasks)
                result += t.toString() + " ";
            return result;
        }
    }

    public static void main(String[] args)
    {
        var tasks = new PriorityQueue<Task>();      // Warteschlange für Aufgaben
        var machines = new ArrayList<Machine>();    // Liste von Maschinen

        // Aufgaben zur Warteschlange
        tasks.add(new Task(1, 3));
        tasks.add(new Task(1, 4));
        tasks.add(new Task(2, 5));
        tasks.add(new Task(3, 7));
        tasks.add(new Task(4, 7));
        tasks.add(new Task(6, 9));
        tasks.add(new Task(7, 8));

        int m = 0; // Anfangs haben wir keine Maschinen

        // Bearbeitet alle Aufgaben in der Warteschlange
        while (!tasks.isEmpty())
        {
            Task currentTask = tasks.poll(); // Schritt c)I) entfernt die Aufgabe mit der frühesten Startzeit

            boolean assigned = false;
            // Überprüft jede vorhandene Maschine, ob sie die Aufgabe aufnehmen kann
            for (int j = 0; j < m; j++)
            {
                if (!machines.get(j).conflicts(currentTask))
                {
                    machines.get(j).add(currentTask); // Schritt c)II) fügt die Aufgabe einer passenden Maschine hinzu
                    assigned = true;
                    break;
                }
            }

            // Wenn keine passende Maschine gefunden wurde, wird eine neue erstellt
            if (!assigned)
            {
                Machine newMachine = new Machine();
                newMachine.add(currentTask);        // Fügt die Aufgabe der neuen Maschine hinzu
                machines.add(newMachine);
                m++;    // Erhöht die Anzahl der Maschinen
            }
        }

        // Gibt die Aufgaben jeder Maschine aus
        for (int i = 0; i < machines.size(); i++)
        {
            System.out.println("Machine M" + i + ": " + machines.get(i));
        }
    }
}
