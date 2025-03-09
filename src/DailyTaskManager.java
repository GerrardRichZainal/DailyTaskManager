import java.time.LocalTime;
import java.util.*;


//menyimpan daftar tugas dinamis dengan linked list.
class TaskNode {
    String task;
    TaskNode next;

    public TaskNode(String task) {
        this.task = task;
        this.next = null;
    }
}

public class DailyTaskManager {
    private static String[] tasks = {"Periksa email", "Hadiri kuliah", "Latihan", "Mengerjakan tugas", "Membaca buku"};
    private static Stack<String> completedTasks = new Stack<>();
    private static TaskNode head = null;
    private static Scanner scanner = new Scanner(System.in);
    private static String userName;

    public static void main(String[] args) {
        greetUser();
        int choice;
        do {
            displayMenu(); 
            System.out.print("\033[31m" + "Pilihan: ");
            System.out.print("\033[0m");
            while (!scanner.hasNextInt()) {
                System.out.println("Harap masukkan angka yang valid.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: displayTasks(); break;
                case 2: updateTask(); break;
                case 3: markTaskCompleted(); break;
                case 4: undoTaskCompletion(); break;
                case 5: addDynamicTask(); break;
                case 6: removeDynamicTask(); break;
                case 7: displayDynamicTasks(); break;
                case 8: System.out.println("\033[33m" + "Terima Kasih Telah Menggunakan Daily Task Mananger" + "\033[0m"); break;
                default: System.out.println("\033[31m" + "Pilihan tidak valid." + "\\033[0m");
            }
        } while (choice != 8);
        scanner.close();
    }

    private static void greetUser() { //input nama pengguna
        System.out.print("\033[31m" + "Masukkan nama Anda: " + "\033[0m" );
        userName = scanner.nextLine();
        String greeting = getGreeting();
        System.out.println("\033[33m"  + greeting + ", "+"\033[32m" + userName + "\033[0m" );
        
        System.out.println("\033[33m"  + "        .--,       ");
        System.out.println("     .-.    __,,,__/    | ");
        System.out.println("    /   \\'-\'`        `-./_");
        System.out.println("    |    |               `)");
        System.out.println("     \\   `             `\\ ;");
        System.out.println("    /       ,        ,    |");
        System.out.println("    |      /         : O /_");
        System.out.println("    |          O  .--;__   '.");
        System.out.println("    |                (  )`.  |");
        System.out.println("    \\                 `-` /  |");
        System.out.println("     \\          ,_  _.-./`  /");
        System.out.println("      \\          \\\"`-.(    /   Selamat Mengerjakan Tugas");
        System.out.println("      |           `---'   /--.");
        System.out.println("    ,--\\___..__        _.'   /--.");
        System.out.println("    \"          `-._  _`/    '    '.");
        System.out.println("    .' ` ' .       ``    '        ." + "\033[0m" );
    }

    private static String getGreeting() {//untuk mengeluarkan sesuai jam jika di run
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) return "Selamat Pagi";
        else if (hour >= 12 && hour < 16) return "Selamat Siang";
        else if (hour >= 16 && hour < 19) return "Selamat Sore";
        else return "Selamat Malam";
    }

    private static void displayMenu() { // menu utama
        System.out.println("\n==============================");
        System.out.println("=      Daily Task Manager    = ");
        System.out.println("==============================");
        System.out.println("1. Lihat daftar tugas");
        System.out.println("2. Perbarui tugas");
        System.out.println("3. Tandai tugas selesai");
        System.out.println("4. Batalkan penyelesaian tugas");
        System.out.println("5. Tambah tugas ke daftar dinamis");
        System.out.println("6. Hapus tugas dari daftar dinamis");
        System.out.println("7. Tampilkan semua tugas dalam daftar dinamis");
        System.out.println("8. Keluar");
        System.out.println("==============================");
    }

