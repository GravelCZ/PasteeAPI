package cz.GravelCZ.PasteeeAPI.Utils;

public class Pair<L, R> {

	private L left;
	private R right;
	
	public Pair(L l, R r) {
		left = l;
		right = r;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return left.equals(p.left) && right.equals(p.right);
    }
	
	@Override
    public int hashCode() {
        return (right == null ? 0 : right.hashCode()) ^ (left == null ? 0 : left.hashCode());
    }
	
	public static <R, L> Pair<R, L> create(R r, L l) {
		return new Pair<R, L>(r, l);
		
	}
	
}
