package _20220219_MyShell;


// scp 192.168.1.197 src/_20220219_MyShell/banner-scp.txt src/_20220219_MyShell/banner.txt
public class Main {
    public static void main(String[] args) {
        Shell shell = Shell.createDefault();

//        shell.launch();
        SecureCopy secureCopy = new SecureCopy("192.168.1.2", "test.txt", "src/_20220219_MyShell/banner.txt");
        secureCopy.execute();
    }
}
