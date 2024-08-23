 /* 
                      Bom-Bom Oyunu Nedir?
 
 Bu projede bom-bom oyunu tasarlayacagiz. Bu oyunda haritamizi bir txt dosyadan cekecegiz.
 Daha sonrasinda ekrana haritayi yazdirip kullanıcıdan secim yapmasini isteyecegiz.
 Bu secim dogrultusunda, secilen sayi ve ona temas eden tüm ayni sayilar yerine "x" yazdirilacak.
 Oyun kullanici 0,0 girisi yapana kadar tekrar tekrar devam edecek.
 Tum hücrelerin "x" olmasi halinde ekrana bir tebrik mesaji yazdirilir ve oyun sonlanir.
 Bu oyunu tasarlarken recursive (özyinelemeli) metot yapisini kullanacagiz.
 */

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.Scanner;

public class proje_final {

 // txt dosyadaki sayilari okuyup ardindan string tipinde matrise aktaran metot. 
 public static String[][] txtFileToStringMatrix() {
    
     File dosya = new File("harita.txt"); // Okunacak dosya adını belirle
     String[][] tempStringMatrix = null; // Geçici String matrisini tanımla

     try {
         Scanner scanner = new Scanner(dosya); // Dosyayı okuma işlemi için Scanner kullan
         StringBuilder stringBuilder = new StringBuilder();

         // Dosyadaki her satırı okuyarak birleştir
         while (scanner.hasNextLine()) {
             stringBuilder.append(scanner.nextLine());
         }

         scanner.close(); // Dosya okuma işlemi tamamlandı, Scanner'ı kapat

         // Boşluk karakterlerini temizle ve harfleri içeren bir string elde et
         String harfler = stringBuilder.toString().replaceAll("\\s", "");

         int elemanSayisi = harfler.length(); // Toplam harf sayısını al
         int satir = 10; // Matrisin satır sayısını belirle
         int sutun = (int) Math.ceil((double) elemanSayisi / satir); // Matrisin sütun sayısını belirle

         tempStringMatrix = new String[satir][sutun]; // Matrisi oluştur

         int index = 0;

         // String matrisini doldur
         for (int j = 0; j < satir; j++) {
             for (int k = 0; k < sutun; k++) {
                 if (index < elemanSayisi) {
                     tempStringMatrix[j][k] = String.valueOf(harfler.charAt(index));
                     index++;
                 } else {
                     tempStringMatrix[j][k] = " "; // Harf kalmadıysa boşluk karakteri ekle
                 }
             }
         }

      } catch (FileNotFoundException e) {
              e.printStackTrace(); // Dosya bulunamazsa hatayı ekrana yazdır
         }

    return tempStringMatrix; // Bu metot oluşturulan String matrisini döndür
 }

//----------------------------------------------------------------------------------------------------------   

    // secilen rakamin sagini, solunu, ustunu ve altini kisaca 'cevresini' kontrol eden metot.
    public static void controls(int x, int y, String[][] stringMatris) {
        
        if (x < 0 || y < 0 || y >= stringMatris.length || x >= stringMatris[0].length ||        //sayinin sinirinin ve degerinin kontrolleri
                stringMatris[y][x].equals("x") || stringMatris[y][x].equals("null")) {
            return;
        }
        //karsilastirmada kullanmak icin secilenRakam degiskeni olusturup kullanicinin sectigi rakami atiyorum.
        String secilenRakam = stringMatris[y][x];

        stringMatris[y][x] = "x";

        //Sag kontrol: x ekseni sinirlarini ve secilen rakama esitligini kontrol ediyorum.
        if (x + 1 < stringMatris[0].length && secilenRakam.equals(stringMatris[y][x + 1])) {
            controls(x + 1, y, stringMatris);            
        }

        //Sol kontrol: x ekseni sinirlarini ve secilen rakama esitligini kontrol ediyorum.
        if (x - 1 >= 0 && secilenRakam.equals(stringMatris[y][x - 1])) {
            controls(x - 1, y, stringMatris);
        }

        //Ust kontrol: y ekseni sinirlarini ve secilen rakama esitligini kontrol ediyorum.
        if (y - 1 >= 0 && secilenRakam.equals(stringMatris[y - 1][x])) {
            controls(x, y - 1, stringMatris);
        }

        //Alt kontrol: y ekseni sinirlarini ve secilen rakama esitligini kontrol ediyorum.
        if (y + 1 < stringMatris.length && secilenRakam.equals(stringMatris[y + 1][x])) {
            controls(x, y + 1, stringMatris);
        }
    }

//----------------------------------------------------------------------------------------------------------
    
