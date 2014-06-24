public class BinaryTest {
	public static void main(String[] args) {
		int a = 0b11;
		int b = 0b10;
		int c = 0b00;
		int d = 0b10;
		int e = 0b01;
		int f = (((((((a << 2) | b) << 2) | c) << 2) | d) << 2) | e;
		System.out.println(Integer.toBinaryString(f));

		int g = f & 0b11;
		int h = (f >> 2) & 0b11;
		int j = (f >> 4) & 0b11;
		int k = (f >> 6) & 0b11;
		int l = (f >> 8) & 0b11;

		System.out.println("g: " + Integer.toBinaryString(g));
		System.out.println("h: " + Integer.toBinaryString(h));
		System.out.println("j: " + Integer.toBinaryString(j));
		System.out.println("k: " + Integer.toBinaryString(k));
		System.out.println("l: " + Integer.toBinaryString(l));

	}
}
