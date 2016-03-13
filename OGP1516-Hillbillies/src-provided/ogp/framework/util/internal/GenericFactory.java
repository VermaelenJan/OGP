package ogp.framework.util.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A factory that creates objects based on the source object's type.
 * 
 * Supplier functions can be registered for any type. If no exact match is
 * found, suppliers for superclasses are tried.
 * 
 */
public class GenericFactory {

	private final Map<Class<?>, Function<?, ?>> suppliers = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <S, T> T create(S object) {
		Class<?> supplierClass = getSupplierClass(object.getClass());
		if (supplierClass == null) {
			throw new IllegalArgumentException(
					"No factory registered for class " + object.getClass() + " or any of its parents.");
		}
		// if (supplierClass != object.getClass()) {
		// System.out.println("[" + this.getClass().getName() + "] No supplier
		// found for "
		// + object.getClass().getName() + ", using supplier for " +
		// supplierClass.getName() + " instead.");
		// }
		Function<S, T> supplier = (Function<S, T>) suppliers.get(supplierClass);
		if (supplier != null) {
			T result = supplier.apply(object);
			return result;
		} else {
			throw new IllegalArgumentException("No sprite factory registered for class " + object.getClass());
		}
	}

	private Class<?> getSupplierClass(Class<?> c) {
		if (c == null) {
			return null;
		}
		if (suppliers.containsKey(c)) {
			return c;
		}
		Class<?> candidate = getSupplierClass(c.getSuperclass());
		if (candidate != null) {
			return candidate;
		}
		for (Class<?> implementedInterface : c.getInterfaces()) {
			candidate = getSupplierClass(implementedInterface);
			if (candidate != null) {
				return candidate;
			}
		}
		return null;
	}

	public <S, T> void registerSupplier(Class<S> type, Function<S, ? extends T> supplier) {
		suppliers.put(type, supplier);
	}

	public boolean hasSupplierFor(Object object) {
		return getSupplierClass(object.getClass()) != null;
	}
}
