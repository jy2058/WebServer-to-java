import org.apache.tika.Tika;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*System.out.println("ddddddddddd");
		File file = new File("");
		System.out.println(file.getAbsolutePath());*/
		String aa = "favicon.ico";
		String detect = new Tika().detect(aa);
		System.out.println(detect);
		}

}
