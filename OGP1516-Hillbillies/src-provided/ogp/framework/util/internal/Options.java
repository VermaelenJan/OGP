package ogp.framework.util.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class Options {

	public static class Option<T> {
		private ObjectProperty<T> currentValue = new SimpleObjectProperty<>();
		private ObjectProperty<T> defaultValue = new SimpleObjectProperty<>();
		private String name;
		private Option<Boolean> condition;
		private boolean enabled = true;
		private String description;
		private final Class<T> type;

		public Option(Class<T> type, String name, String description, T defaultValue) {
			this(type, name, description, defaultValue, null);
		}

		public Option(Class<T> type, String name, String description, T defaultValue, Option<Boolean> condition) {
			this.type = type;
			this.defaultValue.set(defaultValue);
			this.description = description;
			this.name = name;
			this.condition = condition;
			reset();
		}

		public Class<T> getType() {
			return type;
		}

		public String getDescription() {
			return description;
		}

		public boolean isEnabled() {
			return enabled && (condition == null || condition.getCurrentValue());
		}

		public Option<Boolean> getCondition() {
			return condition;
		}

		public void setCondition(Option<Boolean> condition) {
			this.condition = condition;
		}

		public String getName() {
			return name;
		}

		public void setCurrentValue(T currentValue) {
			this.currentValue.set(currentValue);
		}

		public T getCurrentValue() {
			return currentValue.get();
		}

		public T getDefaultValue() {
			return defaultValue.get();
		}

		public void setDefaultValue(T defaultValue) {
			this.defaultValue.set(defaultValue);
		}

		public void reset() {
			this.currentValue.set(defaultValue.get());
		}

		public ObjectProperty<T> currentValueProperty() {
			return currentValue;
		}
	}

	private final Map<String, Option<?>> options = new HashMap<>();

	protected Option<Boolean> addBooleanOption(String name, String description, boolean defaultValue) {
		return addOption(Boolean.class, name, description, defaultValue);
	}

	protected Option<Integer> addIntegerOption(String name, String description, int defaultValue) {
		return addOption(Integer.class, name, description, defaultValue);
	}

	public Property<Boolean> getBooleanValue(String name) {
		return getValue(name, Boolean.class);
	}
	
	public Property<Integer> getIntegerValue(String name) {
		return getValue(name, Integer.class);
	}


	protected <T> Option<T> addOption(Class<T> type, String name, String description, T defaultValue) {
		Option<T> option = new Option<>(type, name, description, defaultValue);
		options.put(name, option);
		return option;
	}

	protected <T> ObjectProperty<T> getValue(String name, Class<? extends T> type) {
		@SuppressWarnings("unchecked")
		Option<T> option = (Option<T>) options.get(name);
		return option.currentValueProperty();
	}

	public Collection<Option<?>> getOptions() {
		return Collections.unmodifiableCollection(options.values());
	}
}