    public static void main(String[] args) {
        Scanner enter = new Scanner(System.in);     //main icerisinde scanner tanimladik.

        String[][] gameMap = txtFileToStringMatrix();      // Baslangicta txt ile bir matris olusturuluyor. Olusturalan matris oyun haritamiz oluyor.

        System.out.println("Hedef dosyadan çekilen matris: ");

        while (true) {    //donguyu while ile olusturup sadece bir durumda (0,0 girisinde) donguden cikilmasi icin sonsuz dongu olsuturdum.
            System.out.println("Güncel harita: ");

            System.out.println("");     // bir satir bos bıraksın.

            int lineCounter = 1;

            System.out.print("  x  ");      // x eksenini belirtmek için yazdim.

            for (int j = 0; j < gameMap[0].length; j++) {  // secimin kolay saglanmasi icin sayilar ile kordinatları belirttim.
                System.out.print((j + 1) + " ");
            }

            System.out.println("");

            System.out.println("y   ----------------------");  // y eksenini belirtmek için yazdım

            // hizalı bir bicimde haritayı ekrana yazdırma ve y eksenini belirtme            
            for (String[] satir : gameMap) {
                if (lineCounter < 10) {
                    System.out.print(lineCounter + "  | ");
                } else {
                    System.out.print(lineCounter + " | ");
                }

                for (String eleman : satir) {
                    if (eleman.equals("x")) {
                        System.out.print("x ");
                    } else {
                        System.out.print(eleman + " ");
                    }
                }

                lineCounter++;

                System.out.println("");
            }

            System.out.println("");

            //Oyunun temel mantiginin oyuncu ile paylasilmasi ve degerlerin klavyeden alinmasi 
            
            System.out.println("Oyundan çıkmak isterseniz x ve y degerlerini 0 giriniz (0,0)");

            // y ekseni icin giris
            System.out.print("Lütfen patlatmak istediğiniz sayinin kordinatini giriniz (y x ->seklinde giris yapiniz): ");
            //ilk girilen deger y'ye atanacak

            int y = enter.nextInt() - 1;   

            //ikinci girilen deger x'e atanacak
            int x = enter.nextInt() - 1;

            // x ve y degerlerini bir azalttigim icin kullanıcının 0,0 girisi yaptıgı senaryoda x=-1 ve y=-1 degerlerini alır.
            //Bu secim yapildiysa programi sonlandir.
            if (x == -1 && y == -1) {
                System.out.println("");
                System.out.println("GULE GULE!");
                break;
            }
            
            // sınır ve hücrenin zatem x olma durumu kontrolü
            if (x >= 0 && y >= 0 && x < gameMap[0].length && y < gameMap.length &&
                    !gameMap[y][x].equals("x") && !gameMap[y][x].equals("null")) {

                controls(x, y, gameMap);

                int xCounter = 0;

                for (String[] satır : gameMap) {
                    for (String eleman : satır) {
                        if (eleman.equals("x")) {
                            xCounter++;
                        }
                    }
                }

                //haritayi hizali bir sekilde ekrana yazdir.
                if (xCounter == gameMap.length * gameMap[0].length) {
                    int lineIndex = 1;
                    System.out.println("  x  1 2 3 4 5 6 7 8 9 10");
                    System.out.println("y   -------------------");

                    for (String[] satir : gameMap) {
                        if (lineIndex < 10) {
                            System.out.print(lineIndex + "  | ");
                        } else {
                            System.out.print(lineIndex + " | ");
                        }

                        for (String eleman : satir) {
                            System.out.print(eleman + " ");
                        }

                        lineIndex++;

                        System.out.println("");
                    }

                    //Tum hucrelerin "x" olmasi durumunda ekrana tebrik mesaji yazdirma.
                    System.out.println("Tebrikler! Oyun bitti. Tüm sayıları patlattınız :)");
                    return;
                }

            } else {
                //Yanlis kordinat girilmesi durumunda ekrana masaj yazdiracagiz.
                System.out.println("");
                System.out.println("Geçersiz koordinatlar veya zaten patlatılmış hücre. Lütfen geçerli bir koordinat girin.");
            }
        }
    }
}