    private static void displayTasks() {// menampilkan tugas statis
        System.out.println("==============================");
        System.out.println("\nDaftar Tugas Harian:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + ". " + tasks[i]);
        }
    }

    private static void updateTask() { // memperbaharui tugas 
        displayTasks();
        System.out.print("Masukkan indeks tugas yang akan diperbarui: ");
        if (!scanner.hasNextInt()) {
            System.out.println("\033[31m" + "Input tidak valid."+ "\033[0m" );
            scanner.next();
            return;
        }
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < tasks.length) {
            System.out.print("Masukkan tugas baru: ");
            tasks[index] = scanner.nextLine();
            System.out.println("Tugas diperbarui.");
        } else {
            System.out.println("\033[31m" +"Indeks tidak valid."+"\033[0m");
        }
    }

    private static void markTaskCompleted() { // tugas yang diselesaikan
        displayTasks();
        System.out.print("Masukkan indeks tugas yang selesai: ");
        if (!scanner.hasNextInt()) {
            System.out.println("\033[31m" +"Input tidak valid." + "\033[0m");
            scanner.next();
            return;
        }
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < tasks.length) {
            completedTasks.push(tasks[index]);
            tasks[index] = "[Selesai] " + tasks[index];
            System.out.println("Tugas ditandai selesai dan ditambahkan ke stack.");
        } else {
            System.out.println("\033[31m" +"Indeks tidak valid."+"\033[0m");
        }
    }

    private static void undoTaskCompletion() { // membatalkan tugas yang selesai
        if (!completedTasks.isEmpty()) {
            String undoneTask = completedTasks.pop();
            for (int i = 0; i < tasks.length; i++) {
                if (tasks[i].equals("[Selesai] " + undoneTask)) {
                    tasks[i] = undoneTask;
                    break;
                }
            }
            System.out.println("Tugas dikembalikan: " + undoneTask);
        } else {
            System.out.println("Tidak ada tugas yang bisa dibatalkan.");
        }
    }

    private static void addDynamicTask() { //menambahkan tugas ke daftar dinamis
        System.out.print("Masukkan tugas baru: ");
        String task = scanner.nextLine();
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Tugas ditambahkan.");
    }

    private static void removeDynamicTask() { 
        if (head == null) {
            System.out.println("Daftar tugas kosong.");
            return;
        }
    
        displayDynamicTasks(); // Tampilkan daftar tugas sebelum menghapus
        System.out.print("Masukkan indeks tugas yang akan dihapus: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("\033[31m" + "Input tidak valid. Masukkan angka!" + "\033[0m");
            scanner.next();
            return;
        }
    
        int index = scanner.nextInt();
        scanner.nextLine(); // Konsumsi newline
    
        if (index < 0) {
            System.out.println("\033[31m" + "Indeks tidak valid." + "\033[0m");
            return;
        }
    
        TaskNode temp = head, prev = null;
        int currentIndex = 0;
    
        // Jika yang dihapus adalah elemen pertama
        if (index == 0) {
            head = head.next;
            System.out.println("Tugas dihapus.");
            return;
        }
    
        // Cari node yang ingin dihapus
        while (temp != null && currentIndex < index) {
            prev = temp;
            temp = temp.next;
            currentIndex++;
        }
    
        // Jika tidak ditemukan
        if (temp == null) {
            System.out.println("\033[31m" + "Indeks tidak valid." + "\033[0m");
            return;
        }
    
        // Hapus node
        prev.next = temp.next;
        System.out.println("Tugas dihapus.");
    }
    

    private static void displayDynamicTasks() { // menampilkan semua tugas dari dftar dinamis
        TaskNode temp = head;
        if (temp == null) {
            System.out.println("Tidak ada tugas dalam daftar.");
            return;
        }
        System.out.println("\nDaftar Tugas Dinamis:");
        while (temp != null) {
            System.out.println("- " + temp.task);
            temp = temp.next;
        }
    }
}
