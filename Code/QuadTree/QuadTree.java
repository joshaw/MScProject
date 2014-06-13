public class QuadTree<E> {

	private QuadTree<E> tl;
	private QuadTree<E> tr;
	private QuadTree<E> bl;
	private QuadTree<E> br;
	private E value;
	private boolean leaf;

	public QuadTree(E value) {
		this.value = value;
		this.leaf = true;
	}

	public QuadTree(QuadTree<E> tl, QuadTree<E>tr, QuadTree<E> bl,
			QuadTree<E> br) {
		this.tl = tl;
		this.tr = tr;
		this.bl = bl;
		this.br = br;
		this.leaf = false;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public E getValue() {
		return value;
	}

	public QuadTree<E> getTL() {
		return tl;
	}

	public QuadTree<E> getTR() {
		return tr;
	}

	public QuadTree<E> getBL() {
		return bl;
	}

	public QuadTree<E> getBR() {
		return br;
	}

	@Override
	public String toString() {
		if (leaf) {
			return value.toString() + "";
		} else {
			return "[" + tl.toString() + ", " + tr.toString() + ", "
				+ bl.toString() + ", " + br.toString() + "]";
		}
	}

	public String toDot() {
		String result = "digraph g {\n";
		result += toDot("t");
		return result + "}";
	}

	private String toDot(String s) {
		String result = "";
		if (!this.isLeaf()) {
			result += s + " [label=\"X\"]\n";
			result += s + "0 [label=\"" + this.getTL().getValue() + "\"]\n";
			result += s + "->" + s + "0\n";
			result += this.getTL().toDot(s+"0");
			result += s + "1 [label=\"" + this.getTR().getValue() + "\"]\n";
			result += s + "->" + s + "1\n";
			result += this.getTR().toDot(s+"1");
			result += s + "2 [label=\"" + this.getBL().getValue() + "\"]\n";
			result += s + "->" + s + "2\n";
			result += this.getBL().toDot(s+"2");
			result += s + "3 [label=\"" + this.getBR().getValue() + "\"]\n";
			result += s + "->" + s + "3\n";
			result += this.getBR().toDot(s+"3");
		}
		return result;
  }

}
