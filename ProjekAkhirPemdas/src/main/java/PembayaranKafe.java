import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class PembayaranKafe {

    public static void main(String[] args) {
        tampilanUtama();
    }

    public static void tampilanUtama() {
        Scanner s = new Scanner(System.in);
        System.out.println("=======================================================");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.print("Masukkan pilihan menu (input nomornya) : ");
        int pilihan = s.nextInt();
        switch (pilihan) {
            case 1 -> loginMenu();
            case 2 -> {
                System.out.println("================ Keluar ============");
                System.exit(0);
            }
            default -> {
                System.err.println("PILIHAN ANDA SALAH (PILIH 1-2)!");
                tampilanUtama();
            }
        }
    }
    public static void loginMenu() {
        Scanner s = new Scanner(System.in);
        System.out.println("============ Sistem Pembayaran Kafe Ala-Ala ============");
        System.out.println("========================= Login ========================");
        System.out.print("Masukkan UserName Kafe : ");
        String username = s.next();
        boolean cekUsername = false, cekPassword = false;
        if (username.equals("apayanamanya")) {
            cekUsername = true;
        }
        System.out.print("Masukkan Password Kafe : ");
        String password = s.next();
        if (password.equals("apayapasswordnya")) {
            cekPassword = true;
        }
        if (cekPassword && cekUsername) {
            do {
                tampilanMenu();
            } while (true);
        } else {
            System.err.println("Login invalid");
            tampilanUtama();
        }


    }

    public static void tampilanMenu() {
        Scanner s = new Scanner(System.in);
        System.out.println("""
                1. Daftar Menu
                2. Menu Pemesanan
                3. Log Out
                4. Exit
                """);
        System.out.print("Masukkan pilihan menu : ");
        int pilihMenu = s.nextInt();
        switch (pilihMenu) {
            case 1 -> tampilMenu();
            case 2 -> pemesananMenu();
            case 3 -> tampilanUtama();
            case 4 -> {
                System.out.println("\n\n================= Sistem Berakhir ====================");
                System.exit(0);
            }
            default -> {
                System.err.println("PILIHAN ANDA SALAH (PILIH 1-4)!");
                tampilanMenu();
            }
        }
    }

    public static void pemesananMenu() {
        Scanner s = new Scanner(System.in);
        // input

        // Nama Pembeli
        System.out.print("Masukkan nama : ");
        String namaPembeli = s.nextLine();
        // Jumlah Beli
        System.out.print("Masukkan jumlah pembelian : ");
        int jumlahBeli = s.nextInt();
        s.nextLine();

        // Masukkan barang yg dibeli
        String[] beli = new String[jumlahBeli];
        for (int i = 0; i < beli.length; i++) {
            System.out.print("Barang yg dibeli : ");
            beli[i] = s.nextLine();

        }
        System.out.print("Uang pembayaran : Rp ");
        int pembayaran = s.nextInt();
        // Input hari & tanggal
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE/dd/MM/yyyy");
        String day = sdf.format(today);
        String [] pecah = day.split("/");
        Diskon(pecah[0]);


        //Output

        System.out.println("Nama Pemesan : " + namaPembeli);
        System.out.println("Tanggal      : " + day);
        cekMenu(beli);
        System.out.println("Diskon       : " + tampilanRupiah(Diskon(pecah[0])));
        int total = totalHarga + hargaPajak(totalHarga);
        System.out.println("Pajak        : " + tampilanRupiah(hargaPajak(totalHarga)));
        System.out.println("Harga Total  : " + tampilanRupiah(total));
        System.out.println("Kembalian    : " + tampilanRupiah(cekKembalian(pembayaran, total)));
        System.out.println("================= Selesai ======================");
        System.out.println();
        totalHarga=0;
        tampilanMenu();


    }

    private static int totalHarga;

    private static int cekKembalian(int pembayaran, int totalHarga) {
        return pembayaran - totalHarga;
    }

    private static int hargaPajak(int pembayaran) {
        return pembayaran * 10 / 100;
    }

    private static int Diskon(String tanggal) {
        // Potongan genap
        if (tanggal.equals("Tuesday")){
            System.out.println();
            int potongan = totalHarga * 6 / 100;
            totalHarga = totalHarga - potongan;
            return potongan;
        }else if (tanggal.equals("Friday")){
            System.out.println();
            int potongan = totalHarga * 6 / 100;
            totalHarga = totalHarga - potongan;
            return potongan;
        }else{
            return 0;
        }
    }

    private static final String[][] menuMinuman = {
            {"Americano", "15000"},
            {"Capucino", "17000"},
            {"Coffe latte", "16000"},
            {"Orange Juice", "11000"},
            {"Green Tea", "26000"}
    };

    private static final String[][] menuMakanan = {
            {"Beef Burger", "11000"},
            {"Omelet", "9000"},
            {"Carbonara", "36000"},
            {"Sandwich", "11000"},
            {"Nasi Goreng", "13000"}
    };

    private static void cekMenu(String[] menuBeli) {
        System.out.println("\nMenu dan Harga Pesanan   : \n");
        for (String s : menuBeli) {
            for (int j = 0; j < 5; j++) {
                if (s.equals(menuMakanan[j][0])) {
                    System.out.printf("%-13s: %-11s\n", s, tampilanRupiah(Integer.parseInt(menuMakanan[j][1])));
                    int hargaMakan = Integer.parseInt(menuMakanan[j][1]);
                    totalHarga = totalHarga + hargaMakan;
                }
                if (s.equals(menuMinuman[j][0])) {
                    System.out.printf("%-13s: %-11s\n", s, tampilanRupiah(Integer.parseInt(menuMinuman[j][1])));
                    int hargaMinum = Integer.parseInt(menuMinuman[j][1]);
                    totalHarga = totalHarga + hargaMinum;
                }
            }
        }

        System.out.println("Harga Pesanan: " + tampilanRupiah(totalHarga));

    }

    public static String tampilanRupiah(int desimal) {
        String tampilanRupiah = NumberFormat.getInstance(Locale.GERMANY).format(desimal);
        tampilanRupiah = "Rp " + tampilanRupiah + ",00";
        return tampilanRupiah;
    }

    public static void tampilMenu(){
        Scanner s = new Scanner(System.in);
        System.out.println("""
                ========== Menu ========
                1. Menu Makanan
                2. Menu Minuman
                3. Kembali
                """);
        System.out.print("Masukkan pilihan : ");
        int pilihan = s.nextInt();
        switch (pilihan){
            case 1 -> getMenuMakanan();
            case 2 -> getMenuMinuman();
            case 3 -> tampilanMenu();
            default -> {
                System.err.println("PILIHAN ANDA SALAH (PILIH 1-3)!");
                tampilMenu();
            }
        }
    }

    private static void getMenuMakanan(){
        System.out.println("============= Daftar Makanan ==============");
        for (String[] strings : menuMakanan) {
            System.out.print("Nama makanan : ");
            System.out.println(strings[0]);
            System.out.print("Harga Makanan :");
            int hargaMakanan = Integer.parseInt(strings[1]);
            System.out.println(tampilanRupiah(hargaMakanan));
            System.out.println();
        }
        System.out.println("================= Selesai ==================");
        tampilMenu();
    }

    private static void getMenuMinuman(){
        System.out.println("============= Daftar Minuman ==============");
        for (String[] strings : menuMinuman) {
            System.out.print("Nama Minuman : ");
            System.out.println(strings[0]);
            System.out.print("Harga Minuman :");
            int hargaMinuman = Integer.parseInt(strings[1]);
            System.out.println(tampilanRupiah(hargaMinuman));
            System.out.println();
        }
        System.out.println("================= Selesai ==================");
        tampilMenu();
    }
}
