public class BinaryTest {
	public static void main(String[] args) {
		int a = 0b10;
		int b = 0b00;
		int c = 0b00;
		int d = 0b00;
		int e = 0b11;
		int f = (((((((a << 2) | b) << 2) | c) << 2) | d) << 2) | e;
		int g = (a << 2) | b;
		int h = (g << 2) | c;
		int i = (h << 2) | d;
		int j = (i << 2) | e;
		System.out.println(Integer.toBinaryString(f));
		System.out.println(Integer.toBinaryString(j));

		int ne = f & 0b11;
		int nd = (f >> 2) & 0b11;
		int nc = (f >> 4) & 0b11;
		int nb = (f >> 6) & 0b11;
		int na = (f >> 8) & 0b11;

		System.out.println("a: " + Integer.toBinaryString(a) + " = " + a + ", " + Integer.toBinaryString(na) + " = " + na);
		System.out.println("b: " + Integer.toBinaryString(b) + " = " + b + ", " + Integer.toBinaryString(nb) + " = " + nb);
		System.out.println("c: " + Integer.toBinaryString(c) + " = " + c + ", " + Integer.toBinaryString(nc) + " = " + nc);
		System.out.println("d: " + Integer.toBinaryString(d) + " = " + d + ", " + Integer.toBinaryString(nd) + " = " + nd);
		System.out.println("e: " + Integer.toBinaryString(e) + " = " + e + ", " + Integer.toBinaryString(ne) + " = " + ne);

		String code = "01000111";
		System.out.println(Integer.parseInt(code, 2));

	}
}
