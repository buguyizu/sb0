package net.xxx.sb0.linkage.ds;

public enum ConTarget {

	Default, Third;

	private static ThreadLocal<ConTarget> holder = ThreadLocal.withInitial(() -> ConTarget.Default);

	public static void set(ConTarget connectionTarget) {
		holder.set(connectionTarget);
	}

	public static ConTarget get() {
		return holder.get();
	}

	public static void reset() {
		holder.set(ConTarget.Default);
	}
}
